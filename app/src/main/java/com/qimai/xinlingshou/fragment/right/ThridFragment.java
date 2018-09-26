package com.qimai.xinlingshou.fragment.right;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.qimai.xinlingshou.BaseFragment;
import com.qimai.xinlingshou.R;
import com.qimai.xinlingshou.activity.MainActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

import static android.support.constraint.Constraints.TAG;


/**
 * Created by NIU on 2018/5/18.
 * 第三方支付界面
 */

public class ThridFragment extends BaseFragment  {
    @BindView(R.id.iv_sqm)
    ImageView ivSqm;
    @BindView(R.id.tv_introduction)
    TextView tvIntroduction;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    Unbinder unbinder;
    String isPay="1";

    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View rootView) {
//        EventBus.getDefault().register(this);

    }

    @Override
    protected int getLayout() {
        return R.layout.pay_third;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        EventBus.getDefault().unregister(this);
    }


    @OnClick({R.id.iv_sqm, R.id.tv_introduction, R.id.tv_cancel})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.iv_sqm:
                break;
            case R.id.tv_introduction:
                break;
            case R.id.tv_cancel:
                ((MainActivity)activity).showRightCrashierLayout();
                //设置左侧整单取消与收款可点击
                EventBus.getDefault().post(new MessageEvent("cancelCollection"));

                break;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEventsticky(MessageEvent messageEvent) {
        Log.d(TAG, "onEvent: pay= " + messageEvent.getAuthCode() + " type= " +
                messageEvent.getType());



    }





}
