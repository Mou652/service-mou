package cn.mou.map;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.ArrayList;

/**
 * @author: mou
 * @date: 2020/2/12
 */
public class GuavaDemo {

    /**
     * 连接器
     */
    private static final Joiner JOINER = Joiner.on(",").skipNulls();

    /**
     * 分割器
     */
    private static final Splitter SPLITTER = Splitter.on(",").trimResults().omitEmptyStrings();

    /**
     * Joiner是连接器，Splitter是分割器，通常我们会把它们定义为static final，利用on生成对象后在应用到String进行处理，
     * 这是可以复用的。
     */
    @Test
    public void test1() {
        ArrayList<String> strings = Lists.newArrayList("a", null, "b");
        String join = JOINER.join(Lists.newArrayList("a", null, "b"));
        System.out.println(join);
        for (String s : SPLITTER.split("a, ,b,c")) {
            System.out.print(s);
        }
    }

    @Test
    public void test2() {

    }
}
