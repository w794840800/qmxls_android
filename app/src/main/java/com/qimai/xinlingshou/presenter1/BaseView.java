package com.qimai.xinlingshou.presenter1;

/**
 * Created by wanglei on 18-7-3.
 */

public interface BaseView<P extends BasePresenter> {


    void setPresenter(P presenter);

}
