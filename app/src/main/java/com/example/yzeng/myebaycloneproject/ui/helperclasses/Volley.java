package com.example.yzeng.myebaycloneproject.ui.helperclasses;

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

    private Volley() {
    }

    public static Volley getMyVolly() {
        if (myVolly == null) {
            synchronized (Volley.class) {
                if (myVolly == null) {
                    myVolly = new Volley();
                }
            }
        }
        return myVolly;
    }

    public JsonArrayRequest loginRequest(String mobile, String password, Response.Listener<JSONArray> listener, Response.ErrorListener errorListener) {
        String URL_login = URL + "shop_login.php?" + "mobile=" + mobile + "&password=" + password;
        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(URL_login, listener, errorListener);
        return jsonArrayRequest;
    }


    public StringRequest RegisterRequest(String firstname, String lastname, String address, String mobile, String email, String password, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        String URL_regist = URL + "shop_reg.php?" + "fname=" + firstname + "&lname=" + lastname + "&address=" + address + "&email=" + email + "&mobile=" + mobile + "&password=" + password;
        StringRequest stringRequest = new StringRequest(URL_regist, listener, errorListener);
        return stringRequest;
    }

    public StringRequest ProfileRequest(String firstname, String lastname, String address, String mobile, String email, Response.Listener<String> listener, Response.ErrorListener errorListener) {
        String URL_profile = URL + "edit_profile.php?" + "fname=" + firstname + "&lname=" + lastname + "&address=" + address + "&email=" + email + "&mobile=" + mobile;
        StringRequest stringRequest = new StringRequest(URL_profile, listener, errorListener);
        return stringRequest;
    }

    public JsonObjectRequest resetPasswordRequest(String mobile, String curPassword, String newPassword, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        String URL_password = URL + "shop_reset_pass.php?" + "&mobile=" + mobile + "&password=" + curPassword + "&newpassword=" + newPassword;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(URL_password, null, listener, errorListener);
        return jsonObjectRequest;
    }

    public JsonObjectRequest MainCatRequest(String api_key, String user_id, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        String URL_main = CAT_URL + "cust_category.php?" + "api_key=" + api_key + "&user_id=" + user_id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(URL_main, null, listener, errorListener);
        return jsonObjectRequest;
    }

    public JsonObjectRequest subCatRequest(String id, String api_key, String user_id, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        String URL_sub = CAT_URL + "cust_sub_category.php?" + "Id=" + id + "&api_key=" + api_key + "&user_id=" + user_id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(URL_sub, null, listener, errorListener);
        return jsonObjectRequest;
    }

    public JsonObjectRequest ItemListRequest(String cid, String scid, String api_key, String user_id, Response.Listener<JSONObject> listener, Response.ErrorListener errorListener) {
        String URL_Item = CAT_URL + "product_details.php?" + "cid=" + cid + "&scid=" + scid + "&api_key=" + api_key + "&user_id=" + user_id;
        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest(URL_Item, null, listener, errorListener);
        return jsonObjectRequest;
    }
}
