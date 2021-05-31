package com.hong.biz.user.base;

import com.sun.org.apache.xml.internal.security.utils.Base64;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.UUID;

/**
 * @Author : YiHong
 * @Description: AES加密解密
 *                模式：CBC   补码方式：Pkcs5padding   编码方式：base64
 * @Date: Created in 14:30  2019/1/18.
 * @Modified By:
 */
public class AESBase64 {

    static String e = "1111222233334444";

    // 加密
    public static String Encrypt(String src, String key) throws Exception {
        if (key == null) {
            System.out.print("Key为空null");
            return null;
        }
        // 判断Key是否为16位
        if (key.length() != 16) {
            System.out.print("Key长度不是16位");
            return null;
        }
        byte[] raw = key.getBytes();
        SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
        // "算法/模式/补码方式"0102030405060708
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
        // 使用CBC模式，需要一个向量iv，可增加加密算法的强度
        IvParameterSpec iv = new IvParameterSpec(e.getBytes());
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);

        byte[] encrypted = cipher.doFinal(src.getBytes());
        // 此处使用BASE64做转码功能，同时能起到2次加密的作用。
        return Base64.encode(encrypted);

    }

    // 解密
    public static String Decrypt(String src, String key) throws Exception {
        try {
            // 判断Key是否正确
            if (key == null) {
                System.out.print("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (key.length() != 16) {
                System.out.print("Key长度不是16位");
                return null;
            }
            byte[] raw = key.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec(e.getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            // 先用base64解密
            byte[] encrypted1 = Base64.decode(src);
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original);
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public static void main(String[] args) throws Exception {
        String key = UUID.randomUUID().toString().substring(0, 16);
        String src = "qwerasdf";
        System.out.println(src);
        // 加密
        long start = System.currentTimeMillis();
        String enString = AESBase64.Encrypt(src, key);
        System.out.println("加密后的字串是：" + enString);

        long useTime = System.currentTimeMillis() - start;
        System.out.println("加密耗时：" + useTime + "毫秒");

        // 解密
        start = System.currentTimeMillis();
        String DeString = AESBase64.Decrypt(enString, key);
        System.out.println("解密后的字串是：" + DeString);
        useTime = System.currentTimeMillis() - start;
        System.out.println("解密耗时：" + useTime + "毫秒");
    }
}

