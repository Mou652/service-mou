package cn.mou.map;

import cn.hutool.core.util.ArrayUtil;
import com.google.common.base.Joiner;
import com.google.common.base.Throwables;
import com.google.common.collect.Lists;
import com.google.common.collect.Ordering;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static cn.hutool.core.lang.Validator.isEmpty;
import static cn.hutool.core.util.StrUtil.isNotBlank;

/**
 * @author: mou
 * @date: 2020/1/13
 */
public class Demo {
    public static void main(String[] args) {
        System.out.println(format(32500));
        System.out.println(remove("1001,100001", "1001,2001,2003"));

    }

    @Test
    public void test1() {
        //集合 -> 字符串(separator)
        List<String> roleIds = Lists.newArrayList("1001", "1002", "1003", "");
        String str = Joiner.on(",").join(roleIds);
        System.out.println(str);

        Ordering<Comparable> natural = Ordering.natural();
        int compare = natural.compare(1, 2);
        System.out.println(compare);
    }

    /**
     * 有时候，你会想把捕获到的异常再次抛出。这种情况通常发生在Error或RuntimeException被捕获的时候，
     * 你没想捕获它们，但是声明捕获Throwable和Exception的时候，也包括了了Error或RuntimeException。
     * Guava提供了若干方法，来判断异常类型并且重新传播异常。
     */
    @Test
    public void test2() {
        try {
            int i = 1 / 0;
        } catch (Throwable e) {
            Throwables.propagateIfPossible(e, NullPointerException.class);
            throw Throwables.propagate(e);
        }

    }

    @Test
    public void test3() {
        int FILE_MAX_SIZE = 1024 * 1024;
        System.out.println(FILE_MAX_SIZE);
    }

    /**
     * ReTrying重试
     */
    @Test
    public void test4() {
    }

    /**
     * 访问人数/收藏人数格式转换
     * <p>
     * 转换规则:
     * 1.小于1w,展示具体数值
     * 2.大于等于X万,小于等于X.1万,展示位Xw+
     * 3.大于等于X.1万,四舍五入,展示位X.Xw+
     * 4.大于999万,展示位999w+
     *
     * @param count 需要转换的人数
     * @return 转换后的格式
     */
    private static String format(long count) {
        if (isEmpty(count) || count == 0) {
            return "0";
        }
        String format = null;
        double doubleNum = (double) count / 10000;
        String temp = String.valueOf(doubleNum);
        long indexOne = Integer.parseInt(String.valueOf(temp.charAt(temp.indexOf(".") + 1)));
        if (doubleNum <= 1) {
            format = String.valueOf(count);
        } else if (doubleNum > 1 && doubleNum < 999 && indexOne <= 1) {
            format = (int) Math.floor(doubleNum) + "w+";
        } else if (doubleNum > 1 && doubleNum < 999 && indexOne > 1) {
            //四舍五入
            format = String.format("%.1f", doubleNum) + "w+";
        } else if (doubleNum >= 999) {
            format = "999w+";
        }
        return format;
    }

    /**
     * 移除已删除的角色/标签
     */
    public static Map<String, String> remove(String roleId, String lableId) {
        //已删除的角色
        Map<String, String> map = new HashMap(2);
        if (isNotBlank(roleId)) {
            List<String> roleIds = Lists.newArrayList("1001", "1002", "1003");
            String roleIdStr = Joiner.on(",").join(roleIds);
            String[] roleArray = roleId.split(",");
            String role;
            for (int i = 0, length = roleArray.length; i < length; i++) {
                if (!roleIdStr.contains(roleArray[i])) {
                    roleArray[i] = null;
                }
            }
            //移除空元素
            if (ArrayUtil.hasNull(roleArray)) {
                role = ArrayUtil.join(ArrayUtil.removeEmpty(roleArray), ",");
            } else {
                role = ArrayUtil.join(roleArray, ",");
            }
            map.put("roleId", role);
        }

        //移除已删除的标签
        if (isNotBlank(lableId)) {
            List<String> lableIds = Lists.newArrayList("1001", "1002", "1003");
            String lableIdStr = Joiner.on(",").join(lableIds);
            String[] lableArray = lableId.split(",");
            String lable;
            for (int i = 0, length = lableArray.length; i < length; i++) {
                if (!lableIdStr.contains(lableArray[i])) {
                    lableArray[i] = null;
                }
            }
            //移除空元素
            if (ArrayUtil.hasNull(lableArray)) {
                lable = ArrayUtil.join(ArrayUtil.removeEmpty(lableArray), ",");
            } else {
                lable = ArrayUtil.join(lableArray, ",");
            }
            map.put("lableId", lable);
        }
        return map;
    }
}
