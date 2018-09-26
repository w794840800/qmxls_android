package com.qimai.xinlingshou.bean;

import java.io.Serializable;

/**
 *
 * Created by wanglei on 18-5-22.
 */

public class CouponsEntry implements Serializable {
    String id;
    String store_id;
    String title;
    private String coupon_id;
    @Override
    public String toString() {
        return "CouponsEntry{" +
                "id='" + id + '\'' +
                ", store_id='" + store_id + '\'' +
                ", title='" + title + '\'' +
                ", amount='" + amount + '\'' +
                ", coupon_type='" + coupon_type + '\'' +
                ", min_amount_use='" + min_amount_use + '\'' +
                ", type_id='" + type_id + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }

    String amount;
    String coupon_type;
    String min_amount_use;
    String type_id;
    boolean isSelected;


    public CouponsEntry(String  id,
            String store_id,
            String title,
            String amount,
            String coupon_type,
            String min_amount_use,
            String type_id
            ,String coupon_id
                         )
                         {

        this.coupon_id = coupon_id;
        this.id = id;
        this.store_id = store_id;
        this.amount = amount;
        this.coupon_type = coupon_type;
        this.min_amount_use = min_amount_use;
        this.type_id = type_id;
        this.title = title;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCoupon_type() {
        return coupon_type;
    }

    public void setCoupon_type(String coupon_type) {
        this.coupon_type = coupon_type;
    }

    public String getMin_amount_use() {
        return min_amount_use;
    }

    public void setMin_amount_use(String min_amount_use) {
        this.min_amount_use = min_amount_use;
    }

    public String getType_id() {
        return type_id;
    }

    public void setType_id(String type_id) {
        this.type_id = type_id;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    public String getCoupon_id() {
        return coupon_id;
    }

    public void setCoupon_id(String coupon_id) {
        this.coupon_id = coupon_id;
    }

    //    @Override
//    public String toString() {
//        return "CouponsEntry{" +
//                "id=" + goodsPic +
//                ", goodsName='" + goodsName + '\'' +
//                ", price='" + price + '\'' +
//                ", goodsimg='" + goodsimg + '\'' +
//                ", goodsId='" + goodsId + '\'' +
//                ", number=" + number +
//                '}';
//    }

   }
