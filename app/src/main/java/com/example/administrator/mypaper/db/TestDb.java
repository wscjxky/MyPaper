package com.example.administrator.mypaper.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.widget.Toast;

import junit.framework.Test;

/**
 * Created by Administrator on 2017/3/27.
 */

public class TestDb extends SQLiteOpenHelper{
    public static final String CREATE_BOOK="create table Book("+
            "id integer primary key autoincrement,"
            + "title text)";
    private Context mContext;
    public TestDb(Context context, String name, SQLiteDatabase.CursorFactory factory,int version){
        super(context,name,factory,version);
        mContext =context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.execSQL("drop table if exists Book");
        db.execSQL(CREATE_BOOK);
        Toast.makeText(mContext,"ok",Toast.LENGTH_LONG).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Book");
        onCreate(db);
    }
}
