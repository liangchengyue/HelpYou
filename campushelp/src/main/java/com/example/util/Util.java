package com.example.util;

import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by 梁城月 on 2017/5/20.
 */

public class Util {
    public static String ip="http://192.168.121.1:8080/Express_delivery/";
    public static List<AppCompatActivity> allActiveActivities = new ArrayList<AppCompatActivity>();
    public static String userId;
    public static List<Map<String,Object>> orders=new ArrayList<Map<String, Object>>();

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
}
