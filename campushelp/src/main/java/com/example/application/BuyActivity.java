package com.example.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.util.Util;

public class BuyActivity extends AppCompatActivity {
    //返回个人页面
    private Button Breturn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.allActiveActivities.add(this);
        setContentView(R.layout.activity_buy);

        Breturn= (Button) findViewById(R.id.B_return);
        Breturn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent buy = new Intent(BuyActivity.this,MainActivity.class);
                startActivity(buy);
            }
        });
    }
    protected void onDestroy(){
        Util.allActiveActivities.remove(this);
        super.onDestroy();
    }
}
