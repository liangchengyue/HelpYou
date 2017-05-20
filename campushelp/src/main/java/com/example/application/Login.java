package com.example.application;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.util.Util;

import static android.R.attr.name;

public class Login extends AppCompatActivity {

    private EditText username;
    private EditText password;
    private Button login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        Util.allActiveActivities.add(this);
        username= (EditText) findViewById(R.id.userName);
        password= (EditText) findViewById(R.id.password1);
        login= (Button) findViewById(R.id.login);


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userna = username.getText().toString().trim();
                String psw =password.getText().toString().trim();
                if (userna.equals("")){
                    Toast.makeText(Login.this,"用户名不能为空！",Toast.LENGTH_SHORT).show();
                    return;
                }
                if (psw.equals("")){
                    Toast.makeText(Login.this,"密码不能为空！",Toast.LENGTH_SHORT).show();
                    return;
                }

            }
        });


    }
    protected void onDestroy(){
        Util.allActiveActivities.remove(this);
        super.onDestroy();
    }
}
