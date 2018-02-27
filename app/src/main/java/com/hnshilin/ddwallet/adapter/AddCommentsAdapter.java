package com.hnshilin.ddwallet.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.hnshilin.ddwallet.R;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class AddCommentsAdapter extends RecyclerView.Adapter<AddCommentsAdapter.AddCommentsHolder>{
        private Context context;
        private List<String> list;
        // 存储勾选框状态的map集合
        private Map<Integer, Boolean> map = new HashMap<>();

        public AddCommentsAdapter(Context context, List<String> list){
            this. context = context;
            this.list = list;
            initMap();
        }

        private void initMap() {
            if (list!=null&& list.size()!=0){
                for (int i = 0; i <list.size() ; i++) {
                    map.put(i, false);
                }
            }
        }
        public Map<Integer, Boolean> getChecked(){
            return map;
        }
        @Override
        public AddCommentsHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_comments_item2,parent,false);
            AddCommentsHolder holder = new AddCommentsHolder(view);
            return holder;
        }

        @Override
        public void onBindViewHolder(final AddCommentsHolder holder, final int position) {
            holder.recycler_comments_item_checkBox.setText(list.get(position));
            holder.recycler_comments_item_checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    //用map集合保存
                    map.put(position, isChecked);
                    if (isChecked){
                        holder.recycler_comments_item_checkBox.setTextColor(Color.parseColor("#FFFFFF"));
                    }else{
                        holder.recycler_comments_item_checkBox.setTextColor(Color.parseColor("#FFBA75"));
                    }
                }
            });
            // 设置CheckBox的状态
            if (map.get(position) == null) {
                map.put(position, false);
            }
            holder.recycler_comments_item_checkBox.setChecked(map.get(position));
        }

        @Override
        public int getItemCount() {
            return list.size();
        }

        public class AddCommentsHolder extends RecyclerView.ViewHolder{
            private View mItemView;
            private CheckBox recycler_comments_item_checkBox;
            public AddCommentsHolder(View itemView) {
                super(itemView);
                mItemView = itemView;
                recycler_comments_item_checkBox = (CheckBox) mItemView.findViewById(R.id.recycler_comments_item_checkBox);
            }
        }
    }