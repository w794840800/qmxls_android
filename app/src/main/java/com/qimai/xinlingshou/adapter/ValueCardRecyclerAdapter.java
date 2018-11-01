package com.qimai.xinlingshou.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qimai.xinlingshou.R;
import com.qimai.xinlingshou.bean.ValueCardBean;

import java.util.ArrayList;

public class ValueCardRecyclerAdapter extends RecyclerView.Adapter<ValueCardRecyclerAdapter.ViewHolder> {


    Context context;
    ArrayList<ValueCardBean>valueCardBeanArrayList;
    onViewItemClick onViewItemClick;
    public ValueCardRecyclerAdapter(Context context,ArrayList<ValueCardBean>valueCardBeanArrayList){

        this.context = context;
        this.valueCardBeanArrayList = valueCardBeanArrayList;
    }
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.rv_card_values,parent,false);

        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        ValueCardBean valueCardBean = valueCardBeanArrayList.get(position);

        holder.tvRechargeMoney.setText(valueCardBean.getSell_price());

        holder.tvPresenterMoney.setText("赠送" + valueCardBean.getEntity() + "元");

        if (TextUtils.isEmpty(valueCardBean.getEntity())) {

            holder.tvPresenterMoney.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onViewItemClick.onItemClick(holder.itemView,position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return valueCardBeanArrayList.size();
    }

    public void update(ArrayList<ValueCardBean> data) {

        this.valueCardBeanArrayList = data;

        notifyDataSetChanged();
    }

    public void setOnViewItemClick(onViewItemClick onViewItemClick){

        this.onViewItemClick = onViewItemClick;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{


        TextView tvRechargeMoney;
        TextView tvPresenterMoney;

        public ViewHolder(View itemView) {
            super(itemView);
            tvRechargeMoney = (TextView) itemView.findViewById(R.id.tv_recharge_money);
            tvPresenterMoney = (TextView) itemView.findViewById(R.id.tv_presenter_money);


        }


    }

    public interface onViewItemClick{


        void onItemClick(View v,int position);

    }

}




