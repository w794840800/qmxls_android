package com.qimai.xinlingshou.adapter;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.qimai.xinlingshou.R;
import com.qimai.xinlingshou.bean.CityBean;

import java.util.ArrayList;

public class CustomItemDecoration extends RecyclerView.ItemDecoration {

    private static final String TAG = "CustomItemDecoration";
ArrayList<CityBean>datas;
Paint paint;
    private int mTitleHeight = 30;
    Rect rectF;

    public CustomItemDecoration(ArrayList<CityBean> datas) {
        this.datas = datas;
        rectF = new Rect();
    }

    @Override
    public void onDraw(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDraw(c, parent, state);

        int left = parent.getPaddingLeft();
        int right = parent.getWidth()-parent.getPaddingRight();

        int childCount = parent.getChildCount();


        for (int i = 0; i <childCount ; i++) {

            View child = parent.getChildAt(i);

            RecyclerView.LayoutParams params =  ((RecyclerView.LayoutParams)child.getLayoutParams());
            int position = params.getViewLayoutPosition();

            Log.d(TAG, "onDraw: position= "+position);
            if (position>-1){

                if (position==0){

                    drawTitleArea(c,left,right,child,params,position);

                } else{


                if (datas.get(position).getTag()!=null&&!datas.get(position).getTag()
                        .equals(datas.get(position-1).getTag())) {


                    Log.d(TAG, "onDraw: datas.get(position).getTag()!=null&&!datas.get(position).getTag().equals(datas.get(position-1).getTag())");
                    drawTitleArea(c, left, right, child, params, position);

                }
                }

            }



        }



    }

    private void drawTitleArea(Canvas c, int left, int right, View child, RecyclerView.LayoutParams params, int position) {


        paint = new Paint();
        paint.setColor(Color.BLUE);
        c.drawRect(left,child.getTop()-params.topMargin-mTitleHeight,
                right,child.getTop()
        -params.topMargin,paint);

        paint.setColor(Color.WHITE);

        paint.getTextBounds(datas.get(position).getTag(),0,datas.get(position).getTag().length(),rectF);


        c.drawText(datas.get(position).getTag(),child.getPaddingLeft(),child.getTop()-params
        .topMargin-(mTitleHeight/2-rectF.height()/2),paint);


    }

    @Override
    public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
        super.onDrawOver(c, parent, state);

        int pos = ((LinearLayoutManager)parent.getLayoutManager()).findFirstVisibleItemPosition();
       // View child = parent.findViewHolderForLayoutPosition(pos).itemView;

        View child = parent.getChildAt(pos);
        String tag = datas.get(pos).getTag();

        paint = new Paint();
        paint.setColor(Color.BLUE);
        c.drawRect(parent.getPaddingLeft(),parent.getPaddingTop(),parent
                .getRight()-parent.getPaddingRight(),parent.getPaddingTop()+mTitleHeight,paint);

        paint.setColor(Color.WHITE);

        paint.getTextBounds(tag,0,tag.length(),rectF);


        c.drawText(tag,child.getPaddingLeft(),parent.getPaddingTop()+mTitleHeight-(mTitleHeight/2-rectF.height()/2),paint);




    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        int position = ((RecyclerView.LayoutParams)view.getLayoutParams()).getViewLayoutPosition();

        if (position>-1){
            if (position==0){

                outRect.set(0,mTitleHeight,0,0);
            }else{


                if (datas.get(position).getTag()!=null&&!datas.get(position).getTag().equals(datas.get(position-1).getTag())){

                    outRect.set(0,mTitleHeight,0,0);

                }else{

                    outRect.set(0,0,0,0);
                }



            }

        }



    }


}
