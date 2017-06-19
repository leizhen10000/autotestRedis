package com.dianwoba.cn.testng.selenium;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.AssertJUnit;
import org.testng.annotations.Test;

/**
 * Created by leizhen on 2017/6/1.
 * code is far away from bugs with the god animal protecting
 * I love animals. They taste delicious.
 */
public class TestMultipleThreads {
    @Test(invocationCount = 1)
    public void loadTestThisWebsite() {
        System.out.println("start firefox browser ...");
        System.setProperty("webdriver.firefox.bin", "D:/Program Files (x86)/Mozilla Firefox/firefox.exe");
        WebDriver driver = new FirefoxDriver();
        driver.get("http://www.yiibai.com");
        System.out.println("Page Title is  " + driver.getTitle());
        AssertJUnit.assertEquals("易百教程™ - 专注于IT教程和实例", driver.getTitle());
        driver.quit();
    }

    @Test(invocationCount = 50, threadPoolSize = 10)
    public void loadGoogleWithChrome() {
        WebDriver driver = new ChromeDriver();
        driver.get("http://www.baidu.com");

        WebElement inputBox = driver.findElement(By.id("kw"));
        WebElement submitBox = driver.findElement(By.id("su"));

        inputBox.sendKeys("selenium");
        submitBox.click();

        (new WebDriverWait(driver, 10)).until(new ExpectedCondition<Boolean>() {
            public Boolean apply(WebDriver d) {
                return d.getTitle().toLowerCase().contains("selenium");
            }
        });
        driver.quit();
    }

    @Test(invocationCount = 50, threadPoolSize = 10)
    public void testThreadPools() {
        System.out.println("Thread ID : " + Thread.currentThread().getId());
    }

}
