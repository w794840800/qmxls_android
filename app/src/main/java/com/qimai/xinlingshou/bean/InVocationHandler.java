package com.qimai.xinlingshou.bean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class InVocationHandler implements InvocationHandler {


    Object object ;

    public InVocationHandler(Object o){

        object = o;

    }
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

        return method.invoke(object,args);
    }
}
