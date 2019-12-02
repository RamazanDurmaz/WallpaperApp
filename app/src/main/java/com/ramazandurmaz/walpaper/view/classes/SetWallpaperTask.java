package com.ramazandurmaz.walpaper.view.classes;

import android.app.ProgressDialog;
import android.app.WallpaperManager;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.PowerManager;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.ramazandurmaz.walpaper.view.activities.WallpaperViewActivity;

import java.io.IOException;

public class SetWallpaperTask extends AsyncTask<Void, Void, Void> {

    Bitmap bitmap;
    private Context context;
    private ProgressDialog dialog;
    private PowerManager.WakeLock mWakeLock;
    RelativeLayout rlDownload,rlSetWallpaper;
    public SetWallpaperTask(Context context, Bitmap bitmap,RelativeLayout rlDownload, RelativeLayout rlSetWallpaper){
        this.context = context;
        this.bitmap = bitmap;
        dialog = new ProgressDialog(context);
        this.rlDownload = rlDownload;
        this.rlSetWallpaper = rlSetWallpaper;
    }

    @Override
    protected void onPreExecute() {
        // Runs on the UI thread
        // Do any pre-executing tasks here, for example display a progress bar
        rlSetWallpaper.setEnabled(false);
        rlDownload.setEnabled(false);
        dialog.setMessage("Setting Wallpaper...");
        dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
        dialog.setCancelable(false);

        dialog.show();
    }

    @Override
    protected Void doInBackground(Void... params) {
        // Runs on the background thread
        WallpaperManager wallpaperManager = WallpaperManager.getInstance(context);
        try {
            wallpaperManager.setBitmap(bitmap);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void res) {
        // Runs on the UI thread
        // Here you can perform any post-execute tasks, for example remove the
        // progress bar (if you set one).
        rlSetWallpaper.setEnabled(true);
        rlDownload.setEnabled(true);
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
            Toast.makeText(context, "Wallpaper setted successfully", Toast.LENGTH_SHORT).show();

    }
}