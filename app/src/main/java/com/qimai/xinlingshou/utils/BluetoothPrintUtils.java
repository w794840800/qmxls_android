package com.qimai.xinlingshou.utils;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;

import java.io.OutputStream;

public class BluetoothPrintUtils {

    public static BluetoothAdapter mBluetoothAdapter = null;   //创建蓝牙适配器

    public OutputStream mOutputStream = null;
    public static BluetoothDevice mBluetoothDevice = null;
    public static BluetoothSocket mBluetoothSocket = null;
    public static void connectBluetooth(){

        mBluetoothAdapter = BluetoothAdapter.getDefaultAdapter();


    }
}
