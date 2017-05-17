package com.example.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class SecondActivity extends AppCompatActivity {
    private Button se;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);
        se = (Button) findViewById(R.id.se_btn);
        se.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent in = new Intent(SecondActivity.this, SetActivity.class);
                startActivity(in);
            }
        });
    }
}
