package com.qimai.xinlingshou.bean;

import android.os.Parcel;
import android.os.Parcelable;

public class RechargeBean {

    /**
     * order : {"order_no":"A5BAEEA9D1B8A99031","store_id":"10627","user_id":"1124","status":"1","created_at":"1538189981","updated_at":"1538189981","order_type":"3","source":"3","total_amount":"0.01","amount":"0.01","id":"3119"}
     * token : eyJvcmRlcl9ubyI6IkE1QkFFRUE5RDFCOEE5OTAzMSIsInN0b3JlX2lkIjoxMDYyNywidG90YWwiOjAuMDEsIm9yZGVyX3R5cGUiOiJtaW5pLW9yZGVyIiwib3Blbl9pZCI6Im8zWmJENUc4N3ZiYm1ienFiMi1ZWDNBaWVrTUUiLCJzdWJqZWN0IjoiXHU5NmY2XHU1NTJlXHU1NTQ2XHU1NGMxIiwic2hvcF9pZCI6bnVsbH0
     */

    private RechargeOrderBean order;
    private String token;

    public RechargeOrderBean getOrder() {
        return order;
    }

    public void setOrder(RechargeOrderBean order) {
        this.order = order;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }


    @Override
    public String toString() {
        return "RechargeBean{" +
                "order=" + order +
                ", token='" + token + '\'' +
                '}';
    }
}
