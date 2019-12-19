package com.mou.mapstuct.model;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

@Data
@NoArgsConstructor
public class UserDto {
    private String name;
    private Integer age;
    //与po对象属性名不一致
    private Date birth;
    private float wallet;
    //通过po对象的某一属性扩展
    private String birthformat;
}