package com.hnshilin.ddwallet.util;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.ResolveInfo;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.text.format.DateFormat;
import android.text.format.DateUtils;
import android.text.format.Time;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.hnshilin.ddwallet.log.LogUtil;
import com.hnshilin.ddwallet.view.AlertDialog;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by zhuxi on 2017/8/22.
 */
public class Utils {

    private static final String TAG = "Utils";
    // 防止按钮重复点击
    private static long lastClickTime = 0;
    // 防止按钮重复点击
    public static boolean isFastDoubleClick(float ts) {
        if (System.currentTimeMillis()-lastClickTime>=ts){
            lastClickTime = System.currentTimeMillis();
            return true;
        }else{
            return false;
        }
    }
    /**
     * 隐藏软键盘，只在edittext没有获取焦点时有用
     * @param aty
     */
    public static void hideSoftKeyboard(Activity aty) {
        aty.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN); // 隐藏意见反馈残留的软键盘
    }

    /**
     * 隐藏软键盘
     */
    public static void hideSoftKeyboard(Context mcontext, EditText v) {
        InputMethodManager imm = (InputMethodManager) mcontext.getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(v.getWindowToken(), 0);
    }
    /**
     * 让activity全屏
     * @param aty
     */
    public static void makeFullScreenAty(Activity aty) {
        aty.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 存储单位.
     */
    private static final int STOREUNIT = 1024;

    /**
     * 时间毫秒单位.
     */
    private static final int TIMEMSUNIT = 1000;

    /**
     * 时间单位.
     */
    private static final int TIMEUNIT = 60;



    /**
     * 转化文件单位.
     *
     * @param size
     *            转化前大小(byte)
     * @return 转化后大小
     */
    public static String getFormatSize(double size) {
        double kiloByte = size / STOREUNIT;
        if (kiloByte < 1) {
            return size + " Byte";
        }

        double megaByte = kiloByte / STOREUNIT;
        if (megaByte < 1) {
            BigDecimal result = new BigDecimal(Double.toString(kiloByte));
            return result.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                    + " KB";
        }

        double gigaByte = megaByte / STOREUNIT;
        if (gigaByte < 1) {
            BigDecimal result = new BigDecimal(Double.toString(megaByte));
            return result.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                    + " MB";
        }

        double teraBytes = gigaByte / STOREUNIT;
        if (teraBytes < 1) {
            BigDecimal result = new BigDecimal(Double.toString(gigaByte));
            return result.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                    + " GB";
        }
        BigDecimal result = new BigDecimal(teraBytes);
        return result.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + " TB";
    }

    /**
     * 转化时间单位.
     *
     * @param time
     *            转化前大小(MS)
     * @return 转化后大小
     */
    public static String getFormatTime(long time) {
        double second = (double) time / TIMEMSUNIT;
        if (second < 1) {
            return time + " MS";
        }

        double minute = second / TIMEUNIT;
        if (minute < 1) {
            BigDecimal result = new BigDecimal(Double.toString(second));
            return result.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                    + " SEC";
        }

        double hour = minute / TIMEUNIT;
        if (hour < 1) {
            BigDecimal result = new BigDecimal(Double.toString(minute));
            return result.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                    + " MIN";
        }

        BigDecimal result = new BigDecimal(Double.toString(hour));
        return result.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + " H";
    }


    /**
     * 根据包名启动APK
     *
     * @param packageName
     * @param context
     */
    public static void StartEPG(String packageName, Context context) {
        if (TextUtils.isEmpty(packageName)) {
            LogUtil.i("tv_launcher", "包名为空");
            return;
        }
        PackageInfo pi;
        try {
            pi = context.getPackageManager().getPackageInfo(packageName, 0);
            Intent resolveIntent = new Intent(Intent.ACTION_MAIN, null);
            resolveIntent.setPackage(pi.packageName);
            PackageManager pManager = context.getPackageManager();
            List apps = pManager.queryIntentActivities(resolveIntent, 0);
            ResolveInfo ri = (ResolveInfo) apps.iterator().next();
            if (ri != null) {
                packageName = ri.activityInfo.packageName;
                String className = ri.activityInfo.name;
                Intent intent = new Intent(Intent.ACTION_MAIN);
                ComponentName cn = new ComponentName(packageName, className);
                intent.setComponent(cn);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
    }


    /**
     * @param context
     * @return versionName 版本名字
     */
    public static String getVersionName(Context context) {
        String versionName = "";
        try {
            String pkName = context.getPackageName();
            versionName = context.getPackageManager().getPackageInfo(
                    pkName, 0).versionName;
        } catch (Exception e) {
            e.printStackTrace();
            return "0";
        }
        return versionName;
    }
    /**
     * 获取版本信息
     * @param context
     * @return packageName+versionName+versionCode
     */
    @Nullable
    public static String getVersionInfo(Context context){
        try {

            String pkName = context.getPackageName();

            String versionName = context.getPackageManager().getPackageInfo(

                    pkName, 0).versionName;

            int versionCode = context.getPackageManager()

                    .getPackageInfo(pkName, 0).versionCode;

            return pkName + "   " + versionName + "  " + versionCode;

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    /**
     * @param context
     * @return versionCode 版本号
     */
    public static int getVersionCode(Context context) {
        int versionCode = 0;
        try {
            String pkName = context.getPackageName();
            versionCode = context.getPackageManager()
                    .getPackageInfo(pkName, 0).versionCode;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }
        return versionCode;
    }
    public static String getDateTime(long dataTime) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss:SS");
        Date date = new Date(dataTime);
        String time = dateFormat.format(date);
        return time;
    }
    public static  void stopClick(final Button loginBtn, final long time) {
        loginBtn.setClickable(false);
        Observable.interval(0,1, TimeUnit.SECONDS).filter(new Func1<Long, Boolean>() {
            @Override
            public Boolean call(Long aLong) {
                return aLong <= time;
            }
        }).map(new Func1<Long, Long>() {

            @Override
            public Long call(Long aLong) {
                return -(aLong-time);
            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Long>() {
                    @Override
                    public void onCompleted() {

                    }

                    @Override
                    public void onError(Throwable throwable) {
                        loginBtn.setClickable(true);
                        loginBtn.setText("确  定");
                        this.unsubscribe();
                    }

                    @Override
                    public void onNext(Long aLong) {
                        if (aLong!=0){
                            loginBtn.setText("请等待" + aLong + "秒");
                        }else{
                            this.unsubscribe();
                            loginBtn.setClickable(true);
                            loginBtn.setText("确  定");
                        }
                    }
                });
    }

    /**
     *  吐司
     * @param string
     * @param context
     */
    public static void showToast(String string,Context context) {
        Toast.makeText(context, string, Toast.LENGTH_SHORT).show();
    }

    /**
     * 获取当前时间
     *
     * @return String 2016-6-12 10:53:05:888
     */
    public static String getDateTime() {
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm:ss:SS");
        Date date = new Date(System.currentTimeMillis());
        String time = dateFormat.format(date);
        return time;
    }
    /**
     * 使用系统工具类判断是否是今天 是今天就显示发送的小时分钟 不是今天就显示发送的那一天
     * */
    public static String getDate(Context context, long when) {
        String date = null;
        if (DateUtils.isToday(when)) {
            date = DateFormat.getTimeFormat(context).format(when);
        } else {
            date = DateFormat.getDateFormat(context).format(when);
        }
        return date;
    }
    /**
     * 根据两个长整形数，判断是否是同一天
     *
     * @param lastDay
     * @param thisDay
     * @return
     */
    public static boolean isSameToday(long lastDay, long thisDay) {
        Time time = new Time();
        time.set(lastDay);

        int thenYear = time.year;
        int thenMonth = time.month;
        int thenMonthDay = time.monthDay;

        time.set(thisDay);
        return (thenYear == time.year) && (thenMonth == time.month)
                && (thenMonthDay == time.monthDay);
    }
    /** 生成漂亮的颜色 */
    public static int generateBeautifulColor() {
        Random random = new Random();
        //为了让生成的颜色不至于太黑或者太白，所以对3个颜色的值进行限定
        int red = random.nextInt(150) + 50;//50-200
        int green = random.nextInt(150) + 50;//50-200
        int blue = random.nextInt(150) + 50;//50-200
        return Color.rgb(red, green, blue);//使用r,g,b混合生成一种新的颜色
    }

    /** 获得状态栏的高度 */
    public static int getStatusHeight(Context context) {
        int statusHeight = -1;
        try {
            Class<?> clazz = Class.forName("com.android.internal.R$dimen");
            Object object = clazz.newInstance();
            int height = Integer.parseInt(clazz.getField("status_bar_height").get(object).toString());
            statusHeight = context.getResources().getDimensionPixelSize(height);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return statusHeight;
    }

    /**
     * 关闭或者重启APP
     * @param mcontext
     */
    public static void FinishOrRestart(final Activity mcontext) {
        new AlertDialog(mcontext).builder().setCancelable(false).setTitle("当当钱包提醒!").setMsg("网络未连接,请检查网络").setNegativeButton("退出", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                android.os.Process.killProcess(android.os.Process.myPid());
            }
        }).setPositiveButton("重新启动", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RestartAPPTool.restartAPP(mcontext,2000);
            }
        }).show();
    }

    /**
     * 获取设备型号
     *
     * @return
     */
    public static String getDeviceModel() {
        return android.os.Build.MODEL;

    }

}
