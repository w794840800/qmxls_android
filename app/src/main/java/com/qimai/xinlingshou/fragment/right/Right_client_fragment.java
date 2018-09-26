package com.qimai.xinlingshou.fragment.right;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qimai.xinlingshou.App;
import com.qimai.xinlingshou.BaseFragment;
import com.qimai.xinlingshou.R;
import com.qimai.xinlingshou.callback.NetWorkCallBack;
import com.qimai.xinlingshou.model.ClientModel;
import com.qimai.xinlingshou.utils.Hint;
import com.qimai.xinlingshou.utils.ToastUtils;
import com.qimai.xinlingshou.utils.Xutils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * 客户-会员查找
 * Created by NIU on 2018/5/18.
 */

public class Right_client_fragment extends BaseFragment implements ClientInfoFragment.onClientChangeListener{

    private static final String TAG = "Right_client_fragment";
    @BindView(R.id.tv_vip_id)
    TextView tvVipId;
    @BindView(R.id.tv_key_1)
    TextView tvKey1;
    @BindView(R.id.tv_key_4)
    TextView tvKey4;
    @BindView(R.id.tv_key_7)
    TextView tvKey7;
    @BindView(R.id.tv_key_00)
    TextView tvKey00;
    @BindView(R.id.tv_key_2)
    TextView tvKey2;
    @BindView(R.id.tv_key_5)
    TextView tvKey5;
    @BindView(R.id.tv_key_8)
    TextView tvKey8;
    @BindView(R.id.tv_key_0)
    TextView tvKey0;
    @BindView(R.id.tv_key_3)
    TextView tvKey3;
    @BindView(R.id.tv_key_6)
    TextView tvKey6;
    @BindView(R.id.tv_key_9)
    TextView tvKey9;
    @BindView(R.id.tv_key_point)
    TextView tvKeyPoint;
    @BindView(R.id.tv_key_clear)
    TextView tvKeyClear;
    @BindView(R.id.tv_key_del)
    TextView tvKeyDel;
    @BindView(R.id.tv_key_sure)
    TextView tvKeySure;
    @BindView(R.id.fl_container)
    FrameLayout flContainer;

    Unbinder unbinder;

    //判断是否登录到客户信息fragment
    boolean isVisibity;
    StringBuilder userId = new StringBuilder();


    FragmentManager fragmentManager;

    String pendOrderNumber;
    String clientNumber;
    ClientInfoFragment clientInfoFragment;
    @BindView(R.id.ll_client)
    LinearLayout llClient;

    ClientModel clientModel;
    String scanCode;
    @Override
    protected void initData() {

    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

    }

    @Override
    protected void initView(View rootView) {

        EventBus.getDefault().register(this);
        fragmentManager = getChildFragmentManager();
        addFragment(fragmentManager.beginTransaction());
        hideFragment(fragmentManager.beginTransaction());
        clientInfoFragment.setOnClientChangeListener(this);

    }


    @Override
    protected int getLayout() {
        return R.layout.cashier_right_item_client;
    }


    @OnClick({R.id.tv_vip_id, R.id.tv_key_1, R.id.tv_key_4, R.id.tv_key_7, R.id.tv_key_00, R.id.tv_key_2, R.id.tv_key_5, R.id.tv_key_8, R.id.tv_key_0, R.id.tv_key_3, R.id.tv_key_6, R.id.tv_key_9, R.id.tv_key_clear, R.id.tv_key_del, R.id.tv_key_sure,R.id.tv_scan_tips})
    public void onViewClicked(View view) {
        switch (view.getId()) {

            case R.id.tv_scan_tips:
                //sendClinentWithUserId("20180918082436560296");
                SendClient("","2527",2);
                break;
            case R.id.tv_vip_id:
                break;
            case R.id.tv_key_1:

            case R.id.tv_key_4:

            case R.id.tv_key_7:

            case R.id.tv_key_2:

            case R.id.tv_key_5:

            case R.id.tv_key_8:

            case R.id.tv_key_0:

            case R.id.tv_key_3:

            case R.id.tv_key_6:

            case R.id.tv_key_9:

                TextView tempView = (TextView) rootView.findViewById(view.getId());

                userId.append(tempView.getText().toString());

                break;
            case R.id.tv_key_clear:

                userId = new StringBuilder();

                break;
            case R.id.tv_key_del:

                if (userId.length() > 0) {

                    userId.delete(userId.length() - 1, userId.length());

                }
                break;
            case R.id.tv_key_sure:

                //数据库查询有无此会员

                // ((MainActivity) activity).showClientInfoLayout();
//                Hint.Short(getActivity(),userId.toString());


                SendClient(userId.toString());
                break;
        }
        if (userId.length() > 0) {
            tvVipId.setText(userId.toString());
        } else {
            tvVipId.setText("");
        }
    }

    /***
     * 根据scanCode去查userId并发起请求
     * */
    public void sendClinentWithUserId(String scanCode){

        if (clientModel==null){

            clientModel = new ClientModel();
        }


        clientModel.changeScanDataToUserId(scanCode
                , new NetWorkCallBack<String>() {
                    @Override
                    public void onSucess(String userId) {

                        Log.d(TAG, "onSucess: changeScanDataToUserId= "+userId);

                        SendClient("",userId,2);

                    }

                    @Override
                    public void onFailed(String msg) {

                        Log.d(TAG, "onFailed: changeScanDataToUserId msg="+msg);

                        ToastUtils.showShortToast(msg);
                    }
                });


    }

    //这种是手机号登录，不需要user_id
    //type=1 传手机号
    public void SendClient(final String mobile){

        SendClient(mobile,"",1);

    }


    /**
     * 根据userId或者手机号登录
     * */
    public void SendClient(final String mobile,String user_id,int type){

   /*     clientModel.searchUserInfo(mobile,user_id,type, new NetWorkCallBack<String>() {
            @Override
            public void onSucess(String data) {

                Log.d(TAG, "onSucess: userInfo= "+data);

                Log.d(TAG, "onResponse: send client_info");
                clientNumber = mobile;
               // JSONObject mjsonObject = mjsonObjects.getJSONObject("data");
                MessageEvent messageEvent = new MessageEvent("client_info");
                messageEvent.setClientinfo(data.toString());
                EventBus.getDefault().postSticky(messageEvent);
                llClient.setVisibility(View.GONE);
                isVisibity = true;
                showFragment(fragmentManager.beginTransaction());
               *//* try {
                    JSONObject mjsonObjects = new JSONObject(data);
                    String result = mjsonObjects.getString("status");
                    String message = mjsonObjects.getString("message");
                    if (result.equals("true")) {



                    } else {

                        Hint.Short(getActivity(), message);

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }*//*

            }


            @Override
            public void onFailed(String msg) {

                Hint.Short(getActivity(), msg);
                Log.d(TAG, "onFailed: msg= "+msg);
            }
        });*/




        Log.d(TAG, "SendClient: ");
        String url = App.API_URL+"reta/cashier/user-by-mobile";
        Map<String,String> stringMap = new HashMap<>();

        stringMap.put("mobile", mobile);
        stringMap.put("user_id",user_id);
        stringMap.put("type",type+"");

        Xutils.getInstance().get(url, stringMap, new Xutils.XCallBack() {
            @Override
            public void onResponse(String str) {
                try {
                    JSONObject mjsonObjects = new JSONObject(str);
                    String result = mjsonObjects.getString("status");
                    String message = mjsonObjects.getString("message");
                    if (result.equals("true")) {

                        Log.d(TAG, "onResponse: send client_info");
                        clientNumber = mobile;
                        JSONObject mjsonObject = mjsonObjects.getJSONObject("data");
                        MessageEvent messageEvent = new MessageEvent("client_info");
                        messageEvent.setClientinfo(mjsonObject.toString());
                        EventBus.getDefault().postSticky(messageEvent);
                        llClient.setVisibility(View.GONE);
                        isVisibity = true;
                        showFragment(fragmentManager.beginTransaction());
                    } else {
                        Hint.Short(getActivity(), message);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            protected Object clone() throws CloneNotSupportedException {
                return super.clone();
            }
        });
    }

    @Override
    public void onClientChange() {
        clearAllText();
        hideFragment(fragmentManager.beginTransaction());

    }

    private void clearAllText() {
        userId = new StringBuilder();
        tvVipId.setText("请输入会员手机号，会员号查询");
        llClient.setVisibility(View.VISIBLE);
        clientNumber = "";

        MessageEvent messageEvent = new MessageEvent("remove_vip");
        EventBus.getDefault().post(messageEvent);
        //pendOrderNumber = "";
    }



    public void hideFragment(FragmentTransaction fragmentTransaction){

        if (clientInfoFragment!=null){
            fragmentTransaction
                    .hide(clientInfoFragment)
            .commit();
            isVisibity = false;
        }

    }
    /***
     * 展示客户信息
     * **/

    public void showFragment(FragmentTransaction fragmentTransaction){


        if (clientInfoFragment!=null){


            fragmentTransaction
                    .show(clientInfoFragment)

                    .commit();
            }
            isVisibity = true;


    }
    public void addFragment(FragmentTransaction fragmentTransaction){

        if (clientInfoFragment==null) {
            clientInfoFragment = new ClientInfoFragment();
            fragmentTransaction.add(R.id.fl_container,
                    clientInfoFragment,"clientInfo")
            .commit()
            ;
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)

    public void onEvent(MessageEvent messageEvent) {

        if (messageEvent.getType().equals("takeOrder")){


            if (!TextUtils.isEmpty(pendOrderNumber)){

                SendClient(pendOrderNumber);

            //    showFragment(fragmentManager.beginTransaction());
            }

        }else if (messageEvent.getType().equals("pendingOrderSucess")){

            pendOrderNumber = clientNumber;

        }else if (messageEvent.getType().equals("allDelete")){
            //clearAllText();

            pendOrderNumber = "";

        }else if (messageEvent.getType().equals(MessageEvent.SCAN_VIP_LOGIN)) {

            scanCode = messageEvent.getStringValues();

            Log.d(TAG, "onEvent: scsnCode= "+scanCode);
            sendClinentWithUserId(scanCode);
        }


    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }


}
