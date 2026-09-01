package com.sachin.utilities;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReader {

    public static Object[][] getExcelData(String filePath, String sheetName)
            throws IOException {

        // Open Excel file
        FileInputStream file = new FileInputStream(filePath);

        // Create workbook
        Workbook workbook = new XSSFWorkbook(file);

        // Get sheet
        Sheet sheet = workbook.getSheet(sheetName);

        // Number of rows
        int rowCount = sheet.getPhysicalNumberOfRows();

        // Number of columns
        int columnCount = sheet.getRow(0).getPhysicalNumberOfCells();

        // DataFormatter
        DataFormatter formatter = new DataFormatter();

        // Create Object array
        Object[][] data = new Object[rowCount - 1][columnCount];

        // Read Excel data
        for (int i = 1; i < rowCount; i++) {

            Row row = sheet.getRow(i);

            for (int j = 0; j < columnCount; j++) {

                data[i - 1][j] =
                        formatter.formatCellValue(row.getCell(j));
            }
        }

        // Close resources
        workbook.close();
        file.close();

        return data;
    }
}