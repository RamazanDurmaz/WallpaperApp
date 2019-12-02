package com.ramazandurmaz.walpaper.presenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ramazandurmaz.walpaper.R;
import com.ramazandurmaz.walpaper.common.Common;
import com.ramazandurmaz.walpaper.model.models.Category;
import com.ramazandurmaz.walpaper.view.activities.WalpaperListActivity;

import java.util.ArrayList;
import java.util.List;

public class CategoryFilterAdapter extends RecyclerView.Adapter<CategoryFilterAdapter.CategoryFilterViewHolder> implements Filterable {

    private Context context;
    private List<Category> categoryList;
    private List<Category> categoryListFull;
    List<Category> filteredList;

    public CategoryFilterAdapter(Context context, List<Category> categoryList) {
        this.context = context;
        this.categoryList = categoryList;
        categoryListFull = new ArrayList<>(categoryList);
        categoryList.clear();
    }


    @NonNull
    @Override
    public CategoryFilterAdapter.CategoryFilterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_category_filter_item, parent, false);
        return new CategoryFilterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final CategoryFilterAdapter.CategoryFilterViewHolder holder, int position) {
        holder.tvCategoryFilter.setText(categoryList.get(position).getName());
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    @Override
    public Filter getFilter() {
        return categoryFilter;
    }

    private Filter categoryFilter = new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {
            filteredList = new ArrayList<>();

            if (!(charSequence == null || charSequence.length() == 0)) {
                String filterPattern = charSequence.toString().toLowerCase().trim();

                for (Category category : categoryListFull) {
                    if (category.getName().toLowerCase().contains(filterPattern))
                        filteredList.add(category);
                }
            }
            FilterResults results = new FilterResults();
            results.values = filteredList;
            return results;

        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
            categoryList.clear();
            categoryList.addAll((List) filterResults.values);
            notifyDataSetChanged();

        }
    };

    public class CategoryFilterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView tvCategoryFilter;

        public CategoryFilterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCategoryFilter = itemView.findViewById(R.id.tv_category_filter);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            for (int i = 0; i < categoryListFull.size(); i++) {
                if (categoryListFull.get(i).getName().equals(tvCategoryFilter.getText().toString())) {
                    Common.CATEGORY_ID_SELECTED = i + 1;
                    Common.CATEGORY_SELECTED = categoryListFull.get(Common.CATEGORY_ID_SELECTED-1).getName();
                }

            }

            Intent intent = new Intent(context, WalpaperListActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(intent);
        }
    }

}