package cn.moblog.multithread.utils;

import cn.moblog.multithread.vo.ThreadExecuteUpdateVO;

import java.util.List;

/**
 * 多线程数据库增删改操作 Service
 *
 * @author YongDi.Tang
 * @version 1.0
 * @date 2019-06-26
 */
public interface ThreadExecuteUpdateService {

    /**
     * 多线程批量插入
     * - 当前方法传入的对象数据里面，可以不用包含id，如果id为空，则自动生成id
     * - 禁止在执行当前方法后，再在调用方法内执行插入/更新的数据的查询操作，因为当前方法在一个单独事务， 不受调用方事务管理，
     * 如果在插入/更新后再查询已经插入的数据，会出现脏读
     * - 注意在调用方调用本方法时，把所有的数据都放在该方法一起执行，不要再调用方再有增删改的操作了，因为如果本方法抛出异常，
     * 调用方不会回滚，调用方抛出异常，本方法不会回滚
     * -本地低U电脑测试瓶颈为一次性同时处理100w条数据，所以建议超过100w数据的分开事务处理，本方法持续维护并持续提高效率和处理量
     *
     * @param executeList 需要插入的数据组
     * @return 是否执行成功
     */
    boolean threadBatchInsert(List<ThreadExecuteUpdateVO> executeList);

    /**
     * 多线程批量更新
     * - 禁止在执行当前方法后，再在调用方法内执行插入/更新的数据的查询操作，因为当前方法在一个单独事务， 不受调用方事务管理，
     * 如果在插入/更新后再查询已经插入的数据，会出现脏读
     * - 注意在调用方调用本方法时，把所有的数据都放在该方法一起执行，不要再调用方再有增删改的操作了，因为如果本方法抛出异常，
     * 调用方不会回滚，调用方抛出异常，本方法不会回滚
     * -本地低U电脑测试瓶颈为一次性同时处理100w条数据，所以建议超过100w数据的分开事务处理，本方法持续维护并持续提高效率和处理量
     *
     * @param executeList 需要更新的数据组
     * @return 是否执行成功
     */
    boolean threadBatchUpdate(List<ThreadExecuteUpdateVO> executeList);
}
