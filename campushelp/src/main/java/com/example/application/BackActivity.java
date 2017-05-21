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
import android.widget.TextView;
import android.widget.Toast;

import com.example.util.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

public class BackActivity extends AppCompatActivity {
    private Button back;
    private EditText phone;
    private EditText msmCode;
    private TextView getCode;
    private Button next;
    private  String code;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.allActiveActivities.add(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_back);

        back = (Button) findViewById(R.id.back_btn);
        phone=(EditText)findViewById(R.id.phone_u);
        msmCode=(EditText)findViewById(R.id.smsCode_u);
        getCode=(TextView)findViewById(R.id.getCode_u);
        next=(Button)findViewById(R.id.next_u);

        getCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            String msg=Util.validatephone(phone.getText().toString().trim());
                if (!msg.equals("")){
                    Toast.makeText(BackActivity.this,msg,Toast.LENGTH_SHORT).show();
                    return;
                }
                Thread thread=new Thread(){
                    @Override
                    public void run() {
                        String url= Util.ip+"user/getAndroidIndustrySMS?phone="+phone.getText().toString().trim();
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
                    getCode.setText("发送失败");
                }
                if (msg.what==1){
                    getCode.setText("发送成功");
                }
            }
        };
    next.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            if (msmCode.getText().toString().equals(code)){
                Intent intent12=new Intent(BackActivity.this, NewpasswordActivity.class);
                intent12.putExtra("phone",phone.getText().toString());
                startActivity(intent12);
                finish();
            }else {
                Toast.makeText(BackActivity.this,"验证码错误！", Toast.LENGTH_SHORT).show();
            }
        }
    });
        back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(BackActivity.this,LoginActivity.class);
                startActivity(in);
            }
        });

    }
    protected void onDestroy(){
        Util.allActiveActivities.remove(this);
        super.onDestroy();
    }
}
