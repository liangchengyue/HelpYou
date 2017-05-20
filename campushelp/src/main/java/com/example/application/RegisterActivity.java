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
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;


public class RegisterActivity extends AppCompatActivity {

    private Button logbtn;

    private EditText nickName;
    private EditText userName;
    private EditText password;
    private EditText cpassword;
    private RadioGroup gender;
    private EditText telphone;
    private Spinner school;
    private Button registe;
    private  Button reset;
    private String url;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_register);
        //设置下拉框
        Spinner spinner = null;
        spinner = (Spinner) this.findViewById(R.id.spinner);
        ArrayAdapter adapter = new ArrayAdapter(this,
                android.R.layout.simple_spinner_item, new String[]{"贵州大学北校区",
                "贵州大学南校区", "贵州大学东校区", "贵州大学西校区"});

        //设置下拉样式
        adapter.setDropDownViewResource(android.R.layout.simple_dropdown_item_1line);
        spinner.setAdapter(adapter);

        //跳转到登录的按钮
        logbtn = (Button) findViewById(R.id.log_btn);
        logbtn.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v){
                Intent in = new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(in);
            }
        });
        /**
         * 获取属性
         */
        nickName= (EditText) findViewById(R.id.nickName_r);
        userName=(EditText)findViewById(R.id.userName_r);
        password=(EditText)findViewById(R.id.password_r);
        cpassword=(EditText)findViewById(R.id.cpassword_r);
        gender=(RadioGroup)findViewById(R.id.gender_r);
        telphone=(EditText)findViewById(R.id.telphone_r);
        school=(Spinner)findViewById(R.id.spinner);
        registe=(Button)findViewById(R.id.registe_rr);
        reset=(Button)findViewById(R.id.reset_r);
        registe.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg=validate(userName.getText().toString(),"用户名");
                Log.d("123",msg);
            if (!msg.equals("")){
                Toast.makeText(RegisterActivity.this,msg,Toast.LENGTH_SHORT).show();
                return;
            }
                msg=validate(password.getText().toString(),"密码");
                if (!msg.equals("")){
                    Toast.makeText(RegisterActivity.this,msg,Toast.LENGTH_SHORT).show();
                    return;
                }
                msg=validate(telphone.getText().toString(),"电话号码");
                if (!msg.equals("")){
                    Toast.makeText(RegisterActivity.this,msg,Toast.LENGTH_SHORT).show();
                    return;
                }
                if (password.getText().toString().equals(cpassword.getText().toString())){
                    Toast.makeText(RegisterActivity.this,"两次密码不一致！",Toast.LENGTH_SHORT).show();
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

}
