package com.example.application;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.util.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class PasswordActivity extends AppCompatActivity {
    private Button ps;
    private EditText opassword;
    private EditText npassword;
    private EditText cpassword;
    private Button dl;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.allActiveActivities.add(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_password);
        ps = (Button) findViewById(R.id.ps_btn);
        ps.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent in = new Intent(PasswordActivity.this, SetActivity.class);
                startActivity(in);
            }
        });
        opassword=(EditText)findViewById(R.id.old_password);
        npassword=(EditText)findViewById(R.id.new_password);
        cpassword=(EditText)findViewById(R.id.c_password);
        dl=(Button)findViewById(R.id.dl);
        dl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p=Util.userinfo.get("password").toString();
                if (!p.equals(opassword.getText().toString())){
                    Toast.makeText(PasswordActivity.this,"原密码错误",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (npassword.getText().toString().equals("")){
                    Toast.makeText(PasswordActivity.this,"请输入新密码",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!npassword.getText().toString().equals(cpassword.getText().toString())){
                    Toast.makeText(PasswordActivity.this,"两次密码不一致",Toast.LENGTH_SHORT).show();
                    return;
                }
                Thread thread=new Thread(){
                    @Override
                    public void run() {
                       String url=Util.ip+"user/updatePassword?id="+Util.userId+"&password="+npassword.getText().toString();
                        try {
                            URL url2 = new URL(url);
                            URLConnection uc = url2.openConnection();
                            InputStream is = uc.getInputStream();
                            BufferedReader bf = new BufferedReader(new InputStreamReader(is));
                            String json = bf.readLine();
                            bf.close();
                            is.close();
                            if (json.equals("success")){
                                handler.sendEmptyMessage(0);

                                Util.userinfo.put("password",npassword.getText().toString());
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
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
                    Toast.makeText(PasswordActivity.this,"密码修改成功",Toast.LENGTH_SHORT).show();
                }
            }
        };
    }
    protected void onDestroy(){
        Util.allActiveActivities.remove(this);
        super.onDestroy();
    }
}
