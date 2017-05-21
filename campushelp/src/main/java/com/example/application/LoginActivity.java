package com.example.application;

import android.content.Intent;
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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class LoginActivity extends AppCompatActivity {
    private TextView regist;
    private EditText userName;
    private EditText password;
    private TextView backPassword;
    private Button login;
    private String url= Util.ip+"user/loginAndroid";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.allActiveActivities.add(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        setContentView(R.layout.activity_login);
        userName=(EditText)findViewById(R.id.userName);
        password=(EditText)findViewById(R.id.password1);
        login=(Button)findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {


        @Override
            public void onClick(View v) {
                String msg=validate(userName.getText().toString(),"用户名");
            if (!msg.equals("")){
                Toast.makeText(LoginActivity.this,msg,Toast.LENGTH_SHORT).show();
                return;
            }
            msg=validate(password.getText().toString(),"密码");
            if (!msg.equals("")){
                Toast.makeText(LoginActivity.this,msg,Toast.LENGTH_SHORT).show();
                return;
            }
                Thread thread=new Thread(){
                    @Override
                    public void run() {
                        url=url+"?userName="+userName.getText()+"&password="+password.getText();
                        try {
                            URL url1= new URL(url);
                            URLConnection urlConnection=url1.openConnection();
                            InputStream is=urlConnection.getInputStream();
                            BufferedReader bf=new BufferedReader(new InputStreamReader(is));
                            String line=bf.readLine();
                            bf.close();
                            is.close();
                            try {
                                JSONObject jsonObject=new JSONObject(line);
                                if (jsonObject.getString("id")!=null){
                                    Intent intent=new Intent(LoginActivity.this,MainActivity.class);
                                    Util.userId=jsonObject.getString("id");
                                    startActivity(intent);
                                    finish();
                                }
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                };
                thread.start();
            }
        });
        //跳转到注册页面的点击事件
        regist = (TextView) findViewById(R.id.regist);
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent res = new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(res);
            }
        });
        //跳转到忘记密码
        backPassword = (TextView) findViewById(R.id.back_password);
        backPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in =new Intent(LoginActivity.this,BackActivity.class);
                        startActivity(in);
            }
        });
    }
    protected String validate(String userName,String title){
        String msg="";
        if (userName.trim().equals("")){
            msg=msg+title+"不能为空!";
        }
        return msg;
    }
    protected void onDestroy(){
        Util.allActiveActivities.remove(this);
        super.onDestroy();
    }
}
