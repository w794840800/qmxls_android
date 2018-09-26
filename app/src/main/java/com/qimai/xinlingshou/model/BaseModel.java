package com.qimai.xinlingshou.model;

import io.reactivex.Observable;
import io.reactivex.Observer;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;
import io.reactivex.schedulers.Schedulers;

public class BaseModel {

    CompositeDisposable compositeDisposable;



    public void addDispose(Disposable disposable){

        if (compositeDisposable==null){

            compositeDisposable = new CompositeDisposable();
        }

        compositeDisposable.add(disposable);
    }
    public BaseModel(){

        compositeDisposable = new CompositeDisposable();
    }


    /***
     * 切换线程，请求在子线程，更新在主线程
     * **/

    protected void changeThread(Observable observable, Observer observer){

        observable.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(observer);

    }

    public void onDestory(){


        if (compositeDisposable!=null){

            compositeDisposable.dispose();
        }
    }

}
