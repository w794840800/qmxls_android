package com.qimai.xinlingshou.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qimai.xinlingshou.R;

public class OrderListAdapter extends RecyclerView.Adapter<OrderListAdapter.ViewHolder>
 implements OrderDetailAdapter.OnViewClick{

    OnViewClick onViewClick;
    private static final String TAG = "OrderListAdapter";
   //private ArrayList arrayList;
    private Context context;
    boolean isOpen = false;
    OrderDetailAdapter OrderdetailAdapter;
    public OrderListAdapter(Context context) {

        this.context = context;
        OrderdetailAdapter = new OrderDetailAdapter(context);
        OrderdetailAdapter.setOnViewClick(this);
    }

    public void setOnViewClick(OnViewClick onViewClick) {
        this.onViewClick = onViewClick;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.rv_order_item,parent,false);

        ViewHolder viewHolder = new ViewHolder(view);

        Log.d(TAG, "onCreateViewHolder: "+(viewHolder.recyclerView==null));
        viewHolder.recyclerView.setLayoutManager(new LinearLayoutManager(context));

        viewHolder.recyclerView.setAdapter(OrderdetailAdapter);


        viewHolder.recyclerView.setEnabled(false);
        viewHolder.recyclerView.setPressed(false);
        viewHolder.recyclerView.setClickable(false);

        viewHolder.cl_top.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                isOpen = !isOpen;

                viewHolder.recyclerView.setVisibility(isOpen?View.VISIBLE:View.GONE);

            }
        });
        //viewHolder.recyclerView.setAdapter();
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        holder.recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {



            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
                super.onScrolled(recyclerView, dx, dy);


                Log.d(TAG, "onScrolled: ");
            }

            @Override
            public void onScrollStateChanged(RecyclerView recyclerView, int newState) {



                LinearLayoutManager linearLayoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                Log.d(TAG, "onScrollStateChanged: lastVisib ity= "+linearLayoutManager
                        .findLastVisibleItemPosition()+"  newstate= "+newState
                        +"  adapter count= "+recyclerView.getAdapter().getItemCount());

                //super.onScrollStateChanged(recyclerView, newState);
            }
        });
       // holder.recyclerView.setLayoutManager(new LinearLayoutManager(context));
        //holder.recyclerView.setAdapter(OrderdetailAdapter);
    }


    @Override
    public int getItemCount() {
        return 30;
    }

    @Override
    public void onLoadMore() {


        onViewClick.onLoadMore();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        ConstraintLayout cl_top;
        RecyclerView recyclerView;
        public ViewHolder(View itemView) {
            super(itemView);
            recyclerView = itemView.findViewById(R.id.rv_detail_order);
            cl_top = itemView.findViewById(R.id.cl_top);
            Log.d(TAG, "ViewHolder: "+(recyclerView==null));



        }
    }

    public interface OnViewClick{

        void onLoadMore();

    }

}
