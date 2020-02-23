package cn.moblog.multithread.utils.thread.impl;

import cn.hutool.core.collection.CollUtil;
import cn.moblog.multithread.utils.thread.EntityUtils;
import cn.moblog.multithread.utils.thread.ThreadExecuteUpdateService;
import cn.moblog.multithread.vo.ThreadExecuteUpdateVO;
import com.google.common.util.concurrent.ThreadFactoryBuilder;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.*;

/**
 * 多线程数据库增删改操作 Service 实现类
 *
 * @author mou
 * @date 2020-02-22 21:31:50
 */
@Service
@Slf4j
public class ThreadExecuteUpdateServiceImpl implements ThreadExecuteUpdateService {

    /**
     * 线程执行数
     */
    private static final int EXECUTE_SPILT = 3000;

    /**
     * 线程池最大接受线程数，所以执行多线程批量插入/更新的数据最多支持 EXECUTE_SPILT * THREAD_POOL_MAX_COUNT 条数据
     */
    private static final int THREAD_POOL_MAX_COUNT = 10000;

    /**
     * 并行线程数
     */
    public static int POOL_SIZE = 0;

    static {
        /*
            Runtime.getRuntime().availableProcessors()
            Java虚拟机返回可用处理器的数目。
         */
        POOL_SIZE = Integer.max(Runtime.getRuntime().availableProcessors(), 5);
    }

    @Autowired
    private HikariDataSource hikariDataSource;

    @Override
    public boolean threadBatchInsert(List<ThreadExecuteUpdateVO> executeList) {
        return threadExecuteUpdateHandler(executeList, true);
    }

    @Override
    public boolean threadBatchUpdate(List<ThreadExecuteUpdateVO> executeList) {
        return threadExecuteUpdateHandler(executeList, false);
    }

    /**
     * 多线程执行增删改执行器
     *
     * @param executeList 需要插入/更新的数据组
     * @param isInsert    是否是插入 - true:insert  false:update
     */
    private boolean threadExecuteUpdateHandler(List<ThreadExecuteUpdateVO> executeList, boolean isInsert) {
        long startMs = System.currentTimeMillis();

        // 是否执行成功
        boolean isComplete = false;

        // 创建线程池
        log.info("创建线程池...");
        ThreadFactory executor = new ThreadFactoryBuilder().setNameFormat("execute-pool-%d").build();
        ExecutorService singleThreadPool = new ThreadPoolExecutor(
                50, THREAD_POOL_MAX_COUNT,
                60, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(5), executor, new ThreadPoolExecutor.AbortPolicy());

        Connection conn = null;
        try {
            // 从dataSource中拿到数据库连接
            log.info("获取数据库连接...");
            conn = hikariDataSource.getConnection();

            try {
                // 设置手动提交事务
                conn.setAutoCommit(false);

                // 根据执行内容构建 FutureTask 线程组
                log.info("构建 FutureTask 线程组 ...");
                List<FutureTask<Boolean>> taskList = createTaskByExecuteList(conn, executeList, isInsert);

                // 开启线程
                log.info("开启线程 count:{} ...", taskList.size());

                // 执行线程组
                boolean isExecuteComplete = executeTaskAndGetResult(singleThreadPool, taskList);

                // 如果全部成功 则提交事务
                if (isExecuteComplete) {
                    log.info("批量插入完成 进行提交...");
                    conn.commit();
                    isComplete = true;
                    log.info("批量插入完成 提交成功");
                } else {
                    // 如果有失败，则回滚事务
                    if (!conn.isClosed()) {
                        log.info("批量插入失败 进行回滚...");
                        conn.rollback();
                        isComplete = false;
                        log.info("批量插入失败 回滚成功...");
                    }
                }
            } catch (SQLException e) {
                if (!conn.isClosed()) {
                    conn.rollback();
                }
                log.error(e.getMessage(), e);
            }
        } catch (SQLException sqlEx) {
            sqlEx.printStackTrace();
            log.info("获取 Connection 错误: {}", sqlEx.getMessage());
        } finally {
            try {
                if (null != conn) {
                    conn.close();
                }
                singleThreadPool.shutdown();
            } catch (SQLException e) {
                log.error(e.getMessage(), e);
            }
        }

        log.info("多线程插入完成,耗时:[{}]s",(System.currentTimeMillis()-startMs)/1000);
        return isComplete;
    }

    /**
     * 根据执行内容构建 FutureTask 线程组
     *
     * @param executeList 执行内容
     */
    @SuppressWarnings("unchecked")
    private List<FutureTask<Boolean>> createTaskByExecuteList(Connection conn, List<ThreadExecuteUpdateVO> executeList, boolean isInsert) {
        List<FutureTask<Boolean>> taskList = new ArrayList<>();
        // 根据每个执行对象构建拆分后的线程组
        for (ThreadExecuteUpdateVO execute : executeList) {
            Class clazz = execute.getClazz();
            List executes = execute.getExecuteList();

            // 拆分集合
            List spiltExecutes = new ArrayList<>();

            for (Object exe : executes) {
                spiltExecutes.add(exe);

                // 拆分长度达到时，进行拆分组装
                if (spiltExecutes.size() == EXECUTE_SPILT) {
                    taskList.add(getTask(conn, clazz, spiltExecutes, isInsert));
                    spiltExecutes = new ArrayList<>();
                }
            }

            // 发送最后一轮剩余的
            if (CollUtil.isNotEmpty(spiltExecutes)) {
                taskList.add(getTask(conn, clazz, spiltExecutes, isInsert));
            }

        }

        return taskList;
    }

    /**
     * 获取Task
     *
     * @param clazz         执行的class
     * @param spiltExecutes 分割的数据集合
     * @param isInsert      是否插入
     * @param <T>           泛型
     */
    private <T> FutureTask<Boolean> getTask(Connection conn, Class<T> clazz, List<T> spiltExecutes, boolean isInsert) {
        // 构建执行对象
        ThreadExecuteUpdateVO<T> executeUpdateVO = new ThreadExecuteUpdateVO<>();
        executeUpdateVO.setClazz(clazz);

        // 如果当前是插入，判断当前是否需要添加id
        if (isInsert) {
            //spiltExecutes.forEach(se -> {
            //    if (CollUtil.isEmpty(se.getId())) {
            //        se.setId(IdUtil.fastSimpleUUID());
            //    }
            //});
        }

        executeUpdateVO.setExecuteList(spiltExecutes);

        // 创建task
        ConnectionThread<T> thread = new ConnectionThread<>(conn, executeUpdateVO, isInsert);
        return new FutureTask<>(thread);
    }

    /**
     * 分割并分别执行线程组
     *
     * @param taskList 线程集合
     */
    @Deprecated
    private boolean spiltAndExecuteTask(ExecutorService singleThreadPool, List<FutureTask<Boolean>> taskList) {
        int spilt = 100;

        // 合并的单独执行的Task集合
        List<FutureTask<Boolean>> newTaskList = new ArrayList<>();

        // 要拆分的Task集合
        List<FutureTask<Boolean>> spiltTaskList = new ArrayList<>();
        for (FutureTask<Boolean> task : taskList) {
            spiltTaskList.add(task);

            if (spiltTaskList.size() == spilt) {
                // 创建task
                FutureTaskThread thread = new FutureTaskThread(spiltTaskList, singleThreadPool);
                newTaskList.add(new FutureTask<>(thread));
                spiltTaskList = new ArrayList<>();
            }
        }

        // 组装剩余的
        if (spiltTaskList.size() > 0) {
            // 创建task
            FutureTaskThread thread = new FutureTaskThread(spiltTaskList, singleThreadPool);
            newTaskList.add(new FutureTask<>(thread));
        }

        // 执行合并后的线程组
        return executeTaskAndGetResult(singleThreadPool, newTaskList);
    }

    /**
     * 执行task并获取结果
     *
     * @param singleThreadPool 线程池
     * @param taskList         task集合
     */
    private boolean executeTaskAndGetResult(ExecutorService singleThreadPool, List<FutureTask<Boolean>> taskList) {
        // 是否处理完成
        boolean isComplete;

        log.info("开始执行线程，数量：{}", taskList.size());
        // 批量执行task
        for (FutureTask<Boolean> task : taskList) {
            singleThreadPool.execute(task);
        }
        log.info("线程执行完毕， 开始获取结果... ");

        // 获取结果
        boolean[] isTrueList = new boolean[taskList.size()];
        try {
            for (int i = 0; i < taskList.size(); i++) {
                FutureTask<Boolean> task = taskList.get(i);
                while (true) {
                    if (task.isDone() && !task.isCancelled()) {
                        isTrueList[i] = task.get();
                        break;
                    }
                    else {
                        Thread.sleep(1);
                    }
                }
            }
            isComplete = BooleanUtils.and(isTrueList);

            log.info("获取结果全部成功，结果：{} ", isComplete);
        } catch (Exception e) {
            log.error("executeTaskAndGetResult 执行异常：{}", e.getMessage(), e);
            isComplete = false;
        }

        return isComplete;
    }

    /**
     * Connection 线程类
     *
     * @author YongDi.Tang
     * @version 1.0
     * @date 2019-06-26
     */
    public class ConnectionThread<T> implements Callable<Boolean> {
        /**
         * Connection对象
         */
        private Connection conn;
        /**
         * 执行增删改信息
         */
        private ThreadExecuteUpdateVO<T> execute;
        /**
         * 是否是插入 - true:insert  false:update
         */
        private boolean isInsert;

        //@Autowired
        //private UserMapper userMapper;

        ConnectionThread(Connection conn, ThreadExecuteUpdateVO<T> execute, boolean isInsert) {
            this.conn = conn;
            this.execute = execute;
            this.isInsert = isInsert;
        }

        @Override
        public Boolean call() {
            // 重连次数
            int retryCount = 5;
            // 是否事务成功执行
            boolean transactionCompleted = false;
            do {
                // 构建执行sql
                String sql = EntityUtils.getInsertString(execute.getClazz(), execute.getExecuteList());

                // 如果当前是更新，则添加on duplicate key update语句
                if (!isInsert) {
                    sql += EntityUtils.getOnDuplicateKeyUpdateString(execute.getClazz());
                }
                //userMapper.strInsert(sql);
                PreparedStatement prepareStatement = null;
                try {
                    prepareStatement = conn.prepareStatement(sql);
                    prepareStatement.executeUpdate();
                    transactionCompleted = true;
                    log.info("线程:{}插入完成",Thread.currentThread().getName());
                    sql = null;
                } catch (SQLException sqlEx) {
                    log.info("sql:{}" + sql);
                    sqlEx.printStackTrace();
                    String sqlState = sqlEx.getSQLState();
                    /*
                     * 官方给出的处理方式
                     * 这个08S01就是这个异常的sql状态。单独处理手动重新链接就可以了。
                     */
                    List<String> errorCodes = Arrays.asList("08S01", "40001");
                    if (errorCodes.contains(sqlState)) {
                        log.info("执行重试");
                        retryCount--;
                    } else {
                        retryCount = 0;
                    }
                } catch (Exception e) {
                    try {
                        conn.close();
                    } catch (SQLException e1) {
                        e1.printStackTrace();
                    }
                    log.error(e.getMessage(), e);
                } finally {
                    try {
                        if (null != prepareStatement) {
                            prepareStatement.close();
                        }
                    } catch (SQLException e) {
                        log.error(e.getMessage(), e);
                    }
                }
            } while (!transactionCompleted && (retryCount > 0));

            return transactionCompleted;
        }
    }


    /**
     * FutureTask 线程类
     *
     * @author YongDi.Tang
     * @version 1.0
     * @date 2019-06-27
     */
    public class FutureTaskThread implements Callable<Boolean> {
        /**
         * task集合
         */
        private List<FutureTask<Boolean>> taskList;
        /**
         * 线程池
         */
        private ExecutorService singleThreadPool;

        FutureTaskThread(List<FutureTask<Boolean>> taskList, ExecutorService singleThreadPool) {
            this.taskList = taskList;
            this.singleThreadPool = singleThreadPool;
        }

        @Override
        public Boolean call() {
            // 获取结果
            return executeTaskAndGetResult(singleThreadPool, taskList);
        }
    }
}
