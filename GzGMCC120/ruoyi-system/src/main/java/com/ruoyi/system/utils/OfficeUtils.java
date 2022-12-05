package com.ruoyi.system.utils;


import org.apache.commons.io.FileUtils;
import org.apache.poi.ooxml.POIXMLDocument;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.*;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Office操作工具
 * @LewKAY
 * 20180410
 */
public class OfficeUtils {

    /**
     * 修改Doc中文本域内对象
     * 处理对象${name}类型
     */
    public static void changeText(XWPFDocument document, Map<String, String> map) {

        List<XWPFParagraph> paragraphs = document.getParagraphs();
        for (XWPFParagraph paragraph : paragraphs) {
            //判断此段落时候需要进行替换
            String text = paragraph.getText();
            if (checkText(text)) {
                List<XWPFRun> runs = paragraph.getRuns();
                for (XWPFRun run : runs) {
                    run.setText(changeValue(run.toString(), map), 0);
                }
            }
        }

    }

    /**
     * 修改Doc中Tables text对象
     * 处理对象${name}类型
     *
     * @param document
     */
    public static void changeTables(XWPFDocument document, Map map) {
        List<XWPFTable> tables = document.getTables();
        for (int i = 0; i < tables.size(); i++) {
            XWPFTable table = tables.get(i);
            if (table.getRows().size() >= 1) {
                if (checkText(table.getText())) {
                    List<XWPFTableRow> rows = table.getRows();
                    eachTable(rows, map);
                }
            }
        }
    }

    /**
     * 遍历表格
     */
    public static void eachTable(List<XWPFTableRow> rows, Map<String, String> textMap) {
        for (XWPFTableRow row : rows) {
            List<XWPFTableCell> cells = row.getTableCells();
            for (XWPFTableCell cell : cells) {
                //判断单元格是否需要替换
                if (checkText(cell.getText())) {
                    List<XWPFParagraph> paragraphs = cell.getParagraphs();
                    for (XWPFParagraph paragraph : paragraphs) {
                        List<XWPFRun> runs = paragraph.getRuns();
                        for (XWPFRun run : runs) {
                            run.setText(changeValue(run.toString(), textMap), 0);
                        }
                    }
                }
            }
        }
    }

    /**
     * 匹配传入信息集合与模板
     *
     * @param value 模板需要替换的区域
     */
    public static String changeValue(String value, Map<String, String> textMap) {
        value.trim();
        Set<Map.Entry<String, String>> textSets = textMap.entrySet();
        for (Map.Entry<String, String> textSet : textSets) {
            //匹配模板与替换值 格式${key}
            String key = "${" + textSet.getKey() + "}";
            if (value.indexOf(key) != -1) {
                value = textSet.getValue();
            }
        }
        //模板未匹配到区域替换为空
        if (checkText(value)) {
            value = "";
        }
        return value;
    }

    /**
     * 判断文本中时候包含$
     */
    public static boolean checkText(String text) {
        boolean check = false;
        if (text.indexOf("$") != -1) {
            check = true;
        }
        return check;

    }


    public static void createOfficeFile(POIXMLDocument source, String dirs, String fileName) throws IOException {

        File file = new File(dirs + fileName);
        FileUtils.createParentDirectories(file);
        FileOutputStream stream = new FileOutputStream(file);
        source.write(stream);
        stream.close();
    }

    /**
     * 修改Excell 值
     */
    public static void changeExcelSheet(XSSFWorkbook xwb, Map<String, String> data, int sheet) {
        XSSFSheet xSheet = xwb.getSheetAt(sheet);
        int begin = xSheet.getFirstRowNum();
        int end = xSheet.getLastRowNum();
        for (int i = begin; i <= end; i++) {
            if (null == xSheet.getRow(i)) {
                continue;
            }
            int beginCell = xSheet.getRow(i).getFirstCellNum();
            int endCell = xSheet.getRow(i).getLastCellNum();
            for (int j = beginCell; j <= endCell; j++) {
                if (xSheet.getRow(i).getCell(j) == null) {
                    continue;
                }
                String value =changeValue(xSheet.getRow(i).getCell(j).getStringCellValue(), data);
                if(!value.equals("")){
                    xSheet.getRow(i).getCell(j).setCellValue(value);
                }
            }
        }
    }
}