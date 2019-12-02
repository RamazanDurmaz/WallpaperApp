package com.ramazandurmaz.walpaper.presenter.adapter;

import android.content.Context;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

import com.ramazandurmaz.walpaper.presenter.interfaces.IReloadList;
import com.ramazandurmaz.walpaper.view.fragments.CategoriesFragment;
import com.ramazandurmaz.walpaper.view.fragments.LikedImagesFragment;
import com.ramazandurmaz.walpaper.view.fragments.RandomFragment;


public class ViewPagerAdapter extends FragmentPagerAdapter {

    private Context context;
    public ViewPagerAdapter(FragmentManager fm,Context context) {
        super(fm);
        this.context = context;
    }


    @Override
    public int getCount() {
        return 3;
    }

    @Override
    public Fragment getItem(int i) {
        if(i==0)
            return CategoriesFragment.getInstance();
        else if(i==1)
            return RandomFragment.getInstance();
        else if(i==2)
            return LikedImagesFragment.getInstance();
        return null;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position){
            case 0:
                return "Category";
            case 1:
                return "Random";
            case 2:
                return "LIked Images";
        }
        return "";
    }

}
