package com.example.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class StartActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        Thread thread = new Thread(){

            public  void run(){
                try {
                    sleep(2500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                Intent in = new Intent(StartActivity.this,MainActivity.class);
                startActivity(in);
                //跳转后关闭当前页
                finish();
            }
        } ;
        thread.start();
    }

}
