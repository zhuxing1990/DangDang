package com.hnshilin.ddwallet.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.google.gson.Gson;
import com.hnshilin.ddwallet.R;
import com.hnshilin.ddwallet.activity.DetailsDataActivity;
import com.hnshilin.ddwallet.activity.WebLoadActivity;
import com.hnshilin.ddwallet.adapter.HeaderAdapter;
import com.hnshilin.ddwallet.adapter.HotContentAdapter;
import com.hnshilin.ddwallet.base.Configs;
import com.hnshilin.ddwallet.base.HeaderViewPagerFragment;
import com.hnshilin.ddwallet.log.LogUtil;
import com.hnshilin.ddwallet.manage.AccessProdManager;
import com.hnshilin.ddwallet.manage.UrlManager;
import com.hnshilin.ddwallet.mod.HotProdBean;
import com.hnshilin.ddwallet.mod.ProdCateBean;
import com.hnshilin.ddwallet.mod.VipAdvertBean;
import com.hnshilin.ddwallet.util.GlideUtils;
import com.hnshilin.ddwallet.util.NetUtils;
import com.hnshilin.ddwallet.view.DividerGridItemDecoration;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;
import com.lzy.widget.tab.CircleIndicator;

import org.json.JSONObject;

import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Response;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;

/**
 * 热门
 * Created by zhuxi on 2017/8/22.
 */
public class HotFragment extends HeaderViewPagerFragment {
    private static final String TAG = "HotFragment";
    public static HotFragment newInstance() {
        return new HotFragment();
    }


    private  ViewPager pagerHeader;
//    private RecyclerView hot_funcion_recycler;
    private RecyclerView hot_content_rectcler;
    private   CircleIndicator ci;
    private ViewFlipper hot_viewfilpper;
    private SwipeRefreshLayout hot_swipeRefresh;

    private LinearLayout hot_fuuction_rl1;
    private LinearLayout hot_fuuction_rl2;
    private LinearLayout hot_fuuction_rl3;
    private ImageView hotfunction_img1;
    private ImageView hotfunction_img2;
    private ImageView hotfunction_img3;
    private TextView hotfunction_title1;
    private TextView hotfunction_title2;
    private TextView hotfunction_title3;
    private TextView hotfunction_content1;
    private TextView hotfunction_content2;
    private TextView hotfunction_content3;
    private View hot_titleBar;
    private TextView titlebar_title;
    private boolean HasVipData = false;
    private boolean HasProdCateData1 = false;
    private boolean HasProdCateData2 = false;
    private boolean HasHorProdData = false;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = initView(inflater, container);
        initVipAdvert();
        initData();
        initData2();
        initRx();
        initListener();
        return view;
    }
    @NonNull
    private View initView(LayoutInflater inflater, @Nullable ViewGroup container) {
        View view = inflater.inflate(R.layout.fragment_hot, container, false);
        hot_titleBar = view.findViewById(R.id.hot_titleBar);
        titlebar_title = (TextView) hot_titleBar.findViewById(R.id.titlebar_title);
        titlebar_title.setText(R.string.hot);

        pagerHeader = (ViewPager)view.findViewById(R.id.hot_pagerHeader);
        ci = (CircleIndicator)view.findViewById(R.id.hot_circleIndicator);

        hot_viewfilpper = (ViewFlipper) view.findViewById(R.id.hot_viewfilpper);

        hot_fuuction_rl1 = (LinearLayout) view.findViewById(R.id.hot_fuuction_rl1);
        hot_fuuction_rl2 = (LinearLayout) view.findViewById(R.id.hot_fuuction_rl2);
        hot_fuuction_rl3 = (LinearLayout) view.findViewById(R.id.hot_fuuction_rl3);

        hotfunction_img1 = (ImageView) view.findViewById(R.id.hotfunction_img1);
        hotfunction_img2 = (ImageView) view.findViewById(R.id.hotfunction_img2);
        hotfunction_img3 = (ImageView) view.findViewById(R.id.hotfunction_img3);

        hotfunction_title1 = (TextView) view.findViewById(R.id.hotfunction_title1);
        hotfunction_title2 = (TextView) view.findViewById(R.id.hotfunction_title2);
        hotfunction_title3 = (TextView) view.findViewById(R.id.hotfunction_title3);

        hotfunction_content1 = (TextView) view.findViewById(R.id.hotfunction_content1);
        hotfunction_content2 = (TextView) view.findViewById(R.id.hotfunction_content2);
        hotfunction_content3 = (TextView) view.findViewById(R.id.hotfunction_content3);
//        hot_funcion_recycler = (RecyclerView) view.findViewById(R.id.hot_funcion_recycler);
//        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
//        linearLayoutManager.setOrientation(LinearLayoutManager.HORIZONTAL);
//        hot_funcion_recycler.setLayoutManager(linearLayoutManager);
        hot_swipeRefresh = (SwipeRefreshLayout) view.findViewById(R.id.hot_swipeRefresh);
        hot_content_rectcler = (RecyclerView) view.findViewById(R.id.hot_content_rectcler);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
        hot_content_rectcler.setLayoutManager(gridLayoutManager);
//        FullyGridLayoutManager gridLayoutManager = new FullyGridLayoutManager(getActivity(),2);
//        gridLayoutManager.setOrientation(FullyGridLayoutManager.VERTICAL);
//        hot_content_rectcler.setLayoutManager(gridLayoutManager);
//        hot_content_rectcler.addItemDecoration(new DividerItemDecoration(getActivity(),DividerItemDecoration.VERTICAL));
        hot_content_rectcler.addItemDecoration(new DividerGridItemDecoration(getActivity()));
        hot_content_rectcler.setNestedScrollingEnabled(false);
        return view;
    }
    private void initListener() {
        hot_swipeRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
                                initVipAdvert();
                                initData();
                                initData2();
                                hot_swipeRefresh.setRefreshing(false);
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
        LogUtil.i(TAG, "initRx: initTimeOut");
        Observable.timer(10, TimeUnit.SECONDS)
                .subscribeOn(Schedulers.io())
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
                           if (HasVipData&&HasProdCateData1&&HasProdCateData2&&HasProdCateData2){
                               LogUtil.i(TAG, "onNext: get All Data is success");
                               this.unsubscribe();
                           }else{
                               if(HasVipData==false){
                                   LogUtil.i(TAG, "onNext: get VipData ");
                                    initVipAdvert();
                               }else if(HasProdCateData1 == false){
                                   LogUtil.i(TAG, "onNext: get HasProdCateData1 ");
                                   GetProdCateData1();
                               }else if (HasProdCateData2 == false){
                                   LogUtil.i(TAG, "onNext: get HasProdCateData2 ");
                                   GetProdCateData2();
                               }else if(HasHorProdData == false) {
                                   LogUtil.i(TAG, "onNext: get HasHorProdData ");
                                   initData2();
                               }
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
        if (HasVipData==false){
            initVipAdvert();
        }
        if (HasProdCateData1 == false){
            GetProdCateData1();
        }
        if (HasProdCateData2 == false){
            GetProdCateData2();
        }
        if (HasHorProdData == false){
            initData2();
        }
    }



    private VipAdvertBean vipAdvertBean;
    private void initVipAdvert() {
        LogUtil.i(TAG, "initVipAdvert: "+UrlManager.BaseUrl+ UrlManager.VIP_Advert);
        OkGo.post(UrlManager.BaseUrl+ UrlManager.VIP_Advert).tag(this)
                .execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                LogUtil.i(TAG, "initVipAdvert onSuccess: s:"+s);
                if (!TextUtils.isEmpty(s))
                try {
                    JSONObject js = new JSONObject(s);
                    if (js.has("code")){
                        int code = js.getInt("code");
                        switch (code){
                            case 0:
                                vipAdvertBean = new Gson().fromJson(s,VipAdvertBean.class);
                                break;
                            default:
                                HasVipData = true;
                                break;
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                LogUtil.i(TAG, "initVipAdvert onError: ");
                HasVipData = false;
            }

            @Override
            public void onAfter(String s, Exception e) {
                super.onAfter(s, e);
                LogUtil.i(TAG, "initVipAdvert onAfter: ");
                if (vipAdvertBean!=null){
                    if (vipAdvertBean.getData()!=null&&vipAdvertBean.getData().size()!=0){
                        pagerHeader.setAdapter(new HeaderAdapter(getActivity(),vipAdvertBean));
                        ci.setViewPager(pagerHeader);
                        HasVipData = true;
                    }else{
                        HasVipData = true;
                    }
                }
            }
        });
    }

    HotContentAdapter contentAdapter;
    HotProdBean hotProdBean;
    private void initData2() {

        OkGo.post(UrlManager.BaseUrl+ UrlManager.HotProd)
                .tag(this)
                .execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                LogUtil.i(TAG, "initData2 onSuccess: s"+s);
                if (!TextUtils.isEmpty(s))
                try {
                    JSONObject js = new JSONObject(s);
                    if (js.has("code")){
                        int code = js.getInt("code");
                        switch (code){
                            case 0:
                                hotProdBean = new Gson().fromJson(s,HotProdBean.class);
                                break;
                            default:

                                break;
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                LogUtil.i(TAG, "initData2 onError: ");
                HasHorProdData = false;
            }

            @Override
            public void onAfter(String s, Exception e) {
                super.onAfter(s, e);
                LogUtil.i(TAG, "initData2 onAfter: ");
                if (hotProdBean!=null){
                    if (hotProdBean.getData()!=null&&hotProdBean.getData().size()!=0){
                        contentAdapter = new HotContentAdapter(getActivity(),hotProdBean);
                        hot_content_rectcler.setAdapter(contentAdapter);
                        HasHorProdData = true;
                    }
                }
            }
        });
    }




    private void initData() {
        GetProdCateData1();
        GetProdCateData2();
    }
    private ProdCateBean prodCateBean1;
    private void GetProdCateData1() {
        OkGo.post(UrlManager.BaseUrl+ UrlManager.Prod_cate_index1)
                .tag(this).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                LogUtil.i(TAG, "Prod_cate_index1 onSuccess: s:"+s);
                if (!TextUtils.isEmpty(s))
                try {
                    JSONObject js = new JSONObject(s);
                    if (js.has("code")){
                        int code = js.getInt("code");
                        switch (code){
                            case 0:
                                prodCateBean1 = new Gson().fromJson(s,ProdCateBean.class);
                                break;
                            default:
                                HasProdCateData1 = true;
                                break;
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                LogUtil.i(TAG, "Prod_cate_index1 onError: ");
                HasProdCateData1 = false;
            }

            @Override
            public void onAfter(String s, Exception e) {
                super.onAfter(s, e);
                LogUtil.i(TAG, "Prod_cate_index1 onAfter: ");
                if (prodCateBean1!=null){
                    if (prodCateBean1.getData()!=null&& prodCateBean1.getData().size()!=0){
                        LogUtil.i(TAG, "onAfter: prodDetailsBean.size:"+prodCateBean1.getData().size());
                        if (prodCateBean1.getData().size()==1){
                            final ProdCateBean.DataBean dataBean = prodCateBean1.getData().get(0);
                            GlideUtils.getInstance().LoadContextBitmap(getActivity(),dataBean.getPROD_ICON_URL(),hotfunction_img1,R.drawable.touming,R.drawable.touming,null);
                            hotfunction_title1.setText(dataBean.getPNAME());
                            hotfunction_content1.setText(dataBean.getCOMMENTT());
                            hot_fuuction_rl1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startDetailsPage(dataBean.getPROD_ID());
                                }
                            });
                            HasProdCateData1 = true;
                        }else{
                            final ProdCateBean.DataBean dataBean = prodCateBean1.getData().get(0);
                            GlideUtils.getInstance().LoadContextBitmap(getActivity(),dataBean.getPROD_ICON_URL(),hotfunction_img1,R.drawable.touming,R.drawable.touming,null);
                            hotfunction_title1.setText(dataBean.getPNAME());
                            hotfunction_content1.setText(dataBean.getCOMMENTT());
                            hot_fuuction_rl1.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startDetailsPage(dataBean.getPROD_ID());
                                }
                            });
                            HasProdCateData1 = true;
                            LogUtil.i(TAG, "Prod_cate_index1 get data size >1");
                        }
                    }else{
                        LogUtil.i(TAG, "获取详细信息失败，请稍后再试！");
                        HasProdCateData1 = true;
                    }
                 }
            }
        });
    }



    private ProdCateBean prodCateBean2;
    private void GetProdCateData2() {
        OkGo.post(UrlManager.BaseUrl+ UrlManager.Prod_cate_index2).tag(this)
                .execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                LogUtil.i(TAG, "Prod_cate_index2 onSuccess: s:"+s);
                if (!TextUtils.isEmpty(s))
                try {
                    JSONObject js = new JSONObject(s);
                    if (js.has("code")){
                        int code = js.getInt("code");
                        switch (code){
                            case 0:
                                prodCateBean2 = new Gson().fromJson(s,ProdCateBean.class);
                                break;
                            default:

                                break;
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                LogUtil.i(TAG, "Prod_cate_index2 onError: ");
                HasProdCateData2 = false;
            }

            @Override
            public void onAfter(String s, Exception e) {
                super.onAfter(s, e);
                LogUtil.i(TAG, "Prod_cate_index2 onAfter: ");
                if (prodCateBean2!=null){
                    if (prodCateBean2.getData()!=null&& prodCateBean2.getData().size()!=0){
                        LogUtil.i(TAG, "onAfter: prodDetailsBean.size:"+prodCateBean2.getData().size());
                        if (prodCateBean2.getData().size()==1){
                            final ProdCateBean.DataBean dataBean = prodCateBean2.getData().get(0);
                            GlideUtils.getInstance().LoadContextBitmap(getActivity(),dataBean.getPROD_ICON_URL(),hotfunction_img2,R.drawable.touming,R.drawable.touming,null);
                            hotfunction_title2.setText(dataBean.getPNAME());
                            hotfunction_content2.setText(dataBean.getCOMMENTT());
                            hot_fuuction_rl2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startDetailsPage(dataBean.getPROD_ID());
                                }
                            });
                            HasProdCateData2 = true;
                        }else{
                            final ProdCateBean.DataBean dataBean = prodCateBean2.getData().get(0);
                            GlideUtils.getInstance().LoadContextBitmap(getActivity(),dataBean.getPROD_ICON_URL(),hotfunction_img2,R.drawable.touming,R.drawable.touming,null);
                            hotfunction_title2.setText(dataBean.getPNAME());
                            hotfunction_content2.setText(dataBean.getCOMMENTT());
                            hot_fuuction_rl2.setOnClickListener(new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    startDetailsPage(dataBean.getPROD_ID());
                                }
                            });
                            HasProdCateData2 = true;
                            LogUtil.i(TAG, "Prod_cate_index2 get data size >1");
                        }
                    }else{
                        LogUtil.i(TAG, "Prod_cate_index2 获取详细信息失败，请稍后再试！");
                    }
                }
            }
        });
        hot_fuuction_rl3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Configs.intent = new Intent(getActivity(), WebLoadActivity.class);
                Configs.intent.putExtra("webdata",UrlManager.BaseUrl+UrlManager.CreditCardUrl);
                startActivity(Configs.intent);
            }
        });
    }

    private void startDetailsPage(int prod_id) {
        Configs.intent = new Intent(getActivity(), DetailsDataActivity.class);
        Configs.intent.putExtra("prod_id",prod_id);
        Configs.intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(Configs.intent);
        AccessProdManager.InsertByClickProduct(getActivity(),prod_id);
    }
    @Override
    public View getScrollableView() {
        return hot_content_rectcler;
    }
}
