package com.qimai.xinlingshou.bean;

import com.qimai.xinlingshou.callback.NetWorkCallBack;

import java.lang.reflect.Proxy;
import java.util.ArrayList;



/***
 * 因为打印存在多次打印，如果不单独保存，打印一次后，那些数据会被清空
 * */
public class PrintInfoBean {

    private String storeName;
    private String storeOrderId;
    private String orderTime;

    @Override
    public String toString() {
        return "PrintInfoBean{" +
                "storeName='" + storeName + '\'' +
                ", storeOrderId='" + storeOrderId + '\'' +
                ", orderTime='" + orderTime + '\'' +
                ", balancePay='" + balancePay + '\'' +
                ", goodsBeanArrayList=" + goodsBeanArrayList +
                ", actualPay='" + actualPay + '\'' +
                ", actualCrashPay='" + actualCrashPay + '\'' +
                ", returnCharge='" + returnCharge + '\'' +
                ", discountMoney='" + discountMoney + '\'' +
                ", payType='" + payType + '\'' +
                ", totalGoods=" + totalGoods +
                ", orderTotal='" + orderTotal + '\'' +
                ", isMultiplut=" + isMultiplut +
                ", leaveBalance='" + leaveBalance + '\'' +
                ", method=" + method +
                ", userMobile='" + userMobile + '\'' +
                '}';
    }

    private String balancePay;
    private ArrayList<goodsBean>goodsBeanArrayList;

    //实付金额
    private String actualPay;

    //现金支付收钱金额

    private String actualCrashPay;
    //找零
    private String returnCharge;
    //优惠金额
    private String discountMoney;

    //支付方式
    String payType;

    //合计
    private int totalGoods;
    //订单金额
    private String orderTotal;

    //是否是混合支付

    private boolean isMultiplut;


    //余额剩余
    private String leaveBalance;


    // 0 微信 1 支付宝 2 现金支付 3  标记支付 4 余额支付 5 混合支付
    private int method;

    //会员手机号

    private String userMobile;

    //混合支付 除余额支付以外
    private String leaveOrderPay;

    public String getStoreOrderId() {
        return storeOrderId;
    }

    public void setStoreOrderId(String storeOrderId) {
        this.storeOrderId = storeOrderId;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }

    public ArrayList<goodsBean> getGoodsBeanArrayList() {
        return goodsBeanArrayList;
    }

    public void setGoodsBeanArrayList(ArrayList<goodsBean> goodsBeanArrayList) {
        this.goodsBeanArrayList = goodsBeanArrayList;
    }

    public String getOrderTotal() {
        return orderTotal;
    }

    public void setOrderTotal(String orderTotal) {
        this.orderTotal = orderTotal;
    }

    public String getActualPay() {
        return actualPay;
    }

    public void setActualPay(String actualPay) {
        this.actualPay = actualPay;
    }

    public String getDiscountMoney() {
        return discountMoney;
    }

    public void setDiscountMoney(String discountMoney) {
        this.discountMoney = discountMoney;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }



    public int getTotalGoods() {
        return totalGoods;
    }

    public void setTotalGoods(int totalGoods) {
        this.totalGoods = totalGoods;
    }

    public String getStoreName() {
        return storeName;
    }

    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public static void main(String[]a){

        NetWorkCallBack netWorkCallBack = new NetWorkBeanImpl();

       NetWorkCallBack netWorkCallBack1 = (NetWorkCallBack) Proxy.newProxyInstance(netWorkCallBack.getClass()
        .getClassLoader(),netWorkCallBack.getClass().getInterfaces()
        ,new InVocationHandler(netWorkCallBack));


       netWorkCallBack.onSucess("13");


    }

    public String getBalancePay() {
        return balancePay;
    }

    public void setBalancePay(String balancePay) {
        this.balancePay = balancePay;
    }

    public boolean isMultiplut() {
        return isMultiplut;
    }

    public void setMultiplut(boolean multiplut) {
        isMultiplut = multiplut;
    }

    public String getLeaveBalance() {
        return leaveBalance;
    }

    public void setLeaveBalance(String leaveBalance) {
        this.leaveBalance = leaveBalance;
    }

    public String getUserMobile() {
        return userMobile;
    }

    public void setUserMobile(String userMobile) {
        this.userMobile = userMobile;
    }

    public int getMethod() {
        return method;
    }

    public void setMethod(int method) {
        this.method = method;
    }

    public String getReturnCharge() {
        return returnCharge;
    }

    public void setReturnCharge(String returnCharge) {
        this.returnCharge = returnCharge;
    }

    public String getActualCrashPay() {
        return actualCrashPay;
    }

    public void setActualCrashPay(String actualCrashPay) {
        this.actualCrashPay = actualCrashPay;
    }

    public String getLeaveOrderPay() {
        return leaveOrderPay;
    }

    public void setLeaveOrderPay(String leaveOrderPay) {
        this.leaveOrderPay = leaveOrderPay;
    }
}
