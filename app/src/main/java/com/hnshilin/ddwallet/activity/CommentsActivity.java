package com.hnshilin.ddwallet.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.hnshilin.ddwallet.R;
import com.hnshilin.ddwallet.adapter.AddCommentsAdapter;
import com.hnshilin.ddwallet.base.BaseActivity;
import com.hnshilin.ddwallet.log.LogUtil;
import com.hnshilin.ddwallet.manage.UrlManager;
import com.hnshilin.ddwallet.mod.AddCommentsBean;
import com.hnshilin.ddwallet.util.Utils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import okhttp3.Call;
import okhttp3.Response;

/**
 * 评论页面
 */
public class CommentsActivity extends BaseActivity {
    private static final String TAG = "CommentsActivity";
    private View comments_title;
    private ImageView titlebar_back_img;
    private TextView titlebar_back_text;
    private TextView titlebar_title;
    private RelativeLayout titlebar_back_rl;
    private List<String> commentsList;
    private Button comments_confirm;
    private RecyclerView comments_recycler;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comments);
        initView();
        getIntentData();
        initCommentsData();
    }
    AddCommentsAdapter commentsAdapter;
    private String comments = "";
    private void initCommentsData() {
        commentsList = new ArrayList<>();
        commentsList.add("秒速到账");
        commentsList.add("利率低");
        commentsList.add("超高通过率");
        commentsList.add("国名好货款");
        commentsList.add("良心网货");
        commentsList.add("门槛不能再低啦");
        commentsList.add("大家都在货");
        commentsAdapter = new AddCommentsAdapter(mcontext,commentsList);
        comments_recycler.setAdapter(commentsAdapter);
        comments_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (Utils.isFastDoubleClick(2000)){
                    Map<Integer, Boolean> isCheck_delete = commentsAdapter.getChecked();
                    // 获取到条目数量，map.size = list.size,所以
                    int count = commentsAdapter.getItemCount();
                    // 遍历
                    for (int i = 0; i < count; i++) {
                        if (isCheck_delete.get(i) != null && isCheck_delete.get(i)) {
                            String s = commentsList.get(i);
                            LogUtil.i(TAG, "onClick: s:"+s);
                            comments += s+" ";
                        }
                    }
                    if (TextUtils.isEmpty(comments)){
                        finish();
                        return;
                    }
                    setComments(user_id,prod_id,comments);
                    comments = "";
                }
            }
        });
    }

    private void initView() {
        comments_title = findViewById(R.id.comments_title);
        titlebar_back_img = (ImageView) comments_title.findViewById(R.id.titlebar_back_img);
        titlebar_back_text = (TextView) comments_title.findViewById(R.id.titlebar_back_text);
        titlebar_title = (TextView) comments_title.findViewById(R.id.titlebar_title);
        titlebar_back_rl = (RelativeLayout) comments_title.findViewById(R.id.titlebar_back_rl);
        titlebar_back_rl.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        titlebar_back_img.setImageResource(R.drawable.go_back);
        titlebar_back_text.setText(R.string.back);
        titlebar_title.setText(R.string.comments);

        comments_recycler = (RecyclerView) findViewById(R.id.comments_recycler);
        StaggeredGridLayoutManager gridLayoutManager = new StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL);
        comments_recycler.setLayoutManager(gridLayoutManager);
        comments_confirm = (Button) findViewById(R.id.comments_confirm);

    }

    private int prod_id;
    private int user_id;
    private void getIntentData() {
        Intent intent = getIntent();
        if (intent.hasExtra("prod_id")){
             prod_id = intent.getIntExtra("prod_id", -10);
        }
        if (intent.hasExtra("user_id")){
            user_id = intent.getIntExtra("user_id",-10);
        }
        if (user_id==-10&&prod_id==-10){
            showToast("获取用户信息或者获取产品信息失败，请稍后再试!");
            finish();
        }
    }
    private AddCommentsBean addCommentsBean;
    private void setComments(long user_id,int prod_id,String info) {
        OkGo.post(UrlManager.BaseUrl+ UrlManager.Add_Prod_Comments)
                .tag(mcontext).params("user_id",user_id).params("prod_id",prod_id)
                .params("info",info)
                .execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                    LogUtil.i(TAG, "onSuccess: s:"+s);
                if (!TextUtils.isEmpty(s)){
                try {
                    JSONObject json = new JSONObject(s);
                    if (json.has("code")){
                        int code = json.getInt("code");
                        switch (code){
                            case 0:
                                addCommentsBean = new Gson().fromJson(s,AddCommentsBean.class);
                                int result = addCommentsBean.getData().get(0).get_result();
                                switch (result){
                                    case 0:
                                        showToast("您已经评论过了哦!");
                                        finish();
                                        break;
                                    default:
                                        showToast("评论成功!");
                                        finish();
                                        break;
                                }
                                break;
                            default:
                                if (json.has("message")){
                                    Utils.showToast(json.getString("message"),mcontext);
                                }
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
                LogUtil.i(TAG, "onError: ");
            }

            @Override
            public void onAfter(String s, Exception e) {
                super.onAfter(s, e);
                LogUtil.i(TAG, "onAfter: ");
            }
        });
    }

}
