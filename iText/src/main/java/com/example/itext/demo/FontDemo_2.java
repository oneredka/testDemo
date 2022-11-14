package com.example.itext.demo;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import com.itextpdf.tool.xml.XMLWorkerFontProvider;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;

/**
 * 字体
 */
public class FontDemo_2 {

    public static void main(String[] args) {
        try {
            create();
            System.out.println("生成成功 demo2");
        } catch (Exception ex) {
            System.out.println("文件路径错误或者权限不够");
        }
    }

    /**
     * 这说明 iText 内置字体不支持中文
     * 只有这个是有效的 getFontFromMyService()
     * 从 windows 获取字体，属性->安全--对象名称  即为字体
     * 参考 PdfDemo_1 中获取 windows 字体
     *
     * @throws Exception
     */
    public static void create() throws Exception {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("C:\\hong\\output\\iText\\demo2.pdf"));
        document.open();

        String title = "凉州词";
        String content1 = "黄河远上白云间，";
        String content2 = "一片孤城万仞山。";
        String content3 = "羌笛何须怨杨柳，";
        String content4 = "春风不度玉门关。";
        // 华文行楷
        document.add(new Paragraph(title, getFont("STXINGKA.TTF")));
        document.add(new Paragraph(content1, getWindowsFont_1()));
        document.add(new Paragraph(content2, getWindowsFont_2()));
        document.add(new Paragraph(content3, getFontFromMyService()));
        document.add(new Paragraph(content4, getFontFromItext()));

/*        String title = "Liangzhou word";
        String content1 = "The Yellow River far above the white clouds,";
        String content2 = "A lonely city Wanren Mountain.";
        String content3 = "Why should the Qiang flute complain about the willows?";
        String content4 = "The spring breeze does not pass through Yumen Pass.";


        document.add(new Paragraph(title));
        document.add(new Paragraph(content1));
        document.add(new Paragraph(content2));
        document.add(new Paragraph(content3));
        document.add(new Paragraph(content4));

*/
        document.close();
        writer.close();
    }

    private static Font getFont(String fontName) {
        // xmlworker主要功能是html转pdf用的，非常好用，也是itext官方的
        // 这个是xmlworker提供的获取字体方法，很方便，对中文支持很好
        FontFactoryImp fp = new XMLWorkerFontProvider();
        // 注册指定的字体目录，默认构造方法中会注册全部目录，我这里注册了src/font目录
        URL fontURL = FontDemo_2.class.getClassLoader().getResource("file/pdf/font/weiruanyahei");
        assert fontURL != null;
        fp.registerDirectory(Objects.requireNonNull(fontURL).getFile(), true);

        // 最好的地方是直接支持获取中文的名字
        return fp.getFont(fontName);

        // 当然，最好的方法是自己继承XMLWorkerFontProvider，提供一些常用字体，简单方便
    }

    /**
     * 使用windows系统下的字体,new Font方式
     */
    public static Font getWindowsFont_1() throws DocumentException, IOException {
        //windows里的字体资源路径 --------- 黑体 常规 简体
        String path = "C:\\Windows\\Fonts\\simhei.ttf";
        BaseFont bf = BaseFont.createFont(path, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED);
        return new Font(bf, 10f, Font.NORMAL, BaseColor.BLACK);
    }

    /**
     * 使用windows系统下的字体,FontFactory方式
     */
    public static Font getWindowsFont_2() throws DocumentException, IOException {
        //windows里的字体资源路径 --------- 华文琥珀 常规
        String path = "C:\\Windows\\Fonts\\STHUPO.TTF";
        return FontFactory.getFont(path, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED, 10f, Font.NORMAL, BaseColor.BLACK);
    }

    /**
     * 使用自己查找的字体,FontFactory方式
     */
    public static Font getFontFromMyService() throws DocumentException, IOException {
        String path = "src/main/resources/file/pdf/font/chinese.simyou.ttf";//自己的字体资源路径
        return FontFactory.getFont(path, BaseFont.IDENTITY_H, BaseFont.NOT_EMBEDDED, 10f, Font.NORMAL, BaseColor.BLACK);
    }


    /**
     * 使用iTextAsian.jar中的字体，FontFactory方式
     */
    public static Font getFontFromItext() throws DocumentException, IOException {
        return FontFactory.getFont("STSong-Light", "UniGB-UCS2-H", BaseFont.NOT_EMBEDDED, 10f, Font.NORMAL, BaseColor.BLACK);
    }

}
