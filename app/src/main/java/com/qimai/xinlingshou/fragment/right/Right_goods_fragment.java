package com.qimai.xinlingshou.fragment.right;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.hardware.usb.UsbDevice;
import android.hardware.usb.UsbManager;
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

import com.google.gson.JsonArray;
import com.jakewharton.rxbinding2.widget.RxTextView;
import com.qimai.xinlingshou.App;
import com.qimai.xinlingshou.BaseFragment;
import com.qimai.xinlingshou.R;
import com.qimai.xinlingshou.activity.LoginActivity;
import com.qimai.xinlingshou.activity.MainActivity;
import com.qimai.xinlingshou.adapter.CategoryAdapter;
import com.qimai.xinlingshou.adapter.GoodsRecyclerAdapter;
import com.qimai.xinlingshou.bean.CategoryBean;
import com.qimai.xinlingshou.bean.GoodsCategoryBean;
import com.qimai.xinlingshou.bean.goodsBean;
import com.qimai.xinlingshou.dialog.DialogUtils;
import com.qimai.xinlingshou.presenter.Constract;
import com.qimai.xinlingshou.utils.APPUtil;
import com.qimai.xinlingshou.utils.Hint;
import com.qimai.xinlingshou.utils.NetWorkUtils;
import com.qimai.xinlingshou.utils.OkHttpDownload;
import com.qimai.xinlingshou.utils.ToastUtils;
import com.qimai.xinlingshou.utils.UPacketFactory;
import com.qimai.xinlingshou.utils.Xutils;
import com.qimai.xinlingshou.view.GoodsRecyclerItemDecoration;

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
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.sql.StatementEvent;

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
        GoodsRecyclerAdapter.onItemClick,
        GoodsRecyclerAdapter.saveImageSucess,
        Constract.MainView,CategoryAdapter.OnViewItemClick {

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
    boolean is_show = false;
    //    @BindView(R.id.tv_order_one)
//    TextView tvOrderOne;
//    @BindView(R.id.tv_order_two)
//    TextView tvOrderTwo;
    Unbinder unbinder;

    int totalGoods;
    int index;
    @BindView(R.id.ll_category)
    LinearLayout llCategory;
    @BindView(R.id.rv_category)
    RecyclerView rvCategory;
    private OnGoodsItemSelect onGoodsItemSelect;
    private LinearLayoutManager linearLayoutManager;
    private GoodsRecyclerAdapter goodsRecyclerAdapter;
    private ArrayList<goodsBean> goodsBeanArrayList;

    Observable<CharSequence> etGoodSearchObservable;

    ArrayList<CategoryBean> categoryBeanArrayList;

    CategoryAdapter categoryAdapter;

    DSKernel mDSKernel;
    Handler handler = new Handler(Looper.getMainLooper()) {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);

            switch (msg.what) {

                case REQUEST:

                    break;


                case PANDUAN:

                    Log.d(TAG, "handleMessage: msg.arf1= " + msg.arg1
                            + "total= " + totalGoods);

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


        if (obj == null) {

            return;
        }
        Log.d(TAG, "uodateRecycler: obj size= " + obj.size());

        goodsBeanArrayList = obj;

        Log.d(TAG, "onResponse: goodsBeanArrayList= 444   " + goodsBeanArrayList.size());

        //关闭加载框

        DialogUtils.cancelDialog();

        goodsRecyclerAdapter.updateData(goodsBeanArrayList);
    }

    private void getDatasFromSQL() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                String storeId = App.store.getString("storeId");

                goodsBeanArrayList = (ArrayList<goodsBean>) LitePal.where("storeid = ?", storeId).find(goodsBean.class);
                //goodsBeanArrayList = (ArrayList<goodsBean>) DataSupport.findAll(goodsBean.class);
                Log.d(TAG, "getDatasFromSQL: goodsBeanArrayList= " + goodsBeanArrayList.size());
                Message message = new Message();
                message.what = UPDATEUI;
                message.obj = goodsBeanArrayList;
                handler.sendMessage(message);
            }
        }).start();
    }

    @Override
    protected void initData() {

        categoryBeanArrayList = new ArrayList<>();
        OrderType();

        if (goodsRecyclerAdapter != null) {

            goodsBeanArrayList = new ArrayList<>();
            Log.d(TAG, "onResponse: goodsBeanArrayList= 333   " + goodsBeanArrayList.size());

            goodsRecyclerAdapter.updateData(goodsBeanArrayList);
        }

        linearLayoutManager = new GridLayoutManager(activity, 3);
        goodsBeanArrayList = new ArrayList<>();

        goodsRecyclerAdapter = new GoodsRecyclerAdapter(activity, goodsBeanArrayList);

        goodsRecyclerAdapter.setOnItemClick(this);
        goodsRecyclerAdapter.setSaveImageSucess(this);
        rv_goods.setLayoutManager(linearLayoutManager);
        rv_goods.addItemDecoration(new GoodsRecyclerItemDecoration(3,20));
        rv_goods.setAdapter(goodsRecyclerAdapter);
        //每次

        etGoodsSearch.setOnFocusChangeListener(new View.OnFocusChangeListener() {

            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {//获得焦点
                    is_show = true;
                } else {//失去焦点
                    is_show = false;
                }
            }
        });

        Log.d(TAG, "initData: ");
        new Thread(new Runnable() {
            @Override
            public void run() {
                if (APPUtil.isNetworkAvalible(getActivity())) {
                    initStoreData("", 1 + "");
                } else {
                    Log.d(TAG, "run: database");

                    String storeId = App.store.getString("storeId");

                    goodsBeanArrayList = (ArrayList<goodsBean>)
                            LitePal.where("storeid = ?",storeId).find(goodsBean.class);

                    Log.d(TAG, "run: goodsbEANLIST= " + goodsBeanArrayList.size());
                    if (goodsBeanArrayList != null && goodsBeanArrayList.size() > 0) {

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

                Log.d(TAG, "onKey: keycode= " + keyCode);
                return false;
            }
        });


        initRx();


    }

    private void initRx() {


        etGoodSearchObservable.map(new Function<CharSequence, ArrayList<goodsBean>>() {
            @Override
            public ArrayList<goodsBean> apply(CharSequence charSequence) throws Exception {

                Log.d(TAG, "apply: charSequence= " + charSequence.toString()

                        + " isFirstEnter= " + isFirstEnter);

                if (charSequence.toString().equals("") && !isFirstEnter) {


                    if (goodsBeanArrayList == null) {

                        goodsBeanArrayList = new ArrayList<>();
                        return goodsBeanArrayList;

                    } else {

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
                        if ((integer == null || integer.size() == 0) && !isFirstEnter) {


                            isFirstEnter = true;
                        } else {
                            goodsBeanArrayList = integer;

                            Log.d(TAG, "accept: uodateRecycler");
                            uodateRecycler(goodsBeanArrayList);
                        }
                    }
                }, new Consumer<Throwable>() {
                    @Override
                    public void accept(Throwable throwable) throws Exception {
                        Log.d(TAG, "accept: throwable= " + throwable.getMessage());

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

        Log.d(TAG, "queryFromDatabase: keyword= " + keyword);

        if (TextUtils.isEmpty(keyword)) {

            ArrayList<goodsBean> arrayList = (ArrayList<goodsBean>) LitePal.where("storeid = ?", App.store.getString("storeId"))
                    .find(goodsBean.class);

            if (arrayList != null) {
                return arrayList;

            }
            //return (ArrayList<goodsBean>) DataSupport.findAll(goodsBean.class);

        } else {


            Log.d(TAG, "queryFromDatabase: stroreId= " + App.store.getString("storeId"));
            ArrayList<goodsBean> arrayList1 = (ArrayList<goodsBean>) LitePal.
                    where("goodsname like ? and storeid = ?",
                            "%" + keyword + "%", App.store.getString("storeId"))
                    .find(goodsBean.class);

            Log.d(TAG, "queryFromDatabase: arrayList1= " + arrayList1.size());

            ArrayList<goodsBean> goodsBeanArrayList2 = (ArrayList<goodsBean>)
                    LitePal.where("product_no like ? and storeid = ?",
                            "%" + keyword + "%", App.store.getString("storeId"))
                            .find(goodsBean.class);

            Log.d(TAG, "queryFromDatabase: goodsBeanArrayList2= " + goodsBeanArrayList2.size());
            if (goodsBeanArrayList2.size() > 0) {

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

        if (goodsBeanArrayList.size() > 0 && goodsBeanArrayList.get(position) != null) {

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
                Log.d(TAG, "onViewClicked: inputType= " + etGoodsSearch.getInputType());

                InputMethodManager imm = (InputMethodManager)
                        activity.getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.showSoftInput(etGoodsSearch, 0);
                break;
            case R.id.tv_cancel:
                rlSearchItem1.setVisibility(View.VISIBLE);

                ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).
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

                App.disableView(tvAllGoods, 1);
                //initStoreData("",1+"");

                //从数据库中查询全部数据
                ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).
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

    public void initStoreData(String keywords, String page) {


        if (goodsRecyclerAdapter != null) {


            rv_goods.post(new Runnable() {
                @Override
                public void run() {
                    goodsBeanArrayList = new ArrayList<>();

                    Log.d(TAG, "onResponse: goodsBeanArrayList= 222   " + goodsBeanArrayList.size());

                    goodsRecyclerAdapter.updateData(goodsBeanArrayList);

                }
            });
        }

//       ToastUtils.showLongToast("");


            if (NetWorkUtils.isNetWorkAvaiable(activity)){



            tvCancel.post(new Runnable() {
                @Override
                public void run() {

                    DialogUtils.createDialog(activity);

                }
            });


            }

        goodsBeanArrayList = new ArrayList<>();
        String url = App.API_URL + "ptfw/decoration/goods";
        Map<String, String> stringMap = new HashMap<>();
        stringMap.put("page", page);
        stringMap.put("page_size", Integer.MAX_VALUE + "");
        Xutils.getInstance().get(url, stringMap, new Xutils.XCallBack() {
            @Override
            public void onResponse(String str) {
                try {

                    //关闭加载框


                    tvCancel.post(new Runnable() {
                        @Override
                        public void run() {
                            DialogUtils.cancelDialog();

                        }
                    });
                    Log.e("AAAAAAA11111", str);
                    JSONObject mjsonObjects = new JSONObject(str);
                    String result = mjsonObjects.getString("status");
                    String code = mjsonObjects.getString("code");
                    String message = mjsonObjects.getString("message");
                    if (result.equals("true")) {
                        JSONObject mjsonObject = mjsonObjects.getJSONObject("data");
                        JSONArray mjsonObjectss = mjsonObject.getJSONArray("items");

                        Log.d(TAG, "onResponse: mjsonObjectss.lwnth= " + mjsonObjectss.length());
                        if (mjsonObjectss.length() > 0) {

                            goodsBean mgoods;
                            totalGoods = mjsonObjectss.length();
                            Log.d(TAG, "onResponse: totalGoods= " + totalGoods);
                            //不等于null先把数据库内容删了
                            LitePal.deleteAll(goodsBean.class);

                            LitePal.deleteAll(GoodsCategoryBean.class);
                            //LitePal.deleteAll("")
                            for (int i = 0; i < totalGoods; i++) {

                                Double cny = Double.parseDouble(mjsonObjectss.getJSONObject(i).getString("sell_price"));//6.2041    这个是转为double类型
                                DecimalFormat df = new DecimalFormat("0.00");
                                String CNY = df.format(cny); //6.20   这个是字符串，但已经是我要的两位小数了
                                Log.i(TAG, CNY);
                                Double cny2 = Double.parseDouble(CNY); //6.20
                                String unit = mjsonObjectss.getJSONObject(i).getString("unit");
                                if (unit != null && !unit.equals("")) {

                                } else {
                                    unit = "件";
                                }
                                try {
                                    String store = App.store.getString("storeinfo");
                                    if (store != null) {


                                        String storeid = new JSONObject(store).getString("id");
                                        String mobile = App.store.getString("mobile");
                                        mgoods = new goodsBean();
                                        mgoods.setGoodsimg(mjsonObjectss.getJSONObject(i).getString("image"));
                                        mgoods.setGoodsName(mjsonObjectss.getJSONObject(i).getString("name"));


                                        mgoods.setPrice(cny2);
                                        mgoods.setNumber(mjsonObjectss.getJSONObject(i).getInt("sort"));
                                        mgoods.setGoodsId(mjsonObjectss.getJSONObject(i).getString("id"));


                                        mgoods.setUnit("/" + unit);
                                        mgoods.setStoreid(storeid);
                                        mgoods.setStoremobile(mobile);
                                        mgoods.setProduct_no(mjsonObjectss.getJSONObject(i)
                                                .getJSONArray("product_no").get(0).toString());


                                        Log.d(TAG, "onResponse: url= " + mgoods.getGoodsimg());
                                        //downLoadImageAndSaveSQL(mgoods);

                                        mgoods.setLocal_image(null);


                                        JSONArray jsonArrayCategory = mjsonObjectss.getJSONObject(i).getJSONArray("category_id");

                                        StringBuffer stringBuffer = new StringBuffer();

                                        String category_is = jsonArrayCategory.toString();


                                        GoodsCategoryBean goodsCategoryBean;
                                        for (int j = 0; j < jsonArrayCategory.length(); j++) {

                                            goodsCategoryBean = new GoodsCategoryBean();

                                            goodsCategoryBean.setGoodsId(mgoods.getGoodsId());

                                            goodsCategoryBean.setGoodsName(mgoods.getGoodsName());
                                            goodsCategoryBean.setCategoty_id(jsonArrayCategory.getString(j));
                                            goodsCategoryBean.save();

                                            if (j<jsonArrayCategory.length()-1) {
                                                stringBuffer.append(jsonArrayCategory.get(j).toString()
                                                        + ",");
                                            }else{

                                                stringBuffer.append(jsonArrayCategory.get(j).toString()
                                                        ) ;
                                            }

                                        }

                                        mgoods.setCategory_id(stringBuffer.toString());
                                        Log.d(TAG, "onResponse: stringBuffer= "+stringBuffer.toString());


                                        //避免重复插入数据
                                        //LitePal.deleteAll()
                                        List<goodsBean> goodsBeanList =
                                                DataSupport.where("goodsid=?", mgoods.getGoodsId())
                                                        .find(goodsBean.class);
                                        if (goodsBeanList.size() <= 0) {

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


                            Log.d(TAG, "onResponse: goodsBeanArrayList= 111  " + goodsBeanArrayList.size());
                            goodsRecyclerAdapter.updateData(goodsBeanArrayList);
//                           App.store.getString("storeinfo").
//                          if (issave){
////                              issave
//                              Hint.Short(getActivity(),"保存成功");
//                          }else {
//                              Hint.Short(getActivity(),"保存失败");
//                          }


//                           DataSupport.sa`(mgoodsBean);
                        } else {



                            Hint.Short(getActivity(), "暂无商品！");
                        }

                    } else {



                        tvCancel.post(new Runnable() {
                            @Override
                            public void run() {
                                DialogUtils.cancelDialog();

                            }
                        });

                        Hint.Short(getActivity(), message + ">>>" + code);
                        if (code.equals("9001")) {
                            App.removeUser();
                            Intent intent = new Intent(getActivity(), LoginActivity.class);
                            MessageEvent messageEvent = new MessageEvent("change_store");
                            EventBus.getDefault().post(messageEvent);
                            getActivity().startActivity(intent);
                            getActivity().finish();
                            Hint.Short(getActivity(), "登录超时");
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


    public void OrderType() {
        String url = App.API_URL + "ptfw/cashier/categories";
        Map<String, String> stringMap = new HashMap<>();
        //ToastUtils.showShortToast(""+mOrderbean.getService_orderId());

        Xutils.getInstance().get(url, stringMap, new Xutils.XCallBack() {
            @Override
            public void onResponse(String str) {
                try {
                    JSONObject mjsonObjects = new JSONObject(str);
                    String result = mjsonObjects.getString("status");
                    String message = mjsonObjects.getString("message");
                    if (result.equals("true")) {
                        JSONObject mjsonObject = mjsonObjects.getJSONObject("data");


                        JSONArray jsonArrayItems = mjsonObject.getJSONArray("items");


                        CategoryBean categoryBean = null;
                        ArrayList<String>categoryList;

                        for (int i = 0; i < jsonArrayItems.length(); i++) {

                            int index = 0;
                            JSONObject jsonObjectItem = jsonArrayItems.getJSONObject(i);
                            categoryBean = new CategoryBean();
                            categoryList = new ArrayList<>();
                            String oneCategory = jsonObjectItem.optString("id");
                            categoryList.add(oneCategory);

                            categoryBean.setCategoryId(jsonObjectItem.optString("id"));
                            categoryBean.setStoreId(jsonObjectItem.optString("store_id"));
                            categoryBean.setName(jsonObjectItem.optString("name"));
                            JSONArray jsonArrayChildCategory = jsonObjectItem.optJSONArray("_child");

                            if (jsonArrayChildCategory!=null&&jsonArrayChildCategory.length()!=0){



                                for (int j = 0; j < jsonArrayChildCategory.length(); j++) {

                                    JSONObject jsonObjectChild = (JSONObject) jsonArrayChildCategory.get(j);

                                    String id = jsonObjectChild.optString("id");

                                    if (!TextUtils.isEmpty(id)){

                                        categoryList.add(id);
                                       // category[index] =id;

                                       // index++;
                                    }

                                }
                                //JSONObject jsonObject = jsonArrayChildCategory.get()

                            }


                            Log.d(TAG, "onResponse: category= "+categoryList.toString());

                            categoryBean.setCategory(categoryList);
                            Log.d(TAG, "onResponse: category ----------");
                            categoryBean.setSelected(false);
                            categoryBeanArrayList.add(categoryBean);
                        }


                        updateCategoryMenu(categoryBeanArrayList);


                        Log.d(TAG, "onResponse: mjsonObject= " + mjsonObject.toString());
                    } else {
                        Hint.Short(getActivity(), message);

                    }
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

    private void updateCategoryMenu(ArrayList<CategoryBean> categoryBeanArrayList) {


        if (categoryBeanArrayList != null) {

            if (categoryAdapter==null){

                categoryAdapter = new CategoryAdapter(activity,categoryBeanArrayList);

                categoryAdapter.setOnViewItemClick(this);
                rvCategory.setLayoutManager
                        (new LinearLayoutManager(activity));
                rvCategory.setAdapter(categoryAdapter);

            }else{

                categoryAdapter.update(categoryBeanArrayList);

            }

           /* TextView textView = (TextView) LayoutInflater.from(getActivity()).inflate(R.layout.category_items, llCategory, false);

            llCategory.addView(textView);*/

        }


    }

    private void downLoadImageAndSaveSQL(final goodsBean goodsBean) {

        final ArrayList<goodsBean> goodsBeanArrayList = null;
        OkHttpDownload.getInstance().
                executeRequest(goodsBean.getGoodsimg(), new OkHttpDownload.callBack() {
                    @Override
                    public void onResponse(byte[] imags) {
                        //  Log.d(TAG, "onResponse: imags= "+(imags.length));
                        //goodsBean.setLocal_image(imags);


                        if (goodsBean.getLocal_image() != null) {
                            Log.d(TAG, "onResponse: goodsBean.getLocal_image()!=null");

                        }

                        boolean issave = goodsBean.save();
                        if (issave) {
                            Log.d(TAG, "onResponse: 保存成功");


                            String storeId = App.store.getString("storeinfo");

                            //goodsBeanArrayList = (ArrayList<com.example.niu.myapplication.bean.goodsBean>) DataSupport.findAll(goodsBean.class);


                            //Hint.Short(getActivity(),"保存成功");
                        } else {
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
    public void updateVIew(ArrayList<goodsBean> arrayList) {


    }

    @Override
    public void onImageSucess(int position, File file) {

        //sendImgsMenu(position,file);


    }

    @Override
    public void onViewItemClick(int position, View view) {

        App.disableView(view, 1);
        Log.d(TAG, "onViewItemClick: position= "+position);

        DialogUtils.createDialog(activity);

        queryDBAndUpdate(position);

    }

    @Override
    public void onHeadViewClick(View v) {


        Log.d(TAG, "onHeadViewClick: onheadViewClick");


        App.disableView(v, 1);
        //initStoreData("",1+"");

        DialogUtils.createDialog(activity);



        //从数据库中查询全部数据
        ((InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE)).
                hideSoftInputFromWindow(getActivity().
                        getCurrentFocus().getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);
        uodateRecycler(queryFromDatabase(""));

    }


    /***
     * 分类查询
     * */

    private void queryDBAndUpdate(int position) {


        ArrayList<goodsBean>temp = new ArrayList<>();

        CategoryBean categoryBean = categoryBeanArrayList.get(position);

        String category_id = categoryBean.getCategoryId();

        //String []category = categoryBean.getCategory();

        ArrayList<String>categoryList = categoryBean.getCategory();

        StringBuilder queryContent = new StringBuilder();



        if(categoryList!=null&&categoryList.size()>0){

            for (int i =0;i<categoryList.size();i++){

                if (i==categoryList.size()-1){

                    queryContent.append(categoryList.get(i));

                }else {

                    queryContent.append(categoryList.get(i) + ",");

                }
            }

            Log.d(TAG, "queryDBAndUpdate: queryContent= "+queryContent.toString());


            ArrayList<GoodsCategoryBean>goodsCategoryBeanArrayList  = (ArrayList<GoodsCategoryBean>)LitePal.where("categoty_id in ("+queryContent+")").find(GoodsCategoryBean.class);

           /* temp = (ArrayList<goodsBean>)LitePal.where("category_id in ("+queryContent+")").find(goodsBean.class);*/

            //去重操作


            HashSet<GoodsCategoryBean> hashSet = new HashSet(goodsCategoryBeanArrayList);

            goodsCategoryBeanArrayList = getSingle(goodsCategoryBeanArrayList);
            //temp = new ArrayList<>()

            goodsBean goodsBean;
            for (GoodsCategoryBean goodsCategoryBean:
                 goodsCategoryBeanArrayList) {

                goodsBean = LitePal.where("goodsid="+goodsCategoryBean.getGoodsId())
                        .findFirst(com.qimai.xinlingshou.bean.goodsBean.class);

                //if (goodsBean!=null){

                    temp.add(goodsBean);
                //}
            }
            goodsBeanArrayList = temp;

            DialogUtils.cancelDialog();
            //关闭加载框
            Log.d(TAG, "queryDBAndUpdate: ");
            goodsRecyclerAdapter.updateData(goodsBeanArrayList);

        }



    }

    public interface OnGoodsItemSelect {

        public void onGoodsSelect(goodsBean goodsBean);
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onEvent(MessageEvent messageEvent) {

        String type = messageEvent.getType();
        if (TextUtils.isEmpty(type)) {
            return;
        }

        if (type.equals("change_store")) {

            if (goodsRecyclerAdapter != null) {

                isFirstEnter = false;
                goodsBeanArrayList = new ArrayList<>();
                Log.d(TAG, "onResponse: goodsBeanArrayList= 555  " + goodsBeanArrayList.size());

                goodsRecyclerAdapter.updateData(goodsBeanArrayList);
            }

        }
        if (messageEvent.getType().equals("ThridFragmentPay")) {
//            MainActivity.isRightFragmentShow(RightFragmentType.RIGHT_CRASHIER);
            if (((MainActivity) getActivity()).isRightFragmentShow(RightFragmentType.RIGHT_CRASHIER)) {
                String goods_num = messageEvent.getAuthCode();
                if (is_show) {
                    etGoodsSearch.setText(goods_num);
                }
            }

        }


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

    public static ArrayList getSingle(ArrayList list){
        ArrayList newList = new ArrayList();     //创建新集合
        Iterator it = list.iterator();        //根据传入的集合(旧集合)获取迭代器
        while(it.hasNext()){          //遍历老集合
            Object obj = it.next();       //记录每一个元素
            if(!newList.contains(obj)){      //如果新集合中不包含旧集合中的元素
                newList.add(obj);       //将元素添加
            }
        }
        return newList;
    }
}
