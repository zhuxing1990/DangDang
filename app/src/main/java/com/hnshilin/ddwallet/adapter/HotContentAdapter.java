package com.hnshilin.ddwallet.adapter;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.hnshilin.ddwallet.R;
import com.hnshilin.ddwallet.activity.DetailsDataActivity;
import com.hnshilin.ddwallet.base.Configs;
import com.hnshilin.ddwallet.log.LogUtil;
import com.hnshilin.ddwallet.manage.AccessProdManager;
import com.hnshilin.ddwallet.mod.HotProdBean;
import com.hnshilin.ddwallet.util.GlideUtils;
import com.hnshilin.ddwallet.util.Utils;

public class HotContentAdapter extends RecyclerView.Adapter<HotContentAdapter.HotContentHolder>{
    private static final String TAG = "HotContentAdapter";
        private Activity context;
        private HotProdBean hotProdBean;

        public HotContentAdapter(Activity context, HotProdBean hotProdBean){
            this.context = context;
            this.hotProdBean = hotProdBean;
        }

        @Override
        public HotContentHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_hotcontent_item,parent,false);
            HotContentHolder holder = new HotContentHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(HotContentHolder holder,final int position) {
//            HotContentBean bean = hotProdBean.get(position);
           final HotProdBean.DataBean dataBean = hotProdBean.getData().get(position);
            holder.recycler_hotcontent_content.setText(dataBean.getCOMMENTT());
            int number = dataBean.getDOWNMAMOUNT();
            if (number<=99999){
                holder.recycler_hotcontent_number.setText(dataBean.getDOWNMAMOUNT()+"");
            }else{
                double num = number/10000.0;
                String dow = String.format("%." + 2 + "f", num);
                holder.recycler_hotcontent_number.setText(dow+"万");
            }

            holder.recycler_hotcontent_title.setText(dataBean.getPNAME());
            GlideUtils.getInstance().LoadContextBitmap(context,dataBean.getPROD_ICON_URL(),holder.recycler_hotcontent_img,R.drawable.touming,R.drawable.touming,null);
            holder.mItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (Utils.isFastDoubleClick(1000)){
                        int prod_id = dataBean.getPROD_ID();
                        LogUtil.i(TAG,"onClick getDataBean："+dataBean.toString());
                        Configs.intent = new Intent(context, DetailsDataActivity.class);
                        Configs.intent.putExtra("prod_id",dataBean.getPROD_ID());
                        Configs.intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(Configs.intent);
                        AccessProdManager.InsertByClickProduct(context,dataBean.getPROD_ID());
                    }
                }
            });
        }

        @Override
        public int getItemCount() {
            return hotProdBean.getData().size();
        }

        public class HotContentHolder extends RecyclerView.ViewHolder{
            private ImageView recycler_hotcontent_img;
            private TextView recycler_hotcontent_number;
            private TextView recycler_hotcontent_title;
            private TextView recycler_hotcontent_content;
            private View mItemView;
            public HotContentHolder(View itemView) {
                super(itemView);
                mItemView = itemView;
                recycler_hotcontent_img = (ImageView) mItemView.findViewById(R.id.recycler_hotcontent_img);
                recycler_hotcontent_number = (TextView) mItemView.findViewById(R.id.recycler_hotcontent_number);
                recycler_hotcontent_title = (TextView) mItemView.findViewById(R.id.recycler_hotcontent_title);
                recycler_hotcontent_content = (TextView) mItemView.findViewById(R.id.recycler_hotcontent_content);
            }
        }
    }