package com.qimai.xinlingshou.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qimai.xinlingshou.R;
import com.qimai.xinlingshou.activity.MainActivity;
import com.qimai.xinlingshou.bean.BalancePayBean;
import com.qimai.xinlingshou.dialog.BaseDialogFragment;
import com.qimai.xinlingshou.fragment.right.MessageEvent;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class PaySucessFragment extends Fragment {

    public static final int CRASH = 0;


    private static final String TAG = "PaySucessFragment";
    @BindView(R.id.tv_pay_total)
    TextView tvPayTotal;
    @BindView(R.id.tv_return_money)
    TextView tvReturnMoney;
    @BindView(R.id.tv_repeat_print)
    TextView tvRepeatPrint;
    @BindView(R.id.tv_continue)
    TextView tvContinue;
    @BindView(R.id.ll_price_detail)
    LinearLayout llPriceDetail;
    @BindView(R.id.tv_balance_total)
    TextView tvBalanceTotal;
    @BindView(R.id.tv_balance_releave_oney)
    TextView tvBalanceReleaveOney;
    @BindView(R.id.ll_price_blance)
    LinearLayout llPriceBlance;

    BalancePayBean balancePayBean;
    @BindView(R.id.tv_multi_total)
    TextView tvMultiTotal;
    @BindView(R.id.tv_multi_balance_pay)
    TextView tvMultiBalancePay;
    @BindView(R.id.ll_multipult)
    LinearLayout llMultipult;
    @BindView(R.id.tv_multi_other_pay)
    TextView tvMultiOtherPay;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {


        View view = inflater.inflate(R.layout.pay_sucess_view, container, false);

        Log.d(TAG, "onCreateView: ");
        ButterKnife.bind(this, view);
        return view;
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent messageEvent) {

        //三种支付fragment都会发来所以判断下

        if (messageEvent.getType().equals("XianjingPay")) {

            llPriceDetail.setVisibility(View.VISIBLE);

            tvPayTotal.setText(messageEvent.getShifu_pay() + "元");

            tvReturnMoney.setText(messageEvent.getZhaoling_pay() + "元");
        }
        //余额支付
        else if (messageEvent.getType().equals(MessageEvent.BLANCE_PAY)) {

            llPriceBlance.setVisibility(View.VISIBLE);

            Bundle bundle = messageEvent.getBundle();


            balancePayBean = bundle.getParcelable(MessageEvent.BLANCE_PAY);


            updateUI(balancePayBean);


        }
        //混合支付
        else if (messageEvent.getType().equals(MessageEvent.MULTIPULTE_PAY)) {

            updateUI(balancePayBean);

        }

    }

    /***
     * 用来更新余额支付与混合支付
     * */

    private void updateUI(BalancePayBean balancePayBean) {

        if (balancePayBean != null) {

            Log.d(TAG, "updateUI: balancePayBean= "+balancePayBean.toString());

            int type = balancePayBean.getType();

            //余额支付
            if (type == BaseDialogFragment.BALANCE_PAY) {


                llPriceBlance.setVisibility(View.VISIBLE);

                tvBalanceTotal.setText(balancePayBean.getOrderMoney() + "元");
                tvBalanceReleaveOney.setText(balancePayBean.getBalanceLeave() + "元");


            } else if (type == BaseDialogFragment.BALANCE_MULTIP_PAY) {


                llMultipult.setVisibility(View.VISIBLE);

                tvMultiTotal.setText(balancePayBean.getOrderMoney()+"元");
                tvMultiBalancePay.setText(balancePayBean.getBalanceNeedPay()+"元");
                tvMultiOtherPay.setText(balancePayBean.getOrderLeaveMoney()+"元");
            }


        }


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }

    @OnClick({R.id.tv_pay_total, R.id.tv_return_money, R.id.tv_repeat_print, R.id.tv_continue})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_pay_total:
                break;
            case R.id.tv_return_money:
                break;
            case R.id.tv_repeat_print:

                MessageEvent messageEvent = new MessageEvent(MessageEvent.REPEATPRINT);
                EventBus.getDefault().post(messageEvent);

                break;
            case R.id.tv_continue:

                llMultipult.setVisibility(View.GONE);

                llPriceBlance.setVisibility(View.GONE);
                llPriceDetail.setVisibility(View.GONE);
                ((MainActivity) getActivity()).showRightCrashierLayout();

                break;
        }
    }
}
