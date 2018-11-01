package com.example.yzeng.myebaycloneproject.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME = "database";
    public static final int DATABASE_VERSION = 1;
    public DBHelper(Context context) {
        super( context, DATABASE_NAME, null, DATABASE_VERSION );
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String sql = "create table shoppingCart(" +
                "id INTEGER primary key autoincrement," +
                "mobile varchar," +
                "pid varchar," +
                "pname varchar," +
                "quantity INTEGER," +
                "prize varchar," +
                "imageurl varchar)";
        db.execSQL(sql);

//        String likeSQL = "create table wishCart(" +
//                "id INTEGER primary key autoincrement," +
//                "mobile varchar," +
//                "pid varchar," +
//                "pname varchar," +
//                "quantity INTEGER," +
//                "prize varchar," +
//                "imageurl varchar)";
//        db.execSQL(likeSQL);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
