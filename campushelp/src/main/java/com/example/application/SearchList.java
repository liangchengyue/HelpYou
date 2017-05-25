package com.example.application;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.example.adapter.MyAdapter;
import com.example.util.Util;

import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SearchList extends AppCompatActivity {
    private EditText txKey;
    private TextView scrach;
    private Button out;
    private ListView listView;
    private MyAdapter listAdapder;
    private List<Map<String,Object>> mapList;
    private Handler handler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.allActiveActivities.add(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_search);
        txKey=(EditText)findViewById(R.id.ed_key);
        scrach=(TextView)findViewById(R.id.serach);
        listView=(ListView)findViewById(R.id.serach_list);
        mapList=Util.serOrder;
        listAdapder=new MyAdapter(this,mapList);
        listView.setAdapter(listAdapder);
        out=(Button)findViewById(R.id.out_list);
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        scrach.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Thread thread=new Thread(){
                    @Override
                    public void run() {
                        String url = null;
                        try {
                            url = Util.ip + "order/queryOrderList?keyword="+ URLEncoder.encode(txKey.getText().toString(),"UTF-8");
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                        try {
                            URL url2 = new URL(url);
                            URLConnection uc = url2.openConnection();
                            InputStream is = uc.getInputStream();
                            BufferedReader bf = new BufferedReader(new InputStreamReader(is));
                            String json = bf.readLine();
                            bf.close();
                            is.close();
                            if (!json.equals("]")) {
                                JSONArray jsonArray = new JSONArray(json.toString());
                                Util.serOrder.clear();
                                List<Map<String, Object>> mapList = Util.serOrder;
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
                                handler.sendEmptyMessage(1);

                            }
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                };
                thread.start();
            }
        });
        handler=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what==1){
                    listAdapder.notifyDataSetChanged();
                }
            }
        };
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(SearchList.this, AccordorderActivity.class);
                intent.putExtra("index", position);
                startActivity(intent);
                finish();
            }
        });
}
    protected void onDestroy(){
        Util.allActiveActivities.remove(this);
        super.onDestroy();
    }
}
