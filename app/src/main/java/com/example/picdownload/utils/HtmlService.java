package com.example.picdownload.utils;

import com.example.picdownload.entity.ImageEntity;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xlzhen on 1/24 0024.
 * 网页下载
 */
public class HtmlService {

    public static String getHtml(String path) throws Exception {
        // 通过网络地址创建URL对象
        URL url = new URL(path);
        // 根据URL
        // 打开连接，URL.openConnection函数会根据URL的类型，返回不同的URLConnection子类的对象，这里URL是一个http，因此实际返回的是HttpURLConnection
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        // 设定URL的请求类别，有POST、GET 两类
        conn.setRequestMethod("GET");
        //设置从主机读取数据超时（单位：毫秒）
        conn.setConnectTimeout(50000);
        //设置连接主机超时（单位：毫秒）
        conn.setReadTimeout(50000);
        // 通过打开的连接读取的输入流,获取html数据
        InputStream inStream = conn.getInputStream();
        // 得到html的二进制数据
        byte[] data = readInputStream(inStream);
        // 是用指定的字符集解码指定的字节数组构造一个新的字符串
        String html = new String(data, "utf-8");
        return html;
    }

    /**
     * 读取输入流，得到html的二进制数据
     *
     * @param inStream
     * @return
     * @throws Exception
     */
    public static byte[] readInputStream(InputStream inStream) throws Exception {
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        byte[] buffer = new byte[1024];
        int len = 0;
        while ((len = inStream.read(buffer)) != -1) {
            outStream.write(buffer, 0, len);
        }
        inStream.close();
        return outStream.toByteArray();
    }

    /**
     * 从HTML源码中提取图片路径，最后以一个 String 类型的 List 返回，如果不包含任何图片，则返回一个 size=0　的List
     * 需要注意的是，此方法只会提取以下格式的图片：.jpg|.bmp|.eps|.gif|.mif|.miff|.png|.tif|.tiff|.svg|.wmf|.jpe|.jpeg|.dib|.ico|.tga|.cut|.pic
     * @param htmlCode HTML源码
     * @return <img>标签 src 属性指向的图片地址的List集合
     * @author Carl He
     */
    public static List<ImageEntity> getImageSrc(String path,String htmlCode) {
        List<ImageEntity> imageSrcList = new ArrayList<>();
        Pattern p = Pattern.compile("<img.*src=(.*?)[^>]*?>",Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(htmlCode);

        while (m.find()) {
            imageSrcList.add(new ImageEntity(m.group(),false));
        }
        imageSrcList=getImageSrc(path,imageSrcList);
        return imageSrcList;
    }

    /***
     * 获取ImageSrc地址
     *
     * @param listImageUrl
     * @return
     */
    private static List<ImageEntity> getImageSrc(String url,List<ImageEntity> listImageUrl) {
        List<ImageEntity> listImgSrc = new ArrayList<>();
        for (int i=0;i<listImageUrl.size();i++) {
            if(!listImageUrl.get(i).getImageUrl().contains("http"))
                listImageUrl.get(i).setImageUrl(listImageUrl.get(i).getImageUrl().replace("src=\"","src=\""+url));
            Matcher matcher = Pattern.compile("http:\\\"?(.*?)(\\\"|>|\\\\s+)").matcher(listImageUrl.get(i).getImageUrl());
            while (matcher.find()) {
                listImgSrc.add(new ImageEntity(matcher.group().substring(0, matcher.group().length() - 1),false));
            }
        }
        return listImgSrc;
    }

}
