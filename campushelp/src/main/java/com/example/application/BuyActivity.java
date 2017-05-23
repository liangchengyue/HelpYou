package com.example.application;

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

public class BuyActivity extends AppCompatActivity {
    //返回个人页面
    private Button Breturn;

    private Button evaBtn;

    private List<Map<String,Object>> allValues = new ArrayList<Map<String,Object>>();
    private SimpleAdapter adapter;
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
        /**
         *
         evaBtn = (Button) findViewById(R.id.eva_btn);
         evaBtn.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
        Intent eva =new Intent(BuyActivity.this,EvaluationActivity.class);
        startActivity(eva);
        }
        });
         */

        //显示Listview
        /**
         * 显示购买的订单
         */
        orderList = (ListView) findViewById(R.id.order_list);
        for (int i=0;i<4;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("call", "小石浪");
            map.put("order_time", "接单时间：2017/5/21");
            map.put("price", "总价：5元");
            map.put("complete", "完成");
            allValues.add(map);
        }
        adapter = new SimpleAdapter(this, allValues, R.layout.my_prder_list_buy, new String[]{"call", "order_time", "price", "complete"},
                new int[]{R.id.call, R.id.order_time, R.id.price, R.id.complete});
        orderList.setAdapter(adapter);
    }
    protected void onDestroy(){
        Util.allActiveActivities.remove(this);
        super.onDestroy();
    }
}
