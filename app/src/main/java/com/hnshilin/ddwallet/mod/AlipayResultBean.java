package com.hnshilin.ddwallet.mod;

/**
 * Created by zhuxi on 2017/9/29.
 */
public class AlipayResultBean {
    private String AlipayResult_CODE = "-999";
    private String AlipayRequest_Info;
    private String AlipayResult_Response;
    private String AlipayResult_Remarks;
    private String AlipayResult_Info;
    public String getAlipayResult_CODE() {
        return AlipayResult_CODE;
    }

    public void setAlipayResult_CODE(String alipayResult_CODE) {
        AlipayResult_CODE = alipayResult_CODE;
    }

    public String getAlipayResult_Response() {
        return AlipayResult_Response;
    }

    public void setAlipayResult_Response(String alipayResult_Response) {
        AlipayResult_Response = alipayResult_Response;
    }

    public String getAlipayResult_Remarks() {
        return AlipayResult_Remarks;
    }

    public void setAlipayResult_Remarks(String alipayResult_Remarks) {
        AlipayResult_Remarks = alipayResult_Remarks;
    }

    public String getAlipayRequest_Info() {
        return AlipayRequest_Info;
    }

    public void setAlipayRequest_Info(String alipayRequest_Info) {
        AlipayRequest_Info = alipayRequest_Info;
    }

    public String getAlipayResult_Info() {
        return AlipayResult_Info;
    }

    public void setAlipayResult_Info(String alipayResult_Info) {
        AlipayResult_Info = alipayResult_Info;
    }

    @Override
    public String toString() {
        return "AlipayResultBean{" +
                "AlipayResult_CODE='" + AlipayResult_CODE + '\'' +
                ", AlipayRequest_Info='" + AlipayRequest_Info + '\'' +
                ", AlipayResult_Response='" + AlipayResult_Response + '\'' +
                ", AlipayResult_Remarks='" + AlipayResult_Remarks + '\'' +
                ", AlipayResult_Info='" + AlipayResult_Info + '\'' +
                '}';
    }
}
