package com.qimai.xinlingshou.dialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.qimai.xinlingshou.R;

public class ReChargeDialogFragment extends DialogFragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setStyle(DialogFragment.STYLE_NO_TITLE, R.style.CustomDialog1);

        getDialog().setCanceledOnTouchOutside(false);

        getDialog().setCancelable(false);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {

       View view = inflater.inflate(R.layout.dialog_recharge,container,false);
        return view;
    }
}
