package com.ramazandurmaz.walpaper.view.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ramazandurmaz.walpaper.R;
import com.ramazandurmaz.walpaper.common.Common;
import com.ramazandurmaz.walpaper.presenter.adapter.LikedWallpaperAdapter;
import com.ramazandurmaz.walpaper.presenter.classes.DataManagement;

import java.util.List;

public class LikedImagesFragment extends Fragment {
    View view;
    DataManagement dataManagement;
    private static LikedImagesFragment INSTANCE = null;
    RecyclerView rvLikedWallpapers;
    LikedWallpaperAdapter likedWallpaperAdapter;
    private Context context;
    public LikedImagesFragment() {
    }

    public static LikedImagesFragment getInstance(){
        if(INSTANCE==null)
            INSTANCE=new LikedImagesFragment();
        return INSTANCE;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_liked_images,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        dataManagement = new DataManagement();
        rvLikedWallpapers = view.findViewById(R.id.rv_favorite_wallpapers);
        rvLikedWallpapers.setHasFixedSize(true);
        setLikedWallpaperAdapter();

    }

    public void setLikedWallpaperAdapter() {
        List<String> favoriteWallpapers = dataManagement.getFavoriteWallpaper();
        if(favoriteWallpapers!=null) {
            GridLayoutManager gridLayoutManager = new GridLayoutManager(getActivity(), 2);
            rvLikedWallpapers.setLayoutManager(gridLayoutManager);
            likedWallpaperAdapter = new LikedWallpaperAdapter(getContext(), favoriteWallpapers);
            rvLikedWallpapers.setAdapter(likedWallpaperAdapter);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if(Common.BACK_LIKED_IMAGES){
            setLikedWallpaperAdapter();
            Common.BACK_LIKED_IMAGES=false;
        }
    }
}
