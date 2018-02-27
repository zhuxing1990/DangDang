package com.hnshilin.ddwallet.mod;

import java.util.List;

/**
 * Created by zhuxi on 2017/8/25.
 */
public class LoginInfoBean {

    /**
     * code : 0
     * message : SUCCESS
     * data : [{"IS_AUTH":0,"BALANCE":0,"HEAD_PICTURE":"user-photo.png","CREATEDATE":{"date":25,"day":5,"hours":16,"minutes":31,"month":7,"nanos":0,"seconds":57,"time":1503649917000,"timezoneOffset":-480,"year":117},"AGE":0,"APPLE_STATE":0,"SEX":0,"SUPERIOR_USERID":0,"ID":2250,"ISAGENT":"0","PHONE_NUMBER":"13278875981"}]
     * token : eyJhbGciOiJIUzI1NiJ9.eyJpYXQiOjE1MDM2NTY0MzMsInN1YiI6InBob25lTnVtYmVyPTEzMjc4ODc1OTgxJnVzZXJJZD0yMjUwIiwiZXhwIjoxNTAzNjU2NTIwfQ.L-5By_u6Vx_5tHIXKP_HjgJxwjEiTdG77BRC5qlslBw
     */

    private int code;
    private String message;
    private String token;
    /**
     * IS_AUTH : 0
     * BALANCE : 0
     * HEAD_PICTURE : user-photo.png
     * CREATEDATE : {"date":25,"day":5,"hours":16,"minutes":31,"month":7,"nanos":0,"seconds":57,"time":1503649917000,"timezoneOffset":-480,"year":117}
     * AGE : 0
     * APPLE_STATE : 0
     * SEX : 0
     * SUPERIOR_USERID : 0
     * ID : 2250
     * ISAGENT : 0
     * PHONE_NUMBER : 13278875981
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

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private int IS_AUTH;
        private int BALANCE;
        private String HEAD_PICTURE;
        /**
         * date : 25
         * day : 5
         * hours : 16
         * minutes : 31
         * month : 7
         * nanos : 0
         * seconds : 57
         * time : 1503649917000
         * timezoneOffset : -480
         * year : 117
         */

        private CREATEDATEBean CREATEDATE;
        private int AGE;
        private int APPLE_STATE;
        private int SEX;
        private int SUPERIOR_USERID;
        private int ID;
        private String ISAGENT;
        private String PHONE_NUMBER;

        public int getIS_AUTH() {
            return IS_AUTH;
        }

        public void setIS_AUTH(int IS_AUTH) {
            this.IS_AUTH = IS_AUTH;
        }

        public int getBALANCE() {
            return BALANCE;
        }

        public void setBALANCE(int BALANCE) {
            this.BALANCE = BALANCE;
        }

        public String getHEAD_PICTURE() {
            return HEAD_PICTURE;
        }

        public void setHEAD_PICTURE(String HEAD_PICTURE) {
            this.HEAD_PICTURE = HEAD_PICTURE;
        }

        public CREATEDATEBean getCREATEDATE() {
            return CREATEDATE;
        }

        public void setCREATEDATE(CREATEDATEBean CREATEDATE) {
            this.CREATEDATE = CREATEDATE;
        }

        public int getAGE() {
            return AGE;
        }

        public void setAGE(int AGE) {
            this.AGE = AGE;
        }

        public int getAPPLE_STATE() {
            return APPLE_STATE;
        }

        public void setAPPLE_STATE(int APPLE_STATE) {
            this.APPLE_STATE = APPLE_STATE;
        }

        public int getSEX() {
            return SEX;
        }

        public void setSEX(int SEX) {
            this.SEX = SEX;
        }

        public int getSUPERIOR_USERID() {
            return SUPERIOR_USERID;
        }

        public void setSUPERIOR_USERID(int SUPERIOR_USERID) {
            this.SUPERIOR_USERID = SUPERIOR_USERID;
        }

        public int getID() {
            return ID;
        }

        public void setID(int ID) {
            this.ID = ID;
        }

        public String getISAGENT() {
            return ISAGENT;
        }

        public void setISAGENT(String ISAGENT) {
            this.ISAGENT = ISAGENT;
        }

        public String getPHONE_NUMBER() {
            return PHONE_NUMBER;
        }

        public void setPHONE_NUMBER(String PHONE_NUMBER) {
            this.PHONE_NUMBER = PHONE_NUMBER;
        }

        public static class CREATEDATEBean {
            private int date;
            private int day;
            private int hours;
            private int minutes;
            private int month;
            private int nanos;
            private int seconds;
            private long time;
            private int timezoneOffset;
            private int year;

            public int getDate() {
                return date;
            }

            public void setDate(int date) {
                this.date = date;
            }

            public int getDay() {
                return day;
            }

            public void setDay(int day) {
                this.day = day;
            }

            public int getHours() {
                return hours;
            }

            public void setHours(int hours) {
                this.hours = hours;
            }

            public int getMinutes() {
                return minutes;
            }

            public void setMinutes(int minutes) {
                this.minutes = minutes;
            }

            public int getMonth() {
                return month;
            }

            public void setMonth(int month) {
                this.month = month;
            }

            public int getNanos() {
                return nanos;
            }

            public void setNanos(int nanos) {
                this.nanos = nanos;
            }

            public int getSeconds() {
                return seconds;
            }

            public void setSeconds(int seconds) {
                this.seconds = seconds;
            }

            public long getTime() {
                return time;
            }

            public void setTime(long time) {
                this.time = time;
            }

            public int getTimezoneOffset() {
                return timezoneOffset;
            }

            public void setTimezoneOffset(int timezoneOffset) {
                this.timezoneOffset = timezoneOffset;
            }

            public int getYear() {
                return year;
            }

            public void setYear(int year) {
                this.year = year;
            }
        }
    }
}
