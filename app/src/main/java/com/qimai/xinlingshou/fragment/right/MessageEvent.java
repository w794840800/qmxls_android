package com.qimai.xinlingshou.fragment.right;

import android.os.Bundle;

import com.qimai.xinlingshou.bean.DiscountBean;
import com.qimai.xinlingshou.bean.PrintInfoBean;
import com.qimai.xinlingshou.bean.RechargePrint;
import com.qimai.xinlingshou.bean.goodsBean;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by wanglei on 18-5-20.
 */

public class MessageEvent {

    //删除订单所以信息

    public static final String CANCEL_ORDER_INFO = "allDelete";


    public static final String CALCULATEDISCOUNT = "calculateDiscount";
    public static final String CANCELCOUPONS1 = "cancelCoupons1";
    public static final String CANCELCOUPONS2 = "cancelCoupons2";
    public static final String UPDATEDISCOUNT = "updateDiscount";
    public static final String TOTALMONEY = "total_money";
    public static final String TOTALDISCOUNT = "total_discount" ;
    public static final String SELECT_GOODS = "select_goods";
    public static final String REPEATPRINT = "repeat_print";

    public static final String BLANCE_PAY = "balance_pay";

    public static final String MULTIPULTE_PAY = "multipulte_pay";
    public static final String SCAN_VIP_LOGIN = "scan_vip_pay";

    //支付成功的展示页信息
    public static final String PAY_SUCESS_SHOW = "pay_sucess_show";


    public static final String BALANCEPAYFINISH = "balance_pay_finish";
    public static final String RECHARGE = "recharge";

    //取消收款
    public static final String CANCELCOLLECTION = "cancelCollection";


    //储值卡充值打印
    public static final String RECHARGE_PRINT = "recharge_print";

    //储值卡充值成功
    public static final String RECHARGE_PAY_SUCESS = "recharge_pay_sucess";

    //这一单结束
    public static final String ORDER_FINISH = "order_finish";


    //支付界面可以左右滑动
    public static final String PAY_CAN_SCROLL = "pay_can_scroll";
    //支付界面不能左右滑动
    public static final String PAY_NOT_SCROLL = "pay_not_scroll";


    //扫码枪连接
    public static final String SCAN_CODE_CONNECT = "scan_code_connect";

    //打印机连接
    public static final String PRINT_CONNECT = "print_connect";


    //控制左边收款与全部取消的可点
    public static final String COLLECTION_CANCEL_ENABLE = "collect_cancel_enable";

    private ArrayList<goodsBean>goodsBeanArrayList;
    private goodsBean goodsBean;
    private DiscountBean discountBean;
    private Map<Integer,String>selectGoddsMap;
    private   Map<String, goodsBean> selectedGoodsMap;

    //传递int类型参数
    private int typeInt;
    //传递boolean类型
    private boolean typeBoolean;
    private double totalMoney;
    private double totalMoneys;
    private String stringValues;
    private Bundle bundle;
    public MessageEvent(String type) {
        this.type = type;
    }

    private String clientinfo;
    //传递支付码
    private String authCode;
    //区别支付
    private String isPay;
    //优惠券
    private String youhuiquan;
    //优惠金额
    private String cheap_money;
    //优惠类型
    private String youhuitype;
//现金收款
    private String shifu_pay;
    //找零
    private String zhaoling_pay;

    private String uservip;
    private String min_amount_use;
    private String ThridFragmentdata;
    private  String good_order;
    private   PrintInfoBean printInfoBean;
    private RechargePrint rechargePrint;

    public PrintInfoBean getPrintInfoBean() {
        return printInfoBean;
    }

    public void setPrintInfoBean(PrintInfoBean printInfoBean) {
        this.printInfoBean = printInfoBean;
    }

    public Map<String, com.qimai.xinlingshou.bean.goodsBean> getSelectedGoodsMap() {
        return selectedGoodsMap;
    }

    public void setSelectedGoodsMap(Map<String, com.qimai.xinlingshou.bean.goodsBean> selectedGoodsMap) {
        this.selectedGoodsMap = selectedGoodsMap;
    }

    public String getGood_order() {
        return good_order;
    }

    public void setGood_order(String good_order) {
        this.good_order = good_order;
    }

    public String getThridFragmentdata() {
        return ThridFragmentdata;
    }

    public void setThridFragmentdata(String thridFragmentdata) {
        ThridFragmentdata = thridFragmentdata;
    }

    public String getMin_amount_use() {
        return min_amount_use;
    }

    public void setMin_amount_use(String min_amount_use) {
        this.min_amount_use = min_amount_use;
    }

    public String getYouhuitype() {
        return youhuitype;
    }

    public void setYouhuitype(String youhuitype) {
        this.youhuitype = youhuitype;
    }

    public String getUservip() {
        return uservip;
    }

    public void setUservip(String uservip) {
        this.uservip = uservip;
    }

    public String getShifu_pay() {
        return shifu_pay;
    }

    public void setShifu_pay(String shifu_pay) {
        this.shifu_pay = shifu_pay;
    }

    public String getZhaoling_pay() {
        return zhaoling_pay;
    }

    public void setZhaoling_pay(String zhaoling_pay) {
        this.zhaoling_pay = zhaoling_pay;
    }

    public String getCheap_money() {
        return cheap_money;
    }

    public void setCheap_money(String cheap_money) {
        this.cheap_money = cheap_money;
    }

    public String getYouhuiquan() {
        return youhuiquan;
    }

    public void setYouhuiquan(String youhuiquan) {
        this.youhuiquan = youhuiquan;
    }

    public String getIsPay() {
        return isPay;
    }

    public void setIsPay(String isPay) {
        this.isPay = isPay;
    }

    public String getAuthCode() {
        return authCode;
    }

    public void setAuthCode(String authCode) {
        this.authCode = authCode;
    }

    public boolean isTypeBoolean() {
        return typeBoolean;
    }

    public void setTypeBoolean(boolean typeBoolean) {
        this.typeBoolean = typeBoolean;
    }

    public String getClientinfo() {
        return clientinfo;
    }

    public void setClientinfo(String clientinfo) {
        this.clientinfo = clientinfo;
    }

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ArrayList<goodsBean> getGoodsBeanArrayList() {
        return goodsBeanArrayList;
    }

    public void setGoodsBeanArrayList(ArrayList<goodsBean> goodsBeanArrayList) {
        this.goodsBeanArrayList = goodsBeanArrayList;
    }

    public com.qimai.xinlingshou.bean.goodsBean getGoodsBean() {
        return goodsBean;
    }

    public void setGoodsBean(com.qimai.xinlingshou.bean.goodsBean goodsBean) {
        this.goodsBean = goodsBean;
    }

    public double getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(double totalMoney) {
        this.totalMoney = totalMoney;
    }

    public int getTypeInt() {
        return typeInt;
    }

    public void setTypeInt(int typeInt) {
        this.typeInt = typeInt;
    }

    public DiscountBean getDiscountBean() {
        return discountBean;
    }

    public void setDiscountBean(DiscountBean discountBean) {
        this.discountBean = discountBean;
    }

    public String getStringValues() {
        return stringValues;
    }

    public void setStringValues(String stringValues) {
        this.stringValues = stringValues;
    }

    public Map<Integer, String> getSelectGoddsMap() {
        return selectGoddsMap;
    }

    public void setSelectGoddsMap(Map<Integer, String> selectGoddsMap) {

        this.selectGoddsMap = selectGoddsMap;
    }

    public Bundle getBundle() {

        return bundle;
    }

    public void setBundle(Bundle bundle) {
        this.bundle = bundle;
    }

    public RechargePrint getRechargePrint() {
        return rechargePrint;
    }

    public void setRechargePrint(RechargePrint rechargePrint) {
        this.rechargePrint = rechargePrint;
    }
}
