package com.qimai.xinlingshou.bean;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;

import org.litepal.LitePal;
import org.litepal.crud.LitePalSupport;

public class RechargeOrderBean extends LitePalSupport implements Parcelable{


    /**
     * order_no : A5BAEEA9D1B8A99031
     * store_id : 10627
     * user_id : 1124
     * status : 1
     * created_at : 1538189981
     * updated_at : 1538189981
     * order_type : 3
     * source : 3
     * total_amount : 0.01
     * amount : 0.01
     * id : 3119
     */

    private String order_no;
    private String store_id;
    private String user_id;
    private String status;
    private String created_at;
    private String updated_at;
    private String order_type;
    private String source;
    private String total_amount;
    private String amount;
    //private String id;
    //轮训是否结束 0 未结束 1结束
    @Expose
    private String query_order;
    //服务器返回来的第三方交易号
    private String trade_no;
    //支付时间
    private String pay_at;
    //完成时间
    private String finish_time;

    //是否同步
    private String isauto;
    //是否支付
    private String ispay;

    //微信 010 支付宝 020
    private String payType;

    //支付方式
    private String payment_id;


    private ValueCardBean valueCardBean;

    public String getOrder_no() {
        return order_no;
    }

    public void setOrder_no(String order_no) {
        this.order_no = order_no;
    }

    public String getStore_id() {
        return store_id;
    }

    public void setStore_id(String store_id) {
        this.store_id = store_id;
    }

    public String getUser_id() {
        return user_id;
    }

    public void setUser_id(String user_id) {
        this.user_id = user_id;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }

    public String getOrder_type() {
        return order_type;
    }

    public void setOrder_type(String order_type) {
        this.order_type = order_type;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTotal_amount() {
        return total_amount;
    }

    public void setTotal_amount(String total_amount) {
        this.total_amount = total_amount;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

//    public String getId() {
//        return id;
//    }
//
//    public void setId(String id) {
//        this.id = id;
//    }


    public RechargeOrderBean() {
    }

    public String getTrade_no() {
        return trade_no;
    }

    public void setTrade_no(String trade_no) {
        this.trade_no = trade_no;
    }

    public String getPay_at() {
        return pay_at;
    }

    public void setPay_at(String pay_at) {
        this.pay_at = pay_at;
    }

    public String getFinish_time() {
        return finish_time;
    }

    public void setFinish_time(String finish_time) {
        this.finish_time = finish_time;
    }

    public String getIsauto() {
        return isauto;
    }

    public void setIsauto(String isauto) {
        this.isauto = isauto;
    }

    public String getIspay() {
        return ispay;
    }

    public void setIspay(String ispay) {
        this.ispay = ispay;
    }

    public String getPayType() {
        return payType;
    }

    public void setPayType(String payType) {
        this.payType = payType;
    }

    public String getPayment_id() {
        return payment_id;
    }

    public void setPayment_id(String payment_id) {
        this.payment_id = payment_id;
    }

    public String getQuery_order() {
        return query_order;
    }

    public void setQuery_order(String query_order) {
        this.query_order = query_order;
    }

    public ValueCardBean getValueCardBean() {
        return valueCardBean;
    }

    public void setValueCardBean(ValueCardBean valueCardBean) {
        this.valueCardBean = valueCardBean;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.order_no);
        dest.writeString(this.store_id);
        dest.writeString(this.user_id);
        dest.writeString(this.status);
        dest.writeString(this.created_at);
        dest.writeString(this.updated_at);
        dest.writeString(this.order_type);
        dest.writeString(this.source);
        dest.writeString(this.total_amount);
        dest.writeString(this.amount);
        dest.writeString(this.query_order);
        dest.writeString(this.trade_no);
        dest.writeString(this.pay_at);
        dest.writeString(this.finish_time);
        dest.writeString(this.isauto);
        dest.writeString(this.ispay);
        dest.writeString(this.payType);
        dest.writeString(this.payment_id);
        dest.writeParcelable(this.valueCardBean, flags);
    }

    protected RechargeOrderBean(Parcel in) {
        this.order_no = in.readString();
        this.store_id = in.readString();
        this.user_id = in.readString();
        this.status = in.readString();
        this.created_at = in.readString();
        this.updated_at = in.readString();
        this.order_type = in.readString();
        this.source = in.readString();
        this.total_amount = in.readString();
        this.amount = in.readString();
        this.query_order = in.readString();
        this.trade_no = in.readString();
        this.pay_at = in.readString();
        this.finish_time = in.readString();
        this.isauto = in.readString();
        this.ispay = in.readString();
        this.payType = in.readString();
        this.payment_id = in.readString();
        this.valueCardBean = in.readParcelable(ValueCardBean.class.getClassLoader());
    }

    public static final Creator<RechargeOrderBean> CREATOR = new Creator<RechargeOrderBean>() {
        @Override
        public RechargeOrderBean createFromParcel(Parcel source) {
            return new RechargeOrderBean(source);
        }

        @Override
        public RechargeOrderBean[] newArray(int size) {
            return new RechargeOrderBean[size];
        }
    };

    @Override
    public String toString() {
        return "RechargeOrderBean{" +
                "order_no='" + order_no + '\'' +
                ", store_id='" + store_id + '\'' +
                ", user_id='" + user_id + '\'' +
                ", status='" + status + '\'' +
                ", created_at='" + created_at + '\'' +
                ", updated_at='" + updated_at + '\'' +
                ", order_type='" + order_type + '\'' +
                ", source='" + source + '\'' +
                ", total_amount='" + total_amount + '\'' +
                ", amount='" + amount + '\'' +
                //", id='" + id + '\'' +
                ", query_order='" + query_order + '\'' +
                ", trade_no='" + trade_no + '\'' +
                ", pay_at='" + pay_at + '\'' +
                ", finish_time='" + finish_time + '\'' +
                ", isauto='" + isauto + '\'' +
                ", ispay='" + ispay + '\'' +
                ", payType='" + payType + '\'' +
                ", payment_id='" + payment_id + '\'' +
                ", valueCardBean=" + valueCardBean +
                '}';
    }
}
