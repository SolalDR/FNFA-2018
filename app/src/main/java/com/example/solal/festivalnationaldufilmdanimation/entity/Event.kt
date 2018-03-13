package com.example.solal.festivalnationaldufilmdanimation.entity

import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by sdussoutrevel on 11/12/2017.
 * Entity Event
 */
class Event constructor( jsonObject: JSONObject,  eventType: Category? ) : Comparable<Event> {


    var id: Int? = null                             // ID use for Foreign Key
    var category: Category? = null                     // Event has one EventType
    var description: String? = null                 // Long description
    var date_start: Date                            // The event debut at ...
    var date_end: Date                              // The event end at
    var place_id: Int                               // Foreign Key for (Scene has_many Event) relation
    var name: String
    var image: String

    init {
        id = jsonObject.getString("id").toInt()
        name = jsonObject.getString("nom")
        place_id = jsonObject.getInt("lieu")
        image = jsonObject.getString("image")

        if (jsonObject.has("description")) {
            description = jsonObject.getString("description")
        }
        val sdfmt1 = SimpleDateFormat("yyyy-MM-dd HH:mm", Locale.getDefault())

        date_start = sdfmt1.parse(stringifyDate(jsonObject, "debut"))
        date_end = sdfmt1.parse(stringifyDate(jsonObject, "fin"))

        category = eventType
    }


    private fun stringifyDate(obj: JSONObject, suffixe: String): String {
        return obj.getString("annee_"+ suffixe) + "-" +
                String.format("%02d", obj.getInt("mois_" + suffixe) )+ "-" +
                String.format("%02d", obj.getInt("jour_" + suffixe) ) + " " +
                String.format("%02d", obj.getInt("heure_" + suffixe) ) + ":" +
                String.format("%02d", obj.getInt("minute_" + suffixe) )
    }

    fun getDateFormat() : Date {
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault());

        return dateFormat.parse(dateFormat.format(this.date_start))
    }



    // Debug method to control the value of the attributes
    private fun inspect(indent: String? = "") {
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

    override fun compareTo(other: Event): Int {
        return date_start.compareTo(other.date_start);
    }
}