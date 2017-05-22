package com.example.application;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.WindowManager;

import com.example.util.Globals;
import com.example.util.Util;

public class AccordorderActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.allActiveActivities.add(this);
        //调用init方法，初始化，获取当前手机的宽和高
        Globals.init(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_accordorder);
    }
}
