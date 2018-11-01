package com.qimai.xinlingshou.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qimai.xinlingshou.R;
import com.qimai.xinlingshou.bean.CityBean;

import java.util.ArrayList;

public class CityAdapter extends RecyclerView.Adapter<CityAdapter.ViewHolder>{

    ArrayList<CityBean>cityBeanArrayList;

    Context context;

    public CityAdapter(ArrayList<CityBean> cityBeanArrayList, Context context) {

        this.cityBeanArrayList = cityBeanArrayList;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

       View view = LayoutInflater.from(context).inflate(R.layout.city_rv,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        holder.tv_city.setText(cityBeanArrayList.get(position).getCity());

    }

    @Override
    public int getItemCount() {
        return cityBeanArrayList.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tv_city;
        public ViewHolder(View itemView) {
            super(itemView);

            tv_city = itemView.findViewById(R.id.tv_city);
        }
    }
}
