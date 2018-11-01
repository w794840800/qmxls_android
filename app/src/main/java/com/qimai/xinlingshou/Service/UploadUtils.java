package com.qimai.xinlingshou.Service;

import android.text.TextUtils;
import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.qimai.xinlingshou.App;
import com.qimai.xinlingshou.Retrofit.ApiService;
import com.qimai.xinlingshou.Retrofit.RetrofitUtils;
import com.qimai.xinlingshou.bean.RechargeOrderBean;
import com.qimai.xinlingshou.bean.orderQueryBean;
import com.qimai.xinlingshou.bean.ordersBean;
import com.qimai.xinlingshou.callback.NetWorkCallBack;
import com.qimai.xinlingshou.callback.NetWorkCallBack2;
import com.qimai.xinlingshou.callback.OnTskFinish;
import com.qimai.xinlingshou.model.PayModel;
import com.qimai.xinlingshou.utils.APPUtil;
import com.qimai.xinlingshou.utils.Xutils;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class UploadUtils {


    PayModel payModel;
    private static final String TAG = "UploadUtils";


    /***
     * 更新旧的存在轮训订单
     * */

    public static void uploadQueryOrder(){

        ArrayList<ordersBean> ordersBeanArrayList = (ArrayList<ordersBean>)
                LitePal.where("query_order=0 and isauto=0 and ispay=0")
                        .find(ordersBean.class);
        Log.d(TAG, "updateOldOrder: ordersBeanArrayList size= "+ordersBeanArrayList.size());

        for (ordersBean o :ordersBeanArrayList) {

            Log.d(TAG, "uploadQueryOrder: o= "+o.toString());


            if (!TextUtils.isEmpty(o.getTrade_no())&&!TextUtils.isEmpty(o.getPayType())){

                new PayModel().queryOrderPayStatus(o.getTrade_no(), o.getPayType(), new NetWorkCallBack<orderQueryBean>() {

                    @Override
                    public void onSucess(orderQueryBean data) {


                        Log.d(TAG, "onSucess: data= "+data.toString());


                        if (data.getResult_code().equals("01")){

                            o.setIspay("1");
                            o.setQuery_order("1");
                            o.save();
                            uploadOldOrder();

                        }

                    }

                    /* @Override
                                                            public void onSucess(String data) {

                                                                Log.d(TAG, "onSucess: data= "+data);


                                                                try {


                                                                    //JsonObject jsonObject = new JsonObject()


                                                                    //JSONArray jsonArray = new JSONArray(data);
                                                                   // String result_code = jsonObject.getString("result_code");

                                                                    //订单状态会支付状态，更新数据库
                                                                   *//* if (!TextUtils.isEmpty(result_code)&&result_code=="01"){*//*
*//*
*//*


                            }

                        } catch (JSONException e) {
                            Log.d(TAG, "onSucess: e= "+e.getMessage());
                            e.printStackTrace();
                        }
                    }
*/
                    @Override
                    public void onFailed(String msg) {

                       // uploadOldOrder();
                        Log.d(TAG, "onFailed: msg= "+msg);
                    }
                });
            }





        }

    }
    //同步支付完成后未支付的订单


    public static void uploadOldOrder() {


       // uploadQueryOrder();


        ArrayList<ordersBean> ordersBeanArrayList = (ArrayList<ordersBean>)
                LitePal.where("isauto=0 and ispay=1").find(ordersBean.class);

        Log.d(TAG, "updateOldOrder: ordersBeanArrayList size= " + ordersBeanArrayList.size());

        if (ordersBeanArrayList!= null && ordersBeanArrayList.size() > 0) {

            for (ordersBean o :
                    ordersBeanArrayList) {

                    uploadDateToServe(o);

                Log.d(TAG, "updateOldOrder: 11111");
            }

        }
    }

    private static void uploadDateToServe(final ordersBean mOrderbean) {
        //App.store.put("BeforeUpload", mOrderbean.toString());
        //App.store.commit();

        Log.d(TAG, "uploadDateToServe: 2222222");

        Log.d(TAG, "uploadDateToServe: mOrderbean= " + mOrderbean.toString());
        Map<String, String> map = new HashMap<>();
        map.put("user_id", mOrderbean.getUserid());
        map.put("total_amount", mOrderbean.getTotal_amount());
        map.put("amount", mOrderbean.getAmount());
        Log.d(TAG, "uploadDateToServe: amount= " + mOrderbean.getAmount());
        map.put("user_remarks", "abcdef");
        map.put("seller_remarks", mOrderbean.getSeller_remarks());
        map.put("trade_no", mOrderbean.getService_orderId());
        map.put("payment_id", mOrderbean.getPayment_id());
        map.put("minus_amount", mOrderbean.getMinus_amount());
        map.put("card_minus", mOrderbean.getCard_minus());
        map.put("coupon_minus", mOrderbean.getCoupon_minus());
        map.put("card_id", mOrderbean.getCard_id());
        map.put("coupon_id", mOrderbean.getCoupon_id());
        map.put("items", mOrderbean.getOrderInfo());
        map.put("status", mOrderbean.getStatus() + "");
        map.put("wallet_amount", mOrderbean.getWallet_amount());
        map.put("order_no", mOrderbean.getOrder());
        map.put("create_time",mOrderbean.getCreate_time());
        map.put("pay_time",mOrderbean.getPay_time());
        map.put("finish_time",mOrderbean.getFinish_time());


        Xutils.getInstance().post(App.API_URL +
                App.API_RECEIVE, map, new Xutils.XCallBack() {
            @Override
            public void onResponse(String result) {

                Log.d(TAG, "onResponse: result= " + result);
                //  App.store.put("uploadresp", result);
                //App.store.commit();

                try {
                    JSONObject jsonObject = new JSONObject(result);
                    String status = jsonObject.getString("status");

                    if (status.equals("true")) {

                        mOrderbean.setIsauto("1");

                        JSONObject datas = jsonObject.getJSONObject("data");
                        JSONObject order = datas.getJSONObject("order");


                        String order_no = order.getString("order_no");

                        Log.d(TAG, "onResponse: order= " + order_no);
                        if (!TextUtils.isEmpty(order_no)) {

                            mOrderbean.setServer_order_no(order_no);
                        }

                        mOrderbean.save();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }



            }
        });




    }


    public static void uploadRechargeOrderStatus(){

        uploadRechargeOrderStatus("");

    }

    public static void uploadRechargeOrderStatus(String userId){

        uploadRechargeOrderStatus(userId,null);
    }


    /***
     *
     * 更新指定用户的未处理的储值卡充值信息
     *
     */


    public static void uploadRechargeOrderStatus(String userId,OnTskFinish onTskFinish) {

        PayModel payModel = null;

        ArrayList<RechargeOrderBean> ordersBeanArrayList = null;

        Log.d(TAG, "uploadRechargeOrderStatus: userId= "+userId);
        if (!TextUtils.isEmpty(userId)){

            ordersBeanArrayList = (ArrayList<RechargeOrderBean>)
                    LitePal.where("query_order= 0 and isauto=0 and ispay=0 and user_id= "+userId).find(RechargeOrderBean.class);

        }else{

            ordersBeanArrayList =  (ArrayList<RechargeOrderBean>)
                    LitePal.where("query_order= 0 and isauto=0 and ispay=0")
                            .find(RechargeOrderBean.class);

        }

        Log.d(TAG, "uploadRechargeOrder: ordersBeanArrayList= "
                +ordersBeanArrayList.size());

        for (int i = 0; i < ordersBeanArrayList.size(); i++) {

            if (payModel==null){

                payModel = new PayModel();
            }

            RechargeOrderBean rechargeOrderBean = ordersBeanArrayList.get(i);

            //查询订单支付状态

            payModel.queryOrderPayStatus(rechargeOrderBean.getTrade_no(), rechargeOrderBean
                    .getPayType(),new NetWorkCallBack<orderQueryBean>() {
                @Override
                public void onSucess(orderQueryBean data) {
                    if (data.getResult_code().equals("01")){
                        rechargeOrderBean.setIspay("1");
                        rechargeOrderBean.setQuery_order("1");
                        //rechargeOrderBean.setQuery_order("1");
                        rechargeOrderBean.save();
                        new PayModel().uploadRechargeOrder(rechargeOrderBean.getOrder_no(), rechargeOrderBean.getUser_id(),
                                rechargeOrderBean.getPayment_id(), rechargeOrderBean.getTrade_no(), new NetWorkCallBack2() {
                            @Override
                            public void onStart() {

                            }

                            @Override
                            public void onSucess(Object data) {
                                //更新成功更新状态
                                rechargeOrderBean.setIsauto("1");
                                rechargeOrderBean.save();
                                Log.d(TAG, "onSucess: uploadRechargeOrder sucess i= ");
                            }

                            @Override
                            public void onFailed(String msg) {

                                Log.d(TAG, "onFailed: msg= "+msg);
                            }
                        });



                    }

                }

                @Override
                public void onFailed(String msg) {

                }
            });


            //说明遍历到最后一个数据,更新任务完成
            if (i==ordersBeanArrayList.size()-1){

                if (onTskFinish!=null){

                    onTskFinish.finish();
                }
            }

        }


        Log.d(TAG, "uploadRechargeOrderStatus: finish");





    }
}
