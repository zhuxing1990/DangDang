package com.hnshilin.ddwallet.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;

import com.hnshilin.ddwallet.R;
import com.hnshilin.ddwallet.base.BaseActivity;
import com.hnshilin.ddwallet.base.Configs;
import com.hnshilin.ddwallet.log.LogFileUtils;
import com.hnshilin.ddwallet.log.LogcatHelper;
import com.hnshilin.ddwallet.log.LogUtil;
import com.hnshilin.ddwallet.manage.AccessProdManager;
import com.hnshilin.ddwallet.manage.LoginManager;
import com.hnshilin.ddwallet.util.PermissionsUtils;
import com.hnshilin.ddwallet.util.SharedPreferencesUtil;
import com.hnshilin.ddwallet.util.Utils;

import java.io.File;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 欢迎页面
 */
public class SplashActivity extends BaseActivity {
    private static final String TAG = "SplashActivity";
    private static final String SPLASH = "splash";
    private int oldVersion = 0;
    private int newVersion;
    private static final String OLD_VERSION = "old_version";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        int permission = ActivityCompat.checkSelfPermission(mcontext, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        boolean hasPermission = permission== PackageManager.PERMISSION_GRANTED;
        if (hasPermission){
            LogcatHelper.getInstance(mcontext).start();
        }else{
            LogUtil.i(TAG, "onCreate: not permission");
            PermissionsUtils.verifyStoragePermissions(mcontext);
        }
        setContentView(R.layout.activity_splash);
        init();
    }

    private void init() {
        oldVersion = SharedPreferencesUtil.getIntValue(mcontext,OLD_VERSION,oldVersion);
        newVersion = Utils.getVersionCode(mcontext);
        if (newVersion!=oldVersion){//更新版本号
            SharedPreferencesUtil.setIntValue(mcontext,OLD_VERSION,newVersion);
        }
        if (oldVersion>=0&&oldVersion<newVersion){//如果版本大于0并且小与当前版本（版本号存在并且）
            // 删除老版本数据库并删除登录信息
            SharedPreferencesUtil.RemoveKey(mcontext,SharedPreferencesUtil.USER_ID);
            LoginManager.DelectLoginData(mcontext);
            cleanAppWeb(mcontext);
            SharedPreferencesUtil.RemoveKey(mcontext,SPLASH);
        }
       boolean isSplash= SharedPreferencesUtil.getBooleanValue(mcontext,SPLASH,true);
        if (isSplash){
            SharedPreferencesUtil.setBooleanValue(mcontext,SPLASH,false);
            startUp(GuideActivity.class);
        }else{
            startUp(MainActivity.class);
        }
        AccessProdManager.InsertByCenterApp(mcontext);
    }

    /**
     * 清理网页缓存
     * @param context
     */
    public static void cleanAppWeb(Context context) {
        try {
            File file = new File(context.getFilesDir().getParent()+"/app_webview");
            LogFileUtils.deleteFolderFile(file.getPath(),true);
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    private void startUp(final Class mclass ) {
        Observable.timer(3, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {
                this.unsubscribe();
            }

            @Override
            public void onNext(Long aLong) {
                Configs.intent = new Intent(mcontext,mclass);
                Configs.intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(Configs.intent);
                finish();
                this.unsubscribe();
            }
        });

    }
}
