package com.example.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class LssueActivity extends AppCompatActivity {
    //返回个人页面
    private Button Lreturn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lssue);

        Lreturn = (Button) findViewById(R.id.L_return);
        Lreturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent lssue = new Intent(LssueActivity.this,MainActivity.class);
                startActivity(lssue);
            }
        });
    }
}
