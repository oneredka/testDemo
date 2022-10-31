package com.icarus.demo.excel2Pdf.aspose;


import com.aspose.cells.SaveFormat;
import com.aspose.cells.Workbook;

import java.io.File;

public class Aspose {

    public static void main(String[] args) {
        try {
            convert2Pdf();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void convert2Pdf() throws Exception {
        String dir = "C://hong//output//";
        File file = new File(dir + "test2.xlsx");

        Workbook wb = new Workbook(file.getPath());
        wb.save(dir + "output.pdf", SaveFormat.PDF);
        wb.save(dir + "output.xps", SaveFormat.XPS);
        wb.save(dir + "output.html", SaveFormat.HTML);
        //targetFile.delete();
    }


}
