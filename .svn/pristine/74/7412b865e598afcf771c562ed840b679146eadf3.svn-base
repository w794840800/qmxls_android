package com.qimai.xinlingshou.utils;

import android.util.Log;

import java.util.concurrent.TimeUnit;

import io.reactivex.Observable;
import io.reactivex.ObservableSource;
import io.reactivex.ObservableTransformer;
import io.reactivex.functions.Consumer;
import io.reactivex.functions.Function;

public class testTransformer {


    private static final String TAG = "testTransformer";


    public static ObservableTransformer
    transformer(){


        return new ObservableTransformer<Integer
                ,String>() {
            @Override
            public ObservableSource <String>apply(Observable<Integer> upstream) {
                return upstream.map(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) throws Exception {
                        return integer+"";
                    }
                });
            }
        };
    }
    public static void main(String[]args){

        Observable.just(123,456)
                .take(3, TimeUnit.SECONDS)
                //.compose(transformer())
                .subscribe(new Consumer<Integer>() {
                    @Override
                    public void accept(Integer o) throws Exception {

                        //Log.d(TAG, "accept: o= "+o);

                        System.out.println(o);
                    }
                });

    }


}
