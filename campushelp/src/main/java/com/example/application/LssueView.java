package com.example.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
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
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class LssueView extends AppCompatActivity {
    private Button lssOut;
    private Button lssDelete;
    private Button lssEva;
    private ListView lssList;
    private Map<String,Object> map;
    private TextView name;
    private TextView state;
    private Button lssOk;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lssue_view);
        map= Util.preOrders.get(getIntent().getIntExtra("index",0));
        name=(TextView)findViewById(R.id.lss_name);
        state=(TextView)findViewById(R.id.lss_complete);
        name.setText(map.get("name").toString());
        state.setText(map.get("state").toString());
        //退出按钮
        lssOut= (Button) findViewById(R.id.lss_out);
        lssOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        //取消订单按钮
//        lssCancel= (Button) findViewById(R.id.lss_cancel);
//        lssCancel.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if (map.get("state").toString().equals("未接单")){
//                    Toast.makeText(LssueView.this,"请稍等",Toast.LENGTH_SHORT).show();
//                    Util.preOrders.remove(map);
//                    Util.orders.remove(map);
//                    //更新界面列表
//                    MainActivity.adapter.notifyDataSetChanged();
//                    LssueActivity.adapterLssue.notifyDataSetChanged();
//                    //更新数据库
//                    Thread thread=new Thread(){
//                        @Override
//                        public void run() {
//                            try {
//                                String url=Util.ip+"order/delOrder?id="+map.get("id").toString();
//                                URL url1 = new URL(url);
//                                URLConnection urlConnection = url1.openConnection();
//                                InputStream is = urlConnection.getInputStream();
//                                BufferedReader bf = new BufferedReader(new InputStreamReader(is));
//                                String f = bf.readLine();
//                                bf.close();
//                                is.close();
//                                if (f.equals("succcess")){
//                                    Toast.makeText(LssueView.this,"删除成功",Toast.LENGTH_SHORT).show();
//                                    finish();
//                                }
//                            } catch (IOException e) {
//                                e.printStackTrace();
//                            }
//                        }
//                    };
//                    thread.start();
//                }else {
//                    Toast.makeText(LssueView.this,"该订单不能取消",Toast.LENGTH_SHORT).show();
//                }
//            }
//        });
        //删除订单按钮
        lssDelete= (Button) findViewById(R.id.lss_delete);
        lssDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!map.get("state").toString().equals("已完成")){
                    Toast.makeText(LssueView.this,"该订单不能删除，未完成",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(LssueView.this,"请稍等",Toast.LENGTH_SHORT).show();
                    //更新列表
                    Util.preOrders.remove(map);
                    LssueActivity.adapterLssue.notifyDataSetChanged();
                    //更新数据库
                    Thread thread=new Thread(){
                        @Override
                        public void run() {
                            try {
                                String url=Util.ip+"order/predelOrder?id="+map.get("id").toString();
                                URL url1 = new URL(url);
                                URLConnection urlConnection = url1.openConnection();
                                InputStream is = urlConnection.getInputStream();
                                BufferedReader bf = new BufferedReader(new InputStreamReader(is));
                                String f = bf.readLine();
                                bf.close();
                                is.close();
                                if (f.equals("succcess")){
                                    Toast.makeText(LssueView.this,"删除成功",Toast.LENGTH_SHORT).show();
                                    finish();
                                }
                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    thread.start();
                }
            }
        });
        //确认订单
        lssOk=(Button)findViewById(R.id.lss_ok);
        lssOk.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                map.put("state","已完成");
                LssueActivity.adapterLssue.notifyDataSetChanged();
                state.setText("已完成");
                //更新数据库
                Thread thread=new Thread(){
                    @Override
                    public void run() {
                        try {
                            String url=Util.ip+"order/overOrder?id="+map.get("id").toString();
                            URL url1 = new URL(url);
                            URLConnection urlConnection = url1.openConnection();
                            InputStream is = urlConnection.getInputStream();
                            BufferedReader bf = new BufferedReader(new InputStreamReader(is));
                            String f = bf.readLine();
                            bf.close();
                            is.close();
                            if (f.equals("succcess")){
                                Toast.makeText(LssueView.this,"接收订单成功",Toast.LENGTH_SHORT).show();
                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                };
                thread.start();
            }
        });
        //评价订单按钮
        lssEva= (Button) findViewById(R.id.lss_eva);
        lssEva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!map.get("state").equals("已完成")){
                    Toast.makeText(LssueView.this,"该订单未完成",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Thread thread = new Thread() {
                        @Override
                        public void run() {
                            String url = Util.ip + "comment/queryComment?orderId=" + map.get("id").toString();
                            try {
                                URL url2 = new URL(url);
                                URLConnection uc = url2.openConnection();
                                InputStream is = uc.getInputStream();
                                BufferedReader bf = new BufferedReader(new InputStreamReader(is));
                                String json = bf.readLine();
                                bf.close();
                                is.close();

                                List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
                                    if (!json.equals("]") || !json.equals("")) {
                                        try {
                                        JSONArray jsonArray = new JSONArray(json.toString());

                                        for (int i = 0; i < jsonArray.length(); i++) {
                                            JSONObject jsonObject1 = jsonArray.getJSONObject(i);
                                            Map<String, Object> map = new HashMap<String, Object>();
                                            map.put("id", jsonObject1.getString("id"));
                                            map.put("commentDate", jsonObject1.getString("commentDate"));
                                            map.put("content", jsonObject1.getString("content"));
                                            mapList.add(map);
                                        }
                                        } catch (JSONException e) {
                                            e.printStackTrace();
                                        }
                                        Util.takeMgs = mapList;
                                        if (EvaluationActivity.messageAdapter != null) {
                                            EvaluationActivity.messageAdapter.notifyDataSetChanged();
                                        }
                                    }
                                    Intent in = new Intent(LssueView.this, EvaluationActivity.class);
                                    in.putExtra("orderId", map.get("id").toString());
                                    startActivity(in);

                            } catch (IOException e) {
                                e.printStackTrace();
                            }
                        }
                    };
                    thread.start();
                }
            }
        });
    }
}
