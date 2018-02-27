package com.hnshilin.ddwallet.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.hnshilin.ddwallet.R;
import com.hnshilin.ddwallet.mod.CommentsBean;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsHolder>{
        private Context context;
        private CommentsBean commentsBean;
        public CommentsAdapter(Context context,CommentsBean commentsBean){
            this.commentsBean = commentsBean;
            this.context = context;
        }
        @Override
        public CommentsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_comments_item,parent,false);
            CommentsHolder holder = new CommentsHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(CommentsHolder holder,final int position) {
           final  CommentsBean.DataBean dataBean = commentsBean.getData().get(position);
            if (!TextUtils.isEmpty(dataBean.getINFO())){
                holder.recycler_comments_item_data.setText(dataBean.getINFO());
                holder.recycler_comments_item_number.setText(dataBean.getTOTAL()+"");
            }else{
                holder.recycler_comments_item_data.setText("无评论");
                holder.recycler_comments_item_number.setText(dataBean.getTOTAL()+"");
            }
        }

        @Override
        public int getItemCount() {
            return commentsBean.getData().size();
        }

        public class CommentsHolder extends RecyclerView.ViewHolder{
            private View mItemView ;
            private TextView recycler_comments_item_data;
            private TextView recycler_comments_item_number;
            public CommentsHolder(View itemView) {
                super(itemView);
                mItemView = itemView;
                recycler_comments_item_data = (TextView) mItemView.findViewById(R.id.recycler_comments_item_data);
                recycler_comments_item_number = (TextView) mItemView.findViewById(R.id.recycler_comments_item_number);
            }
        }
    }