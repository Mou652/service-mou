/**
 * Project Name:excelutil
 * File Name:PhoneModel.java
 * Package Name:com.lkx.util
 * Date:2017年6月7日上午9:43:19
 * Copyright (c) 2017~2020, likaixuan@test.com.cn All Rights Reserved.
 *
*/

package com.mou.excel.model;

/**
 * 手机类
 */
public class PhoneModel {
    private String phoneName;
    private String color;
    private double price;
    public String getPhoneName() {
        return phoneName;
    }
    public void setPhoneName(String phoneName) {
        this.phoneName = phoneName;
    }
    public String getColor() {
        return color;
    }
    public void setColor(String color) {
        this.color = color;
    }
    public double getPrice() {
        return price;
    }
    public void setPrice(double price) {
        this.price = price;
    }
}

