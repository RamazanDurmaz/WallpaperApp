package com.ramazandurmaz.walpaper.presenter.classes;

import com.ramazandurmaz.walpaper.common.MyApp;
import com.ramazandurmaz.walpaper.model.interfaces.ILoadFirebaseCategory;
import com.ramazandurmaz.walpaper.model.models.Category;
import com.ramazandurmaz.walpaper.model.models.Walpaper;
import com.ramazandurmaz.walpaper.model.pref.MyPref;
import com.ramazandurmaz.walpaper.model.providers.FirebaseDatabaseHelper;
import com.ramazandurmaz.walpaper.presenter.interfaces.IReloadList;
import com.ramazandurmaz.walpaper.presenter.interfaces.IWallpaperReloadList;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class DataManagement implements ILoadFirebaseCategory {
    private List<Walpaper> walpaperList;
    FirebaseDatabaseHelper firebaseDatabaseHelper;
    IReloadList listReloader;
    IWallpaperReloadList wallpaperReloadList;

    public void addListReloader(IReloadList reloader) {
        listReloader = reloader;
    }

    public void addWallpaperListReloader(IWallpaperReloadList reloader) {
        wallpaperReloadList = reloader;
    }

    public DataManagement() {
        firebaseDatabaseHelper = new FirebaseDatabaseHelper();
    }

    public void getCategory(){
        firebaseDatabaseHelper.addFirebaseResult(this);
        firebaseDatabaseHelper.getCategorytoFirebase();
    }
    public void getWallpaperByCategory(){
        firebaseDatabaseHelper.addFirebaseResult(this);
        firebaseDatabaseHelper.getWallpaperByCategoryFromFirebase();
    }

    public void getAllWallpaperList(){
        firebaseDatabaseHelper.addFirebaseResult(this);
        firebaseDatabaseHelper.getAllWallpaper();
    }

    public void sendFavoriteWallpaper(String imgUrl){
        MyApp.getPreferences().addFavoriteWallpapertoPref(imgUrl);
    }

    public List<String> getFavoriteWallpaper(){
       List<String> walpaperList = MyApp.getPreferences().getUrlsFromPref();
       return walpaperList;
    }

    public boolean isFavoriteWallpaper(String imgUrl){
        boolean isFavorite = false;
        List<String> walpaperList = getFavoriteWallpaper();
        if(walpaperList!=null){
        for (String walpaperUrl : walpaperList) {
            if(walpaperUrl.equals(imgUrl)){
                isFavorite = true;
                break;
            }
        }
        }
        return isFavorite;
    }

    public void removeFavorite(String imgUrl) {
        List<String> walpaperList = getFavoriteWallpaper();
        for (String walpaperUrl : walpaperList) {
            if(walpaperUrl.equals(imgUrl)){
                walpaperList.remove(walpaperUrl);
                break;
            }
        }
        MyApp.getPreferences().addWallpaperListtoPref(walpaperList);
    }


    @Override
    public void onFirebaseResult(List<Category> categoryList) {
            listReloader.onListReady(categoryList);
    }

    @Override
    public void onFirebaseWalpaperResult(List<Walpaper> walpaperList) {
        wallpaperReloadList.onWalpaperListReady(walpaperList);
    }


}
