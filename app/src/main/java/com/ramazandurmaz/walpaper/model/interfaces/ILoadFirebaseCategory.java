package com.ramazandurmaz.walpaper.model.interfaces;

import com.ramazandurmaz.walpaper.model.models.Category;
import com.ramazandurmaz.walpaper.model.models.Walpaper;

import java.util.List;

public interface ILoadFirebaseCategory {
    void onFirebaseResult(List<Category> categoryList);
    void onFirebaseWalpaperResult(List<Walpaper> walpaperList);
}
