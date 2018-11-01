package com.qimai.xinlingshou.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class RechargePrint implements Parcelable {

    private String  orderNo;

    private String time;

    //订单类型
    private String type;

    //会员手机号

    private String mobile;
    //储值卡名称
    private String rechargeCardName;

    //储值卡面值
    private String rechargeMoney;

    //储值卡赠送
    private String rechargeReward;

    //储值+赠送
    private String balanceTotalCanGet;


    private String payType;

    //账单金额
    private String totalAmount;

    private String storeName;

    //会员卡号
    private String user_no;

    //卡内余额
    private String balance_total;

    public String getOrderNo() {
        return orderNo;
    }

    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getRechargeCardName() {
        return rechargeCardName;
    }

    public void setRechargeCardName(String rechargeCardName) {
        this.rechargeCardName = rechargeCardName;
    }

    public String getRechargeMoney() {
        return rechargeMoney;
    }

    public void setRechargeMoney(String rechargeMoney) {
        this.rechargeMoney = rechargeMoney;
    }

    public String getRechargeReward() {
        return rechargeReward;
    }

    public void setRechargeReward(String rechargeReward) {
        this.rechargeReward = rechargeReward;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(String totalAmount) {
        this.totalAmount = totalAmount;
    }



    public void setStoreName(String storeName) {
        this.storeName = storeName;
    }

    public String getStoreName() {
        return storeName;
    }

    public String getBalanceTotalCanGet() {
        return balanceTotalCanGet;
    }

    public void setBalanceTotalCanGet(String balanceTotalCanGet) {
        this.balanceTotalCanGet = balanceTotalCanGet;
    }

    public String getUser_no() {
        return user_no;
    }

    public void setUser_no(String user_no) {
        this.user_no = user_no;
    }

    public String getBalance_total() {
        return balance_total;
    }

    public void setBalance_total(String balance_total) {
        this.balance_total = balance_total;
    }

    @Override
    public String toString() {
        return "RechargePrint{" +
                "orderNo='" + orderNo + '\'' +
                ", time='" + time + '\'' +
                ", type='" + type + '\'' +
                ", rechargeCardName='" + rechargeCardName + '\'' +
                ", rechargeMoney='" + rechargeMoney + '\'' +
                ", rechargeReward='" + rechargeReward + '\'' +
                ", balanceTotalCanGet='" + balanceTotalCanGet + '\'' +
                ", payType='" + payType + '\'' +
                ", totalAmount='" + totalAmount + '\'' +
                ", storeName='" + storeName + '\'' +
                ", user_no='" + user_no + '\'' +
                ", balance_total='" + balance_total + '\'' +
                '}';
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
        dest.writeString(this.orderNo);
        dest.writeString(this.time);
        dest.writeString(this.type);
        dest.writeString(this.mobile);
        dest.writeString(this.rechargeCardName);
        dest.writeString(this.rechargeMoney);
        dest.writeString(this.rechargeReward);
        dest.writeString(this.balanceTotalCanGet);
        dest.writeString(this.payType);
        dest.writeString(this.totalAmount);
        dest.writeString(this.storeName);
        dest.writeString(this.user_no);
        dest.writeString(this.balance_total);
    }

    public RechargePrint() {
    }

    protected RechargePrint(Parcel in) {
        this.orderNo = in.readString();
        this.time = in.readString();
        this.type = in.readString();
        this.mobile = in.readString();
        this.rechargeCardName = in.readString();
        this.rechargeMoney = in.readString();
        this.rechargeReward = in.readString();
        this.balanceTotalCanGet = in.readString();
        this.payType = in.readString();
        this.totalAmount = in.readString();
        this.storeName = in.readString();
        this.user_no = in.readString();
        this.balance_total = in.readString();
    }

    public static final Parcelable.Creator<RechargePrint> CREATOR = new Parcelable.Creator<RechargePrint>() {
        @Override
        public RechargePrint createFromParcel(Parcel source) {
            return new RechargePrint(source);
        }

        @Override
        public RechargePrint[] newArray(int size) {
            return new RechargePrint[size];
        }
    };
}
