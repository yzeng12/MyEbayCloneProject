package com.example.yzeng.myebaycloneproject.ui.Item;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.yzeng.myebaycloneproject.R;
import com.example.yzeng.myebaycloneproject.adapter.ShoppingCartAdapter;
import com.example.yzeng.myebaycloneproject.database.DB_DAO;
import com.example.yzeng.myebaycloneproject.objects.ShoppingCartItem;
import com.example.yzeng.myebaycloneproject.ui.helperclasses.SPfiles;

import java.util.List;


public class ShoppingCartFragment extends Fragment implements View.OnClickListener {

    private RecyclerView rv_cart;
    private Button btn_cart_checkout;
    private DB_DAO db_dao;
    private List<ShoppingCartItem> shopping_cart_list;
    private TextView tv_tax;
    private TextView tv_delivery;
    private TextView tv_total;
    private TextView tv_subtotal;
    private Button btn_checkout_confirm;
    private Button btn_checkout_cancel;
    private ShoppingCartAdapter shoppingCartAdapter;
    private static final int deliveryfee = 10;
    private static final double taxrate = 0.1;

    private static final String TAG = "ShoppingCartFragment";


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.shoppingcart, container, false);
        initDatabase();
        initView(view);
        initRecycleView();
        caculateTotal();

        return view;
    }

    private void caculateTotal() {

        int subtotal = 0;
        for (int i = 0; i < shopping_cart_list.size(); i++) {
            subtotal = subtotal + (Integer.parseInt(shopping_cart_list.get(i).getPrize()) * shopping_cart_list.get(i).getQuantity());
        }

        double taxes = (double) (subtotal * taxrate);
        double estTotal = subtotal + taxes+deliveryfee;
        tv_subtotal.setText("$" + subtotal);
        tv_tax.setText("$" + taxes);
        tv_delivery.setText("$" + deliveryfee);
        tv_total.setText("$" + estTotal);


    }

    public void initView(View view) {
        rv_cart = view.findViewById(R.id.rv_cart);
        btn_cart_checkout = view.findViewById(R.id.btn_cart_checkout);
        btn_cart_checkout.setOnClickListener(this);
        btn_checkout_cancel = view.findViewById(R.id.btn_checkout_cancel);
        btn_checkout_cancel.setOnClickListener(this);
        tv_subtotal = view.findViewById(R.id.tv_subtotal);
        tv_tax = view.findViewById(R.id.tv_tax);
        tv_delivery = view.findViewById(R.id.tv_delivery);
        tv_total = view.findViewById(R.id.tv_total);
    }

    public void initDatabase() {
        db_dao = new DB_DAO(getActivity());//getContext()
        db_dao.openDatabase();

        shopping_cart_list = db_dao.getCartList(SPfiles.getSharePreference(getContext()).getString("mobile", null));
        Log.i("shopping_cart_list", shopping_cart_list.size() + "");
    }

    public void initRecycleView() {
        shoppingCartAdapter = new ShoppingCartAdapter(getContext(), shopping_cart_list);
        rv_cart.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        rv_cart.setAdapter(shoppingCartAdapter);
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btn_cart_checkout:
                //something
                break;
            case R.id.btn_checkout_cancel:
                //something
                break;


        }
    }
}
