package com.mou.simplefactory.operator.impl;

import com.mou.simplefactory.operator.Operator;

/**
 * 乘法实现类
 *
 * @author: mou
 * @date: 2020/10/3
 */
public class OperatorMulti implements Operator {

    public Object calculate(double numA, double numb) {
        return numA * numb;
    }
}
