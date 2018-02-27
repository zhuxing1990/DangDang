package com.hnshilin.ddwallet.mod;

import java.util.List;

/**
 * Created by zhuxi on 2017/8/24.
 */
public class UpdateApkBean {


    /**
     * code : 0
     * data : [{"ID":1,"IS_MUST_UPDATE":1,"NEXT_VERSION_URL":"http://www.shilin.com","VERSION_NAME":"1.0.0"}]
     * message : SUCCESS
     */

    private int code;
    private String message;
    /**
     * ID : 1
     * IS_MUST_UPDATE : 1
     * NEXT_VERSION_URL : http://www.shilin.com
     * VERSION_NAME : 1.0.0
     */

    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
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
        private int ID;
        private int IS_MUST_UPDATE;
        private String NEXT_VERSION_URL;
        private String VERSION_NAME;

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public int getIS_MUST_UPDATE() {
            return IS_MUST_UPDATE;
        }

        public void setIS_MUST_UPDATE(int IS_MUST_UPDATE) {
            this.IS_MUST_UPDATE = IS_MUST_UPDATE;
        }

        public String getNEXT_VERSION_URL() {
            return NEXT_VERSION_URL;
        }

        public void setNEXT_VERSION_URL(String NEXT_VERSION_URL) {
            this.NEXT_VERSION_URL = NEXT_VERSION_URL;
        }

        public String getVERSION_NAME() {
            return VERSION_NAME;
        }

        public void setVERSION_NAME(String VERSION_NAME) {
            this.VERSION_NAME = VERSION_NAME;
        }
    }
}
