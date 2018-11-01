package com.qimai.xinlingshou.Retrofit;

import android.util.Log;

import com.qimai.xinlingshou.App;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

/**
 *
 * 添加请求
 *
 * **/
public class HeaderIntercept implements Interceptor {
    private static final String TAG = "HeaderIntercept";
    @Override
    public Response intercept(Chain chain) throws IOException {

        Request request = chain.request();

        request = request.newBuilder()
                .addHeader("Accept","*/*; v=1.0")
                .addHeader("Referer", App.API_URL)
                .addHeader("Qm-From","android")
                .addHeader("Qm-From-Type","ptfwil")
                .addHeader("Qm-Account-Token",App.store.getString("cookie_auth"))
                .addHeader("User-Agent","wechatdevtools appservice port/62739")
                .build();

        Log.d(TAG, "intercept: request= "+request.toString());

        return chain.proceed(request);
    }
}
