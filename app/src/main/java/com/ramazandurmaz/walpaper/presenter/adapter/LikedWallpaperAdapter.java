package com.ramazandurmaz.walpaper.presenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.ramazandurmaz.walpaper.R;
import com.ramazandurmaz.walpaper.common.Common;
import com.ramazandurmaz.walpaper.model.models.Walpaper;
import com.ramazandurmaz.walpaper.view.activities.WallpaperViewActivity;

import java.util.List;

public class LikedWallpaperAdapter extends RecyclerView.Adapter<LikedWallpaperAdapter.LikedWallpaperViewHolder> {

    private Context context;
    private List<String> walpaperList;

    public LikedWallpaperAdapter(Context context, List<String> wallpaperList) {
        this.context = context;
        this.walpaperList = wallpaperList;
    }

    @NonNull
    @Override
    public LikedWallpaperAdapter.LikedWallpaperViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.layout_favorite_wallpaper_item, parent, false);
        return new LikedWallpaperViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final LikedWallpaperAdapter.LikedWallpaperViewHolder holder, final int position) {
        Glide.with(context)
                .load(walpaperList.get(position))
                .centerCrop()
                .fitCenter()
                .into(holder.imgWallpaper);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Common.BACK_LIKED_IMAGES = true;
                Intent intent = new Intent(context, WallpaperViewActivity.class);
                intent.putExtra(Common.IMAGE_URL_KEY, walpaperList.get(position));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return walpaperList.size();
    }


    public class LikedWallpaperViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView imgWallpaper;

        public LikedWallpaperViewHolder(@NonNull View itemView) {
            super(itemView);
            imgWallpaper = itemView.findViewById(R.id.img_favorite_wallpaper);
        }

        @Override
        public void onClick(View v) {

        }
    }
}
