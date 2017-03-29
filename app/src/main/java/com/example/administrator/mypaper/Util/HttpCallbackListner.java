package com.example.administrator.mypaper.Util;

/**
 * Created by Administrator on 2017/3/27.
 */

public interface HttpCallbackListner {
    void onFinish(String response);
    void onError(Exception e);
}
