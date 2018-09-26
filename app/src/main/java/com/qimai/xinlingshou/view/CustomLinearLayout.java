package com.qimai.xinlingshou.view;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.widget.LinearLayout;

/**
 * Created by wanglei on 18-7-4.
 */

public class CustomLinearLayout extends LinearLayout {
    private static final String TAG = "CUstomLinearLayout";
    public CustomLinearLayout(Context context) {
        super(context);
    }

    public CustomLinearLayout(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomLinearLayout(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {

       /* DrawerLayout view = (DrawerLayout) getParent();
         DrawerLayout.LayoutParams lp =
                (DrawerLayout.LayoutParams) view.getLayoutParams();*/
       // Log.d(TAG, "onInterceptTouchEvent: state = "+lp.openState);

        /* Class<?>test = lp.getClass();
        try {
            Field field = test.getDeclaredField("openState");

            field.setAccessible(true);
            Log.d(TAG, "onInterceptTouchEvent: openState= "+field.get(lp));
        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }*/


        /*for (int i = 0; i < view.getChildCount(); i++) {

          //  Log.d(TAG, "onInterceptTouchEvent: view = "+view.getChildAt(i).toString());

        }*/

        Log.d(TAG, "onInterceptTouchEvent: ");
        return super.onInterceptTouchEvent(ev);
    }
}
