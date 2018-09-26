package com.qimai.xinlingshou.calculate;

import com.qimai.xinlingshou.bean.MoneyBean;

/**
 * Created by wanglei on 18-7-3.
 *
 * Decorator
 */

public abstract class DisCountCrash implements CrashSuper{

    public MoneyBean moneyBean;

    CrashSuper crashSuper;

    public void attach(CrashSuper crashSuper){

        this.crashSuper = crashSuper;

    }

    public void showMoney(){



    }

    @Override
    public MoneyBean calculateMoney() {
   //     totalMoney = preMoney;
        //crashSuper.calculateMoney(preMoney);
        return moneyBean;
    }


}
