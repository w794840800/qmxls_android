package com.qimai.xinlingshou.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.hardware.display.DisplayManager;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Display;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestListener;
import com.bumptech.glide.request.target.Target;
import com.qimai.xinlingshou.App;
import com.qimai.xinlingshou.R;
import com.qimai.xinlingshou.bean.goodsBean;
import com.qimai.xinlingshou.utils.DecimalFormatUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import sunmi.ds.FilesManager;

/**
 * Created by wanglei on 18-5-19.
 */

public class GoodsSelectAdapter extends RecyclerView.Adapter<GoodsSelectAdapter.GoodsSelectViewHolder> {
    public static final boolean isMain = Build.MODEL.equals("t1host") || Build.MODEL.equals("T1-G");

    private static final String TAG = "GoodsSelectAdapter";
    public ArrayList<goodsBean>goodsBeanArrayList;
    private onItemClick onItemClick;
    private Context mContext;
    public GoodsSelectAdapter(Context context,ArrayList<goodsBean> goodsBeanArrayList) {
        this.goodsBeanArrayList = goodsBeanArrayList;
        mContext = context;

        DisplayManager displayManager = (DisplayManager) App.getBaseApplication().getSystemService(Context.DISPLAY_SERVICE);

        Log.d(TAG, "GoodsSelectAdapter: ");
        Display[]displays = displayManager.getDisplays();

      /*  for (int i = 0; i < displays.length; i++) {

            Display display = displays[1];

            Log.d(TAG, "GoodsSelectAdapter: display= "+display.toString()

            );
            Log.d(TAG, "-----------------");

        }*/

//13136571734

    }

    public void setOnItemClick(onItemClick onItemClick){

        this.onItemClick = onItemClick;
    }
    @NonNull
    @Override
    public GoodsSelectViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(mContext)
                .inflate(R.layout.recycler_item_seleceted_goods,parent,false);
        return new GoodsSelectViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final GoodsSelectViewHolder holder, final int position) {

      //  ImageLoader.getInstance() .init(ImageLoaderConfiguration.createDefault(mContext));
//
        //ImageLoader.getInstance().displayImage
        // (goodsBeanArrayList.get(position).getGoodsimg(), holder.tv_goods_img);



        Log.d(TAG, "onBindViewHolder: "+goodsBeanArrayList.get(position)
        .getGoodsimg()+" taskId= "+goodsBeanArrayList.get(position)
                .getTaskId()+" cache path= "+ FilesManager.getInstance().getFile(goodsBeanArrayList.get(position).getTaskId())    );
       /* Glide.with(mContext)
                .load(goodsBeanArrayList.get(position).getGoodsimg())
                .asBitmap()
                .placeholder(R.drawable.default_user)
                .error(R.drawable.default_user)
                .listener(new RequestListener<String, Bitmap>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {

                        Log.d(TAG, "onException: e= "+e.toString());
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {

                        Log.d(TAG, "onResourceReady: resource= "+resource);
                        return false;
                    }
                })
                .into(holder.tv_goods_img);*/
        holder.tv_goods_name.setText(goodsBeanArrayList.get(position)
        .getGoodsName());
        holder.tv_goods_number.setText("x"+goodsBeanArrayList.get(position)
                .getNumber()+"");
        holder.tv_goods_per_price.setText(DecimalFormatUtils.doubleToMoney(goodsBeanArrayList.get(position)
                .getPrice()));
       holder.tv_goods_total_price.setText(DecimalFormatUtils.doubleToMoney(goodsBeanArrayList.get(position)
                .getPrice()*goodsBeanArrayList.get(position).getNumber()));
        holder.itemView.setSoundEffectsEnabled(false);


       if (onItemClick!=null){


           holder.itemView.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {
                   
                   
                   
                   
                   Log.d(TAG, "onClick: v= "+v);


                       onItemClick.onItemClick(v,position);

               }
           });
           holder.tv_goods_delete.setOnClickListener(new View.OnClickListener() {
               @Override
               public void onClick(View v) {

                   onItemClick.onDelete(v,position);

               }
           });


        }


    }

    public void hideView(int id){


    }

    @Override
    public void onBindViewHolder(@NonNull GoodsSelectViewHolder holder, int position, @NonNull List<Object> payloads) {
        super.onBindViewHolder(holder, position, payloads);


    }

    public void updateData(ArrayList<goodsBean>goodsBeanArrayList){
        Collections.reverse(goodsBeanArrayList);
        this.goodsBeanArrayList = goodsBeanArrayList;


        notifyDataSetChanged();

        //notifyItemChanged();

        //notifyItemInserted();
    }


    public void updateItemChange(int position){

        notifyItemChanged(position);

    }


    public void insertItemChange(int position){

        //notifyItemChanged(position);

        notifyItemInserted(position);


    }




    @Override
    public int getItemCount() {
        return goodsBeanArrayList.size();
    }

    public class GoodsSelectViewHolder extends RecyclerView.ViewHolder{

        private ImageView tv_goods_img;
        private TextView tv_goods_name;
        private TextView tv_goods_number;
        private TextView tv_goods_per_price;
        private TextView tv_goods_total_price;
        private TextView tv_goods_delete;
        public GoodsSelectViewHolder(View itemView) {
            super(itemView);
            tv_goods_img = (ImageView) itemView.findViewById(R.id.tv_goods_img);
            tv_goods_img.setVisibility(View.GONE);
            tv_goods_name = (TextView) itemView.findViewById(R.id.tv_goods_name);
                tv_goods_number = (TextView)itemView.findViewById(R.id.tv_goods_number);
            tv_goods_per_price = (TextView)itemView.findViewById(R.id.tv_goods_per_price);
            tv_goods_total_price = (TextView)itemView.findViewById(R.id.tv_goods_total_price);
            tv_goods_delete = (TextView)itemView.findViewById(R.id.tv_goods_delete);



        }
    }
    public interface onItemClick{
         void onItemClick(View view,int position);

         void onDelete(View view,int position);
    }


}
