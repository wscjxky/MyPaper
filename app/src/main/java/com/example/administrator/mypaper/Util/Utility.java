package com.example.administrator.mypaper.Util;

import android.text.TextUtils;
import android.util.Log;

import com.example.administrator.mypaper.db.ArticleDb;
import com.example.administrator.mypaper.model.Article;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Utility {
    public synchronized static boolean handleArticleResponse(ArticleDb articleDb,String response){
        if(!TextUtils.isEmpty(response)){

            try {
                JSONArray jsonArray = new JSONObject(response).getJSONArray("list");

                for(int i=0;i<jsonArray.length();i++){
                    Article article=new Article();
                    JSONObject jsonObject=(JSONObject)jsonArray.get(i);
                    String title=jsonObject.getString("title");
                    String imgurl=jsonObject.getString("imgurl");
                    String url=jsonObject.getString("docurl");
                    Log.e("title",title);
                    Log.e("url",url);
                    Log.e("imgurl",imgurl);
                    article.setTitle(title);
                    article.setImgurl(imgurl);
                    article.setUrl(url);
                    articleDb.saveArticle(article);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

            return true;
        }
        return false;

    }
}
