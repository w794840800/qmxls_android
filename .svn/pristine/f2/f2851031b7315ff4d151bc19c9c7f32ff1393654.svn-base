package com.qimai.xinlingshou;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by wanglei on 18-7-13.
 */

public class testAidl implements Parcelable{


    private String name;
    private int age;

    public testAidl(){


    }
    public testAidl(Parcel in) {
        name = in.readString();
        age = in.readInt();
    }

    public static final Creator<testAidl> CREATOR = new Creator<testAidl>() {
        @Override
        public testAidl createFromParcel(Parcel in) {
            return new testAidl(in);
        }

        @Override
        public testAidl[] newArray(int size) {
            return new testAidl[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeInt(age);
    }

    @Override
    public String toString() {
        return "testAidl{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
