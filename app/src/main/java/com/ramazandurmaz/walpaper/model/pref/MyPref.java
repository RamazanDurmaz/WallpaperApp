package com.ramazandurmaz.walpaper.model.pref;

import android.content.Context;
import android.content.SharedPreferences;

import com.google.gson.Gson;

import com.google.gson.reflect.TypeToken;
import com.ramazandurmaz.walpaper.common.Common;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class MyPref {
    private SharedPreferences pref;
    private SharedPreferences.Editor editor;

    Context _context;

    private final String PREFER_NAME = Common.PREFER_NAME;

    public MyPref(Context _context) {
        this._context = _context;
        pref = _context.getSharedPreferences(PREFER_NAME, Context.MODE_PRIVATE);
        editor = pref.edit();
    }

    public void addFavoriteWallpapertoPref(String imgUrl){
        Gson gson = new Gson();
        List<String> urlList = getUrlsFromPref();
        if(urlList==null){
            List<String> img = new ArrayList<>();
            img.add(imgUrl);
            String json = gson.toJson(img);
            editor.putString(Common.IMG_URL_KEY, json);
            editor.apply();
        }else {
            urlList.add(imgUrl);
            String json = gson.toJson(urlList);
            editor.putString(Common.IMG_URL_KEY, json);
            editor.apply();
        }
    }

    public List<String> getUrlsFromPref(){
        Gson gson = new Gson();
        String json = pref.getString(Common.IMG_URL_KEY, "");
        Type type = new TypeToken<List<String>>() {}.getType();
        return gson.fromJson(json, type);
    }

    public void addWallpaperListtoPref(List<String> walpaperList) {
        Gson gson = new Gson();
        String json = gson.toJson(walpaperList);
        editor.putString(Common.IMG_URL_KEY, json);
        editor.apply();
    }
}
