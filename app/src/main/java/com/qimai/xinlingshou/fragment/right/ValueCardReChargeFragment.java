package com.qimai.xinlingshou.fragment.right;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.DragAndDropPermissions;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.qimai.xinlingshou.BaseFragment;
import com.qimai.xinlingshou.R;
import com.qimai.xinlingshou.activity.MainActivity;
import com.qimai.xinlingshou.adapter.ValueCardRecyclerAdapter;
import com.qimai.xinlingshou.bean.SecondScreenInfo;
import com.qimai.xinlingshou.bean.ValueCardBean;
import com.qimai.xinlingshou.callback.NetWorkCallBack2;
import com.qimai.xinlingshou.dialog.DialogUtils;
import com.qimai.xinlingshou.dialog.RechargeDialog;
import com.qimai.xinlingshou.model.PayModel;
import com.qimai.xinlingshou.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class ValueCardReChargeFragment extends BaseFragment implements ValueCardRecyclerAdapter.onViewItemClick,View.OnClickListener{

    private static final String TAG = "ValueCardReChargeFragme";
    onBackClick onBackClick;
    Toolbar toolbar;
    @BindView(R.id.rv_value_card)
    RecyclerView rvValueCard;
    Unbinder unbinder;
    Dialog dialog;

    String userId;
    String userMobile;
    RechargeDialog rechargeDialog;
    boolean isViewCreate = false;
    PayModel payModel;
    int selectPosition;
    String rechargeId;
    ArrayList<ValueCardBean>valueCardBeanArrayList;

    ValueCardRecyclerAdapter valueCardRecyclerAdapter;
    @Override
    protected void initData() {

    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setHasOptionsMenu(true);

    }

    @Override
    protected void initView(View rootView) {

        rvValueCard = (RecyclerView) rootView.findViewById(R.id.rv_value_card);

        toolbar = (Toolbar) rootView.findViewById(R.id.tl_choose_count);

        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onBackClick != null) {

                    onBackClick.onBackClick();
                }
            }
        });

    }

    @Override
    protected int getLayout() {

        return R.layout.recharge_fragment;

    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);

        Log.d(TAG, "setUserVisibleHint: isVisibleToUse= " + isVisibleToUser);

    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);

        Log.d(TAG, "onHiddenChanged: hidden= " + hidden);

        if (!hidden){

            MessageEvent messageEvent = new MessageEvent(MessageEvent.COLLECTION_CANCEL_ENABLE);

            messageEvent.setTypeBoolean(false);
            EventBus.getDefault().post(messageEvent);
        }

        if (isViewCreate&&!hidden){

            if (payModel==null){

                payModel = new PayModel();
            }


            payModel.getValueCard(1, 128, "", 1, new NetWorkCallBack2<ArrayList<ValueCardBean>>() {
                @Override
                public void onSucess(ArrayList<ValueCardBean> data) {

                    valueCardBeanArrayList = data;
                    if (valueCardRecyclerAdapter==null){

                        valueCardRecyclerAdapter = new ValueCardRecyclerAdapter(getActivity(),data);
                        valueCardRecyclerAdapter.setOnViewItemClick(ValueCardReChargeFragment.this);

                        rvValueCard.setLayoutManager
                                (new GridLayoutManager(activity,4));

                        rvValueCard.setAdapter(valueCardRecyclerAdapter);

                    }else{

                        valueCardRecyclerAdapter.update(data);
                    }


                    DialogUtils.cancelDialog();


                }

                @Override
                public void onFailed(String msg) {

                    ToastUtils.showShortToast(msg);


                    DialogUtils.cancelDialog();
                }

                @Override
                public void onStart() {

                    DialogUtils.createDialog(getActivity());


                }
            });
        }


    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {


        EventBus.getDefault().register(this);
        View view = super.onCreateView(inflater, container, savedInstanceState);

        Log.d(TAG, "onCreateView: ");
        isViewCreate = true;
        //unbinder = ButterKnife.bind(this, view);
        return view;

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {


        switch (item.getItemId()) {

            case android.R.id.home:
                //Toast.makeText(activity, "111ckick ckkdkfjkd", Toast.LENGTH_SHORT).show();

                // ((MainActivity)activity).showRightCrashierLayout();
                //
                if (onBackClick != null) {

                    onBackClick.onBackClick();
                }

                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void setOnBackClick(ValueCardReChargeFragment.onBackClick onBackClick) {

        this.onBackClick = onBackClick;
    }

    @Override
    public void onDestroyView() {
        isViewCreate = false;
        EventBus.getDefault().unregister(this);

        super.onDestroyView();
    }

    @Override
    public void onItemClick(View v, int position) {


        Log.d(TAG, "onItemClick: position= "+position);



        showRechargeDialogTips(position);


    }

    private void showRechargeDialogTips(int position) {


        valueCardBeanArrayList.get(position).setUserId(userId);
        valueCardBeanArrayList.get(position).setMobile(userMobile);

        rechargeDialog = RechargeDialog.getInstance(valueCardBeanArrayList.get(position));

         rechargeDialog.show(getChildFragmentManager(),"recharge");

         /*dialog = new Dialog(getActivity(),R.style.CustomDialog);
        //dialog.setCanceledOnTouchOutside(false);
        //dialog.setCancelable(false);
        View view = LayoutInflater.from(getActivity()).
                inflate(R.layout.dialog_recharge,null);

        TextView tv_user_mobile = view.findViewById(R.id.tv_user_mobile);
        TextView tv_recharge_money = view.findViewById(R.id.tv_recharge_money);
        TextView tv_presenter_money = view.findViewById(R.id.tv_presenter_money);

        TextView tvCancel = view.findViewById(R.id.tv_cancel);
        TextView tvOK = view.findViewById(R.id.tv_ok);
        tvCancel.setOnClickListener(this);
        tvOK.setOnClickListener(this);
        tv_recharge_money.setText(valueCardBeanArrayList.get(position).getSell_price());

        rechargeId = valueCardBeanArrayList.get(position).getRecharge_id();
        tv_presenter_money.setText(valueCardBeanArrayList.get(position).getEntity()+"元");

        dialog.setContentView(view);
*/
        //dialog.show();
    }

    @Override
    public void onClick(View v) {


        switch (v.getId()){

            case R.id.tv_ok:


                ((MainActivity) getActivity()).showPayFragment();

                MessageEvent messageEvent = new MessageEvent(MessageEvent.RECHARGE);

                messageEvent.setStringValues(rechargeId);

                EventBus.getDefault().post(messageEvent);
                if (dialog!=null){

                    dialog.dismiss();
                }


                break;
            case R.id.tv_cancel:

                if (dialog!=null){

                    dialog.dismiss();
                }
                break;
        }
    }

    public interface onBackClick {

        void onBackClick();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent messageEvent) {

        if (messageEvent.getType().equals("client_info")) {


            String msg = messageEvent.getClientinfo();
            if (TextUtils.isEmpty(msg)) {

                return;
            }
            try {
                JSONObject jsonObject = new JSONObject(msg);

                JSONObject jsonObject1 = jsonObject.getJSONObject("base_info");

                userId = jsonObject1.optString("id");

                userMobile = jsonObject1.optString("mobile");

            } catch (JSONException e) {
                e.printStackTrace();
            }


        }

        if (messageEvent.getType().equals("remove_vip")) {


           userId = "";
        }
    }

}