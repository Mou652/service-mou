package com.mou.leetcode;

import org.junit.Test;

/**
 * 给定两个大小为 m 和 n 的有序数组 nums1 和 nums2。
 * 请你找出这两个有序数组的中位数，并且要求算法的时间复杂度为 O(log(m + n))。
 * 你可以假设 nums1 和 nums2 不会同时为空。
 * <p>
 * 示例 :
 * nums1 = [1, 3]
 * nums2 = [2]
 * 则中位数是 2.0
 *
 * @author: mou
 * @date: 2020/2/27
 */
public class FindMedianSortedArrays {

    @Test
    public void test() {

    }

    public double findMedianSortedArrays(int[] nums1, int[] nums2) {
        int length1 = nums1.length;
        int length2 = nums2.length;
        int length3 = length1 + length2;
        int[] resultArray = new int[length3];

        for (int i = 0; i < length3; i++) {
            //num1
            if (i < length1) {

            }

            //num2
            if (i < length2) {

            }
        }

        return 0;
    }
}
