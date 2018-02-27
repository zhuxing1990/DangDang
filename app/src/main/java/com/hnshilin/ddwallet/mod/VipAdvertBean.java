package com.hnshilin.ddwallet.mod;

import java.util.List;

/**
 * Created by zhuxi on 2017/8/22.
 */
public class VipAdvertBean {

    /**
     * message : success
     * data : [{"VIP_LINK_URL":"http://wap.jiehuahua.com/qianbao/index.html?chl=dangdangqianbao","VIP_PROD_NAME":"借花花","VIP_IMG_URL":"http://orzkupmj3.bkt.clouddn.com/47c87020-f8e0-4b28-8c24-78e7cc9393f1.png"},{"VIP_LINK_URL":"http://static.namifunds.com/qyl/web/views/invitation-new.html?iv_code=OLU488746&channel=yy-4","VIP_PROD_NAME":"钱有路","VIP_IMG_URL":"http://orzkupmj3.bkt.clouddn.com/35b51a51-b392-4898-a6e9-036c33e3d91b.jpg"},{"VIP_LINK_URL":"http://wap.51licai.mobi/linkChannel.do?formAction=channel&channel=4","VIP_PROD_NAME":"贷款钱包","VIP_IMG_URL":"http://orzkupmj3.bkt.clouddn.com/d34b6e73-5d1b-4047-80b0-1eafb911597b.png"}]
     * code : 0
     * success : true
     */

    private String message;
    private int code;
    private boolean success;
    /**
     * VIP_LINK_URL : http://wap.jiehuahua.com/qianbao/index.html?chl=dangdangqianbao
     * VIP_PROD_NAME : 借花花
     * VIP_IMG_URL : http://orzkupmj3.bkt.clouddn.com/47c87020-f8e0-4b28-8c24-78e7cc9393f1.png
     */

    private List<DataBean> data;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String VIP_LINK_URL;
        private String VIP_PROD_NAME;
        private String VIP_IMG_URL;

        public String getVIP_LINK_URL() {
            return VIP_LINK_URL;
        }

        public void setVIP_LINK_URL(String VIP_LINK_URL) {
            this.VIP_LINK_URL = VIP_LINK_URL;
        }

        public String getVIP_PROD_NAME() {
            return VIP_PROD_NAME;
        }

        public void setVIP_PROD_NAME(String VIP_PROD_NAME) {
            this.VIP_PROD_NAME = VIP_PROD_NAME;
        }

        public String getVIP_IMG_URL() {
            return VIP_IMG_URL;
        }

        public void setVIP_IMG_URL(String VIP_IMG_URL) {
            this.VIP_IMG_URL = VIP_IMG_URL;
        }
    }
}
