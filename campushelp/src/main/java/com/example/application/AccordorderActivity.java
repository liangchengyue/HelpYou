package com.example.application;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.util.Globals;
import com.example.util.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Map;

public class AccordorderActivity extends AppCompatActivity {
    private TextView takeDate;
    private TextView name;
    private TextView takeaddress;
    private TextView preaddress;
    private TextView teltPhone;
    private TextView grade;
    private TextView remarks;
    private Button jiedan;
    private Button cancal;
    private Handler handler;
    private Map<String, Object> map;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.allActiveActivities.add(this);
        //调用init方法，初始化，获取当前手机的宽和高
        Globals.init(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_accordorder);
        final int index = getIntent().getIntExtra("index", 0);
        takeDate = (TextView) findViewById(R.id.takeDate_i);
        name = (TextView) findViewById(R.id.name_i);
        takeaddress = (TextView) findViewById(R.id.takeaddress_i);
        preaddress = (TextView) findViewById(R.id.preaddress_i);
        teltPhone = (TextView) findViewById(R.id.teltPhone_i);
        grade = (TextView) findViewById(R.id.grade_i);
        remarks = (TextView) findViewById(R.id.remarks_i);
        jiedan = (Button) findViewById(R.id.jiedan_i);
        cancal = (Button) findViewById(R.id.cancl_i);
        map = Util.orders.get(index);
        takeDate.setText(map.get("time").toString());
        name.setText(map.get("name").toString());
        takeaddress.setText(map.get("collage").toString());
        preaddress.setText(map.get("pro").toString());
        teltPhone.setText("接单后可见");
        grade.setText(map.get("grade").toString());
        remarks.setText(map.get("remark").toString());
        handler = new Handler() {
            @Override
            public void handleMessage(Message message) {
                if (message.what == 1) {
                    teltPhone.setText(map.get("phone").toString());
                    jiedan.setText("接单成功");
                    MainActivity.adapter.notifyDataSetChanged();
                }
            }
        };
        jiedan.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String text = jiedan.getText().toString();
                if (text.equals("接单")) {
                    Thread thread1 = new Thread() {
                        @Override
                        public void run() {

                            String url = Util.ip + "order/takeOrder?takeOrderUser.id=" + Util.userId + "&id=" + map.get("id");
                            try {
                                URL url2 = new URL(url);
                                URLConnection uc = url2.openConnection();
                                InputStream is = uc.getInputStream();
                                BufferedReader bf = new BufferedReader(new InputStreamReader(is));
                                String json = bf.readLine();
                                bf.close();
                                is.close();
                                if (json.equals("success")) {
                                    handler.sendEmptyMessage(1);
                                    Util.orders.remove(index);

                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    thread1.start();
                }
            }
        });
        cancal = (Button) findViewById(R.id.cancl_i);
        cancal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
