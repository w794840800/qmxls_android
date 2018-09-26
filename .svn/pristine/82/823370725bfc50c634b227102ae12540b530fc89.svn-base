package com.qimai.xinlingshou.adapter;

import android.support.v7.util.DiffUtil;
import android.util.Log;

import com.qimai.xinlingshou.bean.goodsBean;

import java.util.ArrayList;

public class AdapterDiffCallBack extends DiffUtil.Callback{
    private static final String TAG = "AdapterDiffCallBack";
    ArrayList<goodsBean>oldDatas;
    ArrayList<goodsBean>newDatas;

    public AdapterDiffCallBack(ArrayList<goodsBean> oldDatas, ArrayList<goodsBean> newDatas) {

        Log.d(TAG, "AdapterDiffCallBack: oldDatas= "+oldDatas.size()
        +" newDatas= "+newDatas.size() +" oldDatas= "+oldDatas.toString()
        +" newDatas= "+newDatas.toString());

        this.oldDatas = oldDatas;
        this.newDatas = newDatas;
    }

    @Override
    public int getOldListSize() {
        return oldDatas.size();
    }

    @Override
    public int getNewListSize() {
        return newDatas.size();
    }

    @Override
    public boolean areItemsTheSame(int oldItemPosition, int newItemPosition) {

        if (oldDatas.get(oldItemPosition).getGoodsId().equals(newDatas.get(newItemPosition).getGoodsId())){

            return true;
        }

        return false;
    }

    @Override
    public boolean areContentsTheSame(int oldItemPosition, int newItemPosition) {


        int oldContent = oldDatas.get(oldItemPosition).getNumber();
        int newContent = newDatas.get(newItemPosition).getNumber();

        Log.d(TAG, "areContentsTheSame: oldContent= "+oldContent+" newContent= "+newContent);
        if (oldContent == newContent ){


            return true;
        }

        return false;
    }
}
