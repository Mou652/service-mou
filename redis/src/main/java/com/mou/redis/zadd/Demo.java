package com.mou.redis.zadd;

import redis.clients.jedis.Jedis;

/**
 * @author: mou
 * @date: 2019/12/22
 */
public class Demo {
    public static void main(String[] args) {
        Jedis jedis = new Jedis();
        User user1 = new User("12345", "常少鹏", 99.9);
        User user2 = new User("12346", "王卓卓", 99.8);
        User user3 = new User("12347", "邹雨欣", 96.8);
        User user4 = new User("12348", "郑伟山", 98.8);
        User user5 = new User("12349", "李超杰", 99.6);
        User user6 = new User("12350", "董明明", 99.0);
        User user7 = new User("12351", "陈国峰", 100.0);
        User user8 = new User("12352", "楚晓丽", 99.6);
        jedis.zadd("game".getBytes(), user1.getScore(), ObjectSer.ObjectToByte(user1));
        jedis.zadd("game".getBytes(), user2.getScore(), ObjectSer.ObjectToByte(user2));
        jedis.zadd("game".getBytes(), user3.getScore(), ObjectSer.ObjectToByte(user3));
        jedis.zadd("game".getBytes(), user4.getScore(), ObjectSer.ObjectToByte(user4));
        jedis.zadd("game".getBytes(), user5.getScore(), ObjectSer.ObjectToByte(user5));
        jedis.zadd("game".getBytes(), user6.getScore(), ObjectSer.ObjectToByte(user6));
        jedis.zadd("game".getBytes(), user7.getScore(), ObjectSer.ObjectToByte(user7));
        jedis.zadd("game".getBytes(), user8.getScore(), ObjectSer.ObjectToByte(user8));

    }
}
