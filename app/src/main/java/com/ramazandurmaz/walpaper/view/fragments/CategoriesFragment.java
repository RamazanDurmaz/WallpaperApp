package com.ramazandurmaz.walpaper.view.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ramazandurmaz.walpaper.R;
import com.ramazandurmaz.walpaper.model.models.Category;
import com.ramazandurmaz.walpaper.presenter.adapter.CategoryAdapter;
import com.ramazandurmaz.walpaper.presenter.classes.DataManagement;
import com.ramazandurmaz.walpaper.presenter.interfaces.IReloadList;

import java.util.List;


public class CategoriesFragment extends Fragment  implements IReloadList {
    View view;
    private static CategoriesFragment INSTANCE = null;
    DataManagement dataManagement;
    RecyclerView rvCategory;
    CategoryAdapter categoryAdapter;

    public CategoriesFragment() {
        dataManagement = new DataManagement();

    }

    public static CategoriesFragment getInstance(){
        if(INSTANCE==null)
            INSTANCE=new CategoriesFragment();
        return INSTANCE;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_categories,container,false);
        return view;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        rvCategory = view.findViewById(R.id.rv_category);
        dataManagement.addListReloader(this);
        dataManagement.getCategory();
    }

    @Override
    public void onListReady(List<Category> categoryList) {
        setCategoryAdapter(categoryList);
    }

    private void setCategoryAdapter(List<Category> categoryList) {
        rvCategory.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(),2);
        rvCategory.setLayoutManager(gridLayoutManager);
        categoryAdapter = new CategoryAdapter(getContext(),categoryList);
        rvCategory.setAdapter(categoryAdapter);

    }
}
