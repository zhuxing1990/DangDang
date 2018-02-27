package com.hnshilin.ddwallet.base;

import android.app.Application;

import com.lzy.okgo.OkGo;
import com.lzy.okgo.model.HttpHeaders;

/**
 * Created by zhuxi on 2017/3/29.
 */
public class MyApplication extends Application{
    private static final String TAG = "MyApplication";
    @Override
    public void onCreate() {
        super.onCreate();
        OkGo.init(this);
        OkGo.getInstance().setRetryCount(3);
        HttpHeaders headers = new HttpHeaders();
        headers.put("Connection","close");
        OkGo.getInstance().addCommonHeaders(headers);
    }
}
