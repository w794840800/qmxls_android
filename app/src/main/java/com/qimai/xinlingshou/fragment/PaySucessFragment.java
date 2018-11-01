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
import com.qimai.xinlingshou.bean.RechargePrint;
import com.qimai.xinlingshou.bean.ordersBean;
import com.qimai.xinlingshou.dialog.BaseDialogFragment;
import com.qimai.xinlingshou.fragment.right.MessageEvent;
import com.qimai.xinlingshou.utils.DecimalFormatUtils;

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

    ordersBean ordersBean;
    RechargePrint rechargePrint;
    @BindView(R.id.tv_other_pay_method)
    TextView tvOtherPayMethod;
    @BindView(R.id.tv_recharge_method)
    TextView tvRechargeMethod;
    @BindView(R.id.tv_recharge_money)
    TextView tvRechargeMoney;
    @BindView(R.id.tv_recharge_balance_total)
    TextView tvRechargeBalanceTotal;
    @BindView(R.id.ll_recharge)
    LinearLayout llRecharge;

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




        if (messageEvent.getType().equals(MessageEvent.PAY_SUCESS_SHOW)) {


            hideShowView();

            Bundle bundle = messageEvent.getBundle();

            ordersBean = bundle.getParcelable(MessageEvent.PAY_SUCESS_SHOW);
            updateUI(ordersBean);

        }

        if (messageEvent.getType().equals(MessageEvent.RECHARGE_PAY_SUCESS)) {

            Bundle bundle = messageEvent.getBundle();

            hideShowView();

            rechargePrint = bundle.getParcelable(MessageEvent.RECHARGE_PAY_SUCESS);
            upRechargeUI(rechargePrint);
        }


       /* if (messageEvent.getType().equals("XianjingPay")) {

            llPriceDetail.setVisibility(View.VISIBLE);

            tvPayTotal.setText(messageEvent.getShifu_pay() + "元");

            tvReturnMoney.setText(messageEvent.getZhaoling_pay() + "元");
        }*/
        /*//余额支付
        else if (messageEvent.getType().equals(MessageEvent.BLANCE_PAY)) {

            llPriceBlance.setVisibility(View.VISIBLE);

            Bundle bundle = messageEvent.getBundle();


            balancePayBean = bundle.getParcelable(MessageEvent.BLANCE_PAY);


            updateUI(balancePayBean);


        }
        //混合支付
        else if (messageEvent.getType().equals(MessageEvent.MULTIPULTE_PAY)) {

            updateUI(balancePayBean);

        }*/

    }

    private void upRechargeUI(RechargePrint rechargePrint) {

        if (rechargePrint == null) {

            return;
        }
        llRecharge.setVisibility(View.VISIBLE);

       if (rechargePrint.getPayType().equals("010")){


            tvRechargeMethod.setText("微信支付");
       }else{
           tvRechargeMethod.setText("支付宝支付");


       }

       tvRechargeBalanceTotal.setText(DecimalFormatUtils.stringToMoneyWithOutSymbol(rechargePrint
       .getBalance_total())+"元");


       tvRechargeMoney.setText(DecimalFormatUtils.stringToMoneyWithOutSymbol(rechargePrint.getRechargeMoney())+"元");

    }


    private void updateUI(com.qimai.xinlingshou.bean.ordersBean ordersBean) {


        //表示使用了余额
        if (ordersBean.getUse_wallet() == 1) {

            //默认是0.00  不等于0.00就能说明是不是混合支付
            //混合支付
            if (!(ordersBean.getAmount().equals("0.00"))) {


                if (ordersBean.getPayment_id().equals("1")) {

                    tvOtherPayMethod.setText("微信支付");

                } else if (ordersBean.getPayment_id().equals("2")) {

                    tvOtherPayMethod.setText("支付宝支付");


                } else if (ordersBean.getPayment_id().equals("4")) {

                    tvOtherPayMethod.setText("现金支付支付");
                } else if (ordersBean.getPayment_id().equals("5")) {

                    tvOtherPayMethod.setText("自有微信支付");
                } else if (ordersBean.getPayment_id().equals("6")) {

                    tvOtherPayMethod.setText("自有支付宝支付");
                } else if (ordersBean.getPayment_id().equals("7")) {

                    tvOtherPayMethod.setText("POS机刷卡支付");
                }

                llMultipult.setVisibility(View.VISIBLE);
                tvMultiTotal.setText(DecimalFormatUtils.stringToMoneyWithOutSymbol(ordersBean.getTotal_amount()) + "元");
                tvMultiBalancePay.setText(ordersBean.getWallet_amount() + "元");
                tvMultiOtherPay.setText(ordersBean.getAmount() + "元");

            }
            //纯余额支付
            else {

                llPriceBlance.setVisibility(View.VISIBLE);

                tvBalanceTotal.setText(DecimalFormatUtils
                        .stringToMoneyWithOutSymbol(ordersBean.getWallet_amount()) + "元");
                tvBalanceReleaveOney.setText(DecimalFormatUtils
                        .stringToMoneyWithOutSymbol(ordersBean.getLeaveBalance()) + "元");

            }

        }
        //不带余额的支付
        else {

            //现金支付
            if (ordersBean.getPayment_id().equals("4")) {
                llPriceDetail.setVisibility(View.VISIBLE);

                tvPayTotal.setText(ordersBean.getActual_input_money() + "元");

                tvReturnMoney.setText(ordersBean.getCharge_money() + "元");


            }


        }


    }

    /***
     * 用来更新余额支付与混合支付
     * */

    private void updateUI(BalancePayBean balancePayBean) {

        if (balancePayBean != null) {

            Log.d(TAG, "updateUI: balancePayBean= " + balancePayBean.toString());

            int type = balancePayBean.getType();

            //余额支付
            if (type == BaseDialogFragment.BALANCE_PAY) {


                llPriceBlance.setVisibility(View.VISIBLE);

                tvBalanceTotal.setText(balancePayBean.getOrderMoney() + "元");
                tvBalanceReleaveOney.setText(balancePayBean.getBalanceLeave() + "元");

            } else if (type == BaseDialogFragment.BALANCE_MULTIP_PAY) {


                llMultipult.setVisibility(View.VISIBLE);

                tvMultiTotal.setText(balancePayBean.getOrderMoney() + "元");
                tvMultiBalancePay.setText(balancePayBean.getBalanceNeedPay() + "元");
                tvMultiOtherPay.setText(balancePayBean.getOrderLeaveMoney() + "元");
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

                hideShowView();
                ((MainActivity) getActivity()).showRightCrashierLayout();
                MessageEvent messageEvent2 = new MessageEvent(MessageEvent.ORDER_FINISH);
                EventBus.getDefault().post(messageEvent2);
                break;
        }
    }

    private void hideShowView() {
        llRecharge.setVisibility(View.GONE);
        llMultipult.setVisibility(View.GONE);
        llPriceBlance.setVisibility(View.GONE);
        llPriceDetail.setVisibility(View.GONE);
    }


}
