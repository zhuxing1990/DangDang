package com.hnshilin.ddwallet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.hnshilin.ddwallet.R;
import com.hnshilin.ddwallet.adapter.MypagerAdapter;
import com.hnshilin.ddwallet.base.BaseActivity;
import com.hnshilin.ddwallet.base.Configs;
import com.hnshilin.ddwallet.util.Utils;
import com.lzy.widget.tab.CircleIndicator;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 引导页
 */
public class GuideActivity extends BaseActivity  implements ViewPager.OnPageChangeListener{
    private static final String TAG = "GuideActivity";
    private ViewPager pager;
    private List<View> list;
    private MypagerAdapter ar;
    private ImageView[] points;
    private static final int[] pics = { R.drawable.guide_one, R.drawable.guide_two,
            R.drawable.guide_three };
    private int currentIndex;// 记录当前位置
    private CircleIndicator guide_circleIndicator;
    private Button guide_goactivity_but;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_guide);
        init();
        initDate();
    }

    public void init() {
        pager = (ViewPager) findViewById(R.id.guide_viewpager);
        guide_circleIndicator = (CircleIndicator) findViewById(R.id.guide_circleIndicator);
        guide_goactivity_but = (Button) findViewById(R.id.guide_goactivity_but);
        list = new ArrayList<View>();
        ar = new MypagerAdapter(list);
        guide_goactivity_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (Utils.isFastDoubleClick(5000)){
                    Finish();
                }

            }
        });
    }
    ImageView imageView;
    public void initDate() {
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                LinearLayout.LayoutParams.MATCH_PARENT,
                LinearLayout.LayoutParams.MATCH_PARENT);
        for (int i = 0; i < pics.length; i++) {
            imageView = new ImageView(this);
            imageView.setScaleType(ImageView.ScaleType.FIT_XY);
            imageView.setLayoutParams(params);
            imageView.setBackgroundResource(pics[i]);
            list.add(imageView);
        }
        pager.setAdapter(ar);
        pager.addOnPageChangeListener(this);
        guide_circleIndicator.setViewPager(pager);
    }



    // 在滑动状态时调用
    @Override
    public void onPageScrollStateChanged(int arg0) {

    }

    // 在当前页面被滑动时被调用
    @Override
    public void onPageScrolled(int arg0, float arg1, int arg2) {

    }

    // 在新的页面被选中时调用
    @Override
    public void onPageSelected(int position) {
        setCurDot(position);
    }


    // 设置小点位置
    public void setCurDot(int position) {
        if (position < 0 || position > pics.length - 1
                || currentIndex == position) {
            return;
        }
        currentIndex = position;
        if (position == 2 && currentIndex == 2) {
           guide_goactivity_but .setVisibility(View.VISIBLE);
        } else {
           guide_goactivity_but .setVisibility(View.GONE);
        }
    }

    public void Finish() {
        Observable.timer(100, TimeUnit.MICROSECONDS).subscribeOn(Schedulers.io())
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
                        Configs.intent = new Intent(mcontext, MainActivity.class);
                        Configs.intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(Configs.intent);
                        finish();
                        this.unsubscribe();
                    }
                });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }
}
