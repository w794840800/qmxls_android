package com.qimai.xinlingshou.view;

import android.graphics.Rect;
import android.support.v7.widget.RecyclerView;
import android.view.View;

public class GoodsRecyclerItemDecoration extends RecyclerView.ItemDecoration {


    private int num;

    private int space;
    public GoodsRecyclerItemDecoration(int num,int space){

        this.num = num;
        this.space = space;

    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        outRect.top = space;

        outRect.right = space;
        int count = parent.getChildCount();
        for (int i = 0; i < count; i++) {

            int position = parent.getChildAdapterPosition(view);

            if (position%num==2){


                outRect.right = 0;

            }

        }


    }
}
