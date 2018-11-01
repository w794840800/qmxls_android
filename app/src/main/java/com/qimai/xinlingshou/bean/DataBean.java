package com.qimai.xinlingshou.bean;

import java.util.ArrayList;

public class DataBean {

    private listBean list;

    public listBean getList() {
        return list;
    }

    public void setList(listBean list) {
        this.list = list;
    }


    class listBean{

        private ArrayList<ValueCardBean> data;

        public ArrayList<ValueCardBean> getData() {
            return data;
        }

        public void setData(ArrayList<ValueCardBean> data) {
            this.data = data;
        }

    }




}