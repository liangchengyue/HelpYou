package com.example.application;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class IdActivity extends AppCompatActivity {
    private TextView nameID;
    private TextView ID;
    private TextView sexID;
    private TextView shoolID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_id);

        nameID = (TextView) findViewById(R.id.name_id);
        ID= (TextView) findViewById(R.id.id);
        sexID= (TextView) findViewById(R.id.sex_id);
        shoolID= (TextView) findViewById(R.id.school_id);
    }
}
