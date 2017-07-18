package com.bizi.report.excel;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by guofangbi on 2016/11/24.
 */
@Slf4j
public class ReadExcel {
    public static List<List<String>> readExcel(String filePath,int sheetNo){
        Workbook wb = null;
        InputStream inputStream = null;
        try {
            inputStream = new FileInputStream(new File(filePath));
            if(filePath.endsWith("xls")){
                wb = new HSSFWorkbook(inputStream);
            }else{
                wb = new XSSFWorkbook(inputStream);
            }


            Sheet sheet = wb.getSheetAt(sheetNo);
            int rowNum = sheet.getLastRowNum();
            List<List<String>> sheetValue = new ArrayList<List<String>>(rowNum);
            for (int i = 0;i<rowNum;i++){
                Row row = sheet.getRow(i);
                int cellNum = row.getLastCellNum();
                List<String> rowValue = new ArrayList<String>();
                for (int j=0;j<cellNum;j++){
                    Cell cell = row.getCell(cellNum);
                    String value = ReadCell.getCellValue(cell);
                    rowValue.add(value);
                }
                sheetValue.add(rowValue);
            }

            return sheetValue;
        }catch (Exception e) {
            log.error("读取excel失败",e);
        }finally {
            if(inputStream!=null){
                try {
                    inputStream.close();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            if (wb != null){
                try {
                    wb.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return null;
    }
}
