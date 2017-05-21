package com.example.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.example.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LssueActivity extends AppCompatActivity {
    //返回个人页面
    private Button Lreturn;
    private List<Map<String,Object>> allValues = new ArrayList<Map<String,Object>>();
    private SimpleAdapter adapter;
    private ListView resList;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lssue);
        Util.allActiveActivities.add(this);
        Lreturn = (Button) findViewById(R.id.L_return);
        Lreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lssue = new Intent(LssueActivity.this,MainActivity.class);
                startActivity(lssue);
            }
        });
        //显示订单情况
        resList = (ListView) findViewById(R.id.res_list);
        for (int i=0;i<4;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("me_call", "小石浪");
            map.put("me_order_time", "接单时间：2017/5/21");
            map.put("me_price", "总价：5元");
            map.put("me_complete", "未完成");
            allValues.add(map);
        }
        adapter = new SimpleAdapter(this, allValues, R.layout.my_prder_list_lss, new String[]{"me_call", "me_order_time", "me_price", "me_complete"},
                new int[]{R.id.call_me, R.id.order_time_me, R.id.price_me, R.id.complete_me});
        resList.setAdapter(adapter);
    }
    protected void onDestroy(){
        Util.allActiveActivities.remove(this);
        super.onDestroy();
    }
}
