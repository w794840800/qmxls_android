package com.qimai.xinlingshou.bean;

import org.litepal.crud.LitePalSupport;

public class GoodsCategoryBean extends LitePalSupport {

    private String categoty_id;
    private String goodsId;
    private String goodsName;

    public String getCategoty_id() {
        return categoty_id;
    }

    public void setCategoty_id(String categoty_id) {
        this.categoty_id = categoty_id;
    }

    public String getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(String goodsId) {
        this.goodsId = goodsId;
    }

    @Override
    public String toString() {
        return "GoodsCategoryBean{" +
                "categoty_id='" + categoty_id + '\'' +
                ", goodsId='" + goodsId + '\'' +
                ", goodsName='" + goodsName + '\'' +
                '}';
    }

    public String getGoodsName() {
        return goodsName;
    }


    @Override
    public boolean equals(Object obj) {

        GoodsCategoryBean goodsCategoryBean = (GoodsCategoryBean) obj;
        return this.getGoodsId().equals(goodsCategoryBean.goodsId);
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }
}
