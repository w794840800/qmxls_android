package com.qimai.xinlingshou.callback;

import android.util.Log;

import com.qimai.xinlingshou.bean.NetWorkBean;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

public  abstract class RequestObserver<T> implements Observer<NetWorkBean<T>> {

    private static final String TAG = "RequestObserver";
    @Override
    public void onSubscribe(Disposable d) {
        onStart(d);
    }

    protected abstract void onStart(Disposable d);

    @Override
    public void onNext(NetWorkBean<T> o) {

        Log.d(TAG, "onNext: o.getData= "+o.getData());
        onSucess(o.getData());
    }

    protected abstract void onSucess(T data);

    @Override
    public void onError(Throwable e) {

        Log.d(TAG, "onError: e= "+e.getMessage());
        onFailed(e.getMessage());
    }

    protected abstract void onFailed(String message);

    @Override
    public void onComplete() {

    }
}
