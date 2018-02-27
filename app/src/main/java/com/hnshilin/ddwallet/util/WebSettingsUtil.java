package com.hnshilin.ddwallet.util;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.webkit.SslErrorHandler;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.hnshilin.ddwallet.Listener.MyWebViewDownLoadListener;
import com.hnshilin.ddwallet.log.LogUtil;
import com.hnshilin.ddwallet.manage.UrlManager;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.FileCallback;

import java.io.File;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by zhuxi on 2017/8/26.
 */
public class WebSettingsUtil {
    private static final String TAG = "WebSettingsUtil";
    @SuppressLint({ "JavascriptInterface", "SetJavaScriptEnabled" })
    public static void initWebView(final WebView webview, final Activity activity, final String webLoadUrl){
        WebSettings settings = webview.getSettings();
        // 支持js
        settings.setJavaScriptEnabled(true);
        // 设置字符编码
        settings.setDefaultTextEncodingName("GBK");
        // 启用支持javascript
        settings.setJavaScriptEnabled(true);
        // 设置可以支持缩放
//        settings.setBuiltInZoomControls(true);
        settings.setLightTouchEnabled(true);
        settings.setSupportZoom(true);


        // 开启DOM缓存，开启LocalStorage存储（html5的本地存储方式）
        settings.setDomStorageEnabled(true);
        settings.setDatabaseEnabled(true);
        // Set cache size to 8 mb by default. should be more than enough
        settings.setAppCacheMaxSize(1024*1024*8);
        // This next guide_one is crazy. It's the DEFAULT location for your app's cache
        // But it didn't work for me without this line.
        // UPDATE: no hardcoded path. Thanks to Kevin Hawkins
        String appCachePath = activity.getCacheDir().getAbsolutePath();
        settings.setAppCachePath(appCachePath);
        settings.setAllowFileAccess(true);
        settings.setAppCacheEnabled(true);

        settings.setSavePassword(false);
        settings.setSaveFormData(false);
//        settings.setUseWideViewPort(true);
//        settings.setLoadWithOverviewMode(true);

//        settings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.SINGLE_COLUMN);
        // 不使用缓存，只从网络获取数据.
        settings.setCacheMode(WebSettings.LOAD_NO_CACHE);
//        settings.setCacheMode(WebSettings.LOAD_DEFAULT);
        // settings.setLoadWithOverviewMode(true);
        // 支持JS交互
        settings.setJavaScriptCanOpenWindowsAutomatically(true);
        webview.setWebViewClient(new WebViewClient() {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
                webview.requestFocus();
                LogUtil.i(activity.getLocalClassName(), "网页加载中");
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                LogUtil.i(activity.getLocalClassName(), "网页加载结束");
                        webview.setVisibility(View.VISIBLE);
            }
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                    view.loadUrl(url);
                return true;
            }
            public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
                // handler.cancel(); // Android默认的处理方式
                handler.proceed(); // 接受所有网站的证书
                // handleMessage(Message msg); // 进行其他处理
            }
            public void onReceivedError(WebView view, int errorCode, String description, String failingUrl) {
                webview.loadUrl(UrlManager.NotFondUrl+"?resload="+webLoadUrl);
            }

        });

        webview.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int permission = ActivityCompat.checkSelfPermission(activity,Manifest.permission.WRITE_EXTERNAL_STORAGE);
                boolean hasPermission = permission== PackageManager.PERMISSION_GRANTED;
                if (!hasPermission){
                    LogUtil.i(TAG, "onCreate: not permission");
                    PermissionsUtils.verifyStoragePermissions(activity);
                }
                WebView.HitTestResult result = ((WebView) v).getHitTestResult();
                int type = result.getType();
                //Log.d("line52",type+"=="+WebView.HitTestResult.IMAGE_TYPE);

                if (type == WebView.HitTestResult.IMAGE_TYPE) {
                    final String saveImgUrl = result.getExtra();
                    LogUtil.i(activity.getLocalClassName(),"====share=====saveImgUrl:" + saveImgUrl);
                    if (saveImgUrl.indexOf("http://bshare.optimix.asia") != -1) {//二维码文件

                        OkGo.post(saveImgUrl).tag(this).execute(new FileCallback("shilin.jpg") {
                            @Override
                            public void onSuccess(final File file, Call call, Response response) {
//                                MediaStore.Images.Media.insertImage(mContext.getContentResolver(), bitmap, "title", "description");
                                AlertDialog.Builder builder = new AlertDialog.Builder(activity);
                                builder.setMessage("确认分享？");
                                builder.setTitle("当当钱包提醒!");
                                builder.setPositiveButton("确认", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        LogUtil.i(activity.getLocalClassName(),"====share=====saveImgUrl:" + saveImgUrl);
//                                        String imageUri = insertImageToSystem(activity, file.getAbsolutePath());
                                        Intent intent = new Intent(Intent.ACTION_SEND);
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        intent.setType("image/*");
                                        intent.putExtra(Intent.EXTRA_STREAM, Uri.fromFile(file));
                                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                        activity.startActivity(Intent.createChooser(intent, "分享图片"));

                                    }
                                });
                                builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {

                                    @Override
                                    public void onClick(DialogInterface dialog, int which) {
                                        dialog.dismiss();
                                    }
                                });

                                builder.show();
                            }

                            @Override
                            public void onError(Call call, Response response, Exception e) {
                                super.onError(call, response, e);
                                LogUtil.i(TAG, "onError: 下载图片失败");
                            }
                        });


                    }
                }
                return false;
            }
        });
        webview.setDownloadListener(new MyWebViewDownLoadListener(activity));
        webview.loadUrl(webLoadUrl);
    }

}
