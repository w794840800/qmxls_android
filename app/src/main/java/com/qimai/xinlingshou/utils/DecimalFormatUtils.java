package com.qimai.xinlingshou.utils;

import android.text.TextUtils;

import java.text.DecimalFormat;

/**
 * Created by wanglei on 18-5-21.
 */

public class DecimalFormatUtils {


    public static DecimalFormat decimalFormat = new DecimalFormat("######0.00");
    
    public static String doubleToMoney(double money){
        
        

        return "￥ "+decimalFormat.format(money);
    }
    public static String doubleToMoney2(double money){



        return "￥ "+decimalFormat.format(money);
    }
    public static String stringToMoney(String money){



        return "￥ "+decimalFormat.format(Double.parseDouble(money));
    }
    public static String doubleToMoneyWithOutSymbol(double money){



        return decimalFormat.format(money);
    }



    public static String stringToMoneyWithOutSymbol(String money){

        Double money1 = 0.00;
        if (TextUtils.isEmpty(money)){

            money1 = 0.00;

        }else {
        money1 =Double.parseDouble(money);
        }

        return decimalFormat.format(money1);
    }


}
