package com.qimai.xinlingshou.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

public class RecyclerView2 extends RecyclerView {
    private static final String TAG = "RecyclerView2";
    public RecyclerView2(Context context) {
        super(context);
    }

    public RecyclerView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public RecyclerView2(Context context, @Nullable AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {

        boolean status = super.dispatchTouchEvent(ev);
        Log.d(TAG, "dispatchTouchEvent: status= "+status);

        return status;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent e) {

                boolean status= super.onInterceptTouchEvent(e);

        Log.d(TAG, "onInterceptTouchEvent: status= "+status);
                return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent e) {

        boolean status = super.onTouchEvent(e);
        Log.d(TAG, "onTouchEvent: status= "+status);
        return status;
    }
}
