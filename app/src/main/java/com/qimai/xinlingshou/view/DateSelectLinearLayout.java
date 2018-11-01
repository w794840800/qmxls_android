package com.qimai.xinlingshou.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.widget.LinearLayout;

import com.qimai.xinlingshou.R;

public class DateSelectLinearLayout extends LinearLayout {
    public DateSelectLinearLayout(Context context) {
        super(context);
    }

    public DateSelectLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater.from(context).inflate(R.layout.ll_date_select,this);

    }

    public DateSelectLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }
}
