package com.example.mongo.entity;

import lombok.Data;
import org.mongodb.morphia.annotations.Entity;

/**
 * @author: mou
 * @date: 2022/3/12
 */
@Data
@Entity
public class User {

    private String name;

    private Integer age;

    private Article article;

    private Integer scope;
}
