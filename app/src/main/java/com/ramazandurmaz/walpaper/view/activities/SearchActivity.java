package com.ramazandurmaz.walpaper.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.ramazandurmaz.walpaper.R;
import com.ramazandurmaz.walpaper.model.models.Category;
import com.ramazandurmaz.walpaper.presenter.adapter.CategoryFilterAdapter;
import com.ramazandurmaz.walpaper.presenter.classes.DataManagement;
import com.ramazandurmaz.walpaper.presenter.interfaces.IReloadList;

import java.util.List;

public class SearchActivity extends AppCompatActivity implements IReloadList {

    DataManagement dataManagement;
    RecyclerView rvCategoryFilter;
    CategoryFilterAdapter categoryFilterAdapter;
    EditText etSearchFilter;
    ImageView imgBack;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        dataManagement = new DataManagement();
        rvCategoryFilter = findViewById(R.id.rv_category_filter);
        etSearchFilter = findViewById(R.id.et_search_filter);
        imgBack = findViewById(R.id.img_back);
        dataManagement.addListReloader(this);
        dataManagement.getCategory();

        etSearchFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable editable) {
                categoryFilterAdapter.getFilter().filter(editable);
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

    }

    @Override
    public void onListReady(List<Category> categoryList) {
        categoryFilterAdapter = new CategoryFilterAdapter(getApplicationContext(),categoryList);
        rvCategoryFilter.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        rvCategoryFilter.setAdapter(categoryFilterAdapter);
    }
}
