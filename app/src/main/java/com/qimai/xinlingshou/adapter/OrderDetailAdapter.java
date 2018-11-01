package com.qimai.xinlingshou.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qimai.xinlingshou.R;

public class OrderDetailAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    OnViewClick onViewClick;
    public static final int TYPE_NORMAL = 0;
    public static final int TYPE_FOOTER = 1;

    private static final String TAG = "OrderDetailAdapter";
    private Context context;

    public OrderDetailAdapter(Context context) {

        this.context = context;
    }
    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType==TYPE_FOOTER){

            View footerView = LayoutInflater.from(context)
                    .inflate(R.layout.footer_view,parent,false);

            return new FooterHolder(footerView);

        }else{


        View view = LayoutInflater.from(context)
                .inflate(R.layout.rv_detail_order,parent,false);
        return new ViewHolder(view);

        }


    }

    public void setOnViewClick(OnViewClick onViewClick) {
        this.onViewClick = onViewClick;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


    }



    @Override
    public int getItemViewType(int position) {
        //return super.getItemViewType(position);

        if (position==5){

            return TYPE_FOOTER;
        }else{

            return TYPE_NORMAL;
        }

    }

    @Override
    public int getItemCount() {
        return 5+1;
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        public ViewHolder(View itemView) {
            super(itemView);
            
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: ");
                }
            });
        }
    }

    public class FooterHolder extends RecyclerView.ViewHolder{
        public FooterHolder(View itemView) {
            super(itemView);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    Log.d(TAG, "onClick: footer");
                    if (onViewClick!=null){

                        
                        onViewClick.onLoadMore();
                    }
                }
            });
        }
    }


    public interface OnViewClick{


        void onLoadMore();

    }
}
