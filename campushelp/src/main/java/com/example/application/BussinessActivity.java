package com.example.application;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.util.Util;

import java.util.Map;

public class BussinessActivity extends AppCompatActivity {
    //商家活动页面退出
    private Button bussOut;
    //显示TextView合集
    private TextView bussName;
    private TextView bussActiv;
    private TextView bussAddress;
    private TextView bussTime;
    private Map<String,Object> map;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bussiness);
        bussOut= (Button) findViewById(R.id.buss_out);
        bussOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //显示TextView合集
        bussName= (TextView) findViewById(R.id.buss_name);
        bussActiv= (TextView) findViewById(R.id.buss_activ);
        bussAddress= (TextView) findViewById(R.id.buss_address);
        bussTime= (TextView) findViewById(R.id.buss_time);
        int index=getIntent().getIntExtra("id",0);
        map= Util.mapListBuss.get(index);
        bussName.setText(map.get("businessName").toString());
        bussActiv.setText(map.get("activeContent").toString());
        bussAddress.setText(map.get("businessAddress").toString());
        bussTime.setText(map.get("activeEndDate").toString());
    }
}
