package com.ramazandurmaz.walpaper.presenter.interfaces;

import com.ramazandurmaz.walpaper.model.models.Category;
import com.ramazandurmaz.walpaper.model.models.Walpaper;

import java.util.List;

public interface IReloadList {
    void onListReady(List<Category> categoryList);
}
