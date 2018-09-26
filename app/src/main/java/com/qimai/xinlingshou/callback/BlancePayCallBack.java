package com.qimai.xinlingshou.callback;

import com.qimai.xinlingshou.bean.BalancePayBean;

public interface BlancePayCallBack {

    void onPaySucess(int type,BalancePayBean balancePayBean);

    //支付失败
    void onPayFailed(String msg);
   //取消支付
    void onPayCancel();
}
