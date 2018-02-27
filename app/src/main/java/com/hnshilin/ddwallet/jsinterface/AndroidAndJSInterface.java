package com.hnshilin.ddwallet.jsinterface;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.webkit.JavascriptInterface;
import android.widget.Toast;

import com.hnshilin.ddwallet.R;
import com.hnshilin.ddwallet.alipay.AlipayManager;
import com.hnshilin.ddwallet.log.LogUtil;
import com.hnshilin.ddwallet.manage.LoginManager;
import com.hnshilin.ddwallet.manage.UpdateApkManager;
import com.hnshilin.ddwallet.util.PermissionsUtils;
import com.hnshilin.ddwallet.util.SharedPreferencesUtil;
import com.hnshilin.ddwallet.util.Utils;
import com.hnshilin.ddwallet.weixin.Constant;
import com.hnshilin.ddwallet.weixin.WeixinAuthLogin;
import com.hnshilin.ddwallet.weixin.share.ShareContent;
import com.hnshilin.ddwallet.weixin.share.WeixinShareManager;
import com.hnshilin.ddwallet.ylpay.YinLianPay;

import org.json.JSONObject;


/**
 * Created by Administrator on 2017/7/14 0014.
 */

public class AndroidAndJSInterface {
    private static final String TAG = "AndroidAndJSInterface";
    private Activity mContext;
    private WeixinShareManager mShareManager;
    private long clickTime = 0;

    public AndroidAndJSInterface(Activity context) {
        mContext = context;
    }

    // 定义JS需要调用的方法
    // 被JS调用的方法必须加入@JavascriptInterface注解
    @JavascriptInterface
    public void hello(String msg) {
        LogUtil.i(TAG,msg);
        LogUtil.i(TAG,"JS调用了Android的hello方法");
    }

    /**
     * 微信分享文本
     *
     * @param textContent
     */
    @JavascriptInterface
    public void shareWeixinText(String textContent) {
        LogUtil.i(TAG, "shareWeixinText: ");
        if (Utils.isFastDoubleClick(2000)){
            mShareManager = WeixinShareManager.getInstance(mContext);
            ShareContent mShareContent = mShareManager.getShareContentText(textContent);
            mShareManager.shareByWebchat(mShareContent, Constant.WECHAT_SHARE_TYPE_FRENDS);
        }
    }

    /**
     * 微信分享网页到朋友圈
     *
     * @param title
     * @param content
     * @param url
     */
    @JavascriptInterface
    public void wxShareWebPageToFriends(String title, String content, String url) {
        LogUtil.i(TAG, "wxShareWebPageToFriends: ");
        if (Utils.isFastDoubleClick(2000)){
            mShareManager = WeixinShareManager.getInstance(mContext);
            ShareContent mShareContent = mShareManager.getShareContentWebpag(title, content, url, R.drawable.ddqb_logo);
            mShareManager.shareByWebchat(mShareContent, Constant.WECHAT_SHARE_TYPE_FRENDS);
        }
    }

    /**
     * 微信分享网页到会话
     *
     * @param title
     * @param content
     * @param url
     */
    @JavascriptInterface
    public void wxShareWebPageToTalk(String title, String content, String url) {
        LogUtil.i(TAG, "wxShareWebPageToTalk: ");
        if (Utils.isFastDoubleClick(2000)){
            mShareManager = WeixinShareManager.getInstance(mContext);
            ShareContent mShareContent = mShareManager.getShareContentWebpag(title, content, url, R.drawable.ddqb_logo);
            mShareManager.shareByWebchat(mShareContent, Constant.WECHAT_SHARE_TYPE_TALK);
        }
    }

    /**
     * 微信登录
     */
    @JavascriptInterface
    public void wxAuthLogin() {
        LogUtil.i(TAG, "wxAuthLogin: ");
        if (Utils.isFastDoubleClick(2000)){
            WeixinAuthLogin mWeixinAuthLogin = new WeixinAuthLogin(mContext);
            mWeixinAuthLogin.sendWeixinLoginReq();
            Toast.makeText(mContext,"正在打开微信中,请稍候...",Toast.LENGTH_SHORT).show();
        }
    }


    /**
     * 银联支付
     */
    @JavascriptInterface
    public void yinlianPay(String txnAmt, String orderNo, String userId, int payType) {
        if (Utils.isFastDoubleClick(2000)){
            LogUtil.i(TAG, "yinlianPay: ");
            YinLianPay ylPay = new YinLianPay(mContext);
            ylPay.getTn(txnAmt, orderNo, userId, payType);
        }
//        if ((System.currentTimeMillis() - clickTime) > 2000) {
//          clickTime = System.currentTimeMillis();
//        }
    }
    @JavascriptInterface
    public void Alipay(String txnAmt,String orderNo,String userId,int payType){
        if (Utils.isFastDoubleClick(2000)){
//            Utils.showToast("txnAmt:"+txnAmt,mContext);
            LogUtil.i(TAG, "Alipay: ");
            AlipayManager.getInstance().GetOrderInfo(mContext,txnAmt,orderNo,userId,payType);
        }
    }
    /**
     * 用户登录
     */
    @JavascriptInterface
    public void sendMessageToJAVA(String jsonData) {
        LogUtil.i(TAG, "sendMessageToJAVA: json:"+jsonData);
//        Utils.showToast("json = "+jsonData,mContext);
        try {
            if (TextUtils.isEmpty(jsonData)){
                LogUtil.i(TAG, "sendMessageToJAVA: get jsonData is null");
                return;
            }else  if(jsonData.equals("undefined")){
                LogUtil.i(TAG, "sendMessageToJAVA: get jsonData failed ,jsonData is undefined");
                return;
            }
            JSONObject json = new JSONObject(jsonData);
            if (json.has("user_name")&& json.has("password")&&json.has("userId")){
                String user_name = json.getString("user_name");
                String password = json.getString("password");
                int userId = json.getInt("userId");
                LoginManager.getInstance().getLoginInfo(user_name,password,userId,mContext);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    /**
     * 关闭页面
     */
    @JavascriptInterface
    public void FinishActivity(){
        LogUtil.i(TAG, "FinishActivity: ");
        if (Utils.isFastDoubleClick(2000))mContext.finish();
    }
    /**
     * 注销
     */
    @JavascriptInterface
    public void LoginOut(){
        LogUtil.i(TAG, "LoginOut: ");
        if (Utils.isFastDoubleClick(1000)){
            SharedPreferencesUtil.RemoveKey(mContext,SharedPreferencesUtil.USER_ID);
            LoginManager.getInstance().DelectLoginData(mContext);
        }
    }

    /**
     * 版本检测
     */
    @JavascriptInterface
    public void VersionUpdate(){
        LogUtil.i(TAG, "VersionUpdate: ");
        if (Utils.isFastDoubleClick(2000)){
            int permission = ActivityCompat.checkSelfPermission(mContext, Manifest.permission.WRITE_EXTERNAL_STORAGE);
            boolean hasPermission = permission== PackageManager.PERMISSION_GRANTED;
            if (hasPermission){
                UpdateApkManager.getInstance().GetUpdateInfo(mContext);
            }else{
                LogUtil.i(TAG, "onCreate: not permission");
                PermissionsUtils.verifyStoragePermissions(mContext);
            }
        }
    }
}
