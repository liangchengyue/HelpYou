package com.example.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class MyevaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.my_prder_list_lss);

    }
    /*public void btnclick(View v){
        Intent intent = new Intent(MyevaActivity.this,EvaluationActivity.class);
        startActivity(intent);
    }*/
}
