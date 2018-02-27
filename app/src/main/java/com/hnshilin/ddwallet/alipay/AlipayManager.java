package com.hnshilin.ddwallet.alipay;

import android.Manifest;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.alipay.sdk.app.EnvUtils;
import com.alipay.sdk.app.PayTask;
import com.hnshilin.ddwallet.base.RxBus;
import com.hnshilin.ddwallet.log.LogUtil;
import com.hnshilin.ddwallet.manage.UrlManager;
import com.hnshilin.ddwallet.mod.AlipayResultBean;
import com.hnshilin.ddwallet.mod.UserInfoBean;
import com.hnshilin.ddwallet.util.Utils;
import com.hnshilin.ddwallet.ylpay.YinLianPay;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.PostRequest;

import org.json.JSONObject;

import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by zhuxi on 2017/9/25.
 */
public class AlipayManager {
    private static final String TAG = "AlipayManager";

    private static AlipayManager instance;
    private AlipayResultBean alipayResultBean;
    public static AlipayManager getInstance() {
        if (instance == null) {
            synchronized (AlipayManager.class) {
                if (instance == null) {
                    instance = new AlipayManager();
                }
            }
        }
        return instance;
    }
    private Activity activity;
    public AlipayManager (){
    }
    /** 支付宝支付业务：入参app_id */
    public static final String APPID = "2017091408726151";

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    public  void GetOrderInfo(final Activity activity,String txnAmt,String orderNo,String userId,int payType){
        this.activity = activity;
        alipayResultBean = new AlipayResultBean();
        try {
            if ("".equals(orderNo) && payType != 2) {
                orderNo = YinLianPay.getOrderId();
            }
            PostRequest postRequest = OkGo.post("http://192.168.0.188:8080"+ UrlManager.AddYLOrder).tag(activity);// 支付宝支付
            postRequest
                    .params("txnAmt",txnAmt)//"100"
                    .params("userId",userId)//"1062"
                    .params("money",txnAmt)//"100"
                    .params("orderNo",orderNo)//"201709251127361265"
                    .params("pay_type",2);
                    if (payType == 2) {
                        postRequest .params("orderName", "购买pos机")
                                .params("orderType", "2");
                    }else{
                        postRequest
                                .params("orderName", "升级为代理")
                                .params("orderType", "1");
                    }
            Utils.showToast("请求参数:"+postRequest.getParams().toString(),activity);
            alipayResultBean.setAlipayRequest_Info(postRequest.getParams().toString());
            postRequest.execute(new StringCallback() {
                @Override
                public void onSuccess(String s, Call call, Response response) {
                    LogUtil.i(TAG, "onSuccess: s:"+s);
                    alipayResultBean.setAlipayResult_Response(s);
                    try {
                        JSONObject json = new JSONObject(s);
                        if (json.has("code")){
                            int code = json.getInt("code");
                            LogUtil.i(TAG, "onSuccess: code:"+code);
                            switch (code){
                                case 0:
                                    LogUtil.i(TAG, "onSuccess: 获取支付信息成功");
                                    if (json.has("tn")){
                                        String tn = json.getString("tn");
                                        if (!TextUtils.isEmpty(tn)){
                                            getPermission(tn, activity);
                                        }else{
                                            alipayResultBean.setAlipayResult_CODE(code+"1");
                                            Utils.showToast("获取支付订单信息失败,订单信息异常",activity);
                                            LogUtil.i(TAG, "onSuccess: 获取支付订单信息失败,订单信息异常");
                                        }
                                    }else{
                                        alipayResultBean.setAlipayResult_CODE(code+"2");
                                        Utils.showToast("获取支付订单信息失败",activity);
                                        LogUtil.i(TAG, "onSuccess: 获取支付订单信息失败");
                                    }
                                    break;
                                default:
                                    alipayResultBean.setAlipayResult_CODE(code+"");
                                    if (json.has("message")){
                                        Utils.showToast(json.getString("message"),activity);
                                    }
                                    SendAlipayData();
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
                    LogUtil.i(TAG, "onError: ");
                    alipayResultBean.setAlipayResult_CODE("500");
                    alipayResultBean.setAlipayResult_Response("Reuqest Error");
                    SendAlipayData();
                    Utils.showToast("获取支付订单信息失败,网络异常",activity);
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    private void SendAlipayData() {
        Log.i(TAG, "SendAlipayData: alipayResultBean:"+alipayResultBean.toString());
        UserInfoBean userinfoBean = new UserInfoBean();
        userinfoBean.setCode(UserInfoBean.AlipayCode);
        userinfoBean.setAlipayResultBean(alipayResultBean);
        RxBus.getInstance().post(userinfoBean);
    }

    private void getPermission(String tn , Activity activity) {
        int permissionCheck = ActivityCompat.checkSelfPermission(activity, Manifest.permission.READ_PHONE_STATE);
        if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
            LogUtil.i(TAG, "getPermission: not permission");
            alipayResultBean.setAlipayResult_CODE("2004");
            alipayResultBean.setAlipayResult_Info("not permission :READ_PHONE_STATE");
            SendAlipayData();
            ActivityCompat.requestPermissions(activity, new String[]{Manifest.permission.READ_PHONE_STATE}, 1);
        } else {
            LogUtil.i(TAG, "getPermission: has permission");
            StartAlipay(tn);
        }
    }

    public void StartAlipay(final String orderInfo){
        EnvUtils.setEnv(EnvUtils.EnvEnum.SANDBOX);
        LogUtil.i(TAG, "StartAlipay: orderInfo:"+orderInfo);
        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(activity);
                Map<String, String> result = alipay.payV2(orderInfo, true);
                LogUtil.i(TAG, result.toString());
                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };
        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }
    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    LogUtil.i(TAG, "handleMessage: resultInfo:"+resultInfo);
//                    Utils.showToast("resultInfo: "+resultInfo,activity);
                    String resultStatus = payResult.getResultStatus();
//                    Utils.showToast("resultStatus: "+resultStatus,activity);
                    LogUtil.i(TAG, "handleMessage: resultStatus:"+resultStatus);
                    alipayResultBean.setAlipayResult_CODE(resultStatus);
                    alipayResultBean.setAlipayResult_Info(resultInfo);
                    SendAlipayData();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(activity, "支付成功", Toast.LENGTH_SHORT).show();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(activity, "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(activity,
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(activity,
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        };
    };
}
