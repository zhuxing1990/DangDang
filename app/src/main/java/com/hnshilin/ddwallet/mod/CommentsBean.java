package com.hnshilin.ddwallet.mod;

import java.util.List;

/**
 * Created by zhuxi on 2017/8/24.
 */
public class CommentsBean {

    /**
     * code : 0
     * data : [{"TOTAL":3,"INFO":"良心网贷"},{"TOTAL":1,"INFO":"国明好贷款"},{"TOTAL":4,"INFO":"大家都在贷"},{"TOTAL":1,"INFO":"利率低"},{"TOTAL":1,"INFO":"超高通过率"},{"TOTAL":1,"INFO":"秒速到账"},{"TOTAL":3,"INFO":"门槛不能再低啦"}]
     * success : true
     * message : success
     */

    private int code;
    private boolean success;
    private String message;
    /**
     * TOTAL : 3
     * INFO : 良心网贷
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
        private int TOTAL;
        private String INFO;

        public int getTOTAL() {
            return TOTAL;
        }

        public void setTOTAL(int TOTAL) {
            this.TOTAL = TOTAL;
        }

        public String getINFO() {
            return INFO;
        }

        public void setINFO(String INFO) {
            this.INFO = INFO;
        }
    }
}
