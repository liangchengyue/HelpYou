package com.example.application;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.example.util.Util;

public class EvaluationActivity extends AppCompatActivity {
    private Button eveOut;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Util.allActiveActivities.add(this);
        setContentView(R.layout.activity_evaluation);
        eveOut= (Button) findViewById(R.id.eve_out);
        eveOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }
    protected void onDestroy(){
        Util.allActiveActivities.remove(this);
        super.onDestroy();
    }
}
