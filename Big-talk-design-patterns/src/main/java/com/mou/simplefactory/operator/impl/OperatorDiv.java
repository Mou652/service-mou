package com.mou.simplefactory.operator.impl;

import com.mou.simplefactory.operator.Operator;

/**
 * 除法实现类
 *
 * @author: mou
 * @date: 2020/10/3
 */
public class OperatorDiv implements Operator {

    public Object calculate(double numA, double numb) {
        return numA / numb;
    }
}
