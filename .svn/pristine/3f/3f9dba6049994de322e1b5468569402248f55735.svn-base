package com.qimai.xinlingshou.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.qimai.xinlingshou.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2018/10/9.
 */

public class RadioListViewAdapter  extends BaseAdapter {

    private Context context;
    private LayoutInflater inflater;
    private List<String> itemList = new ArrayList<String>();
    private int position = 0;//用于记录当前位置

    public RadioListViewAdapter(Context context){
        this.context = context;
        inflater = LayoutInflater.from(context);
    }

    /*
    * 用于设置当前位置
    */
    public void setPosition(int position) {
        this.position = position;
    }

    public void setItems(List<String> itemList) {
        this.itemList.clear();
        this.itemList = itemList;
        notifyDataSetChanged();
    }
    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public Object getItem(int position) {
        return itemList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int i, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {

            holder = new ViewHolder();

            convertView = inflater.inflate(R.layout.good_item, parent, false);
            holder.tvItem = (TextView) convertView.findViewById(R.id.tv_item);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tvItem.setText(itemList.get(i));

        if (position == i) {
            holder.tvItem.setTextColor(context.getResources().getColor(R.color.TvBlue));
            holder.tvItem.setBackgroundResource(R.drawable.shape_blue_bg);
        } else {
            holder.tvItem.setTextColor(context.getResources().getColor(R.color.TvGray));
            holder.tvItem.setBackgroundResource(R.drawable.shape_gray_bg);
        }
        return convertView;
    }

    class ViewHolder {
        TextView tvItem;
    }

}
