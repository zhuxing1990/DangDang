package com.hnshilin.ddwallet.sql;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.hnshilin.ddwallet.log.LogUtil;

/**
 * Created by zhuxi on 2017/7/28.
 */
public class LoginSqlite extends SQLiteOpenHelper {
    private static final String TAG = "LoginSqlite";
    private static final String DATABASE_NAME = "login.db";
    private static final int DATABASE_VERSION = 2;
    private String CreateTable = "create table login (_id integer not null primary key autoincrement ,user_name varchar,password varchar,user_id varchar,login_time varchar)";
    public LoginSqlite(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        LogUtil.i(TAG, "onCreate: sql upgrade");
        db.execSQL(CreateTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        LogUtil.i(TAG, "onUpgrade: sql upgrade");
        String sql = "drop table if exists login";
        db.execSQL(sql);
        db.execSQL(CreateTable);
    }

}
