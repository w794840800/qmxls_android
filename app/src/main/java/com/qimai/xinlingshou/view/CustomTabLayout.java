package com.qimai.xinlingshou.view;

import android.content.Context;
import android.support.design.widget.TabLayout;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class CustomTabLayout extends TabLayout {

    boolean isCanScroll = false;

    public CustomTabLayout(Context context) {
        super(context);
    }


    public CustomTabLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

}
