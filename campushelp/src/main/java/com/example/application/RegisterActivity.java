package com.example.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;


public class RegisterActivity extends AppCompatActivity {

    private Button logbtn;

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

    }



}
