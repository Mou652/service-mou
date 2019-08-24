package com.mou.tactics.cash;

import com.mou.tactics.constant.BaseCashSuper;
import com.mou.tactics.enums.NumberEnum;

/**
 * 策略与简单工厂结合:将实例化具体策略的过程由客户端转移到这里Context类
 *
 * @author: mou
 * @date: 2019-08-24
 */
public class CashContext {
    private BaseCashSuper cs;

    public CashContext(NumberEnum type) {

        switch (type) {
            //正常收费
            case NORMAL:
                cs = new CashNormal();
                break;
            //打八折
            case REBATE:
                cs = new CashRebate(0.8);
                break;
            //满三百减100
            case RETURN:
                cs = new CashReturn(300, 100);
                break;
            default:
        }
    }

    public double getResult(double money) {
        return cs.accptCash(money);
    }
}
