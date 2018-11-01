package com.qimai.xinlingshou.bean;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

public class MyHelper extends SQLiteOpenHelper {


    public static final String STUDENT_SQL = "create table if not exists student(id integer,name text,age int)";

    public static final String PERSON_SQL = "create table if not exists person(id integer,name text,age int)";

    public MyHelper(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);


    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        db.execSQL(STUDENT_SQL);
        db.execSQL(PERSON_SQL);

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
