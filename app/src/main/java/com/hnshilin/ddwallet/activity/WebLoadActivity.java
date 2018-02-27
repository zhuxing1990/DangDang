package com.hnshilin.ddwallet.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

import com.hnshilin.ddwallet.R;
import com.hnshilin.ddwallet.base.BaseActivity;
import com.hnshilin.ddwallet.jsinterface.AndroidAndJSInterface;
import com.hnshilin.ddwallet.log.LogUtil;
import com.hnshilin.ddwallet.manage.UrlManager;
import com.hnshilin.ddwallet.util.WebSettingsUtil;
import com.hnshilin.ddwallet.view.CircularProgress;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 网页加载
 * Created by zhuxi on 2017/8/22.
 */
public class WebLoadActivity extends BaseActivity {
    private static final String TAG = "WebLoadActivity";
    private WebView webload_webview;
    private CircularProgress webload_progress;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED,
                WindowManager.LayoutParams.FLAG_HARDWARE_ACCELERATED);
        setContentView(R.layout.activity_webload);
        webload_webview = (WebView) findViewById(R.id.webload_webview);
        webload_progress = (CircularProgress) findViewById(R.id.webload_progress);
        webload_progress.bringToFront();
        initData();
    }
    String getWebData = "";
    private void initData() {
        Intent intent = getIntent();
        if (intent.hasExtra("webdata")){
            getWebData = intent.getStringExtra("webdata");
            if (TextUtils.isEmpty(getWebData)){
                showToast("加载网页失败,请稍后再试！");
                finish();
            }else{
                LogUtil.i(TAG, "initData: get getWebData:"+getWebData);
                initWebView(webload_webview);
            }
        }

    }

    private void initWebView(final WebView webView) {
        webload_webview.addJavascriptInterface(new AndroidAndJSInterface(mcontext),"android");
        WebSettingsUtil.initWebView(webView,mcontext,getWebData);
        webView.setWebChromeClient(new WebChromeClient() {

            @Override
            public void onProgressChanged(WebView view, int newProgress) {
//                personal_progress.setProgress(newProgress);
                if (newProgress < 1) { // 加载中
                    LogUtil.i(TAG, "网页加载进度：" + newProgress);
                    webload_webview.requestFocus();
                    // notfy_webView.setVisibility(View.GONE);
                } else if (newProgress == 100) { // 网页加载完成
                    LogUtil.i(TAG, "网页加载进度：" + newProgress);
                    view.setVisibility(View.VISIBLE);
                    webload_webview.requestFocus();
                    webload_progress.setVisibility(View.GONE);
                    // notfy_webView.setVisibility(View.VISIBLE);
                }
                super.onProgressChanged(view, newProgress);
            }
          /**
            * 当WebView加载之后，返回 HTML 页面的标题 Title
            * @param view
            * @param title
            */
            @Override
            public void onReceivedTitle(WebView view, String title) {
                //判断标题 title 中是否包含有“error”字段，如果包含“error”字段，则设置加载失败，显示加载失败的视图
                if(!TextUtils.isEmpty(title)&&title.toLowerCase().contains("error")){
                    webload_webview.loadUrl(UrlManager.NotFondUrl);
                }
            }
        });

    }

    /**
     * 微信登录成功后回调JS方法
     */
    public void wxLoginSuccessCallbackJS(JSONObject data) throws Exception {
       LogUtil.i(TAG,"login data===" + data.toString());
        // 因为该方法在 Android 4.4 以上版本才可使用，所以使用时需进行版本判断
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webload_webview.evaluateJavascript("javascript:WXloginSuccess(" + data + ")", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    //此处为 js 返回的结果
                }
            });
        } else {
            webload_webview.loadUrl("javascript:WXloginSuccess(" + data + ")");
        }
    }
    /**
     * 微信绑定手机号回调JS方法
     */
    public void wxBindingPhoneCallbackJS(String wxOpenid, String nickname, String headimgurl) {
        LogUtil.i(TAG,"android version :" + Build.VERSION.SDK_INT);
        // 因为该方法在 Android 4.4 以上版本才可使用，所以使用时需进行版本判断
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            webload_webview.evaluateJavascript("javascript:toBindingPhone('" + wxOpenid + "', '" + nickname + "', '" + headimgurl + "')", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    //此处为 js 返回的结果
                    LogUtil.i(TAG,"wxBindingPhoneCallbackJS result=" + value);
                }
            });
        } else {
            String url = "javascript:toBindingPhone('" + wxOpenid + "', '" + nickname + "', '" + headimgurl + "')";
            webload_webview.loadUrl(url);
        }
    }
    /**
     * 银联支付回调JS方法
     */
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        /*************************************************
         * 步骤3：处理银联手机支付控件返回的支付结果
         ************************************************/
        if (data == null) {
            return;
        }
        String msg = "";
        AlertDialog.Builder builder = new AlertDialog.Builder(mcontext);
        builder.setTitle("支付结果通知");
        builder.setInverseBackgroundForced(true);
        // builder.setCustomTitle();
        builder.setNegativeButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        /*
         * 支付控件返回字符串:success、fail、cancel 分别代表支付成功，支付失败，支付取消
         */
        String str = data.getExtras().getString("pay_result");
        if (str.equalsIgnoreCase("success")) {
            // 支付成功后，extra中如果存在result_data，取出校验
            // result_data结构见c）result_data参数说明
            if (data.hasExtra("result_data")) {
                String result = data.getExtras().getString("result_data");
                try {
                    JSONObject resultJson = new JSONObject(result);
                    String sign = resultJson.getString("sign");
                    String dataOrg = resultJson.getString("data");
                    // 验签证书同后台验签证书
                    // 此处的verify，商户需送去商户后台做验签
                    verify(dataOrg, sign);
                } catch (JSONException e) {

                }
            } else {
                //未收到签名信息
                //建议通过商户后台查询支付结果
                msg = "支付成功！";
            }
        } else if (str.equalsIgnoreCase("fail")) {
            msg = "支付失败！";
            builder.setMessage(msg);
            builder.create().show();
        } else if (str.equalsIgnoreCase("cancel")) {
            msg = "用户取消了支付";
            builder.setMessage(msg);
            builder.create().show();
        }

    }
    /**
     * 银联支付成功后
     */
    private void verify(String msg, String sign64) {
        //从后台校验
        String url = UrlManager.BASE_PATH + UrlManager.YL_verify;
        OkGo.post(url).tag(this)
                .params("msg", msg)
                .params("sign64", sign64)
                .execute(new StringCallback() {
                    @Override
                    public void onSuccess(final String s, Call call, Response response) {
                        AlertDialog.Builder builder1 = new AlertDialog.Builder(mcontext);
                        builder1.setTitle("支付结果通知");
                        builder1.setInverseBackgroundForced(true);
                        builder1.setNegativeButton("确定", new DialogInterface.OnClickListener()
//
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                                webload_webview.loadUrl("javascript:PaySuccess()");
                            }
                        });
                        try {
                            //请求成功回调
                            if (!TextUtils.isEmpty(s)) {
                                LogUtil.i(TAG, "onSuccess: s" + s);
                                JSONObject json = new JSONObject(s);
                                if (json.has("code")) {
                                    int code = json.getInt("code");
                                    switch (code) {
                                        case 0:
                                            builder1.setMessage("支付成功");
                                            builder1.create().show();
                                            break;
                                        default:
                                            builder1.setMessage("支付失败");
                                            builder1.create().show();
                                            break;
                                    }
                                }
                            }

                        } catch (Exception e) {
                            Toast.makeText(mcontext, "数据解析异常", Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onError(Call call, Response response, Exception e) {
                        super.onError(call, response, e);
                        AlertDialog.Builder builder2 = new AlertDialog.Builder(mcontext);
                        builder2.setTitle("支付结果通知");
                        builder2.setInverseBackgroundForced(true);
                        builder2.setNegativeButton("确定", new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                dialog.dismiss();
                            }
                        });
                        builder2.setMessage("支付失败");
                        builder2.create().show();
                    }
                });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        LogUtil.i(TAG, "keyCode:" + keyCode);
        if (keyCode == KeyEvent.KEYCODE_BACK && webload_webview.canGoBack()) {  //表示按返回键
            LogUtil.i(TAG, "onKeyDown: url:"+webload_webview.getUrl());
            if (webload_webview.getUrl().contains(getWebData)){
                return super.onKeyDown(keyCode, event);
            }else if (webload_webview.getUrl().contains(UrlManager.NotFondUrl)){
                return super.onKeyDown(keyCode, event);
            }
            webload_webview.goBack();   //后退
            //webview.goForward();//前进
            return true;    //已处理
        }
//        else if(keyCode == KeyEvent.KEYCODE_BACK){
//            return false;
//        }
        return super.onKeyDown(keyCode, event);

    }
}
