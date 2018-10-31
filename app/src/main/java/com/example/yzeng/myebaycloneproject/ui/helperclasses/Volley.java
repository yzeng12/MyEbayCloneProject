package com.example.yzeng.myebaycloneproject.ui.helperclasses;

import android.util.Log;

import com.android.volley.Response;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;

import org.json.JSONArray;
import org.json.JSONObject;

public class Volley {
    private static final String TAG = "Volley";
    private static Volley myVolly;
    private final String URL = "http://rjtmobile.com/aamir/e-commerce/android-app/";
    private final String CAT_URL = "http://rjtmobile.com/ansari/shopingcart/androidapp/";
    private final String BASE_URL_ORDER = "http://rjtmobile.com/aamir/e-commerce/android-app/";

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

    public StringRequest updateProfileRequest(String firstname, String lastname, String address, String mobile, String email,
                                              Response.Listener<String> listener, Response.ErrorListener errorListener){
        //http://rjtmobile.com/aamir/e-commerce/android-app/edit_profile.php?fname=aamir&lname=husain&address=noida& email=aa@gmail.com&mobile=55565454
        String updateProfileUrl = URL + "edit_profile.php?" + "fname=" + firstname + "&lname="
                + lastname + "&address=" + address + "&email=" + email + "&mobile="
                + mobile;

        StringRequest stringRequest = new StringRequest(updateProfileUrl, listener, errorListener);
        return stringRequest;
    }

    public JsonObjectRequest resetPasswordRequest(String mobile, String curPassword, String newPassword,
                                                  Response.Listener<JSONObject> listener, Response.ErrorListener errorListener){
        //http://rjtmobile.com/aamir/e-commerce/android-app/shop_reset_pass.php?&mobile=55565454&password=2&newpassword=456
        String resetPasswordUrl = URL + "shop_reset_pass.php?" + "&mobile=" + mobile + "&password=" + curPassword + "&newpassword=" + newPassword;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(resetPasswordUrl, null,listener, errorListener);
        return jsonObjectRequest;
    }

    public JsonObjectRequest categorysObject(String api_key, String user_id, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener){

        //http://rjtmobile.com/ansari/shopingcart/androidapp/cust_category.php?api_key=7057bc8168b477b9420aeca3fda3c868&user_id=1217

        String categoryUrl = CAT_URL + "cust_category.php?" + "api_key=" + api_key + "&user_id=" + user_id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(categoryUrl, null, listener, errorListener);
        return jsonObjectRequest;
    }

    public JsonObjectRequest subCategorysObject(String id, String api_key, String user_id, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener){
        //http://rjtmobile.com/ansari/shopingcart/androidapp/cust_sub_category.php?Id=107&api_key=7057bc8168b477b9420aeca3fda3c868&user_id=1217
        String subCategoryUrl = CAT_URL + "cust_sub_category.php?" + "Id=" + id + "&api_key=" + api_key + "&user_id=" + user_id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(subCategoryUrl, null,listener, errorListener);
        return jsonObjectRequest;
    }
}
