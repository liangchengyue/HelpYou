package com.example.util;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import static android.R.attr.id;

/**
 * Created by 谭超 on 2017/5/16.
 */

public class DBuitl extends SQLiteOpenHelper{

    private static final String DB_NAME = "test.db";
    private static final int DB_VERSION = 1;

    public DBuitl(Context ctx){

        super(ctx,DB_NAME,null,DB_VERSION);
    }

    public DBuitl(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }


    //创建用户表
    @Override
    public void onCreate(SQLiteDatabase db) {
        createUser_table(db);
        createLogintable(db);
    }


    public void createUser_table(SQLiteDatabase db) {
        String user_table = "create table usertable( "+
                "id integer primary key,"+
                "password integer"+
                "nickname text,"+
                "tel text"+
                "sex integer"+
                "school text"+
                "email text"+
                " )";
        db.execSQL(user_table);
    }


    //创建登陆时输入的帐户和用户名表
    public void createLogintable(SQLiteDatabase db){

        String logintable = "create table logintable("+
                 "userid integer primary key,"+
                  "password integer"+
                ")";
        db.execSQL(logintable);
    }



    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
