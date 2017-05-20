package com.example.application;

import android.content.Intent;
import android.media.MediaCodec;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.util.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegisterActivity extends AppCompatActivity {
    //返回登录页
    private Button loGin;
    //判断输入情况
    private EditText inputPhone;
    private EditText inputCode;
    private TextView verCode;
    private Button regirst;
    private  String code;
    private Handler handler;

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
                Intent in = new Intent(RegisterActivity.this, LoginActivity.class);
                startActivity(in);
            }
        });
        //判断输入情况
        inputPhone = (EditText) findViewById(R.id.input_phone);
        inputCode = (EditText) findViewById(R.id.input_code);
        verCode = (TextView) findViewById(R.id.ver_code);
        regirst=(Button)findViewById(R.id.regist_r);
        verCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                String msg =validate(inputPhone.getText().toString().trim());
                if (!msg.equals("")){
                    Toast.makeText(RegisterActivity.this,msg, Toast.LENGTH_SHORT).show();
                    return;
                }
                Thread thread=new Thread(){
                    @Override
                    public void run() {
                        String url= Util.ip+"user/getAndroidIndustrySMS?phone="+inputPhone.getText().toString().trim();
                        try {
                            URL url1= new URL(url);
                            URLConnection urlConnection=url1.openConnection();
                            InputStream is=urlConnection.getInputStream();
                            BufferedReader bf=new BufferedReader(new InputStreamReader(is));
                            code=bf.readLine();
                            bf.close();
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (code==null){
                            handler.sendEmptyMessage(0);
                        }else {
                            handler.sendEmptyMessage(1);
                        }
                    }
                };
                thread.start();
            }
        });
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
               if (msg.what==0){
                   verCode.setText("发送失败");
               }
                if (msg.what==1){
                    verCode.setText("发送成功");
                }
            }
        };
        regirst.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (inputCode.getText().toString().equals(code)){
                    Intent intent=new Intent(RegisterActivity.this,SetpasswordActivity.class);
                    intent.putExtra("phone",inputPhone.getText().toString());
                    startActivity(intent);
                    finish();
                }else {
                    Toast.makeText(RegisterActivity.this,"验证码错误！", Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    protected String validate(String text) {
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
