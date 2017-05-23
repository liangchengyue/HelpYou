package com.example.util;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 梁城月 on 2017/5/20.
 */

public class Util {
    public static String ip="http://192.168.1.98:8080/Express_delivery/";
    public static List<AppCompatActivity> allActiveActivities = new ArrayList<AppCompatActivity>();
    public static String userId;
    public static List<Map<String,Object>> orders=new ArrayList<Map<String, Object>>();
    public static Map<String,Object> userinfo=new HashMap<>();

    /**
     * 验证电话号码
     * @param text 电话号码
     * @return 验证结果
     */
    public static String validatephone(String text) {
        String msg = "";
        String regExp = "^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$";
        Pattern p = Pattern.compile(regExp);
        Matcher m = p.matcher(text);
        if (text.equals("")) {
            msg = "电话号码不能为空！";
        }else if (!m.matches()) {
            msg="电话号码格式不正确！";
        }
        return msg;
    }
    public static Bitmap getBitmap(String path) throws IOException {

        URL url = new URL(path);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setConnectTimeout(5000);
        conn.setRequestMethod("GET");
        if (conn.getResponseCode() == 200){
            InputStream inputStream = conn.getInputStream();
            Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
            return bitmap;
        }
        return null;
    }
}
