package com.qimai.xinlingshou.view;

import android.content.Context;
import android.util.AttributeSet;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.Button;

public class CustomButton extends Button {
    private static final String TAG="CustomButton";

    public CustomButton(Context context) {
        super(context);
    }

    public CustomButton(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public CustomButton(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {

        Log.d(TAG, "dispatchKeyEvent: "+event.getDevice()
        .getName());
        if (event.getDevice().getName().contains("SM-2D")){

            return false;
        }
        return super.dispatchKeyEvent(event);
    }
}
