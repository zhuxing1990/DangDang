package com.hnshilin.ddwallet.weixin.share;


import com.hnshilin.ddwallet.weixin.Constant;

/**
 * 设置分享链接的内容
 */

public class ShareContentWebpage extends ShareContent {

    private String title;
    private String content;
    private String url;
    private int pictureResource;

    public ShareContentWebpage(String title, String content, String url, int pictureResource){
        this.title = title;
        this.content = content;
        this.url = url;
        this.pictureResource = pictureResource;
    }

    @Override
    protected int getShareWay() {
        return Constant.WECHAT_SHARE_WAY_WEBPAGE;
    }

    @Override
    protected String getContent() {
        return content;
    }

    @Override
    protected String getTitle() {
        return title;
    }

    @Override
    protected String getURL() {
        return url;
    }

    @Override
    protected int getPictureResource() {
        return pictureResource;
    }
}
