package com.qimai.xinlingshou.bean;

import android.content.ContentProvider;
import android.content.ContentValues;
import android.content.IntentFilter;
import android.content.UriMatcher;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.net.Uri;
import android.os.Environment;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

public class MyContentProvider extends ContentProvider {



    private static String AUTHORITY = "com.qimai.xinlingshou.provider";
    static UriMatcher uriMatcher;
    static {

        uriMatcher = new UriMatcher(UriMatcher.NO_MATCH);

        uriMatcher.addURI(AUTHORITY,"student",1);
        uriMatcher.addURI(AUTHORITY,"student/#",2);
        uriMatcher.addURI(AUTHORITY,"person",3);
        uriMatcher.addURI(AUTHORITY,"person/#",4);

    }
    MyHelper myHelper;
    SQLiteDatabase sqLiteDatabase;
    @Override
    public boolean onCreate() {

        myHelper = new MyHelper(getContext(),"select_enable_disenable.db",null,1);
        return true;
    }

    @Nullable
    @Override


    public Cursor query(@NonNull Uri uri, @Nullable String[] projection, @Nullable String selection, @Nullable String[] selectionArgs, @Nullable String sortOrder) {

        sqLiteDatabase = myHelper.getWritableDatabase();

        Cursor cursor = null;
        switch (uriMatcher.match(uri)){

            case 1:
                cursor = sqLiteDatabase.query("student",projection,selection,selectionArgs,null,null,sortOrder);
                break;

            case 2:
                String id = uri.getPathSegments().get(1);
                cursor =  sqLiteDatabase.query("student",projection,"id=?"
                        ,new String[]{id},null,null,sortOrder);


                break;

            case 3:

                cursor = sqLiteDatabase.query("person",projection,selection,selectionArgs,null,null,sortOrder);
                break;

            case 4:

                String id1 = uri.getPathSegments().get(1);
                cursor =  sqLiteDatabase.query("person",projection,"id=?"
                        ,new String[]{id1},null,null,sortOrder);

                break;
        }


        return cursor;
    }

    @Nullable
    @Override
    public String getType(@NonNull Uri uri) {
        return null;
    }

    @Nullable
    @Override
    public Uri insert(@NonNull Uri uri, @Nullable ContentValues values) {

        SQLiteDatabase sqLiteDatabase = myHelper.getReadableDatabase();

        Uri return_uri = null;
        long id ;
        switch (uriMatcher.match(uri)){

            case 1:

            case 2:

               id = sqLiteDatabase.insert("student",null,values);

                return_uri = Uri.parse("content://"+AUTHORITY+"student/"+id);

                break;

            case 3:

            case 4:

                id = sqLiteDatabase.insert("person",null,values);

                return_uri = Uri.parse("content://"+AUTHORITY+"person/"+id);

                break;

        }


        return return_uri;
    }

    @Override
    public int delete(@NonNull Uri uri, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }

    @Override
    public int update(@NonNull Uri uri, @Nullable ContentValues values, @Nullable String selection, @Nullable String[] selectionArgs) {
        return 0;
    }
}
