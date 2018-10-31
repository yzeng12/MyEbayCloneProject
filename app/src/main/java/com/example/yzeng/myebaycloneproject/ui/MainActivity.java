package com.example.yzeng.myebaycloneproject.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.yzeng.myebaycloneproject.R;
import com.example.yzeng.myebaycloneproject.ui.Item.MainCatFragment;
import com.example.yzeng.myebaycloneproject.ui.UserInfo.ChangeProfileFragment;
import com.example.yzeng.myebaycloneproject.ui.UserInfo.LoginActivity;
import com.example.yzeng.myebaycloneproject.ui.helperclasses.SPfiles;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {
    private static final String TAG = "MainActivity";
    private ImageButton ib_cart;
    private TextView tv_toolbarTitle,tv_username;
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main_screen);
        toolbar = (Toolbar) findViewById(R.id.mytoolbar);
        tv_toolbarTitle = toolbar.findViewById(R.id.tv_toolbar_title);
        tv_toolbarTitle.setText("Main menu");
        setSupportActionBar(toolbar);

        ib_cart = toolbar.findViewById(R.id.ib_cart);
        ib_cart.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tv_toolbarTitle.setText("My Shopping Cart");
         /*       getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.Maincontent, new ChangeProfileFragment()).
                    addToBackStack(null)
                    .commit();

                    */
         //TODO

            }
        } );


//        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
//        fab.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
//            }
//        });

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View head = navigationView.getHeaderView(0);
        tv_username = head.findViewById(R.id.tv_username);
        tv_username.setText(SPfiles.getSharePreference(getApplicationContext()).getString("firstname", null) + " " + SPfiles.getSharePreference(getApplicationContext()).getString("lastname", null));

        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.Maincontent, new MainCatFragment()).
                addToBackStack(null)
                .commit();
        Log.i(TAG, "onCreate: MainCatFragment done");
        //TODO
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        //getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.profile) {
            tv_toolbarTitle.setText("Change profile");
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.Maincontent, new ChangeProfileFragment()).
                    addToBackStack(null)
                    .commit();
        } else if (id == R.id.shop) {
            tv_toolbarTitle.setText("Main menu");
            getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.Maincontent, new MainCatFragment()).
                    addToBackStack(null)
                    .commit();
        } else if (id == R.id.order) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.logout) {
            SPfiles.clearUserInfo(getBaseContext());
            Intent intent = new Intent(MainActivity.this, LoginActivity.class);
            startActivity(intent);
        } else if (id == R.id.techology) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
