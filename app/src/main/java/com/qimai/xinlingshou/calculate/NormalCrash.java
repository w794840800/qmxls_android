package com.qimai.xinlingshou.calculate;

import android.util.Log;

import com.qimai.xinlingshou.bean.MoneyBean;

/**
 * Created by wanglei on 18-7-3.
 *
 * ConcreteComponent
 */

public class NormalCrash implements CrashSuper{
    private static final String TAG = "NormalCrash";

    private Double money1;
    public NormalCrash(Double money){
       money1 =money;
    }
   MoneyBean moneyBean;
    @Override
    public MoneyBean calculateMoney() {
        moneyBean = new MoneyBean();
        moneyBean.setTotalMoney(money1);
        Log.d(TAG, "calculateMoney: moneyBean= "+moneyBean.toString());
        return moneyBean;
    }
}
