/*
 * Copyright (C) 2015 Square, Inc.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.qimai.xinlingshou.Retrofit;

import android.util.Log;

import com.google.gson.Gson;
import com.qimai.xinlingshou.bean.NetWorkBean;

import java.io.IOException;
import java.lang.reflect.Type;

import okhttp3.ResponseBody;
import retrofit2.Converter;

final class CustomGsonResponseBodyConverter<T> implements Converter<ResponseBody, T> {
  private final Gson gson;
  private final Type type;

  public static final String TAG = "GsonResponseBody";
  CustomGsonResponseBodyConverter(Gson gson, Type adapter) {
    this.gson = gson;
    this.type = adapter;
  }

  @Override public T convert(ResponseBody value) throws IOException {

    try{
    String value1 = value.string();
      Log.d(TAG, "convert: type= "+type+" values= "+value1);
    NetWorkBean userResultEntry = gson.fromJson(value1,NetWorkBean.class);

    if (userResultEntry.getStatus().equals("true")){
      return gson.fromJson(value1,type);
    }else{
     /* if (userResultEntry.getCode().equals("400")){

        String msg = userResultEntry.getMsg();
        if (msg.equals("账号或密码不正确")){

          ActivityManagers.getInstance().finishAll();
          Intent intent = new Intent(BaseApplication.getBaseApplicationContext()
          , LoginActivity.class);

          intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);

          intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

          BaseApplication.getBaseApplicationContext().startActivity(intent);

 }

        }*/



      throw new HttpResponseErrorException(userResultEntry.getCode(),userResultEntry.getMessage());
    }

    }finally {
      value.close();
    }

}
}
