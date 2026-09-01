package com.sachin.utilities;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.sachin.base.BaseClass;

public class TestListener implements ITestListener {

    private static ExtentReports extent =
            ExtentReportManager.getReportInstance();

    private static ThreadLocal<ExtentTest> test =
            new ThreadLocal<>();

    @Override
    public void onTestStart(ITestResult result) {

        ExtentTest extentTest =
                extent.createTest(result.getMethod().getMethodName());

        test.set(extentTest);

        test.get().info("Test Started");
    }

    @Override
    public void onTestSuccess(ITestResult result) {

        test.get().pass("Test Passed");

    }

    @Override
    public void onTestFailure(ITestResult result) {

        test.get().fail("Test Failed");

        test.get().fail(result.getThrowable());

        // Capture and attach screenshot on failure
        try {

            Object currentTestInstance = result.getInstance();
            WebDriver driver = ((BaseClass) currentTestInstance).getDriver();

            if (driver != null) {

                String screenshotPath = captureScreenshot(
                        driver, result.getMethod().getMethodName());

                test.get().addScreenCaptureFromPath(screenshotPath);
            }

        } catch (Exception e) {
            test.get().info("Screenshot could not be captured: " + e.getMessage());
        }

    }

    private String captureScreenshot(WebDriver driver, String testName) throws IOException {

        TakesScreenshot ts = (TakesScreenshot) driver;
        File source = ts.getScreenshotAs(OutputType.FILE);

        String screenshotDir =
                System.getProperty("user.dir") + "/test-output/screenshots/";

        Files.createDirectories(Paths.get(screenshotDir));

        String destPath = screenshotDir + testName + "_" + System.currentTimeMillis() + ".png";

        File destination = new File(destPath);

        FileUtils.copyFile(source, destination);

        return destPath;
    }

    @Override
    public void onTestSkipped(ITestResult result) {

        test.get().skip("Test Skipped");

    }

    @Override
    public void onStart(ITestContext context) {

        System.out.println("Test Execution Started");

    }

    @Override
    public void onFinish(ITestContext context) {

        extent.flush();

        System.out.println("Test Execution Finished");

    }
}