package com.qimai.xinlingshou.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

public class NetWorkUtils {

    //public static boolean isNetWork

    public static boolean isNetWorkAvaiable(Context context){


        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);


        if (connectivityManager==null){

            return false;
        }else{


            NetworkInfo[]networkInfos = connectivityManager.getAllNetworkInfo();

            if (networkInfos!=null){

                for (int i = 0; i <networkInfos.length ; i++) {

                    if (networkInfos[i].getState()==NetworkInfo.State.CONNECTED){

                        return true;
                    }

                }
            }



        }

        return false;
    }


}
