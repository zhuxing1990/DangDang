package com.hnshilin.ddwallet.weixin.share;

import android.app.Activity;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.hnshilin.ddwallet.R;
import com.hnshilin.ddwallet.weixin.Constant;


/**
 * Created by Administrator on 2017/7/14 0014.
 */

public class WeixinShareActivity extends Activity implements OnClickListener {

    private Button mShareText, mSharePicture, mShareVideo;
    private WeixinShareManager mShareManager;

    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mShareText = (Button) findViewById(R.id.share_text);
        mSharePicture = (Button) findViewById(R.id.share_picture);
        mShareVideo = (Button) findViewById(R.id.share_video);
        mShareText.setOnClickListener(this);
        mSharePicture.setOnClickListener(this);
        mShareVideo.setOnClickListener(this);

        mContext = this;

        mShareManager = WeixinShareManager.getInstance(mContext);
    }

    @Override
    public void onClick(View v) {
        if (!isWebchatAvaliable()) {
            Toast.makeText(mContext, "请先安装微信", Toast.LENGTH_LONG).show();
            return;
        }
        switch (v.getId()) {
            case R.id.share_text:
                ShareContentText mShareContentText = (ShareContentText) mShareManager.getShareContentText("微信文本分享");
                mShareManager.shareByWebchat(mShareContentText, Constant.WECHAT_SHARE_TYPE_FRENDS);
                break;
            case R.id.share_picture:
                ShareContentPicture mShareContentPicture = (ShareContentPicture) mShareManager.getShareContentPicture(R.drawable.send_img);//TODO
                mShareManager.shareByWebchat(mShareContentPicture, Constant.WECHAT_SHARE_TYPE_FRENDS);
                break;
            case R.id.share_video:
                ShareContentVideo mShareContentVideo = (ShareContentVideo) mShareManager.getShareContentVideo("http://baidu.hz.letv.com/kan/agSlT?fr=v.baidu.com/");
                mShareManager.shareByWebchat(mShareContentVideo, Constant.WECHAT_SHARE_TYPE_FRENDS);
                break;
            default:
                break;
        }
    }

    private boolean isWebchatAvaliable() {
        //检测手机上是否安装了微信
        try {
            getPackageManager().getPackageInfo("com.tencent.mm", PackageManager.GET_ACTIVITIES);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

}
