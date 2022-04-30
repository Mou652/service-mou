package cn.mou.demo;

import cn.hutool.core.collection.CollectionUtil;
import com.google.common.collect.Lists;

import java.util.*;

import static cn.hutool.core.lang.Validator.isNotEmpty;


/**
 * 获取两个集合的不同元素
 *
 * @author: mou
 * @date: 2020/2/13
 */
public class Demo {
    public static void main(String[] args) {

        List<PlatformAppsUserService> list1 = new ArrayList<>();
        List<PlatformAppsUserService> list2 = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            PlatformAppsUserService platformAppsUserService = new PlatformAppsUserService();
            platformAppsUserService.setServiceId(i + "");
            platformAppsUserService.setUserId(1L);
            list1.add(platformAppsUserService);
        }

        for (int i = 1; i <= 3; i++) {
            PlatformAppsUserService platformAppsUserService = new PlatformAppsUserService();
            platformAppsUserService.setServiceId(i + "");
            platformAppsUserService.setUserId(1L);
            list2.add(platformAppsUserService);
        }
        System.out.println(getDiffList(list1, list2));

    }

    /**
     * 获取两个集合的不同元素
     *
     * @param collMax collection max
     * @param collMin collection min
     */
    // @SuppressWarnings("all")
    private static List<String> getDiffList(List<PlatformAppsUserService> collMax, List<PlatformAppsUserService> collMin) {
        if (CollectionUtil.isEmpty(collMax)) {
            return null;
        }

        LinkedList<String> diffList = Lists.newLinkedList();
        List<PlatformAppsUserService> max = collMax;
        List<PlatformAppsUserService> min = collMin;

        if (collMax.size() < collMin.size()) {
            max = collMin;
            min = collMax;
        }

        Map<String, Integer> map = new HashMap<>(max.size());
        max.forEach(item -> {
            map.put(item.getUserId() + ":".concat(item.getServiceId()), 1);
        });

        min.forEach(item -> {
            String key = item.getUserId() + ":".concat(item.getServiceId());
            Integer frequency = map.get(key);
            if (isNotEmpty(frequency)) {
                map.put(key, ++frequency);
                return;
            }
            map.put(key, 1);
        });
        map.forEach((key, value) -> {
            if (value == 1) {
                // userId:serviceId
                String[] split = key.split(":");
                diffList.add(split[1]);
            }
        });

        return diffList;

    }
}
