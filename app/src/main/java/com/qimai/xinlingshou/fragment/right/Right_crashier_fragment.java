package com.qimai.xinlingshou.fragment.right;

import android.content.Context;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.qimai.xinlingshou.BaseFragment;
import com.qimai.xinlingshou.R;
import com.qimai.xinlingshou.adapter.GoodsAndClientFragmentAdapter;
import com.qimai.xinlingshou.fragment.left.Left_crashier_fragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

/**
 * Created by NIU on 2018/5/18.
 */

public class Right_crashier_fragment extends BaseFragment implements Left_crashier_fragment.onPendingOrderSucess {

    private static final String TAG = "Right_crashier_fragment";
    @BindView(R.id.tv_guadan)
    TextView tvGuadan;
    @BindView(R.id.tv_qudan)
    TextView tvQudan;
    @BindView(R.id.ll_bottom_menu)
    LinearLayout llBottomMenu;

    @BindView(R.id.tl_goods_client)
    TabLayout tlGoodsClient;
    @BindView(R.id.vp_goods_client)
    ViewPager vpGoodsClient;
    Unbinder unbinder;
    @BindView(R.id.tv_qudan_num)
    TextView tvQudanNum;
    Unbinder unbinder1;
    @BindView(R.id.tv_cheng)
    TextView tvCheng;
    @BindView(R.id.tv_qiang)
    TextView tvQiang;
    @BindView(R.id.tv_piao)
    TextView tvPiao;
    Unbinder unbinder2;
    private ArrayList<Fragment> fragmentArrayList;
    private GoodsAndClientFragmentAdapter goodsAndClientFragmentAdapter;

    private Right_client_fragment right_client_fragment;
    private Right_goods_fragment right_goods_fragment;

    private ClientInfoFragment clientInfoFragment;

    private int takeOrderId;

    private boolean isTakeOrder;
    private boolean isScanCodeConnect;

    @Override
    protected void initData() {



        UsbManager usbManager  = (UsbManager) getActivity().getSystemService(Context.USB_SERVICE);

        HashMap<String,UsbDevice> usbDeviceHashMap =  usbManager.getDeviceList();

        UsbDevice usbDevice = null;

        for (Map.Entry<String, UsbDevice> entry : usbDeviceHashMap.entrySet()) {

            /*Log.d(TAG, "onReceive: Key = " + entry.getKey() + ", Value = " + entry.getValue().toString());*/

            usbDevice = entry.getValue();




        if(usbDevice!=null){
        int type = usbDevice.getInterface(0).getInterfaceClass();

        if (type==3){

            tvQiang.setSelected(true);

        }
        Log.d(TAG, "onReceive: type= "+type);
        //https://blog.csdn.net/Rodulf/article/details/51916998?utm_source=blogxgwz9
        //type=3 表示是扫码枪
            Log.d(TAG, "onReceive: usbdevice11111= "+usbDevice.toString());

        }

        }
    }

    @Override
    protected void initView(View rootView) {

        EventBus.getDefault().register(this);

        fragmentArrayList = new ArrayList<>();

        right_client_fragment = new Right_client_fragment();
        right_goods_fragment = new Right_goods_fragment();
        clientInfoFragment = new ClientInfoFragment();
        fragmentArrayList.add(right_goods_fragment);
        fragmentArrayList.add(right_client_fragment);
        goodsAndClientFragmentAdapter = new GoodsAndClientFragmentAdapter
                (getChildFragmentManager()
                        , fragmentArrayList, getContext());
        tlGoodsClient.setupWithViewPager(vpGoodsClient);

        vpGoodsClient.setAdapter(goodsAndClientFragmentAdapter);


    }

    @Override
    protected int getLayout() {

        return R.layout.cashier_right_fragment;
    }


    @OnClick({R.id.tv_guadan, R.id.tv_qudan, R.id.ll_bottom_menu, R.id.tl_goods_client, R.id.vp_goods_client})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_guadan:

                MessageEvent messageEvent = new MessageEvent("pendingOrder");

                EventBus.getDefault().post(messageEvent);


                break;
            case R.id.tv_qudan:
                if (isTakeOrder) {

                    MessageEvent messageEvent1 = new MessageEvent("takeOrder");


                    messageEvent1.setTypeInt(takeOrderId);


                    EventBus.getDefault().post(messageEvent1);


                    setQudanNUmVisibity(false);

                    isTakeOrder = false;


                }

                break;
            case R.id.ll_bottom_menu:
                break;

            case R.id.tl_goods_client:
                break;
            case R.id.vp_goods_client:
                break;
        }
    }

    private void setQudanNUmVisibity(boolean b) {

        tvQudanNum.setVisibility(b ? View.VISIBLE : View.GONE);
    }


    @Override
    public void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);

    }


    @Subscribe(threadMode = ThreadMode.MAIN)

    public void onEvent(MessageEvent messageEvent) {


        if (messageEvent.getType().equals("pendingOrderSucess")) {

            takeOrderId = messageEvent.getTypeInt();
//            ToastUtils.showShortToast("挂单number= "+messageEvent.getTypeInt());
            if (takeOrderId == 0) {

                setQudanNUmVisibity(true);

                isTakeOrder = true;
            }
        } else if (messageEvent.getType().equals("takeOrder")) {

            vpGoodsClient.setCurrentItem(1);

        } else if (messageEvent.getType().equals("allDelete")) {

            vpGoodsClient.setCurrentItem(0);

        } else if (messageEvent.getType().equals(MessageEvent.SCAN_CODE_CONNECT)) {


            isScanCodeConnect = messageEvent.isTypeBoolean();

            Log.d(TAG, "onEvent: isScanCodeConnect= " + isScanCodeConnect);

            if (tvQiang!=null) {

                tvQiang.setSelected(isScanCodeConnect);
            }
        }else if (messageEvent.getType().equals(MessageEvent.PRINT_CONNECT)){

            if (tvPiao!=null){

                tvPiao.setSelected(messageEvent.isTypeBoolean());
            }

        }

    }


    @Override
    public void onPendingOrderSucess(int position) {


        // ToastUtils.showShortToast("挂单number= "+position);
    }

    public boolean getright_client_fragmentVisibity() {

        return right_client_fragment.getUserVisibleHint();

    }


    public boolean getright_goods_fragmentVisibity() {

        return right_goods_fragment.getUserVisibleHint();

    }


    public boolean getRight_vip_info_fragmentVisibity() {

        Log.d(TAG, "clientInfoFragment.visibity: " + (clientInfoFragment.isFragmentShow));

        return right_client_fragment.getVipInfoVisibity();
    }

    public boolean getRight_value_fragmentVisibity() {

        Log.d(TAG, "getRight_value_fragmentVisibity.visibity: " + (clientInfoFragment.isFragmentShow));

        return right_client_fragment.getValueCardRechargeVisibity();

    }




    @Override
    public void onDestroyView() {
        super.onDestroyView();
    }
}
