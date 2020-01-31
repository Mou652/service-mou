package cn.mou.map;

import com.google.common.collect.HashMultiset;
import org.junit.Test;

/**
 * 新集合类型
 *
 * @author: mou
 * @date: 2020/1/22
 */
public class MultiDemo {

    @Test
    public void test() {
        HashMultiset<Object> multiset = HashMultiset.create();

    }

    @Test
    public void test1() {
        /*
        返回可用处理器的Java虚拟机的数量。
        Runtime.getRuntime().availableProcessors()
         */
        int max = Integer.max(Runtime.getRuntime().availableProcessors(), 5);
        System.out.println(max);
    }
}
