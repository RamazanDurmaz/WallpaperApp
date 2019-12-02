package com.ramazandurmaz.walpaper.presenter.otto;

import com.ramazandurmaz.walpaper.model.models.Walpaper;

import java.util.List;

public class GetAllWallpaper {
    public List<Walpaper> wallpaperList;

    public GetAllWallpaper(List<Walpaper> wallpaperList) {
        this.wallpaperList = wallpaperList;
    }

    public List<Walpaper> getWallpaperList() {
        return wallpaperList;
    }
}
