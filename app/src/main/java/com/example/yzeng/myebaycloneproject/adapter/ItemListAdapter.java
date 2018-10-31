package com.example.yzeng.myebaycloneproject.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;


import com.example.yzeng.myebaycloneproject.R;
import com.example.yzeng.myebaycloneproject.objects.ListItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class ItemListAdapter extends RecyclerView.Adapter<ItemListAdapter.ProductListViewHolder> implements View.OnClickListener{
    private Context context;
    private List<ListItem> List;
    private OnItemClickListener onItemClickListener;

    public ItemListAdapter(Context context, List<ListItem> List) {
        this.context = context;
        this.List = List;
    }

    @NonNull
    @Override
    public ProductListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        ProductListViewHolder holder;
        View view = LayoutInflater.from(context).inflate(R.layout.detail_list, parent, false);
        view.setOnClickListener(this);
        holder = new ProductListViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ProductListViewHolder holder, int position) {
        holder.tv_price.setText("Price: " + List.get(position).getPrice());
        holder.tv_name.setText(List.get(position).getName());
        holder.tv_quantity.setText("quantity left: "+List.get(position).getQuantity());
        holder.tv_detail.setText("Click Item for Detail");
        Picasso.with(context).load(List.get(position).getImage()).into(holder.iv);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return List.size();
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener){
        this.onItemClickListener = onItemClickListener;
    }

    @Override
    public void onClick(View v) {
        if(onItemClickListener != null){
            onItemClickListener.onItemClick(v, (Integer)v.getTag());
        }
    }

    public interface OnItemClickListener {
        void onItemClick(View v, int position);
    }

    public class ProductListViewHolder extends RecyclerView.ViewHolder {
        ImageView iv;
        TextView tv_name, tv_price,tv_quantity,tv_detail;
        Context context;
        public ProductListViewHolder(@NonNull View view) {
            super(view);
            iv = view.findViewById( R.id.iv_listItemImage);
            tv_name = view.findViewById(R.id.tv_listItemName);
            tv_price = view.findViewById(R.id.tv_listItemPrice);
            tv_quantity=view.findViewById(R.id.tv_listItemQuantity);
            tv_detail=view.findViewById(R.id.tv_listItemDetail);
        }
    }
}
