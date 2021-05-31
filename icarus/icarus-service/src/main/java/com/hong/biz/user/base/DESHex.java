package com.hong.biz.user.base;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;
import java.util.UUID;

/**
 * @Author : YiHong
 * @Description: DES加密解密
 *                模式：CBC   补码方式：Pkcs5padding   编码方式：hex(十六进制)
 * @Date: Created in 14:30  2019/1/18.
 * @Modified By:
 */
public class DESHex {
    //密钥 长度都必须是8的倍数
    private static final String PASSWORD_CRYPT_KEY = UUID.randomUUID()
            .toString().replace("-", "").toUpperCase().substring(0, 8);

    public static void main(String[] args) {
        String pwd = "123456";
        try {
            System.out.println("使用UUID生成的密钥加密");
            String afterEncrypt = encrypt(pwd);
            System.out.println("加密后： " + afterEncrypt);
            String afterDescrypt = decrypt(afterEncrypt, PASSWORD_CRYPT_KEY);
            System.out.println("解密后： " + afterDescrypt);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //解密
    public static String decrypt(String message, String key) throws Exception {
        byte[] bytesrc = convertHexString(message);
        //Cipher对象实际完成加密操作 这里选择EBC模式，Pkcs5padding填充
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        // 创建一个DESKeySpec对象
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
        //创建一个密匙工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        // 将DESKeySpec对象转换成SecretKey对象
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        // 使用CBC模式，需要一个向量iv，可增加加密算法的强度
        IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
        //用密匙初始化Cipher对象
        cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);
        // 真正开始解密操作
        byte[] retByte = cipher.doFinal(bytesrc);
        return new String(retByte);
    }

    //加密 -->bytes
    public static byte[] encrypt(String message, String key) throws Exception {
        //Cipher对象实际完成加密操作 这里选择EBC模式，Pkcs5padding填充
        Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
        // 创建一个DESKeySpec对象
        DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
        //创建一个密匙工厂
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        // 将DESKeySpec对象转换成SecretKey对象
        SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
        // 使用CBC模式，需要一个向量iv，可增加加密算法的强度
        IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
        // 用密匙初始化Cipher对象
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);
       //正式执行加密操作
        return cipher.doFinal(message.getBytes("UTF-8"));
    }

    //加密
    public static String encrypt(String value) {
        String result = "";
        try {
            value = java.net.URLEncoder.encode(value, "utf-8");
            result = toHexString(encrypt(value, PASSWORD_CRYPT_KEY))
                    .toUpperCase();
        } catch (Exception ex) {
            ex.printStackTrace();
            return "";
        }
        return result;
    }
    //使用16进制转码
    public static byte[] convertHexString(String ss) {
        byte[] digest = new byte[ss.length() / 2];
        for (int i = 0; i < digest.length; i++) {
            String byteString = ss.substring(2 * i, 2 * i + 2);
            int byteValue = Integer.parseInt(byteString, 16);
            digest[i] = (byte) byteValue;
        }
        return digest;
    }

    //使用16进制编码
    public static String toHexString(byte[] b) {
        StringBuilder hexString = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            String plainText = Integer.toHexString(0xff & b[i]);
            if (plainText.length() < 2) {
                plainText = "0" + plainText;
            }
            hexString.append(plainText);
        }
        return hexString.toString();
    }
}
