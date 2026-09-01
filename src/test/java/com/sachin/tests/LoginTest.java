package com.sachin.tests;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.sachin.base.BaseClass;
import com.sachin.pages.HomePage;
import com.sachin.pages.LoginPage;
import com.sachin.utilities.TestData;

public class LoginTest extends BaseClass {

    // ==========================================
    // Test 1 - Valid Login
    // ==========================================

    @Test(groups = "smoke")
    public void verifyValidLogin() {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        HomePage homePage = new HomePage(driver);

        String actualHeading =
                homePage.getProductsHeading();

        System.out.println(
                "Page Heading: " + actualHeading);

        Assert.assertEquals(
                actualHeading,
                "Products");
    }


    // ==========================================
    // Test 2 - Logout
    // ==========================================

    @Test(groups = "regression")
    public void verifyLogout() {

        LoginPage loginPage = new LoginPage(driver);

        loginPage.enterUsername("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLogin();

        HomePage homePage = new HomePage(driver);

        homePage.clickMenu();
        homePage.clickLogout();

        String actualUrl =
                driver.getCurrentUrl();

        System.out.println(
                "Login Page URL: " + actualUrl);

        Assert.assertTrue(
                actualUrl.contains("saucedemo.com"));
    }


    // ==========================================
    // Test 3 - Excel DataProvider Login
    // ==========================================

    @Test(
        groups = "regression",
        dataProvider = "loginData",
        dataProviderClass = TestData.class
    )
    public void verifyLoginWithMultipleData(
            String username,
            String password,
            String expectedResult) {

        LoginPage loginPage =
                new LoginPage(driver);

        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickLogin();

        // ======================================
        // Valid Login
        // ======================================

        if (expectedResult.equalsIgnoreCase("Valid")) {

            HomePage homePage =
                    new HomePage(driver);

            String actualHeading =
                    homePage.getProductsHeading();

            System.out.println(
                    "Username: " + username
                    + " | Expected: Valid"
                    + " | Actual: Login Successful");

         Assert.assertEquals( actualHeading, "Products");
            
            //For Failed Test Case
      //      Assert.assertEquals(actualHeading, "Wrong Heading");
            
            
        }

        // ======================================
        // Invalid Login
        // ======================================

        else {

            String errorMessage =
                    loginPage.getLoginErrorMessage();

            System.out.println(
                    "Username: " + username
                    + " | Expected: Invalid"
                    + " | Actual: Login Failed");

            Assert.assertTrue(
                    errorMessage.contains(
                            "Username and password do not match")
                    || errorMessage.contains(
                            "Username is required")
                    || errorMessage.contains(
                            "Password is required")
            );
        }
    }

}