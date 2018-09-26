package com.qimai.xinlingshou.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qimai.xinlingshou.R;
import com.qimai.xinlingshou.bean.CouponsEntry;

import java.util.ArrayList;

/**
 * Created by wanglei on 18-5-22.
 */

public class CouponsRecyclerAdapter extends RecyclerView.Adapter<CouponsRecyclerAdapter.CouponsViewHolder>{
    boolean isSelectCoupons;
    private static final String TAG = "CouponsRecyclerAdapter";
    private View preSelectView;
    private int preSelectPosition = -1;
    private View nowSelectView;
    private int nowSelectPosition = -1;
    private int selectedPosition = -1;// 选中的位置
    private OnViewItemClickListener onViewItemClickListener;
    private ArrayList<CouponsEntry>CouponsEntryArrayList;
    private Context mContext;

    public CouponsRecyclerAdapter(Context context,ArrayList<CouponsEntry>EntryArrayList){

        mContext = context;
       this.CouponsEntryArrayList= EntryArrayList;

    }
    public void setSelectedPosition(int position) {
        selectedPosition = position;
    }
    public void updateData(ArrayList<CouponsEntry>goodsBeanList) {
       // if (goodsBeanList != null && goodsBeanList.size() > 0){
         if (goodsBeanList!=null){
            this.CouponsEntryArrayList = goodsBeanList;
    }

        for (CouponsEntry couponsEntry:CouponsEntryArrayList) {

            Log.d(TAG, "updateData: couponsEntry= "+couponsEntry.toString());
        }

        notifyDataSetChanged();
    }


    @NonNull
    @Override
    public CouponsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.recycle_item_coupons,parent,false);


        return new CouponsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CouponsViewHolder holder, final int position) {



          CouponsEntry couponsEntry = CouponsEntryArrayList.get(position);

        Log.d(TAG, "onBindViewHolder: couponsEntry= "+couponsEntry.toString());

        final boolean selected = couponsEntry.isSelected();
        holder.tv_validity_period.setText(couponsEntry.getType_id());
        holder.tv_cheap_money.setText(couponsEntry.getAmount());
        if (couponsEntry.getCoupon_type().equals("0")){
            holder.tv_coupons_style.setText("面值券");
            holder.tv_unit.setText("元");
           // holder.tv_vip_name.setText(couponsEntry.getTitle());
        }else {
            holder.tv_coupons_style.setText("折扣券");
            holder.tv_unit.setText("折");
        }
        //holder.tv_vip_name.setText(couponsEntry.getMin_amount_use());
        holder.tv_vip_name.setText(couponsEntry.getTitle());
        if (selected){

            holder.llCoupons.setBackgroundResource(R.drawable.coupons_select);
        }else{

            holder.llCoupons.setBackgroundResource(R.drawable.coupons_unselect);

        }


        holder.llCoupons.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectedPosition=position;
                notifyDataSetChanged();

                nowSelectPosition = position;

                nowSelectView = v;

                if (preSelectPosition!=-1){
                    CouponsEntryArrayList.get(preSelectPosition).setSelected(false);


                }
                //setCouponsBackground(preSelectView,nowSelectView);

                CouponsEntryArrayList.get(position).setSelected(!selected);



                updateData(CouponsEntryArrayList);
                preSelectPosition = nowSelectPosition;

                preSelectView = nowSelectView;
                onViewItemClickListener.onClick(v,position);

            }
        });

       /* if (selectedPosition==position) {
                if (!isSelectCoupons){
            if (CouponsEntryArrayList.get(position).getCoupon_type().equals("0")) {
                MessageEvent messageEvent = new MessageEvent("coupons");
                messageEvent.setYouhuiquan("1");//优惠券是否隐藏
                messageEvent.setYouhuitype("1");//
                messageEvent.setMin_amount_use(CouponsEntryArrayList.get(position).
                        getMin_amount_use());
                messageEvent.setCheap_money(holder.tv_cheap_money.getText().toString());
                EventBus.getDefault().postSticky(messageEvent);
            } else {
                MessageEvent messageEvent = new MessageEvent("coupons");
                messageEvent.setYouhuiquan("1");
                messageEvent.setYouhuitype("2");
                messageEvent.setMin_amount_use(CouponsEntryArrayList.get(position).getMin_amount_use());
                messageEvent.setCheap_money(holder.tv_cheap_money.getText().toString());
                EventBus.getDefault().postSticky(messageEvent);
            }
            holder.llCoupons.setBackgroundResource(R.drawable.coupons_select);
                    isSelectCoupons = !isSelectCoupons;
        }else {

                    if (CouponsEntryArrayList.get(position).getCoupon_type().equals("0")){
                        MessageEvent messageEvent = new MessageEvent("coupons");
                        messageEvent.setYouhuiquan("0");
                        messageEvent.setCheap_money(holder.tv_cheap_money.getText().toString());
                        messageEvent.setYouhuitype("1");
                        EventBus.getDefault().postSticky(messageEvent);
                    }else {
                        MessageEvent messageEvent = new MessageEvent("coupons");
                        messageEvent.setYouhuiquan("0");
                        messageEvent.setCheap_money(holder.tv_cheap_money.getText().toString());
                        messageEvent.setYouhuitype("2");
                    }
                    holder.llCoupons.setBackgroundResource(R.drawable.coupons_unselect);
                    isSelectCoupons = !isSelectCoupons;
                }


        }*/



    }

    private void setCouponsBackground(View preView, View nowView) {

        if (nowView!=null)
        nowView.setBackgroundResource(R.drawable.coupons_select);

        if (preView!=null){

            preView.setBackgroundResource(R.drawable.coupons_unselect);
        }




    }

    @Override
    public int getItemCount() {
        return CouponsEntryArrayList.size();
    }

    public static class CouponsViewHolder extends RecyclerView.ViewHolder{



        RelativeLayout llCoupons;
        TextView tv_validity_period;
        TextView tv_cheap_money;
        TextView tv_vip_name;
        TextView tv_coupons_style;
        TextView tv_info;
        TextView tv_unit;

        public CouponsViewHolder(View itemView) {
            super(itemView);
            llCoupons = (RelativeLayout) itemView.findViewById(R.id.ll_coupons_card);
            tv_cheap_money = (TextView) itemView.findViewById(R.id.tv_cheap_money);
            tv_unit = (TextView) itemView.findViewById(R.id.tv_unit);
            tv_validity_period = (TextView) itemView.findViewById(R.id.tv_validity_period);
            tv_vip_name = (TextView) itemView.findViewById(R.id.tv_vip_name);
            tv_coupons_style = (TextView) itemView.findViewById(R.id.tv_coupons_style);
            tv_info = (TextView) itemView.findViewById(R.id.tv_info);


        }

    }

    public void setViewItemClickListener(OnViewItemClickListener onViewItemClickListener){

        this.onViewItemClickListener = onViewItemClickListener;
    }


    public interface OnViewItemClickListener{

        public void onClick(View view,int position);

    }
//    public void updateData(ArrayList<goodsBean>goodsBeanList){
//
//        goodsBeanArrayList = goodsBeanList;
//
//        notifyDataSetChanged();
//    }
}