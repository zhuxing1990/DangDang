package com.hnshilin.ddwallet.weixin.share;


import com.hnshilin.ddwallet.weixin.Constant;

/**
 * 设置分享图片的内容
 */

public class ShareContentPicture extends ShareContent {

    private int pictureResource;

    public ShareContentPicture(int pictureResource){
        this.pictureResource = pictureResource;
    }

    @Override
    protected int getShareWay() {
        return Constant.WECHAT_SHARE_WAY_PICTURE;
    }

    @Override
    protected int getPictureResource() {
        return pictureResource;
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
        return null;
    }
}
