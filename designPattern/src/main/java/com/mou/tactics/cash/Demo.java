package com.mou.tactics.cash;

import com.mou.tactics.enums.NumberEnum;

/**
 * 业务使用
 *
 * @author: mou
 * @date: 2019-08-24
 */
public class Demo {

    public static void main(String[] args) {
        CashContext context = new CashContext(NumberEnum.REBATE);
        //打折后的价格
        double result = context.getResult(500);
    }
}
