package com.example.application;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.Toast;

import com.example.adapter.MessageAdapter;
import com.example.util.Util;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EvaluationActivity extends AppCompatActivity {
    private Button eveOut;
    public static MessageAdapter messageAdapter;
    private List<Map<String,Object>> mapList=new ArrayList<>();
    private ListView listView;
    private Button sumbit;
    private EditText centent;
    private Handler handler;
   private String orderid;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.allActiveActivities.add(this);
        setContentView(R.layout.activity_evaluation);

        mapList=Util.takeMgs;
        orderid=getIntent().getStringExtra("orderId");
        eveOut= (Button) findViewById(R.id.eve_out12);
        eveOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        centent=(EditText)findViewById(R.id.centent);
        listView=(ListView)findViewById(R.id.message);
        messageAdapter=new MessageAdapter(this,mapList);
        listView.setAdapter(messageAdapter);
        sumbit=(Button)findViewById(R.id.submit);
        sumbit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (centent.getText().toString().trim().equals("")){
                    Toast.makeText(EvaluationActivity.this,"评价内容不能为空",Toast.LENGTH_SHORT).show();
                    return;
                }
                Map map=new HashMap();
                map.put("content",centent.getText().toString());
                map.put("commentDate",new Date().toString());
                mapList.add(map);
                handler.sendEmptyMessage(0);

                Thread thread =new Thread(){
                    @Override
                    public void run() {
                        String url= null;
                        try {
                            url = Util.ip+"comment/addComment?content="+ URLEncoder.encode(centent.getText().toString(),"UTF-8")+"&order.id="+orderid;
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
                        } catch (IOException e) {
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
                if (msg.what==0){
                    messageAdapter.notifyDataSetChanged();
                }
            }
        };

    }
    protected void onDestroy(){
        Util.allActiveActivities.remove(this);
        super.onDestroy();
    }
}
