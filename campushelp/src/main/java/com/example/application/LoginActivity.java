package com.example.application;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
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
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LoginActivity extends AppCompatActivity {
    private TextView regist;
    private EditText userName;
    private EditText password;
    private TextView backPassword;
    private Button login;
    private String url = Util.ip + "user/loginAndroid";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.allActiveActivities.add(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);

        setContentView(R.layout.activity_login);
        userName = (EditText) findViewById(R.id.userName);
        password = (EditText) findViewById(R.id.password1);
        login = (Button) findViewById(R.id.login);

        login.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                String msg = validate(userName.getText().toString(), "用户名");
                if (!msg.equals("")) {
                    Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                    return;
                }
                msg = validate(password.getText().toString(), "密码");
                if (!msg.equals("")) {
                    Toast.makeText(LoginActivity.this, msg, Toast.LENGTH_SHORT).show();
                    return;
                }
                login.setText("正在登陆");
                login.setClickable(false);
                Thread thread = new Thread() {
                    @Override
                    public void run() {
                            url = url + "?userName=" + userName.getText() + "&password=" + password.getText();

                        try {
                            URL url1 = new URL(url);
                            URLConnection urlConnection = url1.openConnection();
                            InputStream is = urlConnection.getInputStream();
                            BufferedReader bf = new BufferedReader(new InputStreamReader(is));
                            String line = bf.readLine();
                            bf.close();
                            is.close();

                            try {
                                final JSONObject jsonObject = new JSONObject(line);
                                Util.userId = jsonObject.getString("id");
                                if (jsonObject.getString("id") != null) {
                                    Thread thread1 = new Thread() {
                                        @Override
                                        public void run() {
                                            url = Util.ip + "order/queryOrderList";
                                            try {
                                                URL url2 = new URL(url);
                                                URLConnection uc = url2.openConnection();
                                                InputStream is = uc.getInputStream();
                                                BufferedReader bf = new BufferedReader(new InputStreamReader(is));
                                                String json = bf.readLine();
                                                bf.close();
                                                is.close();
                                                if (!json.equals("]")){
                                                JSONArray jsonArray = new JSONArray(json.toString());
                                                List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
                                                for (int i = 0; i < jsonArray.length(); i++) {
                                                    JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                                    Map<String, Object> map = new HashMap<String, Object>();
                                                    map.put("id", jsonObject1.getString("id"));
                                                    map.put("time", jsonObject1.getString("takeDate"));
                                                    map.put("collage", jsonObject1.getString("takeaddress"));
                                                    map.put("pro", jsonObject1.getString("preaddress"));
                                                    map.put("remark", jsonObject1.getString("remarks"));
                                                    map.put("img", R.mipmap.head);
                                                    map.put("name", jsonObject1.getString("name"));
                                                    map.put("phone", jsonObject1.getString("teltPhone"));
                                                    map.put("grade", jsonObject1.getString("grade"));
                                                    mapList.add(map);
                                                }

                                                Util.orders = mapList;
                                                    if (MainActivity.adapter!=null){
                                                        MainActivity.adapter.notifyDataSetChanged();
                                                    }
                                                }
                                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                                startActivity(intent);
                                                finish();
                                            } catch (Exception e) {
                                                e.printStackTrace();
                                            }

                                        }
                                    };
                                    thread1.start();
                                    Thread thread =new Thread(){
                                        @Override
                                        public void run() {
                                            String url=Util.ip+"user/getUserbyIdForAndroid?id="+Util.userId;
                                            try {
                                                URL url2 = new URL(url);
                                                URLConnection uc = url2.openConnection();
                                                InputStream is = uc.getInputStream();
                                                BufferedReader bf = new BufferedReader(new InputStreamReader(is));
                                                String json = bf.readLine();
                                                bf.close();
                                                is.close();
                                                try {
                                                    JSONObject object=new JSONObject(json);
                                                    Map<String,Object> map=Util.userinfo;
                                                    map.put("nickName",object.getString("nickName"));
                                                    map.put("userName",object.getString("userName"));
                                                    map.put("integral",object.getString("integral"));
                                                    map.put("password",object.getString("password"));
                                                    map.put("idCard",object.getString("idCard"));
                                                    map.put("gender",object.getString("gender"));
                                                    map.put("imagePath",object.getString("imagePath"));
                                                    map.put("school",object.getString("school"));
                                                    map.put("realName",object.getString("trueName"));
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    };
                                    thread.start();
                                    //获取商家活动信息
                                    Thread thread2=new Thread(){
                                        @Override
                                        public void run() {
                                            String url=Util.ip+"businessActivities/queryBusinessActivities";
                                            try {
                                                URL url2 = new URL(url);
                                                URLConnection uc = url2.openConnection();
                                                InputStream is = uc.getInputStream();
                                                BufferedReader bf = new BufferedReader(new InputStreamReader(is));
                                                String json = bf.readLine();
                                                bf.close();
                                                is.close();
                                                try {
                                                    JSONArray jsonArray = new JSONArray(json.toString());
                                                    List<Map<String, Object>> mapList =Util.mapListBuss ;
                                                    for (int i = 0; i < jsonArray.length(); i++) {
                                                        JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                                        Map<String, Object> map = new HashMap<String, Object>();
                                                        map.put("activeContent",jsonObject1.getString("activeContent"));
                                                        map.put("activeEndDate",jsonObject1.getString("activeEndDate"));
                                                        map.put("businessAddress",jsonObject1.getString("businessAddress"));
                                                        map.put("businessName",jsonObject1.getString("businessName"));
                                                        map.put("imagePath",jsonObject1.getString("imagePath"));
                                                        map.put("id",jsonObject1.getString("id"));
                                                        mapList.add(map);
                                                    }
                                                } catch (JSONException e) {
                                                    e.printStackTrace();
                                                }
                                            } catch (IOException e) {
                                                e.printStackTrace();
                                            }
                                        }
                                    };
                                    thread2.start();
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
                Intent res = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(res);
            }
        });
        //跳转到忘记密码
        backPassword = (TextView) findViewById(R.id.back_password);
        backPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(LoginActivity.this, BackActivity.class);
                startActivity(in);
            }
        });
    }

    protected String validate(String userName, String title) {
        String msg = "";
        if (userName.trim().equals("")) {
            msg = msg + title + "不能为空!";
        }
        return msg;
    }

    protected void onDestroy() {
        Util.allActiveActivities.remove(this);
        super.onDestroy();
    }
}
