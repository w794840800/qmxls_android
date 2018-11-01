package com.qimai.xinlingshou.bean;


import android.os.Parcel;
import android.os.Parcelable;
import android.text.TextUtils;

import java.util.List;

/**
 *储值卡
 * */
public class ValueCardBean implements Parcelable {


    private String sell_price;
    //赠送金额
    private String entity;

    //id
    private String recharge_id;

    //userId
    private String userId;

    private String store_id;
    private String recharge_name;

    private String mobile;


    public String getSell_price() {
        return sell_price;
    }

    public void setSell_price(String sell_price) {
        this.sell_price = sell_price;
    }

    public String getEntity() {
        if (TextUtils.isEmpty(entity)){

            entity = "0.00";
        }
        return entity;
    }

    public void setEntity(String entity) {
        this.entity = entity;
    }

    public String getRecharge_id() {
        return recharge_id;
    }

    public void setRecharge_id(String recharge_id) {
        this.recharge_id = recharge_id;
    }


    public ValueCardBean() {
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getRecharge_name() {
        return recharge_name;
    }

    public void setRecharge_name(String recharge_name) {
        this.recharge_name = recharge_name;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.sell_price);
        dest.writeString(this.entity);
        dest.writeString(this.recharge_id);
        dest.writeString(this.userId);
        dest.writeString(this.store_id);
        dest.writeString(this.recharge_name);
        dest.writeString(this.mobile);
    }

    protected ValueCardBean(Parcel in) {
        this.sell_price = in.readString();
        this.entity = in.readString();
        this.recharge_id = in.readString();
        this.userId = in.readString();
        this.store_id = in.readString();
        this.recharge_name = in.readString();
        this.mobile = in.readString();
    }

    public static final Creator<ValueCardBean> CREATOR = new Creator<ValueCardBean>() {
        @Override
        public ValueCardBean createFromParcel(Parcel source) {
            return new ValueCardBean(source);
        }

        @Override
        public ValueCardBean[] newArray(int size) {
            return new ValueCardBean[size];
        }
    };

    @Override
    public String toString() {
        return "ValueCardBean{" +
                "sell_price='" + sell_price + '\'' +
                ", entity='" + entity + '\'' +
                ", recharge_id='" + recharge_id + '\'' +
                ", userId='" + userId + '\'' +
                ", store_id='" + store_id + '\'' +
                ", recharge_name='" + recharge_name + '\'' +
                ", mobile='" + mobile + '\'' +
                '}';
    }
}
