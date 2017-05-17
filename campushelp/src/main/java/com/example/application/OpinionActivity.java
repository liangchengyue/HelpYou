package com.example.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class OpinionActivity extends AppCompatActivity {
    private Button op;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_opinion);
        op = (Button) findViewById(R.id.op_btn);
        op.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent in = new Intent(OpinionActivity.this, SetActivity.class);
                startActivity(in);
            }
        });
    }
}
