package com.example.solal.festivalnationaldufilmdanimation;

import android.app.Application;
import com.example.solal.festivalnationaldufilmdanimation.entity.Event;
import com.example.solal.festivalnationaldufilmdanimation.helpers.DataManager;
import com.example.solal.festivalnationaldufilmdanimation.helpers.EventHelper;

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

        ArrayList<Event> events = EventHelper.orderEvents(this.manager.findAllEvents(), "ASC");
        for(int i=0; i<events.size(); i++){
            events.get(i).inspect("");
        }
    }

    private DataManager getManager(){
        return this.manager;
    }
}