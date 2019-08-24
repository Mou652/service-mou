package com.mou.tactics.cash;

import com.mou.tactics.constant.BaseCashSuper;

/**
 * 打折收费子类
 *
 * @author: mou
 * @date: 2019-08-24
 */
public class CashRebate extends BaseCashSuper {
    private double moneyRebate = 1d;

    public CashRebate(double moneyRebate) {
        this.moneyRebate = moneyRebate;
    }

    @Override
    public double accptCash(double money) {
        return money * moneyRebate;
    }
}
