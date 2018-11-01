package com.qimai.xinlingshou.fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.qimai.xinlingshou.R;
import com.qimai.xinlingshou.bean.orderQueryBean;
import com.qimai.xinlingshou.callback.NetWorkCallBack;
import com.qimai.xinlingshou.model.PayModel;
import com.qimai.xinlingshou.utils.NetWorkUtils;
import com.qimai.xinlingshou.utils.ToastUtils;

import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;

public class PayTipsDialogFragment extends DialogFragment {

    //第三方支付
    public static final int WECHAT_ZHIFUBAO = 1;
    //余额充值
    public static final int RECHARGE = 2;

    private static final String TAG = "PayTipsDialogFragment";
    @BindView(R.id.iv_close)
    ImageView ivClose;
    @BindView(R.id.tv_again_pay)
    TextView tvAgainPay;
    @BindView(R.id.tv_query_status)
    TextView tvQueryStatus;
    @BindView(R.id.tv_status)
    TextView tvStatus;

    String payType;
    String trade_no;
    OnQueryListener onQueryListener;
    @BindView(R.id.pb_progress)
    ProgressBar pbProgress;

    int type;

    public static PayTipsDialogFragment getInstance(int type,String trade_no, String payType) {


        PayTipsDialogFragment payTipsDialogFragment = new PayTipsDialogFragment();

        Bundle bundle = new Bundle();
        bundle.putInt("type",type);
        bundle.putString("trade_no", trade_no);
        bundle.putString("payType", payType);
        payTipsDialogFragment.setArguments(bundle);
        return payTipsDialogFragment;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog);

        Bundle bundle = getArguments();

        if (bundle == null) {

            return;
        }
        type = bundle.getInt("type");
        payType = bundle.getString("payType");
        trade_no = bundle.getString("trade_no");


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.dialog_pay_tips, container, false);

        ButterKnife.bind(this, view);

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();

        getDialog().setCanceledOnTouchOutside(false);
        getDialog().setCancelable(false);


        getDialog().setOnDismissListener(new DialogInterface.OnDismissListener() {
            @Override
            public void onDismiss(DialogInterface dialog) {
                if (onQueryListener!=null){


                    onQueryListener.onDialogDisimiss();

                }
            }
        });

        Observable.intervalRange(0, 1, 20, 0, TimeUnit.SECONDS)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<Long>() {
                    @Override
                    public void onSubscribe(Disposable d) {

                        Log.d(TAG, "onSubscribe: start");
                    }

                    @Override
                    public void onNext(Long aLong) {
                        Log.d(TAG, "onNext: receive");
                        tvQueryStatus.setEnabled(true);

                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                });


    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

    }

    @OnClick({R.id.iv_close, R.id.tv_again_pay, R.id.tv_query_status})
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.iv_close:
                getDialog().dismiss();
                break;
            case R.id.tv_again_pay:

                getDialog().dismiss();
                break;
            case R.id.tv_query_status:


                if (!NetWorkUtils.isNetWorkAvaiable(getActivity())) {


                    ToastUtils.showShortToast("请检查网络连接");
                    return;
                }

                pbProgress.setVisibility(View.VISIBLE);
                PayModel.getInstance().queryOrderPayStatus(trade_no, payType, new NetWorkCallBack<orderQueryBean>() {


                    @Override
                    public void onSucess(orderQueryBean data) {


                        pbProgress.setVisibility(View.GONE);

                        if (data.getResult_code().equals("01")) {
                            if (onQueryListener != null) {

                                onQueryListener.onQueryPaySucess(type);

                                getDialog().dismiss();
                            }
                        } else if (data.getResult_code().equals("02")) {


                            tvStatus.setText("支付失败,请重新支付");
                            tvStatus.setTextColor(getContext().getResources().getColor(R.color.errorRed));

                        }
                    }

                    @Override
                    public void onFailed(String msg) {
                        pbProgress.setVisibility(View.GONE);


                    }
                });


                break;
        }

    }

    public void setOnQueryListener(OnQueryListener onQueryListener) {
        this.onQueryListener = onQueryListener;
    }

    public interface OnQueryListener {

        void onQueryPaySucess(int type);
        void onDialogDisimiss();
    }

    public void setTradeInfo(String trade_no, String payType) {

        Log.d(TAG, "setTradeInfo: trade_no= "+trade_no+" trade_no= "+payType);
        this.trade_no = trade_no;
        this.payType = payType;
    }

}
