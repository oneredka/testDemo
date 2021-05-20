package com.hong.mobilecity;


import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 *
 * 请求第三方接口，查询手机号归属地和运营商
 * @author HongYi@10004580
 * @createTime 2021年04月26日 19:44:00
 */
public class NewMobile {

    public static void main(String[] args) {

        System.out.println(NewMobile.getCarrier("18576710402"));
        System.out.println(NewMobile.getCity("18576710402"));
        System.out.println(NewMobile.getResult("18576710402"));
    }

    //得到归属地
    public static String getCity(String tel) {
        try{
            //获取返回结果
            String json = httpRequest(tel).toString();
            //拆分xml页面代码
            String[] a = json.split("att");
            String[] b = a[1].split(",");
            //归属地
            return b[2].replace(">", "").replace("</", "");
        }catch(Exception e){
            return "暂无相关归属地信息！";
        }
    }

    //得到运营商
    public static String getCarrier(String tel) {
        try{
            //获取返回结果
            String json = httpRequest(tel).toString();
            //拆分xml页面代码
            String[] a = json.split("att");
            String[] c = a[2].split("operators");
            //运营商
            return c[1].replace(">", "").replace("</", "");
        }catch(Exception e){
            return "暂无相关运营商信息！";
        }
    }

    //得归属地，运营商。如：西双版纳,中国电信
    public static String getResult(String tel) {
        try{
            //获取返回结果
            String json = httpRequest(tel).toString();
            //拆分xml页面代码
            String[] a = json.split("att");
            String[] b = a[1].split(",");
            //归属地
            String city = b[2].replace(">", "").replace("</", "");
            String[] c = a[2].split("operators");
            //运营商
            String carrier = c[1].replace(">", "").replace("</", "");
            return  city+","+carrier;
        }catch(Exception e){
            return "暂无相关归属地、运营商信息！";
        }
    }

    /**
     * 发起http请求获取返回结果
     * @param tel 待查询手机号
     * @return String 结果字符串
     */
    public static String httpRequest(String tel) {

        //组装查询地址(requestUrl 请求地址)
        String requestUrl = "http://api.k780.com:88/?app=phone.get&phone="+tel+"&appkey=10003&sign=b59bc3ef6191eb9f747dd4e83c99f2a4&format=xml";

        StringBuffer buffer = new StringBuffer();
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection httpUrlConn = (HttpURLConnection) url.openConnection();
            httpUrlConn.setDoOutput(false);
            httpUrlConn.setDoInput(true);
            httpUrlConn.setUseCaches(false);
            httpUrlConn.setRequestMethod("GET");
            httpUrlConn.connect();
            //将返回的输入流转换成字符串
            InputStream inputStream = httpUrlConn.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

            String str = null;
            while ((str = bufferedReader.readLine()) != null) {
                buffer.append(str);
            }
            bufferedReader.close();
            inputStreamReader.close();
            //释放资源
            inputStream.close();
            inputStream = null;
            httpUrlConn.disconnect();
        }
        catch (Exception e) {
            return "发起http请求后，获取返回结果失败！";
        }
        return buffer.toString();
    }
}