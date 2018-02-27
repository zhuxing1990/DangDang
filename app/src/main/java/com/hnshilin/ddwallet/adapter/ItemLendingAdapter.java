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
import com.hnshilin.ddwallet.mod.AllLendingBean;
import com.hnshilin.ddwallet.util.GlideUtils;
import com.hnshilin.ddwallet.util.Utils;

import java.util.List;

public class ItemLendingAdapter extends RecyclerView.Adapter<ItemLendingAdapter.ItemLendingHolder>{
    private static final String TAG = "ItemLendingAdapter";
        private Activity context;
        private List<AllLendingBean.DataBean.CHILDRENBean> children;
       public ItemLendingAdapter(Activity context, List<AllLendingBean.DataBean.CHILDRENBean> children){
           this.context =context;
           if (children!=null){
               this.children = children;
           }

        }
        public void setDataToAdapter( List<AllLendingBean.DataBean.CHILDRENBean> children){
            this.children = children;
            this.notifyDataSetChanged();
        }
        @Override
        public ItemLendingHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_all_pfg_item2,parent,false);
            ItemLendingHolder holder = new ItemLendingHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(ItemLendingHolder holder, final int position) {
            if (children!=null&&children.size()!=0){
               final AllLendingBean.DataBean.CHILDRENBean childrenBean = children.get(position);
                GlideUtils.getInstance().LoadContextBitmap(context,childrenBean.getPROD_ICON_URL(),holder.all_pfg_item_img,R.drawable.touming,R.drawable.touming,null);
                holder.all_pfg_item_prod_name.setText(childrenBean.getPNAME());
                holder.all_pfg_item_prod_info.setText(childrenBean.getCOMMENTT());
                holder.mItenView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (Utils.isFastDoubleClick(1000)){
                            int prod_id = childrenBean.getID();
                            LogUtil.i(TAG,"onClick getDataBean："+childrenBean.toString());
                            Configs.intent = new Intent(context, DetailsDataActivity.class);
                            Configs.intent.putExtra("prod_id",childrenBean.getID());
                            Configs.intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                            context.startActivity(Configs.intent);
                            AccessProdManager.InsertByClickProduct(context,childrenBean.getID());
                        }
                    }
                });
            }
        }

        @Override
        public int getItemCount() {
            if (children!=null&&children.size()!=0){
                return children.size();
            }
           return 0;
        }

        public class ItemLendingHolder extends RecyclerView.ViewHolder{
            private View mItenView;
            private ImageView all_pfg_item_img;
            private TextView all_pfg_item_prod_name;
            private TextView all_pfg_item_prod_info;
            public ItemLendingHolder(View itemView) {
                super(itemView);
                mItenView = itemView;
                all_pfg_item_img = (ImageView) mItenView.findViewById(R.id.all_pfg_item_img);
                all_pfg_item_prod_name = (TextView) mItenView.findViewById(R.id.all_pfg_item_prod_name);
                all_pfg_item_prod_info = (TextView) mItenView.findViewById(R.id.all_pfg_item_prod_info);
            }
        }
    }