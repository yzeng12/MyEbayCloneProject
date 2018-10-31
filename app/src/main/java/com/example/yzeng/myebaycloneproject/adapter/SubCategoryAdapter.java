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
import com.example.yzeng.myebaycloneproject.objects.MainCatItem;
import com.example.yzeng.myebaycloneproject.objects.SubCatItem;
import com.squareup.picasso.Picasso;

import java.util.List;

public class SubCategoryAdapter extends RecyclerView.Adapter<SubCategoryAdapter.SubContentViewHolder> implements View.OnClickListener{
    private Context context;
    private List<SubCatItem> sublist;
    private List<MainCatItem> mainlist;
    private OnItemClickListener itemClickListener;


    public SubCategoryAdapter(Context context, List<SubCatItem> sublist, List<MainCatItem> mainlist) {
        this.context = context;
        this.sublist = sublist;
        this.mainlist = mainlist;
    }
    @NonNull
    @Override
    public SubCategoryAdapter.SubContentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int position) {
        SubContentViewHolder holder;

        View view = LayoutInflater.from(context).inflate(R.layout.cardview_sub_category, parent, false);
        holder = new SubContentViewHolder(view);
        view.setOnClickListener(this);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull SubContentViewHolder holder, int position) {
        String data =sublist.get(position).getSubCatname();
        holder.tv.setText(data);
        Picasso.with(context).load(sublist.get(position).getSubCatimage()).into(holder.iv);
        holder.itemView.setTag(position);
    }



    @Override
    public int getItemCount() {
        return sublist.size();
    }

    @Override
    public void onClick(View v) {
        if(itemClickListener != null){
            itemClickListener.onItemClick(v, (Integer)v.getTag());
        }
    }

    public class SubContentViewHolder extends RecyclerView.ViewHolder{
        TextView tv;
        ImageView iv;
        Context context;
        public SubContentViewHolder(@NonNull View view) {
            super( view );
            tv = view.findViewById( R.id.tv_subCatItem);
            iv = view.findViewById(R.id.iv_subCatItem);
        }
    }

    public void setItemClickListener(SubCategoryAdapter.OnItemClickListener itemClickListener){
        this.itemClickListener = itemClickListener;
    }

    public interface OnItemClickListener{
        void onItemClick(View view, int position);
    }
}
