package com.mou.leetcode.xiaohao;

/**
 * 最长公共前缀
 * 编写一个函数来查找字符串数组中的最长公共前缀。如果不存在公共前缀，则返回""
 *
 * @author: mou
 * @date: 2020/9/27
 */
public class CommonPrefix {

    public static void main(String[] args) {
        String s = longestCommonPrefix(new String[]{"abcde", "abcd", "ab"});
        System.out.println(s);
    }

    public static String longestCommonPrefix(String[] strs) {
        String result = "";
        int j;
        char a;
        if (strs.length <= 0) {
            return "";
        }
        for (int i = 0; i < strs[0].length(); i++) {
            a = strs[0].charAt(i);
            for (j = 0; j < strs.length; j++) {
                if (i < strs[j].length()) {
                    if (a != strs[j].charAt(i)) {
                        break;
                    }
                } else {
                    break;
                }
            }
            if (j >= strs.length) {
                result += a;
            } else {
                break;
            }
        }
        return result;
    }

}
