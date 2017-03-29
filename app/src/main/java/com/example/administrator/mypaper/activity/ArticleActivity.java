package com.example.administrator.mypaper.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ImageView;

import com.example.administrator.mypaper.R;
import com.squareup.picasso.Picasso;

/**
 * Created by Administrator on 2017/3/30.
 */

public class ArticleActivity extends AppCompatActivity {
    private WebView webView;
    private ImageView imageView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.article_info);
        imageView=(ImageView) findViewById(R.id.view_image) ;
        webView=(WebView)findViewById(R.id.view_article) ;
        Intent intent = this.getIntent();
        String imgUrl = intent.getStringExtra("imgUrl");
        String url = intent.getStringExtra("url");
        Log.e("url",url);
        Log.e("imgUrl",imgUrl);
        Picasso.with(this).load(imgUrl).into(imageView);
        webView.loadUrl(url);
        webView.setWebViewClient(new WebViewClient() {
            public boolean shouldOverrideUrlLoading(WebView view, String url)
            { //  重写此方法表明点击网页里面的链接还是在当前的webview里跳转，不跳到浏览器那边
                view.loadUrl(url);
                return true;
            }
        });

}
}
