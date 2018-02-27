package com.hnshilin.ddwallet.mod;

import java.util.List;

/**
 * Created by zhuxi on 2017/8/24.
 */
public class ProdCateBean {


    /**
     * code : 0
     * data : [{"PROD_ICON_URL":"http://orzkupmj3.bkt.clouddn.com/02233bf0-2de8-4351-8e13-a4ad71825990.jpg","PNAME":"奇速贷","COMMENTT":"轻松申请，1分钟到账。","PROD_LINK_URL":"https://www.baidu.com/","PROD_ID":1161}]
     * success : true
     * message : success
     */

    private int code;
    private boolean success;
    private String message;
    /**
     * PROD_ICON_URL : http://orzkupmj3.bkt.clouddn.com/02233bf0-2de8-4351-8e13-a4ad71825990.jpg
     * PNAME : 奇速贷
     * COMMENTT : 轻松申请，1分钟到账。
     * PROD_LINK_URL : https://www.baidu.com/
     * PROD_ID : 1161
     */

    private List<DataBean> data;

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

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String PROD_ICON_URL;
        private String PNAME;
        private String COMMENTT;
        private String PROD_LINK_URL;
        private int PROD_ID;

        public String getPROD_ICON_URL() {
            return PROD_ICON_URL;
        }

        public void setPROD_ICON_URL(String PROD_ICON_URL) {
            this.PROD_ICON_URL = PROD_ICON_URL;
        }

        public String getPNAME() {
            return PNAME;
        }

        public void setPNAME(String PNAME) {
            this.PNAME = PNAME;
        }

        public String getCOMMENTT() {
            return COMMENTT;
        }

        public void setCOMMENTT(String COMMENTT) {
            this.COMMENTT = COMMENTT;
        }

        public String getPROD_LINK_URL() {
            return PROD_LINK_URL;
        }

        public void setPROD_LINK_URL(String PROD_LINK_URL) {
            this.PROD_LINK_URL = PROD_LINK_URL;
        }

        public int getPROD_ID() {
            return PROD_ID;
        }

        public void setPROD_ID(int PROD_ID) {
            this.PROD_ID = PROD_ID;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "PROD_ICON_URL='" + PROD_ICON_URL + '\'' +
                    ", PNAME='" + PNAME + '\'' +
                    ", COMMENTT='" + COMMENTT + '\'' +
                    ", PROD_LINK_URL='" + PROD_LINK_URL + '\'' +
                    ", PROD_ID=" + PROD_ID +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ProdCateBean{" +
                "code=" + code +
                ", success=" + success +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
