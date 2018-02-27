package com.hnshilin.ddwallet.fragment;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.google.gson.Gson;
import com.hnshilin.ddwallet.R;
import com.hnshilin.ddwallet.adapter.AllLending_Adapter;
import com.hnshilin.ddwallet.base.HeaderViewPagerFragment;
import com.hnshilin.ddwallet.log.LogUtil;
import com.hnshilin.ddwallet.manage.UrlManager;
import com.hnshilin.ddwallet.mod.AllLendingBean;
import com.hnshilin.ddwallet.util.NetUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 货款大全
 * Created by zhuxi on 2017/8/22.
 */
public class AllLendingFragment extends HeaderViewPagerFragment {
    private static final String TAG = "AllLendingFragment";
    public static AllLendingFragment newInstance(){
        return new AllLendingFragment();
    }

    private RecyclerView all_lending_recyclerview;
    private View all_lending_titleBar;
    private TextView titlebar_title;
    private ViewFlipper all_lendi_viewfilpper;
    private boolean HasData = false;
    private SwipeRefreshLayout all_lending_swiperefresh;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = initView(inflater,container);
        initData();
        initRx();
        initListener();
        return view;
    }

    @NonNull
    private View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_all_lending,container,false);
        all_lending_swiperefresh = (SwipeRefreshLayout) view.findViewById(R.id.all_lending_swiperefresh);
        all_lending_titleBar = view.findViewById(R.id.all_lending_titleBar);

        titlebar_title = (TextView) all_lending_titleBar.findViewById(R.id.titlebar_title);
        titlebar_title.setText(R.string.all_lending);
        all_lendi_viewfilpper = (ViewFlipper) view.findViewById(R.id.all_lending_viewfilpper);

        all_lending_recyclerview = (RecyclerView) view.findViewById(R.id.all_lending_recyclerview);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        all_lending_recyclerview.setLayoutManager(linearLayoutManager);
//        all_lending_recyclerview.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.HORIZONTAL));

        return view;
    }

    /**
     * 下拉刷新
     */
    private void initListener() {
        all_lending_swiperefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Observable.timer(2,TimeUnit.SECONDS).subscribeOn(Schedulers.io())
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
                                initData();
                                all_lending_swiperefresh.setRefreshing(false);
                                this.unsubscribe();
                            }
                        });
            }
        });
    }

    /**
     * 定时器，每10秒判断1次请求是否成功，
     * 请求成功之后关闭监听
     */
    private void initRx() {
        LogUtil.i(TAG, "initRx: initTimeOut ");
        Observable.timer(10, TimeUnit.SECONDS).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable e) {
                        LogUtil.i(TAG, "initRx onError: ");
                        this.unsubscribe();
                    }

                    @Override
                    public void onNext(Long aLong) {
                         if (NetUtils.isNetConnected(getActivity())){
                            if (HasData == false){
                                LogUtil.i(TAG, "initRx: get Data failed,timeout..");
                                initData();
                            }else{
                                LogUtil.i(TAG, "onNext: get Data Success");
                                this.unsubscribe();
                            }
                         }else{
                            LogUtil.i(TAG, "onNext: get newWork is disconnect");
                         }
                    }
                });
    }


    @Override
    public void onResume() {
        super.onResume();
        LogUtil.i(TAG, "onResume: ");
        if (HasData == false){
            initData();
        }
    }

    AllLendingBean allLendingBean;
    AllLending_Adapter allLendingAdapter;

    /**
     * 获取货款大全的数据
     */
    private void initData() {
        LogUtil.i(TAG, "initData: url:"+ UrlManager.BaseUrl+ UrlManager.All_Lending);
        OkGo.post(UrlManager.BaseUrl+ UrlManager.All_Lending)
                .tag(this).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                LogUtil.i(TAG, "onSuccess: s"+s);
                if (!TextUtils.isEmpty(s)){
                    try {
                        JSONObject json = new JSONObject(s);
                        if (json.has("code")){
                            int code = json.getInt("code");
                            switch(code){
                                case 0:
                                    allLendingBean = new Gson().fromJson(s,AllLendingBean.class);
                                    break;
                                default:
                                    break;
                            }
                        }
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                LogUtil.i(TAG, "initData onError: ");
            }

            @Override
            public void onAfter(String s, Exception e) {
                super.onAfter(s, e);
                LogUtil.i(TAG, "onAfter: ");
                if (allLendingBean !=null){
                    if (allLendingBean.getData()!=null&& allLendingBean.getData().size()!=0){
                        allLendingAdapter = new AllLending_Adapter(getActivity(), allLendingBean);
                        all_lending_recyclerview.setAdapter(allLendingAdapter);
                        if (all_lending_recyclerview.getVisibility()!=View.VISIBLE)all_lending_recyclerview.setVisibility(View.VISIBLE);
                        HasData = true;
                    }else{
                        all_lending_recyclerview.setVisibility(View.GONE);
                    }
                }else{
                        all_lending_recyclerview.setVisibility(View.GONE);
                }
            }
        });
    }

    @Override
    public View getScrollableView() {
        return null;
    }
}
