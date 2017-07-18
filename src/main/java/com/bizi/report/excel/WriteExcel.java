package com.bizi.report.excel;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import java.io.*;
import java.util.List;


@Slf4j
public class WriteExcel {

    public static void writeToStream(List<?> list, String[] attrList, String[] head, OutputStream outputStream) {
        Workbook workbook = getWorkbook(list,attrList,head);
        try {
            workbook.write(outputStream);
        } catch (IOException e) {
            log.error("excel写入失败",e);
        }finally {
            closeStream(workbook,outputStream);
        }
    }
    public static void closeStream(Workbook workbook,OutputStream outputStream){
        if (workbook!=null){
            try {
                workbook.close();
            } catch (IOException e) {
                log.error("流关闭失败",e);
            }
        }
        if (outputStream!=null){
            try {

                outputStream.close();
            }catch (Exception e){
                log.error("流关闭失败",e);
            }
        }
    }
    public static Workbook getWorkbook(List<?> list,String[] attrList,String[] head){
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet();
        int rowNumber = 0;
        if (head != null) {
            HSSFRow row = sheet.createRow(rowNumber);
            for (int i = 0; i < head.length; i++) {
                row.createCell(i).setCellValue(head[i]);
            }
            rowNumber++;
        }
        for (Object object : list) {
            Row row = sheet.createRow(rowNumber);
            for (int j = 0; j < attrList.length; j++) {
                try {
                    row.createCell(j).setCellValue(BeanUtils.getSimpleProperty(object, attrList[j]));
                } catch (Exception e) {
                    log.error("获取属性值失败.类:{},属性:{}", object.getClass().getName(), attrList[j], e);
                }
            }
            rowNumber++;
        }
        return workbook;
    }
    public static InputStream writeToInputStream(List<?> list,String[] attrList,String[] head){
        Workbook workbook = getWorkbook(list,attrList,head);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        try {
            workbook.write(outputStream);
          outputStream.flush();
           byte[] aa = outputStream.toByteArray();
           return new ByteArrayInputStream(aa, 0, aa.length);
        } catch (IOException e) {
            log.error("excel写入InputStream失败",e);
        }finally {
            closeStream(workbook,outputStream);
        }
        return null;
    }
    /**
     * 将数据写入excel
     * @param list 要传入的数据对象list
     * @param attrList 要读取的对象属性列表
     * @param head 标题头（不需要标题则置null）
     * @param filePath 要写入的文件路径
     */
    public static void writeToFile(List<?> list,String[] attrList,String[] head,String filePath){
        try {
            FileOutputStream fop = new FileOutputStream(filePath);
            writeToStream(list,attrList,head,fop);
        } catch (Exception e) {
            log.error("写入文件失败.",e);
        }
    }
}
