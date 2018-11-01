package com.example.yzeng.myebaycloneproject.ui.UserInfo;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.yzeng.myebaycloneproject.R;
import com.example.yzeng.myebaycloneproject.ui.MainActivity;
import com.example.yzeng.myebaycloneproject.ui.helperclasses.SPfiles;
import com.example.yzeng.myebaycloneproject.ui.helperclasses.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class LoginFragment extends Fragment implements View.OnClickListener {
    private static final String TAG = "LoginFragment";
    private EditText et_mobile, et_password;
    private Button btn_signin;
    private TextView tv_create_account;
    private ProgressDialog progress;
    SharedPreferences sp;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.login, container, false);
        et_mobile = view.findViewById(R.id.et_mobile_login);
        et_password = view.findViewById(R.id.et_password_login);
        tv_create_account = view.findViewById(R.id.tv_newAccount);
        tv_create_account.setOnClickListener(this);

        btn_signin = view.findViewById(R.id.btn_signin);
        btn_signin.setOnClickListener(this);

        String phone = SPfiles.getphone(getContext());

        et_mobile.setText(phone);
        return view;
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.btn_signin) {
            //initilize the progress dialog while log in
            progress = new ProgressDialog(getActivity());
            progress.setTitle("Loging");
            progress.setCancelable(false);
            progress.show();


            SPfiles.setphone(getContext(), et_mobile.getText().toString());

            if (!android.util.Patterns.PHONE.matcher(et_mobile.getText().toString()).matches()) {
//                !android.util.Patterns.PHONE.matcher(et_mobile.getText().toString()).matches()
                progress.dismiss();
                Toast.makeText(getContext(), "", Toast.LENGTH_SHORT).show();
            }
            //check whether the mobile for log in is valid
            else if (et_mobile.getText().toString().length() != 10) {
                progress.dismiss();
                Toast.makeText(getContext(), "phone length !=10", Toast.LENGTH_SHORT).show();
            } else if (et_password.getText().toString().length() < 6) {
                progress.dismiss();
                Toast.makeText(getContext(), "password<6", Toast.LENGTH_SHORT).show();
            } else {
                Response.Listener<JSONArray> listener = new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {
                        try {
                                /*
                                JsonObject:
                                [{"msg":"success","id":"1249","firstname":"patel","lastname":"husain","email":"vansh3vns@gmail.com","mobile":"55565454","appapikey ":"4e200ca562c8f3dcda5f025b72508644"}]
                                 */
                            for (int i = 0; i < response.length(); i++) {
                                JSONObject jsonObject = response.getJSONObject(i);
                                String msg = jsonObject.getString("msg");
                                String id = jsonObject.getString("id");
                                String firstname = jsonObject.getString("firstname");
                                String lastname = jsonObject.getString("lastname");
                                String email = jsonObject.getString("email");
                                String mobile = jsonObject.getString("mobile");
                                String appapikey = jsonObject.getString("appapikey ");

                                SPfiles.setUserInfo(getContext(), id, firstname, lastname, email, mobile, appapikey);

                                Intent intent = new Intent(getActivity(), MainActivity.class);
                                startActivity(intent);

                                progress.dismiss();
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                };

                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.e("error", error.getMessage());
                        Log.i("error", error.getMessage());
                        Toast.makeText(getContext(), "login error", Toast.LENGTH_SHORT).show();
                        progress.dismiss();
                    }
                };

                //create the Request Queue and add the login Request into the Queue
                Log.i(TAG, "onClick: " + et_mobile.getText().toString() + et_password.getText().toString());

                JsonArrayRequest jsonArrayRequest = Volley.getMyVolly()
                        .loginRequest(et_mobile.getText().toString(), et_password.getText().toString(), listener, errorListener);
                RequestQueue requestQueue = com.android.volley.toolbox.Volley.newRequestQueue(getActivity());
                Log.i(TAG, "onClick: " + requestQueue);
                requestQueue.add(jsonArrayRequest);

            }

        } else if (id == R.id.tv_newAccount) {

            getFragmentManager().beginTransaction()
                    .replace(R.id.MainPage, new NewUserFragment())
                    .addToBackStack(null)
                    .commit();


        }
    }

}
