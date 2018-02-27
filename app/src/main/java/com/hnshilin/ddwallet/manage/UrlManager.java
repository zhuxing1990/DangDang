package com.hnshilin.ddwallet.manage;

/**
 * Created by zhuxi on 2017/8/22.
 */
public class UrlManager {
    /**
     *  基础请求地址s
     */
    public static final String BaseUrl
            = "http://www.hnshilin.com:8080";//正式服务器
//          = "http://192.168.0.222:8080"; //测试服务器
//          = "http://167a24551o.51mypc.cn:29083"; //映射服务器
//          = "http://192.168.0.180:8088";//唐雄电脑
//          = "http://192.168.0.177:8020";//漆俊霞电脑
//          = "http://106.15.94.95:8080";//正式服务器 ip地址访问
    /** 服务器路径 */
    public static final String BASE_PATH
            = "http://106.15.94.95:8080";//正式
//            = "http://192.168.0.222:8080" ;// 测试服务器
    /**
     * 登录
     */
    public static final String Login = "/ddqb/appapi/login.do";
    /**s
     *  VIP广告轮播图
     */
    public static final String VIP_Advert = "/ddqb/pa/vip_advertisement.ns";

    /**
     * 获取所有热门产品
     */
    public static final String HotProd = "/ddqb/pa/hot_prod.ns";
    /**
     * 获取所有货款大全
     */
    public static final String All_Lending = "/ddqb/appapi/categoryProductList.do";
    /**
     * 单个产品详情
     */
    public static final String Prod_Details = "/ddqb/pa/prod_by_id.ns";
    /**
     * 甲方 产品 VIP1
     */
    public static final String Prod_cate_index1 = "/ddqb/pa/prod_cate_2_index1.ns";
    /**
     * 甲方 产品 VIP2
     */
    public static final String Prod_cate_index2 = "/ddqb/pa/prod_cate_2_index2.ns";
    /**
     * 获取信用卡信息
     */
    @Deprecated
    public static final String Credit_query = "/ddqb/pa/credit_query.ns";
    /**
     * 获取单个产品评论
     */
    public static final String Prod_Comments = "/ddqb/pa/prod_comments.ns";
    /**
     * 添加评论
     */
    public static final String Add_Prod_Comments = "/ddqb/pa/prod_comment_add.ns";
    /**
     * 版本检测
     */
    public static final String Update_APK = "/ddqb/appapi/versionCheck.do";
    /**
     *  注册网页
     */
    public static final String Register = "/ddqbWeb/reg.html";
    /**
     * 信用卡网页
     */
    public static final String CreditCardUrl = "/ddqbWeb/card.html";
    /**
     * 我的
     */
    public static final String  PersonalUrl = "/ddqbWeb/index.html";
    /**
     * 加载失败网页
     */
    public static final String NotFondUrl = "file:///android_asset/notFond.html";
    /**
     * 点击立即申请真实点击量+1
     */
    public static final String Realdownmount = "/ddqb/pa/realdownmount_upt.ns";
    /**
     *点击产品,进入详情UV,PV接口
     */
    public static final String InsertByClickProduct = "/ddqb/pa/insertByClickProduct.do";
    /**
     *立即申请PV接口
     */
    public static final String InsertByClickApply = "/ddqb/pa/insertByClickApply.do";
    /**
     *进入APP UV接口
     */
    public static final String InsertByCenterApp = "/ddqb/pa/insertByCenterApp.do";
    /**
     * 支付接口
     */
    public static final String AddYLOrder = "/ddqb/appapi/addYLOrder.do";
    /**
     * 微信登录
     */
    public static final String AppWxLogin = "/ddqb/appapi/appWxLogin.do";
    /**
     * 支付后台验证
     */
    public static final String YL_verify = "/ddqb/appapi/yl_verify.do";
}
