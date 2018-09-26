package com.qimai.xinlingshou.utils;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Environment;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

/**
 * Created by wanglei on 18-7-16.
 */

public class DownloadUtils2 {


    @SuppressLint("StaticFieldLeak")
    void start(){


        new AsyncTask<String,Integer,Boolean>(){

            @Override
            protected void onPreExecute() {
                super.onPreExecute();
            }

            @Override
            protected void onPostExecute(Boolean aBoolean) {
                super.onPostExecute(aBoolean);
            }

            @Override
            protected void onProgressUpdate(Integer... values) {
                super.onProgressUpdate(values);
            }

            @Override
            protected Boolean doInBackground(String... strings) {

                try {
                    URL url = new URL("");

                    File file = new File(Environment.DIRECTORY_DOWNLOADS);
                    HttpURLConnection httpURLConnection = (HttpURLConnection) url.openConnection();

                    httpURLConnection.connect();
                    InputStream inputStream = null;
                    OutputStream outputStream = null;


                    int fileLenth = httpURLConnection.getContentLength();

                    inputStream = httpURLConnection.getInputStream();

                    outputStream = new FileOutputStream(file);


                    byte[]bytes = new byte[1024];

                    int n ;
                    while ((n=inputStream.read(bytes))!=-1){

                        outputStream.write(bytes,0,n);
                    }




                } catch (MalformedURLException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                }


                return null;
            }
        };
    }

}
