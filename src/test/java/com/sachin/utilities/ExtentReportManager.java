package com.sachin.utilities;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportManager {

    private static ExtentReports extent;

    public static ExtentReports getReportInstance() {

        if (extent == null) {

            String reportPath =
                    System.getProperty("user.dir")
                    + "/test-output/ExtentReport.html";

            ExtentSparkReporter sparkReporter =
                    new ExtentSparkReporter(reportPath);

            sparkReporter.config().setReportName(
                    "Maven Selenium Practice Report");

            sparkReporter.config().setDocumentTitle(
                    "Automation Test Report");

            extent = new ExtentReports();

            extent.attachReporter(sparkReporter);

            extent.setSystemInfo(
                    "Tester", "Sachin");

            extent.setSystemInfo(
                    "Project", "Maven Selenium Practice");

            extent.setSystemInfo(
                    "Browser", "Chrome");

        }

        return extent;
    }
}