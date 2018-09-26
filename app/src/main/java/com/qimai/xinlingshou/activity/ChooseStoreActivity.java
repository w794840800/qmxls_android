package com.qimai.xinlingshou.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.qimai.xinlingshou.App;
import com.qimai.xinlingshou.R;
import com.qimai.xinlingshou.adapter.JsonAdapter;
import com.qimai.xinlingshou.adapter.StoreRecyclerAdapter;
import com.qimai.xinlingshou.bean.storeBean;
import com.qimai.xinlingshou.fragment.right.MessageEvent;
import com.qimai.xinlingshou.utils.Hint;
import com.qimai.xinlingshou.utils.Xutils;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.zyao89.view.zloading.ZLoadingDialog;
import com.zyao89.view.zloading.Z_TYPE;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class
ChooseStoreActivity extends AppCompatActivity implements View.OnClickListener{
    Toolbar mToolbar;
    private static final String TAG = "ChooseStoreActivity";
    RecyclerView rvStore;
    LinearLayoutManager linearLayoutManager;
    private StoreRecyclerAdapter storeRecyclerAdapter;
    private ArrayList<storeBean> storeBeanArrayList;
//        XuanAdapter mAdapter;
    ZLoadingDialog dialog = new ZLoadingDialog(this);
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
            setContentView(R.layout.choose_stores_activity);
        mToolbar= (Toolbar) findViewById(R.id.tl_choose_stores);
        mToolbar.setOnClickListener(this);
        mToolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
         rvStore= (RecyclerView) findViewById(R.id.rv_sore);
        linearLayoutManager = new GridLayoutManager(this, 2);
        storeBeanArrayList = new ArrayList<>();

        EventBus.getDefault().register(this);

        storeRecyclerAdapter = new StoreRecyclerAdapter(this, storeBeanArrayList);

        storeRecyclerAdapter.setOnItemClick(new StoreRecyclerAdapter.onItemClick() {
            @Override
            public void onViewClick(View view, final int position) {
                final String chooseId = storeBeanArrayList.get(position).getId();
                final String storestatus = storeBeanArrayList.get(position).getStoreStatus();
//                if (storestatus.contains("打烊")){
//                    Hint.Short(ChooseStoreActivity.this,"该店铺已打烊！");
//                    return;
//                }
                dialog.setLoadingBuilder(Z_TYPE.STAR_LOADING)//设置类型
                        .setLoadingColor(Color.BLACK)//颜色
                        .setHintText("Loading...")
                        .show();
                new Thread(new Runnable(){
                    @Override
                    public void run() {

                        Log.d(TAG, "run: position= "+position+" chooseId= "+chooseId);
                        ChooseitemStore(chooseId);
                    }
                }).start();

            }
        });
        rvStore.setLayoutManager(linearLayoutManager);
        rvStore.setAdapter(storeRecyclerAdapter);

        try {
            dialog.setLoadingBuilder(Z_TYPE.STAR_LOADING)//设置类型
                            .setLoadingColor(Color.BLACK)//颜色
                            .setHintText("Loading...")
                            .show();
            new Thread(new Runnable(){
                @Override
                public void run() {
//
                    chooiceStore();
                }
            }).start();
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public void ChooseitemStore(final String storeId) {

        Log.d(TAG, "ChooseitemStore: ");
        //清楚之前所有选择信息
       MessageEvent messageEvent = new MessageEvent("allDelete");

        EventBus.getDefault().post(messageEvent);

            String url = App.API_URL+"seller/store/select";
            Map<String,String> stringMap = new HashMap<>();
            stringMap.put("store_id", storeId);
        App.store.put("storeId",storeId);
        App.store.commit();
        Xutils.getInstance().post(url, stringMap, new Xutils.XCallBack() {
            @Override
            public void onResponse(String str) {
//            final String str = result.body().string();
                dialog.dismiss();
                try {
                    Log.e("AAAAAAA",str);
                    JSONObject mjsonObjects = new JSONObject(str);
                    String result = mjsonObjects.getString("status");
                    String code = mjsonObjects.getString("code");
                    String message = mjsonObjects.getString("message");
                    if (result.equals("true")) {
                        JSONObject mjsonObject =mjsonObjects.getJSONObject("data");
                        JSONObject mjsonObjectss =mjsonObject.getJSONObject("store");
                        App.store.put("storeinfo",mjsonObjectss.toString());
                        App.store.commit();
                    }else {
                        Hint.Short(ChooseStoreActivity.this,message+">>>"+code);
                   if (code.equals("9001")){
                       Hint.Short(ChooseStoreActivity.this,"登录超时");
                   }
                    }
                   Intent intent = new Intent(ChooseStoreActivity.this,MainActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                    startActivity(intent);

                    MessageEvent messageEvent1 = new MessageEvent("chooseStoreSucess");
                    EventBus.getDefault().post(messageEvent1);

                    finish();
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

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tl_choose_stores:
                App.removeUser();
                finish();

                break;
//            case R.id.tl_choose_stores:
//                finish();
//                break;
        }
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.tl_choose_stores:
                finish();
                break;
        }
        return true;
    }
    public void chooiceStore(){
        String url = App.API_URL+"seller/store/index";
        Map<String,String> stringMap = new HashMap<>();
        stringMap.put("type_id", "9");
        Xutils.getInstance().get(url, stringMap, new Xutils.XCallBack() {
            @Override
            public void onResponse(String str) {
                try {
                    dialog.dismiss();
                    JSONObject mjsonObjects = new JSONObject(str);
                    String result = mjsonObjects.getString("status");
                    String message = mjsonObjects.getString("message");
                    if (result.equals("true")) {
                        JSONObject mjsonObject = mjsonObjects.getJSONObject("data");
                        JSONArray storeArray = mjsonObject.getJSONArray("store_types");
                        JSONArray storeAr = mjsonObject.getJSONArray("stores");
                        for (int i=0;i<storeAr.length();i++){
//                            storeBeanArrayList.add(new storeBean(storeArray.getJSONObject(i).getString("name"),storeAr.getJSONObject(i).getString("store_type")
//                                    , storeArray.getJSONObject(i).getString("ico"), storeAr.getJSONObject(i).getString("service_status")));
//                        storeBeanArrayList.add(new storeBean(storeArray.getJSONObject(i).getString("name"),"",""));
                         for (int j=0;j<storeArray.length();j++){
                            if (storeArray.getJSONObject(j).getString("id").equals(storeAr.getJSONObject(i).getString("store_type"))){
                                storeBeanArrayList.add(new storeBean(storeAr.getJSONObject(i).getString("id"),storeAr.getJSONObject(i).getString("name"), storeArray.getJSONObject(j).getString("name"),storeAr.getJSONObject(i).getString("logo"), storeAr.getJSONObject(i).getString("service_status")));
                            }
                        }}
//
                        storeRecyclerAdapter.updateData(storeBeanArrayList);
                    } else {
                        Hint.Short(ChooseStoreActivity.this, message);
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


    class XuanAdapter extends JsonAdapter {

        ViewHolder holder = null;
        Context mContext;

        public XuanAdapter(Context context) {
            super(context);
            this.mContext = context;
            // TODO Auto-generated constructor stub
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {

            if (convertView == null) {
                convertView = inflater.inflate(R.layout.xuan_store_item, parent, false);
                holder = new ViewHolder();
                holder.txt_name = (TextView) convertView.findViewById(R.id.tv_store_name);
                 holder.iv_store_icon = (ImageView) convertView.findViewById(R.id.iv_store_icon);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }

            try {
                final JSONObject o = getList().getJSONObject(position);
                    holder.txt_name.setText(o.getString("name"));
                  //  Hint.Short(context,App.API_URL+"/"+o.getString("ico"));
                ImageLoader.getInstance().init(ImageLoaderConfiguration.createDefault(mContext));
                    ImageLoader.getInstance().displayImage(App.API_URL+o.getString("logo"), holder.iv_store_icon);
            } catch (Exception e) {
            }
            return convertView;
        }


        final class ViewHolder {
            TextView txt_name;
            ImageView iv_store_icon;

        }
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();

        EventBus.getDefault().unregister(this);
    }
    @Subscribe(threadMode = ThreadMode.MAIN, sticky = true)
    public void onEvent(MessageEvent messageEvent) {



    }
}
