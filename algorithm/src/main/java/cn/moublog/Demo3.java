package cn.moublog;

import cn.hutool.core.lang.WeightRandom;
import cn.hutool.core.util.RandomUtil;
import com.google.common.collect.Lists;
import org.junit.Test;

import java.util.List;

/**
 * @author: mou
 * @date: 2021/1/10
 */
public class Demo3 {

    @Test
    public void test() {
        List<WeightRandom.WeightObj<Long>> weightRandomList = Lists.newArrayList();
        weightRandomList.add(new WeightRandom.WeightObj<Long>(1L, 0.001));
        weightRandomList.add(new WeightRandom.WeightObj<Long>(2L, 0.00));
        for (long i = 0; i < 10000000; i++) {
            Long next = RandomUtil.weightRandom(weightRandomList).next();
            if (next == 2) {
                System.out.println("抽中了mac");
            }
            System.out.println(next);
        }
    }
}
