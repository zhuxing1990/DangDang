package com.hnshilin.ddwallet.manage;

import android.app.Activity;
import android.text.TextUtils;

import com.hnshilin.ddwallet.base.Configs;
import com.hnshilin.ddwallet.log.LogUtil;
import com.hnshilin.ddwallet.util.DevicesUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.PostRequest;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by zhuxi on 2017/9/6.
 */
public class AccessProdManager {
    private static final String TAG = "AccessProdManager";
    public static void InsertByClickProduct(Activity context, int productId){
        LogUtil.i(TAG, "InsertByClickProduct: productId:"+productId);
        try {
            PostRequest post = OkGo.post(UrlManager.BaseUrl + UrlManager.InsertByClickProduct).tag(context)
                    .params("productId",productId).params("systemType",Configs.systemType);
            int userId = Configs.getUserId(context);
            if (userId!=-10){
                post.params("userId",userId);
            }
            String pesudoUniqueID = DevicesUtils.getPesudoUniqueID();
            if (!TextUtils.isEmpty(pesudoUniqueID)){
                LogUtil.i(TAG, "InsertByClickProduct: pesudoUniqueID:"+pesudoUniqueID);
                post.params("DeviceInfo",pesudoUniqueID);
            }
            post.execute(new StringCallback() {
                @Override
                public void onSuccess(String s, Call call, Response response) {
                    LogUtil.i(TAG, "onSuccess: s:"+s);
                }

                @Override
                public void onError(Call call, Response response, Exception e) {
                    super.onError(call, response, e);
                    LogUtil.i(TAG, "onError: ");
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public static void InsertByClickApply(Activity context, int productId){
        LogUtil.i(TAG, "InsertByClickApply: productId:"+productId);
        try {
            PostRequest post = OkGo.post(UrlManager.BaseUrl + UrlManager.InsertByClickApply).tag(context)
                    .params("productId",productId).params("systemType",Configs.systemType);
            int userId = Configs.getUserId(context);
            if (userId!=-10){
                post.params("userId",userId);
            }
            String pesudoUniqueID = DevicesUtils.getPesudoUniqueID();
            if (!TextUtils.isEmpty(pesudoUniqueID)){
                LogUtil.i(TAG, "InsertByClickApply: pesudoUniqueID:"+pesudoUniqueID);
                post .params("DeviceInfo",pesudoUniqueID);
            }
            post.execute(new StringCallback() {
                @Override
                public void onSuccess(String s, Call call, Response response) {
                    LogUtil.i(TAG, "onSuccess: s:"+s);
                }

                @Override
                public void onError(Call call, Response response, Exception e) {
                    super.onError(call, response, e);
                    LogUtil.i(TAG, "onError: ");
                }
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static void InsertByCenterApp(Activity context){
        PostRequest post = OkGo.post(UrlManager.BaseUrl + UrlManager.InsertByCenterApp).tag(context).params("systemType",Configs.systemType);
        String pesudoUniqueID = DevicesUtils.getPesudoUniqueID();
        if (!TextUtils.isEmpty(pesudoUniqueID)){
            LogUtil.i(TAG, "InsertByCenterApp: pesudoUniqueID:"+pesudoUniqueID);
            post .params("DeviceInfo",pesudoUniqueID);
        }
        post.execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                LogUtil.i(TAG, "onSuccess: s:"+s);
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                LogUtil.i(TAG, "onError: ");
            }
        });
    }
}
