package com.icarus.watermark.utils;


import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.*;

public class ImagemarkUtils2 {

    //水印颜色
    private static Color markContentColor = Color.lightGray;
    //水印字体，大小
    private static Font font = new Font("宋体", Font.BOLD, 18);
    //设置水印文字的旋转角度
    private static Integer degree = 45;
    //设置水印透明度
    private static float alpha = 0.2f;
    // 水印之间的间隔
    private static final int XMOVE = 80;
    // 水印之间的间隔
    private static final int YMOVE = 120;

    public static void main(String[] args) {
        addWatermarkByFile("C:\\hong\\output\\p1.jpg", "文字水印");
        addImageWatermarkByFile("C:\\hong\\output\\1667207896852.jpg","C:\\hong\\output\\1667208039981.jpg");
    }

    /**
     * 批量处理图片添加文字水印-平铺
     * @param sourceImgPath 源图片路径 / 文件夹路径
     * @param waterMarkContent 水印内容
     */
    public static boolean addWatermarkByFile(String sourceImgPath, String waterMarkContent) {
        //获取路径
        File sourceImgFile = new File(sourceImgPath);
        if (!sourceImgFile.exists()) {
            System.out.println("文件路径错误：" + sourceImgFile.getPath());
            return false;
        } else {
            if (sourceImgFile.isFile()){
                addWatermark(sourceImgFile.getPath(), sourceImgFile.getPath(), waterMarkContent);
            }else {
                List<String> proList=getTotalSizeOfFilesInDir(sourceImgFile);
                for (String s : proList) {
                    File f = new File(s);
                    if (!"thumb.jpg".equals(f.getName()) && !"preview.jpg".equals(f.getName())){
                        boolean b=addWatermark(s, s, waterMarkContent);
                        System.out.println(b+"\t"+s);
                    }
                }
            }
        }
        return true;
    }

    /**
     * 批量处理图片添加图片水印-平铺
     * @param sourceImagePath
     * @param waterImagePath
     * @return
     */
    public static boolean addImageWatermarkByFile(String sourceImagePath, String waterImagePath) {
        //获取路径
        File sourceImgFile = new File(sourceImagePath);
        if (!sourceImgFile.exists()) {
            System.out.println("文件路径错误：" + sourceImgFile.getPath());
            return false;
        } else {
            if (sourceImgFile.isFile()){
                addImageWatermark(new File(sourceImagePath),new File(waterImagePath));
            }else {
                List<String> proList=getTotalSizeOfFilesInDir(sourceImgFile);
                for (String s : proList) {
                    File f = new File(s);
                    if (!"thumb.jpg".equals(f.getName()) && !"preview.jpg".equals(f.getName())){
                        boolean b=addImageWatermark(new File(s),new File(waterImagePath));
                        System.out.println(b+"\t"+s);
                    }
                }
            }
        }
        return true;
    }


    /**
     * 递归方式 查询全部文件
     * @param file
     * @return
     */
    private static List<String> getTotalSizeOfFilesInDir(final File file) {
        List<String> asList=new ArrayList<>();
        if (file.isFile()) {
            asList.add(file.getPath());
            return asList;
        }
        final File[] children = file.listFiles();
        if (children != null) {
            for (final File child : children){
                List<String> daList=getTotalSizeOfFilesInDir(child);
                for (String s : daList) {
                    asList.add(s);
                }
            }
        }
        return asList;
    }



    /**
     * 添加文字水印平铺
     * @description
     * @param sourceImgPath 源图片路径
     * @param tarImgPath 保存的图片路径
     * @param waterMarkContent 水印内容
     * @return void
     */
    public static boolean addWatermark(String sourceImgPath, String tarImgPath, String waterMarkContent){

        OutputStream outImgStream = null;
        try {
            File srcImgFile = new File(sourceImgPath);//得到文件
            Image srcImg = ImageIO.read(srcImgFile);//文件转化为图片
            int srcImgWidth = srcImg.getWidth(null);//获取图片的宽
            int srcImgHeight = srcImg.getHeight(null);//获取图片的高
            // 加水印
            BufferedImage bufImg = new BufferedImage(srcImgWidth, srcImgHeight, BufferedImage.TYPE_INT_RGB);
            Graphics2D g = bufImg.createGraphics();//得到画笔
            g.drawImage(srcImg, 0, 0, srcImgWidth, srcImgHeight, null);
            g.setColor(markContentColor); //设置水印颜色
            g.setFont(font);              //设置字体
            g.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP, alpha));//设置水印文字透明度
            if (null != degree) {
                g.rotate(Math.toRadians(degree));//设置水印旋转
            }
            JLabel label = new JLabel(waterMarkContent);
            FontMetrics metrics = label.getFontMetrics(font);
            int width = metrics.stringWidth(label.getText())+XMOVE;//文字水印的宽
            int rowsNumber = srcImgHeight/width;// 图片的高  除以  文字水印的宽    ——> 打印的行数(以文字水印的宽为间隔)
            int columnsNumber = srcImgWidth/width;//图片的宽 除以 文字水印的宽   ——> 每行打印的列数(以文字水印的宽为间隔)
            /*//防止图片太小而文字水印太长，所以至少打印一次
            if(rowsNumber < 1){
                rowsNumber = 1;
            }
            if(columnsNumber < 1){
                columnsNumber = 1;
            }*/
            for(int j=0;j<rowsNumber;j++){
                for(int i=0;i<columnsNumber;i++){
                    g.drawString(waterMarkContent, i*width + j*width, -i*width + j*width+YMOVE);//画出水印,并设置水印位置
                }
            }
            g.dispose();// 释放资源
            // 输出图片
            outImgStream = new FileOutputStream(tarImgPath);
            //获取到文件的后缀名
            String fileName = srcImgFile.getName();
            String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
            //覆盖原图
            ImageIO.write(bufImg, formatName, outImgStream);
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            e.getMessage();
            return false;
        } finally{
            try {
                if(outImgStream != null){
                    outImgStream.flush();
                    outImgStream.close();
                }
                return true;
            } catch (Exception e) {
                e.printStackTrace();
                e.getMessage();
                return false;
            }
        }
    }

    /**
     *  添加图片水印平铺
     * @param srcImageFile  目标图片
     * @param waterImageFile    水印图片
     * @return
     */
    public static boolean addImageWatermark(File srcImageFile, File waterImageFile) {
        try {

            Image srcImg = ImageIO.read(srcImageFile);//文件转化为图片
            int srcImgWidth = srcImg.getWidth(null);//获取图片的宽
            int srcImgHeight = srcImg.getHeight(null);//获取图片的高

            Image waterImage = ImageIO.read(waterImageFile);//文件转化为图片
            int waterImageWidth = waterImage.getWidth(null);//获取水印图片的宽

            //使用ImageIO的read方法读取图片
            BufferedImage read = ImageIO.read(srcImageFile);
            BufferedImage image = ImageIO.read(waterImageFile);
            //获取画布
            Graphics2D graphics = read.createGraphics();
            //设置透明度为0.5
            graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,alpha));

            if (waterImageWidth<100){
                waterImageWidth=200;
            }
            waterImageWidth+=XMOVE;
            int rowsNumber = srcImgHeight/waterImageWidth;// 图片的高  除以  文字水印的宽    ——> 打印的行数(以文字水印的宽为间隔)
            int columnsNumber = srcImgWidth/waterImageWidth;//图片的宽 除以 文字水印的宽   ——> 每行打印的列数(以文字水印的宽为间隔)
            /*//防止图片太小而文字水印太长，所以至少打印一次
            if(rowsNumber < 1){
                rowsNumber = 1;
            }
            if(columnsNumber < 1){
                columnsNumber = 1;
            }*/
            for(int j=0;j<rowsNumber;j++){
                for(int i=0;i<columnsNumber;i++){
                    int x=i*waterImageWidth + j*waterImageWidth;
                    int y=-i*waterImageWidth + j*waterImageWidth+YMOVE;
                    //添加水印
                    graphics.drawImage(image,x,y,null);
                }
            }
            //关闭透明度
            //graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER));
            graphics.dispose();
            //获取到文件的后缀名
            String fileName = srcImageFile.getName();
            String formatName = fileName.substring(fileName.lastIndexOf(".") + 1);
            //使用ImageIO的write方法进行输出-覆盖原图
            ImageIO.write(read,formatName,srcImageFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return true;
    }


}

