package com.qimai.xinlingshou.dialog;

import android.app.Dialog;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qimai.xinlingshou.R;
import com.qimai.xinlingshou.callback.BlancePayCallBack;
import com.qimai.xinlingshou.fragment.right.MessageEvent;
import com.qimai.xinlingshou.model.PayModel;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class BaseDialogFragment extends DialogFragment {

    Dialog progressDialog ;
    //只是余额支付
    public static final int BALANCE_PAY = 1;

    //混合支付
    public static final int BALANCE_MULTIP_PAY = 2;

    protected BlancePayCallBack blancePayCallBack;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        createProgressDialog();

        EventBus.getDefault().register(this);
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    public void setBlancePayCallBack(BlancePayCallBack blancePayCallBack) {
        this.blancePayCallBack = blancePayCallBack;
    }


    public void createProgressDialog() {
        progressDialog = new Dialog(getActivity(),R.style.CustomDialog);

        progressDialog.setContentView(R.layout.progress_bar);
        progressDialog.getWindow().setBackgroundDrawable(null);
    }

     void NotificationClientInfoUpdate(String userId) {


        MessageEvent messageEvent = new MessageEvent(MessageEvent.BALANCEPAYFINISH);

        messageEvent.setStringValues(userId);

        EventBus.getDefault().post(messageEvent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();

        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent messageEvent){




    }
}
