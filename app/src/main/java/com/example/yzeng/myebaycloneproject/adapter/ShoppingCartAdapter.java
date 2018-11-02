package com.example.yzeng.myebaycloneproject.adapter;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.yzeng.myebaycloneproject.R;
import com.example.yzeng.myebaycloneproject.database.DB_DAO;
import com.example.yzeng.myebaycloneproject.objects.ShoppingCartItem;
import com.example.yzeng.myebaycloneproject.ui.Item.ShoppingCartFragment;
import com.example.yzeng.myebaycloneproject.ui.helperclasses.SPfiles;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ShoppingCartAdapter extends RecyclerView.Adapter<ShoppingCartAdapter.CartViewHolder>{
    private ShoppingCartFragment fragment;
    private Context context;
    private List<ShoppingCartItem> orderList;
    private DB_DAO db_dao;


    public ShoppingCartAdapter(Context context, List<ShoppingCartItem> orderList, ShoppingCartFragment fragment) {
        this.context = context;
        this.orderList = orderList;
        this.fragment=fragment;
        db_dao = new DB_DAO(context);
        db_dao.openDatabase();
    }


    @NonNull
    @Override
    public ShoppingCartAdapter.CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CartViewHolder holder;
     /*   if (viewType == -1){
            View view = LayoutInflater.from(context).inflate( R.layout.item_empty, parent, false);
            holder = new CartViewHolder(view);
        }else {

        }*/
        View view = LayoutInflater.from(context).inflate( R.layout.cart_item, parent, false);
        holder = new CartViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ShoppingCartAdapter.CartViewHolder holder, int position) {
        if(orderList.size() > 0){
            Log.i("orderlist_position", position + "");
            Picasso.with(context).load(orderList.get(position).getImage()).into(holder.iv_cart_item_pic);

            holder.tv_cart_name.setText(orderList.get(position).getPname());
            holder.tv_cart_price.setText("$" +(orderList.get(position).getPrice()));
            holder.tv_cart_quantity.setText(String.valueOf(orderList.get(position).getQuantity()));
        }
    }

    @Override
    public int getItemCount() {
        return orderList.size() > 0 ? orderList.size() : 1;
    }

    @Override
    public int getItemViewType(int position) {
        if(orderList.size() <= 0){
            return -1;
        }
        return super.getItemViewType( position );
    }

    public class CartViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView iv_cart_item_pic;
        private TextView tv_cart_name, tv_cart_price, tv_cart_quantity;
        private ImageButton ib_cart_add, ib_cart_minus, ib_cart_delete;

        public CartViewHolder(@NonNull View itemView) {
            super( itemView );
            iv_cart_item_pic = itemView.findViewById(R.id.iv_cart_image);
            tv_cart_name = itemView.findViewById(R.id.tv_cart_name);
            tv_cart_price = itemView.findViewById(R.id.tv_cart_price);
            tv_cart_quantity = itemView.findViewById(R.id.tv_cart_quantity);
            ib_cart_add = itemView.findViewById(R.id.ib_cart_add);
            ib_cart_minus = itemView.findViewById(R.id.ib_cart_minus);
            ib_cart_delete = itemView.findViewById(R.id.ib_cart_delete);

            if(ib_cart_add != null){
                ib_cart_add.setOnClickListener(this);
                ib_cart_minus.setOnClickListener(this);
                ib_cart_delete.setOnClickListener(this);
            }
        }

        @Override
        public void onClick(View v) {
            int position = v.getId();
            switch (position){
                case R.id.ib_cart_add:
                    int quantity = Integer.parseInt(tv_cart_quantity.getText().toString());
                    quantity += 1;
                    tv_cart_quantity.setText(String.valueOf(quantity));
                    orderList.get(getLayoutPosition()).setQuantity(quantity);
                    db_dao.updataCartQuantity(quantity, orderList.get(getLayoutPosition()).getPid(), SPfiles.getSharePreference(context).getString("mobile", null));
                    fragment.caculateTotal();
                    break;
                case R.id.ib_cart_delete:
                    AlertDialog dialog = new AlertDialog.Builder(context).setNegativeButton( "No", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            notifyDataSetChanged();
                            fragment.caculateTotal();
                        }
                    }).setPositiveButton( "Yes", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            db_dao.deleteItemCart(SPfiles.getSharePreference(context).getString("mobile", null), orderList.get(getLayoutPosition()).getPid());
                            orderList.remove(getLayoutPosition());
                            notifyDataSetChanged();
                            fragment.caculateTotal();
                        }
                    } ).setMessage("Do you want remove this item from cart?").setTitle("Warning").create();
                    dialog.setCancelable(false);
                    dialog.show();
                    break;
                case R.id.ib_cart_minus:
                    int quantity1 = Integer.parseInt(tv_cart_quantity.getText().toString());
                    quantity1 -= 1;
                    tv_cart_quantity.setText(String.valueOf(quantity1));
                    orderList.get(getLayoutPosition()).setQuantity(quantity1);
                    db_dao.updataCartQuantity(quantity1, orderList.get(getLayoutPosition()).getPid(), SPfiles.getSharePreference(context).getString("mobile", null));
                    if(tv_cart_quantity.getText().toString().equals("0")){
                        AlertDialog dialog1 = new AlertDialog.Builder(context).setNegativeButton( "No", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                tv_cart_quantity.setText("1");
                                orderList.get(getLayoutPosition()).setQuantity(1);
                                db_dao.updataCartQuantity(orderList.get(getLayoutPosition()).getQuantity()
                                        , orderList.get(getLayoutPosition()).getPid()
                                        , SPfiles.getSharePreference(context).getString("mobile", null));
                                fragment.caculateTotal();

                            }
                        }).setPositiveButton( "Yes", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                db_dao.deleteItemCart(SPfiles.getSharePreference(context).getString("mobile", null), orderList.get(getLayoutPosition()).getPid());
                                orderList.remove(getLayoutPosition());
                                notifyDataSetChanged();
                                fragment.caculateTotal();
                            }
                        } ).setMessage("Do you want remove this item from cart?").setTitle("Warning").create();
                        dialog1.setCancelable(false);
                        dialog1.show();

                    }
                    fragment.caculateTotal();
                    break;
            }
        }
    }

}
