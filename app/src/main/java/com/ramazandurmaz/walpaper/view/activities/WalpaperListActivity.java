package com.ramazandurmaz.walpaper.view.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ramazandurmaz.walpaper.R;
import com.ramazandurmaz.walpaper.common.Common;
import com.ramazandurmaz.walpaper.model.models.Category;
import com.ramazandurmaz.walpaper.model.models.Walpaper;
import com.ramazandurmaz.walpaper.presenter.adapter.CategoryAdapter;
import com.ramazandurmaz.walpaper.presenter.adapter.WallpaperAdapter;
import com.ramazandurmaz.walpaper.presenter.classes.DataManagement;
import com.ramazandurmaz.walpaper.presenter.interfaces.IReloadList;
import com.ramazandurmaz.walpaper.presenter.interfaces.IWallpaperReloadList;

import java.util.ArrayList;
import java.util.List;

public class WalpaperListActivity extends AppCompatActivity implements IWallpaperReloadList {
    private TextView tvCategoryName;
    private ImageView imgBack;
    private RecyclerView rvWallpapers;
    DataManagement dataManagement;
    private WallpaperAdapter wallpaperAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_walpaper_list);

        tvCategoryName = findViewById(R.id.tv_category_name);
        imgBack = findViewById(R.id.img_back);
        rvWallpapers = findViewById(R.id.rv_walpapers);


        dataManagement = new DataManagement();

        tvCategoryName.setText(Common.CATEGORY_SELECTED);

        setWallpaperAdapter();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
    }

    private void setWallpaperAdapter() {
        dataManagement.addWallpaperListReloader(this);
        dataManagement.getWallpaperByCategory();
    }


    @Override
    public void onWalpaperListReady(List<Walpaper> wallpaperList) {
        rvWallpapers.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(this,2);
        rvWallpapers.setLayoutManager(gridLayoutManager);
        wallpaperAdapter = new WallpaperAdapter(getApplicationContext(),wallpaperList);
        rvWallpapers.setAdapter(wallpaperAdapter);
    }
}
