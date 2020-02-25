package com.mou.leetcode;

import cn.hutool.core.util.ArrayUtil;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * 两数之和
 * 给定一个整数数组 nums 和一个目标值 target，请你在该数组中找出和为目标值的那 两个 整数，并返回他们的数组下标。
 * 你可以假设每种输入只会对应一个答案。但是，你不能重复利用这个数组中同样的元素。
 * <p>
 * 示例:给定 nums = [2, 7, 11, 15], target = 9
 * 因为 nums[0] + nums[1] = 2 + 7 = 9
 * 所以返回 [0, 1]
 * <p>
 *
 * @author: mou
 * @date: 2020/2/25
 */
public class FirstQuestion {

    /**
     * 执行用时 :45 ms, 在所有 Java 提交中击败了14.83%的用户
     * 内存消耗 :39.6 MB, 在所有 Java 提交中击败了5.07%的用户
     */
    @Test
    public void test() {
        int[] nums = {3, 7, 3, 7};
        int target = 10;
        System.out.println(ArrayUtil.toString(twoSum1(nums, target)));
        // 优解
        System.out.println(ArrayUtil.toString(twoSum2(nums, target)));
    }

    /**
     * 自己解法
     *
     * @param nums
     * @param target
     * @return
     */
    public int[] twoSum1(int[] nums, int target) {
        int[] result = new int[2];
        stop:
        for (int i = 0; i < nums.length; i++) {
            int num0 = nums[i];
            for (int j = i + 1; j < nums.length; j++) {
                if (num0 + nums[j] == target) {
                    result[0] = i;
                    result[1] = j;
                    break stop;
                }
            }
        }

        return result;
    }

    /**
     * 思路 1 - 时间复杂度: O(N)- 空间复杂度: O(N)******
     */
    public int[] twoSum2(int[] nums, int target) {
        Map<Integer, Integer> lookup = new HashMap<Integer, Integer>();
        int[] res = new int[2];
        for (int i = 0; i < nums.length; i++) {
            if (lookup.containsKey(target - nums[i])) {
                res = new int[]{lookup.get(target - nums[i]), i};
                break;
            } else {
                lookup.put(nums[i], i);
            }
        }
        return res;
    }

}
