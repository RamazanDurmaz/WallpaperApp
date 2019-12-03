package com.ramazandurmaz.walpaper.view.classes;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;
import android.os.Environment;
import android.os.PowerManager;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ramazandurmaz.walpaper.common.Common;
import com.ramazandurmaz.walpaper.view.activities.WallpaperViewActivity;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Calendar;

public class DownloadTask extends AsyncTask<String,Integer,String> {

    private ProgressDialog dialog;
    private Context context;
    private PowerManager.WakeLock mWakeLock;
    RelativeLayout rlDownload,rlSetWallpaper;

    public DownloadTask(Context context, RelativeLayout rlDownload, RelativeLayout rlSetWallpaper) {
        this.context=context;
        dialog = new ProgressDialog(context);
        this.rlDownload = rlDownload;
        this.rlSetWallpaper = rlSetWallpaper;
    }

    @Override
    protected void onPreExecute() {
       rlSetWallpaper.setEnabled(false);
       rlDownload.setEnabled(false);
        dialog.setMessage("Downloading image...");
        dialog.setIndeterminate(true);
        dialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
        dialog.setCancelable(false);
        dialog.show();
    }

    @Override
    protected String doInBackground(String... strings) {
        try {
            URL url = new URL(strings[0]);
            URLConnection urlConnection = url.openConnection();
            urlConnection.connect();
            int fileLenght =0;
            fileLenght = urlConnection.getContentLength();
            InputStream inputStream = new BufferedInputStream(url.openStream());
            File newFolder = new File(Environment.getExternalStorageDirectory() + File.separator + "HDWallpaper");
            if (!newFolder.exists()) {
                newFolder.mkdir();
            }
            Calendar currentTime = Calendar.getInstance();
            File inputFile = new File(newFolder, "HD" + currentTime.getTimeInMillis()+".jpg");
            OutputStream outputStream = new FileOutputStream(inputFile);

            byte[] data = new byte[4096];
            int total = 0;
            int count = 0;

            while ((count = inputStream.read(data)) != -1) {
                total += count;
                publishProgress((total * 100) / fileLenght);
                outputStream.write(data, 0, count);
            }
            outputStream.flush();
            outputStream.close();
            inputStream.close();

        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Download Complete";
    }


    @Override
    protected void onProgressUpdate(Integer... values) {
        dialog.setProgress(values[0]);
    }

    @Override
    protected void onPostExecute(String result) {
        rlSetWallpaper.setEnabled(true);
        rlDownload.setEnabled(true);
        if (dialog.isShowing()) {
            dialog.dismiss();
        }
        Toast.makeText(context, result, Toast.LENGTH_SHORT).show();
    }
}
