package cn.mou;

import com.alibaba.fastjson.JSON;

import java.util.HashMap;
import java.util.Map;

/**
 * @author: mou
 * @date: 2019-08-28
 */
public class JsonTest {

    public static void main(String[] args) {
        /*
         * object转成map类型
         */
        Map map = new HashMap<String, Object>();
        map.put("name", "牟江川");
        Object o = map;
        map = JSON.parseObject(JSON.toJSONString(o), Map.class);
        System.out.println(map);

    }
}
