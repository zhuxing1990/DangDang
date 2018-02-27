package com.hnshilin.ddwallet.weixin;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;

/**
 * 常量配置类
 */

public class Constant {

    public static final int THUMB_SIZE = 150;

    public static final int WECHAT_SHARE_WAY_TEXT = 1;   //文字
    public static final int WECHAT_SHARE_WAY_PICTURE = 2; //图片
    public static final int WECHAT_SHARE_WAY_WEBPAGE = 3;  //链接
    public static final int WECHAT_SHARE_WAY_VIDEO = 4; //视频

    public static final int WECHAT_SHARE_TYPE_TALK = SendMessageToWX.Req.WXSceneSession;  //会话
    public static final int WECHAT_SHARE_TYPE_FRENDS = SendMessageToWX.Req.WXSceneTimeline; //朋友圈

    /** 微信开放平台AppID */
    public static final String WECHAT_APP_ID = "wxaf6aaca6d665b193";


}
