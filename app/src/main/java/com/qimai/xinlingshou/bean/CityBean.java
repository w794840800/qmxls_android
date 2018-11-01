package com.qimai.xinlingshou.bean;

public class CityBean {

    private String tag;
    private String city;


    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public String toString() {
        return "CityBean{" +
                "tag='" + tag + '\'' +
                ", city='" + city + '\'' +
                '}';
    }
}
