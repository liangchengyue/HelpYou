package com.example.application;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.example.adapter.MyLssAdapter;
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
    private ListView resList;
    //ListView数据源
    private List<String> data;

/*    private MyLssAdapter adapter;
    private List<Map<String, Object>> allValuesLss = new ArrayList<Map<String , Object>>();
    private int [] allImgs = new int[]{R.mipmap.head};*/
/*
    //跳转评价按钮
    private Button evaBtn;
    //获取发布订单的listView
    private ListView resList;
    private List<Map<String,Object>> allValueslss = new ArrayList<Map<String,Object>>();
    private MyAdapterLssue adapter;
    private int[] allImgs = new int[]{R.mipmap.head };
*/

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

        data = new ArrayList<>();
        resList= (ListView) findViewById(R.id.res_list);
        for (int i=0;i<4;i++){
            data.add("小石浪");
            data.add("接单时间：2017/5/21");
            data.add("总价：5元");
            data.add("未完成");
        }
        MyLssAdapter adapter = new MyLssAdapter(data);
        resList.setAdapter(adapter);
        //ListView item点击事件
        resList.setOnItemClickListener(new ListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(LssueActivity.this,"我是item点击事件 i = " + position + "l = " + 1,Toast.LENGTH_SHORT).show();
            }
        });



      /*  //老版
        Random random = new Random();
        resList= (ListView) findViewById(R.id.res_list);
        for (int i=0 ;i<10;i++){
            Map<String,Object> map =new HashMap<String , Object>();
            map.put("call_me", "小石浪"+i);
            map.put("order_time_me", "接单时间：2017/5/21");
            map.put("price_me", "总价：5元");
            map.put("complete_me", "未完成");
            map.put("head_me",allImgs[random.nextInt(1)]);
            allValuesLss.add(map);
        }*/
       /* adapterLssue= new MyAdapterLssue(this,allValuesLss);*/
        //显示订单情况
        /*Random random = new Random();
        resList = (ListView) findViewById(R.id.res_list);
        for (int i=0;i<5;i++){
            Map<String, Object> map = new HashMap<String, Object>();
            map.put("callme", "小石浪"+i);
            map.put("ordertimeme", "接单时间：2017/5/21");
            map.put("priceme", "总价：5元");
            map.put("completeme", "未完成");
            map.put("headme",allImgs[random.nextInt(allImgs.length)]);
            allValueslss.add(map);
        }*/

       /* //创建自定义Adapyer
        adapter = new MyAdapterLssue(this,allValueslss);
        //将adapter添加到ListView中
        resList.setAdapter(adapter);
        resList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(LssueActivity.this,"你选择的是第"+position+"行",Toast.LENGTH_SHORT).show();
            }
        });*/
     /*   adapter = new SimpleAdapter(this, allValues, R.layout.my_prder_list_lss, new String[]{"me_call", "me_order_time", "me_price", "me_complete"},
                new int[]{R.id.call_me, R.id.order_time_me, R.id.price_me, R.id.complete_me});
        resList.setAdapter(adapter);*/
        //点击一行事件


/*
                evaBtn= (Button) findViewById(R.id.me_eva_btn);
                evaBtn.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent= new Intent(LssueActivity.this,EvaluationActivity.class);
                        startActivity(intent);
                    }
                });
*/
    }
    protected void onDestroy(){
        Util.allActiveActivities.remove(this);
        super.onDestroy();
    }
}
