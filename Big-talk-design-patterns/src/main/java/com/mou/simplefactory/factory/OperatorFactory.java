package com.mou.simplefactory.factory;

import com.mou.simplefactory.operator.Operator;
import com.mou.simplefactory.operator.impl.OperatorAdd;
import com.mou.simplefactory.operator.impl.OperatorDiv;
import com.mou.simplefactory.operator.impl.OperatorMulti;
import com.mou.simplefactory.operator.impl.OperatorSub;

/**
 * 计算工厂类
 *
 * @author: mou
 * @date: 2020/10/3
 */
public class OperatorFactory {

    public static Operator getOperator(String operator) {
        Operator oper = null;
        if ("+".equals(operator)) {
            oper = new OperatorAdd();
        } else if ("-".equals(operator)) {
            oper = new OperatorSub();
        } else if ("*".equals(operator)) {
            oper = new OperatorMulti();
        } else if ("/".equals(operator)) {
            oper = new OperatorDiv();
        }
        return oper;
    }
}
