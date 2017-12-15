package com.example.solal.festivalnationaldufilmdanimation;

import android.app.Application;

import com.example.solal.festivalnationaldufilmdanimation.repository.DataRepository;
import com.example.solal.festivalnationaldufilmdanimation.repository.FavoriteRepository;


/**
 * Created by sdussoutrevel on 12/12/2017.
 * Override of Application.
 * Used to instantiate the dataMapper and load the ressource when the app start
 */

public class MyApplication extends Application  {

    private DataRepository manager;
    private FavoriteRepository favoriteRepo;

    @Override
    public void onCreate() {
        super.onCreate();
        this.manager = new DataRepository(this.getApplicationContext());
        this.manager.launchData();

        this.favoriteRepo = new FavoriteRepository(this.manager);
        this.favoriteRepo.setStoredFavorites();
    }

    private DataRepository getManager(){
        return this.manager;
    }
}