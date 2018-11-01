package com.qimai.xinlingshou.Service;

import android.app.IntentService;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.qimai.xinlingshou.App;
import com.qimai.xinlingshou.bean.RechargeOrderBean;
import com.qimai.xinlingshou.bean.ordersBean;
import com.qimai.xinlingshou.callback.NetWorkCallBack2;
import com.qimai.xinlingshou.model.PayModel;
import com.qimai.xinlingshou.utils.Xutils;

import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by wanglei on 18-7-16.
 *
 * //断网开网后同步未同步订单
 */

public class UploadService extends IntentService{


    public UploadService(){
        super("upload");


    }
    private static final String TAG = "UploadService";
    /**
     * Creates an IntentService.  Invoked by your subclass's constructor.
     *
     * @param name Used to name the worker thread, important only for debugging.
     */
    public UploadService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(@Nullable Intent intent) {

        Log.d(TAG, "onHandleIntent: ");
        updateOldOrder();

    }

    private void updateOldOrder() {

        //同步用户支付完成情况下，但存在订单轮训状态的储值充值订单

        UploadUtils.uploadRechargeOrderStatus();


        //同步用户支付成功的单

        ArrayList<RechargeOrderBean> rechargeBeanArrayList = (ArrayList<RechargeOrderBean>)
                LitePal.where("isauto=0 and ispay=1").find(RechargeOrderBean.class);


        for (int i = 0; i < rechargeBeanArrayList.size(); i++) {

            RechargeOrderBean rechargeOrderBean = rechargeBeanArrayList.get(i);
            new PayModel().uploadRechargeOrder(rechargeOrderBean.getOrder_no()
                    , rechargeOrderBean.getUser_id(), rechargeOrderBean.getPayment_id(), rechargeOrderBean
                            .getTrade_no(),new NetWorkCallBack2(){
                        @Override
                        public void onSucess(Object data) {

                            rechargeOrderBean.setIsauto("1");
                            rechargeOrderBean.save();
                        }

                        @Override
                        public void onFailed(String msg) {

                        }

                        @Override
                        public void onStart() {

                        }
                    });

        }


        //同步用户支付完成情况下，但存在订单轮训状态，需要更新这种订单

        UploadUtils.uploadQueryOrder();

        //同步支付完成后未支付的订单
        ArrayList<ordersBean> ordersBeanArrayList = (ArrayList<ordersBean>)
                LitePal.where("isauto=0 and ispay=1").find(ordersBean.class);

        Log.d(TAG, "updateOldOrder: ordersBeanArrayList size= "+ordersBeanArrayList.size());

        if (ordersBeanArrayList!=null&&ordersBeanArrayList.size()>0){

            for (ordersBean o:
                    ordersBeanArrayList) {
                uploadDateToServe(o);

                Log.d(TAG, "updateOldOrder: 11111");
            }

        }






    }
    private void uploadDateToServe(final ordersBean mOrderbean) {
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

}
