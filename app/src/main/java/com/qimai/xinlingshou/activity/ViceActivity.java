package com.qimai.xinlingshou.activity;

import android.app.Activity;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.qimai.xinlingshou.R;
import com.qimai.xinlingshou.adapter.GoodsSelectAdapter;
import com.qimai.xinlingshou.adapter.ViceGoodsSelectAdapter;
import com.qimai.xinlingshou.bean.MoneyBean;
import com.qimai.xinlingshou.bean.ReceiverInfo;
import com.qimai.xinlingshou.bean.SecondScreenInfo;
import com.qimai.xinlingshou.bean.VipInfo;
import com.qimai.xinlingshou.bean.goodsBean;
import com.qimai.xinlingshou.fragment.right.MessageEvent;
import com.qimai.xinlingshou.utils.Data;
import com.qimai.xinlingshou.utils.DecimalFormatUtils;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Action;
import io.reactivex.schedulers.Schedulers;
import sunmi.ds.DSKernel;
import sunmi.ds.FilesManager;
import sunmi.ds.callback.IConnectionCallback;
import sunmi.ds.callback.IReceiveCallback;
import sunmi.ds.data.DSData;
import sunmi.ds.data.DSFile;
import sunmi.ds.data.DSFiles;

public class ViceActivity extends AppCompatActivity {
    private final String TAG = ViceActivity.class.getSimpleName();
    @BindView(R.id.tv_balance)
    TextView tvBalance;


    //在准备付钱的时候赋值
    private SecondScreenInfo paySecondScreenInfo;
    Disposable cutDownDispose;
    ViceGoodsSelectAdapter goodsSelectAdapter;
    ArrayList<goodsBean> selectedGoodsArrayList;
    SecondScreenInfo secondScreenInfo;

    @BindView(R.id.tv_status)
    TextView tvStatus;
    @BindView(R.id.tv_zhekou)
    TextView tvZhekou;
    @BindView(R.id.rl_youhui)
    RelativeLayout rlYouhui;
    @BindView(R.id.tv_vip_zhekou)
    TextView tvVipZhekou;
    @BindView(R.id.rl_vip_youhui)
    RelativeLayout rlVipYouhui;
    @BindView(R.id.tv_total_pay)
    TextView tvTotalPay;
    @BindView(R.id.tv_actual_pay)
    TextView tvActualPay;
    @BindView(R.id.rl_actual_pay)
    RelativeLayout rlActualPay;
    @BindView(R.id.tv_zhaoling)
    TextView tvZhaoling;
    @BindView(R.id.rl_zhaoling)
    RelativeLayout rlZhaoling;
    @BindView(R.id.rl_need_pay)
    RelativeLayout rlNeedPay;


    ReceiverInfo<SecondScreenInfo> receiverInfo;
    String more = ",\"dataModel\":\"TEXT\"";
    @BindView(R.id.ll_top)
    RelativeLayout llTop;

    //商品总价 不包括优惠券啥的
    @BindView(R.id.tv_total_money)
    TextView tvTotalMoney;
    @BindView(R.id.ll_bottom_menu)
    LinearLayout llBottomMenu;
    @BindView(R.id.ll_empty_goods)
    LinearLayout llEmptyGoods;
    @BindView(R.id.rv_select_goods)
    RecyclerView rvSelectGoods;
    @BindView(R.id.ll_goods_items)
    RelativeLayout llGoodsItems;
    @BindView(R.id.ll_left_container)
    LinearLayout llLeftContainer;
    @BindView(R.id.ll_pay_sucess)
    LinearLayout llPaySucess;
    @BindView(R.id.tv_vip_name)
    TextView tvVipName;
    @BindView(R.id.tv_vip_phone)
    TextView tvVipPhone;
    @BindView(R.id.ll_vip_info)
    LinearLayout llVipInfo;
    //双屏通讯帮助类
    private DSKernel mDSKernel = null;
    //文件管理帮助类
    private FilesManager mFilesManager;

    private Handler myHandler;
    private Gson gson = new Gson();
    private Intent intent = new Intent();

    //DataReceiver dataReceiver;
    IntentFilter intentFilter;
    private IConnectionCallback mIConnectionCallback = new IConnectionCallback() {
        @Override
        public void onDisConnect() {
            Message message = new Message();
            message.what = 1;
            message.obj = getString(R.string.unconnect_main_service);
            myHandler.sendMessage(message);
        }

        @Override
        public void onConnected(ConnState state) {
            Message message = new Message();
            message.what = 1;
            switch (state) {
                case AIDL_CONN:
                    message.obj = getString(R.string.connect_main_service);
                    break;
                case VICE_SERVICE_CONN:
                    message.obj = getString(R.string.connect_vice_service);
                    break;
                case VICE_APP_CONN:
                    message.obj = getString(R.string.connect_vice_dsd);
                    break;
                default:
                    break;
            }
            myHandler.sendMessage(message);
        }
    };

    /**
     * 双屏通讯消息回调
     */
    private IReceiveCallback mIReceiveCallback = new IReceiveCallback() {
        @Override
        public void onReceiveData(DSData data) {
            Log.d(TAG, "onReceiveData: ---------->" + data.data.replace(more, ""));

            Gson gson = new Gson();
            //receiverInfo = gson.fromJson(data.data,ReceiverInfo.class);

            try {
                JSONObject jsonObject = new JSONObject(data.data);

                String json1 = jsonObject.getString("data");
                secondScreenInfo = gson.fromJson(json1, SecondScreenInfo.class);

                Log.d(TAG, "onReceiveData: ");


            } catch (JSONException e) {
                e.printStackTrace();
            }
            Log.d(TAG, "onReceiveData: secondScreenInfo= " + secondScreenInfo.toString());

            if (secondScreenInfo.getType().equals(SecondScreenInfo.UPDATE)) {

                tvStatus.setText("企小店欢迎光临");
                hideView(rlVipYouhui);
                hideView(rlYouhui);
                hideView(rlZhaoling);
                hideView(rlActualPay);
                if (cutDownDispose != null && !cutDownDispose.isDisposed()) {

                    cutDownDispose.dispose();
                }
                llLeftContainer.setVisibility(View.VISIBLE);

                rvSelectGoods.setVisibility(View.VISIBLE);
                llPaySucess.setVisibility(View.GONE);

                updateUI(secondScreenInfo);


                //只用来更新优惠和会员折扣
                updateBill(secondScreenInfo.getMoneyBean());

            } else if (secondScreenInfo.getType().equals(SecondScreenInfo.PAYSUCESS)) {


                llLeftContainer.setVisibility(View.VISIBLE);
                tvStatus.setText("收款成功 欢迎下次光临");
                rvSelectGoods.setVisibility(View.GONE);
                llPaySucess.setVisibility(View.VISIBLE);


                hideView(rlVipYouhui);
                hideView(rlYouhui);
                hideView(rlNeedPay);
                hideView(rlZhaoling);
                hideView(rlActualPay);


                if (paySecondScreenInfo != null) {

                    // tvTotalMoney.setText(DecimalFormatUtils.doubleToMoney());

                    setTextAndVisibity(tvTotalMoney, Double.parseDouble(paySecondScreenInfo.getTotalActualPay()));
                    if (secondScreenInfo.getPayMethod() == 1) {

                        setTextAndVisibity(tvActualPay, Double.parseDouble(secondScreenInfo.getAcual_collect_money()));

                        setTextAndVisibity(tvZhaoling, Double.parseDouble(secondScreenInfo.getZhaoling()));

                    } else {

                        setTextAndVisibity(tvActualPay, Double.parseDouble(paySecondScreenInfo.getTotalActualPay()));


                    }


                }
                //setTextAndVisibity(tvTotalMoney,Double.parseDouble(secondScreenInfo.getUser_need_pay()));

               /* if (secondScreenInfo.getPayMethod()==1) {
                    setTextAndVisibity(tvActualPay, Double.parseDouble(secondScreenInfo.getAcual_collect_money()));
                    setTextAndVisibity(tvZhaoling, Double.parseDouble(secondScreenInfo
                            .getZhaoling()
                    ));
                }else{


                    setTextAndVisibity(tvActualPay,
                            Double.parseDouble(secondScreenInfo.getTotalActualPay()));

                }*/
                cutDownDispose = beginCutDown();


            } else if (secondScreenInfo.getType().equals(SecondScreenInfo.ADD_VIP_INFO)) {

                VipInfo vipInfo = secondScreenInfo.getVipInfo();
                if (vipInfo != null) {
                    //secondScreenInfo.getVipInfo();
                    llVipInfo.setVisibility(View.VISIBLE);
                    tvVipName.setText(vipInfo.getName());
                    tvVipPhone.setText(vipInfo.getMobile());

                    if (TextUtils.isEmpty(vipInfo.getAccount())){

                        tvBalance.setVisibility(View.GONE);
                    }else {
                        tvBalance.setVisibility(View.VISIBLE);
                        tvBalance.setText(vipInfo.getAccount() + "元");
                    }
                }


            } else if (secondScreenInfo.getType().equals(SecondScreenInfo.REMOVE_VIP_INFO)) {

                llVipInfo.setVisibility(View.GONE);

            } else if (secondScreenInfo.getType().equals(SecondScreenInfo.BEGINPAY)) {

                paySecondScreenInfo = secondScreenInfo;
                tvStatus.setText("请出示支付宝/微信二维码\n\n请仔细核对收款金额");

            } else if (secondScreenInfo.getType().equals(SecondScreenInfo.CANCELPAY)) {

                tvStatus.setText("企小店欢迎光临");
                paySecondScreenInfo = new SecondScreenInfo();

            } else if (secondScreenInfo.getType().equals(SecondScreenInfo.PENDINGORDERSUCESS)) {

                llLeftContainer.setVisibility(View.GONE);
            }


        }

        @Override
        public void onReceiveFile(DSFile file) {
            Log.d(TAG, "onReceiveFile: file= " + file.path);


        }

        @Override
        public void onReceiveFiles(DSFiles files) {

            Log.d(TAG, "onReceiveFiles: files= ");
        }

        @Override
        public void onReceiveCMD(DSData cmd) {
            Log.d(TAG, "onReceiveCMD: ------------------->" + cmd.data);
            mFilesManager = FilesManager.getInstance();
            Log.d(TAG, "onReceiveCMD: ------------>1111111111111");
            Data data = gson.fromJson(cmd.data, Data.class);
            Log.d(TAG, "onReceiveCMD: ------------------->" + data.dataModel);
            switch (data.dataModel) {
                //副屏显示单张图片
                case SHOW_IMG_WELCOME:
//                    intent.setClass(ViceActivity.this, PictureActivity.class);
//                    intent.putExtra("path", data.data);
//                    startActivity(intent);
                    break;
                //播放单个视频
                case VIDEO:
                    String path = mFilesManager.getFile(cmd.fileId).path;
//                    intent.setClass(ViceActivity.this, VideoActivity.class);
//                    intent.putExtra("path", path);
//                    intent.putExtra("FILEID", cmd.fileId);
//                    intent.putExtra("DATA", data.data);
//                    intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//                    startActivity(intent);
                    break;
                //播放多个视频文件
                case VIDEOS:
                    DSFiles videosFile = mFilesManager.getFiles(cmd.fileId);
                    ArrayList<String> videoFiles = new ArrayList<>();
                    for (DSFile dsFile : videosFile.files) {
                        videoFiles.add(dsFile.path);
                    }
//                    intent.setClass(ViceActivity.this, VideosActivity.class);
//                    intent.putStringArrayListExtra("paths", videoFiles);
//                    intent.putExtra("FILEID", cmd.fileId);
//                    intent.putExtra("DATA", data.data);
//                    startActivity(intent);
                    break;
                //显示轮播图
                case IMAGES:
//                    DSFiles dsFiles1 = mFilesManager.getFiles(cmd.fileId);
//                    String msg1 = dsFiles1.filesDescribe.msg;
//                    List<DSFile> paths = dsFiles1.files;
//                    List<String> imgPaths = new ArrayList<>();
//                    for (DSFile dsFile : paths) {
//                        imgPaths.add(dsFile.path);
//                    }
//                    intent.setClass(ViceActivity.this, ImgsActivity.class);
//                    intent.putExtra("json", msg1);
//                    intent.putStringArrayListExtra("paths", (ArrayList<String>) imgPaths);
//                    startActivity(intent);
                    break;
                case CLEAN_FILES:
                    Log.d(TAG, "delete file is ----->" + data.data);
//                    FileUtils.deleteDir(data.data);
                    break;
                case GETVICECACHEFILESIZE://获取副屏缓存文件大小
                    Log.d(TAG, "获取副屏缓存文件大小----->" + data.data);
                    long size = 0L;
                    File file = new File(data.data);
                    if (file.exists()) {
                        size = getFilesSize(file);
                    }
                    mDSKernel.sendResult(cmd.sender, String.valueOf(size), cmd.taskId, null);
                    break;
                default:
                    break;
            }
        }
    };

    private void showView(RelativeLayout rlActualPay) {
    }

    private void hideView(RelativeLayout view) {
        if (view != null) {

            view.setVisibility(View.GONE);
        }
    }


    /**
     * @Function 只用来更新优惠和会员折扣
     **/
    private void updateBill(MoneyBean moneyBean) {


        Log.d(TAG, "updateBill: moneyBean= " + (moneyBean == null));
        if (moneyBean == null) {

            rlVipYouhui.setVisibility(View.GONE);
            rlYouhui.setVisibility(View.GONE);
            return;

        }

        //更新会员卡的折扣
        setTextAndVisibity(tvVipZhekou, moneyBean.getVipDiscount());

        //更新折扣
        setTextAndVisibity(tvZhekou, moneyBean.getMianzhiOrZhrkouDiscount());


    }

    private void setTextAndVisibity(TextView tvVipYouhui, double vipDiscount) {

        if (vipDiscount != 0) {

            tvVipYouhui.setText(DecimalFormatUtils.doubleToMoney(vipDiscount));
            ((ViewGroup) tvVipYouhui.getParent()).setVisibility(View.VISIBLE);

        } else {

            ((ViewGroup) tvVipYouhui.getParent()).setVisibility(View.GONE);

        }

    }

    private Disposable beginCutDown() {

        return Observable.intervalRange(0, 10, 0, 1, TimeUnit.SECONDS)

                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnComplete(new Action() {
                    @Override

                    public void run() throws Exception {

                        tvStatus.setText("企小店欢迎光临");

                        llLeftContainer.setVisibility(View.GONE);

                    }
                })
                .subscribe();

    }

    private void updateUI(SecondScreenInfo secondScreenInfo) {


        if (secondScreenInfo != null) {


            setTextAndVisibity(tvTotalMoney, Double.parseDouble(secondScreenInfo.getTotalCostMoney()));
            //tvTotalMoney.setText(DecimalFormatUtils.stringToMoney(secondScreenInfo.getTotalCostMoney()));
//            tvTotalMoney.setText((DecimalFormatUtils.stringToMoney(secondScreenInfo.getTotalCostMoney())));

            setTextAndVisibity(tvTotalPay, Double.parseDouble(secondScreenInfo.getTotalActualPay()));
            //tvTotalPay.setText((DecimalFormatUtils.stringToMoney(secondScreenInfo.getTotalActualPay())));
            selectedGoodsArrayList = new ArrayList<>(secondScreenInfo.getGoodsBeanList());

            Log.d(TAG, "updateUI: selectedGoodsArrayList=null " + (selectedGoodsArrayList == null));
            if (goodsSelectAdapter == null) {

                goodsSelectAdapter = new ViceGoodsSelectAdapter(this, selectedGoodsArrayList);
                goodsSelectAdapter.updateData(selectedGoodsArrayList);

                rvSelectGoods.setAdapter(goodsSelectAdapter);
            } else {

                goodsSelectAdapter.updateData(selectedGoodsArrayList);
            }


        }


    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vice2);
        EventBus.getDefault().register(this);
        ButterKnife.bind(this);
        initSdk();
        initData();
        initView();

        Log.d(TAG, "onCreate: Environment.getDataDirectory()=" +
                " " + Environment.getDataDirectory() + " Environment.getDownloadCacheDirectory()= " + Environment.getDownloadCacheDirectory()
                + "  "
        );

        Log.d(TAG, "onCreate: " + "  Environment.getExternalStorageState()= " + Environment.getExternalStorageState());


        Log.d(TAG, "onCreate: Environment.getExternalStoragePublicDirectory(DOWNLOAD_SERVICE)= " + Environment.getExternalStoragePublicDirectory(DOWNLOAD_SERVICE));


        Log.d(TAG, "onCreate: getApplicationContext().getExternalCacheDir()= " + getApplicationContext().getExternalCacheDir());


        Log.d(TAG, "onCreate: " + getApplicationContext().getExternalCacheDir().getPath() + "" +
                "" +
                "" +
                "  " + getApplicationContext().getExternalCacheDir().getAbsolutePath());

        // bindService(intent,serviceConnection,BIND_AUTO_CREATE);


        // dataReceiver = new DataReceiver();
        intentFilter = new IntentFilter();
        intentFilter.addAction("select_enable_disenable.data");
        // registerReceiver(dataReceiver,intentFilter);
    }

    private void initView() {

        selectedGoodsArrayList = new ArrayList<>();
        goodsSelectAdapter = new ViceGoodsSelectAdapter(this, selectedGoodsArrayList);

        rvSelectGoods.setLayoutManager(new LinearLayoutManager(this));

        rvSelectGoods.setAdapter(goodsSelectAdapter);
    }

    private void initSdk() {
        mDSKernel = DSKernel.newInstance();
        mDSKernel.init(this, mIConnectionCallback);
        mDSKernel.addReceiveCallback(mIReceiveCallback);
    }

    private void initData() {

        myHandler = new MyHandler(this);
    }

    private static class MyHandler extends Handler {
        private WeakReference<Activity> mActivity;

        public MyHandler(Activity activity) {
            mActivity = new WeakReference<Activity>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            if (mActivity.get() != null && !mActivity.get().isFinishing()) {
                switch (msg.what) {
                    case 1://消息提示用途
                        Toast.makeText(mActivity.get(), msg.obj + "", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        }
    }

    /**
     * 获取文件夹大小
     *
     * @param file
     * @return
     */
    private long getFilesSize(File file) {
        long size = 0L;
        File[] files = file.listFiles();
        for (int i = 0; i < files.length; i++) {
            Log.d(TAG, "filename----->" + files[i]);
            if (files[i].isDirectory()) {
                size = size + getFilesSize(files[i]);
            } else {
                size = size + getFileSize(files[i]);
            }
        }
        return size;
    }

    /**
     * @param file
     */
    private long getFileSize(File file) {
        long size = 0L;
        if (file.exists()) {
            FileInputStream fileInputStream;
            try {
                fileInputStream = new FileInputStream(file);
                size = fileInputStream.available();
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        Log.d(TAG, file.getName() + "大小----》" + size);
        return size;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (cutDownDispose != null && !cutDownDispose.isDisposed()) {

            cutDownDispose.dispose();
        }
        EventBus.getDefault().unregister(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MessageEvent messageEvent) {

        String type = messageEvent.getType();
        if (TextUtils.isEmpty(type)) {

            return;
        }

        if (type.equals(MessageEvent.TOTALDISCOUNT)) {

            Log.d(TAG, "onMessageEvent: = " + messageEvent.getStringValues());

            // tvTotalMoney.setText(messageEvent.getStringValues());

        } else if (type.equals(MessageEvent.SELECT_GOODS)) {

            Log.d(TAG, "onMessageEvent: size= " + selectedGoodsArrayList.size());


        }

    }


}
