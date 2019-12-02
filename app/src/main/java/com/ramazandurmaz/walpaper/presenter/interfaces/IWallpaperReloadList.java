package com.ramazandurmaz.walpaper.presenter.interfaces;

import com.ramazandurmaz.walpaper.model.models.Walpaper;

import java.util.List;

public interface IWallpaperReloadList {
    void onWalpaperListReady(List<Walpaper> categoryList);
}
