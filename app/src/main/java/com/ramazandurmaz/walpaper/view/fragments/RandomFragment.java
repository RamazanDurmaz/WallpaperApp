package com.ramazandurmaz.walpaper.view.fragments;

import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ramazandurmaz.walpaper.R;
import com.ramazandurmaz.walpaper.model.models.Walpaper;
import com.ramazandurmaz.walpaper.presenter.adapter.RandomWallpapersAdapter;
import com.ramazandurmaz.walpaper.presenter.adapter.WallpaperAdapter;
import com.ramazandurmaz.walpaper.presenter.classes.DataManagement;
import com.ramazandurmaz.walpaper.presenter.otto.AddPartialWallpaper;
import com.ramazandurmaz.walpaper.presenter.otto.BusProvider;
import com.ramazandurmaz.walpaper.presenter.otto.GetAllWallpaper;
import com.ramazandurmaz.walpaper.view.classes.CustomScrollView;
import com.squareup.otto.Subscribe;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

@SuppressWarnings("ALL")
public class RandomFragment extends Fragment {
    View view;
    DataManagement dataManagement;
    private RecyclerView rvRandomWallpapers;
    private RandomWallpapersAdapter wallpaperAdapter;
    List<Walpaper> wallpaperList = new ArrayList<>();
    List<Walpaper> walpaperPartialList = new ArrayList<>();
    List<Walpaper> oldWalpaperList = new ArrayList<>();
    List<Walpaper> setAdapterWalpaperList = new ArrayList<>();
    LinearLayout linearLayout;
    CustomScrollView scrollView;
    private static RandomFragment INSTANCE = null;

    public RandomFragment() {
        dataManagement = new DataManagement();
    }

    public static RandomFragment getInstance(){
        if(INSTANCE==null)
            INSTANCE=new RandomFragment();
        return INSTANCE;
    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        BusProvider.getInstance().register(this);
    }

    @Override
    public void onDestroy() {
        BusProvider.getInstance().unregister(this);
        super.onDestroy();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_random,container,false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvRandomWallpapers = view.findViewById(R.id.rv_random_wallpapers);
        linearLayout = view.findViewById(R.id.linear_layout);
        scrollView = (CustomScrollView) view.findViewById(R.id.custom_scroll_view);
        CustomScrollView customScrollView = new CustomScrollView(getActivity());
        rvRandomWallpapers.setHasFixedSize(true);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(getContext(),2);
        rvRandomWallpapers.setLayoutManager(gridLayoutManager);

       // dataManagement.addWallpaperListReloader(this);
        dataManagement.getAllWallpaperList();

        scrollViewListener();

    }

    private void scrollViewListener() {
        scrollView.setOnBottomReachedListener(new CustomScrollView.OnBottomReachedListener() {
            @Override
            public void onBottomReached() {
                insertPartialWallpaperList();
            }
        });
    }

    private void insertPartialWallpaperList() {
        setAdapterWalpaperList.clear();
        setAdapterWalpaperList.addAll(getPartialWallpaper(oldWalpaperList));
        BusProvider.getInstance().post(new AddPartialWallpaper(setAdapterWalpaperList));
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Subscribe
    public void GetAllWallpaper(GetAllWallpaper event) {
        this.wallpaperList = event.getWallpaperList();
        Collections.shuffle(wallpaperList);
        walpaperPartialList = getPartialWallpaper(wallpaperList);
        buildRecyclerView();
    }

    private void buildRecyclerView() {
        wallpaperAdapter = new RandomWallpapersAdapter(getContext(),walpaperPartialList);
        rvRandomWallpapers.setAdapter(wallpaperAdapter);
    }

    private List<Walpaper> getPartialWallpaper(List<Walpaper> oldWallpaperList) {
        List<Walpaper> newWallpaperPartialList = new ArrayList<>();
        if (oldWallpaperList.size()>=119){
            for(int i=0;i<8;i++){
                newWallpaperPartialList.add(oldWallpaperList.get(i));
                oldWallpaperList.remove(i);
            }
        }
        this.oldWalpaperList = oldWallpaperList;
        return newWallpaperPartialList;
    }
}
