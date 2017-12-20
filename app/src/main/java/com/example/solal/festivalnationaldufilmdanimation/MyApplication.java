package com.example.solal.festivalnationaldufilmdanimation;

import android.app.Application;

import com.example.solal.festivalnationaldufilmdanimation.entity.Event;
import com.example.solal.festivalnationaldufilmdanimation.repository.DataRepository;
import com.example.solal.festivalnationaldufilmdanimation.repository.FavoriteRepository;

import java.util.ArrayList;


/**
 * Created by sdussoutrevel on 12/12/2017.
 * Override of Application.
 * Used to instantiate the dataMapper and load the ressource when the app start
 */

public class MyApplication extends Application  {

    private DataRepository manager;
    private FavoriteRepository favoriteRepo;

    private static MyApplication application;

    public MyApplication getInstance() {
        return application;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        application = this;
        this.manager = new DataRepository(this.getApplicationContext());
        this.manager.launchData();

        this.favoriteRepo = new FavoriteRepository(this.manager);
        this.favoriteRepo.getStoredFavorite();
    }


    public DataRepository getManager(){
        return this.manager;
    }
}