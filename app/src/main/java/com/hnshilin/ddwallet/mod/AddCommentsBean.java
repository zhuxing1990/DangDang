package com.hnshilin.ddwallet.mod;

import java.util.List;

/**
 * Created by zhuxi on 2017/9/11.
 */
public class AddCommentsBean {
    /**
     * code : 0
     * data : [{"_result":1,"info":"大家都在货 ","prod_id":1010,"user_id":-10}]
     * message : success
     * success : true
     */

    private int code;
    private String message;
    private boolean success;
    /**
     * _result : 1
     * info : 大家都在货
     * prod_id : 1010
     * user_id : -10
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
        private int _result;
        private String info;
        private int prod_id;
        private int user_id;

        public int get_result() {
            return _result;
        }

        public void set_result(int _result) {
            this._result = _result;
        }

        public String getInfo() {
            return info;
        }

        public void setInfo(String info) {
            this.info = info;
        }

        public int getProd_id() {
            return prod_id;
        }

        public void setProd_id(int prod_id) {
            this.prod_id = prod_id;
        }

        public int getUser_id() {
            return user_id;
        }

        public void setUser_id(int user_id) {
            this.user_id = user_id;
        }
    }

//    /**
//     * message : success
//     * data : {"_action":"prod_comment_add","prod_id":"1381","user_id":"13278875981","_result":1,"info":"国名好货款 大家都在货 "}
//     * code : 0
//     * success : true
//     */
//
//    private String message;
//    /**
//     * _action : prod_comment_add
//     * prod_id : 1381
//     * user_id : 13278875981
//     * _result : 1
//     * info : 国名好货款 大家都在货
//     */
//
//    private DataBean data;
//    private int code;
//    private boolean success;
//
//    public String getMessage() {
//        return message;
//    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }
//
//    public DataBean getData() {
//        return data;
//    }
//
//    public void setData(DataBean data) {
//        this.data = data;
//    }
//
//    public int getCode() {
//        return code;
//    }
//
//    public void setCode(int code) {
//        this.code = code;
//    }
//
//    public boolean isSuccess() {
//        return success;
//    }
//
//    public void setSuccess(boolean success) {
//        this.success = success;
//    }
//
//    public static class DataBean {
//        private String _action;
//        private String prod_id;
//        private String user_id;
//        private int _result;
//        private String info;
//
//        public String get_action() {
//            return _action;
//        }
//
//        public void set_action(String _action) {
//            this._action = _action;
//        }
//
//        public String getProd_id() {
//            return prod_id;
//        }
//
//        public void setProd_id(String prod_id) {
//            this.prod_id = prod_id;
//        }
//
//        public String getUser_id() {
//            return user_id;
//        }
//
//        public void setUser_id(String user_id) {
//            this.user_id = user_id;
//        }
//
//        public int get_result() {
//            return _result;
//        }
//
//        public void set_result(int _result) {
//            this._result = _result;
//        }
//
//        public String getInfo() {
//            return info;
//        }
//
//        public void setInfo(String info) {
//            this.info = info;
//        }
//    }
}
