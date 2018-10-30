package com.example.yzeng.myebaycloneproject.ui.helperclasses;

import android.content.Context;
import android.content.SharedPreferences;

public class SPfiles  {
    public static SharedPreferences getSp(Context context){
        return context.getSharedPreferences( "MySharedPreference info", Context.MODE_PRIVATE );
    }

    public static void setUserInfo(Context context, String id, String firstname, String lastname, String email, String mobile, String appapikey){
        SharedPreferences.Editor editor = getSp(context).edit();
        editor.putString("id", id);
        editor.putString("firstname", firstname);
        editor.putString("lastname", lastname);
        editor.putString("email", email);
        editor.putString("mobile", mobile);
        editor.putString("appapikey", appapikey);
        editor.commit();
    }

    public static void updateUserInfo(Context context, String firstname, String lastname, String email, String mobile){
        SharedPreferences.Editor editor = getSp(context).edit();
        editor.putString("firstname", firstname);
        editor.putString("lastname", lastname);
        editor.putString("email", email);
        editor.putString("mobile", mobile);
        editor.commit();
    }

    public static void clearUserInfo(Context context) {
        SharedPreferences.Editor editor = getSp(context).edit();
        editor.putString("id", null);
        editor.putString("firstname", null);
        editor.putString("lastname", null);
        editor.putString("email", null);
        editor.putString("mobile", null);
        editor.putString("appapikey", null);
        editor.commit();
    }

    public static void setphone(Context context, String mobile){
        SharedPreferences.Editor editor = getSp( context ).edit();
        editor.putString( "phone", mobile );
        editor.commit();
    }

    public static String getphone(Context context){
        return getSp( context ).getString( "phone", null );
    }


}
