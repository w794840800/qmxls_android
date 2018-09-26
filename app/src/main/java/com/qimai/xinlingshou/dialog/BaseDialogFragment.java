package com.qimai.xinlingshou.dialog;

import android.support.v4.app.DialogFragment;

import com.qimai.xinlingshou.callback.BlancePayCallBack;
import com.qimai.xinlingshou.model.PayModel;

public class BaseDialogFragment extends DialogFragment {


    //只是余额支付
    public static final int BALANCE_PAY = 1;

    //混合支付
    public static final int BALANCE_MULTIP_PAY = 2;

    protected BlancePayCallBack blancePayCallBack;


    public void setBlancePayCallBack(BlancePayCallBack blancePayCallBack) {
        this.blancePayCallBack = blancePayCallBack;
    }
}
