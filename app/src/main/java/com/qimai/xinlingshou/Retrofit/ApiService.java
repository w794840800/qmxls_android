package com.qimai.xinlingshou.Retrofit;

import com.qimai.xinlingshou.bean.NetWorkBean;
import com.qimai.xinlingshou.bean.RechargeBean;
import com.qimai.xinlingshou.bean.UserIdBean;
import com.qimai.xinlingshou.bean.orderQueryBean;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Field;
import retrofit2.http.FieldMap;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface ApiService {

    @GET("ptmarketing/recharge-card/pay-code")
    Observable<NetWorkBean<UserIdBean>> changeScanCodeToId(@Query("code") String code);

    @GET("ptfw/cashier/user-by-mobile")
    Observable getVipInfo(@Query("mobile")String mobile,@Query("user_id")String userId
    ,@Query("type")int type);


    //获取用户余额
    @GET("ptfw/order/wallet-info")
    Observable<NetWorkBean> getBalance(@Query("user_id")String user_id);

    //余额减扣
    @FormUrlEncoded
    @POST("ptfw/order/wealth-deduct")
    Observable<NetWorkBean> balancePay(@Field("user_id") String user_id,@Field("order_no")String order_no);


    //取消余额支付
    @FormUrlEncoded
    @POST("ptfw/order/cancel")
    Observable<NetWorkBean> cancelBalance(@Field("user_id") String orderId);


    //上传订单信息
    @FormUrlEncoded
    @POST("ptfw/order/receive")
    Observable<NetWorkBean> uploadOrder(@FieldMap HashMap<String,String>hashMap);


    //单余额支付完成同步订单状态
    @FormUrlEncoded
    @POST("ptfw/order/update-pos-order-status")
    Observable<NetWorkBean> asyncBalanceOrderStatus(@Field("order_no")String order_no
    ,@Field("trade_no")String trade_no,@Field("payment_id")String payment_id
                                                    ,@Field("pay_time")String pay_time
                                                    ,@Field("finish_time")String finish_time
    );

    //ptfw/order/cancel
    @FormUrlEncoded
    @POST("ptfw/order/cancel")
    Observable<NetWorkBean> cancelOrder(@Field("order_no")String order_no);

    //ptfw/cashier/query-order
    //查询订单支付状态
    @FormUrlEncoded
    @POST("ptfw/cashier/query-order")
    Observable<NetWorkBean<orderQueryBean>> queryOrderPayStatus(@Field("out_trade_no")String out_trade_no, @Field("pay_type")String pay_type);


    //ptmarketing/recharge-card/list
    @GET("ptmarketing/recharge-card/list")
    Observable<NetWorkBean> getRecharge_card(@Query("page") int page, @Query("page_size")int
            page_size, @Query("keyword")String keyword, @Query("is_cashier")int is_cashier);


    //ptfw/pos-stored/create
    @FormUrlEncoded
    @POST("ptfw/pos-stored/create")
    Observable<NetWorkBean<RechargeBean>> create_recharge_order(@Field("entity")String entity, @Field("user_id")String user_id,@Field("store_id")String store_id);



   /* "order_no": "P5AC42B7B7BA8C5240",
            "user_id": "3906",
            "payment_id": "2",
            "trade_no": "4200000059201804105787974846"*/
    @FormUrlEncoded
    @POST("ptfw/pos-stored/update-order")
    Observable<NetWorkBean> upload_recharge_order(@Field("order_no")String order_no,
                                                  @Field("user_id")String user_id
    ,@Field("payment_id")String payment_id,
                                                  @Field("trade_no")String trade_no
    );

}
