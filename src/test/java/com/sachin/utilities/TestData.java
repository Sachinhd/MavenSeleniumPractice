package com.sachin.utilities;

import java.io.IOException;

import org.testng.annotations.DataProvider;

public class TestData {

    @DataProvider(name = "loginData")
    public Object[][] loginData() throws IOException {

        String filePath =
                "src/test/resources/testdata/TestData.xlsx";

        return ExcelReader.getExcelData(
                filePath,
                "LoginData");
    }
}