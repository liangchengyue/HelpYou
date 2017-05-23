package com.example.application;

import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.util.Util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import static com.example.application.R.id.id;
import static com.example.application.R.id.man;
import static com.example.application.R.id.spinner;

public class EditorActivity extends AppCompatActivity {
    private Button setBtn;
    private RadioGroup radioGroup;
    private RadioButton man;
    private RadioButton wman;
    private EditText realName;
    private EditText idCard;
    private String gender;
    private String school;
    private Spinner spinner;

    private Button registe;
    private Handler ha;
    private  Button reset;
    private String url;

    //Spinnier
    private String[] allArea = new String[]{"贵州大学北校区","贵州大学东校区","贵州大学西校区","贵州大学南校区"};
    private ArrayAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.allActiveActivities.add(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_editor);
        setBtn = (Button) findViewById(R.id.set_btn);
        setBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                finish();
            }
        });





        //获取下拉列表
        spinner=(Spinner)findViewById(R.id.spinner);
        //创建
        adapter = new ArrayAdapter(this,R.layout.my_spinner_item,allArea);
        //将adapter添加到spinner中
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
            school=allArea[position];
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
        radioGroup=(RadioGroup)findViewById(R.id.gender_r);
        man=(RadioButton)findViewById(R.id.man);
        wman=(RadioButton)findViewById(R.id.wman);


        radioGroup.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                if (man.getId()==checkedId){
                    gender="男";
                }
                if (wman.getId()==checkedId){
                    gender="女";
                }
            }
        });

        realName= (EditText) findViewById(R.id.realName_r);
        radioGroup=(RadioGroup)findViewById(R.id.gender_r);
        idCard=(EditText)findViewById(R.id.id_card);



        registe=(Button)findViewById(R.id.registe_rr);
        reset=(Button)findViewById(R.id.reset_r);
        registe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg=validate(realName.getText().toString().trim(),"真实姓名");
                if (!msg.equals("")){
                    Toast.makeText(EditorActivity.this, msg, Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!validateIdCard(idCard.getText().toString().trim())){
                    Toast.makeText(EditorActivity.this, "身份证格式不正确", Toast.LENGTH_SHORT).show();
                    return;
                }
                if (gender==null) {
                    Toast.makeText(EditorActivity.this, "请选择性别", Toast.LENGTH_SHORT).show();
                    return;
                }
                final Thread th=new Thread(){
                    @Override
                    public void run() {
                        Map<String,String> map=new HashMap<String, String>();
                        map.put("id",Util.userId);
                        map.put("trueName",realName.getText().toString().trim());
                        map.put("idCard",idCard.getText().toString().trim());
                        map.put("gender",gender);
                        map.put("school",school);
                        String url=Util.ip+"user/androidByIdcard?"+fommatPamer(map);
                        String line="";
                        try {
                            URL url1= new URL(url);
                            URLConnection urlConnection=url1.openConnection();
                            InputStream is=urlConnection.getInputStream();
                            BufferedReader bf=new BufferedReader(new InputStreamReader(is));
                            line =bf.readLine();
                            bf.close();
                            is.close();
                            if (line.equals("ok")){
                                Map<String,Object> m=Util.userinfo;
                                m.put("realName",realName.getText().toString());
                                m.put("idCard",idCard.getText().toString());
                                m.put("gender",gender);
                                m.put("school",school);
                                Intent in=new Intent(EditorActivity.this,IdActivity.class);
                                startActivity(in);
                                finish();
                                //Toast.makeText(EditorActivity.this,"实名认证通过",Toast.LENGTH_SHORT).show();
                            }else {
                                ha.sendEmptyMessage(0);

                            }
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                };
                th.start();

            }
        });
        ha=new Handler(){
            @Override
            public void handleMessage(Message msg) {
                if (msg.what==0){
                    Toast.makeText(EditorActivity.this,"实名认证未通过",Toast.LENGTH_SHORT).show();
                }
            }
        };
    }
    protected  String validate(String text,String title){
        String msg="";
        if (text.trim().equals("")){
            msg=msg+title+"不能为空！";
        }
        return msg;
    }
    protected void onDestroy(){
        Util.allActiveActivities.remove(this);
        super.onDestroy();
    }
    protected boolean validateIdCard(String idCard){

        String REGEX_ID_CARD = "(^\\d{18}$)|(^\\d{15}$)";
        boolean flag= Pattern.matches(REGEX_ID_CARD, idCard);
        return flag;

    }
    protected String fommatPamer(Map<String,String> map){
        StringBuffer sb=new StringBuffer();
        for (String item:map.keySet()){
            try {
                sb.append(item+"="+ URLEncoder.encode(map.get(item),"UTF-8")+"&");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
        return sb.toString().substring(0,sb.toString().length()-1);
    }
}
