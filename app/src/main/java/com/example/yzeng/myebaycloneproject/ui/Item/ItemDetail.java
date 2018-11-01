package com.example.yzeng.myebaycloneproject.ui.Item;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.yzeng.myebaycloneproject.R;
import com.example.yzeng.myebaycloneproject.database.DB_DAO;
import com.example.yzeng.myebaycloneproject.ui.helperclasses.SPfiles;
import com.squareup.picasso.Picasso;


public class ItemDetail extends Fragment implements View.OnClickListener {
    private ImageView iv_image;
    private TextView tv_name, tv_price, tv_description;
    private Button button;
    private String id, name, price, description, image;
    private DB_DAO db_dao;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.product_detail, container, false);

        db_dao = new DB_DAO(getActivity());//getContext()
        db_dao.openDatabase();

        iv_image = view.findViewById(R.id.iv_detail_image);
        tv_name = view.findViewById(R.id.tv_detail_name);
        tv_price = view.findViewById(R.id.tv_detail_price);
        tv_description = view.findViewById(R.id.tv_detail_description);
        button = view.findViewById(R.id.btn_add_into_cart);
        button.setOnClickListener(this);

        Bundle bundle = getArguments();

        if (bundle != null) {
            id = bundle.getString("id");
            name = bundle.getString("name");
            price = bundle.getString("price");
            description = bundle.getString("description");
            image = bundle.getString("image");
            tv_name.setText(name);
            tv_price.setText("$" + price);
            tv_description.setText(description);
            Picasso.with(getContext()).load(image).into(iv_image);
        }
        return view;
    }

    @Override
    public void onClick(View v) {
        //put shipping cart item into database
        int quantity = db_dao.verifyItemInCart(id, SPfiles.getSharePreference(getContext()).getString("mobile", null));

        //Toast.makeText(getContext(), "quantity= "+quantity, Toast.LENGTH_SHORT).show();
        if (quantity == 0) {
            db_dao.addItemToCart(SPfiles.getSharePreference(getContext()).getString("mobile", null), id, name, 1, price, image);
            //Toast.makeText(getContext(), "item=0   add to db success", Toast.LENGTH_SHORT).show();
        } else {
            //Toast.makeText(getContext(), "exist  quantity+1", Toast.LENGTH_SHORT).show();
            db_dao.updataCartQuantity(quantity + 1, id, SPfiles.getSharePreference(getContext()).getString("mobile", null));
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (db_dao != null) {
            db_dao.closeDatabase();
        }
    }
}
