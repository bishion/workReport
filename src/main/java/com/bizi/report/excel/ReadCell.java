package com.bizi.report.excel;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;

/**
 * Created by guofangbi on 2016/11/24.
 */
public class ReadCell {
    public static String getCellValue(Cell cell) {
        CellType cellType = cell.getCellTypeEnum();
        switch (cellType) {
            case NUMERIC:
                return String.valueOf(cell.getNumericCellValue());
            default: STRING:
                return cell.getStringCellValue();
        }
    }
}
