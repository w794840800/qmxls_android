package com.qimai.xinlingshou.dialog;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;

import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

public class DialogUtils {

    private static  ZLoadingDialog dialog;


    public static void createDialog(Context context){

        if (context instanceof Activity){


            if (dialog!=null){
                dialog.dismiss();
            }
            dialog = new ZLoadingDialog(context);

            dialog.setCancelable(false);
            dialog.setCanceledOnTouchOutside(false);
            dialog.setLoadingBuilder(Z_TYPE.STAR_LOADING)//设置类型
                    .setLoadingColor(Color.BLACK)//颜色
                    .setHintText("Loading...")
                    .show();
        }

    }

    public static void cancelDialog(){

        if (dialog!=null){

            dialog.dismiss();
        }

    }


}
