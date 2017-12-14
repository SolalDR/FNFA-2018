package com.example.solal.festivalnationaldufilmdanimation;

import android.app.Application;
import com.example.solal.festivalnationaldufilmdanimation.entity.Scene;
import com.example.solal.festivalnationaldufilmdanimation.helpers.DataManager;
import java.util.ArrayList;

/**
 * Created by sdussoutrevel on 12/12/2017.
 * Override of Application.
 * Used to instantiate the dataMapper and load the ressource when the app start
 */

public class MyApplication extends Application  {

    private DataManager manager;

    @Override
    public void onCreate() {
        super.onCreate();
        this.manager = new DataManager(this.getApplicationContext());
        this.manager.launchData();
        ArrayList<Scene> scenes = this.manager.findAllScenes();
        for (int counter = 0; counter < scenes.size(); counter++) {
            scenes.get(counter).inspect("");
        }
    }

    private DataManager getManager(){
        return this.manager;
    }
}