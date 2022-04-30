package cn.moublog;

import cn.hutool.core.lang.WeightRandom;
import cn.hutool.core.util.RandomUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * @author: mou
 * @date: 2020/11/9
 */
public class Demo2 {

    public static void main(String[] args) {
        boolean a = true;
        if (a) {
            System.out.println("111");
        } else {
            System.out.println();
        }

        System.out.println();
        System.out.println('a');
        System.out.println("哈哈哈");
        // 权重
        List<WeightRandom.WeightObj<Long>> weightList = new ArrayList<>();
        weightList.add(new WeightRandom.WeightObj<Long>(1L, 0.08));
        weightList.add(new WeightRandom.WeightObj<Long>(2L, 0.01));
        weightList.add(new WeightRandom.WeightObj<Long>(3L, 0.01));
        weightList.add(new WeightRandom.WeightObj<Long>(4L, 0.1));
        weightList.add(new WeightRandom.WeightObj<Long>(6L, 0.8));
        WeightRandom wr = RandomUtil.weightRandom(weightList);
        for (int j = 0; j < 100; j++) {
            System.out.println(wr.next().toString());
        }
    }

    private static int printOut(int number) {
        if (number > 10) {
            int i = number / 10;
            System.out.println("递归:" + i);
            printOut(i);
        }
        int i = number % 10;
        System.out.println("求余:" + i);
        return i;
    }

    /**
     * 设有一组N个数要确定第k个最大值
     * 2
     *
     * @param arr   源
     * @param index k
     * @return
     */
    public int getArrayMax(int[] arr, int index) {
        // 按照大小排序
        int[] newArr = new int[arr.length];
        for (int i = 0; i < newArr.length; i++) {

        }

        return 0;
    }
}
