package com.mou.simplefactory.operator;

/**
 * 运算接口类
 *
 * @author: mou
 * @date: 2020/10/3
 */
public interface Operator {

    /**
     * 计算两个数的操作结果
     *
     * @param numA 数字一
     * @param numb 数字二
     * @return 操作结果
     */
    Object calculate(double numA, double numb);

}
