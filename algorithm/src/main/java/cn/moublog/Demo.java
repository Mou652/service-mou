package cn.moublog;

import com.google.common.collect.Maps;
import org.junit.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.HashMap;

/**
 * @author: mou
 * @date: 2020/9/26
 */
public class Demo {

    /**
     * 给定两个数组，编写一个函数来计算它们的交集。
     * <p>
     * 输入: nums1 = [1,2,2,1], nums2 = [2,2]
     * 输出: [2,2]
     */
    @Test
    public void intersectionOfArraysMain() {

        System.out.println(BigDecimal.valueOf(1).compareTo(BigDecimal.valueOf(0.3))>0);
        System.out.println(BigDecimal.valueOf(0.1).compareTo(BigDecimal.valueOf(0.3))>0);
        // int[] arr = intersectionOfArrays(new int[]{1, 2, 3, 4}, new int[]{3, 4, 4});
        // int[] arr = intersectionOfArrays2(new int[]{1, 2, 3, 4}, new int[]{3});
        // for (int i : arr) {
        //     System.out.println(i);
        // }
    }

    private int[] intersectionOfArrays(int[] arr1, int[] arr2) {
        /*
            方式一
            效率低下,复杂度o²
         */
        // 遍历最小的数组
        int[] maxArr;
        int[] minArr;
        if (arr1.length > arr2.length) {
            maxArr = arr1;
            minArr = arr2;
        } else {
            maxArr = arr2;
            minArr = arr1;
        }
        int[] resultArr = new int[minArr.length];
        int index = 0;
        // for (int i : minArr) {
        //     for (int j : maxArr) {
        //         if (i == j) {
        //             resultArr[index] = i;
        //             index++;
        //         }
        //     }
        // }

        /*
            方式二
            待优化,有重复元素的可能
         */
        HashMap<Integer, Integer> map = Maps.newHashMap();
        for (int i : arr1) {
            map.put(i, 1);
        }
        Integer count;
        for (int i : arr2) {
            count = map.get(i);
            if (count != null) {
                resultArr[index] = i;
                index++;
            }
        }

        return resultArr;
    }

    /**
     * 进阶,如果已经排好序,优化算法
     */
    public int[] intersectionOfArrays2(int[] arr1, int[] arr2) {
        Arrays.sort(arr1);
        Arrays.sort(arr2);

        int[] resultArray = new int[arr1.length];
        int i = 0, j = 0, index = 0;

        for (int k = 0; k < arr1.length; k++) {
            if (k > arr2.length) {
                break;
            }

            if (arr1[i] > arr2[j]) {
                j++;
            } else if (arr1[i] < arr2[j]) {
                i++;
            } else {
                j++;
                i++;
                resultArray[index] = arr1[k];
                index++;
            }
        }

        return resultArray;
    }

}
