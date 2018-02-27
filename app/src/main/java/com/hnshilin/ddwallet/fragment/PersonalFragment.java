package com.hnshilin.ddwallet.fragment;

import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.widget.Toast;

import com.hnshilin.ddwallet.R;
import com.hnshilin.ddwallet.base.HeaderViewPagerFragment;
import com.hnshilin.ddwallet.base.RxBus;
import com.hnshilin.ddwallet.jsinterface.AndroidAndJSInterface;
import com.hnshilin.ddwallet.log.LogUtil;
import com.hnshilin.ddwallet.manage.UrlManager;
import com.hnshilin.ddwallet.mod.UserInfoBean;
import com.hnshilin.ddwallet.util.Utils;
import com.hnshilin.ddwallet.util.WebSettingsUtil;
import com.hnshilin.ddwallet.view.CircularProgress;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * 我的
 * Created by zhuxi on 2017/8/22.
 */
public class PersonalFragment extends HeaderViewPagerFragment {
    private static final String TAG = "PersonalFragment";
    public static PersonalFragment newInstance(){
        return new PersonalFragment();
    }
    private int count = 0;
    private WebView personal_webview;
    private CircularProgress personal_progress;

    private String PersonalUrl // 网页地址
            = UrlManager.BaseUrl+UrlManager.PersonalUrl;
//            ="http://192.168.0.222:8080"
//            ="http://192.168.0.177:8020"
//            +UrlManager.PersonalUrl;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_personal,container,false);
        personal_webview = (WebView) view.findViewById(R.id.personal_webview);
        personal_progress = (CircularProgress) view.findViewById(R.id.personal_progress);
        initWebView(personal_webview,PersonalUrl);
        initRx();
        return view;
    }
    @SuppressLint({ "JavascriptInterface", "SetJavaScriptEnabled" })
    private void initWebView(final WebView webview,String webLoadUrl) {
        webview.addJavascriptInterface(new AndroidAndJSInterface(getActivity()) ,"android");
        String versionName = Utils.getVersionName(getActivity());
        LogUtil.i(TAG, "onCreateView version:"+versionName);
        webLoadUrl+= "?versionName="+versionName;
        WebSettingsUtil.initWebView(webview,getActivity(),webLoadUrl);
        webview.setWebChromeClient(new WebChromeClient() {
            /**
             * 当WebView加载之后，返回 HTML 页面的标题 Title
             * @param view
             * @param title
             */
            @Override
            public void onReceivedTitle(WebView view, String title) {
                //判断标题 title 中是否包含有“error”字段，如果包含“error”字段，则设置加载失败，显示加载失败的视图
                if(!TextUtils.isEmpty(title)&&title.toLowerCase().contains("error")){
                    view.loadUrl(UrlManager.NotFondUrl);
                }
            }
            @Override
            public void onProgressChanged(WebView view, int newProgress) {
                if (newProgress == 100) {
                    personal_progress.setVisibility(View.GONE);
                    view.setVisibility(View.VISIBLE);
                    view.requestFocus();
                    if (view.getUrl().indexOf(UrlManager.BASE_PATH) == -1) { //外网
//                        personal_back.setVisibility(View.VISIBLE);
                    } else {
//                        personal_back.setVisibility(View.GONE);
                    }
                    //count=0时是首次进入程序,加载
                    if (count == 0) {
//                        personal_img.setVisibility(View.GONE);
                        view.setVisibility(View.VISIBLE);
                        count++;
                    }

                } else {
                    webview.requestFocus();
                    if (count == 0) {
                        view.setVisibility(View.GONE);
//                        personal_img.setVisibility(View.VISIBLE);
                    }
                }
                super.onProgressChanged(view, newProgress);
            }
        });

    }

    private Subscription subscribe;

    /**
     * RxBus 把要传的参数传到这来，通过判断CODE来区分
     */
    private void initRx() {
        subscribe = RxBus.getInstance().toObservable(UserInfoBean.class).filter(new Func1<UserInfoBean, Boolean>() {
            @Override
            public Boolean call(UserInfoBean userInfoBean) {
                return userInfoBean.getCode() == UserInfoBean.LoginCode
                        || userInfoBean.getCode() == UserInfoBean.WxLoginCode
                        || userInfoBean.getCode() == UserInfoBean.WxBindingPhone
                        || userInfoBean.getCode() == UserInfoBean.AlipayCode;
//                        || userInfoBean.getCode() == UserInfoBean.ReloadWeb;
            }
        }).subscribeOn(Schedulers.io())
          .observeOn(AndroidSchedulers.mainThread())
         .subscribe(new Subscriber<UserInfoBean>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                LogUtil.i(TAG, "RxBus error");
                e.printStackTrace();
                this.isUnsubscribed();
                initRx();
            }

            @Override
            public void onNext(UserInfoBean userInfoBean) {
//                    Utils.showToast(userInfoBean.toString(),getActivity());
                    try {
                        switch (userInfoBean.getCode()){
                            case UserInfoBean.LoginCode:
                                JSONObject json = new JSONObject();
                                json.put("user_name",userInfoBean.getUser_name());
                                json.put("password",userInfoBean.getPassword());
                                SendMessageToWeb(json.toString());
                                break;
                            case UserInfoBean.WxLoginCode:
                                wxLoginSuccessCallbackJS(userInfoBean.getWxJsonResult());
                                break;
                            case UserInfoBean.WxBindingPhone:
                                wxBindingPhoneCallbackJS(userInfoBean.getWxOpenid(),userInfoBean.getNickname(),userInfoBean.getHeadimgurl());
                                break;
//                            case UserInfoBean.ReloadWeb:
//                                personal_webview.loadUrl(PersonalUrl);
//                                break;//
                          case UserInfoBean.AlipayCode:

                               break;
                        }

                    }catch (Exception e){
                        e.printStackTrace();
                    }
            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (subscribe!=null&&!subscribe.isUnsubscribed()){
            subscribe.unsubscribe();
        }
    }



    /**
     * 发送数据到网页
     * @param data
     */
    public void SendMessageToWeb(String data){
        LogUtil.i(TAG, "SendMessageToWeb: data:"+data);
//        Utils.showToast("json = "+data,getActivity());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            personal_webview.evaluateJavascript("javascript:getAndroidData(" + data + ")", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    //此处为 js 返回的结果
                }
            });
        } else {
            personal_webview.loadUrl("javascript:getAndroidData(" + data + ")");
        }
        personal_webview.loadUrl(PersonalUrl);
    }
    /**
     * 微信登录成功后回调JS方法
     */
    public void wxLoginSuccessCallbackJS(JSONObject data) throws Exception {
        LogUtil.i(TAG,"login data===" + data.toString());
        // 因为该方法在 Android 4.4 以上版本才可使用，所以使用时需进行版本判断
//        Utils.showToast("loginSuccess data="+data.toString(),getActivity());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            personal_webview.evaluateJavascript("javascript:WXloginSuccess(" + data + ")", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    //此处为 js 返回的结果
                }
            });
        } else {
            personal_webview.loadUrl("javascript:WXloginSuccess(" + data + ")");
        }
    }
    /**
     * 微信绑定手机号回调JS方法
     */
    public void wxBindingPhoneCallbackJS(String wxOpenid, String nickname, String headimgurl) {
        LogUtil.i(TAG,"android version :" + Build.VERSION.SDK_INT);
        // 因为该方法在 Android 4.4 以上版本才可使用，所以使用时需进行版本判断
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            personal_webview.evaluateJavascript("javascript:toBindingPhone('" + wxOpenid + "', '" + nickname + "', '" + headimgurl + "')", new ValueCallback<String>() {
                @Override
                public void onReceiveValue(String value) {
                    //此处为 js 返回的结果
                    LogUtil.i(TAG,"wxBindingPhoneCallbackJS result=" + value);
                }
            });
        } else {
            String url = "javascript:toBindingPhone('" + wxOpenid + "', '" + nickname + "', '" + headimgurl + "')";

            personal_webview.loadUrl(url);
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
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
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
                AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
                builder1.setTitle("支付结果通知");
                builder1.setInverseBackgroundForced(true);
                builder1.setNegativeButton("确定", new DialogInterface.OnClickListener()
//
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        personal_webview.loadUrl("javascript:PaySuccess()");
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
                   Toast.makeText(getActivity(), "数据解析异常", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                AlertDialog.Builder builder2 = new AlertDialog.Builder(getActivity());
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
    public View getScrollableView() {
        return null;
    }
}
