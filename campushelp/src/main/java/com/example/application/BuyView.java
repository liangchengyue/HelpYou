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

public class BuyView extends AppCompatActivity {
    private Button buyOut;
    private Button buyCancel;
    private Button buyDelete;
    private Button buyEva;
    private ListView buyList;
    private Map<String,Object> map;
    private TextView buyName;
    private TextView state;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_buy_view);
        map= Util.takeOrders.get(getIntent().getIntExtra("index",0));
        buyName=(TextView)findViewById(R.id.buy_name);
        state=(TextView)findViewById(R.id.buy_complete);
        buyName.setText(map.get("name").toString());
        state.setText(map.get("state").toString());


        //退出按钮
        buyOut= (Button) findViewById(R.id.buy_out);
        buyOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        //删除订单按钮
        buyDelete= (Button) findViewById(R.id.buy_delete);
        buyDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!map.get("state").toString().equals("已完成")){
                    Toast.makeText(BuyView.this,"该订单不能删除，未完成",Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(BuyView.this,"请稍等",Toast.LENGTH_SHORT).show();
                    //更新列表
                    Util.takeOrders.remove(map);
                    BuyActivity.adapterBuy.notifyDataSetChanged();
                    //更新数据库
                    Thread thread=new Thread(){
                        @Override
                        public void run() {
                            try {
                                String url=Util.ip+"order/takedelOrder?id="+map.get("id").toString();
                                URL url1 = new URL(url);
                                URLConnection urlConnection = url1.openConnection();
                                InputStream is = urlConnection.getInputStream();
                                BufferedReader bf = new BufferedReader(new InputStreamReader(is));
                                String f = bf.readLine();
                                bf.close();
                                is.close();
                                if (f.equals("succcess")){
                                    Toast.makeText(BuyView.this,"删除成功",Toast.LENGTH_SHORT).show();
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
        //查看评价按钮
        buyEva= (Button) findViewById(R.id.buy_eva);
        buyEva.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!map.get("state").equals("已完成")){
                    Toast.makeText(BuyView.this,"该订单未完成",Toast.LENGTH_SHORT).show();
                    return;
                }else {
                    Thread thread=new Thread(){
                        @Override
                        public void run() {
                            String url=Util.ip+"comment/queryComment?orderId="+map.get("id").toString();
                            try {
                                URL url2 = new URL(url);
                                URLConnection uc = url2.openConnection();
                                InputStream is = uc.getInputStream();
                                BufferedReader bf = new BufferedReader(new InputStreamReader(is));
                                String json = bf.readLine();
                                bf.close();
                                is.close();
                                List<Map<String, Object>> mapList = new ArrayList<Map<String, Object>>();
                                    if (!json.equals("]") || !json.equals("")){
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
                                    if (EvaluationActivity.messageAdapter!=null){
                                        EvaluationActivity.messageAdapter.notifyDataSetChanged();
                                    }}
                                    Intent in = new Intent(BuyView.this,EvaluationActivity.class);
                                   in.putExtra("orderId",map.get("id").toString());
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
