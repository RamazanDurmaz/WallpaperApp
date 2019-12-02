package com.ramazandurmaz.walpaper.model.providers;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.ramazandurmaz.walpaper.common.Common;
import com.ramazandurmaz.walpaper.model.interfaces.ILoadFirebaseCategory;
import com.ramazandurmaz.walpaper.model.models.Category;
import com.ramazandurmaz.walpaper.model.models.Walpaper;
import com.ramazandurmaz.walpaper.presenter.otto.BusProvider;
import com.ramazandurmaz.walpaper.presenter.otto.GetAllWallpaper;

import java.util.ArrayList;
import java.util.List;

public class FirebaseDatabaseHelper {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference categoryBackground;
    private DatabaseReference walpaperBackground;
    private List<Category> categoryList;
    private List<Walpaper> walpaperList;
    ILoadFirebaseCategory iLoadFirebaseCategory;

    public FirebaseDatabaseHelper() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        categoryBackground = firebaseDatabase.getReference(Common.STR_CATEGORY_BACKGROUND);
        walpaperBackground = firebaseDatabase.getReference(Common.STR_WALPAPER_BACKGROUND);
        categoryList = new ArrayList<>();
        walpaperList = new ArrayList<>();
    }

    public void addFirebaseResult(ILoadFirebaseCategory iLoadFirebaseCategory) {
        this.iLoadFirebaseCategory = iLoadFirebaseCategory;
    }


    public void getCategorytoFirebase() {
        categoryBackground.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                categoryList.clear();
               for (DataSnapshot categorySnapshot : dataSnapshot.getChildren()){
                   Category category = categorySnapshot.getValue(Category.class);
                   categoryList.add(category);
               }
                iLoadFirebaseCategory.onFirebaseResult(categoryList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

    public void getWallpaperByCategoryFromFirebase(){
        walpaperBackground.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                walpaperList.clear();
                for (DataSnapshot walpaperSnapshot : dataSnapshot.getChildren()){
                    Walpaper wallpaper = walpaperSnapshot.getValue(Walpaper.class);

                    assert wallpaper != null;
                    if(wallpaper.getCategoryId().equals("" + Common.CATEGORY_ID_SELECTED)) {
                        walpaperList.add(wallpaper);
                    }
                }
                iLoadFirebaseCategory.onFirebaseWalpaperResult(walpaperList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }

    public void getAllWallpaper(){
        walpaperBackground.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                walpaperList.clear();
                for (DataSnapshot walpaperSnapshot : dataSnapshot.getChildren()){
                    Walpaper wallpaper = walpaperSnapshot.getValue(Walpaper.class);
                        walpaperList.add(wallpaper);
                }
                BusProvider.getInstance().post(new GetAllWallpaper(walpaperList));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }

        });
    }



}
