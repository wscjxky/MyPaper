package com.example.administrator.mypaper.db;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class ArticleDbCreator extends SQLiteOpenHelper {
    private Context mContext;
    public  static final String CREATE_ARTICLE="create table Article("
            + "id integer primary key autoincrement, "
            + "title text,"
            + "url text,"
            + "imgurl text)";

    public ArticleDbCreator(Context context, String name, SQLiteDatabase.CursorFactory factory, int version){
        super(context,name,factory,version);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_ARTICLE);
        Log.e("数据库创建成功"," ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists Article;");
        onCreate(db);
    }
}

