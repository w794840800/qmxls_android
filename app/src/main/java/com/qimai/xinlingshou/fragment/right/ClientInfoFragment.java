package com.qimai.xinlingshou.fragment.right;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qimai.xinlingshou.BaseFragment;
import com.qimai.xinlingshou.R;
import com.qimai.xinlingshou.Service.UploadUtils;
import com.qimai.xinlingshou.adapter.CouponsRecyclerAdapter;
import com.qimai.xinlingshou.bean.CouponsEntry;
import com.qimai.xinlingshou.bean.DiscountBean;
import com.qimai.xinlingshou.bean.PenderOrderClientInfo;
import com.qimai.xinlingshou.bean.RechargeOrderBean;
import com.qimai.xinlingshou.callback.OnTskFinish;
import com.qimai.xinlingshou.dialog.DialogUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

import static com.qimai.xinlingshou.fragment.right.MessageEvent.CALCULATEDISCOUNT;
import static com.qimai.xinlingshou.fragment.right.MessageEvent.CANCELCOUPONS1;
import static com.qimai.xinlingshou.fragment.right.MessageEvent.CANCELCOUPONS2;
import static com.qimai.xinlingshou.fragment.right.MessageEvent.UPDATEDISCOUNT;

/**
 * Created by wanglei on 18-5-22.
 */

public class ClientInfoFragment extends BaseFragment implements CouponsRecyclerAdapter.OnViewItemClickListener {


    private static final String TAG = "ClientInfoFragment";
    @BindView(R.id.tv_client_name)
    TextView tvClientName;
    @BindView(R.id.lv_youhuiquan)
    LinearLayout lvYouhuiquan;
    @BindView(R.id.lv_vip)
    LinearLayout lvVip;
    @BindView(R.id.tv_client_mobile)
    TextView tvClientMobile;
    @BindView(R.id.bt_client_change)
    TextView btClientChange;
    @BindView(R.id.tv_client_consume_numbe)
    TextView tvClientConsumeNumbe;
    @BindView(R.id.tv_client_last_consume_time)
    TextView tvClientLastConsumeTime;
    @BindView(R.id.ll_vip_card)
    RelativeLayout llVipCard;
    @BindView(R.id.ll_coupons)
    LinearLayout llCoupons;
    Unbinder unbinder;
    @BindView(R.id.tv_vip_style)
    TextView tvVipStyle;
    @BindView(R.id.tv_validity_period)
    TextView tvValidityPeriod;
    @BindView(R.id.tv_discount)
    TextView tvDiscount;
    @BindView(R.id.tv_vip_name)
    TextView tvVipName;
    boolean isSelectVipCard;
    @BindView(R.id.rv_coupons)
    RecyclerView rvCoupons;
    @BindView(R.id.tv_recharge_refresh)
    TextView recharge_refresh;
    CouponsRecyclerAdapter couponsRecyclerAdapter;
    onClientChangeListener onClientChangeListener;
    @BindView(R.id.tv_account)
    TextView tvAccount;
    @BindView(R.id.ll_account)
    LinearLayout llAccount;
    Unbinder unbinder1;
    private ArrayList<CouponsEntry> CouponsEntryArrayList;

    PenderOrderClientInfo penderOrderClientInfo;
    DiscountBean discountBean;
    private int selectPosition = -1;
    String clientInfo;

    String userId;
    @Override
    protected void initData() {

    }

    @Override
    protected void initView(View rootView) {
        CouponsEntryArrayList = new ArrayList<>();

        ArrayList<RechargeOrderBean> ordersBeanArrayList = (ArrayList<RechargeOrderBean>)
                LitePal.where("query_order= 0 and isauto=0 and ispay=0 and user_id= "+userId).find(RechargeOrderBean.class);

        if (ordersBeanArrayList==null||ordersBeanArrayList.size()==0){

            recharge_refresh.setVisibility(View.GONE);
        }
        // EventBus.getDefault().register(this);
       /* couponsRecyclerAdapter = new CouponsRecyclerAdapter(activity,CouponsEntryArrayList);
        rvCoupons.setLayoutManager(new GridLayoutManager(activity,3));
        rvCoupons.setAdapter(couponsRecyclerAdapter);*/
//        rvCoupons.et
    }

    @Override
    protected int getLayout() {

        return R.layout.client_info;
    }

    //
    @OnClick({R.id.bt_client_change, R.id.tv_client_consume_numbe, R.id.tv_client_last_consume_time, R.id.ll_vip_card, R.id.ll_coupons,R.id.tv_recharge_refresh})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.bt_client_change:
                isSelectVipCard = false;
                // llVipCard.setBackgroundResource(R.drawable.vip_bg);
              /*  discountBean = new DiscountBean();
                CouponsEntryArrayList.clear();
                if (onClientChangeListener!=null){
                   onClientChangeListener.onClientChange();

                }

                onClick(rootView,-1);*/

                clearCurrentClientInfo();

                break;
            case R.id.tv_client_consume_numbe:
                break;
            case R.id.tv_client_last_consume_time:
                break;
            case R.id.ll_vip_card:

                break;
            case R.id.ll_coupons:

                break;

            case R.id.tv_recharge_refresh:

                updateRecharge();



                break;
        }
    }

    //更新余额
    private void updateRecharge() {

        //先查询数据库中是否有支付未同步单

       // UploadUtils.uploadRechargeOrderStatus();


        UploadUtils.uploadRechargeOrderStatus(userId, new OnTskFinish() {
            @Override
            public void finish() {
                Log.d(TAG, "finish: finish ");
                //重新更新用户用户

                onClientChangeListener.onRefresh(userId);

            }
        });

    }

    public void setOnClientChangeListener(onClientChangeListener onClientChangeListener) {


        this.onClientChangeListener = onClientChangeListener;
    }

    @Override
    public void onClick(View view, int position) {

        Log.d(TAG, "onClick: ");


        changeYhDataAndUI(position);

       /* if (selectPosition==-1){


        }else*/


    }


    /**
     * @Function 更新当前优惠券的选择状态及优惠计算
     */
    private void changeYhDataAndUI(int position) {

        Log.d(TAG, "changeYhDataAndUI: ");

        if (position >= 0) {
            CouponsEntry couponsEntry = CouponsEntryArrayList.get(position);
            selectPosition = position;
            boolean isSelected = CouponsEntryArrayList.get(position).isSelected();
            String type = couponsEntry.getCoupon_type();

            Log.d(TAG, "onClick: couponsEntry= " + couponsEntry.toString());
            Log.d(TAG, "onClick: type= " + type);
            if (type.equals("0")) {

                if (isSelected) {

                    discountBean.setMianzhi_num(couponsEntry.getAmount());
                    discountBean.setMianzhi_max(couponsEntry.getMin_amount_use());
                    discountBean.setCoupons_id(couponsEntry.getCoupon_id());
                    discountBean.setZhekou_num(null);
                } else {

                    discountBean.setCoupons_id("");
                    discountBean.setMianzhi_num(null);
                    discountBean.setMianzhi_max(null);
                    discountBean.setZhekou_num(null);
                }


            } else {


                if (isSelected) {

                    discountBean.setZhekou_num(couponsEntry.getAmount());
                    discountBean.setMianzhi_max(null);
                    discountBean.setMianzhi_num(null);
                    discountBean.setCoupons_id(couponsEntry.getCoupon_id());
                } else {

                    discountBean.setCoupons_id("");
                    discountBean.setZhekou_num(null);
                    discountBean.setMianzhi_max(null);
                    discountBean.setMianzhi_num(null);

                }

            }


            if (!isSelected) {

                MessageEvent messageEvent1 = new MessageEvent(CANCELCOUPONS1);

                EventBus.getDefault().post(messageEvent1);
            }


        }
        MessageEvent messageEvent = new MessageEvent(CALCULATEDISCOUNT);

        messageEvent.setDiscountBean(discountBean);
        EventBus.getDefault().post(messageEvent);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder1 = ButterKnife.bind(this, rootView);

        EventBus.getDefault().register(this);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        EventBus.getDefault().unregister(this);
        unbinder1.unbind();
    }

    @OnClick(R.id.tv_balance_recharge)
    public void onClick() {

        if (onClientChangeListener!=null){

            onClientChangeListener.onGoToRecharge();
        }


    }


    //13516492591
    public interface onClientChangeListener {

        void onClientChange();

        //去充值储值卡界面
        void onGoToRecharge();

        void onRefresh(String userId);



    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent messageEvent) {
        Log.d(TAG, "onEvent: Left_crashier_fragment1111 messageEvent= " + messageEvent.getType());
        if (messageEvent.getType().equals("client_info")) {


            clientInfo = messageEvent.getClientinfo();

            updateUI(clientInfo);

               /* discountBean = new DiscountBean();
                isSelectVipCard = false;

                couponsRecyclerAdapter = new CouponsRecyclerAdapter(activity,CouponsEntryArrayList);
                couponsRecyclerAdapter.setViewItemClickListener(this);

                rvCoupons.setLayoutManager(new GridLayoutManager(activity,3));
                rvCoupons.setAdapter(couponsRecyclerAdapter);
                String clientinfo= messageEvent.getClientinfo();
                Log.d(TAG, "onEvent: clientinfo= "+clientinfo);
                JSONObject client_info = new JSONObject(clientinfo);
                JSONObject base_info  =   client_info.getJSONObject("base_info");
                JSONObject purchase  =   client_info.getJSONObject("purchase");
                JSONArray couponsArr  =   client_info.getJSONArray("coupons");
                App.store.put("userid",base_info.getString("id")).commit();
                JSONObject card = null;
                try {
                 card = client_info.getJSONObject("card");
            }catch (JSONException e){
                e.printStackTrace();
            }
                tvClientName.setText(base_info.getString("nickname"));
                tvClientMobile.setText(base_info.getString("mobile"));
                tvClientConsumeNumbe.setText(purchase.getString("consumption"));
                tvClientLastConsumeTime.setText(purchase.getString("last_time"));
                if (couponsArr.length()>0) {
                    lvYouhuiquan.setVisibility(View.VISIBLE);
                    for (int i = 0; i < couponsArr.length(); i++) {
                        JSONObject coupons = couponsArr.getJSONObject(i);
                        JSONObject json = coupons.getJSONObject("coupon");
                        Log.d(TAG, "onEvent: json= "+json.getString("title"));
                        CouponsEntryArrayList.add(new CouponsEntry(json.getString("id"),
                                json.getString("store_id"),
                                json.getString("title"),
                                json.getString("amount"),
                                json.getString("coupon_type"),
                                json.getString("min_amount_use"),
                                json.getString("expire_date_text")));
                    }



                    couponsRecyclerAdapter.updateData(CouponsEntryArrayList);
                }else {
                    lvYouhuiquan.setVisibility(View.GONE);
                }
//                tvVipStyle.setText(card.getString("title"));//卡信息
//                tvValidityPeriod.setText(card.getString("card_type"));//有效
//                tvDiscount.setText(card.getString("discount"));//几折
//                tvVipName.setText(card.getString("last_time"));//版本

                if (card!=null&&card.length()>0) {
                    lvVip.setVisibility(View.VISIBLE);
                    tvVipStyle.setText(card.getString("title"));
                    tvDiscount.setText(card.getString("discount"));
                    tvVipName.setText(card.getString("card_type"));
                }else {
                    lvVip.setVisibility(View.GONE);
                }

                //默认登录会员账号只要有会员卡就要选中

                String discount = card.getString("discount");
                if (!TextUtils.isEmpty(discount)) {

                    Log.d(TAG, "onEvent: discount= "+discount);
                    discountBean.setVip_card_num(discount);
                    MessageEvent messageEventVip = new MessageEvent(CALCULATEDISCOUNT);
                   // discountBean.setVipCard_id();
                    messageEventVip.setDiscountBean(discountBean);
                    EventBus.getDefault().post(messageEventVip);
                }*/


        } else if (messageEvent.getType().equals("allDelete")) {

            try {
                Log.d(TAG, "onEvent: allDelete");
                discountBean = null;
                //llVipCard.setBackgroundResource(R.drawable.vip_bg);
                CouponsEntryArrayList.clear();
                CouponsEntryArrayList = new ArrayList<>();
                couponsRecyclerAdapter.updateData(CouponsEntryArrayList);
                isSelectVipCard = false;
                Log.d(TAG, "onEvent: onClientChangeListener==null " + (onClientChangeListener == null));
                if (onClientChangeListener != null) {
                    onClientChangeListener.onClientChange();
                }
            } catch (Exception e) {

            }
        } else if (messageEvent.getType()
                .equals("change_store")) {

            isSelectVipCard = false;


            clearCurrentClientInfo();

        } else if (messageEvent.getType().equals(CANCELCOUPONS2)) {


            try {

                if (CouponsEntryArrayList != null && CouponsEntryArrayList.size() >= 0 && CouponsEntryArrayList.get(selectPosition) != null) {

                    CouponsEntryArrayList.get(selectPosition).setSelected(false);
                    couponsRecyclerAdapter.updateData(CouponsEntryArrayList);
                    onClick(rootView, selectPosition);
                }
            } catch (Exception e) {

            }
        } else if (messageEvent.getType().equals(UPDATEDISCOUNT)) {

            Log.d(TAG, "onEvent: UPDATEDISCOUNT selectPosition= " + selectPosition);
            //if (selectPosition!=-1){
            changeYhDataAndUI(-1);
            // }
        } else if (messageEvent.getType().equals("pendingOrderSucess")) {

            //penderOrderClientInfo = new PenderOrderClientInfo();
            //penderOrderClientInfo.setPenderOrderInfo(clientInfo);
            //penderOrderClientInfo.setYhqSelected(selectPosition);

            Log.d(TAG, "onEvent: changestore");
            clearCurrentClientInfo();

        } else if (messageEvent.getType().equals("takeOrder")) {

                /*if (penderOrderClientInfo != null) {
                    String info = penderOrderClientInfo.getPenderOrderInfo();
                    if (!TextUtils.isEmpty(info))
                        updateUI(info);
                }

                int historyPOsition = penderOrderClientInfo.getYhqSelected();

                if (historyPOsition != -1 && CouponsEntryArrayList.size() >= historyPOsition) {

                    CouponsEntryArrayList.get(historyPOsition).setSelected(true);

                    couponsRecyclerAdapter.updateData(CouponsEntryArrayList);

                    changeYhDataAndUI(historyPOsition);


                }*/


        }
        //最后移除粘性事件
        EventBus.getDefault().removeAllStickyEvents();

    }


    /**
     * @Function 用户清除当前登录的用户信息
     */
    private void clearCurrentClientInfo() {

        discountBean = new DiscountBean();
        CouponsEntryArrayList.clear();
        Log.d(TAG, "clearCurrentClientInfo: onClientChangeListener= " + (onClientChangeListener == null));
        if (onClientChangeListener != null) {
            onClientChangeListener.onClientChange();

        }

        //onClick(rootView,-1);


        changeYhDataAndUI(-1);
    }

    private void updateUI(String clientinfo1) {

        Log.d(TAG, "updateUI: clientinfo1= " + clientinfo1+" throawble= "+Log
        .getStackTraceString(new Throwable()));
        String clientinfo = clientinfo1;
        discountBean = new DiscountBean();

        isSelectVipCard = false;

        couponsRecyclerAdapter = new CouponsRecyclerAdapter(activity, CouponsEntryArrayList);
        couponsRecyclerAdapter.setViewItemClickListener(this);

        rvCoupons.setLayoutManager(new GridLayoutManager(activity, 3));
        rvCoupons.setAdapter(couponsRecyclerAdapter);

        Log.d(TAG, "onEvent: clientinfo= " + clientinfo);
        JSONObject client_info = null;
        try {
            client_info = new JSONObject(clientinfo);
            JSONObject base_info = client_info.optJSONObject("base_info");
            JSONObject purchase = client_info.optJSONObject("purchase");
            JSONObject balance = client_info.optJSONObject("balance");
            JSONArray couponsArr = client_info.optJSONArray("coupons");

            Log.d(TAG, "updateUI: balance= " + (balance == null));
            //更新用户账户余额
            if (balance != null) {

                String totalBlance = balance.getString("total_amount");

                if (!TextUtils.isEmpty(totalBlance)) {

                    llAccount.setVisibility(View.VISIBLE);
                    tvAccount.setText(totalBlance + "元");
                }
            } else {
                llAccount.setVisibility(View.GONE);


            }
            discountBean.setUser_id(base_info.getString("id"));

            userId = base_info.getString("id");


            //App.store.put("userid",base_info.getString("id")).commit();
            JSONObject card = null;

            card = client_info.optJSONObject("card");

            tvClientName.setText(base_info.getString("nickname"));
            tvClientMobile.setText(base_info.getString("mobile"));
            tvClientConsumeNumbe.setText(purchase.getString("consumption"));
            tvClientLastConsumeTime.setText(purchase.getString("last_time"));


            if (couponsArr.length() > 0) {

                lvYouhuiquan.setVisibility(View.VISIBLE);
                for (int i = 0; i < couponsArr.length(); i++) {
                    JSONObject coupons = couponsArr.getJSONObject(i);
                    JSONObject json = coupons.getJSONObject("coupon");
                    Log.d(TAG, "onEvent: json= " + json.getString("title"));
                    String coupon_id = null;
                    try {
                        coupon_id = coupons.getString("id");
                        Log.d(TAG, "updateUI: coupon_id= " + coupon_id + " i= " + i);
                        if (TextUtils.isEmpty(coupon_id)) {

                            coupon_id = "";
                        }
                    } catch (Exception e) {

                        Log.d(TAG, "updateUI111: e= " + e.toString());

                    }
                    CouponsEntryArrayList.add(new CouponsEntry(json.getString("id"),
                            json.getString("store_id"),
                            json.getString("title"),
                            json.getString("amount"),
                            json.getString("coupon_type"),
                            json.getString("min_amount_use"),
                            json.getString("expire_date_text"), coupon_id));
                }


                couponsRecyclerAdapter.updateData(CouponsEntryArrayList);
            } else {
                lvYouhuiquan.setVisibility(View.GONE);
            }
//                tvVipStyle.setText(card.getString("title"));//卡信息
//                tvValidityPeriod.setText(card.getString("card_type"));//有效
//                tvDiscount.setText(card.getString("discount"));//几折
//                tvVipName.setText(card.getString("last_time"));//版本

            if (card != null && card.length() > 0) {
                lvVip.setVisibility(View.VISIBLE);
                tvVipStyle.setText(card.getString("title"));
                tvDiscount.setText(card.getString("discount"));

                Log.d(TAG, "updateUI: card= " + card.toString());
                // tvVipName.setText(card.getString("card_type"));
                discountBean.setVipCard_id(card.getString("id"));

                Log.d(TAG, "updateUI: cardId = " + card.getString("id"));
            } else {
                lvVip.setVisibility(View.GONE);
            }

            //默认登录会员账号只要有会员卡就要选中

            String discount = card.optString("discount");
            if (!TextUtils.isEmpty(discount)) {

                Log.d(TAG, "onEvent: discount= " + discount);
                discountBean.setVip_card_num(discount);
                MessageEvent messageEventVip = new MessageEvent(CALCULATEDISCOUNT);
                // discountBean.setVipCard_id();
                messageEventVip.setDiscountBean(discountBean);
                EventBus.getDefault().post(messageEventVip);
            }
        } catch (JSONException e) {

            e.printStackTrace();
        }

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {

        Log.d(TAG, "setUserVisibleHint: isVisibleToUser= "+isVisibleToUser);
        super.setUserVisibleHint(isVisibleToUser);
    }

    @Override
    public void onDestroy() {
        discountBean = null;
        userId = null;
        super.onDestroy();
        // EventBus.getDefault().unregister(this);


    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        Log.d(TAG, "onHiddenChanged: hideen= "+hidden);

    }



}
