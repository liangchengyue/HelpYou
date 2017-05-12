package com.example.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

public class SetActivity extends AppCompatActivity {
    private TextView editorData;
    private TextView psW;
    private TextView intro;
    private TextView oPi;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.personal_set);
        //跳转到编辑资料的点击事件
        editorData = (TextView) findViewById(R.id.editor_data);
        editorData.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent edi = new Intent(SetActivity.this,EditorActivity.class);
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
    }
}
