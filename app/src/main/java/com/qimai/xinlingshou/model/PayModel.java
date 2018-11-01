package com.qimai.xinlingshou.model;

import android.text.TextUtils;
import android.util.Log;

import com.qimai.xinlingshou.App;
import com.qimai.xinlingshou.Retrofit.RetrofitUtils;
import com.qimai.xinlingshou.bean.RechargeBean;
import com.qimai.xinlingshou.bean.ValueCardBean;
import com.qimai.xinlingshou.bean.orderQueryBean;
import com.qimai.xinlingshou.bean.ordersBean;
import com.qimai.xinlingshou.callback.NetWorkCallBack;
import com.qimai.xinlingshou.callback.NetWorkCallBack2;
import com.qimai.xinlingshou.callback.RequestObserver;
import com.qimai.xinlingshou.utils.Xutils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import io.reactivex.disposables.Disposable;
import retrofit2.http.Field;

public class PayModel extends BaseModel{

    private static final String TAG = "PayModel";
    private static PayModel payModel = new PayModel();

    public static PayModel getInstance(){

        return payModel;

    }
    /**
     * 余额支付
     * */
    public void blancePay(String user_id,String order_no,NetWorkCallBack netWorkCallBack){

        changeThread(RetrofitUtils.getRetrofitUtils().getApiService()
                        .balancePay(user_id, order_no)
                , new RequestObserver() {
                    @Override
                    protected void onStart(Disposable d) {

                        addDispose(d);
                    }

                    @Override
                    protected void onSucess(Object data) {

                        netWorkCallBack.onSucess(data.toString());

                        Log.d(TAG, "onSucess: yuezhifu");
                    }

                    @Override
                    protected void onFailed(String message) {

                        netWorkCallBack.onFailed(message);
                    }

                });


    }


    /**
     * 余额查询
     * */
    public void queryBlance(String userId,NetWorkCallBack netWorkCallBack){

        changeThread(RetrofitUtils.getRetrofitUtils().getApiService()
                .getBalance(userId), new RequestObserver() {
            @Override
            protected void onStart(Disposable d) {

                addDispose(d);
            }

            @Override
            protected void onSucess(Object data) {

                String dataContent = data.toString();

                if (!TextUtils.isEmpty(dataContent)){

                    try {
                        JSONObject jsonObject = new JSONObject(dataContent);




                        netWorkCallBack.onSucess(jsonObject.getString("wealth"));


                    } catch (JSONException e) {
                        e.printStackTrace();
                    }


                }
                Log.d(TAG, "onSucess: data= "+data.toString());


            }

            @Override
            protected void onFailed(String message) {

                netWorkCallBack.onFailed(message);
            }


        });

    }


    /**
     * 上传订单信息
     * */

    public void uploadOrderInfo(ordersBean mOrderbean, NetWorkCallBack netWorkCallBack){

        Log.d(TAG, "uploadOrderInfo: mOrderbean= "+mOrderbean.toString());
        Map<String,String> map = new HashMap<>();


        putMapValues(map,"use_wallet",mOrderbean.getUse_wallet()+"");
        putMapValues(map,"user_id",mOrderbean.getUserid());
        putMapValues(map,"total_amount",mOrderbean.getTotal_amount());
        putMapValues(map,"amount",mOrderbean.getAmount());
        //Log.d(TAG, "uploadDateToServe: amount= "+mOrderbean.getAmount());
        putMapValues(map,"user_remarks","abcdef");
        putMapValues(map,"seller_remarks",mOrderbean.getSeller_remarks());
        putMapValues(map,"trade_no",mOrderbean.getService_orderId());
        putMapValues(map,"payment_id",mOrderbean.getPayment_id());
        putMapValues(map,"minus_amount",mOrderbean.getMinus_amount());
        putMapValues(map,"card_minus",mOrderbean.getCard_minus());
        putMapValues(map,"coupon_minus",mOrderbean.getCoupon_minus());
        putMapValues(map,"card_id",mOrderbean.getCard_id());
        putMapValues(map,"coupon_id",mOrderbean.getCoupon_id());
        putMapValues(map,"items",mOrderbean.getOrderInfo());
        putMapValues(map,"status",mOrderbean.getStatus()+"");
        putMapValues(map,"wallet_amount",mOrderbean.getWallet_amount());
        putMapValues(map,"order_no",mOrderbean.getOrder());
        putMapValues(map,"create_time",mOrderbean.getCreate_time());
        putMapValues(map,"pay_time",mOrderbean.getPay_time());
        putMapValues(map,"finish_time",mOrderbean.getFinish_time());


        changeThread(RetrofitUtils.getRetrofitUtils().getApiService()
                        .uploadOrder((HashMap<String, String>) map)
                , new RequestObserver() {
                    @Override
                    protected void onStart(Disposable d) {

                        Log.d(TAG, "onStart: ");
                        addDispose(d);
                    }

                    @Override
                    protected void onSucess(Object data) {

                        Log.d(TAG, "onSucess: data= "+data.toString());

                        netWorkCallBack.onSucess(data);
                    }

                    @Override
                    protected void onFailed(String message) {

                        Log.d(TAG, "onFailed: messag = "+message);

                        netWorkCallBack.onFailed(message);
                    }

                });





    }


    /**
     * 同步余额支付订单信息
     * **/
    public void asyncBalanceOrder(String order_no,String trade_no,String payment_id,

                                  String pay_time,String finish_time,
                                  NetWorkCallBack netWorkCallBack){

        Log.d(TAG, "asyncBalanceOrder: order_no= "+order_no+" trade_no= "+trade_no

        +" payment_id= "+payment_id);
        changeThread(RetrofitUtils.getRetrofitUtils().getApiService().asyncBalanceOrderStatus(order_no, trade_no
                , payment_id,pay_time,finish_time), new RequestObserver() {
            @Override
            protected void onStart(Disposable d) {

            }

            @Override
            protected void onSucess(Object data) {

                netWorkCallBack.onSucess(data);
            }

            @Override
            protected void onFailed(String message) {

                netWorkCallBack.onFailed(message);
            }


        });


    }


    public void cancelOrder(String id,NetWorkCallBack netWorkCallBack){

        changeThread(RetrofitUtils.getRetrofitUtils().getApiService()
                .cancelOrder(id), new RequestObserver() {
            @Override
            protected void onStart(Disposable d) {

                addDispose(d);
            }

            @Override
            protected void onSucess(Object data) {

                netWorkCallBack.onSucess(data);

            }

            @Override
            protected void onFailed(String message) {

                netWorkCallBack.onFailed(message);
            }

        });



    }
    /**
     * 添加参数到map中，为空的值不添加
     * @param params 参数名
     * @param values 参数值
     *
     * */

    private void putMapValues(Map<String,String> map, String params, String values) {

        if (TextUtils.isEmpty(values)){
            return;
        }else{

            map.put(params,values);
        }
    }



    /**
     * 查询订单支付状态
     * */
    public void queryOrderPayStatus(String out_trade_no,String pay_type,NetWorkCallBack netWorkCallBack){

        changeThread(RetrofitUtils.getRetrofitUtils().getApiService()
                .queryOrderPayStatus(out_trade_no, pay_type), new RequestObserver<orderQueryBean>() {
            @Override
            protected void onStart(Disposable d) {

            }

            @Override
            protected void onSucess(orderQueryBean data) {

                netWorkCallBack.onSucess(data);
            }

            @Override
            protected void onFailed(String message) {

                netWorkCallBack.onFailed(message);

            }

        });


    }


    //获取储值卡列表

    public void getValueCard(int page,int page_size,String keyword,int is_cashier,NetWorkCallBack2 netWorkCallBack) {


        ArrayList<ValueCardBean>valueCardBeanArrayList = new ArrayList<>();
        HashMap<String, String> params = new HashMap<>();

        params.put("page", page + "");
        params.put("page_size", page_size + "");
        params.put("keyword", "");
        params.put("is_cashier", is_cashier + "");

        Xutils.getInstance().get(App.API_URL + "ptmarketing/recharge-card/list", params
                , new Xutils.XCallBackWithError() {
                    @Override
                    public void onError(Throwable throwable) {

                        netWorkCallBack.onFailed(throwable.getMessage());
                        Log.d(TAG, "onError: error= " + throwable.getMessage());
                    }

                    @Override
                    public void onResponse(String result) {

                        Log.d(TAG, "onResponse: result= " + result);


                        try {
                            JSONObject jsonObject = new JSONObject(result);

                            boolean status = jsonObject.getBoolean("status");


                            if (status){


                                    JSONObject jsonObjectData = jsonObject.getJSONObject("data");


                                    JSONObject jsonObjectList = jsonObjectData.getJSONObject("list");

                                JSONArray jsonArrayData = jsonObjectList.getJSONArray("data");

                                ValueCardBean valueCardBean;

                                for (int i = 0; i <jsonArrayData.length(); i++) {

                                    valueCardBean = new ValueCardBean();
                                    JSONObject jsonItem = jsonArrayData.getJSONObject(i);

                                    valueCardBean.setSell_price(jsonItem.getString("sell_price"));
                                    valueCardBean.setStore_id(jsonItem.getString("store_id"));
                                    valueCardBean.setRecharge_name(jsonItem.getString("name"));

                                    String id = jsonItem.getString("id");

                                    valueCardBean.setRecharge_id(id);
                                    //gifts数组

                                    JSONArray jsonArrayGifts = jsonItem.getJSONArray("gifts");


                                    for (int j = 0; j < jsonArrayGifts.length(); j++) {

                                        JSONObject jsonObjectGiftsItem = jsonArrayGifts.getJSONObject(j);

                                        if (jsonObjectGiftsItem.toString().contains("Balance")){

                                             /*String id = jsonObjectGiftsItem.getString("recharge_card_id");

                                             valueCardBean.setRecharge_id(id);*/
                                            JSONObject jsonObjectExt = jsonObjectGiftsItem.getJSONObject("ext");


                                            String entity = jsonObjectExt.optString("entity");

                                            if (TextUtils.isEmpty(entity)){

                                                entity = "0";

                                            }                                            valueCardBean.setEntity(entity);





                                        }



                                        break;

                                    }


                                    Log.d(TAG, "onResponse: valueCardBean= "+valueCardBean.toString());
                                    valueCardBeanArrayList.add(valueCardBean);





                                }


                                Log.d(TAG, "onResponse: valueSize size= "+valueCardBeanArrayList.size()+" content= ");






                            }


                            netWorkCallBack.onSucess(valueCardBeanArrayList);

                        } catch (JSONException e) {

                            e.printStackTrace();

                            Log.d(TAG, "onResponse: e= "+e.getMessage());
                        }



                    }
                });


    }

    public void create_recharge_order(String entity,String user_id,String store_id,NetWorkCallBack2 netWorkCallBack2){

        Log.d(TAG, "create_recharge_order: entity= "+entity+" user_id= "+user_id);

        changeThread(RetrofitUtils.getRetrofitUtils().getApiService()
                .create_recharge_order(entity, user_id,store_id), new RequestObserver<RechargeBean>() {
            @Override
            protected void onStart(Disposable d) {

                netWorkCallBack2.onStart();
            }

            @Override
            protected void onSucess(RechargeBean data) {

                netWorkCallBack2.onSucess(data);

            }

            @Override
            protected void onFailed(String message) {

                netWorkCallBack2.onFailed(message);
            }


        });

    }

    public void uploadRechargeOrder(String order_no,
                                    String user_id
            ,String payment_id,String trade_no,NetWorkCallBack2 netWorkCallBack2 ){


        changeThread(RetrofitUtils.getRetrofitUtils().getApiService()
                .upload_recharge_order(order_no, user_id, payment_id, trade_no), new RequestObserver() {
            @Override
            protected void onStart(Disposable d) {

            }

            @Override
            protected void onSucess(Object data) {

                netWorkCallBack2.onSucess(data);

            }

            @Override
            protected void onFailed(String message) {


                netWorkCallBack2.onFailed(message);
            }


        });

    }

}

