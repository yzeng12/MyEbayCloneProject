package com.example.yzeng.myebaycloneproject.ui.helperclasses;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;

public class Volley {
    private static final String TAG = "Volley";
    private static Volley myVolly;
    private final String URL = "http://rjtmobile.com/aamir/e-commerce/android-app/";

    private Volley(){

    }

    public static Volley getMyVolly(){
        if(myVolly == null){
            synchronized (Volley.class){
                if(myVolly == null){
                    myVolly = new Volley();
                }
            }
        }
        return myVolly;
    }

     public JsonArrayRequest loginRequest(String mobile, String password, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener){
        //http://rjtmobile.com/aamir/e-commerce/android-app/shop_login.php?mobile=55565454&password=7011
        String loginUrl = URL + "shop_login.php?" + "mobile=" + mobile + "&password=" + password;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(loginUrl, listener, errorListener);
        return jsonArrayRequest;
    }


    public StringRequest newRegisterRequest(String firstname, String lastname, String address, String mobile, String email, String password,
                                            Response.Listener<String> listener, Response.ErrorListener errorListener){
        //http://rjtmobile.com/aamir/e-commerce/android-app/shop_reg.php?fname=aamir&lname=husain&address=noida& email=aa@gmail.com&mobile=55565454&password=7011
        Log.i(TAG, "newRegisterRequest: called");
        String registerUrl = URL + "shop_reg.php?" + "fname=" + firstname + "&lname="
                + lastname + "&address=" + address + "&email=" + email + "&mobile="
                + mobile + "&password=" + password;
        StringRequest stringRequest = new StringRequest(registerUrl, listener, errorListener);
        return stringRequest;
    }
}
