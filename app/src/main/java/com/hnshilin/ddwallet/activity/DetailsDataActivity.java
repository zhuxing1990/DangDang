package com.hnshilin.ddwallet.activity;

import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hnshilin.ddwallet.R;
import com.hnshilin.ddwallet.adapter.CommentsAdapter;
import com.hnshilin.ddwallet.base.BaseActivity;
import com.hnshilin.ddwallet.base.Configs;
import com.hnshilin.ddwallet.log.LogUtil;
import com.hnshilin.ddwallet.manage.AccessProdManager;
import com.hnshilin.ddwallet.manage.LoginManager;
import com.hnshilin.ddwallet.manage.UrlManager;
import com.hnshilin.ddwallet.mod.CommentsBean;
import com.hnshilin.ddwallet.mod.ProdDetailsBean;
import com.hnshilin.ddwallet.util.GlideUtils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONObject;

import me.zhanghai.android.materialratingbar.MaterialRatingBar;
import okhttp3.Call;
import okhttp3.Response;

/**
 * 产品详情页面
 */
public class DetailsDataActivity extends BaseActivity {
    private static final String TAG = "DetailsDataActivity";
    private View details_data_title;
    private TextView titlebar_title;
    private ImageView titlebar_back_img;
    private TextView titlebar_back_text;
    private RelativeLayout titlebar_back_rl;

    private ImageView details_data_imageview;
    private MaterialRatingBar details_data_start;
    private TextView details_data_prod_name;
    private TextView details_data_prod_number;
    private TextView details_data_lending_quota;
    private TextView details_data_lending_day;
    private TextView details_data_money_rate;
    private TextView details_data_lending_time;
    private TextView details_data_details;
    private TextView details_data_lending_data;
    private TextView details_data_conditions;
    private TextView details_data_evaluate;
    private TextView details_data_apply;

    private RecyclerView details_data_recycler;
    private boolean HasDetailsData = false;
    private boolean HasCommentsData = false;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_data);
        initView();
        getIntentData();
    }
    private void initView() {
        details_data_title = findViewById(R.id.details_data_title);
        titlebar_back_img = (ImageView) details_data_title.findViewById(R.id.titlebar_back_img);
        titlebar_back_text = (TextView) details_data_title.findViewById(R.id.titlebar_back_text);
        titlebar_back_rl = (RelativeLayout) details_data_title.findViewById(R.id.titlebar_back_rl);
        titlebar_back_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titlebar_back_img.setImageResource(R.drawable.go_back);
        titlebar_back_text.setText(R.string.back);
        titlebar_title = (TextView) details_data_title.findViewById(R.id.titlebar_title);


        details_data_imageview = (ImageView) findViewById(R.id.details_data_imageview);
        details_data_start = (MaterialRatingBar) findViewById(R.id.details_data_start);

        details_data_prod_name = (TextView) findViewById(R.id.details_data_prod_name);
        details_data_prod_number = (TextView) findViewById(R.id.details_data_prod_number);

        details_data_lending_quota = (TextView) findViewById(R.id.details_data_lending_quota);
        details_data_lending_day = (TextView) findViewById(R.id.details_data_lending_day);
        details_data_money_rate = (TextView) findViewById(R.id.details_data_money_rate);
        details_data_lending_time = (TextView) findViewById(R.id.details_data_lending_time);
        details_data_details = (TextView) findViewById(R.id.details_data_details);
        details_data_lending_data = (TextView) findViewById(R.id.details_data_lending_data);
        details_data_conditions = (TextView) findViewById(R.id.details_data_conditions);
        details_data_evaluate = (TextView) findViewById(R.id.details_data_evaluate);
        details_data_apply = (TextView) findViewById(R.id.details_data_apply);

        details_data_recycler = (RecyclerView) findViewById(R.id.details_data_recycler);
        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2,StaggeredGridLayoutManager.VERTICAL);
        details_data_recycler.setLayoutManager(staggeredGridLayoutManager);
    }
    private int prod_id;
    private void getIntentData() {
        Intent intent = getIntent();
        if (intent.hasExtra("prod_id")){
             prod_id = intent.getIntExtra("prod_id", -10);
            LogUtil.i(TAG,"getIntentData prod_id:"+prod_id);
            if (prod_id==-10){
                showToast("获取信息失败，请稍后再试!");
            }
            initData(prod_id);
        }

    }

    ProdDetailsBean prodDetailsBean;
    private void initData(final int prod_id) {
        OkGo.post(UrlManager.BaseUrl+ UrlManager.Prod_Details)
                .tag(mcontext)
                .params("PROD_ID",prod_id)
                .execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                LogUtil.i(TAG, "onSuccess: s:"+s);
                if (!TextUtils.isEmpty(s))
                try {
                    JSONObject json = new JSONObject(s);
                    if (json.has("code")){
                        int code = json.getInt("code");
                        switch (code){
                            case 0:
                                prodDetailsBean = new Gson().fromJson(s,ProdDetailsBean.class);
                                LogUtil.i(TAG, "onSuccess: prodDetailsBean:"+prodDetailsBean.toString());
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
                LogUtil.i(TAG, "onError: ");
                HasDetailsData = false;
            }

            @Override
            public void onAfter(String s, Exception e) {
                super.onAfter(s, e);
                LogUtil.i(TAG, "onAfter: ");
                if (prodDetailsBean!=null){
                    if (prodDetailsBean.getData()!=null&& prodDetailsBean.getData().size()!=0){
                        LogUtil.i(TAG, "onAfter: prodDetailsBean.size:"+prodDetailsBean.getData().size());
                        if (prodDetailsBean.getData().size()==1){
                            ProdDetailsBean.DataBean dataBean = prodDetailsBean.getData().get(0);
                                SetProdData(dataBean);
                                HasDetailsData = true;
                        }else{
                            ProdDetailsBean.DataBean dataBean = prodDetailsBean.getData().get(0);
                            SetProdData(dataBean);
                            HasDetailsData = true;
                            LogUtil.i(TAG, "onAfter: get data size >1");
                        }
                    }else{
                        LogUtil.i(TAG ,"onAfter : get data is null ");
                        showToast("获取详细信息失败，请稍后再试！");
//                        finish();
                    }
                    getCommentsData(prod_id);
                }
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    private void SetProdData(final ProdDetailsBean.DataBean dataBean) {
        titlebar_title.setText(dataBean.getPNAME());
        try {
            if (!isDestroyed()){ //曾经出现过崩溃，闪退， 设置图片的时候，该页面已经destoryed 了，所以闪退
            GlideUtils.getInstance().LoadContextBitmap(DetailsDataActivity.this,dataBean.getPROD_ICON_URL(),details_data_imageview,R.drawable.touming,R.drawable.touming,null);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        details_data_prod_name.setText(dataBean.getPNAME());

        int number = dataBean.getDOWNMAMOUNT();
        if (number<=99999){
            details_data_prod_number.setText(number+"");
        }else{
            double num = number/10000.0;
            String dow = String.format("%." + 2 + "f", num);
            details_data_prod_number.setText(dow+"万");
        }

        if (dataBean.getLIMITNUMBER_MIN()>=1&& dataBean.getLIMITNUMBER_MAX()>=1){
            details_data_lending_quota.setText(dataBean.getLIMITNUMBER_MIN()+"-"+dataBean.getLIMITNUMBER_MAX());
        }else if (dataBean.getLIMITNUMBER_MAX()>=1&&dataBean.getLIMITNUMBER_MIN()<1){
            details_data_lending_quota.setText(dataBean.getLIMITNUMBER_MAX()+"");
        }else if(dataBean.getLIMITNUMBER_MIN()>=1&&dataBean.getLIMITNUMBER_MAX()<1){
            details_data_lending_quota.setText(dataBean.getLIMITNUMBER_MIN()+"");
        }else{
            details_data_lending_quota.setText("");
        }


        details_data_lending_day.setText(dataBean.getLOANTIME());
        details_data_money_rate.setText(dataBean.getINTEREST());
        details_data_lending_time.setText(dataBean.getFASTESTTIME());
        details_data_lending_data.setText(dataBean.getDATA());
        details_data_conditions.setText(dataBean.getREQUIREMENTS());
        details_data_details.setText(dataBean.getPROD_COMMENT());
        details_data_evaluate.setText("马上评价");
        details_data_apply.setText("立即申请");
        switch (dataBean.getSUCCESS()){
            case 1:
                details_data_start.setRating(3);
                break;
            case 2:
                details_data_start.setRating(3.5f);
                break;
            case 3:
                details_data_start.setRating(4);
                break;
            case 4:
                details_data_start.setRating(4.5f);
                break;
            case 5:
                details_data_start.setRating(5);
                break;
            default:
                details_data_start.setRating(1);
                break;
        }

        details_data_evaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_id = Configs.getUserId(mcontext);
                if (user_id!=-10){
                    Configs.intent = new Intent(mcontext,CommentsActivity.class);
                    Configs.intent.putExtra("prod_id",dataBean.getPROD_ID());
                    Configs.intent.putExtra("user_id", user_id);
                    Configs.intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(Configs.intent);
                }else{
                    LoginManager.getInstance().startLogin(mcontext);
                }
            }
        });
        details_data_apply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                user_id = Configs.getUserId(mcontext);
                if (user_id!=-10){
                    Configs.intent = new Intent(mcontext,WebLoadActivity.class);
                    Configs.intent.putExtra("webdata",dataBean.getPROD_LINK_URL());
                    Configs.intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(Configs.intent);
                    uploadRealDownmount();
                }else{
                    LoginManager.getInstance().startLogin(mcontext);
                }
            }

        });
    }

    /**
     * 上传下载量和统计立即申请量
     */
    private void uploadRealDownmount() {
        OkGo.post(UrlManager.BaseUrl+UrlManager.Realdownmount).tag(this).params("prod_id",prod_id).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                LogUtil.i(TAG, "Realdownmount onSuccess: s:"+s);
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                LogUtil.i(TAG, "Realdownmount onError: ");
            }
        });
        AccessProdManager.InsertByClickApply(mcontext,prod_id);
    }
    private int user_id = -10;


    private CommentsAdapter commentsAdapter;
    private CommentsBean commentsBean;
    public void getCommentsData(int prod_id) {
        OkGo.post(UrlManager.BaseUrl+ UrlManager.Prod_Comments).tag(mcontext).params("prod_id",prod_id).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                LogUtil.i(TAG, "getCommentsData onSuccess: s:"+s);
                if (!TextUtils.isEmpty(s))
                try {
                    JSONObject json = new JSONObject(s);
                    if (json.has("code")){
                        int code = json.getInt("code");
                        switch (code){
                            case 0:
                                commentsBean = new Gson().fromJson(s,CommentsBean.class);
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
                LogUtil.i(TAG, "getCommentsData onError: ");
                HasCommentsData = false;
            }

            @Override
            public void onAfter(String s, Exception e) {
                super.onAfter(s, e);
                LogUtil.i(TAG, "getCommentsData onAfter: ");
                if (commentsBean!=null){
                    if (commentsBean.getData()!=null&&commentsBean.getData().size()!=0){
                        commentsAdapter = new CommentsAdapter(mcontext,commentsBean);
                        details_data_recycler.setAdapter(commentsAdapter);
                        HasCommentsData = true;
                    }
                }
            }
        });
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK){
            finish();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    protected void onResume() {
        super.onResume();
        if(HasDetailsData == false){
            if (prod_id!=-10){
                initData(prod_id);
            }
        }
        if (HasCommentsData == false){
            getCommentsData(prod_id);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
