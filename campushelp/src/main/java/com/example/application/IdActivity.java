package com.example.application;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.util.Util;

import java.util.Map;

public class IdActivity extends AppCompatActivity {
    private TextView nameID;
    private TextView ID;
    private TextView sexID;
    private TextView shoolID;
    private Button out;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id);

        nameID = (TextView) findViewById(R.id.name_id);
        ID= (TextView) findViewById(R.id.id);
        sexID= (TextView) findViewById(R.id.sex_id);
        shoolID= (TextView) findViewById(R.id.school_id);
        out=(Button)findViewById(R.id.set_btn_id);
        Map<String,Object> map= Util.userinfo;
        nameID.setText(map.get("realName").toString());
        String idca=map.get("idCard").toString();
        String id=idca.substring(0,4)+"****"+idca.substring(idca.length()-4,idca.length());
        ID.setText(id);
        String gender=map.get("gender").toString();
        sexID.setText(gender);
        String school=map.get("school").toString();
        shoolID.setText(school);
        out.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent in = new Intent(IdActivity.this, SetActivity.class);
                startActivity(in);
            }
        });
    }
}
