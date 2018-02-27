package com.hnshilin.ddwallet.weixin;

import android.graphics.Bitmap;

import java.io.ByteArrayOutputStream;

/**
 * Created by Administrator on 2017/7/14 0014.
 */

public class WeixinUtil {

    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }
        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return result;
    }
}
