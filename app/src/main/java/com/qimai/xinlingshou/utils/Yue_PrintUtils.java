package com.qimai.xinlingshou.utils;


import android.text.TextUtils;
import android.util.Log;

import com.qimai.xinlingshou.bean.PrintInfoBean;
import com.qimai.xinlingshou.bean.goodsBean;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * 打印模块
 *
 * typeid=1  余额支付
 *
 *
 * typeid=2  会员支付
 *
 *typeid=3 混合支付
 *
 * Created by niuchao on 18-9-28.
 */

public class Yue_PrintUtils {

    private static final String TAG = "PrintUtils";

        public static void print_info(PrintInfoBean  printInfoBean){

            if (printInfoBean==null){
                return;
            }

            Log.d(TAG, "print_info: printInfoBean= "+printInfoBean.toString());
        AidlUtil.getInstance().printText(
                ""+printInfoBean.getStoreName(), 2, 35, true,
                false, ESCUtil.alignCenter());
        AidlUtil.getInstance().printText(
                "订单号:"+printInfoBean.getStoreOrderId(), 1, 28, false, false,
                ESCUtil.alignLeft());

        AidlUtil.getInstance().printText(
                "日期：" + ""+printInfoBean.getOrderTime(), 1, 25, false,
                false, ESCUtil.alignLeft());

        AidlUtil.getInstance().printText(
                "订单类型：" + "商品订单", 1, 25, false,
                false, ESCUtil.alignLeft());

            AidlUtil.getInstance().printText("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _",
                    2, 25, false, false, null);

            AidlUtil.getInstance().printText(formatStr2("编号") +
                            formatStr("商品名称")
                            + formatStr2("数量")
                            + formatStr2("单价")+formatStr2("金额"), 1, 28, false, false,
                    ESCUtil.alignLeft());

            int i=1;
            int totalNum = 0;
            for (goodsBean entry:
                    printInfoBean.getGoodsBeanArrayList()) {


                AidlUtil.getInstance().printText(formatStr6(i+"") +
                                formatStr(""+entry.getGoodsName())+"  "
                                + formatStr6("  "+entry.getNumber())
                                + formatStr2(" "+DecimalFormatUtils.doubleToMoneyWithOutSymbol(entry.getPrice()))+formatStr2(""+DecimalFormatUtils.doubleToMoneyWithOutSymbol(entry.getPrice()*entry.getNumber())), 1, 28, false, false,
                        ESCUtil.alignLeft());

                totalNum +=entry.getNumber();

                i++;
            }




//        AidlUtil.getInstance().printText(formatStr3("1") , 0, 28, false, false,
//                ESCUtil.alignCenter());
//        AidlUtil.getInstance().printText(formatStr3("1")+
//                formatStr("梅干菜扣肉")
//                        + formatStr3("12")
//                        + formatStr2("￥12.00")+formatStr2("￥128.00"), 0, 28, false, false,
//                ESCUtil.alignLeft());


        //商品列表循环
//        AidlUtil.getInstance().printText(format2Life("1") +
//                formatStr("梅干菜扣肉")
//                        + formatStr2("2")
//                        + formatStr2("10.00"), 0, 28, false, false,
//                ESCUtil.alignLeft());
//        AidlUtil.getInstance().printText(
//                formatRight("￥" + 20.00),
//                1, 28, false, false,
//
//                ESCUtil.alignLeft());
//
//
//
//        AidlUtil.getInstance().printText(formatStr2("1") +
//                        formatStr("梅干菜扣肉")
//                        + formatStr2("2") + " "
//                        + formatStr2("10.00"), 0, 28, false, false,
//                ESCUtil.alignLeft());
//        AidlUtil.getInstance().printText(
//                formatRight("￥" + 20.00),
//                1, 28, false, false,
//
//                ESCUtil.alignLeft());
//
//
//
//        AidlUtil.getInstance().printText(formatStr2("1") +
//                        formatStr("梅干菜扣肉")
//                        + formatStr2("2") + " "
//                        + formatStr2("10.00"), 0, 28, false, false,
//                ESCUtil.alignLeft());
//        AidlUtil.getInstance().printText(
//                formatRight("￥" + 20.00),
//                1, 28, false, false,
//
//                ESCUtil.alignLeft());

      /*  AidlUtil.getInstance().printText(
                formatStr("红烧仔鸡")
                        + formatStr2("2") + " "
                        + formatStr2("13.00"), 0, 28, false, false,
                ESCUtil.alignLeft());

        AidlUtil.getInstance().printText(
                formatRight("￥" + 20.00),
                1, 28, false, false,
                ESCUtil.alignLeft());
        AidlUtil.getInstance().printText(
                formatStr("蒸小碗")
                        + formatStr2("2") + " "
                        + formatStr2("10.00"), 0, 28, false, false,
                ESCUtil.alignLeft());

        AidlUtil.getInstance().printText(
                formatRight("￥" + 20.00),
                1, 28, false, false,
                ESCUtil.alignLeft());
        AidlUtil.getInstance().printText(
                formatStr("黄焖鸡")
                        + formatStr2("2") + " "
                        + formatStr2("10.00"), 0, 28, false, false,
                ESCUtil.alignLeft());

        AidlUtil.getInstance().printText(
                formatRight("￥" + 20.00),
                1, 28, false, false,
                ESCUtil.alignLeft());*///商品循环结束
            AidlUtil.getInstance().printText("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _",
                    2, 25, false, false, null);

            AidlUtil.getInstance().printText(
                    formatStr1("商品品种:"+printInfoBean.getGoodsBeanArrayList().size()), 0, 28, false, false,
                    ESCUtil.alignLeft());
            AidlUtil.getInstance().printText(
                    formatStr1("商品数量："+totalNum), 1, 28, false, false,
                    ESCUtil.alignLeft());

            AidlUtil.getInstance().printText(formatLife("合计金额："), 0, 28, false, false,
                    ESCUtil.alignLeft());
            AidlUtil.getInstance().printText(
                    formatRight2(""+printInfoBean.getOrderTotal()+""), 1, 30, false,
                    false, ESCUtil.alignLeft());

            AidlUtil.getInstance().printText(formatLife("优惠金额："), 0, 28, false, false,
                    ESCUtil.alignLeft());
            AidlUtil.getInstance().printText(
                    formatRight2(DecimalFormatUtils.stringToMoney(printInfoBean.getDiscountMoney())+""), 1, 30, false,
                    false, ESCUtil.alignLeft());

            AidlUtil.getInstance().printText(formatLife("应收金额："), 0, 28, false, false,
                    ESCUtil.alignLeft());
            AidlUtil.getInstance().printText(
                    formatRight2(""+printInfoBean.getActualPay()+""), 1, 30, false,
                    false, ESCUtil.alignLeft());



            int typeid = printInfoBean.getMethod();


            AidlUtil.getInstance().printText(formatLife("支付方式："), 0, 28, false, false,
                    ESCUtil.alignLeft());

            if (printInfoBean.isMultiplut()){

                AidlUtil.getInstance().printText(
                        formatRight2("混合支付"), 1, 30, false,
                        false, ESCUtil.alignLeft());
            }else {

                AidlUtil.getInstance().printText(
                        formatRight2(""+printInfoBean.getPayType()), 1, 30, false,
                        false, ESCUtil.alignLeft());
            }


        //微信/支付宝
         if (typeid==1) {
                AidlUtil.getInstance().printText(formatLife("："), 0, 28, false, false,
                        ESCUtil.alignLeft());
                AidlUtil.getInstance().printText(
                        formatRight2(""+printInfoBean.getActualPay()+""), 1, 30, false,
                        false, ESCUtil.alignLeft());


                if (!TextUtils.isEmpty(printInfoBean.getUserMobile())){

                    AidlUtil.getInstance().printText("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _",
                            2, 25, false, false, null);
                    AidlUtil.getInstance().printText(formatLife("会员卡号："), 0, 28, false, false,
                            ESCUtil.alignLeft());
                    AidlUtil.getInstance().printText(
                            formatRight(""+printInfoBean.getUserMobile()), 1, 30, false,
                            false, ESCUtil.alignLeft());
                }
            /*AidlUtil.getInstance().printText(
                    formatStr("本次积分:50"), 0, 28, true, false,
                    ESCUtil.alignLeft());
            AidlUtil.getInstance().printText(
                    formatStr("总积分：20"), 1, 28, true, false,
                    ESCUtil.alignLeft());*/
            }
            //标记支付
            else if (typeid==3){


             AidlUtil.getInstance().printText(formatLife("标记支付："), 0, 28, false, false,
                     ESCUtil.alignLeft());

             AidlUtil.getInstance().printText(
                     formatRight2(""+printInfoBean.getActualPay()+""), 1, 30, false,
                     false, ESCUtil.alignLeft());


             if (!TextUtils.isEmpty(printInfoBean.getUserMobile())){



                 AidlUtil.getInstance().printText("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _",
                         2, 25, false, false, null);
                 AidlUtil.getInstance().printText(formatLife("会员卡号："), 0, 28, false, false,
                         ESCUtil.alignLeft());
                 AidlUtil.getInstance().printText(
                         formatRight(""+printInfoBean.getUserMobile()), 1, 30, false,
                         false, ESCUtil.alignLeft());

             }

         }

        //余额支付
        else if (typeid==4) {

            AidlUtil.getInstance().printText(formatLife("余额支付："), 0, 28, false, false,
                    ESCUtil.alignLeft());



             AidlUtil.getInstance().printText(
                     formatRight2(""+printInfoBean.getActualPay()+""), 1, 30, false,
                     false, ESCUtil.alignLeft());

            AidlUtil.getInstance().printText("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _",
                    2, 25, false, false, null);
            AidlUtil.getInstance().printText(formatLife("会员卡号："), 0, 28, false, false,
                    ESCUtil.alignLeft());
            AidlUtil.getInstance().printText(
                    formatRight(""+printInfoBean.getUserMobile()), 1, 30, false,
                    false, ESCUtil.alignLeft());
           /* AidlUtil.getInstance().printText(
                    formatStr("本次积分:50"), 0, 28, true, false,
                    ESCUtil.alignLeft());
            AidlUtil.getInstance().printText(
                    formatStr("总积分：20"), 1, 28, true, false,
                    ESCUtil.alignLeft());*/
            AidlUtil.getInstance().printText(formatLife("账户余额："), 0, 28, false, false,
                    ESCUtil.alignLeft());
            AidlUtil.getInstance().printText(
                    formatRight(""+DecimalFormatUtils.stringToMoneyWithOutSymbol(printInfoBean.getLeaveBalance())+"元"), 1, 30, false,
                    false, ESCUtil.alignLeft());
        }
        //现金支付
        else if (typeid==2) {
            AidlUtil.getInstance().printText(formatLife("现金支付："), 0, 28, false, false,
                    ESCUtil.alignLeft());
            AidlUtil.getInstance().printText(
                    formatRight2("￥ "+DecimalFormatUtils.stringToMoneyWithOutSymbol(printInfoBean.getActualCrashPay())+""), 1, 30, false,
                    false, ESCUtil.alignLeft());
            AidlUtil.getInstance().printText(formatLife("找零："), 0, 28, false, false,
                    ESCUtil.alignLeft());
            AidlUtil.getInstance().printText(
                    formatRight2(""+printInfoBean.getReturnCharge()+""), 1, 30, false,
                    false, ESCUtil.alignLeft());

            if (!TextUtils.isEmpty(printInfoBean.getUserMobile())){

            AidlUtil.getInstance().printText("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _",
                    2, 25, false, false, null);
            AidlUtil.getInstance().printText(formatLife("会员卡号："), 0, 28, false, false,
                    ESCUtil.alignLeft());
            AidlUtil.getInstance().printText(
                    formatRight(""+printInfoBean.getUserMobile()), 1, 30, false,
                    false, ESCUtil.alignLeft());
            }
            /*AidlUtil.getInstance().printText(
                    formatStr("本次积分:50"), 0, 28, true, false,
                    ESCUtil.alignLeft());
            AidlUtil.getInstance().printText(
                    formatStr("总积分：20"), 1, 28, true, false,
                    ESCUtil.alignLeft());*/
        }
        else if (typeid==5){  //混合支付
            AidlUtil.getInstance().printText(formatLife("余额支付："), 0, 28, false, false,
                    ESCUtil.alignLeft());

             AidlUtil.getInstance().printText(
                     formatRight2("￥ "+printInfoBean.getBalancePay()+""), 1, 30, false,
                     false, ESCUtil.alignLeft());


             if (printInfoBean.getPayType().contains("现金")){


                 AidlUtil.getInstance().printText(formatLife(printInfoBean.getPayType()+"："), 0, 28, false, false,
                         ESCUtil.alignLeft());


                 AidlUtil.getInstance().printText(
                         formatRight2("￥ "+DecimalFormatUtils.stringToMoneyWithOutSymbol(printInfoBean.getActualCrashPay())+""), 1, 30, false,
                         false, ESCUtil.alignLeft());


             }else{

                 AidlUtil.getInstance().printText(formatLife(printInfoBean.getPayType()+"："), 0, 28, false, false,
                         ESCUtil.alignLeft());

                 AidlUtil.getInstance().printText(
                         formatRight2(""+DecimalFormatUtils.stringToMoney(printInfoBean.getLeaveOrderPay())), 1, 30, false,
                         false, ESCUtil.alignLeft());
             }



             if (!TextUtils.isEmpty(printInfoBean.getReturnCharge())){


                 AidlUtil.getInstance().printText(formatLife("找零金额："), 0, 28, false, false,
                         ESCUtil.alignLeft());


                 AidlUtil.getInstance().printText(
                         formatRight2(""+printInfoBean.getReturnCharge()+""), 1, 30, false,
                         false, ESCUtil.alignLeft());


             }

             if (!TextUtils.isEmpty(printInfoBean.getUserMobile())){



                 AidlUtil.getInstance().printText("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _",
                         2, 25, false, false, null);
                 AidlUtil.getInstance().printText(formatLife("会员卡号："), 0, 28, false, false,
                         ESCUtil.alignLeft());
                 AidlUtil.getInstance().printText(
                         formatRight(""+printInfoBean.getUserMobile()), 1, 30, false,
                         false, ESCUtil.alignLeft());

             }


            /*AidlUtil.getInstance().printText(
                    formatStr("本次积分:50"), 0, 28, true, false,
                    ESCUtil.alignLeft());
            AidlUtil.getInstance().printText(
                    formatStr("总积分：20"), 1, 28, true, false,
                    ESCUtil.alignLeft());*/
            AidlUtil.getInstance().printText(formatLife("账户余额："), 0, 28, false, false,
                    ESCUtil.alignLeft());
            AidlUtil.getInstance().printText(
                    formatRight("0.00元"), 1, 30, false,
                    false, ESCUtil.alignLeft());

        }
            AidlUtil.getInstance().printText("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _",
                    2, 25, false, false, null);
        AidlUtil.getInstance().printText(
                        "  " + "谢谢惠顾，感谢下次光临！", 1, 25, false,
                        false, null);
                AidlUtil.getInstance().print3Line();

                    AidlUtil.getInstance().cutPrint();

    }

    /**
     *  储值打印模块
     * @param xxxx
     */
    public static void print_chuzhi(String xxxx) {
        AidlUtil.getInstance().printText(
                "XX超市（长江西路二店）", 2, 35, true,
                false, ESCUtil.alignCenter());
        AidlUtil.getInstance().printText(
                "订单号:10055488450", 0, 28, true, false,
                ESCUtil.alignLeft());
        AidlUtil.getInstance().printText(
                "流水号：100558881", 1, 28, true, false,
                ESCUtil.alignLeft());

        AidlUtil.getInstance().printText(
                "日期：" + "2018.08.08 17:25:22", 0, 25, true,
                false, ESCUtil.alignLeft());
        AidlUtil.getInstance().printText(
                "机号：" + "02", 1, 25, true,
                false, ESCUtil.alignLeft());
        AidlUtil.getInstance().printText(
                "收银员：" + "张三", 0, 25, true,
                false, ESCUtil.alignLeft());
        AidlUtil.getInstance().printText(
                "订单类型：" + "商品订单", 1, 25, true,
                false, ESCUtil.alignLeft());

        AidlUtil.getInstance().printText("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _",
                2, 25, false, false, null);


//储值
        AidlUtil.getInstance().printText(
                        formatStr("储值卡名称") + " "
                        + formatStr("储值金额") + " "
                                + formatStr("赠送金额")+ " "
                                + formatStr("总金额"), 1, 28, false, false,
                ESCUtil.alignLeft());
        AidlUtil.getInstance().printText(
                formatStr("中国邮政卡") + " "
                        + formatStr("200.00") + " "
                        + formatStr("10.00")+ " "
                        + formatStr("210.00"), 1, 28, false, false,
                ESCUtil.alignLeft());


        AidlUtil.getInstance().printText(formatLife("微信支付："), 0, 28, false, false,
                ESCUtil.alignLeft());
        AidlUtil.getInstance().printText(
                formatRight("20元"), 1, 30, false,
                false, ESCUtil.alignLeft());
        AidlUtil.getInstance().printText("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _",
                2, 25, false, false, null);
        AidlUtil.getInstance().printText(formatLife("会员卡号："), 0, 28, false, false,
                ESCUtil.alignLeft());
        AidlUtil.getInstance().printText(
                formatRight("1115555551"), 1, 30, false,
                false, ESCUtil.alignLeft());
        AidlUtil.getInstance().printText(
                formatStr("本次积分:50"), 0, 28, true, false,
                ESCUtil.alignLeft());
        AidlUtil.getInstance().printText(
                formatStr("总积分：20"), 1, 28, true, false,
                ESCUtil.alignLeft());
        AidlUtil.getInstance().printText(formatLife("账户余额："), 0, 28, false, false,
                ESCUtil.alignLeft());
        AidlUtil.getInstance().printText(
                formatRight("70.00"), 1, 30, false,
                false, ESCUtil.alignLeft());

        AidlUtil.getInstance().printText("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _",
                2, 25, false, false, null);
        AidlUtil.getInstance().printText(
                "  " + "谢谢惠顾，感谢下次光临！", 1, 25, false,
                false, null);
        AidlUtil.getInstance().print3Line();
    }


    public static String formatStr2(String str) {


        int len = strlen(str);

        if (len >= 8) {
            str = str.substring(0, 8);
            return str;
        }

        int padLen = 8 - len;
        for (int i = 0; i < padLen; i++) {
            str += " ";
        }

        return str;


    }

    public static String formatStr3(String str) {

        int len = strlen(str);
        //   str = str.replaceAll("\\.", "");

        if (len >= 10) {
//            str = str.substring(0,6);
            return str;
        }

        int padLen = 10 - len;
        for (int i = 0; i < padLen; i++) {
            str= " "+str;
        }


        return str;


    }
    public static String format2Life(String str) {


        int len = strlen(str);
        //   str = str.replaceAll("\\.", "");

        if (len >= 8) {

            return str;
        }

        int padLen = 8 - len;
        for (int i = 0; i < padLen; i++) {
            str += " ";
        }

        return str;

    }


    public static String formatStr(String str) {


        int len = strlen(str);
     //   str = str.replaceAll("\\.", "");

        if (len >= 12) {
            str = str.substring(0,6);
            return str;
        }

        int padLen = 12 - len;
        for (int i = 0; i < padLen; i++) {
            str += " ";
        }

        return str;

    }
    public static String formatStr22(String str) {


        int len = strlen(str);
        //   str = str.replaceAll("\\.", "");

        if (len >= 8) {
            str = str.substring(0,4);
            return str;
        }

        int padLen = 8 - len;
        for (int i = 0; i < padLen; i++) {
            str += " ";
        }

        return str;

    }


    public static String formatLife(String str) {


        int len = strlen(str);
        //   str = str.replaceAll("\\.", "");

        if (len >= 12) {

            return str;
        }

        int padLen = 12 - len;
        for (int i = 0; i < padLen; i++) {
            str += " ";
        }

        return str;

    }

    public static String formatRight(String str) {


        int len = strlen(str);
        //   str = str.replaceAll("\\.", "");

        if (len >= 10) {
//            str = str.substring(0,6);
            return str;
        }

        int padLen = 10 - len;
        for (int i = 0; i < padLen; i++) {
           str= " "+str;
        }

        return str;

    }
    public static int strlen(String str)
    {
        if (str == null)
        {
            return 0;
        }

        return str.replaceAll("[^\\x00-\\xff]", "**").length();
//
//        int nResult = 0;
//
//        char[] chars = str.toCharArray();
//        //判断每个字符
//        for (int i = 0; i < chars.length; i++){
//            if (chars[i] <= 127) {
//                nResult++;
//            } else{
//                nResult += 2;
//            }
//        }

//        return nResult;
    }

//        try {
//            String res = "";
//            if (str.length() > 6) {
//                str = str.substring(0,6);
//                res = str.replaceAll("\\.", "");
//                return res;
//            }
//            if (str.length() == 5) {
//                res = String.format("%2s", "");
//                res = res.replaceAll("\\s", " ");
//            } else if (str.length() == 4) {
//                res = String.format("%4s", "");
//                res = res.replaceAll("\\s", " ");
//            } else if (str.length() == 3) {
//                res = String.format("%6s", "");
//                res = res.replaceAll("\\s", " ");
//            } else if (str.length() == 2) {
//                res = String.format("%8s", "");
//                res = res.replaceAll("\\s", " ");
//            } else if (str.length() == 1) {
//                res = String.format("%10s", "");
//                res = res.replaceAll("\\s", " ");
//            }
//            res = str + res;
//
//            return res;
//        } catch (Exception e) {
//e.printStackTrace();
//        }
//return null;
//    }
public static String formatStr6(String str) {


    int len = strlen(str);

    if (len >= 6) {
        str = str.substring(0, 6);
        return str;
    }

    int padLen = 6 - len;
    for (int i = 0; i < padLen; i++) {
        str += " ";
    }

    return str;


}
    public static String formatStr1(String str) {


        int len = strlen(str);
        //   str = str.replaceAll("\\.", "");

        if (len >= 24) {
            str = str.substring(0,12);
            return str;
        }

        int padLen = 24 - len;
        for (int i = 0; i < padLen; i++) {
            str += " ";
        }

        return str;

    }

    public static String formatRight2(String str) {


        int len = strlen(str);
        //   str = str.replaceAll("\\.", "");

        if (len >= 26) {
//            str = str.substring(0,6);
            return str;
        }

        int padLen = 26 - len;
        for (int i = 0; i < padLen; i++) {
            str= " "+str;
        }

        return str;

    }

}
