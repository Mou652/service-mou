package com.mou.leetcode.xiaohao;

/**
 * @author: mou
 * @date: 2020/9/28
 */
public class TradingStock {

    public static void main(String[] args) {
        System.out.println(maxProfit(new int[]{1, 3, 5, 8}));
    }

    /**
     * 买卖股票的最佳时机
     * 如果你最多只允许完成一笔交易（即买入和卖出一支股票），设计一个算法来计算你所能获取的最大利润。
     * 第 2 天（股票价格 = 1）的时候买入，在第 3 天（股票价格 = 5）的时候卖出, 这笔交易所能获得利润 = 5-1 = 4 。
     *
     * @return
     */
    public int test(int[] arr) {

        return 0;
    }

    public static int maxProfit(int[] prices) {
        int fstBuy = Integer.MIN_VALUE, fstSell = 0;
        int secBuy = Integer.MIN_VALUE, secSell = 0;
        for (int p : prices) {
            fstBuy = Math.max(fstBuy, -p);
            fstSell = Math.max(fstSell, fstBuy + p);
            secBuy = Math.max(secBuy, fstSell - p);
            secSell = Math.max(secSell, secBuy + p);
        }
        return secSell;
    }
}
