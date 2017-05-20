package com.example.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

import com.example.util.Util;

public class PasswordActivity extends AppCompatActivity {
    private Button ps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.allActiveActivities.add(this);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_password);
        ps = (Button) findViewById(R.id.ps_btn);
        ps.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent in = new Intent(PasswordActivity.this, SetActivity.class);
                startActivity(in);
            }
        });
    }
    protected void onDestroy(){
        Util.allActiveActivities.remove(this);
        super.onDestroy();
    }
}
