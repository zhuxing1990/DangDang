package com.hnshilin.ddwallet.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.widget.Toast;

import com.hnshilin.ddwallet.log.LogUtil;
import com.hnshilin.ddwallet.util.AppManager;


/**
 * Created by zhuxi on 2017/5/23.
 */
public class BaseFragementActivity extends FragmentActivity {
    public BaseFragementActivity mcontext;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //禁止横屏
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        mcontext = this;
        // 添加Activity到堆栈
        AppManager.getAppManager().addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // 结束Activity&从堆栈中移除
        AppManager.getAppManager().finishActivity(this);
    }

    /**
     * 吐司
     */
    public void showToast(String string) {
        Toast.makeText(getApplicationContext(), string, Toast.LENGTH_SHORT)
                .show();
    }

    @Override
    public void onTrimMemory(int level) {
        super.onTrimMemory(level);
        switch(level){
            case TRIM_MEMORY_UI_HIDDEN:
                LogUtil.i(mcontext.getLocalClassName(), "onTrimMemory: level:TRIM_MEMORY_UI_HIDDEN");
                break;
            case TRIM_MEMORY_RUNNING_MODERATE:
                LogUtil.i(mcontext.getLocalClassName(), "onTrimMemory: level:TRIM_MEMORY_RUNNING_MODERATE");
                break;
            case TRIM_MEMORY_RUNNING_LOW:
                LogUtil.i(mcontext.getLocalClassName(), "onTrimMemory: level:TRIM_MEMORY_RUNNING_LOW");
                break;
            case TRIM_MEMORY_RUNNING_CRITICAL:
                LogUtil.i(mcontext.getLocalClassName(), "onTrimMemory: level:TRIM_MEMORY_RUNNING_CRITICAL");
                break;
        }
        System.gc();
    }
}
