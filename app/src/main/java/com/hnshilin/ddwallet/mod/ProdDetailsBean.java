package com.hnshilin.ddwallet.mod;

import java.util.List;

/**
 * Created by zhuxi on 2017/8/24.
 */
public class ProdDetailsBean {


    /**
     * message : success
     * data : [{"LIMITNUMBER_MIN":500,"SUCCESS":5,"INTERESTWAY":"月息","LABEL":"资料简单","PROD_LINK_URL":"https://api.daishangqian.com/vue/?channel=59896b43c224#!/active/promotion/page6","PNAME":"贷上钱","LIMITNUMBER_MAX":2000,"INTEREST":"0.03%","PROD_ICON_URL":"http://orzkupmj3.bkt.clouddn.com/06a96583-d7b8-4faf-8e5d-2f91ca314dd7.png","PROD_ID":1348,"DOWNMAMOUNT":165165,"LOANTIME":"14-30天","FASTESTTIME":"5分钟","PROD_COMMENT":"3分钟快速审核，最快88秒极速放款。让你借钱方便无忧！","DATA":"1.\u201c二代身份证\u201d正反面照片  2.手机验证 3.芝麻分授权","OVERDUE":"0.05%","REQUIREMENTS":"1.年龄18～40周岁   2.实名制手机使用满6个月 "}]
     * code : 0
     * success : true
     */

    private String message;
    private int code;
    private boolean success;
    /**
     * LIMITNUMBER_MIN : 500
     * SUCCESS : 5
     * INTERESTWAY : 月息
     * LABEL : 资料简单
     * PROD_LINK_URL : https://api.daishangqian.com/vue/?channel=59896b43c224#!/active/promotion/page6
     * PNAME : 贷上钱
     * LIMITNUMBER_MAX : 2000
     * INTEREST : 0.03%
     * PROD_ICON_URL : http://orzkupmj3.bkt.clouddn.com/06a96583-d7b8-4faf-8e5d-2f91ca314dd7.png
     * PROD_ID : 1348
     * DOWNMAMOUNT : 165165
     * LOANTIME : 14-30天
     * FASTESTTIME : 5分钟
     * PROD_COMMENT : 3分钟快速审核，最快88秒极速放款。让你借钱方便无忧！
     * DATA : 1.“二代身份证”正反面照片  2.手机验证 3.芝麻分授权
     * OVERDUE : 0.05%
     * REQUIREMENTS : 1.年龄18～40周岁   2.实名制手机使用满6个月
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
        private int LIMITNUMBER_MIN;
        private int SUCCESS;
        private String INTERESTWAY;
        private String LABEL;
        private String PROD_LINK_URL;
        private String PNAME;
        private int LIMITNUMBER_MAX;
        private String INTEREST;
        private String PROD_ICON_URL;
        private int PROD_ID;
        private int DOWNMAMOUNT;
        private String LOANTIME;
        private String FASTESTTIME;
        private String PROD_COMMENT;
        private String DATA;
        private String OVERDUE;
        private String REQUIREMENTS;

        public int getLIMITNUMBER_MIN() {
            return LIMITNUMBER_MIN;
        }

        public void setLIMITNUMBER_MIN(int LIMITNUMBER_MIN) {
            this.LIMITNUMBER_MIN = LIMITNUMBER_MIN;
        }

        public int getSUCCESS() {
            return SUCCESS;
        }

        public void setSUCCESS(int SUCCESS) {
            this.SUCCESS = SUCCESS;
        }

        public String getINTERESTWAY() {
            return INTERESTWAY;
        }

        public void setINTERESTWAY(String INTERESTWAY) {
            this.INTERESTWAY = INTERESTWAY;
        }

        public String getLABEL() {
            return LABEL;
        }

        public void setLABEL(String LABEL) {
            this.LABEL = LABEL;
        }

        public String getPROD_LINK_URL() {
            return PROD_LINK_URL;
        }

        public void setPROD_LINK_URL(String PROD_LINK_URL) {
            this.PROD_LINK_URL = PROD_LINK_URL;
        }

        public String getPNAME() {
            return PNAME;
        }

        public void setPNAME(String PNAME) {
            this.PNAME = PNAME;
        }

        public int getLIMITNUMBER_MAX() {
            return LIMITNUMBER_MAX;
        }

        public void setLIMITNUMBER_MAX(int LIMITNUMBER_MAX) {
            this.LIMITNUMBER_MAX = LIMITNUMBER_MAX;
        }

        public String getINTEREST() {
            return INTEREST;
        }

        public void setINTEREST(String INTEREST) {
            this.INTEREST = INTEREST;
        }

        public String getPROD_ICON_URL() {
            return PROD_ICON_URL;
        }

        public void setPROD_ICON_URL(String PROD_ICON_URL) {
            this.PROD_ICON_URL = PROD_ICON_URL;
        }

        public int getPROD_ID() {
            return PROD_ID;
        }

        public void setPROD_ID(int PROD_ID) {
            this.PROD_ID = PROD_ID;
        }

        public int getDOWNMAMOUNT() {
            return DOWNMAMOUNT;
        }

        public void setDOWNMAMOUNT(int DOWNMAMOUNT) {
            this.DOWNMAMOUNT = DOWNMAMOUNT;
        }

        public String getLOANTIME() {
            return LOANTIME;
        }

        public void setLOANTIME(String LOANTIME) {
            this.LOANTIME = LOANTIME;
        }

        public String getFASTESTTIME() {
            return FASTESTTIME;
        }

        public void setFASTESTTIME(String FASTESTTIME) {
            this.FASTESTTIME = FASTESTTIME;
        }

        public String getPROD_COMMENT() {
            return PROD_COMMENT;
        }

        public void setPROD_COMMENT(String PROD_COMMENT) {
            this.PROD_COMMENT = PROD_COMMENT;
        }

        public String getDATA() {
            return DATA;
        }

        public void setDATA(String DATA) {
            this.DATA = DATA;
        }

        public String getOVERDUE() {
            return OVERDUE;
        }

        public void setOVERDUE(String OVERDUE) {
            this.OVERDUE = OVERDUE;
        }

        public String getREQUIREMENTS() {
            return REQUIREMENTS;
        }

        public void setREQUIREMENTS(String REQUIREMENTS) {
            this.REQUIREMENTS = REQUIREMENTS;
        }

        @Override
        public String toString() {
            return "DataBean{" +
                    "LIMITNUMBER_MIN=" + LIMITNUMBER_MIN +
                    ", SUCCESS=" + SUCCESS +
                    ", INTERESTWAY='" + INTERESTWAY + '\'' +
                    ", LABEL='" + LABEL + '\'' +
                    ", PROD_LINK_URL='" + PROD_LINK_URL + '\'' +
                    ", PNAME='" + PNAME + '\'' +
                    ", LIMITNUMBER_MAX=" + LIMITNUMBER_MAX +
                    ", INTEREST='" + INTEREST + '\'' +
                    ", PROD_ICON_URL='" + PROD_ICON_URL + '\'' +
                    ", PROD_ID=" + PROD_ID +
                    ", DOWNMAMOUNT=" + DOWNMAMOUNT +
                    ", LOANTIME='" + LOANTIME + '\'' +
                    ", FASTESTTIME='" + FASTESTTIME + '\'' +
                    ", PROD_COMMENT='" + PROD_COMMENT + '\'' +
                    ", DATA='" + DATA + '\'' +
                    ", OVERDUE='" + OVERDUE + '\'' +
                    ", REQUIREMENTS='" + REQUIREMENTS + '\'' +
                    '}';
        }
    }

    @Override
    public String toString() {
        return "ProdDetailsBean{" +
                "message='" + message + '\'' +
                ", code=" + code +
                ", success=" + success +
                ", data=" + data +
                '}';
    }
}
