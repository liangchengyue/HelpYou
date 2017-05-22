package com.example.application;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;

import com.example.util.Globals;
import com.example.util.Util;

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
    private Map<String,Object> map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.allActiveActivities.add(this);
        //调用init方法，初始化，获取当前手机的宽和高
        Globals.init(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_accordorder);
        int index=getIntent().getIntExtra("index",0);
        takeDate=(TextView)findViewById(R.id.takeDate_i);
        name=(TextView)findViewById(R.id.name_i);
        takeaddress=(TextView)findViewById(R.id.takeaddress_i);
        preaddress=(TextView)findViewById(R.id.preaddress_i);
        teltPhone=(TextView)findViewById(R.id.teltPhone_i);
        grade=(TextView)findViewById(R.id.grade_i);
        remarks=(TextView)findViewById(R.id.remarks_i);
        jiedan=(Button)findViewById(R.id.jiedan_i);
        cancal=(Button)findViewById(R.id.cancl_i);
        map=Util.orders.get(index);
        String text=jiedan.getText().toString();
       Thread thread=new Thread(){
           @Override
           public void run() {
               handler.sendEmptyMessage(0);
           }
       };
        thread.start();
        handler=new Handler(){
            @Override
            public void handleMessage(Message message) {
                if (message.what==0){
                    takeDate.setText(map.get("time").toString());
                    name.setText(map.get("name").toString());
                    takeaddress.setText(map.get("collage").toString());
                    preaddress.setText(map.get("pro").toString());
                    teltPhone.setText("接单后可见");
                    grade.setText(map.get("grade").toString());
                    remarks.setText(map.get("remark").toString());
                }
            }
        };

    }
}
