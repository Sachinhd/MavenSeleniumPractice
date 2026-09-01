package com.sachin.pages;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class HomePage {

    WebDriver driver;
    WebDriverWait wait;

    // Locators
    By productsHeading = By.xpath("//span[text()='Products']");
    By menuButton = By.id("react-burger-menu-btn");
    By logoutLink = By.id("logout_sidebar_link");

    // Constructor
    public HomePage(WebDriver driver) {
        this.driver = driver;
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    // Get Products Heading
    public String getProductsHeading() {
        return wait.until(
                ExpectedConditions.visibilityOfElementLocated(productsHeading)
        ).getText();
    }

    // Click Menu
    public void clickMenu() {
        wait.until(
                ExpectedConditions.elementToBeClickable(menuButton)
        ).click();
    }

    // Click Logout
    public void clickLogout() {
        wait.until(
                ExpectedConditions.elementToBeClickable(logoutLink)
        ).click();
    }
}