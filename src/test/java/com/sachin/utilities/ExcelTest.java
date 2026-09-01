package com.sachin.utilities;

public class ExcelTest {

    public static void main(String[] args) throws Exception {

        String filePath =
                "src/test/resources/testdata/TestData.xlsx";

        Object[][] data =
                ExcelReader.getExcelData(
                        filePath,
                        "LoginData");

        for (int i = 0; i < data.length; i++) {

            System.out.println(
                    data[i][0] + " | "
                    + data[i][1] + " | "
                    + data[i][2]);
        }
    }
}