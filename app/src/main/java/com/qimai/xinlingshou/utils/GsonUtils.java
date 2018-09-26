package com.qimai.xinlingshou.utils;

import com.google.gson.Gson;

public class GsonUtils {

    private static class SingleInstance{

        private static Gson gson = new Gson();

    }

    public static Gson getInstance(){


        return SingleInstance.gson;
    }
}
