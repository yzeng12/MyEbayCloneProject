package com.example.yzeng.myebaycloneproject.ui.UserInfo;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.example.yzeng.myebaycloneproject.R;
import com.example.yzeng.myebaycloneproject.ui.helperclasses.SPfiles;
import com.example.yzeng.myebaycloneproject.ui.helperclasses.Volley;

import org.json.JSONObject;


public class ChangeProfileFragment extends Fragment implements View.OnClickListener {
    private EditText et_firstname, et_lastname, et_mobile;
    private EditText et_email, et_cur_password, et_new_password;
    private Button btn_update_profile, btn_reset_password;
    private static final String TAG = "ChangeProfileFragment";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.updateprofile, container, false);
        et_firstname = view.findViewById(R.id.et_Pfirstname);
        et_lastname = view.findViewById(R.id.et_Plastname);
        et_mobile = view.findViewById(R.id.et_Pmobile);
        et_email = view.findViewById(R.id.et_Pemail);

        et_cur_password = view.findViewById(R.id.et_cur_password);
        et_new_password = view.findViewById(R.id.et_new_password);

        et_firstname.setText(SPfiles.getSharePreference(getContext()).getString("firstname", null));
        et_lastname.setText(SPfiles.getSharePreference(getContext()).getString("lastname", null));
        et_mobile.setText(SPfiles.getSharePreference(getContext()).getString("mobile", null));
        et_email.setText(SPfiles.getSharePreference(getContext()).getString("email", null));

        btn_update_profile = view.findViewById(R.id.btn_update_profile);
        btn_update_profile.setOnClickListener(this);
        btn_reset_password = view.findViewById(R.id.btn_reset_password);
        btn_reset_password.setOnClickListener(this);
        return view;
    }

    @Override
    public void onClick(View v) {
        int position = v.getId();
        switch (position) {
            case R.id.btn_update_profile: {
                SPfiles.updateUserInfo(getContext(), et_firstname.getText().toString(), et_lastname.getText().toString(), et_email.getText().toString(), et_mobile.getText().toString());
                Response.Listener<String> listener = new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        Toast.makeText(getContext(), "upgrade success", Toast.LENGTH_SHORT).show();

                    }
                };

                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.e(TAG, error.getMessage());
                        Toast.makeText(getActivity().getBaseContext(), "something Wrong", Toast.LENGTH_SHORT).show();
                    }
                };


                StringRequest stringRequest = Volley.getMyVolly().updateProfileRequest(
                        SPfiles.getSharePreference(getContext()).getString("firstname", null),
                        SPfiles.getSharePreference(getContext()).getString("lastname", null),
                        SPfiles.getSharePreference(getContext()).getString("address", null),
                        SPfiles.getSharePreference(getContext()).getString("mobile", null),
                        SPfiles.getSharePreference(getContext()).getString("email", null),
                        listener, errorListener);
                RequestQueue requestQueue = com.android.volley.toolbox.Volley.newRequestQueue(getActivity());
                requestQueue.add(stringRequest);
                break;
            }

            case R.id.btn_reset_password: {

                if (TextUtils.isEmpty(et_cur_password.getText().toString())) {
                    Toast.makeText(getContext(), "Please input the current password", Toast.LENGTH_SHORT).show();
                    return;
                } else if (TextUtils.isEmpty(et_new_password.getText().toString())) {
                    Toast.makeText(getContext(), "Please input the new password", Toast.LENGTH_SHORT).show();
                    return;
                } else if (et_cur_password.getText().toString().equals(et_new_password.getText().toString())) {
                    Toast.makeText(getContext(), "The new password should not be same as old password", Toast.LENGTH_SHORT).show();
                    return;
                }

                Response.Listener<JSONObject> listener = new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        Toast.makeText(getContext(), "reset password success", Toast.LENGTH_SHORT).show();

                    }
                };

                Response.ErrorListener errorListener = new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        VolleyLog.e(TAG, error.getMessage());
                        Toast.makeText(getActivity().getBaseContext(),"something Wrong", Toast.LENGTH_SHORT).show();
                    }
                };

                JsonObjectRequest jsonObjectRequest = Volley.getMyVolly().resetPasswordRequest(et_mobile.getText().toString(),
                        et_cur_password.getText().toString(),
                        et_new_password.getText().toString(), listener, errorListener);
                RequestQueue requestQueue = com.android.volley.toolbox.Volley.newRequestQueue(getActivity());
                requestQueue.add(jsonObjectRequest);
                break;
            }
        }
    }


}
