package com.example.solal.festivalnationaldufilmdanimation.entity

import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by sdussoutrevel on 11/12/2017.
 */
class Event constructor(
        jsonObject: JSONObject,
        eventType: EventType?
){

    var id: Int? = null
    var age_min: Int? = null  // Ex: 6 => 6 years old or more
    var type: EventType? = null
    var background: String? = null
    var description_short: String? = null
    var description: String? = null
    var date_end: Date
    var date_start: Date
    var scene_id: Int? = null
    lateinit var name: String

    //var duration: Int? = null //Seconds
    //lateinit var author: Author
    //lateinit var scene: Scene

    init {

        id = jsonObject.getString("id").toInt()
        name = jsonObject.getString("name")
        scene_id = jsonObject.getInt("scene")
        if(jsonObject.has("age_min")){
            age_min = jsonObject.getString("age_min").toIntOrNull()
        }
        if(jsonObject.has("description_short")){
            description_short = jsonObject.getString("description_short")
        }
        if(jsonObject.has("description")){
            description = jsonObject.getString("description")
        }
        if(jsonObject.has("background")){
            background = jsonObject.getString("background")
        }

        val sdfmt1 = SimpleDateFormat("yyyy-MM-dd HH:mm:SS", Locale.getDefault())
        date_start = sdfmt1.parse(jsonObject.getString("date_start"))
        date_end = sdfmt1.parse(jsonObject.getString("date_end"))

        type = eventType

    }

    fun inspect(indent:String? = ""){
        System.out.println(indent + "-----------EVENT-----------");
        System.out.println(indent + "Event");
        System.out.println(indent + "id : "+ id);
        System.out.println(indent + "name : "+ name);
        System.out.println(indent + "age_min : "+ age_min);
        System.out.println(indent + "type : "+ name);
        System.out.println(indent + "bg : "+ background);
        System.out.println(indent + "description : "+ description);
        System.out.println(indent + "Date Départ : "+ date_start);
        System.out.println(indent + "Date Fin : "+ date_end);
        System.out.println(indent + "-----------/EVENT-----------");
    }
}