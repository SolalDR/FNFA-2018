package com.example.solal.festivalnationaldufilmdanimation.helpers;

import com.example.solal.festivalnationaldufilmdanimation.entity.Event;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by sdussoutrevel on 14/12/2017.
 * Helper for events
 */

public class EventHelper {
    public static ArrayList<Event> orderEvents(ArrayList<Event> events, String order ) {
        ArrayList<Event> newList = new ArrayList<Event>(events);
        Collections.sort(newList);

        if( order == "DESC" ){
            Collections.reverse(newList);
        }
        return newList;
    }
}
