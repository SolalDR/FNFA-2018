package com.example.solal.festivalnationaldufilmdanimation;

import android.app.Application;

/**
 * Created by sdussoutrevel on 12/12/2017.
 */

public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        String eventsRaw = JsonParser.getStringFromJson(R.raw.events, this.getApplicationContext());
        String scenesRaw = JsonParser.getStringFromJson(R.raw.scenes, this.getApplicationContext());

    }
}
