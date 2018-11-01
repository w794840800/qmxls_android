package com.qimai.xinlingshou.utils;

import android.Manifest;
import android.content.Context;
import android.content.pm.PackageManager;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.Log;

import com.qimai.xinlingshou.App;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Created by NIU on 2018/5/3.
 */

public class DeviceUtils {


    public static String getDeviceId() {

        String id;
        //android.telephony.TelephonyManager
        TelephonyManager mTelephony = (TelephonyManager)App.getBaseApplication().getSystemService(Context.TELEPHONY_SERVICE);
        if (ContextCompat.checkSelfPermission(App.getBaseApplication(), Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.

     //   ToastUtils.showToast("没有读取内容权限",Toast.LENGTH_LONG);

        }
        if (mTelephony.getDeviceId() != null) {
            id = mTelephony.getDeviceId();
        } else {
            //android.provider.Settings;
            id = Settings.Secure.getString(App.getBaseApplication().getApplicationContext().getContentResolver(), Settings.Secure.ANDROID_ID);
        }
            return id;
        }



        /**
         *
         * 得到设备sn号
         * **/

        public static String getDeviceSN(){


        String SN = "";
            Class c = null;
            try {
                c = Class.forName("android.os.SystemProperties");


                Method get = c.getMethod("get", String.class);
                Log.i("sunmi", "the sn:" + (String) get.invoke(c, "ro.serialno"));




                SN = (String) get.invoke(c, "ro.serialno");
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (NoSuchMethodException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }


            return SN;
        }
}
