package com.qimai.xinlingshou.bean;

/**
 * Created by wanglei on 18-7-12.
 */

public class VipInfo {

    private String mobile;
    private String name;
    private String account;

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }




    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "VipInfo{" +
                "mobile='" + mobile + '\'' +
                ", name='" + name + '\'' +
                ", account='" + account + '\'' +
                '}';
    }
}
