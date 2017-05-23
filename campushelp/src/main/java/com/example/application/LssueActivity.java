package com.example.application;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.adapter.MyAdapterLssue;
import com.example.util.Util;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class LssueActivity extends AppCompatActivity {
    //返回个人页面按钮
    private Button Lreturn;
    //ListView中的分步点击事件
    //获取发布订单的listView
    private ListView resList;
    private List<Map<String,Object>> allValues = Util.orders;
    private MyAdapterLssue adapterLssue;
    private int[] allImgs = new int[]{R.mipmap.head };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lssue);
        Util.allActiveActivities.add(this);
        Lreturn = (Button) findViewById(R.id.L_return);
        Lreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        /*
        * ListView中分步点击功能
        * */

        //显示订单情况
        Random random = new Random();
        resList = (ListView) findViewById(R.id.res_list);
        for (int i=0;i<5;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("call_me", "小石浪"+i);
            map.put("order_time_me", "接单时间：2017/5/21");
            map.put("price_me", "总价：5元");
            map.put("complete_me", "未完成");
            map.put("head_me",allImgs[random.nextInt(1)]);
            allValues.add(map);
        }

        //创建自定义Adapyer
        adapterLssue = new MyAdapterLssue(this,allValues);
        //将adapter添加到ListView中
        resList.setAdapter(adapterLssue);
        resList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String, Object> map =allValues.get(position);
                Toast.makeText(LssueActivity.this,"您选中的是:"+ map.get("call_me").toString(),Toast.LENGTH_SHORT).show();
            }
        });
        resList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Map<String ,Object> map =allValues.get(position);
                Toast.makeText(LssueActivity.this,"删除的是:"+ position+"条记录",Toast.LENGTH_LONG).show();
                return false;
            }
        });
    }
    protected void onDestroy(){
        Util.allActiveActivities.remove(this);
        super.onDestroy();
    }
}
