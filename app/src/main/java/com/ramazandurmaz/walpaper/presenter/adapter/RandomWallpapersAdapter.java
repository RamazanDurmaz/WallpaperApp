package com.ramazandurmaz.walpaper.presenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ramazandurmaz.walpaper.R;
import com.ramazandurmaz.walpaper.common.Common;
import com.ramazandurmaz.walpaper.model.models.Walpaper;
import com.ramazandurmaz.walpaper.presenter.otto.AddPartialWallpaper;
import com.ramazandurmaz.walpaper.presenter.otto.BusProvider;
import com.ramazandurmaz.walpaper.view.activities.WallpaperViewActivity;
import com.squareup.otto.Subscribe;

import java.util.List;

public class RandomWallpapersAdapter extends RecyclerView.Adapter<RandomWallpapersAdapter.RandomWallpaperViewHolder> {

    private Context context;
    private List<Walpaper> walpaperList;

    public RandomWallpapersAdapter(Context context, List<Walpaper> wallpaperList) {
        this.context = context;
        this.walpaperList = wallpaperList;
        BusProvider.getInstance().register(this);
    }

    @NonNull
    @Override
    public RandomWallpapersAdapter.RandomWallpaperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_random_wallpaper_item, parent, false);
        return new RandomWallpaperViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final RandomWallpapersAdapter.RandomWallpaperViewHolder holder, final int position) {

        Glide.with(context)
                .load(walpaperList.get(position).getImageUrl())
                .centerCrop()
                .fitCenter()
                .into(holder.imgWallpaper);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(context, WallpaperViewActivity.class);
                intent.putExtra(Common.IMAGE_URL_KEY,walpaperList.get(position).getImageUrl());
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return walpaperList.size();
    }


    @RequiresApi(api = Build.VERSION_CODES.N)
    @Subscribe
    public void AddPartialWallpaper(AddPartialWallpaper event) {
        for (Walpaper walpaper : event.getWallpaperList()) {
            this.walpaperList.add(  this.walpaperList.size(), walpaper);
        }
        notifyItemInserted(this.walpaperList.size());
    }


    public class RandomWallpaperViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imgWallpaper;

        public RandomWallpaperViewHolder(@NonNull View itemView) {
            super(itemView);
            imgWallpaper = itemView.findViewById(R.id.img_random_wallpaper);
        }

        @Override
        public void onClick(View v) {

        }
    }
    @Override
    public void onDetachedFromRecyclerView(@NonNull RecyclerView recyclerView) {
        BusProvider.getInstance().unregister(this);
        super.onDetachedFromRecyclerView(recyclerView);
    }
}
