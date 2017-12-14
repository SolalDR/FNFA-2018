package com.example.solal.festivalnationaldufilmdanimation;

import android.app.Application;

import com.example.solal.festivalnationaldufilmdanimation.entity.Event;
import com.example.solal.festivalnationaldufilmdanimation.entity.EventType;
import com.example.solal.festivalnationaldufilmdanimation.entity.Scene;
import com.squareup.moshi.JsonAdapter;
import com.squareup.moshi.Moshi;
import com.squareup.moshi.Types;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by sdussoutrevel on 12/12/2017.
 * Override of Application.
 * Used to instantiate the dataMapper and load the ressource when the app start
 */

public class MyApplication extends Application  {

    private DataManager dataManager;

    @Override
    public void onCreate() {
        super.onCreate();

        this.dataManager = new DataManager(this.getApplicationContext());
        this.dataManager.launchData();
        ArrayList<Scene> scenes = this.dataManager.findAllScenes();
        for (int counter = 0; counter < scenes.size(); counter++) {
            scenes.get(counter).inspect("");
        }
    }

    private DataManager getDataManager(){
        return this.dataManager;
    }
}
