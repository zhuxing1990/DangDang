package com.hnshilin.ddwallet.weixin.share;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.hnshilin.ddwallet.R;
import com.hnshilin.ddwallet.weixin.Constant;
import com.hnshilin.ddwallet.weixin.WeixinUtil;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXTextObject;
import com.tencent.mm.opensdk.modelmsg.WXVideoObject;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

/**
 * 微信分享管理类
 */

public class WeixinShareManager {

    private static WeixinShareManager mInstance;
    private ShareContent mShareContentText, mShareContentPicture, mShareContentWebpag, mShareContentVideo;
    private IWXAPI mWXApi;
    private Context mContext;

    private WeixinShareManager(Context context){
        this.mContext = context;
        //初始化数据  
        //初始化微信分享代码  
        initWechatShare(context);
    }

    /**
     * 获取WeixinShareManager实例 
     * 非线程安全，请在UI线程中操作 
     * @return
     */
    public static WeixinShareManager getInstance(Context context){
        if(mInstance == null){
            mInstance = new WeixinShareManager(context);
        }
        return mInstance;
    }

    private void initWechatShare(Context context){
        if (mWXApi == null) {
            mWXApi = WXAPIFactory.createWXAPI(context, Constant.WECHAT_APP_ID, true);
        }
        mWXApi.registerApp(Constant.WECHAT_APP_ID);
    }

    /**
     * 通过微信分享 
     * @param shareContent 分享的方式（文本、图片、链接）
     * @param shareType 分享的类型（朋友圈，会话） 
     */
    public void shareByWebchat(ShareContent shareContent, int shareType){
        if(!mWXApi.isWXAppInstalled()) {
            Toast.makeText(mContext, "您还未安装微信客户端", Toast.LENGTH_SHORT).show();
            return;
        }
        switch (shareContent.getShareWay()) {
            case Constant.WECHAT_SHARE_WAY_TEXT:
                shareText(shareContent, shareType);
                break;
            case Constant.WECHAT_SHARE_WAY_PICTURE:
                sharePicture(shareContent, shareType);
                break;
            case Constant.WECHAT_SHARE_WAY_WEBPAGE:
                shareWebPage(shareContent, shareType);
                break;
            case Constant.WECHAT_SHARE_WAY_VIDEO:
                shareVideo(shareContent, shareType);
                break;
        }
    }

    /**
     * 获取文本分享对象 
     */
    public ShareContent getShareContentText(String content) {
        if (mShareContentText == null) {
            mShareContentText = new ShareContentText(content);
        }
        return mShareContentText;
    }

    /**
     * 获取图片分享对象 
     */
    public ShareContent getShareContentPicture(int pictureResource) {
        if (mShareContentPicture == null) {
            mShareContentPicture = new ShareContentPicture(pictureResource);
        }
        return mShareContentPicture;
    }


    /**
     * 获取网页分享对象 
     */
    public ShareContent getShareContentWebpag(String title, String content, String url, int pictureResource) {
        if (mShareContentWebpag == null) {
            mShareContentWebpag = new ShareContentWebpage(title, content, url, pictureResource);
        }
        return mShareContentWebpag;
    }

    /**
     * 获取视频分享内容 
     */
    public ShareContent getShareContentVideo(String url) {
        if (mShareContentVideo == null) {
            mShareContentVideo = new ShareContentVideo(url);
        }
        return mShareContentVideo;
    }

    /**
     * 分享文字 
     */
    public void shareText(ShareContent shareContent, int shareType) {
        String text = shareContent.getContent();
        //初始化一个WXTextObject对象  
        WXTextObject textObj = new WXTextObject();
        textObj.text = text;
        //用WXTextObject对象初始化一个WXMediaMessage对象  
        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = textObj;
        msg.description = text;
        //构造一个Req  
        SendMessageToWX.Req req = new SendMessageToWX.Req();
        //transaction字段用于唯一标识一个请求  
        req.transaction = buildTransaction("text");
        req.message = msg;
        //发送的目标场景， 可以选择发送到会话 WXSceneSession 或者朋友圈 WXSceneTimeline。 默认发送到会话。
        req.scene = shareType;
        mWXApi.sendReq(req);
    }

    /**
     * 分享图片 
     */
    private void sharePicture(ShareContent shareContent, int shareType) {
        Bitmap bitmap = BitmapFactory.decodeResource(mContext.getResources(), shareContent.getPictureResource());
        WXImageObject imgObj = new WXImageObject(bitmap);

        WXMediaMessage msg = new WXMediaMessage();
        msg.mediaObject = imgObj;

        Bitmap thumbBitmap =  Bitmap.createScaledBitmap(bitmap, Constant.THUMB_SIZE, Constant.THUMB_SIZE, true);
        bitmap.recycle();
        msg.thumbData = WeixinUtil.bmpToByteArray(thumbBitmap, true);  //设置缩略图

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("img");
        req.message = msg;
        req.scene = shareType;
        mWXApi.sendReq(req);
    }

    /**
     * 分享链接 
     */
    private void shareWebPage(ShareContent shareContent, int shareType) {
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = shareContent.getURL();
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = shareContent.getTitle();

        msg.description = shareContent.getContent();

        Bitmap thumb = BitmapFactory.decodeResource(mContext.getResources(), shareContent.getPictureResource());
        if(thumb == null) {
            Toast.makeText(mContext, "图片不能为空", Toast.LENGTH_SHORT).show();
        } else {
            msg.thumbData = WeixinUtil.bmpToByteArray(thumb, true);
        }

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = shareType;
        mWXApi.sendReq(req);
    }

    /**
     * 分享视频 
     */
    private void shareVideo(ShareContent shareContent, int shareType) {
        WXVideoObject video = new WXVideoObject();
        video.videoUrl = shareContent.getURL();

        WXMediaMessage msg = new WXMediaMessage(video);
        msg.title = shareContent.getTitle();
        msg.description = shareContent.getContent();
        Bitmap thumb = BitmapFactory.decodeResource(mContext.getResources(), R.drawable.send_music_thumb);
//      BitmapFactory.decodeStream(new URL(video.videoUrl).openStream());  
        /**
         * 测试过程中会出现这种情况，会有个别手机会出现调不起微信客户端的情况。造成这种情况的原因是微信对缩略图的大小、title、description等参数的大小做了限制，所以有可能是大小超过了默认的范围。 
         * 一般情况下缩略图超出比较常见。Title、description都是文本，一般不会超过。 
         */
        Bitmap thumbBitmap =  Bitmap.createScaledBitmap(thumb, Constant.THUMB_SIZE, Constant.THUMB_SIZE, true);
        thumb.recycle();
        msg.thumbData = WeixinUtil.bmpToByteArray(thumbBitmap, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("video");
        req.message = msg;
        req.scene =  shareType;
        mWXApi.sendReq(req);
    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }




}

