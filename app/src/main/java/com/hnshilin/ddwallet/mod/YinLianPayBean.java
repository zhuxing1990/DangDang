package com.hnshilin.ddwallet.mod;

import java.util.List;

/**
 * Created by zhuxi on 2017/8/26.
 */
public class YinLianPayBean {

    /**
     * code : 0
     * data : [{"createDate":{"date":26,"day":6,"hours":16,"minutes":31,"month":7,"seconds":2,"time":1503736262504,"timezoneOffset":-480,"year":117},"money":"0","orderName":"升级为代理","orderNo":"20170826163101158860","orderType":"1","state":0,"userId":"1385"}]
     * message : SUCCESS
     * tn : 635008945521287537904
     */

    private int code;
    private String message;
    private String tn;
    /**
     * createDate : {"date":26,"day":6,"hours":16,"minutes":31,"month":7,"seconds":2,"time":1503736262504,"timezoneOffset":-480,"year":117}
     * money : 0
     * orderName : 升级为代理
     * orderNo : 20170826163101158860
     * orderType : 1
     * state : 0
     * userId : 1385
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

    public String getTn() {
        return tn;
    }

    public void setTn(String tn) {
        this.tn = tn;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * date : 26
         * day : 6
         * hours : 16
         * minutes : 31
         * month : 7
         * seconds : 2
         * time : 1503736262504
         * timezoneOffset : -480
         * year : 117
         */

        private CreateDateBean createDate;
        private String money;
        private String orderName;
        private String orderNo;
        private String orderType;
        private int state;
        private String userId;

        public CreateDateBean getCreateDate() {
            return createDate;
        }

        public void setCreateDate(CreateDateBean createDate) {
            this.createDate = createDate;
        }

        public String getMoney() {
            return money;
        }

        public void setMoney(String money) {
            this.money = money;
        }

        public String getOrderName() {
            return orderName;
        }

        public void setOrderName(String orderName) {
            this.orderName = orderName;
        }

        public String getOrderNo() {
            return orderNo;
        }

        public void setOrderNo(String orderNo) {
            this.orderNo = orderNo;
        }

        public String getOrderType() {
            return orderType;
        }

        public void setOrderType(String orderType) {
            this.orderType = orderType;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public String getUserId() {
            return userId;
        }

        public void setUserId(String userId) {
            this.userId = userId;
        }

        public static class CreateDateBean {
            private int date;
            private int day;
            private int hours;
            private int minutes;
            private int month;
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
