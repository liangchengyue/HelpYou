package com.example.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.util.Util;

import java.util.ArrayList;
import java.util.List;

public class SetActivity extends AppCompatActivity {
    private TextView editorData;
    private TextView psW;
    private TextView intro;
    private TextView oPi;
    private Button mA;
    private Button outBtn;
    //完成退出功能的组件

    private int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.allActiveActivities.add(this);
        setContentView(R.layout.personal_set);
        //跳转到编辑资料的点击事件
        editorData = (TextView) findViewById(R.id.editor_data);
        editorData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edi=null;
                if (Util.userinfo.get("realName").equals("null")){
                    edi = new Intent(SetActivity.this,EditorActivity.class);
                }else {
                    edi=new Intent(SetActivity.this,IdActivity.class);
                }

                startActivity(edi);
            }
        });
        //跳转到修改密码的点击事件
        psW = (TextView) findViewById(R.id.psw);
        psW.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Intent ps =new Intent(SetActivity.this,PasswordActivity.class);
                startActivity(ps);
            }
        });
        //跳转到关于我们
        intro =(TextView) findViewById(R.id.intro);
        intro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //准备跳转测试了
                Intent intent = new Intent(SetActivity.this,SecondActivity.class);
                startActivity(intent);
            }
        });
        oPi = (TextView) findViewById(R.id.opi);
        oPi.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                Intent op =new Intent(SetActivity.this,OpinionActivity.class);
                startActivity(op);
            }
        });
        mA = (Button) findViewById(R.id.index_btn);
        mA.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent in = new Intent(SetActivity.this, MainActivity.class);
                startActivity(in);
            }
        });
        //完成退出功能
        outBtn = (Button) findViewById(R.id.out_btn);
        outBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               for(AppCompatActivity a:Util.allActiveActivities)
                a.finish();
            }
        });
    }
    protected void onDestroy(){
        Util.allActiveActivities.remove(this);
        super.onDestroy();
    }
}
