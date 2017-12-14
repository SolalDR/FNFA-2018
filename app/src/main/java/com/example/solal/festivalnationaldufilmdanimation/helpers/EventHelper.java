package com.example.solal.festivalnationaldufilmdanimation.helpers;

import com.example.solal.festivalnationaldufilmdanimation.entity.Event;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Created by sdussoutrevel on 14/12/2017.
 * Helper for events
 */

public class EventHelper {

    /*
     *  Used to order Events per date
     */

    public static ArrayList<Event> orderEvents(ArrayList<Event> events, String order ) {

        ArrayList<Event> newList = new ArrayList<Event>(events);

        Collections.sort(newList);

        if( order.equals("DESC")  ){
            Collections.reverse(newList);
        }

        return newList;
    }

}
