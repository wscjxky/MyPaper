package com.example.administrator.mypaper.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.administrator.mypaper.model.Article;

import java.util.ArrayList;
import java.util.List;


public class ArticleDb {
    public static final String DB_NAME="Articles";

    public static final  int VERSION=10;

    private static final String ARTICLETABLE="article";

    private static ArticleDb articleDb;

    private SQLiteDatabase db;

    private ArticleDb(Context context){
        ArticleDbCreator dbCreator= new ArticleDbCreator(context,DB_NAME,null,VERSION);
        Log.e("数据库创建成功"," ");
        db=dbCreator.getWritableDatabase();

    }
    public synchronized static ArticleDb getInstance(Context context){
        if(articleDb==null){
            articleDb=new ArticleDb(context);
        }
        return articleDb;
    }
    public void saveArticle(Article article){
        if(article !=null){
            ContentValues values=new ContentValues();
            values.put("title",article.getTitle());
            values.put("imgurl",article.getImgurl());
            values.put("url",article.getUrl());
            db.insert(ARTICLETABLE,null,values);
        }
    }
    public List<Article> loadArticle(){

        List<Article> list =new ArrayList<Article>();
        Cursor cursor=db.query(
                ARTICLETABLE,null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do{
                Article article=new Article();
                article.setId(cursor.getInt((cursor.getColumnIndex("id"))));
                article.setTitle(cursor.getString(cursor.getColumnIndex("title")));
                article.setImgurl(cursor.getString(cursor.getColumnIndex("imgurl")));
                article.setUrl(cursor.getString(cursor.getColumnIndex("url")));
                list.add(article);
            }
            while(cursor.moveToNext());}
        if(cursor!=null){
            cursor.close();
        }
        return list;
    }
}

