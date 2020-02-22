package cn.moblog.multithread.vo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.List;

/**
 * 多线程增删改VO
 *
 * @author YongDi.Tang
 * @version 1.0
 * @date 2019-06-26
 */
@Getter
@Setter
@ToString
public class ThreadExecuteUpdateVO<T> {

    /**
     * 执行的实体Class
     */
    private Class<T> clazz;

    /**
     * 执行的实体数据集合
     */
    private List<T> executeList;
}
