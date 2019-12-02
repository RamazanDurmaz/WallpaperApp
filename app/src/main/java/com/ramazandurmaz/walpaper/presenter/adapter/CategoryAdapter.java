package com.ramazandurmaz.walpaper.presenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ramazandurmaz.walpaper.R;
import com.ramazandurmaz.walpaper.common.Common;
import com.ramazandurmaz.walpaper.model.models.Category;
import com.ramazandurmaz.walpaper.view.activities.WalpaperListActivity;

import java.util.List;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

public class CategoryAdapter extends RecyclerView.Adapter<CategoryAdapter.CategoryViewHolder> {

    private Context context;
    private List<Category> categoryList;

    public CategoryAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
    }

    @NonNull
    @Override
    public CategoryAdapter.CategoryViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_category_item, parent, false);
        return new CategoryViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryAdapter.CategoryViewHolder holder, int position) {

        Glide.with(context)
                .load(categoryList.get(position).getImageLink())
                .centerCrop()
                .fitCenter()
                .into(holder.imgCategory);
        holder.tvCategory.setText(categoryList.get(position).getName());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common.CATEGORY_ID_SELECTED = holder.getAdapterPosition() + 1;
                Common.CATEGORY_SELECTED = categoryList.get( Common.CATEGORY_ID_SELECTED - 1).getName();
                context.startActivity(new Intent(context, WalpaperListActivity.class));

            }
        });

    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }


    public class CategoryViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imgCategory;
        TextView tvCategory;

        public CategoryViewHolder(@NonNull View itemView) {
            super(itemView);
            imgCategory = itemView.findViewById(R.id.img_category);
            tvCategory = itemView.findViewById(R.id.tv_category);
        }

        @Override
        public void onClick(View v) {

        }
    }
}


