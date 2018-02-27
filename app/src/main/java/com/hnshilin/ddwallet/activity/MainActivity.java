package com.hnshilin.ddwallet.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.hnshilin.ddwallet.R;
import com.hnshilin.ddwallet.base.BaseFragementActivity;
import com.hnshilin.ddwallet.base.Configs;
import com.hnshilin.ddwallet.base.HeaderViewPagerFragment;
import com.hnshilin.ddwallet.base.MyFragmentPagerAdapter;
import com.hnshilin.ddwallet.fragment.AllLendingFragment;
import com.hnshilin.ddwallet.fragment.HotFragment;
import com.hnshilin.ddwallet.fragment.PersonalFragment;
import com.hnshilin.ddwallet.log.LogcatHelper;
import com.hnshilin.ddwallet.log.LogUtil;
import com.hnshilin.ddwallet.manage.UpdateApkManager;
import com.hnshilin.ddwallet.util.NetUtils;
import com.hnshilin.ddwallet.util.SharedPreferencesUtil;
import com.hnshilin.ddwallet.util.Utils;
import com.hnshilin.ddwallet.view.AlertDialog;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 首页
 */
public class MainActivity extends BaseFragementActivity implements View.OnClickListener{
    private static final String TAG = "MainActivity";
    private List<HeaderViewPagerFragment> fragments;
    private ViewPager maint_viewPager;
    private LinearLayout main_home;
    private LinearLayout main_center;
    private LinearLayout main_right;

    private TextView main_center_text;
    private TextView main_right_text;
    private TextView main_home_text;
    private ImageView main_center_img;
    private ImageView main_right_img;
    private ImageView main_home_img;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        if (NetUtils.isNetConnected(mcontext)){
            initViewPager();
        }else{
            Utils.FinishOrRestart(mcontext);
        }
    }
    private void initView() {
        maint_viewPager = (ViewPager) findViewById(R.id.maint_viewPager);
        main_home = (LinearLayout) findViewById(R.id.main_home);
        main_center = (LinearLayout) findViewById(R.id.main_center);
        main_right = (LinearLayout) findViewById(R.id.main_right);

        main_home_text = (TextView) findViewById(R.id.main_home_text);
        main_center_text = (TextView) findViewById(R.id.main_center_text);
        main_right_text = (TextView) findViewById(R.id.main_right_text);

        main_center_img = (ImageView) findViewById(R.id.main_center_img);
        main_right_img = (ImageView) findViewById(R.id.main_right_img);
        main_home_img = (ImageView) findViewById(R.id.main_home_img);

        main_home.setOnClickListener(this);
        main_center.setOnClickListener(this);
        main_right.setOnClickListener(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        initUpdate();
    }
    /**
     * 版本更新
     */
    private void initUpdate() {
        if (NetUtils.isNetConnected(mcontext)) {
            long theDate = System.currentTimeMillis();
            long isSameToday = (long) SharedPreferencesUtil.getLongValue(mcontext,
                    Configs.UPDATE_TOMORROW, Configs.defaultValue);
            boolean sameToday = Utils.isSameToday(theDate, isSameToday);
            if (sameToday) {
                LogUtil.e("UpdateManager", "theDate:" + theDate
                        + "\n isSameToday:" + isSameToday);
                LogUtil.e("UpdateManager", "暂不更新");
                return;
            } else {
                LogUtil.e("UpdateManager", "theDate:" + theDate
                        + "\n isSameToday:" + isSameToday);
                LogUtil.e("UpdateManager", "检测更新");
                int permission = ActivityCompat.checkSelfPermission(mcontext, Manifest.permission.WRITE_EXTERNAL_STORAGE);
                boolean hasPermission = permission== PackageManager.PERMISSION_GRANTED;
                if (hasPermission) {
                    UpdateApkManager.getInstance().GetUpdateInfo(mcontext);
                }else{
                    LogUtil.i(TAG, "onCreate: not permission");
                };
            }
        }
    }


    private void initViewPager() {
        fragments = new ArrayList<>();
        fragments.add(HotFragment.newInstance());
        fragments.add(AllLendingFragment.newInstance());
        fragments.add(PersonalFragment.newInstance());

        //tab标签和内容viewpager
        MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager(),fragments);
        maint_viewPager.setAdapter(adapter);
        maint_viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                OnPagerSelected(position);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
        main_home.setSelected(true);
        main_center.setSelected(false);
        main_right.setSelected(false);
        main_center_img.setSelected(true);
        main_home_text.setTextColor(Color.parseColor("#333333"));
        maint_viewPager.setOffscreenPageLimit(2);
    }



    private void OnPagerSelected(int position) {
        maint_viewPager.setCurrentItem(position);
        switch (position){
            case 0:
                main_home.setSelected(true);
                main_center.setSelected(false);
                main_right.setSelected(false);

                main_home_img.setSelected(true);
                main_center_img.setSelected(false);
                main_right_img.setSelected(false);
                main_home_text.setTextColor(Color.parseColor("#333333"));
                main_center_text.setTextColor(Color.parseColor("#999999"));
                main_right_text.setTextColor(Color.parseColor("#999999"));
                break;
            case 1:
                main_home.setSelected(false);
                main_center.setSelected(true);
                main_right.setSelected(false);

                main_home_img.setSelected(false);
                main_center_img.setSelected(true);
                main_right_img.setSelected(false);
                main_home_text.setTextColor(Color.parseColor("#999999"));
                main_center_text.setTextColor(Color.parseColor("#333333"));
                main_right_text.setTextColor(Color.parseColor("#999999"));
                break;

            case 2:
                main_home.setSelected(false);
                main_center.setSelected(false);
                main_right.setSelected(true);

                main_home_img.setSelected(false);
                main_center_img.setSelected(false);
                main_right_img.setSelected(true);

                main_home_text.setTextColor(Color.parseColor("#999999"));
                main_center_text.setTextColor(Color.parseColor("#999999"));
                main_right_text.setTextColor(Color.parseColor("#333333"));
                break;
            default:
                break;
        }
    }
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_home:
                maint_viewPager.setCurrentItem(0);
                break;
            case R.id.main_center:
                maint_viewPager.setCurrentItem(1);
                break;
            case R.id.main_right:
                maint_viewPager.setCurrentItem(2);
                break;
        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            showBackDiaLog() ;
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }


    private AlertDialog dialog;

    /**
     * 退出提示框
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void showBackDiaLog() {
        Observable.timer(0, TimeUnit.SECONDS).subscribeOn(Schedulers.io())
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
                        dialog =   new AlertDialog(mcontext);
                        dialog.builder()
                                .setCancelable(false)
                                .setTitle("当当钱包提醒").setMsg("确定退出应用程序吗?")
                                .setNegativeButton("确定", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        android.os.Process.killProcess(android.os.Process.myPid());
                                    }
                                })
                                .setPositiveButton("取消", new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {

                                    }
                                });
                            dialog .show();
                        this.unsubscribe();
                    }
                });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        LogUtil.i(TAG, "onDestroy: ");
        if (dialog!=null&&dialog.isShowing()){
            dialog.cancel();
        }
        LogcatHelper.getInstance(mcontext).stop();
    }


}
