package com.mou.redis.zadd;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: mou
 * @date: 2019/12/22
 */
@Data
@NoArgsConstructor
public class User {
    private String id;                //编号
    private String name;            //姓名
    private double score;            //得分
    private int rank;                //排名

    public User(String id, String name, double score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }
}
