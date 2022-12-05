package com.ruoyi.system.utils;

import com.deepoove.poi.XWPFTemplate;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;

public class TestOfficeUtils {

    static final String docUrl="D:\\A-kingpoint\\改造20221201\\";

    public static void main(String[] args) throws Exception {
        //创建数据
        Map<String,String> data = new HashMap<>();
        data.put("name","刘凯");
        data.put("age","25");
        //获取模板
//        File file= new File("D:\\A-kingpoint\\改造20221201\\test.xlsx");
//        InputStream stream = new FileInputStream(file);
//        XSSFWorkbook xwb = new XSSFWorkbook(stream);
        //修改的模板数据在sheet0里面
        //OfficeUtil.changeExcelSheet(xwb, data, 0);
//        String documentname ="template2.xlsx";
//        String documentname ="template22.docx";
      /*  XWPFTemplate template = XWPFTemplate.compile("D:\\A-kingpoint\\改造20221201\\"+documentname).render(
                data);*/
//        template.write(new FileOutputStream("D:\\kingpoint\\output.docx"));
//        template.close();
//        template.writeAndClose(new FileOutputStream("D:\\A-kingpoint\\output.docx"));
        //输出到新位置
//        File fileNew = new File("F:/test_new.xlsx");
//        FileOutputStream streamOut = new FileOutputStream(fileNew);
//        xwb.write(streamOut);
//        stream.close();
        //创建数据
        //获取模板
        File file= new File(docUrl+"/test.docx");
        InputStream stream = new FileInputStream(file);
        XWPFDocument xwb = new XWPFDocument(stream);
        //修改word中文本
        OfficeUtils.changeText(xwb, data);
        //修改word中表格
        OfficeUtils.changeTables(xwb, data);
        //输出到新位置
        File fileNew = new File("F:/test_new.docx");
        FileOutputStream streamOut = new FileOutputStream(fileNew);
        xwb.write(streamOut);
        stream.close();
    }
}