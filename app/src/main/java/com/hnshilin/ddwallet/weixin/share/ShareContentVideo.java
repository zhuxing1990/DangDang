package com.hnshilin.ddwallet.weixin.share;


import com.hnshilin.ddwallet.weixin.Constant;

/**
 * 设置分享视频的内容
 */

public class ShareContentVideo extends ShareContent {

    private String url;
    public ShareContentVideo(String url) {
        this.url = url;
    }

    @Override
    protected int getShareWay() {
        return Constant.WECHAT_SHARE_WAY_VIDEO;
    }

    @Override
    protected String getContent() {
        return null;
    }

    @Override
    protected String getTitle() {
        return null;
    }

    @Override
    protected String getURL() {
        return url;
    }

    @Override
    protected int getPictureResource() {
        return -1;
    }
}
