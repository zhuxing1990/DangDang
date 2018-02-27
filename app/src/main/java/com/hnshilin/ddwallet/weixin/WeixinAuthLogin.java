package com.hnshilin.ddwallet.weixin;

import android.content.Context;
import android.text.TextUtils;
import android.widget.Toast;

import com.hnshilin.ddwallet.base.RxBus;
import com.hnshilin.ddwallet.log.LogUtil;
import com.hnshilin.ddwallet.manage.UrlManager;
import com.hnshilin.ddwallet.mod.UserInfoBean;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import org.json.JSONArray;
import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 微信登录
 */

public class WeixinAuthLogin {
    private static final String TAG = "WeixinAuthLogin";
    private IWXAPI mWXApi;
    private Context mContext;


    public WeixinAuthLogin(Context context){
        this.mContext = context;
        initWeixinLogin(context);
    }

    private void initWeixinLogin(Context context){
        if (mWXApi == null) {
            mWXApi = WXAPIFactory.createWXAPI(context, Constant.WECHAT_APP_ID, true);
        }
        mWXApi.registerApp(Constant.WECHAT_APP_ID);
    }

    public void sendWeixinLoginReq() {
        if(!mWXApi.isWXAppInstalled()) {
            Toast.makeText(mContext, "您还未安装微信客户端", Toast.LENGTH_SHORT).show();
            return;
        }
        final SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "shilin_weixin_login";
        mWXApi.sendReq(req);
    }

    public void wxAuth(String code) {
        //根据code请求服务器进行微信授权登录
        String url = UrlManager.BASE_PATH + UrlManager.AppWxLogin;
        OkGo.post(url).tag(this).params("code",code).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                try {
                    if (!TextUtils.isEmpty(s)){
                        UserInfoBean userInfoBean = new UserInfoBean();
                        LogUtil.i(TAG, "onSuccess: s"+s);
                        JSONObject json = new JSONObject(s);
                        if (json.has("code")){
                            int code = json.getInt("code");
                            switch (code){
                                case 0:
                                    userInfoBean.setCode(UserInfoBean.WxLoginCode);
                                    userInfoBean.setWxJsonResult(json);
                                    RxBus.getInstance().post(userInfoBean);
                                    break;
                                case 1:
                     //需要绑定手机号
                        JSONArray data = json.getJSONArray("data");
                        String wxOpenid = data.getJSONObject(0).getString("wxOpenid");//微信OpenID
                        String nickname = data.getJSONObject(0).getString("nickname");//昵称
                        String headimgurl = data.getJSONObject(0).getString("headimgurl");//头像
                                    userInfoBean.setCode(UserInfoBean.WxBindingPhone);
                                    userInfoBean.setWxOpenid(wxOpenid);
                                    userInfoBean.setNickname(nickname);
                                    userInfoBean.setHeadimgurl(headimgurl);
                                    RxBus.getInstance().post(userInfoBean);
                                    break;
                                default:
                                    break;
                            }
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                Toast.makeText(mContext, "微信登录失败", Toast.LENGTH_SHORT).show();
            }
        });

    }
}
