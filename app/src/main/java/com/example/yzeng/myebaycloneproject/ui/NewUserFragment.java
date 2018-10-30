package com.example.yzeng.myebaycloneproject.ui;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.telephony.PhoneNumberUtils;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.example.yzeng.myebaycloneproject.R;
import com.example.yzeng.myebaycloneproject.ui.helperclasses.MainControllor;
import com.example.yzeng.myebaycloneproject.ui.helperclasses.Volley;

public class NewUserFragment extends Fragment implements View.OnClickListener {
    private EditText et_firstname, et_lastname, et_address, et_mobile, et_email, et_password;
    private Button btn_create_account;
    private TextView tv_gotologin;
    private static final String TAG = "NewUserFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.create_account, container, false);

        //initialize the views and set click listener
        et_firstname = view.findViewById(R.id.et_firstname);
        et_lastname = view.findViewById(R.id.et_lastname);
        et_address = view.findViewById(R.id.et_address);
        et_mobile = view.findViewById(R.id.et_mobile);
        et_email = view.findViewById(R.id.et_email);
        et_password = view.findViewById(R.id.et_password);

        btn_create_account = view.findViewById(R.id.btn_create_account);
        btn_create_account.setOnClickListener(this);

        tv_gotologin = view.findViewById(R.id.tv_gotologin);
        tv_gotologin.setOnClickListener(this);

        return view;
    }

    @Override
    public void onClick(View v) {
        int id = v.getId();

        if (id == R.id.tv_gotologin) {
            getActivity().getSupportFragmentManager().beginTransaction()
                    .replace(R.id.MainPage, new LoginFragment())
                    .addToBackStack(null)
                    .commit();
        } else if (id == R.id.btn_create_account) {
            if (TextUtils.isEmpty(et_firstname.getText().toString()) || TextUtils.isEmpty(et_lastname.getText().toString())
                    || TextUtils.isEmpty(et_address.getText().toString()) || TextUtils.isEmpty(et_mobile.getText().toString())
                    || TextUtils.isEmpty(et_email.getText().toString()) || TextUtils.isEmpty(et_password.getText().toString())) {
                Toast.makeText(getActivity().getBaseContext(), "something empty", Toast.LENGTH_SHORT).show();

            } else if (!Patterns.EMAIL_ADDRESS.matcher(et_email.getText().toString()).matches()) {
                Toast.makeText(getContext(), "Please input a validated email address", Toast.LENGTH_SHORT).show();
                return;
            } else if (!PhoneNumberUtils.isGlobalPhoneNumber(et_mobile.getText().toString()) || !Patterns.PHONE.matcher(et_mobile.getText().toString()).matches()) {
                Toast.makeText(getContext(), "Please input a validated phone number", Toast.LENGTH_SHORT).show();
                return;
            } else if (et_mobile.getText().toString().length() != 10) {
                Toast.makeText(getActivity().getBaseContext(), "mobile length !=10", Toast.LENGTH_SHORT).show();

            } else if (et_password.getText().toString().length() < 6) {
                Toast.makeText(getContext(), "The password should be at least 6 digits", Toast.LENGTH_SHORT).show();

            } else {
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getActivity().getBaseContext(), response, Toast.LENGTH_SHORT).show();

                        Log.i(TAG, "onClick: after good response");
                    }
                };

                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Toast.makeText(getActivity().getBaseContext(), error.getMessage(), Toast.LENGTH_SHORT).show();
                        Log.i("error message", error.getMessage());
                    }
                };
                Log.i(TAG, "onClick: before request");
                StringRequest stringRequest = Volley.getMyVolly().newRegisterRequest(et_firstname.getText().toString(), et_lastname.getText().toString()
                        , et_address.getText().toString(), et_mobile.getText().toString(), et_email.getText().toString(), et_password.getText().toString()
                        , listener, errorListener);

                Log.i(TAG, "onClick: before controller");
                MainControllor.getAppInstance().addToRequestQueue(stringRequest);
                Log.i(TAG, "onClick: before switch");
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.MainPage, new LoginFragment()).commit();

            }

        }
    }
}
