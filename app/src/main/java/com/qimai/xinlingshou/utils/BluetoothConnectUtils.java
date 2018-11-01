package com.qimai.xinlingshou.utils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.Context;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class BluetoothConnectUtils {

   static BluetoothConnectUtils bluetoothConnectUtils;
    public BluetoothDevice mBluetoothDevice = null;
    private static final UUID SPP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    BluetoothAdapter mBluetoothAdapter;
    Set<BluetoothDevice> pairedDevices = null;
    //设备列表
    public List<String> mpairedDeviceList;

    boolean isConnect;
    public Context context;
    public OutputStream mOutputStream = null;
    public BluetoothSocket mBluetoothSocket = null;

    public BluetoothConnectUtils() {

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


        pairedDevices = mBluetoothAdapter.getBondedDevices();


        mpairedDeviceList = new ArrayList<String>();


        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


        pairedDevices = mBluetoothAdapter.getBondedDevices();





    }


    public static BluetoothConnectUtils getInstance(){


        if (bluetoothConnectUtils==null){

            bluetoothConnectUtils = new BluetoothConnectUtils();
        }
        return bluetoothConnectUtils;
    }


    public boolean connectPrint(String address){

        mBluetoothDevice = mBluetoothAdapter.getRemoteDevice(address);
        try {
            mBluetoothSocket = mBluetoothDevice.createRfcommSocketToServiceRecord(SPP_UUID);
            mBluetoothSocket.connect();
            mOutputStream = mBluetoothSocket.getOutputStream();

            isConnect = true;


        } catch (IOException e) {
            e.printStackTrace();
            isConnect = false;
        }


        return isConnect;
    }




    public List<String>getDeviceList(){

        if (pairedDevices!=null){
        for (BluetoothDevice device : pairedDevices) {
            // Add the name and address to an array adapter to show in a ListView
            String getName = device.getName() + "--" + device.getAddress();
            mpairedDeviceList.add(getName);

            }
        }

        return mpairedDeviceList;
    }


    public boolean getBlueToothEnable(){


        if (mBluetoothAdapter!=null){

            return mBluetoothAdapter.isEnabled();
        }
        return false;


    }

    public void disconnect(){

        if (mOutputStream!=null){

            try {
                mOutputStream.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        if (mBluetoothSocket!=null){

            try {
                mBluetoothSocket.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }


    }

}
