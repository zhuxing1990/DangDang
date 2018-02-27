package com.hnshilin.ddwallet.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.hnshilin.ddwallet.R;
import com.hnshilin.ddwallet.activity.WebLoadActivity;
import com.hnshilin.ddwallet.base.Configs;
import com.hnshilin.ddwallet.mod.VipAdvertBean;
import com.hnshilin.ddwallet.util.GlideUtils;
import com.hnshilin.ddwallet.util.Utils;

/**
     * 头布局的适配器
     */
    public class HeaderAdapter extends PagerAdapter {
        private Activity context;
        private VipAdvertBean vipBean;
        public HeaderAdapter(Activity context,VipAdvertBean vipBean){
            this.context = context;
            this.vipBean = vipBean;
        }

        @Override
        public Object instantiateItem(ViewGroup container, final int position) {
            final VipAdvertBean.DataBean dataBean = vipBean.getData().get(position);
            ImageView imageView = new ImageView(context);
            imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
            GlideUtils.getInstance().LoadContextBitmap(context,dataBean.getVIP_IMG_URL(),imageView, R.drawable.touming,R.drawable.touming,null);
            container.addView(imageView);
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    Toast.makeText(context, "第" + position + "页", Toast.LENGTH_SHORT).show();
                    if (TextUtils.isEmpty(dataBean.getVIP_LINK_URL())){
                        return;
                    }
                    if (Utils.isFastDoubleClick(1000)){
                        Configs.intent = new Intent(context, WebLoadActivity.class);
                        Configs.intent.putExtra("webdata",dataBean.getVIP_LINK_URL());
                        Configs.intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(Configs.intent);
                    }
                }
            });
            return imageView;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }

        @Override
        public int getCount() {
            return vipBean.getData().size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }
    }