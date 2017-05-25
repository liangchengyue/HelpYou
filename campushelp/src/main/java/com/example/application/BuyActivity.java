package com.example.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;

import com.example.adapter.MyAdapterBuy;
import com.example.adapter.MyAdapterLssue;
import com.example.util.Util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class BuyActivity extends AppCompatActivity {
    //返回个人页面
    private Button Breturn;


    private List<Map<String,Object>> allValues =Util.takeOrders;
    public static MyAdapterBuy adapterBuy;
    private int[] allImgs = new int[]{R.mipmap.head };
    private ListView orderList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.allActiveActivities.add(this);
        setContentView(R.layout.activity_buy);

        Breturn= (Button) findViewById(R.id.B_return);
        Breturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        orderList = (ListView) findViewById(R.id.order_list);
         adapterBuy = new MyAdapterBuy(this,allValues);
         orderList.setAdapter(adapterBuy);
        orderList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent=new Intent(BuyActivity.this,BuyView.class);
                intent.putExtra("index",position);
                startActivity(intent);
            }
        });
    }
    protected void onDestroy(){
        Util.allActiveActivities.remove(this);
        super.onDestroy();
    }
}
