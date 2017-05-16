package com.example.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

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
    private EditText userName;
    private EditText password;
    private Button login;
    private String url="http://192.168.121.1:8080/Express_delivery/user/loginAndroid";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        userName=(EditText)findViewById(R.id.userName);
        password=(EditText)findViewById(R.id.password1);
        login=(Button)findViewById(R.id.login);
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.v("ss","____________________________");
                Thread thread=new Thread(){
                    @Override
                    public void run() {
                        url=url+"?userName="+userName.getText()+"&password="+password.getText();
                        try {
                            URL url1=new URL(url);
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
                                    intent.putExtra("userId",jsonObject.getString("id"));
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
    }
}
