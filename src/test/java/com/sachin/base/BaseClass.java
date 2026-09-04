package com.sachin.base;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.sachin.utilities.ConfigReader;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseClass {

    protected WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {

        WebDriverManager.chromedriver().setup();

        // Load configuration test
        ConfigReader.loadProperties();

        // Read browser
        String browser = ConfigReader.getProperty("browser");

        // Launch browser
        if (browser.equalsIgnoreCase("chrome")) {

            driver = new ChromeDriver();

        } else {

            throw new RuntimeException(
                    "Browser not supported: " + browser);
        }

        // Maximize
        driver.manage().window().maximize();

        // Implicit wait
        driver.manage().timeouts().implicitlyWait(
                Duration.ofSeconds(
                        Integer.parseInt(
                                ConfigReader.getProperty("timeout")
                        )
                )
        );

        // Open application
        driver.get(ConfigReader.getProperty("url"));
    }

    public WebDriver getDriver() {
        return driver;
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown() {

        if (driver != null) {
            driver.quit();
        }
    }
}