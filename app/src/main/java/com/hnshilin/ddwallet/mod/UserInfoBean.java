package com.hnshilin.ddwallet.mod;

import org.json.JSONObject;

/**
 * Created by zhuxi on 2017/8/25.
 */
public class UserInfoBean {
    public static final int LoginCode = 0x12636;
    public static final int WxLoginCode = 0x12637;
    public static final int WxBindingPhone = 0x12638;
    public static final int ReloadWeb = 0x12639;
    public static final int AlipayCode = 0x12640;
    private String user_name;
    private String password;
    private JSONObject WxJsonResult;
    private int Code;
    private  String wxOpenid;
    private String nickname;
    private   String headimgurl;
//    private static UserInfoBean instance;
    private AlipayResultBean alipayResultBean;

    public String getUser_name() {
        return user_name;
    }

    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getCode() {
        return Code;
    }

    public void setCode(int code) {
        Code = code;
    }

    public JSONObject getWxJsonResult() {
        return WxJsonResult;
    }

    public void setWxJsonResult(JSONObject wxJsonResult) {
        WxJsonResult = wxJsonResult;
    }

    public String getWxOpenid() {
        return wxOpenid;
    }

    public void setWxOpenid(String wxOpenid) {
        this.wxOpenid = wxOpenid;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getHeadimgurl() {
        return headimgurl;
    }

    public void setHeadimgurl(String headimgurl) {
        this.headimgurl = headimgurl;
    }

    public AlipayResultBean getAlipayResultBean() {
        return alipayResultBean;
    }

    public void setAlipayResultBean(AlipayResultBean alipayResultBean) {
        this.alipayResultBean = alipayResultBean;
    }

    @Override
    public String toString() {
        return "UserInfoBean{" +
                "user_name='" + user_name + '\'' +
                ", password='" + password + '\'' +
                ", WxJsonResult=" + WxJsonResult +
                ", Code=" + Code +
                ", wxOpenid='" + wxOpenid + '\'' +
                ", nickname='" + nickname + '\'' +
                ", headimgurl='" + headimgurl + '\'' +
                ", alipayResultBean=" + alipayResultBean +
                '}';
    }
}
