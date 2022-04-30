package com.mou.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

/**
 * @author: mou
 * @date: 2021/5/21
 */
public class CsjDemo {

    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.pangle.cn/?operation=login");
        driver.findElement(By.xpath("//div[(text()='邮箱登录')]")).click();
    }
}
