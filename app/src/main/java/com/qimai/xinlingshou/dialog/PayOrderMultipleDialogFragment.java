package com.qimai.xinlingshou.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.qimai.xinlingshou.R;
import com.qimai.xinlingshou.bean.BalancePayBean;
import com.qimai.xinlingshou.bean.ordersBean;
import com.qimai.xinlingshou.callback.BlancePayCallBack;
import com.qimai.xinlingshou.callback.NetWorkCallBack;
import com.qimai.xinlingshou.fragment.right.MessageEvent;
import com.qimai.xinlingshou.model.PayModel;
import com.qimai.xinlingshou.utils.DecimalFormatUtils;
import com.qimai.xinlingshou.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;


/**
 * 当余额不足够的时候调这个Dialog 混合支付
 */

public class PayOrderMultipleDialogFragment extends BaseDialogFragment {

    private static final String TAG = "PayOrderMultipleDialogF";
    @BindView(R.id.tv_tips1_desc)
    TextView tvTips1Desc;
    @BindView(R.id.tv_order_money)
    TextView tvOrderMoney;
    @BindView(R.id.tv_blance_money)
    TextView tvBlanceMoney;
    @BindView(R.id.tv_leave_money)
    TextView tvLeaveMoney;
    @BindView(R.id.tv_ok)
    Button tvOk;
    @BindView(R.id.tv_cancel)
    Button tvCancel;

    String balance;
    PayModel payModel;

    //剩余应支付的金额
    String leaveMoney;
    String userId;
    private String needPayMoney;
    BalancePayBean balancePayBean;
    String totalMoney;

    public void setBlancePayCallBack(BlancePayCallBack blancePayCallBack){


        this.blancePayCallBack = blancePayCallBack;
    }
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog1);


    }

    public static PayOrderMultipleDialogFragment getInstance(String balance,String orderMoney
    ,String userId) {

        PayOrderMultipleDialogFragment payOrderBalanceDialogFragment = new PayOrderMultipleDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putString("balance", balance);
        bundle.putString("orderMoney",orderMoney);
        bundle.putString("userId",userId);

        payOrderBalanceDialogFragment.setArguments(bundle);

        return payOrderBalanceDialogFragment;
    }


    public static PayOrderMultipleDialogFragment getInstance(BalancePayBean balancePayBean) {

        PayOrderMultipleDialogFragment payOrderBalanceDialogFragment = new PayOrderMultipleDialogFragment();
        Bundle bundle = new Bundle();
        bundle.putParcelable("balance",balancePayBean);
        payOrderBalanceDialogFragment.setArguments(bundle);
        return payOrderBalanceDialogFragment;
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_multiple_pay, container, false);

        ButterKnife.bind(this, view);

        updateUI(getArguments());


        return view;
    }



    private void updateUI(Bundle arguments) {

        if (arguments==null){
            return;
        }

        balancePayBean = arguments.getParcelable("balance");

        Log.d(TAG, "updateUI: balancePayBean- "+balancePayBean.toString());

        userId = balancePayBean.getUserId();

        totalMoney = balancePayBean.getOrderMoney();

        tvOrderMoney.setText(DecimalFormatUtils.stringToMoneyWithOutSymbol(totalMoney)+"元");
        tvBlanceMoney.setText(balancePayBean.getBalanceTotal()+"元");
        tvLeaveMoney.setText(DecimalFormatUtils.doubleToMoneyWithOutSymbol(
                Double.parseDouble(balancePayBean.getOrderLeaveMoney()))+"元");

       // tvOrderMoney.setText(totalMoney+"元");
    }

    @Override
    public void onStart() {
        super.onStart();
        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setCancelable(false);
        /*WindowManager.LayoutParams layoutParams = getDialog().getWindow().getAttributes();

        layoutParams.width = 500;
        layoutParams.height = 500;
        getDialog().getWindow().setAttributes(layoutParams);*/
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @OnClick({R.id.tv_ok, R.id.tv_cancel})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_ok:

                ordersBean ordersBean = balancePayBean.getOrdersBean();

                ordersBean.setUse_wallet(balancePayBean.getUse_wallet());
                ordersBean.setTotalBalance(balancePayBean.getBalanceTotal());
                ordersBean.setAmount((balancePayBean.getAmount()));

                ordersBean.setWallet_amount(balancePayBean.getWallet_amount());
                ordersBean.setStatus(1);

                ordersBean.setLeaveBalance(balancePayBean.getBalanceLeave());
                if (payModel==null){

                    payModel = new PayModel();
                }

                if (progressDialog==null){

                    createProgressDialog();
                }
                //progressDialog.show();

                DialogUtils.createDialog(getActivity());
                payModel.uploadOrderInfo(balancePayBean.getOrdersBean(),new NetWorkCallBack() {
                    @Override
                    public void onSucess(Object data) {

                        //上传成功就进行余额扣减

                        payModel.blancePay(userId, balancePayBean.getOrdersBean().getOrder(), new NetWorkCallBack() {
                            @Override
                            public void onSucess(Object data) {

                                //progressDialog.dismiss();

                                DialogUtils.cancelDialog();

                                //余额扣除成功就要通知会员界面，重新刷新
                                NotificationClientInfoUpdate(userId);

                               // balance_leave = calculateLeavemoney(balancePayBean);
                                if (blancePayCallBack!=null){
                                 //   Log.d(TAG, "onSucess: balance_leave= "+balance_leave);
                                   // balancePayBean.setBalanceLeave(balance_leave);
                                    balancePayBean.setType(BaseDialogFragment.BALANCE_MULTIP_PAY);

                                    /*balancePayBean.setOrderLeaveMoney(balancePayBean.getOrdersBean().getAmount());*/
                                    Log.d(TAG, "onSucess222: balancePayBean= "+balancePayBean.toString());

                                    blancePayCallBack.onPaySucess(BaseDialogFragment.BALANCE_MULTIP_PAY,balancePayBean);
                                }

                                getDialog().dismiss();


                            }

                            @Override
                            public void onFailed(String msg) {

                                DialogUtils.cancelDialog();

                                //progressDialog.dismiss();

                                //ToastUtils.showShortToast(msg);
                                if (blancePayCallBack!=null){

                                    blancePayCallBack.onPayFailed(msg);
                                }


                            }
                        });



                    }

                    @Override
                    public void onFailed(String msg) {

                        progressDialog.dismiss();

                        ToastUtils.showLongToast(msg);
                    }
                });

                break;
            case R.id.tv_cancel:
                blancePayCallBack.onPayCancel();
                getDialog().dismiss();

                break;
        }
    }






}
