package com.hnshilin.ddwallet.base;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.alipay.sdk.app.EnvUtils;
import com.hnshilin.ddwallet.util.AppManager;


/**
 * Created by zhuxi on 2017/3/9.
 */
public class BaseActivity extends AppCompatActivity {
    public BaseActivity mcontext;

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

}
