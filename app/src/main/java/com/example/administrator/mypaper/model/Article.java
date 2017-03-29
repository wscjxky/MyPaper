package com.example.administrator.mypaper.model;


public class Article {
    private int id;
    private String title;
    private String imgurl;
    private String url;
    public int getId(){
        return id;
    }
    public void setId(int id){
        this.id= id;
    }
    public String getTitle(){
        return title;
    }
    public void setTitle(String title){
        this.title= title;
    }
    public String getImgurl(){
        return imgurl;
    }
    public void setImgurl(String imgurl){
        this.imgurl= imgurl;
    }
    public String getUrl(){
        return url;
    }
    public void setUrl(String url){
        this.url= url;
    }
}
