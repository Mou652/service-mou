package cn.mou.map;

import cn.hutool.core.lang.Console;
import com.google.common.collect.ArrayListMultimap;
import org.junit.Test;

/**
 * @author: mou
 * @date: 2019-08-22
 */

public class MapTest {

    @Test
    public void test(){
        ArrayListMultimap<String, Object> multimap = ArrayListMultimap.create();
        multimap.put("A",1);
        multimap.put("A",2);
        multimap.put("A",3);
        Console.log(multimap);
    }
}
