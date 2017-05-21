package com.example.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.Toast;

import com.example.util.Util;

import static com.example.application.R.id.spinner;

public class EditorActivity extends AppCompatActivity {
    private Button setBtn;
    private RadioGroup radioGroup;
    private EditText nickName;
    private EditText userName;
    private EditText password;
    private EditText cpassword;
    private RadioGroup gender;
    private EditText telphone;

    private Button registe;
    private  Button reset;
    private String url;
    //Spinnier
    private String[] allArea = new String[]{"贵州大学北校区","贵州大学东校区","贵州大学西校区","贵州大学南校区"};
    private Spinner school;
    private ArrayAdapter adapter;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_editor);
        setBtn = (Button) findViewById(R.id.set_btn);
        setBtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent in = new Intent(EditorActivity.this, SetActivity.class);
                startActivity(in);
            }
        });

        //设置RadioGroup默认选中项
    radioGroup = (RadioGroup) findViewById(R.id.gender_r);
        radioGroup.check(radioGroup.getChildAt(0).getId());




        //获取下拉列表
        school=(Spinner)findViewById(spinner);
        //创建
        adapter = new ArrayAdapter(this,R.layout.my_spinner_item,allArea);
        //将adapter添加到spinner中
        school.setAdapter(adapter);

//        //设置下拉框
//        Spinner spinner = null;
//        spinner = (Spinner) this.findViewById(R.id.spinner);
//        ArrayAdapter adapter = new ArrayAdapter(this,
//                android.R.layout.simple_spinner_item, new String[]{"贵州大学北校区",
//                "贵州大学南校区", "贵州大学东校区", "贵州大学西校区"});
//        spinner.setSelection(1,true);
//
//        //设置下拉样式
//        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
//        spinner.setAdapter(adapter);




        /**
         * 获取属性
         */
        nickName= (EditText) findViewById(R.id.nickName_r);
        userName=(EditText)findViewById(R.id.userName_r);
        gender=(RadioGroup)findViewById(R.id.gender_r);

        registe=(Button)findViewById(R.id.registe_rr);
        reset=(Button)findViewById(R.id.reset_r);
        registe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg=validate(userName.getText().toString(),"用户名");
                Log.d("123",msg);
                if (!msg.equals("")){
                    Toast.makeText(EditorActivity.this,msg,Toast.LENGTH_SHORT).show();
                    return;
                }
                msg=validate(password.getText().toString(),"密码");
                if (!msg.equals("")){
                    Toast.makeText(EditorActivity.this,msg,Toast.LENGTH_SHORT).show();
                    return;
                }
                msg=validate(telphone.getText().toString(),"电话号码");
                if (!msg.equals("")){
                    Toast.makeText(EditorActivity.this,msg,Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.getText().toString().equals(cpassword.getText().toString())){
                    Toast.makeText(EditorActivity.this,"两次密码不一致！",Toast.LENGTH_SHORT).show();
                    return;
                }
            }
        });
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
}
