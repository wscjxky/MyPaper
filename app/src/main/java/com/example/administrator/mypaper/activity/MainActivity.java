package com.example.administrator.mypaper.activity;

import android.Manifest;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.administrator.mypaper.R;
import com.example.administrator.mypaper.Util.HttpCallbackListner;
import com.example.administrator.mypaper.Util.HttpUtil;
import com.example.administrator.mypaper.Util.Utility;
import com.example.administrator.mypaper.db.ArticleDb;
import com.example.administrator.mypaper.db.TestDb;
import com.example.administrator.mypaper.model.Article;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import kr.co.namee.permissiongen.PermissionGen;

public class MainActivity extends AppCompatActivity {
    private TestDb testDbOpener;
    public static final  int LEVEL_ARTICLE=0;
    private int currentLevel;
    private List<Article> articleList;
    private Article selectedArticle;
    private TextView titleText;
    private ListView listView;
    private ArrayAdapter<String > adapter;
    private ArticleDb articleDb;
    private List<String> dataList=new ArrayList<String>();

    private SwipeRefreshLayout swipeRefreshLayout;
    private boolean STAGE=true;
    public static  int PAGE=1;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        listView = (ListView) findViewById(R.id.list_view);


        //handler
        adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,dataList);
        listView.setAdapter(adapter);
        articleDb = ArticleDb.getInstance(this);
        swipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.swipeLayout);
        swipeRefreshLayout.setColorSchemeResources(android.R.color.holo_blue_light, android.R.color.holo_red_light, android.R.color.holo_orange_light, android.R.color.holo_green_light);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                PAGE+=1;
                queryFromSever();
            }
        });
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int index, long arg3) {
               if(currentLevel ==LEVEL_ARTICLE){
                   selectedArticle=articleList.get(index);
                   Log.e("url",selectedArticle.getTitle());
                   Log.e("url",selectedArticle.getImgurl());
                   Intent intent = new Intent(MainActivity.this,ArticleActivity.class);
                   intent.putExtra("imgUrl", selectedArticle.getImgurl());
                   intent.putExtra("url", selectedArticle.getUrl());
                   MainActivity.this.startActivity(intent);//启动Activity
                   Log.e("当前",index+"");
               }
            }
        });

//        articleDb=new TestDb(MainActivity.this,"Book.db",null,2);
//        SQLiteDatabase testDb=testDbOpener.getWritableDatabase();
//        ContentValues values=new ContentValues();
//        values.put("title","我去啊");
//        testDb.insert("Book",null,values);
//        Cursor cursor = testDb.query("Book",null,null,null,null,null,null);
//        if(cursor.moveToFirst()){
//            do{
//                String title=cursor.getString(cursor.getColumnIndex("title"));
//                Log.e("Main","book is"+title);
//                titleText.setText(title);
//            }
//            while (cursor.moveToNext());
//        }
//        cursor.close();

//        adapter = new ArrayAdapter<String>(MainActivity.this,android.R.layout.simple_list_item_1,dataList);
//
//        listView.setAdapter(adapter);
//        articleDb = ArticleDb.getInstance(this);
//        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> arg0, View view, int index, long arg3) {
//               if(currentLevel ==LEVEL_ARTICLE){
//                   selectedArticle=articleList.get(index);
//               }
//            }
//        });
//        if(STAGE){
//            queryFromSever(1);
//            STAGE=false;
//
//        }

    }
    private void queryArticle(){
        articleList=articleDb.loadArticle();
        if(articleList.size()>0) {
            for (Article article : articleList) {
                dataList.add(article.getTitle());
                Log.e("显示成功", article.getTitle());
            }
            Collections.reverse(dataList);
            adapter.notifyDataSetChanged();
            listView.setSelection(0);
            swipeRefreshLayout.setRefreshing(false);
            Log.e("显示成功", "asd" + " ");
            currentLevel = LEVEL_ARTICLE;
        }
        else {
            Toast.makeText(this,"无法加载信息",Toast.LENGTH_LONG).show();
        }

//        }h
//        else {
//            Log.e("显示错误",3+" ");
//            queryFromSever("article");
//        }
    }
    private void queryFromSever(){
        String address;
        address="http://wangyi.butterfly.mopaasapp.com/news/api?type=war&page="+PAGE+"&limit=5" ;

        HttpUtil.sendHttpRequest(address, new HttpCallbackListner() {
            @Override
            public void onFinish(String response) {
                boolean result=false;
                result= Utility.handleArticleResponse(articleDb,response);
                Log.e("处理将结果",result+"");
                if(result){
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {

                            queryArticle();

                        }
                    });
                }
            }
            @Override
            public void onError(Exception e) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,"错误",Toast.LENGTH_LONG).show();
                    }
                });
            }
        });
    }
    private void requestPermission(){
        PermissionGen.with(MainActivity.this)
                .addRequestCode(100)
                .permissions(
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.MOUNT_UNMOUNT_FILESYSTEMS,
                        Manifest.permission.INTERNET)
                .request();
    }
}
