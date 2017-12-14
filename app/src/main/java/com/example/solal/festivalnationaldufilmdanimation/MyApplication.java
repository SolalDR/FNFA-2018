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
 */

public class MyApplication extends Application  {

    private DataManager dataManager;

    @Override
    public void onCreate() {
        super.onCreate();

        Moshi moshi = new Moshi.Builder().build();

        String scenesRaw = JsonParser.getStringFromJson(R.raw.scenes, this.getApplicationContext());

        this.dataManager = new DataManager(this.getApplicationContext());


        //List<Scene> scenes = this.loadScenes(moshi);
        //if(!scenes.isEmpty()) {
        //    this.dataManager.setScenes(new ArrayList<Scene>(this.loadScenes(moshi)));
        //}

        this.dataManager.launchData();
        ArrayList<Scene> scenes = this.dataManager.findAllScenes();
        for (int counter = 0; counter < scenes.size(); counter++) {
            scenes.get(counter).inspect("");
        }
    }

    public void onActivityResumed(){
        this.dataManager = new DataManager(this.getApplicationContext());
        this.dataManager.launchData();
    }

    private DataManager getDataManager(){
        return this.dataManager;
    }

    private List<EventType> loadEventsType(Moshi moshi) {

        String eventsTypeRaw = JsonParser.getStringFromJson(R.raw.events_type, this.getApplicationContext());

        Type type = Types.newParameterizedType(List.class, EventType.class);
        JsonAdapter<List<EventType>> adapter = moshi.adapter(type);

        List<EventType> eventsTypes = null;
        try {
            eventsTypes = adapter.fromJson(eventsTypeRaw);
        } catch(IOException e) {
            System.out.println("There is no scenes");
        }
        return eventsTypes;
    }

    private List<Event> loadEvents(Moshi moshi) {

        String eventsRaw = JsonParser.getStringFromJson(R.raw.events, this.getApplicationContext());

        Type type = Types.newParameterizedType(List.class, Event.class);
        JsonAdapter<List<Event>> adapter = moshi.adapter(type);

        List<Event> events = null;
        try {
            events = adapter.fromJson(eventsRaw);
        } catch(IOException e) {
            System.out.println("There is no events");
        }
        return events;
    }

    private List<Scene> loadScenes(Moshi moshi) {

        String scenesRaw = JsonParser.getStringFromJson(R.raw.scenes, this.getApplicationContext());

        Type type = Types.newParameterizedType(List.class, Scene.class);
        JsonAdapter<List<Scene>> adapter = moshi.adapter(type);

        List<Scene> scenes = null;
        try {
            scenes = adapter.fromJson(scenesRaw);
        } catch(IOException e) {
            System.out.println("There is no scenes");
        }
        return scenes;
    }

}
