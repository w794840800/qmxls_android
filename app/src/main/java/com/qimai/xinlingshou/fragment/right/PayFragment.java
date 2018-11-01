package com.qimai.xinlingshou.fragment.right;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;

import com.qimai.xinlingshou.BaseFragment;
import com.qimai.xinlingshou.R;
import com.qimai.xinlingshou.adapter.PayFragmentAdapter;
import com.qimai.xinlingshou.view.CustomViewPager;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.Unbinder;


/**
 * Created by NIU on 2018/5/18.
 * 管理支付页面
 */

public class PayFragment extends BaseFragment {

    private static final String TAG = "PayFragment";
    @BindView(R.id.tl_pay_method)
    TabLayout tlPayMethod;
    @BindView(R.id.vp_pay_method)
    CustomViewPager vpPayMethod;
    Unbinder unbinder;
    private ArrayList<Fragment> fragmentArrayList;
    private PayFragmentAdapter payFragmentAdapter;
    private Fragment thirdFragment;
    private Fragment crashFragment;
    private Fragment payOtherFragment;

    boolean isSelectOne;
    @Override
    protected void initData() {

    }

    public PayFragment() {
       /* thirdFragment = new ThridFragment();
        crashFragment = new CrashFragment();
        payOtherFragment = new PayOtherFragment();*/
    }

    @Override
    protected void initView(View rootView) {

       //boolean visibity = ((MainActivity)getActivity()).isRightFragmentShow(RightFragmentType.PAY);
      //  Log.d(TAG, "initView: visibity= "+visibity);
        EventBus.getDefault().register(this);
        thirdFragment = new ThridFragment();
        crashFragment = new CrashFragment();
        payOtherFragment = new PayOtherFragment();
        fragmentArrayList = new ArrayList<>();
        fragmentArrayList.add(thirdFragment);
        fragmentArrayList.add(crashFragment);
        fragmentArrayList.add(payOtherFragment);
        payFragmentAdapter = new PayFragmentAdapter(getChildFragmentManager());
        payFragmentAdapter.setFragmentList(fragmentArrayList);
        tlPayMethod.setupWithViewPager(vpPayMethod);
        vpPayMethod.setAdapter(payFragmentAdapter);

        //默认让其先显示第二个
     //   vpPayMethod.setCurrentItem(1);

    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        Log.d(TAG, "onHiddenChanged234: hide= " + hidden);

        if (!hidden) {

            if (vpPayMethod != null) {
               // vpPayMethod.setCurrentItem(1);

            }
        } else if (hidden) {
            Log.d(TAG, "onHiddenChanged234678888: hide= " + hidden);

           // vpPayMethod.setCurrentItem(0);
            //EventBus.getDefault().post(new MessageEvent("cancelCollection"));

        }
        //isFragmentShow = !hidden;
    }

    @Override
    protected int getLayout() {

        return R.layout.pay_fragment;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)

    public void onEvent(MessageEvent messageEvent) {

        if (messageEvent.getType().equals("crashPay")){


            //这里不知道为啥viewPager中的现金支付Fragment EventBus收不到
            //所以在这里更新现金支付Fragment应付的钱

            if (crashFragment!=null){

                ((CrashFragment)crashFragment).setTotalMoney(messageEvent.getTotalMoney());
            }
        }else if (messageEvent.getType().equals(MessageEvent.PAY_CAN_SCROLL)){

            Log.d(TAG, "onEvent: PAY_CAN_SCROLL");

            vpPayMethod.setScroll(true);
            vpPayMethod.setClickable(true);

            LinearLayout tabStrip = (LinearLayout) tlPayMethod.getChildAt(0);
            for (int i = 0; i < tabStrip.getChildCount(); i++) {
                View tabView = tabStrip.getChildAt(i);
                if (tabView != null) {
                    tabView.setClickable(true);

                    tabView.setVisibility(View.VISIBLE);
                }
            }

        }else if (messageEvent.getType().equals(MessageEvent.PAY_NOT_SCROLL)){

            Log.d(TAG, "onEvent: PAY_NOT_SCROLL");
            vpPayMethod.setScroll(false);
            vpPayMethod.setCurrentItem(0);
            vpPayMethod.setClickable(false);
            LinearLayout tabStrip = (LinearLayout) tlPayMethod.getChildAt(0);
            for (int i = 0; i < tabStrip.getChildCount(); i++) {
                View tabView = tabStrip.getChildAt(i);
                if (tabView != null) {
                    tabView.setClickable(false);
                    if (i>0){
                    tabView.setVisibility(View.GONE);
                }
                }
            }
        }

        Log.d(TAG, "onEvent: type= "+messageEvent.getType()+" pay= "+messageEvent.getTotalMoney());
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

    }




}
