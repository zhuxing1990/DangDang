package com.hnshilin.ddwallet.ylpay;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.google.gson.Gson;
import com.hnshilin.ddwallet.log.LogUtil;
import com.hnshilin.ddwallet.manage.UrlManager;
import com.hnshilin.ddwallet.mod.YinLianPayBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.okgo.request.PostRequest;
import com.unionpay.UPPayAssistEx;

import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.Date;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by Administrator on 2017/7/28 0028.
 */

public class YinLianPay {
    private static final String TAG = "YinLianPay";
    private final String mMode = "00";
    private Context mContext;

    public YinLianPay(Context context) {
        this.mContext = context;
    }

    public void getTn(String txnAmt, String orderNo, String userId, int payType) {
        //从后台获取tn
        String url = UrlManager.BASE_PATH+UrlManager.AddYLOrder;
        if ("".equals(orderNo) && payType != 2) {
            orderNo = getOrderId();
        }
        try {
            PostRequest postRequest = OkGo.post(url).tag(this);
            postRequest .params("merId", "826520148160070")
                  .params("txnAmt", txnAmt)
                  .params("txnTime", getCurrentTime())
                  .params("orderNo", orderNo)
                  .params("payType", payType)
                  .params("pay_type",1);
                if (payType == 2) {
                    postRequest .params("orderName", "购买pos机")
                    .params("orderType", "2");
                }else{
                    postRequest
                            .params("orderName", "升级为代理")
                            .params("orderType", "1");
                }
                   postRequest
                           .params("userId", userId)
                           .params("money", txnAmt);
            postRequest.execute(new StringCallback() {
                @Override
                public void onSuccess(String s, Call call, Response response) {
                    try {
                        if (!TextUtils.isEmpty(s)){
                            JSONObject json = new JSONObject(s);
                            if (json.has("code")){
                                int code = json.getInt("code");
                                YinLianPayBean yinLianPayBean = new Gson().fromJson(s, YinLianPayBean.class);
                                LogUtil.i(TAG, "onSuccess: yinLianPayBean.getTn():"+yinLianPayBean.getTn());
                                switch (code){
                                    case 0:
                                        UPPayAssistEx.startPay(mContext, null, null, yinLianPayBean.getTn(), mMode);
                                        break;
                                    default:
                                        UPPayAssistEx.startPay(mContext, null, null, yinLianPayBean.getTn(), mMode);
                                        break;
                                }
                            }
                            Toast.makeText(mContext, "成功", Toast.LENGTH_SHORT).show();
                        }else{

                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }

                @Override
                public void onError(Call call, Response response, Exception e) {
                    super.onError(call, response, e);
                    if (!TextUtils.isEmpty(response.body().toString())){
                        UPPayAssistEx.startPay(mContext, null, null, response.body().toString(), mMode);
                    }
                    Toast.makeText(mContext, "失败", Toast.LENGTH_SHORT).show();
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    // 商户发送交易时间 格式:YYYYMMDDhhmmss
    public static String getCurrentTime() {
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
    }

    // AN8..40 商户订单号，不能含"-"或"_"
    public static String getOrderId() {
        int num = 1;
        double random = Math.random();
        if (random < 0.1) {
            random = random + 0.1;
        }
        for (int i = 0; i < 6; i++) {
            num = num * 10;
        }
        return new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()) + (int) ((random * num));
    }


}
