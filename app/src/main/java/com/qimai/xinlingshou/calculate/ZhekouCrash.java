package com.qimai.xinlingshou.calculate;

import com.qimai.xinlingshou.bean.MoneyBean;

/**
 * Created by wanglei on 18-7-3.
 */

public class ZhekouCrash extends DisCountCrash {

    private double discount;
    public ZhekouCrash(double discount){

        this.discount = discount;
    }


    @Override
    public MoneyBean calculateMoney() {

    moneyBean = crashSuper.calculateMoney();

    double zhekouMoney = (moneyBean.getEndMoney()*(1-discount));

    moneyBean.setMianzhiOrZhrkouDiscount(zhekouMoney);
    moneyBean.setDiscountMoney(moneyBean.getDiscountMoney()
    +zhekouMoney);


    return moneyBean;
    }
}
