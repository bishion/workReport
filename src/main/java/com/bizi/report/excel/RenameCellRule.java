package com.bizi.report.excel;

import org.apache.poi.hssf.usermodel.HSSFCell;

/**
 * Created by guofangbi on 2017/1/24.
 */
public interface RenameCellRule {
    String getNewName(HSSFCell cell);
}
