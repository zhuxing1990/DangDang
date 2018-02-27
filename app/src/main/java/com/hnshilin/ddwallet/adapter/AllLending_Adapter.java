package com.hnshilin.ddwallet.adapter;

import android.app.Activity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.hnshilin.ddwallet.R;
import com.hnshilin.ddwallet.mod.AllLendingBean;
import com.hnshilin.ddwallet.util.GlideUtils;
import com.hnshilin.ddwallet.view.DividerGridItemDecoration;

public class AllLending_Adapter extends RecyclerView.Adapter<AllLending_Adapter.AllPFG_Holder>{
    private static final String TAG = "AllLending_Adapter";
        private Activity context;
        private AllLendingBean allbean ;
        public AllLending_Adapter(Activity context, AllLendingBean allbean){
           this.context=context;
            this.allbean = allbean;
        }
        @Override
        public AllPFG_Holder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_allpfg_item,parent,false);
            AllPFG_Holder holder = new AllPFG_Holder(view);
            return holder;
        }

        @Override
        public int getItemCount() {
            return allbean.getData().size();
        }

        @Override
        public void onBindViewHolder(final AllPFG_Holder holder, final int position) {
            final AllLendingBean.DataBean dataBean = allbean.getData().get(position);
            holder.recycler_allpfg_title.setText(dataBean.getCATEGORY_2_NAME());
            holder.itemLendingAdapter.setDataToAdapter(dataBean.getCHILDREN());
            GlideUtils.getInstance().LoadContextBitmap(context,dataBean.getCATEGORY_2_IOCN_URL(),holder.recycler_allpfg_l,R.drawable.touming,R.drawable.touming,null);
            holder.recycler_allpfg_rel.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    if (holder.all_pfg_item_recycler.getVisibility() == View.VISIBLE){
//                        holder.all_pfg_item_recycler.setVisibility(View.GONE);
//                        holder.recycler_allpfg_close.setImageResource(R.drawable.go_right);
//                    }else{
//                        holder.all_pfg_item_recycler.setVisibility(View.VISIBLE);
//                        holder.recycler_allpfg_close.setImageResource(R.drawable.go_down);
//                    }
                }
            });
        }

        public class AllPFG_Holder extends RecyclerView.ViewHolder{
            private View mItenView;
            private ImageView recycler_allpfg_l;
            private TextView recycler_allpfg_title;
            private RecyclerView all_pfg_item_recycler;
            private ItemLendingAdapter itemLendingAdapter;
            private RelativeLayout recycler_allpfg_rel;
            private ImageView recycler_allpfg_close;
            public AllPFG_Holder(View itemView) {
                super(itemView);
                mItenView = itemView;
                recycler_allpfg_l = (ImageView) mItenView.findViewById(R.id.recycler_allpfg_l);
                recycler_allpfg_close = (ImageView) mItenView.findViewById(R.id.recycler_allpfg_close);
                recycler_allpfg_title = (TextView) mItenView.findViewById(R.id.recycler_allpfg_title);
                recycler_allpfg_rel = (RelativeLayout) mItenView.findViewById(R.id.recycler_allpfg_rel);
                all_pfg_item_recycler = (RecyclerView) mItenView.findViewById(R.id.all_pfg_item_recycler);
                GridLayoutManager gridLayoutManager = new GridLayoutManager(context,2);
                gridLayoutManager.setOrientation(GridLayoutManager.VERTICAL);
                all_pfg_item_recycler.addItemDecoration(new DividerGridItemDecoration(context));
                all_pfg_item_recycler.setLayoutManager(gridLayoutManager);
                itemLendingAdapter = new ItemLendingAdapter(context,null);
                all_pfg_item_recycler.setAdapter(itemLendingAdapter);
            }
        }
    }