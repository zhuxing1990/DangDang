package com.hnshilin.ddwallet.manage;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.google.gson.Gson;
import com.hnshilin.ddwallet.base.Configs;
import com.hnshilin.ddwallet.log.LogUtil;
import com.hnshilin.ddwallet.mod.UpdateApkBean;
import com.hnshilin.ddwallet.service.UpdateService;
import com.hnshilin.ddwallet.util.SharedPreferencesUtil;
import com.hnshilin.ddwallet.util.Utils;
import com.lzy.okgo.OkGo;
import com.lzy.okgo.callback.StringCallback;

import org.json.JSONObject;

import okhttp3.Call;
import okhttp3.Response;

/**
 * Created by zhuxi on 2016/12/1.
 */
public class UpdateApkManager {
    private static final String TAG = "UpdateApkManager";
    private Activity context;


    /**
     * 接收消息
     */
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0x100://强制升级
                    Configs.intent = new Intent(context, UpdateService.class);
                    Configs.intent.putExtra("download_url", dataBean.getNEXT_VERSION_URL());
                    context.startService(Configs.intent);
                    break;
                default:

                    break;
            }
        }
    };

    private  static UpdateApkManager updateApkManager;
    public static UpdateApkManager getInstance(){
        if (updateApkManager == null){
            synchronized (UpdateApkManager.class){
                if (updateApkManager == null){
                    updateApkManager = new UpdateApkManager();
                }
            }
        }
        return updateApkManager;
    }
    /**
     * 获取更新的数据的 JAVABean
     */
    private UpdateApkBean.DataBean dataBean;
    public UpdateApkManager() {
    }

    public void GetUpdateInfo(final Activity context) {
        this.context = context;
        final int versionCode = Utils.getVersionCode(context);
        OkGo.post(UrlManager.BaseUrl+ UrlManager.Update_APK).tag(this).params("versionName", Utils.getVersionName(context)).execute(new StringCallback() {
            @Override
            public void onSuccess(String s, Call call, Response response) {
                LogUtil.i(TAG, "initUpdate onSuccess: s:"+s);
                if (!TextUtils.isEmpty(s)) {
                    try {
                        JSONObject json = new JSONObject(s);
                        if (json.has("code")) {
                            int code = json.getInt("code");
                            switch (code) {
                                case 0:
                                    UpdateApkBean updateBean = new Gson().fromJson(s, UpdateApkBean.class);
                                    if (updateBean != null) {
                                        if (updateBean.getData() != null && updateBean.getData().size() != 0) {
                                            if (updateBean.getData().size() == 1) {
                                                dataBean = updateBean.getData().get(0);
                                                int is_must_update = dataBean.getIS_MUST_UPDATE();
                                                switch (is_must_update) {
                                                    case 0:
                                                        LogUtil.i(TAG, "onSuccess: 可选择升级");
                                                        AlertDialog.Builder builder = new AlertDialog.Builder(context);
                                                        builder.setTitle("更新")
                                                                .setMessage("发现新版本，是否立即更新?")
                                                                .setCancelable(true)
//                                                    setNeutralButton
                                                                .setPositiveButton
                                                                        ("确定", new DialogInterface.OnClickListener() {
                                                                            @Override
                                                                            public void onClick(DialogInterface dialog, int which) {
                                                                                dialog.dismiss();
                                                                                Configs.intent = new Intent(context, UpdateService.class);
                                                                                Configs.intent.putExtra("download_url", dataBean.getNEXT_VERSION_URL());
                                                                                context.startService(Configs.intent);
                                                                            }
                                                                        })
                                                                .setNegativeButton("稍后更新", new DialogInterface.OnClickListener() {
                                                                    @Override
                                                                    public void onClick(DialogInterface dialog, int which) {
                                                                        dialog.dismiss();
                                                                    }
                                                                })
//                                                    setPositiveButton
                                                                .setNeutralButton
                                                                        ("暂不提醒", new DialogInterface.OnClickListener() {
                                                                            @Override
                                                                            public void onClick(DialogInterface dialog, int which) {
                                                                                /**暂不更新**/
                                                                                long time = System.currentTimeMillis();
                                                                                SharedPreferencesUtil.setLongValue(context,
                                                                                        Configs.UPDATE_TOMORROW, time);
                                                                                dialog.dismiss();
                                                                            }
                                                                        });
                                                        Dialog updateDialog = builder.create();
                                                        updateDialog.show();
                                                        break;
                                                    case 1:
                                                        LogUtil.i(TAG, "onSuccess: 必须升级");
                                                        handler.sendEmptyMessage(0x100);
                                                        break;
                                                }
                                                dataBean.getVERSION_NAME();
                                                dataBean.getIS_MUST_UPDATE();
                                            } else {
                                                LogUtil.i(TAG, "onSuccess: getUpdateBean getData size:" + updateBean.getData().size());
                                            }
                                        } else {
                                            LogUtil.i(TAG, "onSuccess: getUpdateBean getData is null");
                                        }
                                    }
                                    break;
                                default:
                                    break;
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }

            @Override
            public void onError(Call call, Response response, Exception e) {
                super.onError(call, response, e);
                LogUtil.i(TAG, "initUpdate onError: ");
            }

        });
    }
}
