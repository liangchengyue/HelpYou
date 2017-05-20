package com.example.application;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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

public class SetpasswordActivity extends AppCompatActivity {
    private EditText password;
    private EditText cpassword;
    private Button regist;
    private String phone;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_setpassword);
        phone=getIntent().getStringExtra("phone");
        password=(EditText)findViewById(R.id.password_rs);
        cpassword=(EditText)findViewById(R.id.cpassword_rs);
        regist=(Button)findViewById(R.id.regist_rs);
        regist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String p=password.getText().toString().trim();
                String c=cpassword.getText().toString().trim();
                if (p.equals("")){
                    Toast.makeText(SetpasswordActivity.this,"密码不能为空", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!p.equals(c)){
                    Toast.makeText(SetpasswordActivity.this,"两次密码不一致", Toast.LENGTH_SHORT).show();
                    return;
                }
                Thread thread=new Thread(){
                    @Override
                    public void run() {
                        String u= Util.ip+"user/regsterAndroid?userName="+phone+"&password="+password.getText().toString().trim()+"&telephone="+phone;
                        String line1="";
                        try {
                            URL url1= new URL(u);
                            URLConnection urlConnection=url1.openConnection();
                            InputStream is=urlConnection.getInputStream();
                            BufferedReader bf=new BufferedReader(new InputStreamReader(is));
                            line1=bf.readLine();
                            bf.close();
                            is.close();

                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                        if (line1.equals("succss")){
                            Intent intent=new Intent(SetpasswordActivity.this,LoginActivity.class);
                            startActivity(intent);
                            finish();
                        }
                    }
                };
                thread.start();

            }
        });
    }
}
