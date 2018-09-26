package com.qimai.xinlingshou.model;


import android.util.Log;

import com.qimai.xinlingshou.Retrofit.ApiService;
import com.qimai.xinlingshou.Retrofit.RetrofitUtils;
import com.qimai.xinlingshou.bean.NetWorkBean;
import com.qimai.xinlingshou.bean.UserIdBean;
import com.qimai.xinlingshou.callback.NetWorkCallBack;
import com.qimai.xinlingshou.callback.RequestObserver;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * 跟客户登录相关请求都在这
 * */
public class ClientModel extends BaseModel{

    private static final String TAG = "ClientModel";
    /***
     *
     * 扫码结果转成userId
     * */

    public void changeScanDataToUserId(String scanCode, final NetWorkCallBack netWorkCallBack){



        changeThread(RetrofitUtils.getRetrofitUtils()
                        .getApiService().changeScanCodeToId(scanCode)
                , new RequestObserver<UserIdBean>() {


                    @Override
                    protected void onStart(Disposable d) {

                        addDispose(d);
                    }

                    @Override
                    protected void onSucess(UserIdBean data) {

                        netWorkCallBack.onSucess(data.getUser_id());

                    }

                    @Override
                    protected void onFailed(String message) {

                        netWorkCallBack.onFailed(message);

                    }
                });



    }


/**
 * 查询vip信息
 * */
public void searchUserInfo(String number, String user_id, int type, final NetWorkCallBack netWorkCallBack){

        changeThread(RetrofitUtils.getRetrofitUtils().getApiService()
                        .getVipInfo(number, user_id, type)
                , new Observer() {
                    @Override
                    public void onSubscribe(Disposable d) {

                    }

                    @Override
                    public void onNext(Object o) {

                        Log.d(TAG, "onNext: content= "+o.toString());
                    }

                    @Override
                    public void onError(Throwable e) {

                    }

                    @Override
                    public void onComplete() {

                    }
                }


        );


}
}