package com.mou.tactics.constant;

/**
 * 现金收费抽象类
 *
 * @author: mou
 * @date: 2019-08-24
 */
public abstract class BaseCashSuper {

    /**
     * 计算打折后的价格
     *
     * @param money 原价
     * @return 打折后的价格
     */
    public abstract double accptCash(double money);

}
