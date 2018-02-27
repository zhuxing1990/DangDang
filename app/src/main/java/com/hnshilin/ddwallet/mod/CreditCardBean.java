package com.hnshilin.ddwallet.mod;

import java.util.List;

/**
 * Created by zhuxi on 2017/8/24.
 */
public class CreditCardBean {

    /**
     * message : success
     * data : [{"CREDIT_ID":1009,"CREDIT_ORDER":3,"CREDIT_DETAILS_URL":"http://www.hnshilin.com/web/page.html","CREDIT_PROD_URL":"http://orzkupmj3.bkt.clouddn.com/75beb6e4-8766-4c25-94e7-1734daa27b0e.jpg","CREDIT_NAME":"浦发银行信用卡","CREDIT_DOWNLOAD":10093,"CREDIT_LABEL":"开门礼 门槛低","CREDIT_DESC":"免年费,消费送礼包"},{"CREDIT_ID":1002,"CREDIT_ORDER":2,"CREDIT_DETAILS_URL":"http://www.hnshilin.com/web/page.html","CREDIT_PROD_URL":"http://orzkupmj3.bkt.clouddn.com/5209b3cd-e604-4d3e-8838-e368f6fc4454.jpg","CREDIT_NAME":"招商银行信用卡","CREDIT_DOWNLOAD":20020,"CREDIT_LABEL":"门槛低 流程简","CREDIT_DESC":"招行粉丝的首选"},{"CREDIT_ID":1001,"CREDIT_ORDER":1,"CREDIT_DETAILS_URL":"http://www.hnshilin.com/web/page.html","CREDIT_PROD_URL":"http://orzkupmj3.bkt.clouddn.com/c047be0a-6317-4d59-992e-641f0f512577.jpg","CREDIT_NAME":"光大银行信用卡","CREDIT_DOWNLOAD":20028,"CREDIT_LABEL":"额度高 开门礼","CREDIT_DESC":"首年免年费,多重优惠"}]
     * code : 0
     * success : true
     */

    private String message;
    private int code;
    private boolean success;
    /**
     * CREDIT_ID : 1009
     * CREDIT_ORDER : 3
     * CREDIT_DETAILS_URL : http://www.hnshilin.com/web/page.html
     * CREDIT_PROD_URL : http://orzkupmj3.bkt.clouddn.com/75beb6e4-8766-4c25-94e7-1734daa27b0e.jpg
     * CREDIT_NAME : 浦发银行信用卡
     * CREDIT_DOWNLOAD : 10093
     * CREDIT_LABEL : 开门礼 门槛低
     * CREDIT_DESC : 免年费,消费送礼包
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
        private int CREDIT_ID;
        private int CREDIT_ORDER;
        private String CREDIT_DETAILS_URL;
        private String CREDIT_PROD_URL;
        private String CREDIT_NAME;
        private int CREDIT_DOWNLOAD;
        private String CREDIT_LABEL;
        private String CREDIT_DESC;

        public int getCREDIT_ID() {
            return CREDIT_ID;
        }

        public void setCREDIT_ID(int CREDIT_ID) {
            this.CREDIT_ID = CREDIT_ID;
        }

        public int getCREDIT_ORDER() {
            return CREDIT_ORDER;
        }

        public void setCREDIT_ORDER(int CREDIT_ORDER) {
            this.CREDIT_ORDER = CREDIT_ORDER;
        }

        public String getCREDIT_DETAILS_URL() {
            return CREDIT_DETAILS_URL;
        }

        public void setCREDIT_DETAILS_URL(String CREDIT_DETAILS_URL) {
            this.CREDIT_DETAILS_URL = CREDIT_DETAILS_URL;
        }

        public String getCREDIT_PROD_URL() {
            return CREDIT_PROD_URL;
        }

        public void setCREDIT_PROD_URL(String CREDIT_PROD_URL) {
            this.CREDIT_PROD_URL = CREDIT_PROD_URL;
        }

        public String getCREDIT_NAME() {
            return CREDIT_NAME;
        }

        public void setCREDIT_NAME(String CREDIT_NAME) {
            this.CREDIT_NAME = CREDIT_NAME;
        }

        public int getCREDIT_DOWNLOAD() {
            return CREDIT_DOWNLOAD;
        }

        public void setCREDIT_DOWNLOAD(int CREDIT_DOWNLOAD) {
            this.CREDIT_DOWNLOAD = CREDIT_DOWNLOAD;
        }

        public String getCREDIT_LABEL() {
            return CREDIT_LABEL;
        }

        public void setCREDIT_LABEL(String CREDIT_LABEL) {
            this.CREDIT_LABEL = CREDIT_LABEL;
        }

        public String getCREDIT_DESC() {
            return CREDIT_DESC;
        }

        public void setCREDIT_DESC(String CREDIT_DESC) {
            this.CREDIT_DESC = CREDIT_DESC;
        }
    }
}
