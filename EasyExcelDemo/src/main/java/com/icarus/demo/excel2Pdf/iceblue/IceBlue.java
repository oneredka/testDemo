package com.icarus.demo.excel2Pdf.iceblue;

import com.spire.xls.FileFormat;
import com.spire.xls.Workbook;
import com.spire.xls.Worksheet;

public class IceBlue {


    public static void main(String[] args) {
        convert2Pdf1();
        convert2Pdf2();
        convert2Html();
    }

    public static void convert2Pdf1(){
        //创建一个Workbook实例并加载Excel文件
        Workbook workbook = new Workbook();
        workbook.loadFromFile("C://hong//output//test2.xlsx");

        //设置转换后的PDF页面高宽适应工作表的内容大小
        workbook.getConverterSetting().setSheetFitToPage(true);

        //将生成的文档保存到指定路径
        workbook.saveToFile("C://hong//output//WorksheetToPdf1.pdf", FileFormat.PDF);

    }

    public static void convert2Pdf2() {
        //创建一个Workbook实例并加载Excel文件
        Workbook workbook = new Workbook();
        workbook.loadFromFile("C://hong//output//test2.xlsx");

        //设置转换后PDF的页面宽度适应工作表的内容宽度
        workbook.getConverterSetting().setSheetFitToWidth(true);

        //获取第一个工作表
        Worksheet worksheet = workbook.getWorksheets().get(0);

        //转换为PDF并将生成的文档保存到指定路径
        worksheet.saveToPdf("C://hong//output//WorksheetToPdf2.pdf");
    }

    public static void convert2Html() {
        //创建一个Workbook实例并加载Excel文件
        Workbook workbook = new Workbook();
        workbook.loadFromFile("C://hong//output//test2.xlsx");

        //设置转换后PDF的页面宽度适应工作表的内容宽度
        workbook.getConverterSetting().setSheetFitToWidth(true);

        //获取第一个工作表
        Worksheet worksheet = workbook.getWorksheets().get(0);

        //转换为PDF并将生成的文档保存到指定路径
        worksheet.saveToHtml("C://hong//output//WorksheetToHtml.html");
    }

}
