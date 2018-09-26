package com.qimai.xinlingshou.calculate;

import com.qimai.xinlingshou.bean.MoneyBean;

/**
 * Created by wanglei on 18-7-3.
 */

public class MianzhiCrash extends DisCountCrash{

    private static final String TAG = "MianzhiCrash";
    private double max;
    private double reduce;
    public MianzhiCrash(double max,double reduce){

        this.max = max;
        this.reduce = reduce;

    }

    @Override
    public MoneyBean calculateMoney() {


        moneyBean = crashSuper.calculateMoney();

      //  System.out.println(moneyBean.toString());

        if (moneyBean.getEndMoney()>max){

            moneyBean.setDiscountMoney(moneyBean.getDiscountMoney()+reduce);

            moneyBean.setMianzhiOrZhrkouDiscount(reduce);
        }else{

            moneyBean.setCanDiscount(true);
            moneyBean.setMianzhiOrZhrkouDiscount(0.00);

        }

       return moneyBean;
        //return super.calculateMoney();
    }
}
