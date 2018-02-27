package com.hnshilin.ddwallet.weixin.share;

/**
 * Created by Administrator on 2017/7/14 0014.
 */

public abstract class ShareContent {
    protected abstract int getShareWay();
    protected abstract String getContent();
    protected abstract String getTitle();
    protected abstract String getURL();
    protected abstract int getPictureResource();
}
