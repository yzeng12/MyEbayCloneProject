package com.example.yzeng.myebaycloneproject.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.yzeng.myebaycloneproject.objects.ShoppingCartItem;

import java.util.ArrayList;
import java.util.List;


public class DB_DAO {
    private DBHelper DBHelper;
    private SQLiteDatabase DB;
    private String cartTable = "shoppingCart";
    private String wishTable = "wishCart";

    private static final String TAG = "DB_DAO";
    public DB_DAO(Context context) {
        DBHelper = new DBHelper(context);
    }

    public void openDatabase(){
        DB = DBHelper.getWritableDatabase();
    }

    public void closeDatabase(){
        DB.close();
    }

    public void addItemToCart(String mobile, String pid, String pname, int quantity, String prize, String image){
        ContentValues contentValues = new ContentValues();
        contentValues.put("mobile", mobile);
        contentValues.put("pid", pid);
        contentValues.put("pname", pname);
        contentValues.put("quantity", quantity);
        contentValues.put("prize", prize);
        contentValues.put("imageurl", image);
        this.DB.insertOrThrow(cartTable, null, contentValues);
    }

    public int verifyItemInCart(String id, String mobile) {
        int quantity = 0;
        String sql = "SELECT quantity FROM " + cartTable + " WHERE pid =" + " " + id + " " + "AND mobile =" + " " + mobile + " ";
        Cursor result = DB.rawQuery(sql, null);
        //Log.i(TAG, "verifyItemInCart: "+result.toString());
        for(result.moveToFirst(); !result.isAfterLast(); result.moveToNext()){

            quantity = result.getInt(0);
        }

        Log.i(TAG, "verifyItemInCart: "+quantity);
        return quantity;
    }

    public void updataCartQuantity(int newQuantity, String Itemid, String mobile){
        String sql = "UPDATE " + cartTable + " SET quantity =" + " " + newQuantity + " " + "WHERE pid=" + " " + Itemid + " " + "AND mobile =" + " " + mobile + " ";
        DB.execSQL(sql);
    }


    public List<ShoppingCartItem> getCartList(String mobile){
        List<ShoppingCartItem> list = new ArrayList<>();
        String sql = "SELECT pid, pname, quantity, prize, imageurl From " + cartTable + " WHERE mobile =" + " " + mobile + " ";
        Cursor result = DB.rawQuery(sql +"", null);
        for(result.moveToFirst(); !result.isAfterLast(); result.moveToNext()){
            ShoppingCartItem shoppingCartItem = new ShoppingCartItem(result.getString(0),
                    result.getString(1),
                    Integer.valueOf(result.getString(2)),
                    result.getString(3),
                    result.getString(4));
            list.add(shoppingCartItem);
        }

        return list;
    }

    public void deleteItem(String mobile){
        String sql = "DELETE FROM " + cartTable + " WHERE mobile=" +  " " + mobile + " ";
        DB.execSQL(sql);
    }

    public void deleteItemCart(String mobile, String pid){
        String sql = "DELETE FROM " + cartTable + " WHERE mobile=" +  " " + mobile + " " + "AND pid =" + " " + pid + " ";
        DB.execSQL(sql);
    }

}
