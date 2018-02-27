package com.hnshilin.ddwallet.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.hnshilin.ddwallet.R;
import com.hnshilin.ddwallet.log.LogUtil;
import com.hnshilin.ddwallet.weixin.Constant;
import com.hnshilin.ddwallet.weixin.WeixinAuthLogin;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


/**
 * 微信分享Activity
 */

public class WXEntryActivity extends AppCompatActivity implements IWXAPIEventHandler {
    private static final String TAG = "WXEntryActivity";
    private IWXAPI iwxapi;
    //微信登录
    private static final int RETURN_MSG_TYPE_LOGIN = 1;
    //微信分享
    private static final int RETURN_MSG_TYPE_SHARE = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wxentry);
        iwxapi = WXAPIFactory.createWXAPI(this, Constant.WECHAT_APP_ID, true);
        iwxapi.handleIntent(getIntent(), this);
        iwxapi.registerApp(Constant.WECHAT_APP_ID);
    }


    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        iwxapi.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq baseReq) {
    }

    @Override
    public void onResp(BaseResp resp) {
        switch (resp.errCode) {
            case BaseResp.ErrCode.ERR_OK:
                if(RETURN_MSG_TYPE_SHARE == resp.getType()){
                    Toast.makeText(this, "分享成功", Toast.LENGTH_SHORT).show();
                }else if(RETURN_MSG_TYPE_LOGIN == resp.getType()) {
                    String code = ((SendAuth.Resp) resp).code; //即为所需的code
                    LogUtil.i(TAG,"weixin code==="+code);
                    WeixinAuthLogin wal = new WeixinAuthLogin(this);
                    wal.wxAuth(code);
                }
                break;
            case BaseResp.ErrCode.ERR_USER_CANCEL:
                if (RETURN_MSG_TYPE_SHARE == resp.getType()){
                    Toast.makeText(this, "取消分享", Toast.LENGTH_SHORT).show();
                } else if(RETURN_MSG_TYPE_LOGIN == resp.getType()){
                    Toast.makeText(this, "取消登录", Toast.LENGTH_SHORT).show();
                }
                break;
            case BaseResp.ErrCode.ERR_AUTH_DENIED:
                Toast.makeText(this, "请求被拒绝", Toast.LENGTH_SHORT).show();
                break;
            default:
                Toast.makeText(this, "未知的错误", Toast.LENGTH_SHORT).show();
                break;
        }
        finish();
    }



}
