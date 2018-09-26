package com.qimai.xinlingshou.Retrofit;

import android.util.Log;

import com.qimai.xinlingshou.App;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitUtils {

    private static final String TAG = "RetrofitUtils";

    private OkHttpClient okHttpClient;

    Retrofit retrofit;
    public static RetrofitUtils retrofitUtils = new RetrofitUtils();

    ApiService apiService;
    HttpLoggingInterceptor httpLoggingInterceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
        @Override
        public void log(String message) {

            Log.d(TAG, "log: message= "+message);
        }
    });
    private RetrofitUtils(){

        okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(httpLoggingInterceptor)
                .addInterceptor(new HeaderIntercept())
                .build();
        retrofit = new Retrofit.Builder()
                .baseUrl(App.API_URL)
                .client(okHttpClient)
                .addConverterFactory(CustomGsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        apiService = retrofit.create(ApiService.class);

    }


    public ApiService getApiService() {

        return apiService;
    }

    public static RetrofitUtils getRetrofitUtils() {
        return retrofitUtils;
    }
}
