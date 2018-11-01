package com.qimai.xinlingshou.fragment.left;

import android.annotation.SuppressLint;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.qimai.xinlingshou.BaseFragment;
import com.qimai.xinlingshou.R;
import com.qimai.xinlingshou.activity.MainActivity;

import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import android.content.DialogInterface;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import android.app.AlertDialog.Builder;
/**
 * Created by wanglei on 18-5-19.
 */

public class Left_setting_fragment extends BaseFragment {

    private static final String TAG = "Left_setting_fragment";
    @BindView(R.id.tv_pay_setting)
    TextView tvPaySetting;
    @BindView(R.id.tv_system_setting)
    TextView tvSystemSetting;
    @BindView(R.id.view_system_setting)
    View viewSystemSetting;
    @BindView(R.id.view_pay_setting)


    View viewPaySetting;
    Unbinder unbinder;

    @Override
    protected void initData() {
    }

    @Override
    protected void initView(View rootView) {


       /* rootView.findViewById(R.id.tv_system_setting)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ToastUtils.showShortToast("select_enable_disenable");
                    }
                });*/
    }

    @Override
    protected int getLayout() {

        return R.layout.setting_item_fragment;
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // TODO: inflate a fragment view
        View rootView = super.onCreateView(inflater, container, savedInstanceState);
        unbinder = ButterKnife.bind(this, rootView);
        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @OnClick({R.id.tv_system_setting, R.id.view_system_setting, R.id.tv_pay_setting, R.id.view_pay_setting })
    public void onViewClicked(View view) {
        switch (view.getId()) {


            case R.id.tv_system_setting:

                //ToastUtils.showShortToast("tv_system_setting");
                ((MainActivity) activity).showSettingSystemViewLayout();

                tvSystemSetting.setTextColor(getResources().getColor(R.color.TvDefault));

                viewSystemSetting.setBackgroundColor(getResources().getColor(R.color.TvBlue));
                tvPaySetting.setTextColor(getResources().getColor(R.color.TvBlack));
                viewPaySetting.setBackgroundColor(getResources().getColor(R.color.TvGray));

                break;
            case R.id.tv_pay_setting:
                // ToastUtils.showShortToast("tv_pay_setting");

                Log.d(TAG, "onViewClicked: tv_pay_setting= ");
                ((MainActivity) activity).showSettingPayViewLayout();
                tvPaySetting.setTextColor(getResources().getColor(R.color.TvBlue));
                viewPaySetting.setBackgroundColor(getResources().getColor(R.color.TvBlue));
                tvSystemSetting.setTextColor(getResources().getColor(R.color.TvBlack));
                viewSystemSetting.setBackgroundColor(getResources().getColor(R.color.TvGray));
                break;


        }
    }
}
