package com.qimai.xinlingshou.presenter;

import com.qimai.xinlingshou.bean.goodsBean;

import java.util.ArrayList;

import io.reactivex.Observable;

/**
 * Created by wanglei on 18-6-29.
 */

public interface Constract {


    interface MainModel extends BaseModel{

        Observable getDataFromSql();


    }

    interface MainView extends BaseView{

        void updateVIew(ArrayList<goodsBean>arrayList);

    }

    abstract class MainPresenter extends BasePresenter<MainView,MainModel>{


        abstract void getDateFromSQL();


    }



}
