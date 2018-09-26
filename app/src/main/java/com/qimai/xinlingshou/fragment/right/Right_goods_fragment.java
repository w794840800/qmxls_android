package com.qimai.xinlingshou.fragment.right;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.qimai.xinlingshou.App;
import com.qimai.xinlingshou.BaseFragment;
import com.qimai.xinlingshou.R;
import com.qimai.xinlingshou.adapter.GoodsRecyclerAdapter;
import com.qimai.xinlingshou.bean.goodsBean;
import com.qimai.xinlingshou.presenter.Constract;
import com.qimai.xinlingshou.utils.APPUtil;
import com.qimai.xinlingshou.utils.Hint;
import com.qimai.xinlingshou.utils.OkHttpDownload;
import com.qimai.xinlingshou.utils.ToastUtils;
import com.qimai.xinlingshou.utils.UPacketFactory;
import com.qimai.xinlingshou.utils.Xutils;
import com.jakewharton.rxbinding2.widget.RxTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.litepal.LitePal;
import org.litepal.crud.DataSupport;

import java.io.File;
import java.lang.ref.WeakReference;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;
import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import sunmi.ds.DSKernel;
import sunmi.ds.callback.IConnectionCallback;
import sunmi.ds.callback.IReceiveCallback;
import sunmi.ds.callback.ISendCallback;
import sunmi.ds.data.DSData;
import sunmi.ds.data.DSFile;
import sunmi.ds.data.DSFiles;
import sunmi.ds.data.DataPacket;

/**
 * 商品列表
 * Created by NIU on 2018/5/18.
 */

@SuppressLint("ValidFragment")
public class Right_goods_fragment extends BaseFragment implements
        GoodsRecyclerAdapter.onItemClick,GoodsRecyclerAdapter.saveImageSucess,Constract.MainView {

    private static final String TAG = "Right_goods_fragment";

    public static final int REQUEST = 1;
    public static final int UPDATEUI = 2;
    private static final int PANDUAN = 3;

    boolean isFirstEnter;
    @BindView(R.id.rv_goods)
    RecyclerView rv_goods;
    @BindView(R.id.iv_search)
    ImageView ivSearch;
    @BindView(R.id.rl_search_item1)
    RelativeLayout rlSearchItem1;
    @BindView(R.id.tv_cancel)
    TextView tvCancel;
    @BindView(R.id.et_goods_search)
    EditText etGoodsSearch;
    @BindView(R.id.rl_search_item2)
    RelativeLayout rlSearchItem2;
    @BindView(R.id.ll_goods_items)
    LinearLayout llGoodsItems;
    @BindView(R.id.ll_empty)
    LinearLayout llEmpty;
    @BindView(R.id.tv_all_goods)
    TextView tvAllGoods;
    MyHandler myHandler;
//    @BindView(R.id.tv_order_one)
//    TextView tvOrderOne;
//    @BindView(R.id.tv_order_two)
//    TextView tvOrderTwo;
    Unbinder unbinder;

    int totalGoods;
    int index;
    private OnGoodsItemSelect onGoodsItemSelect;
    private LinearLayoutManager linearLayoutManager;
    private GoodsRecyclerAdapter goodsRecyclerAdapter;
    private ArrayList<goodsBean> goodsBeanArrayList;

    Observable<CharSequence>etGoodSearchObservable;


    /**
     * 双屏通讯消息回调
     */
    private IReceiveCallback mIReceiveCallback = new IReceiveCallback() {
        @Override
        public void onReceiveData(DSData data) {

        }

        @Override
        public void onReceiveFile(DSFile file) {

        }

        @Override
        public void onReceiveFiles(DSFiles files) {

        }

        @Override
        public void onReceiveCMD(DSData cmd) {

        }
    };

    private IConnectionCallback mIConnectionCallback = new IConnectionCallback() {
        @Override
        public void onDisConnect() {
            Message message = new Message();
            message.what = 1;
            if (!isAdded()){

                return;
            }
            message.obj = getString(R.string.unconnect_main_service);
            myHandler.sendMessage(message);
        }

        @Override
        public void onConnected(IConnectionCallback.ConnState state) {
            Message message = new Message();
            message.what = 1;
            switch (state) {
                case AIDL_CONN:
                    if (!isAdded()){
                        return;
                    }
                    message.obj = getString(R.string.connect_main_service);
                    break;
                case VICE_SERVICE_CONN:

                    if (!isAdded()){
                        return;
                    }
                    message.obj = getString(R.string.connect_vice_service);
                    break;
                case VICE_APP_CONN:
                    if (!isAdded()){
                        return;
                    }
                    message.obj = getString(R.string.connect_vice_dsd);
                    DataPacket dsPacket = UPacketFactory.buildOpenApp("com.example.niu.myapplication    ", null);
                    mDSKernel.sendCMD(dsPacket);
                    break;
                default:
                    break;
            }
            myHandler.sendMessage(message);
        }
    };



    DSKernel mDSKernel;
    Handler handler = new Handler(Looper.getMainLooper()){

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what){

                case REQUEST:

                    break;


                case PANDUAN:

                    Log.d(TAG, "handleMessage: msg.arf1= "+msg.arg1
                    +"total= "+totalGoods);

                        getDatasFromSQL();


                    break;


                case UPDATEUI:

                    Log.d(TAG, "handleMessage: UPDATEUI");
                    uodateRecycler(goodsBeanArrayList);

                    break;
            }

        }
    };



    private void uodateRecycler(ArrayList<goodsBean> obj) {



        Log.d(TAG, "uodateRecycler: obj size= "+obj.size());
        goodsBeanArrayList = obj;
        Log.d(TAG, "onResponse: goodsBeanArrayList= 444   "+goodsBeanArrayList.size());

        goodsRecyclerAdapter.updateData(goodsBeanArrayList);
    }

    private void getDatasFromSQL() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String storeId = App.store.getString("storeId");

                goodsBeanArrayList = (ArrayList<goodsBean>) LitePal.where("storeid = ?",storeId).find(goodsBean.class);
                //goodsBeanArrayList = (ArrayList<goodsBean>) DataSupport.findAll(goodsBean.class);
                Log.d(TAG, "getDatasFromSQL: goodsBeanArrayList= "+goodsBeanArrayList.size());


                Message message = new Message();
                message.what = UPDATEUI;
                message.obj = goodsBeanArrayList;
                handler.sendMessage(message);
            }
        }).start();
    }


    @Override
    protected void initData() {
        
        if (goodsRecyclerAdapter!=null){

            goodsBeanArrayList = new ArrayList<>();
            Log.d(TAG, "onResponse: goodsBeanArrayList= 333   "+goodsBeanArrayList.size());

            goodsRecyclerAdapter.updateData(goodsBeanArrayList);
        }

        linearLayoutManager = new GridLayoutManager(activity, 3);
        goodsBeanArrayList = new ArrayList<>();

        goodsRecyclerAdapter = new GoodsRecyclerAdapter(activity, goodsBeanArrayList);

        goodsRecyclerAdapter.setOnItemClick(this);
        goodsRecyclerAdapter.setSaveImageSucess(this);
        rv_goods.setLayoutManager(linearLayoutManager);
        rv_goods.setAdapter(goodsRecyclerAdapter);
        //每次



        Log.d(TAG, "initData: ");
        new Thread(new Runnable(){
            @Override
            public void run() {
                if (APPUtil.isNetworkAvalible(getActivity())){
                    initStoreData("",1+"");
                }else {
                    Log.d(TAG, "run: database");
                    String storeId = App.store.getString("storeId");

                    goodsBeanArrayList = (ArrayList<goodsBean>)
                            LitePal.where("storeid = ?",storeId).find(goodsBean.class);

                    Log.d(TAG, "run: goodsbEANLIST= "+goodsBeanArrayList.size());
                   if (goodsBeanArrayList!=null&& goodsBeanArrayList.size()>0){

                       Message message = new Message();
                       Log.d(TAG, "run: updateUi");
                       message.what = UPDATEUI;
                     //  message.obj = goodsBeanArrayList;
                     //handler.sendMessage();
                       handler.sendMessage(message);
                      // goodsRecyclerAdapter.updateData(goodsBeanArrayList);

                   }
                }
            }
        }).start();

    }

    @Override
    protected void initView(View rootView) {

        myHandler = new MyHandler(getActivity());
        EventBus.getDefault().register(this);

        etGoodSearchObservable = RxTextView.textChanges(etGoodsSearch);


        etGoodsSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                Log.d(TAG, "onKey: keycode= "+keyCode);
                return false;
            }
        });
        mDSKernel = DSKernel.newInstance();
        mDSKernel.init(getActivity(), mIConnectionCallback);
        mDSKernel.addReceiveCallback(mIReceiveCallback);

        initRx();



    }

    private void initRx() {



        etGoodSearchObservable.map(new Function<CharSequence, ArrayList<goodsBean>>() {
            @Override
            public ArrayList<goodsBean> apply(CharSequence charSequence) throws Exception {

                Log.d(TAG, "apply: charSequence= "+charSequence.toString()

                +" isFirstEnter= "+isFirstEnter);

                if (charSequence.toString().equals("")&&!isFirstEnter){



                    if (goodsBeanArrayList==null){

                        goodsBeanArrayList = new ArrayList<>();
                        return goodsBeanArrayList;

                    }else{

                        return goodsBeanArrayList;
                    }

                }



                return queryFromDatabase(charSequence.toString());


            }
        }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<ArrayList<goodsBean>>() {
                    @Override
                    public void accept(ArrayList<goodsBean> integer) throws Exception {
                        Log.d(TAG, "accept: integer= " + integer.size());

        //因为EditText设置了监听，他会在一打开EditText为空的时候也会调用一次，这里就是为了区分
                        if ((integer==null||integer.size()==0)&&!isFirstEnter){


                            isFirstEnter = true;
                        }else {
                            goodsBeanArrayList = integer;

                            Log.d(TAG, "accept: uodateRecycler");
                            uodateRecycler(goodsBeanArrayList);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG, "accept: throwable= "+throwable.getMessage());

                        ToastUtils.showShortToast("暂无此商品");
                    }
                });


    /*  etGoodSearchObservable.subscribe(new Consumer<CharSequence>() {
          @Override
          public void accept(CharSequence charSequence) throws Exception {

              Log.d(TAG, "accept: charSequence= "+charSequence.toString());
          }
      });*/

    }

    private ArrayList<goodsBean> queryFromDatabase(String keyword) {

        Log.d(TAG, "queryFromDatabase: keyword= "+keyword);

        if(TextUtils.isEmpty(keyword)){

           ArrayList<goodsBean>arrayList =  (ArrayList<goodsBean>)LitePal.where("storeid = ?",App.store.getString("storeId"))
            .find(goodsBean.class);

           if (arrayList!=null){
               return arrayList;

           }
            //return (ArrayList<goodsBean>) DataSupport.findAll(goodsBean.class);

        }else{


            Log.d(TAG, "queryFromDatabase: stroreId= "+App.store.getString("storeId"));
            ArrayList<goodsBean>arrayList1 = (ArrayList<goodsBean>) LitePal.
                    where("goodsname like ? and storeid = ?",
                            "%"+keyword+"%",App.store.getString("storeId"))
                            .find(goodsBean.class);

            Log.d(TAG, "queryFromDatabase: arrayList1= "+arrayList1.size());

            ArrayList<goodsBean>goodsBeanArrayList2 = (ArrayList<goodsBean>)
                    LitePal.where("product_no like ? and storeid = ?",
                    "%"+keyword+"%",App.store.getString("storeId"))
                    .find(goodsBean.class);

            Log.d(TAG, "queryFromDatabase: goodsBeanArrayList2= "+goodsBeanArrayList2.size());
            if (goodsBeanArrayList2.size()>0){

                arrayList1.addAll(goodsBeanArrayList2);

            }

            return arrayList1;
        }


       return null;
    }


    @Override
    protected int getLayout() {
        return R.layout.cashier_right_item_goods;
    }

    @Override
    public void onViewClick(View view, int position) {

        //onGoodsItemSelect.onGoodsSelect(goodsBeanArrayList.get(position));

        MessageEvent messageEvent = new MessageEvent("update");

        if (goodsBeanArrayList.size()>0&&goodsBeanArrayList.get(position)!=null){

            messageEvent.setGoodsBean(goodsBeanArrayList.get(position));

            EventBus.getDefault().post(messageEvent);
         }

        /*Toast.makeText(activity,"position= "+position,Toast.LENGTH_SHORT)
                .show();*/
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

    @OnClick({R.id.rl_search_item1, R.id.tv_cancel, R.id.et_goods_search, R.id.rl_search_item2, R.id.ll_goods_items, R.id.ll_empty, R.id.tv_all_goods})
    public void onViewClicked(View view) {
        switch (view.getId()) {
            case R.id.rl_search_item1:
                rlSearchItem1.setVisibility(View.GONE);
                rlSearchItem2.setVisibility(View.VISIBLE);
                etGoodsSearch.setFocusable(true);
                etGoodsSearch.performClick();
                etGoodsSearch.setFocusableInTouchMode(true);
                etGoodsSearch.requestFocus();
                //etGoodsSearch.setInputType();
                Log.d(TAG, "onViewClicked: inputType= "+etGoodsSearch.getInputType());

                InputMethodManager imm = (InputMethodManager)
                        activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(etGoodsSearch,0);
                break;
            case R.id.tv_cancel:
                rlSearchItem1.setVisibility(View.VISIBLE);

                ((InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).
                        hideSoftInputFromWindow(getActivity().
                                getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                etGoodsSearch.setText("");
                etGoodsSearch.setHint("请输入商品条形码 商品简称搜索");
                rlSearchItem2.setVisibility(View.GONE);

                break;
            case R.id.et_goods_search:
                break;
            case R.id.rl_search_item2:
                break;
            case R.id.ll_goods_items:

                break;

                case R.id.ll_empty:

                break;

                case R.id.tv_all_goods:

                //initStoreData("",1+"");

                //从数据库中查询全部数据
                    ((InputMethodManager)getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).
                            hideSoftInputFromWindow(getActivity().
                                    getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
                uodateRecycler(queryFromDatabase(""));



                break;
//            case R.id.tv_order_one:
//                break;
//            case R.id.tv_order_two:
//                break;
        }
    }
   public void initStoreData(String keywords,String page){


       if (goodsRecyclerAdapter!=null){


          rv_goods.post(new Runnable() {
              @Override
              public void run() {
                  goodsBeanArrayList = new ArrayList<>();

                  Log.d(TAG, "onResponse: goodsBeanArrayList= 222   "+goodsBeanArrayList.size());

                  goodsRecyclerAdapter.updateData(goodsBeanArrayList);

              }
          });
       }


       goodsBeanArrayList = new ArrayList<>();
       String url = App.API_URL+"reta/decoration/goods";
       Map<String,String> stringMap = new HashMap<>();
       stringMap.put("page", page);
       stringMap.put("page_size", Integer.MAX_VALUE+"");
       Xutils.getInstance().get(url, stringMap, new Xutils.XCallBack() {
           @Override
           public void onResponse(String str) {
               try {
                   Log.e("AAAAAAA11111",str);
                   JSONObject mjsonObjects = new JSONObject(str);
                   String result = mjsonObjects.getString("status");
                   String code = mjsonObjects.getString("code");
                   String message = mjsonObjects.getString("message");
                   if (result.equals("true")) {
                       JSONObject mjsonObject =mjsonObjects.getJSONObject("data");
                       JSONArray mjsonObjectss =mjsonObject.getJSONArray("items");

                       Log.d(TAG, "onResponse: mjsonObjectss.lwnth= "+mjsonObjectss.length());
                       if (mjsonObjectss.length()>0){

                           goodsBean mgoods;
                           totalGoods = mjsonObjectss.length();
                           Log.d(TAG, "onResponse: totalGoods= "+totalGoods);
                           //不等于null先把数据库内容删了
                           LitePal.deleteAll(goodsBean.class,"goodsid is not null");

                           //LitePal.deleteAll("")
                           for (int i=0;i<totalGoods;i++) {

                           Double cny = Double.parseDouble(mjsonObjectss.getJSONObject(i).getString("sell_price"));//6.2041    这个是转为double类型
                           DecimalFormat df = new DecimalFormat("0.00");
                           String CNY = df.format(cny); //6.20   这个是字符串，但已经是我要的两位小数了
                           Log.i(TAG, CNY);
                           Double cny2 = Double.parseDouble(CNY); //6.20
                           String unit = mjsonObjectss.getJSONObject(i).getString("unit");
                           if (unit != null && !unit.equals(""))
                           {

                           }else {
                               unit="件";
                           }
                           try {
                               String store = App.store.getString("storeinfo");
                               if (store!=null){


                                   String  storeid = new JSONObject(store).getString("id");
                                   String mobile = App.store.getString("mobile");
                                   mgoods = new goodsBean();
                                   mgoods.setGoodsimg(mjsonObjectss.getJSONObject(i).getString("image"));
                                   mgoods.setGoodsName(mjsonObjectss.getJSONObject(i).getString("name"));
                                   mgoods.setPrice(cny2);
                                   mgoods.setNumber(mjsonObjectss.getJSONObject(i).getInt("sort"));
                                   mgoods.setGoodsId(mjsonObjectss.getJSONObject(i).getString("id"));
                                   mgoods.setUnit("/"+unit);
                                   mgoods.setStoreid(storeid);
                                   mgoods.setStoremobile(mobile);
                                   mgoods.setProduct_no( mjsonObjectss.getJSONObject(i)
                                           .getJSONArray("product_no").get(0).toString());

                                   Log.d(TAG, "onResponse: url= "+mgoods.getGoodsimg());
                                   //downLoadImageAndSaveSQL(mgoods);

                                   mgoods.setLocal_image(null);


                                   //避免重复插入数据
                                   //LitePal.deleteAll()
                                   List<goodsBean> goodsBeanList=
                                     DataSupport.where("goodsid=?",mgoods.getGoodsId())
                                            .find(goodsBean.class);
                                    if (goodsBeanList.size()<=0){

                                      mgoods.save();


                                    }
                                      goodsBeanArrayList.add(mgoods);

                                   /*goodsBeanArrayList.add(new goodsBean(mjsonObjectss.getJSONObject(i).getString("image"),mjsonObjectss.getJSONObject(i).getString("name")
                                           , cny2, mjsonObjectss.getJSONObject(i).getInt("sort"), mjsonObjectss.getJSONObject(i).getString("sell_price"), "/"+unit
                                           ,storeid,mobile,mjsonObjectss.getJSONObject(i).getString("product_no"),""));
*/
                                   //                                   DataSupport.saveAll();
                               }
                           } catch (JSONException e) {
                               e.printStackTrace();
                           }


                       }


                           Log.d(TAG, "onResponse: goodsBeanArrayList= 111  "+goodsBeanArrayList.size());
                           goodsRecyclerAdapter.updateData(goodsBeanArrayList);
//                           App.store.getString("storeinfo").
//                          if (issave){
////                              issave
//                              Hint.Short(getActivity(),"保存成功");
//                          }else {
//                              Hint.Short(getActivity(),"保存失败");
//                          }


//                           DataSupport.sa`(mgoodsBean);
                       }else {

                           Hint.Short(getActivity(),"暂无商品！");
                       }

                   }else {
                       Hint.Short(getActivity(),message+">>>"+code);
                       if (code.equals("9001")){
                           Hint.Short(getActivity(),"登录超时");
                       }
                   }

                   Log.d(TAG, "onResponse: save finish");
               } catch (JSONException e) {
                   e.printStackTrace();
               }


           }

           @Override
           protected Object clone() throws CloneNotSupportedException {
               return super.clone();
           }

       });

    }

    private void downLoadImageAndSaveSQL(final goodsBean goodsBean) {

       final ArrayList<goodsBean>goodsBeanArrayList = null;
       OkHttpDownload.getInstance().
                executeRequest(goodsBean.getGoodsimg(), new OkHttpDownload.callBack() {
                    @Override
                    public void onResponse(byte[] imags) {
                      //  Log.d(TAG, "onResponse: imags= "+(imags.length));
                        //goodsBean.setLocal_image(imags);


                        if (goodsBean.getLocal_image()!=null){
                            Log.d(TAG, "onResponse: goodsBean.getLocal_image()!=null");

                        }

                        boolean issave = goodsBean.save();
                        if (issave){
                            Log.d(TAG, "onResponse: 保存成功");


                            String storeId = App.store.getString("storeinfo");

                            //goodsBeanArrayList = (ArrayList<com.example.niu.myapplication.bean.goodsBean>) DataSupport.findAll(goodsBean.class);



                            //Hint.Short(getActivity(),"保存成功");
                        }else {
                        //    Hint.Short(getActivity(),"保存失败");

                            Log.d(TAG, "onResponse: 保存失败");
                        }

                        Message message = new Message();
                        message.what = PANDUAN;
                        message.arg1 = index;
                        handler.sendMessage(message);

                        index++;

                        //Message message = new Message();
                        //handler.sendMessage()


                    }

        });







   }



    @Override
    public void updateVIew(ArrayList<goodsBean>arrayList) {





    }

    @Override
    public void onImageSucess(int position,File file) {

        sendImgsMenu(position,file);


    }

    public interface OnGoodsItemSelect {

        public void onGoodsSelect(goodsBean goodsBean);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent messageEvent) {

       String type = messageEvent.getType();
       if (TextUtils.isEmpty(type))
       {
           return;
       }

       if (type.equals("change_store")){

           if (goodsRecyclerAdapter!=null){

               isFirstEnter = false;
               goodsBeanArrayList = new ArrayList<>();
               Log.d(TAG, "onResponse: goodsBeanArrayList= 555  "+goodsBeanArrayList.size());

               goodsRecyclerAdapter.updateData(goodsBeanArrayList);
           }

       }


    }
    private void sendImgsMenu(final int position, File file) {

        Log.d(TAG, "sendImgsMenu: file= "+file.getAbsolutePath()+
                " path= "+file.getPath().toString()+" size= "+file.getFreeSpace());


       if (file==null||file.getTotalSpace()<=0){
           return;
       }
        mDSKernel.sendFile("com.example.niu.myapplication", "",
                file.getPath().toString(), new ISendCallback() {
                    @Override
                    public void onSendSuccess(long taskId) {


                        Log.d(TAG, "onSendSuccess: taskId= "+taskId);
                        goodsBeanArrayList.get(position).setTaskId(taskId);
                    }

                    @Override
                    public void onSendFail(int errorId, String errorInfo) {

                    }

                    @Override
                    public void onSendProcess(long totle, long sended) {

                    }
                });


              /*  mDSKernel.sendFiles("com.example.niu.myapplication", "",
                        null, new ISendFilesCallback() {
                            @Override
                            public void onAllSendSuccess(long fileId) {

                            }

                            @Override
                            public void onSendSuccess(String path, long taskId) {
                            }

                            @Override
                            public void onSendFaile(int errorId, String errorInfo) {
                                //            showToast("发送轮播图失败---->" + errorInfo);
                                //            dismissDialog();
                                Hint.Short(getActivity(), "发送轮播图失败---->" + errorInfo);
                            }

                            @Override
                            public void onSendFileFaile(String path, int errorId, String errorInfo) {
                                //            dismissDialog();
                                //            showToast("发送轮播图失败---->" + errorInfo);
                            }

                            @Override
                            public void onSendProcess(String path, long totle, long sended) {
                            }
                        });*/
    }
    @Override
    public void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
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
//                        Toast.makeText(mActivity.get(), msg.obj + "", Toast.LENGTH_SHORT).show();
                        break;
                    default:
                        break;
                }
            }
        }

    }
}
