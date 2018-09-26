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

    private ArrayList<goodsBean>goodsBeanArrayList;



    //合计
    private int totalGoods;
    //订单金额
    private String orderTotal;

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

    //实付金额
    private String actualPay;

    //优惠金额
    private String discountMoney;

    //支付方式
    String payType;

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

    @Override
    public String toString() {
        return "PrintInfoBean{" +
                "storeName='" + storeName + '\'' +
                ", storeOrderId='" + storeOrderId + '\'' +
                ", orderTime='" + orderTime + '\'' +
                ", goodsBeanArrayList=" + goodsBeanArrayList +
                ", totalGoods=" + totalGoods +
                ", orderTotal='" + orderTotal + '\'' +
                ", actualPay='" + actualPay + '\'' +
                ", discountMoney='" + discountMoney + '\'' +
                ", payType='" + payType + '\'' +
                '}';
    }

    public static void main(String[]a){

        NetWorkCallBack netWorkCallBack = new NetWorkBeanImpl();

       NetWorkCallBack netWorkCallBack1 = (NetWorkCallBack) Proxy.newProxyInstance(netWorkCallBack.getClass()
        .getClassLoader(),netWorkCallBack.getClass().getInterfaces()
        ,new InVocationHandler(netWorkCallBack));


       netWorkCallBack.onSucess("13");


    }
}
