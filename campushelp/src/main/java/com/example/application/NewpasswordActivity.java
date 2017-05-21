package com.example.application;

import android.content.Intent;
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

public class NewpasswordActivity extends AppCompatActivity {
    private EditText password;
    private EditText cpassword;
    private Button update;
    String phone="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.allActiveActivities.add(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_newpassword);
        phone=getIntent().getStringExtra("phone");
        password=(EditText)findViewById(R.id.password_u);
        cpassword=(EditText)findViewById(R.id.cpassword_u);
        update=(Button)findViewById(R.id.update_u);
        update.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (password.getText().toString().equals("")){
                    Toast.makeText(NewpasswordActivity.this,"密码不能为空！",Toast.LENGTH_LONG).show();
                    return;
                }
                if (!cpassword.getText().toString().equals(password.getText().toString())){
                    Toast.makeText(NewpasswordActivity.this,"两次密码不一致！",Toast.LENGTH_LONG).show();
                    return;
                }
                Thread thread=new Thread(){
                    @Override
                    public void run() {
                        String url= Util.ip+"user/modifyAndroidUserPassword?telephone="+phone+"&password="+password.getText().toString();
                        String msg=""
;                        try {
                            URL url1= new URL(url);
                            URLConnection urlConnection=url1.openConnection();
                            InputStream is=urlConnection.getInputStream();
                            BufferedReader bf=new BufferedReader(new InputStreamReader(is));
                            msg=bf.readLine();
                            bf.close();
                            is.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (msg.equals("success")){
                            Intent intent=new Intent(NewpasswordActivity.this,LoginActivity.class);
                            startActivity(intent);
                        }else {
                            Toast.makeText(NewpasswordActivity.this,"密码修改失败！",Toast.LENGTH_LONG).show();
                        }
                    }
                };
                thread.start();
            }
        });
    }
    protected void onDestroy(){
        Util.allActiveActivities.remove(this);
        super.onDestroy();
    }
}
