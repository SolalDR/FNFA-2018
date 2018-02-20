package com.example.solal.festivalnationaldufilmdanimation.entity

import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by sdussoutrevel on 11/12/2017.
 * Entity Event
 */
class Event constructor( jsonObject: JSONObject,  eventType: EventType? ) : Comparable<Event> {


    var id: Int? = null                             // ID use for Foreign Key
    var type: EventType? = null                     // Event has one EventType
    var description: String? = null                 // Long description
    var date_start: Date                            // The event debut at ...
    var date_end: Date                              // The event end at
    var scene_id: Int                               // Foreign Key for (Scene has_many Event) relation
    var name: String
    //var duration: Int? = null //Seconds
    //late init var author: Author
    //lateinit var scene: Scene
    //age_min: Int? = null                        // Ex: 6 => 6 years old or more
    //var background: String? = null                  // Background (not used yet)
    //var description_short: String? = null           // Excerpt

    init {
        id = jsonObject.getString("id").toInt()
        name = jsonObject.getString("name")
        scene_id = jsonObject.getInt("scene")

        if (jsonObject.has("description")) {
            description = jsonObject.getString("description")
        }

        val sdfmt1 = SimpleDateFormat("yyyy-MM-dd HH:mm:SS", Locale.getDefault())
        date_start = sdfmt1.parse(jsonObject.getString("date_start"))
        date_end = sdfmt1.parse(jsonObject.getString("date_end"))
        type = eventType

        //if(jsonObject.has("age_min")){ age_min = jsonObject.getString("age_min").toIntOrNull() }
        //if(jsonObject.has("description_short")){ description_short = jsonObject.getString("description_short") }
        //if(jsonObject.has("background")){ background = jsonObject.getString("background") }
    }


    // Debug method to control the value of the attributes
    fun inspect(indent: String? = "") {
        System.out.println(indent + "-----------EVENT-----------" + "\n"
                + indent + "Event\n"
                + indent + "id : " + id + "\n"
                + indent + "name : " + name
                + "\n" + indent + "type : " + name
                + "\n" + indent + "description : " + description
                + "\n" + indent + "Date Départ : " + date_start
                + "\n" + indent + "Date Départ : " + date_start
                + "\n" + indent + "Date Fin : " + date_end
                + "\n" + indent + "-----------/EVENT-----------"
        )
    }

    override fun compareTo(event: Event): Int {
        return date_start.compareTo(event.date_start);
    }
}