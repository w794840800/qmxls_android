package com.qimai.xinlingshou.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;

import com.qimai.xinlingshou.App;

import org.litepal.LitePal;
import org.w3c.dom.Text;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.channels.FileChannel;

public class DBCopyUtils {

    private static final String TAG = "DBCopyUtils";
    public static void copyDataBaseToSD(){

        FileWriter fileWriter = null;
        BufferedWriter bfw = null;
        String dbName = "temp";
        if (!Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {

            Log.d(TAG, "copyDataBaseToSD: error");
            return ;

    }


        File dbFile = new File(App.getBaseApplication().getDatabasePath("qimai_xls")+".db");

        File File = new File(App.getBaseApplication().getDatabasePath("qimai_xls")+".db");

        try {

            if (!dbFile.exists()){

                return;
            }

            Class c = Class.forName("android.os.SystemProperties");
            Method get = c.getMethod("get", String.class);
            Log.i("sunmi", "the sn:" + (String) get.invoke(c, "ro.serialno"));




            String temp = (String) get.invoke(c, "ro.serialno");

            if (!TextUtils.isEmpty(temp)){

                dbName = temp;

            }else{

                //dbName = System.currentTimeMillis()+"";

                /*String deviceId = DeviceUtils.getDeviceId();
                if (!TextUtils.isEmpty(deviceId)){

                    dbName = temp;
                }else{
                    dbName = System.currentTimeMillis()+"";

                }*/

            }



            /*Log.i("sunmi", "First four characters:"
                    + ((String) get.invoke(c, "ro.serialno")).substring(0, 4));*/
        } catch (Exception e) {
            Log.d(TAG, "copyDataBaseToSD: e= "+e.getMessage());
            e.printStackTrace();
        }


        Log.d(TAG, "copyDataBaseToSD: dbName= "+dbName);
        File file  = new File(Environment.getExternalStoragePublicDirectory(Environment
        .DIRECTORY_DOWNLOADS), dbName+".db");


        File filecsv  = new File(Environment.getExternalStoragePublicDirectory(Environment
                .DIRECTORY_DOWNLOADS), dbName+".csv");

        FileChannel inChannel = null,outChannel = null;


        try {



            String head = "id,store_id,actual_input_money,amount,card_id,card_minus,changetype,charge_money,coupon_id,coupon_minus,create_time,"+
                    "finish_time,goodsid,goodsname,goodspic,goodsimg,huiyuan,isauto,ispay,leavebalance,minus_amount," +
                    "number,order_lpcolumn,orderinfo,pay_time,payment_id,price,seller_remarks,server_order_no," +
                    "status,totalbalance,total_amount,trade_no,unit,use_wallet,user_remarks,userid,wallet_amount";


           String []headContent = head.split(",");
            file.createNewFile();
            filecsv.createNewFile();
            inChannel = new FileInputStream(dbFile).getChannel();
            outChannel = new FileOutputStream(file).getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
            Log.d(TAG, "copyDataBaseToSD: sucess");

            fileWriter = new FileWriter(filecsv);

            bfw = new BufferedWriter(fileWriter);

            bfw.write(headContent+"\n");


            SQLiteDatabase sqLiteDatabase  = App.getBaseApplication().openOrCreateDatabase("qimai_xls.db", Context.MODE_PRIVATE,null);

            //Cursor cursor = sqLiteDatabase.query("ordersbean",)

           Cursor cursor =  LitePal.getDatabase().rawQuery("select\n" +
                    "    id ,store_id,actual_input_money , amount , card_id , card_minus , changetype , charge_money , coupon_id , coupon_minus , create_time , \n" +
                    "    finish_time , goodsid , goodsname , goodspic , goodsimg , huiyuan , isauto , ispay , leavebalance , minus_amount ,\n" +
                    "    number , order_lpcolumn , orderinfo , pay_time , payment_id , price , seller_remarks , server_order_no ,\n" +
                    "    status , totalbalance , total_amount , trade_no , unit , use_wallet , user_remarks , userid , wallet_amount from ordersbean",null);


            Log.d(TAG, "copyDataBaseToSD: cursor size= "+cursor.getCount());
            //SQLiteDatabase.openDatabase()
            if (cursor!=null&&cursor.getCount()>0){


                for (int i = 0; i < headContent.length; i++) {


                 //   cursor.moveToPosition(i);




                }

            }

            FileInputStream in = new FileInputStream(file);
           new Thread(new Runnable() {
               @Override
               public void run() {
                   boolean isUpload = FtpUtil.uploadFile("119.29.25.121",221,"dataftp"
                           ,"Data2018@#%","select_enable_disenable",null,file.getName(),in);

                   Log.d(TAG, "copyDataBaseToSD: isUpload= "+isUpload);


               }
           }).start();

        }
        catch (Exception e) {
            Log.d(TAG, "copyDataBaseToSD: failed= "+e.getMessage());

            //LogUtils.e(TAG, "copy dataBase to SD error.");
            e.printStackTrace();
        }finally{
            try {
                if (inChannel != null) {
                    inChannel.close();
                    inChannel = null;
                }
                if(outChannel != null){
                    outChannel.close();
                    outChannel = null;
                }			}
                catch (IOException e) {
                //LogUtils.e(TAG, "file close error.");
                e.printStackTrace();
            }
        }





}


}
