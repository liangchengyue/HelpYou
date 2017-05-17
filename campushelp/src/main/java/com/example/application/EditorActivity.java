package com.example.application;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;

public class EditorActivity extends AppCompatActivity {
    private Button edo;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_PAN);
        setContentView(R.layout.activity_editor);
        edo = (Button) findViewById(R.id.edo_btn);
        edo.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent in = new Intent(EditorActivity.this, SetActivity.class);
                startActivity(in);
            }
        });
    }
}
