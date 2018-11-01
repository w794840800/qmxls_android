package com.qimai.xinlingshou.db;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.qimai.xinlingshou.App;

public class DBManager {


    private static DBManager dbManager;
    TestSQLiteDBHelper testSQLiteDBHelper ;
    SQLiteDatabase sqLiteDatabase;
    public DBManager(){

        testSQLiteDBHelper = new TestSQLiteDBHelper(App
                .getBaseApplication(),"select_enable_disenable.db",null,1);

        sqLiteDatabase = testSQLiteDBHelper.getWritableDatabase();


    }

    public static DBManager getInstance(){


        if (dbManager==null){

            dbManager = new DBManager();

        }
        return dbManager;
    }

    /*public Cursor query(){

//        sqLiteDatabase.query()

    }*/


}
