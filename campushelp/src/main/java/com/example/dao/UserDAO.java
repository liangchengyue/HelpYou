package com.example.dao;

import android.database.sqlite.SQLiteDatabase;

import java.util.Map;

/**
 * Created by TC1016 on 2017/5/16.
 */

public class UserDAO {
    public void insertUser(SQLiteDatabase db, Map<String,Object> map){
            String sql= "insert into User(id,password,nickname,tel,sex,school,email) values(?,?,?,?,?,?,?)";
            db.execSQL(sql,new Object[]{map.get("id"),map.get("password")});
            db.execSQL(sql,new Object[]{map.get("nikename")});
            db.execSQL(sql,new Object[]{map.get("tel")});
            db.execSQL(sql,new Object[]{map.get("sex")});
            db.execSQL(sql,new Object[]{map.get("school")});
            db.execSQL(sql,new Object[]{map.get("email")});

    }

}
