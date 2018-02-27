package com.hnshilin.ddwallet.util;

import android.Manifest;
import android.app.Activity;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;

import com.hnshilin.ddwallet.log.LogUtil;

/**
 * Created by zhuxi on 2017/9/7.
 */
public class PermissionsUtils {
    private static final String TAG = "PermissionsUtils";
    public static final int REQUEST_CODE = 1;
    public static String[] PERMISSIONS_STORAGE = {
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE
    };

    public static void verifyStoragePermissions(Activity activity) {

        try {
            //检测是否有写的权限
            int permissionCheck = ActivityCompat.checkSelfPermission(activity,PERMISSIONS_STORAGE[0]);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, PERMISSIONS_STORAGE,REQUEST_CODE);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static void requestPermissions(Activity activity,String permission){
        try {
            int permissionCheck = ActivityCompat.checkSelfPermission(activity, permission);
            if (permissionCheck != PackageManager.PERMISSION_GRANTED) {
                // 没有写的权限，去申请写的权限，会弹出对话框
                ActivityCompat.requestPermissions(activity, new String[]{permission}, REQUEST_CODE);
            }else{
                LogUtil.i(TAG, "getPermission: has permission");
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}
