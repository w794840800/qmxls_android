package com.qimai.xinlingshou.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qimai.xinlingshou.R;
import com.qimai.xinlingshou.bean.CategoryBean;

import java.util.ArrayList;


/**
 * 带头header 全部商品
 *
 * */
public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.ViewHolder> {

    View headView;
    Context context;
   ArrayList<CategoryBean>categoryBeanArrayList;

   View preClickView;
   int TYPE_HEADER = 1;
   int TYPE_NORMAL = 2;
    OnViewItemClick onViewItemClick;
    public CategoryAdapter(Context context,ArrayList<CategoryBean>categoryBeanArrayList) {

        this.context = context;
        this.categoryBeanArrayList = categoryBeanArrayList;
    }

    private void setHeadView(View headView) {
        this.headView = headView;

        notifyItemInserted(0);

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.category_items,parent,false);

        if (viewType==TYPE_HEADER){
            headView = view;

            preClickView = headView;

            headView.setSelected(true);
            return new ViewHolder(headView);
        }

        return new ViewHolder(view);
    }


    @Override
    public int getItemViewType(int position) {




        if (position==0){


            return TYPE_HEADER;
        }else{

            return TYPE_NORMAL;
        }

    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {

        if (getItemViewType(position)==TYPE_HEADER){

            if (onViewItemClick!=null){

                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onViewItemClick.onHeadViewClick(v);


                        if (preClickView!=null){

                            preClickView.setSelected(false);
                        }
                        preClickView = v;

                        v.setSelected(true);

                    }
                });
            }
            return;
        }

        holder.tv_category.setText(categoryBeanArrayList.get(position-1).getName());

        if (onViewItemClick!=null) {

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    onViewItemClick.onViewItemClick(position-1, holder.itemView);
                     //点击就把selected 设为true


                    if (preClickView!=null){

                        preClickView.setSelected(false);
                    }
                    preClickView = v;

                    v.setSelected(true);

                }
            });
        }
    }

    public void setOnViewItemClick(OnViewItemClick onViewItemClick) {
        this.onViewItemClick = onViewItemClick;
    }

    @Override
    public int getItemCount() {
        return categoryBeanArrayList.size()+1;
    }


    public void update(ArrayList<CategoryBean> categoryBeanArrayList) {

        this.categoryBeanArrayList = categoryBeanArrayList;
        notifyDataSetChanged();

    }

    class ViewHolder extends RecyclerView.ViewHolder{

        TextView tv_category;

        public ViewHolder(View itemView) {
            super(itemView);

            tv_category = (TextView) itemView.findViewById(R.id.tv_category);

        }
    }

    public interface OnViewItemClick{

        void onViewItemClick(int position,View view);

        //头部headview全部商品被点击
        void onHeadViewClick(View v);

    }
}
