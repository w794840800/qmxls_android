package com.qimai.xinlingshou.adapter;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
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

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;

import static android.support.constraint.Constraints.TAG;

/**
 * Created by wanglei on 18-5-19.
 */

public class GoodsRecyclerAdapter
        extends RecyclerView.Adapter<GoodsRecyclerAdapter.GoodsViewHolder> {

    private static onItemClick onItemClick;
    saveImageSucess saveImageSucess;
    private int position;
    private Context mContext;
    private ArrayList<goodsBean>goodsBeanArrayList;

    public void setOnItemClick(onItemClick onItemClick){

        this.onItemClick = onItemClick;

    }
    public GoodsRecyclerAdapter(Context context,
                                ArrayList<goodsBean>goodsBeanList){

        mContext = context;
        goodsBeanArrayList = goodsBeanList;


    }

    public void updateData(ArrayList<goodsBean>goodsBeanList){

        goodsBeanArrayList = goodsBeanList;

        Log.d(TAG, "updateData: goodsBeanArrayList= "+goodsBeanArrayList.size());

        for (goodsBean goodsBean:goodsBeanArrayList) {
            Log.d(TAG, "updateData: goodsBean= "+goodsBean.toString());

        }

        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public GoodsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        final View view = LayoutInflater.from(mContext)
                .inflate(R.layout.recycler_item_goods,parent,false);


        return new GoodsViewHolder(view);
    }

    public void setSaveImageSucess(saveImageSucess saveImageSucess){

        this.saveImageSucess = saveImageSucess;

    }


    @Override
    public void onBindViewHolder(@NonNull final GoodsViewHolder holder, final int position) {
        //ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(mContext));
        //ImageLoader.getInstance().displayImage(goodsBeanArrayList.get(position).
        // getGoodsimg(), holder.goodsPic);

        this.position = position;
        Log.d(TAG, "onBindViewHolder: position= "+goodsBeanArrayList.get(position)
        .getGoodsName());
        holder.goodsName.setText(goodsBeanArrayList.get(position)
        .getGoodsName());

        Glide.with(mContext)
                .load(goodsBeanArrayList.get(position).getGoodsimg())
                .asBitmap()
                .placeholder(R.drawable.default_user)
                .error(R.drawable.default_user)
                .listener(new RequestListener<String, Bitmap>() {
                    @Override
                    public boolean onException(Exception e, String model, Target<Bitmap> target, boolean isFirstResource) {
                        return false;
                    }

                    @Override
                    public boolean onResourceReady(Bitmap resource, String model, Target<Bitmap> target, boolean isFromMemoryCache, boolean isFirstResource) {

                        Log.d(TAG, "onResourceReady: size= "+goodsBeanArrayList.size()

                        +" position= "+position);
                        if (goodsBeanArrayList.size()>position) {
                            saveImage(resource, goodsBeanArrayList.get(position),position);
                        }
                        return false;
                    }
                })
                .into(holder.goodsPic);
       // double price = goodsBeanArrayList.get(position).getPrice();
       // DecimalFormat df = new DecimalFormat(".00");
       // ImageLoader.getInstance() .init(ImageLoaderConfiguration.createDefault(mContext));
//
      /*  ImageLoader.getInstance().displayImage(goodsBeanArrayList.
                get(position).getGoodsimg(), holder.goodsPic);*/

        holder.goodsPrice.setText(DecimalFormatUtils.doubleToMoney(goodsBeanArrayList.get(position).getPrice())+goodsBeanArrayList
        .get(position).getUnit());


        if (onItemClick!=null){

            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Log.d(TAG, "onClick: position= "+position);

                    onItemClick.onViewClick(holder.itemView,position);
                }
            });
        }
    }

    private void saveImage(Bitmap resource, goodsBean goodsBean,int pisition) {



        String goodsId = goodsBean.getGoodsId();
        //BitmapDrawable bitmapDrawable = (BitmapDrawable) resource;


 //       Bitmap bitmap = bitmapDrawable.getBitmap();

        //String path = App.getBaseApplication().getFilesDir() + File.separator + goodsId+".png";

        String path = Environment.getExternalStorageDirectory().getPath()+File.separator+goodsId+".png";
        File file = new File(path);
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            resource.compress(Bitmap.CompressFormat.PNG,100,fileOutputStream);

            fileOutputStream.close();

            if (saveImageSucess!=null)

                saveImageSucess.onImageSucess(position,file);

            Log.d(TAG, "saveImage: save sucess "+file.getAbsolutePath()+"  filesDir= "+path);



        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            {

                goodsBean.setLocal_image(path);
                goodsBean.updateAll("goodsid = ?",goodsId);

            }
        }


    }

    @Override
    public int getItemCount() {
        return goodsBeanArrayList.size();
    }

    public static class GoodsViewHolder extends RecyclerView.ViewHolder{



        private ImageView goodsPic;
        private TextView goodsName;
        private TextView goodsPrice;
        public GoodsViewHolder(View itemView) {
            super(itemView);


            goodsName = (TextView) itemView.findViewById(R.id.tv_goods_name);

            goodsPic = (ImageView) itemView.findViewById(R.id.tv_goods_pic);

            goodsPrice = (TextView)itemView.findViewById(R.id.tv_goods_price);

        }
    }

    public interface onItemClick{

        public void onViewClick(View view,int position);

    }

    public interface saveImageSucess{


        void onImageSucess(int position,File file);

    }


}
