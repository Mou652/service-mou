import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;

/**
 * @author: mou
 * @date: 2019-08-28
 */
@Slf4j
public class JsonTest {

    @Test
    public void test() {
        String jsonStr = "{\"b\":\"value2\",\"c\":\"value3\",\"a\":\"value1\"}";
//方法一：使用工具类转换
        JSONObject jsonObject = JSONUtil.parseObj(jsonStr);
//方法二：new的方式转换
        JSONObject jsonObject2 = new JSONObject(jsonStr);
        System.out.println(jsonObject);
//JSON对象转字符串
        String s = jsonObject.toString();
        System.out.println(s);
    }
}
