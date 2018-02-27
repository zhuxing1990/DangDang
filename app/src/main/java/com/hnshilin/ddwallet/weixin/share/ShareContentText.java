package com.hnshilin.ddwallet.weixin.share;


import com.hnshilin.ddwallet.weixin.Constant;

/**
 * 设置分享文字的内容
 */

public class ShareContentText extends ShareContent {
    private String content;

    /**
     * 构造分享文字类
     * @param  分享的文字内容
     */
    public ShareContentText(String content){
        this.content = content;
    }

    @Override
    protected int getShareWay() {
        return Constant.WECHAT_SHARE_WAY_TEXT;
    }

    @Override
    protected String getContent() {
        return content;
    }

    @Override
    protected String getTitle() {
        return null;
    }

    @Override
    protected String getURL() {
        return null;
    }

    @Override
    protected int getPictureResource() {
        return -1;
    }
}
