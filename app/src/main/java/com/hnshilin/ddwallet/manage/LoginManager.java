package com.hnshilin.ddwallet.manage;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.ContentValues;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.view.View;

import com.google.gson.Gson;
import com.hnshilin.ddwallet.activity.WebLoadActivity;
import com.hnshilin.ddwallet.base.Configs;
import com.hnshilin.ddwallet.base.RxBus;
import com.hnshilin.ddwallet.log.LogUtil;
import com.hnshilin.ddwallet.mod.LoginInfoBean;
import com.hnshilin.ddwallet.mod.UserInfoBean;
import com.hnshilin.ddwallet.util.SharedPreferencesUtil;
import com.hnshilin.ddwallet.util.Utils;
import com.hnshilin.ddwallet.view.LoginDlalog;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by zhuxi on 2017/8/25.
 */
public class LoginManager {
    private static final String TAG = "LoginManager";
  public static  LoginManager loginManager;
    private static final Uri uri = Uri.parse("content://com.hnshilin.ddwallet.provider.login/login");
   public static LoginManager getInstance(){
       if (loginManager == null){
           synchronized (LoginManager.class){
               if (loginManager == null){
                   loginManager = new LoginManager();
               }
           }
       }
       return loginManager;
    }
    private LoginDlalog dialog;

    private String login_user="";
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public void startLogin(final Activity context){
        login_user = SharedPreferencesUtil.getStringValue(context,SharedPreferencesUtil.USER_ID,"");
        dialog  =   new LoginDlalog(context);
        dialog.builder().setCancelable(true).setCanceledOnTouchOutside(false)
                .setLoginOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        final String userName = dialog.getUserName().toString().trim();
                        final String password = dialog.getPassWord().toString().trim();
                        if (TextUtils.isEmpty(userName)){
                            Utils.showToast("请输入帐号",context);
                            return;
                        }
                        if (TextUtils.isEmpty(password)){
                            Utils.showToast("请输入密码",context);
                            return;
                        }
                        OkGo.post(UrlManager.BaseUrl+UrlManager.Login).tag(context).params("phoneNumber",userName).params("password",password).execute(new StringCallback() {
                            @Override
                            public void onSuccess(String s, Call call, Response response) {
                                LogUtil.i(TAG, "onSuccess: s:"+s);
                                if (!TextUtils.isEmpty(s))
                                try {
                                    JSONObject json = new JSONObject(s);
                                    if (json.has("code")){
                                        int code = json.getInt("code");
                                        switch (code){
                                            case 0:
                                                Utils.showToast("登录成功!",context);
                                                SharedPreferencesUtil.setStringValue(context,SharedPreferencesUtil.USER_ID,userName);
                                                LoginInfoBean loginBean = new Gson().fromJson(s,LoginInfoBean.class);
                                                if (loginBean!=null){
                                                    if (loginBean.getData()!=null&&loginBean.getData().size()!=0){
                                                        LoginInfoBean.DataBean dataBean = loginBean.getData().get(0);
                                                        InsertLoginData(context,userName,password,dataBean.getID());
                                                        SendUserInfo(dataBean.getID()+"",password);
                                                    }
                                                }
                                                dialog.cancel();
                                                break;
                                            default:
                                                if (json.has("message")){
                                                    Utils.showToast(json.getString("message"),context);
                                                }
                                                break;
                                        }
                                    }
                                }catch (Exception e){
                                    e.printStackTrace();
                                }
                            }

                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                super.onError(call, response, e);
                                Utils.showToast("登录失败",context);
                            }
                        });
                    }
                })
                .setRegisterOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Configs.intent = new Intent(context, WebLoadActivity.class);
                        Configs.intent.putExtra("webdata", UrlManager.BaseUrl+UrlManager.Register);
                        Configs.intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(Configs.intent);
                    }
                });
              dialog.setUserName(login_user);
        if (!context.isDestroyed())
              dialog.show();
    }
    public  void AutoMaticLogin(Activity context,String userName){
        if (TextUtils.isEmpty(userName)){
            LogUtil.i(TAG, "AutoMaticLogin: userName is null");
            return;
        }
        String password =  QueryPassword(context,userName);
        if (TextUtils.isEmpty(password)){
            Utils.showToast("自动登录失败",context);
        }else{
            startLogin(userName,password,context);
        }
    }

    public  void startLogin(final String userName,final String password,final Activity context) {
        OkGo.post(UrlManager.BaseUrl+UrlManager.Login).tag(context).params("phoneNumber",userName).params("password",password).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                LogUtil.i(TAG, "onSuccess: s:"+s);
                if (!TextUtils.isEmpty(s))
                try {
                    JSONObject json = new JSONObject(s);
                    if (json.has("code")){
                        int code = json.getInt("code");
                        switch (code){
                            case 0:
//                                Utils.showToast("登录成功!",context);
                                LogUtil.i(TAG, "onSuccess: 登录成功");
                                SharedPreferencesUtil.setStringValue(context,SharedPreferencesUtil.USER_ID,userName);
                                LoginInfoBean loginBean = new Gson().fromJson(s,LoginInfoBean.class);
                                if (loginBean!=null){
                                    if (loginBean.getData()!=null&&loginBean.getData().size()!=0){
                                        LoginInfoBean.DataBean dataBean = loginBean.getData().get(0);
                                        InsertLoginData(context,userName,password,dataBean.getID());
                                        SendUserInfo(dataBean.getID()+"",password);
                                    }
                                }else{
                                    LogUtil.i(TAG, "onSuccess: get login data is null");
                                }
                                break;
                            default:
                                if (json.has("message")){
                                    Utils.showToast(json.getString("message"),context);
                                }
                                break;
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                Utils.showToast("登录失败,服务器繁忙",context);
            }
        });
    }
    public static void getLoginInfo(String userName,String password,int user_id,Activity context){
        SharedPreferencesUtil.setStringValue(context,SharedPreferencesUtil.USER_ID,userName);
        InsertLoginData(context,userName,password,user_id);
//        UserInfoBean userInfoBean = new UserInfoBean();
//        userInfoBean.setCode(UserInfoBean.ReloadWeb);
//        RxBus.getInstance().post(userInfoBean);
    }
    private static void SendUserInfo(String userName, String password) {
        UserInfoBean userInfoBean = new UserInfoBean();
        userInfoBean.setCode(UserInfoBean.LoginCode);
        userInfoBean.setUser_name(userName);
        userInfoBean.setPassword(password);
        RxBus.getInstance().post(userInfoBean);
    }
    private static String QueryPassword(Activity context, String userName) {
        String password = "";
        String[] strings = new String[] { userName.trim() };
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, null,null , strings, null);
            while (cursor.moveToNext()){
                password = cursor.getString(cursor.getColumnIndex("password"));
                LogUtil.i(TAG, "QueryPassword: password:"+password);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor!=null)
                cursor.close();
        }
        return password;
    }
    public static int QueryUserId(Activity context, String userName) {
        int userId = -10;
        String[] strings = new String[] { userName.trim() };
        Cursor cursor = null;
        try {
            cursor = context.getContentResolver().query(uri, null,null , strings, null);
            while (cursor.moveToNext()){
                userId = cursor.getInt(cursor.getColumnIndex("user_id"));
                LogUtil.i(TAG, "QueryUserId: user_id:"+userId);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            if (cursor!=null)
                cursor.close();
        }
        return userId;
    }
    private static void InsertLoginData(Activity context,String userName,String password,int user_id){
        ContentValues values = new ContentValues();
        values.put("user_name",userName);
        values.put("password",password);
        values.put("user_id",user_id);
        values.put("login_time",System.currentTimeMillis());
        Uri insert = context.getContentResolver().insert(uri, values);
    }
    public static void DelectLoginData(Activity context){
        try {
            int delete = context.getContentResolver().delete(uri, null, null);
            LogUtil.i(TAG,"DelectLoginData delete:"+delete);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
