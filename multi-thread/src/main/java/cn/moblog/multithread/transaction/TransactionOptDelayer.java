package cn.moblog.multithread.transaction;

import cn.hutool.core.collection.CollUtil;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;
import org.springframework.transaction.support.TransactionSynchronizationAdapter;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.Map;
import java.util.Stack;

/**
 * 延迟事务提交
 *
 * @author: defei
 * @date 2017/9/15
 */
@Component
@Slf4j
public class TransactionOptDelayer {

    private static ThreadLocal<Map<String, Stack<Executable>>> afterTransactionOpts = new NamedThreadLocal<>("redis后置操作thread-local");

    /**
     * 延迟执行代码,直至事务提交
     * 如果当前不在事务中,抛出异常
     *
     * @param executable
     */
    public void executeAfterTransactionCommit(Executable executable) {
        if (TransactionSynchronizationManager.isActualTransactionActive()) {
            //manager的synchronizations容器是set,可以随便注册,不会重复
            TransactionSynchronizationManager.registerSynchronization(AfterTransactionSynchronization.instance);
            Stack<Executable> executables = getExecutablesCreateIfNecessary();
            executables.push(executable);
        } else {
            throw new RuntimeException("事务后置操作*必须*在一个活跃的事务中");
        }
    }


    /**
     * 从threadlocal获取执行器栈,如果没有拿到,就创建一个
     * 要根据事务名称区分事务
     *
     * @return
     */
    private Stack<Executable> getExecutablesCreateIfNecessary() {
        Map<String, Stack<Executable>> executableMap = afterTransactionOpts.get();
        String transactionName = getTransactionName();
        if (executableMap == null) {
            executableMap = Maps.newHashMap();
            afterTransactionOpts.set(executableMap);
        }
        Stack<Executable> executables = executableMap.get(transactionName);
        if (executables == null) {
            executables = new Stack<>();
            executableMap.put(transactionName, executables);
        }
        return executables;
    }

    /**
     * spring事务默认为全类名加方法名,如果是编程事务,可能会null
     * 为null的情况下,使用default
     *
     * @return
     */
    private static String getTransactionName() {
        String name = TransactionSynchronizationManager.getCurrentTransactionName();
        if (StringUtils.isBlank(name)) {
            return "default";
        }
        return name;
    }


    /**
     * 事务提交之后,执行延迟操作
     */
    @Slf4j
    private static class AfterTransactionSynchronization extends TransactionSynchronizationAdapter {

        public static AfterTransactionSynchronization instance = new AfterTransactionSynchronization();

        private AfterTransactionSynchronization() {
        }

        /**
         * 事务完成之后(包括回滚), 清除绑定的操作
         *
         * @param status
         */
        @Override
        public void afterCompletion(int status) {
            Map<String, Stack<Executable>> map = afterTransactionOpts.get();
            if (map != null) {
                map.remove(getTransactionName());
            }
        }


        /**
         * 事务提交成功后,执行操作
         * 如果事务没有成功提交,绑定的操作会在afterCompletion方法中清除
         */
        @Override
        public void afterCommit() {
            Map<String, Stack<Executable>> stackMap = afterTransactionOpts.get();
            String transactionName = getTransactionName();
            Stack<Executable> executables = stackMap.get(transactionName);
            if (CollUtil.isNotEmpty(executables)) {
                while (!executables.isEmpty()) {
                    Executable executable = executables.pop();
                    try {
                        executable.exec();
                    } catch (Throwable e) {
                        log.error("执行事务后置操作出错, transactionName={},msg={}", transactionName, e.getMessage(), e);
                    }
                }
                executables.clear();
            }
        }
    }
}
