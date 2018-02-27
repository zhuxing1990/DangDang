package com.hnshilin.ddwallet.base;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;

import com.hnshilin.ddwallet.log.LogUtil;
import com.hnshilin.ddwallet.manage.LoginManager;
import com.hnshilin.ddwallet.util.SharedPreferencesUtil;

/**
 * Created by zhuxi on 2017/3/25.
 */
public class Configs {
    private static final String TAG = "Configs";
    public static Intent intent = null;// 意图
    /**暂不更新**/
    public static final String UPDATE_TOMORROW = "update_tomorrow";
    /**默认的值**/
    public static long defaultValue = 0;
    /**
     * 设备类型 1 安卓 2 苹果
     */
    public static final int systemType = 1;

    public static int getUserId(Activity context) {
        int userId = -10;
        String userName = SharedPreferencesUtil.getStringValue(context,SharedPreferencesUtil.USER_ID,"");
        if (!TextUtils.isEmpty(userName)){
            userId = LoginManager.QueryUserId(context, userName);
            if (userId!=-10){
                LogUtil.i(TAG, " userId:"+userId);
            }
        }
        return userId;
    }
}
