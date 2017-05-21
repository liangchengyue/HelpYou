package com.example.util;

import android.support.v7.app.AppCompatActivity;

/**
 * Created by 90603 on 2017/5/21.
 */

public class Globals {
    public static int SCREEN_WIDTH;
    public static int SCREEN_HEIGHT;

    public static void init(AppCompatActivity a){
        //获取屏幕手机的宽和高
        SCREEN_WIDTH = a.getWindowManager().getDefaultDisplay().getWidth();
        SCREEN_HEIGHT =a.getWindowManager().getDefaultDisplay().getHeight();
    }
}
