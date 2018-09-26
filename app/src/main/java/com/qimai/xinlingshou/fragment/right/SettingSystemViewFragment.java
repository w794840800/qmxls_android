package com.qimai.xinlingshou.fragment.right;

import android.app.AlertDialog;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothDevice;
import android.bluetooth.BluetoothSocket;
import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import com.qimai.xinlingshou.App;
import com.qimai.xinlingshou.BaseFragment;
import com.qimai.xinlingshou.R;
import com.qimai.xinlingshou.activity.ChooseStoreActivity;
import com.qimai.xinlingshou.activity.LoginActivity;
import com.qimai.xinlingshou.activity.MainActivity;
import com.qimai.xinlingshou.bean.goodsBean;
import com.qimai.xinlingshou.utils.AidlUtil;
import com.qimai.xinlingshou.utils.DecimalFormatUtils;
import com.qimai.xinlingshou.utils.ESCUtil;
import com.qimai.xinlingshou.utils.Pos;
import com.qimai.xinlingshou.utils.ToastUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import butterknife.BindView;
import butterknife.OnClick;
import butterknife.Unbinder;
import android.app.AlertDialog.Builder;
import android.bluetooth.*;
/**
 * Created by wanglei on 18-5-19.
 */

public class SettingSystemViewFragment extends BaseFragment {
    private static final String TAG = "AAAAAAAAAA";
    @BindView(R.id.tv_exit)
    TextView tvExit;
    @BindView(R.id.store_name)
    TextView storeName;
    @BindView(R.id.change_store)
    TextView changeStore;
    Unbinder unbinder;
    @BindView(R.id.deviceSpinners)
    Spinner mSpinner;

    @BindView(R.id.buttonConnects)
    Button buttonConnect;


    //定义编码方式
    private static String encoding = "gbk";








    //    private Spinner mSpinner=null;
    public List<String> mpairedDeviceList=new ArrayList<String>();
    public ArrayAdapter<String> mArrayAdapter;
    public Builder dialog=null;
    public BluetoothAdapter mBluetoothAdapter = null;   //创建蓝牙适配器
    public BluetoothDevice mBluetoothDevice=null;
    public BluetoothSocket mBluetoothSocket=null;
    OutputStream mOutputStream=null;
    /*Hint: If you are connecting to a Bluetooth serial board then try using
     * the well-known SPP UUID 00001101-0000-1000-8000-00805F9B34FB. However
     * if you are connecting to an Android peer then please generate your own unique UUID.*/
    private static final UUID SPP_UUID = UUID.fromString("00001101-0000-1000-8000-00805F9B34FB");

    Set<BluetoothDevice> pairedDevices=null;


    @Override
    protected void initData() {

        try {
            String store = App.store.getString("storeinfo");
            if (store!=null){
            storeName.setText(new JSONObject(store).getString("name"));}
            else {
                storeName.setText("企小店");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mBluetoothAdapter=BluetoothAdapter.getDefaultAdapter();
//        ButtonListener buttonListener=new ButtonListener();

        dialog=new AlertDialog.Builder(getActivity());
        dialog.setTitle("企迈收银提醒");
        dialog.setMessage(getString(R.string.XPrinterhint));
        dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                startActivity(new Intent(android.provider.Settings.ACTION_SETTINGS));
                //finish();
            }
        });

        dialog.setNeutralButton("No", new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                //finish();
            }
        });

        mpairedDeviceList.add(this.getString(R.string.PlsChoiceDevice));
        mArrayAdapter=new ArrayAdapter<String>(getActivity(), android.R.layout.simple_spinner_dropdown_item,mpairedDeviceList);
        mSpinner.setAdapter(mArrayAdapter);
        mSpinner.setOnTouchListener(new Spinner.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                // TODO Auto-generated method stub
                if (event.getAction()!=MotionEvent.ACTION_UP) {
                    return false;
                }
                try {
                    if (mBluetoothAdapter==null) {
//                        mTipTextView.setText(getString(R.string.NotBluetoothAdapter));
//                        PrintfLogs(getString(R.string.NotBluetoothAdapter));
                    }
                    else if (mBluetoothAdapter.isEnabled()) {
                        String getName=mBluetoothAdapter.getName();
                        pairedDevices=mBluetoothAdapter.getBondedDevices();
                        while (mpairedDeviceList.size()>1) {
                            mpairedDeviceList.remove(1);
                        }
                        if (pairedDevices.size()==0) {
                            dialog.create().show();
                        }
                        for (BluetoothDevice device : pairedDevices) {
                            // Add the name and address to an array adapter to show in a ListView
                            getName=device.getName() + "--" + device.getAddress();
                            mpairedDeviceList.add(getName);
                        }
                    }
                    else {
//                        PrintfLogs("BluetoothAdapter not open...");
                        dialog.create().show();
                    }
                }
                catch (Exception e) {
                    // TODO: handle exception
//                    mprintfData.setText(e.toString());
                }
                return false;
            }
        });



    }

    @Override
    protected void initView(View rootView) {


        EventBus.getDefault().register(this);

    }

    @Override
    protected int getLayout() {
        return R.layout.setting_system_view;
    }


    @OnClick({R.id.tv_exit,R.id.change_store,R.id.buttonConnects})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.tv_exit:
                App.removeUser();
                Intent intent = new Intent(getActivity(), LoginActivity.class);
//                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

                MessageEvent messageEvent = new MessageEvent("change_store");
                EventBus.getDefault().post(messageEvent);
                getActivity().startActivity(intent);
                getActivity().finish();
            break;
        case R.id.change_store:
                Intent intent1 = new Intent(getActivity(), ChooseStoreActivity.class);
               // intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK | Intent.FLAG_ACTIVITY_NEW_TASK);

           /* MessageEvent messageEvent1 = new MessageEvent("change_store");
            EventBus.getDefault().post(messageEvent1);*/

            //getActivity().finish();
                getActivity().startActivity(intent1);
            break;
            case R.id.buttonConnects:
                // ToastUtils.showShortToast("tv_pay_setting");
                String temString=(String) mSpinner.getSelectedItem();
                if (mSpinner.getSelectedItemId()!=0) {
                    if (buttonConnect.getText()!=getString(R.string.Disconnected)) {
                        try {
                            mOutputStream.close();
                            mBluetoothSocket.close();
                            buttonConnect.setText(getString(R.string.Disconnected));
//                    setButtonEnadle(false);
                            buttonConnect.setEnabled(true);
                        } catch (Exception e) {
                            // TODO: handle exception
//                    PrintfLogs(e.toString());
                        }
                        return;
                    }
                    temString=temString.substring(temString.length()-17);
                    try {
                        buttonConnect.setText(getString(R.string.Connecting));
                        mBluetoothDevice=mBluetoothAdapter.getRemoteDevice(temString);
                        mBluetoothSocket=mBluetoothDevice.createRfcommSocketToServiceRecord(SPP_UUID);
                        mBluetoothSocket.connect();
                        mOutputStream=mBluetoothSocket.getOutputStream();

                        buttonConnect.setText(getString(R.string.Connected));
//                        printText("uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu");
//                        printTextNewLine("订 单 号："+"77777777777777777777777777");
//                        printTextNewLine("订 单 号："+"77777777777777777777777777");
//                        printTextNewLine("订 单 号："+"77777777777777777777777777");
//                        printTextNewLine("订 单 号："+"77777777777777777777777777");
//                        printTextNewLine("订 单 号："+"77777777777777777777777777");
//                        printTextNewLine("订 单 号："+"77777777777777777777777777");
//                        printTextNewLine("订 单 号："+"77777777777777777777777777");
//                        printLocation(20, 1);
//                        mOutputStream.write((("jjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjjj"+"\n\r"+"kkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkkk").getBytes("GBK")));
////					mOutputStream.write((mprintfData.getText().toString()+"--->"+getString(R.string.app_name)+getString(R.string.Logs)+"\n").getBytes("GBK"));
//                        printLine(10);
//
//                        mOutputStream.flush();
//                setButtonEnadle(true);
                        buttonConnect.setEnabled(true);
                    } catch (Exception e) {
                        // TODO: handle exception
//                PrintfLogs(getString(R.string.Disconnected));
                        buttonConnect.setText(getString(R.string.Disconnected));
//                setButtonEnadle(false);
                        buttonConnect.setEnabled(true);
//                PrintfLogs(getString(R.string.ConnectFailed)+e.toString());
                    }

                }
                else {
//            PrintfLogs("Pls select a bluetooth device...");
                }

                break;

        }
    }



    @Override
    public void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode=ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent messageEvent){
        try {

            if (messageEvent.getType().equals("ThridFragmentdata")) {
//                Log.d(TAG, "onEvent: ThridFragmentdata= " + messageEvent.);

//                ToastUtils.showLongToast(messageEvent.getThridFragmentdata());
                String store= messageEvent.getThridFragmentdata();
                JSONObject storeinfo = null;
                try {

                    storeinfo = new JSONObject(store);

                    printLocation(1);
                    bold(true);
                    printTabSpace(2);
                    printTextNewLine(storeinfo.getString("name"));


                    printLocation(0);
                    printTextNewLine("销售订单:" +messageEvent.getGood_order());
                    Date date = new Date();
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
//
                    printTextNewLine("交易时间:" + format.format(date));

                    printLocation(0);
                    printTextNewLine("--------------------------------");
                    bold(false);
                    setTextSize(70);
                    printTextNewLine( App.formatStr("商品名称") + " "
                                    + App.formatStr2("数量")+"  单价");

//                    AidlUtil.getInstance().printText(
//                            storeinfo.getString("name"), 2, 50, true,
//                            false, ESCUtil.alignCenter());
//                    Date date = new Date();
//                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault());
//                    AidlUtil.getInstance().printText("销售订单:" + messageEvent.getGoodorder(), 2, 35, false,
//                            false, ESCUtil.alignLeft());
//                    AidlUtil.getInstance().printText("交易时间:" + format.format(date), 1, 35, false,
//                            false, ESCUtil.alignLeft());
//                    AidlUtil.getInstance().printText("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _", 2, 25, false, false,
//                            null);
//                    AidlUtil.getInstance().printLine(1);
//                    AidlUtil.getInstance().printText(
//                            App.formatStr("商品名称") + ""
//                                    + App.formatStr2("数量"), 0, 28, false, false,
//                            ESCUtil.alignLeft());
//
//                    AidlUtil.getInstance().printText(
//                            //formatRight("￥" + jsinfo.getString("price")),
//                            "单价" ,
//                            1, 28, false, false,
//                            ESCUtil.alignLeft());
                    for (Map.Entry<String, goodsBean> entry : messageEvent.getSelectedGoodsMap().entrySet()) {
                        bold(false);
                        setTextSize(70);

                        printTextNewLine( App.formatStr(entry.getValue().getGoodsName()) +"  x"
                                + App.formatStr2(""+entry.getValue().getNumber())+""+DecimalFormatUtils.doubleToMoney2(entry.getValue().getPrice()));
//                        printTextNewLine(
//                                App.formatStr(entry.getValue().getGoodsName()) + " x"
//                                        + App.formatStr2(""+entry.getValue().getNumber()))+ DecimalFormatUtils.doubleToMoney(entry.getValue().getPrice());
                    }
                    printLocation(0);
                    printTextNewLine("--------------------------------");
//                        String namse = entry.getValue().getGoodsName();
//
////                        AidlUtil.getInstance().printText(
////                                App.formatStr(entry.getValue().getGoodsName()) + " x"
////                                        + App.formatStr2(""+entry.getValue().getNumber()), 0, 28, false, false,
////                                ESCUtil.alignLeft());
////
////                        AidlUtil.getInstance().printText(
////                                //formatRight("￥" + jsinfo.getString("price")),
////                                "" + DecimalFormatUtils.doubleToMoney(entry.getValue().getPrice()),
////                                1, 28, false, false,
////                                ESCUtil.alignLeft());
//                        printTextNewLine(App.formatStr(entry.getValue().getGoodsName())+"          "+App.formatStr2(""+entry.getValue().getNumber())+"     "+DecimalFormatUtils.doubleToMoney(entry.getValue().getPrice()));
//                        printLocation(20, 1);
//                    }
//                    AidlUtil.getInstance().printText("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _", 2, 25, false, false,
//                            null);
                    printTextNewLine("合    计：" + messageEvent.getSelectedGoodsMap().size());
                    printTextNewLine("订单金额：" +messageEvent.getTotalMoney());
                    printTextNewLine("实付金额："+messageEvent.getShifu_pay() );
                    printTextNewLine("优惠金额：" +messageEvent.getCheap_money());
                    printTextNewLine("支付方式：" );
                    printLocation(0);
                    printTextNewLine("--------------------------------");
                    printTextNewLine("谢谢您的惠顾，欢迎下次光临！");
////            AidlUtil.getInstance().printLine(1);
//                    AidlUtil.getInstance().printText("合   计：" + messageEvent.getSelectedGoodsMap().size(), 1, 35,
//                            false, false, ESCUtil.alignLeft());
//                    AidlUtil.getInstance().printLine(1);
//                    AidlUtil.getInstance().printText("订单金额：" + DecimalFormatUtils.doubleToMoney(messageEvent.getTotalMoney()), 2, 35,
//                            false, false, ESCUtil.alignLeft());
//                    AidlUtil.getInstance().printText("实付金额：" +
//                                    messageEvent.getShifu_pay(), 1, 35,
//                            false, false, ESCUtil.alignLeft());
//                    AidlUtil.getInstance().printLine(1);
//                    AidlUtil.getInstance().printText("优惠金额："+"¥ "+messageEvent.getCheap_money(), 1, 35,
//                            false, false, ESCUtil.alignLeft());
//                    AidlUtil.getInstance().printLine(1);
//            /*if (moneyBean!=null) {
//                AidlUtil.getInstance().printText("优惠券  ："+
//                                , 2, 35,
//                        false, false, ESCUtil.alignLeft());
//            }else{
//
//                AidlUtil.getInstance().printText("优惠券  ：0", 2, 35,
//                        false, false, ESCUtil.alignLeft());
//            }*/
//                    AidlUtil.getInstance().printText("支付方式："+messageEvent.getType(), 2, 35,
//                            false, false, ESCUtil.alignLeft());
//                    AidlUtil.getInstance().printText("_ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _ _", 1, 25, false, false,
//                            null);
//                    AidlUtil.getInstance().printLine(1);
//                    AidlUtil.getInstance().printText("  谢谢您的惠顾，欢迎下次光临！", 2, 35, false,
//                            false, null);
//
//                    AidlUtil.getInstance().print3Line();
//
//                    AidlUtil.getInstance().cutPrint();
//                    MessageEvent  messageEvent2 = new MessageEvent("allDelete");
//                    EventBus.getDefault().post(messageEvent2);
//                    ((MainActivity)activity).showRightCrashierLayout();
//
////                    uploadDateToServe(mOrderbean);
////
////                    isPaySucess = true;
//
//                    MessageEvent messageEventSucess = new MessageEvent("paySucess");
//                    EventBus.getDefault().post(messageEventSucess);


printLine(5);
                    ((MainActivity)activity).showRightCrashierLayout();
                    //设置左侧整单取消与收款可点击
                    EventBus.getDefault().post(new MessageEvent("cancelCollection"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }catch (Exception e){

        }


    }


    /**
     * 加粗
     *
     * @param flag false为不加粗
     * @return
     * @throws IOException
     */
    public void bold(boolean flag) throws IOException {
        if (flag) {
            //常规粗细
            mOutputStream.write(0x1B);
            mOutputStream.write(69);
            mOutputStream.write(0xF);
            mOutputStream.flush();
        } else {
            //加粗
            mOutputStream.write(0x1B);
            mOutputStream.write(69);
            mOutputStream.write(0);
            mOutputStream.flush();
        }
    }


    /**
     * 打印二维码
     *
     * @param qrData 二维码的内容
     * @throws IOException
     */
    public void qrCode(String qrData) throws IOException {
        int moduleSize = 8;
        int length = qrData.getBytes(encoding).length;

        //打印二维码矩阵
        mOutputStream.write(0x1D);// init
        mOutputStream.write("(k".getBytes("gbk"));// adjust height of barcode
        mOutputStream.write(length + 3); // pl
        mOutputStream.write(0); // ph
        mOutputStream.write(49); // cn
        mOutputStream.write(80); // fn
        mOutputStream.write(48); //
        mOutputStream.write(qrData.getBytes("gbk"));

        mOutputStream.write(0x1D);
        mOutputStream.write("(k".getBytes("gbk"));
        mOutputStream.write(3);
        mOutputStream.write(0);
        mOutputStream.write(49);
        mOutputStream.write(69);
        mOutputStream.write(48);

        mOutputStream.write(0x1D);
        mOutputStream.write("(k".getBytes("gbk"));
        mOutputStream.write(3);
        mOutputStream.write(0);
        mOutputStream.write(49);
        mOutputStream.write(67);
        mOutputStream.write(moduleSize);

        mOutputStream.write(0x1D);
        mOutputStream.write("(k".getBytes("gbk"));
        mOutputStream.write(3); // pl
        mOutputStream.write(0); // ph
        mOutputStream.write(49); // cn
        mOutputStream.write(81); // fn
        mOutputStream.write(48); // m

        mOutputStream.flush();

    }

    /**
     * 进纸并全部切割
     *
     * @return
     * @throws IOException
     */
    public void feedAndCut() throws IOException {
        mOutputStream.write(0x1D);
        mOutputStream.write(86);
        mOutputStream.write(65);
        //    mOutputStream.write(0);
        //切纸前走纸多少
        mOutputStream.write(100);
        mOutputStream.flush();

        //另外一种切纸的方式
        //    byte[] bytes = {29, 86, 0};
        //    socketOut.write(bytes);
    }

    /**
     * 打印换行
     *
     * @return length 需要打印的空行数
     * @throws IOException
     */
    public void printLine(int lineNum) throws IOException {
        for (int i = 0; i < lineNum; i++) {
            mOutputStream.write("\n".getBytes("gbk"));
        }
        mOutputStream.flush();
    }

    /**
     * 打印换行(只换一行)
     *
     * @throws IOException
     */
    protected void printLine() throws IOException {
        mOutputStream.write("\n".getBytes("gbk"));
        mOutputStream.flush();
    }

    /**
     * 打印空白(一个Tab的位置，约4个汉字)
     *
     * @param length 需要打印空白的长度,
     * @throws IOException
     */
    public void printTabSpace(int length) throws IOException {
        for (int i = 0; i < length; i++) {
            mOutputStream.write("\t".getBytes("gbk"));
        }
        mOutputStream.flush();
    }

    /**
     * 打印空白（一个汉字的位置）
     *
     * @param length 需要打印空白的长度,
     * @throws IOException
     */
    public void printWordSpace(int length) throws IOException {
        for (int i = 0; i < length; i++) {
            mOutputStream.write(" ".getBytes("gbk"));
        }
        mOutputStream.flush();
    }

    /**
     * 打印位置调整
     *
     * @param position 打印位置 0：居左(默认) 1：居中 2：居右
     * @throws IOException
     */
    public void printLocation(int position) throws IOException {
        mOutputStream.write(0x1B);
        mOutputStream.write(97);
        mOutputStream.write(position);
        mOutputStream.flush();
    }

    /**
     * 绝对打印位置
     *
     * @throws IOException
     */
    public void printLocation(int light, int weight) throws IOException {
        mOutputStream.write(0x1B);
        mOutputStream.write(0x24);
        mOutputStream.write(light);
        mOutputStream.write(weight);
        mOutputStream.flush();
    }
    /**
     * 打印文字
     *
     * @param text
     * @throws IOException
     */
    public void printText(String text) throws IOException {
        String s = text;
        byte[] content = s.getBytes("gbk");
        mOutputStream.write(content);
        fontSizeSetBig(2);
        mOutputStream.flush();
    }
    /**
     * 新起一行，打印文字
     *
     * @param text
     * @throws IOException
     */
    public void printTextNewLine(String text) throws IOException {
        //换行
        mOutputStream.write("\n".getBytes("gbk"));
        mOutputStream.flush();
        fontSizeSetBig(2);
        String s = text;
        byte[] content = s.getBytes("gbk");
        mOutputStream.write(content);
        mOutputStream.flush();
    }

    /**
     * 设置字体大小
     * */

    protected void setTextSize(int size)throws IOException{
        mOutputStream.write(CMD_FontSize(size).getBytes("gbk"));
        mOutputStream.flush();
    }
    // / 字体的大小
    // / </summary>
    // / <param name="nfontsize">0:正常大小 1:两倍高 2:两倍宽 3:两倍大小 4:三倍高 5:三倍宽 6:三倍大小
    // 7:四倍高 8:四倍宽 9:四倍大小 10:五倍高 11:五倍宽 12:五倍大小</param>
    // / <returns></returns>
    public String CMD_FontSize(int nfontsize) {
        String _cmdstr = "";
        // 设置字体大小
        switch (nfontsize) {
            case -1:
                _cmdstr = new StringBuffer().append((char) 29).append((char) 33)
                        .append((char) 0).toString();// 29 33
                break;

            case 0:
                _cmdstr = new StringBuffer().append((char) 29).append((char) 33)
                        .append((char) 0).toString();// 29 33
                break;

            case 1:
                _cmdstr = new StringBuffer().append((char) 29).append((char) 33)
                        .append((char) 1).toString();
                break;

            case 2:
                _cmdstr = new StringBuffer().append((char) 29).append((char) 33)
                        .append((char) 16).toString();
                break;

            case 3:
                _cmdstr = new StringBuffer().append((char) 29).append((char) 33)
                        .append((char) 17).toString();
                break;

            case 4:
                _cmdstr = new StringBuffer().append((char) 29).append((char) 33)
                        .append((char) 2).toString();
                break;

            case 5:
                _cmdstr = new StringBuffer().append((char) 29).append((char) 33)
                        .append((char) 32).toString();
                break;

            case 6:
                _cmdstr = new StringBuffer().append((char) 29).append((char) 33)
                        .append((char) 34).toString();
                break;

            case 7:
                _cmdstr = new StringBuffer().append((char) 29).append((char) 33)
                        .append((char) 3).toString();
                break;

            case 8:
                _cmdstr = new StringBuffer().append((char) 29).append((char) 33)
                        .append((char) 48).toString();
                break;

            case 9:
                _cmdstr = new StringBuffer().append((char) 29).append((char) 33)
                        .append((char) 51).toString();
                break;

            case 10:
                _cmdstr = new StringBuffer().append((char) 29).append((char) 33)
                        .append((char) 4).toString();
                break;

            case 11:
                _cmdstr = new StringBuffer().append((char) 29).append((char) 33)
                        .append((char) 64).toString();
                break;

            case 12:
                _cmdstr = new StringBuffer().append((char) 29).append((char) 33)
                        .append((char) 68).toString();
                break;

        }
        return _cmdstr;
    }
    /**
     * 字体变大为标准的n倍
     *
     * @param num
     * @return
     */
    public static byte[] fontSizeSetBig(int num) {
        byte realSize = 0;
        switch (num) {
            case 1:
                realSize = 0;
                break;
            case 2:
                realSize = 17;
                break;
            case 3:
                realSize = 34;
                break;
            case 4:
                realSize = 51;
                break;
            case 5:
                realSize = 68;
                break;
            case 6:
                realSize = 85;
                break;
            case 7:
                realSize = 102;
                break;
            case 8:
                realSize = 119;
                break;
        }
        byte[] result = new byte[3];
        result[0] = 29;
        result[1] = 33;
        result[2] = realSize;
        return result;
    }


}
