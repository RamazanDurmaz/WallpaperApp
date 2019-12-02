package com.ramazandurmaz.walpaper.view.activities;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.ramazandurmaz.walpaper.R;
import com.ramazandurmaz.walpaper.common.Common;
import com.ramazandurmaz.walpaper.presenter.classes.DataManagement;
import com.ramazandurmaz.walpaper.view.classes.DownloadTask;
import com.ramazandurmaz.walpaper.view.classes.SetWallpaperTask;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;

@SuppressWarnings("ALL")
public class WallpaperViewActivity extends AppCompatActivity {

    private ImageView imgViewWallpaper, imgBack, imgFavorite;
    private String imgUrl;
    private RelativeLayout rlSetWallpaper, rlDownload, rlFavorite;
    DataManagement dataManagement;
    Bitmap relativeBitmap;
    OutputStream outputStream;
    private ProgressDialog mProgressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wallpaper_view);

        init();
       dataManagement = new DataManagement();

        imgUrl = getIntent().getExtras().getString(Common.IMAGE_URL_KEY);
        setViewWallpaperImage();

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });

        rlSetWallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setWallpaperToDevice();
            }
        });

        rlDownload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dowloadWalpaper();
            }
        });

        rlFavorite.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addFavoriteUrl();
            }
        });

        imgFavorite = findViewById(R.id.img_favorite);

        setFavoriteImageIcon();
    }

    private void setFavoriteImageIcon() {
        if(dataManagement.isFavoriteWallpaper(imgUrl)){
            imgFavorite.setImageDrawable(getDrawable(R.drawable.ic_favorite_liked_24dp));
        }
    }

    private void addFavoriteUrl() {
            if (dataManagement.isFavoriteWallpaper(imgUrl)) {
                dataManagement.removeFavorite(imgUrl);
                imgFavorite.setImageDrawable(getDrawable(R.drawable.ic_favorite_black_24dp));
            } else {
                dataManagement.sendFavoriteWallpaper(imgUrl);
                imgFavorite.setImageDrawable(getDrawable(R.drawable.ic_favorite_liked_24dp));
            }
    }


    private void dowloadWalpaper() {
            new DownloadTask(WallpaperViewActivity.this,rlDownload,rlSetWallpaper).execute(imgUrl);
    }

    private void init() {
        imgBack = findViewById(R.id.img_back);
        imgViewWallpaper = findViewById(R.id.img_view_wallpaper);
        rlSetWallpaper = findViewById(R.id.rl_set_wallpaper);
        rlDownload = findViewById(R.id.rl_download);
        rlFavorite = findViewById(R.id.rl_favorite);
    }

    public static Bitmap viewToBitmap(View view, int width, int height) {
        Bitmap bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        view.draw(canvas);
        return bitmap;
    }

    private void setWallpaperToDevice() {
        if(imgViewWallpaper.getWidth()>0 && imgViewWallpaper.getHeight()>0) {
            Bitmap bitmap = viewToBitmap(imgViewWallpaper, imgViewWallpaper.getWidth(), imgViewWallpaper.getHeight());
            new SetWallpaperTask(WallpaperViewActivity.this, bitmap,rlDownload,rlSetWallpaper).execute();
        }
    }

    private void setViewWallpaperImage() {
        Glide.with(this)
                .load(imgUrl)
                .centerCrop()
                .fitCenter()
                .into(imgViewWallpaper);
    }
}
