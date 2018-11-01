package com.qimai.xinlingshou.dialog;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.qimai.xinlingshou.R;
import com.qimai.xinlingshou.activity.MainActivity;
import com.qimai.xinlingshou.bean.RechargeBean;
import com.qimai.xinlingshou.bean.ValueCardBean;
import com.qimai.xinlingshou.callback.NetWorkCallBack2;
import com.qimai.xinlingshou.fragment.right.MessageEvent;
import com.qimai.xinlingshou.model.PayModel;
import com.qimai.xinlingshou.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class RechargeDialog extends DialogFragment {

    ValueCardBean valueCardBean;
    @BindView(R.id.tv_user_mobile)
    TextView tvUserMobile;
    @BindView(R.id.tv_recharge_money)
    TextView tvRechargeMoney;
    @BindView(R.id.tv_presenter_money)
    TextView tvPresenterMoney;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.tv_ok)
    TextView tvOk;
    @BindView(R.id.pr_loading)
    ProgressBar prLoading;
    @BindView(R.id.tv_add)
    TextView tvAdd;
    @BindView(R.id.tv_reward_tips)
    TextView tvRewardTips;

    Activity activity;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog);

    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        activity = (Activity) context;

    }

    @Override
    public void onDetach() {
        super.onDetach();


       // activity = null;
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.dialog_recharge, container, false);


        ButterKnife.bind(this, view);
        return view;
    }

    public static RechargeDialog getInstance(ValueCardBean valueCardBean) {

        RechargeDialog rechargeDialog = new RechargeDialog();

        Bundle bundle = new Bundle();
        bundle.putParcelable("recharge", valueCardBean);
        rechargeDialog.setArguments(bundle);

        return rechargeDialog;
    }

    @Override
    public void onStart() {
        super.onStart();
        valueCardBean = getArguments().getParcelable("recharge");



        tvUserMobile.setText(valueCardBean.getMobile());

        if (TextUtils.isEmpty(valueCardBean.getEntity())) {
            tvRechargeMoney.setText(valueCardBean.getSell_price()+"元");

            tvPresenterMoney.setVisibility(View.GONE);
            tvAdd.setVisibility(View.GONE);
            tvRewardTips.setVisibility(View.GONE);
        }else{

            tvRechargeMoney.setText(valueCardBean.getSell_price());
            tvPresenterMoney.setText(valueCardBean.getEntity()+"元");

        }

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @OnClick({R.id.tv_cancel, R.id.tv_ok})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_cancel:

                getDialog().dismiss();
                break;
            case R.id.tv_ok:

                PayModel payModel = new PayModel();

                payModel.create_recharge_order(valueCardBean.getRecharge_id(), valueCardBean.getUserId(), valueCardBean.getStore_id(), new NetWorkCallBack2<RechargeBean>() {
                    @Override
                    public void onStart() {
                        prLoading.setVisibility(View.VISIBLE);
                    }

                    @Override
                    public void onSucess(RechargeBean data) {

                        prLoading.setVisibility(View.GONE);

                        if (getDialog()!=null){

                            getDialog().dismiss();
                        }

                        ((MainActivity)activity).showPayFragment();

                        MessageEvent messageEvent = new MessageEvent(MessageEvent.RECHARGE);

                        Bundle bundle = new Bundle();
                        data.getOrder().setValueCardBean(valueCardBean);

                        bundle.putParcelable(MessageEvent.RECHARGE, data.getOrder());
                        messageEvent.setBundle(bundle);
                        EventBus.getDefault().post(messageEvent);
                    }

                    @Override
                    public void onFailed(String msg) {

                        ToastUtils.showShortToast(msg);
                        prLoading.setVisibility(View.GONE);

                    }
                });


                break;
        }
    }
}
