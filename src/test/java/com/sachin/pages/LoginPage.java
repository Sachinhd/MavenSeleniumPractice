package com.sachin.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage {

    WebDriver driver;

    // Locators
    By username = By.id("user-name");
    By password = By.id("password");
    By loginButton = By.id("login-button");

    By loginError = By.cssSelector("h3[data-test='error']");

    // Constructor
    public LoginPage(WebDriver driver) {
        this.driver = driver;
    }

    // Enter Username
    public void enterUsername(String user) {
        driver.findElement(username).sendKeys(user);
    }

    // Enter Password
    public void enterPassword(String pass) {
        driver.findElement(password).sendKeys(pass);
    }

    // Click Login
    public void clickLogin() {
        driver.findElement(loginButton).click();
    }

    // Get Login Error
    public String getLoginErrorMessage() {
        return driver.findElement(loginError).getText();
    }
}
