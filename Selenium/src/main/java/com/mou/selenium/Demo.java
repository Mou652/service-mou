package com.mou.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

/**
 * @author: mou
 * @date: 2021/5/20
 */
public class Demo {

    public static void main(String[] args) throws InterruptedException {
        // 新建一个firefox浏览器实例
        WebDriver driver = new FirefoxDriver();
        // 打开码云首页
        driver.get("https://gitee.com/");
        // 弹出登陆框
        WebElement showLoginSubmit = driver.findElement(By.className("git-nav-user__login-item"));
        showLoginSubmit.click();

        // 输入账号
        driver.findElement(By.id("user_login")).sendKeys("847474659@qq.com");
        // 输入密码
        driver.findElement(By.id("user_password")).sendKeys("tanfu847474");
        // 点击登录
        driver.findElement(By.xpath("//div[@class='field']/input[@name='commit']")).click();
        // 睡眠两秒刷新页面
        Thread.sleep(2000);
        driver.navigate().refresh();
        // 睡眠两秒后退
        Thread.sleep(2000);
        driver.navigate().back();
        // 睡眠两秒关闭窗口
        Thread.sleep(2000);
        driver.close();
    }
}
