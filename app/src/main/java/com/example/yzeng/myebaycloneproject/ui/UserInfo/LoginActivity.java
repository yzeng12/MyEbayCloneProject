package com.example.yzeng.myebaycloneproject.ui.UserInfo;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.yzeng.myebaycloneproject.R;

public class LoginActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        getSupportFragmentManager().beginTransaction().
                replace( R.id.MainPage, new LoginFragment()).commit();
    }

}
