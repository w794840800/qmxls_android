package com.qimai.xinlingshou.view;

import android.content.Context;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewCompat;
import android.support.v4.widget.DrawerLayout;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;

import com.qimai.xinlingshou.R;

/**
 *
 * 功能描述：自定义DrawerLayout,调整Drawerlayout事件拦截逻辑
 */
public class CustomDrawerLayout extends DrawerLayout {

    private static final String TAG = "CustomDrawerLayout";
    public CustomDrawerLayout(Context context){
        this(context, null);
    }

    public CustomDrawerLayout(Context context,AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CustomDrawerLayout(Context context,AttributeSet attrs, int defStyle) {
        super(context, attrs,defStyle);
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev){
        switch(ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                final float x = ev.getX();
                final float y = ev.getY();
                Log.d(TAG, "onInterceptTouchEvent: ");
                final View touchedView = findTopChildUnder((int) x, (int) y);
                if (touchedView != null && isContentView(touchedView)
                      //因为收银界面需要右侧遮罩层点击事件不能响应到到子控件中
                        //&&(findViewById(R.id.ll_setiing).getVisibility()==View.VISIBLE)
                        && this.isDrawerOpen(GravityCompat.START)) {

                    Log.d(TAG, "onInterceptTouchEvent: == false ");

                    return false;
                }
                break;

            default:
                break;
        }
        return super.onInterceptTouchEvent(ev);
    }

    /**
     * 判断点击位置是否位于相应的View内
     * @param x
     * @param y
     * @return
     */
    public View findTopChildUnder(int x, int y) {
        final int childCount = getChildCount();
        for (int i = childCount - 1; i >= 0; i--) {
            final View child = getChildAt(i);
            if (x >= child.getLeft() && x < child.getRight() &&
                    y >= child.getTop() && y < child.getBottom()) {
                return child;
            }
        }
        return null;
    }

    /**
     * 判断点击触摸点的View是否是ContentView(即是主页面的View)
     * @param child
     * @return
     */
    boolean isContentView(View child) {

        Log.d(TAG, "isContentView: child= "+child.getId()+" "+child.getMeasuredWidth()

        +" id= "+R.layout.cashier_left_fragment);

        Log.d(TAG, "isContentView: visibity="
                +findViewById(R.id.ll_setiing).getVisibility()+"");

       /* Class class1 = this.getClass();
        Class superClass = class1.getSuperclass();
*/
     /*   Field field = null;
        try {
            field = superClass.getDeclaredField("mLockModeLeft");

            field.setAccessible(true);
            Log.d(TAG, "isContentView: mLockModeLeft= "+field.getInt(superClass));

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        field.setAccessible(true);
        Field[] fields = superClass.getDeclaredFields();
        for (Field f:
                fields) {
            Log.d(TAG, "isContentView: name= "+f.getName());

        }
*/


        /*FrameLayout frameLayout = (FrameLayout) ((ViewGroup)(getChildAt(0)))
                .getChildAt(0);*/


        //((FrameLayout)findViewById(R.id.fl_left_container)).getChildAt(0);


        /*Class select_enable_disenable = this.getClass().getSuperclass();

        //setScrimColor(0);

        Log.d(TAG, "isContentView: select_enable_disenable= "+(select_enable_disenable!=null));
        try {
            Field[] fields = select_enable_disenable.getDeclaredFields();

            for (Field f:
                 fields) {
                Log.d(TAG, "isContentView: name= "+f.getName());

            }
            Field field = select_enable_disenable.getDeclaredField("mDrawerState");


            int color = field.getInt(select_enable_disenable);
            field.setAccessible(true);
            Log.d(TAG, "isContentView: mScrimColor= "+color);


        } catch (Exception e) {
            e.printStackTrace();

            Log.d(TAG, "isContentView: error= "+e.toString());
        }


        FrameLayout frameLayout = (FrameLayout) ((ViewGroup)(getChildAt(0)))
                .getChildAt(0);
        *//*for (int i = 0; i < frameLayout.getChildCount(); i++) {

            Log.d(TAG, "isContentView: view= "+((ViewGroup)frameLayout.getChildAt(i))
            .getChildAt(0).getLeft()
            );
        }*/

        ViewGroup viewGroup1 = ((ViewGroup)getChildAt(0));
                ViewGroup viewGroup2 = (ViewGroup) viewGroup1.getChildAt(0);

                ViewGroup viewGroup3 = (ViewGroup) viewGroup2.getChildAt(0);

        Log.d(TAG, "isContentView: viewGroup3 id= "+viewGroup3.getId()+" left_right= "

        +R.id.ll_setiing+" viewId= "+child.getId());
        boolean isOk = ((LayoutParams) child.getLayoutParams()).gravity == Gravity.NO_GRAVITY;

        Log.d(TAG, "isContentView: isOk= "+isOk);
        return isOk;


    }

    /**
     * 判断点击触摸点的View是否是DrawerView(即是侧边栏的View)
     * @param child
     * @return
     */
    boolean isDrawerView(View child) {
        final int gravity = ((LayoutParams) child.getLayoutParams()).gravity;
        final int absGravity = GravityCompat.getAbsoluteGravity(gravity,
                ViewCompat.getLayoutDirection(child));
        if ((absGravity & Gravity.LEFT) != 0) {
            // This child is a left-edge drawer
            return true;
        }
        if ((absGravity & Gravity.RIGHT) != 0) {
            // This child is a right-edge drawer
            return true;
        }
        return false;
    }


}


