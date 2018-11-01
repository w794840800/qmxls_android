package com.qimai.xinlingshou.bean;

import org.litepal.crud.LitePalSupport;

import java.util.ArrayList;

public class CategoryBean  {

    private String categoryId;
    private ArrayList<String> category;
    private String name;
    private String storeId;
    private String goodsId;
    private boolean isSelected;
    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStoreId() {
        return storeId;
    }

    public void setStoreId(String storeId) {
        this.storeId = storeId;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    public boolean isSelected() {
        return isSelected;
    }

    public void setSelected(boolean selected) {
        isSelected = selected;
    }

    @Override
    public String toString() {
        return "CategoryBean{" +
                "categoryId='" + categoryId + '\'' +
                ", name='" + name + '\'' +
                ", storeId='" + storeId + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", isSelected=" + isSelected +
                '}';
    }


    public ArrayList<String> getCategory() {
        return category;
    }

    public void setCategory(ArrayList<String> category) {
        this.category = category;
    }
}
