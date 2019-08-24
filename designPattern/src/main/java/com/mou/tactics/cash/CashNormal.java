package com.mou.tactics.cash;

import com.mou.tactics.constant.BaseCashSuper;

/**
 * 正常收费子类
 * @author: mou
 * @date: 2019-08-24
 */
public class CashNormal extends BaseCashSuper {

    @Override
    public double accptCash(double money) {
        return money;
    }
}
