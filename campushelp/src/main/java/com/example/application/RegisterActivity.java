package com.example.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class RegisterActivity extends AppCompatActivity {
    //返回登录页
    private Button loGin;
    //判断输入情况
    private EditText inputPhone;
    private EditText inputCode;
    private TextView verCode;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_register);
        //返回登录页
        loGin = (Button) findViewById(R.id.login);
        loGin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(in);
            }
        });
        //判断输入情况
        inputPhone = (EditText) findViewById(R.id.input_phone);
        inputCode = (EditText) findViewById(R.id.input_code);
        verCode= (TextView) findViewById(R.id.ver_code);

    }
}
