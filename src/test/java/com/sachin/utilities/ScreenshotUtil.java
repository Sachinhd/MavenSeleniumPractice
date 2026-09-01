package com.sachin.utilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.StandardCopyOption;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

public class ScreenshotUtil {

    public static String takeScreenshot(
            WebDriver driver,
            String testName) {

        String screenshotPath =
                System.getProperty("user.dir")
                + "/test-output/screenshots/"
                + testName + ".png";

        File source =
                ((TakesScreenshot) driver)
                .getScreenshotAs(OutputType.FILE);

        File destination =
                new File(screenshotPath);

        try {

            destination.getParentFile().mkdirs();

            Files.copy(
                    source.toPath(),
                    destination.toPath(),
                    StandardCopyOption.REPLACE_EXISTING);

            System.out.println(
                    "Screenshot saved: "
                    + screenshotPath);

        } catch (IOException e) {

            e.printStackTrace();
        }

        return screenshotPath;
    }
}