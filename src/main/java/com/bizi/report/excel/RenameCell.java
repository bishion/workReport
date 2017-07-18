package com.bizi.report.excel;

import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.util.CellAddress;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.Iterator;

/**
 * Created by guofangbi on 2017/1/24.
 */
public class RenameCell {
    public static void renameCell(String filePath,RenameCellRule rule,int sheetNo){
        try {
            HSSFWorkbook workbook = new HSSFWorkbook(new FileInputStream(filePath));

            HSSFSheet sheet = workbook.getSheetAt(sheetNo);
            for (Iterator<Row> rowIterator = sheet.rowIterator(); rowIterator.hasNext();){
                HSSFRow row = (HSSFRow)rowIterator.next();
                if(row == null){continue;}
                for (Iterator<Cell> cellIterator= row.cellIterator();cellIterator.hasNext();){
                    HSSFCell cell = (HSSFCell)cellIterator.next();
                    if (cell==null){continue;}

                    HSSFName name = workbook.createName();
                    name.setNameName(rule.getNewName(cell));
                    name.setRefersToFormula(getCellRefer(cell));

                }
            }

            workbook.write(new FileOutputStream(filePath));
            workbook.close();
        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private static String getCellRefer(HSSFCell cell){
        CellAddress address = cell.getAddress();
        String sheetName = cell.getSheet().getSheetName();
        String colName = CellReference.convertNumToColString(address.getColumn());
        int    rowName = address.getRow()+1;
        return sheetName+"!$"+colName+"$"+rowName;
    }
}
