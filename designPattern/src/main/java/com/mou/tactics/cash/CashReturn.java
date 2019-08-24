package com.mou.tactics.cash;

import com.mou.tactics.constant.BaseCashSuper;

/**
 * 返利收费子类
 *
 * @author: mou
 * @date: 2019-08-24
 */
public class CashReturn extends BaseCashSuper {
    private double moneyCondition = 0.0d;
    private double moneyReturn = 0.0d;

    public CashReturn(double moneyCondition, double moenyReturn) {
        this.moneyCondition = moneyCondition;
        this.moneyReturn = moenyReturn;
    }

    @Override
    public double accptCash(double money) {
        double result = money;
        if (money >= moneyCondition) {
            result = money - Math.floor(money / moneyCondition) * moneyReturn;
        }
        return result;
    }
}
