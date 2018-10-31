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
import com.example.yzeng.myebaycloneproject.objecgts.MainCatItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class MainCategoryAdapter extends RecyclerView.Adapter<MainCategoryAdapter.ContentViewHolder> implements View.OnClickListener{
    private Context context;
    private List<MainCatItem> mainCatItems;
    private OnItemClickListener mOnItemClickListener;


    public MainCategoryAdapter(Context context, List<MainCatItem> mainCatItems) {
        this.context = context;
        this.mainCatItems = mainCatItems;

    }

    @NonNull
    @Override
    public MainCategoryAdapter.ContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ContentViewHolder holder;
        View view = LayoutInflater.from(context).inflate( R.layout.cardview_main_category, parent, false);
        holder = new ContentViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull ContentViewHolder holder, int position) {
        String data =mainCatItems.get(position).getMainCatname();
        holder.tv.setText(data);
        Picasso.with(context).load(mainCatItems.get(position).getMainCatimage()).into(((ContentViewHolder) holder).iv);
        holder.itemView.setTag(position);
    }

    @Override
    public int getItemCount() {
        return mainCatItems.size();
    }

    @Override
    public void onClick(View v) {
        if(mOnItemClickListener != null){
            mOnItemClickListener.onItemClick(v, (Integer)v.getTag());
        }
    }


    public class ContentViewHolder extends RecyclerView.ViewHolder{
        TextView tv;
        ImageView iv;
        Context ctx;

        public ContentViewHolder(@NonNull View view) {
            super(view);
            tv =view.findViewById(R.id.tv_mainCatItem);
            iv =view.findViewById(R.id.iv_mainCatItem);
        }
    }

    public void setOnItemClickListener(OnItemClickListener itemClickListener){
        mOnItemClickListener = itemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
}
