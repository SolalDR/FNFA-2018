package com.example.solal.festivalnationaldufilmdanimation.helpers

import com.example.solal.festivalnationaldufilmdanimation.entity.Event
import com.example.solal.festivalnationaldufilmdanimation.entity.EventType
import org.json.JSONObject

/**
 * Created by sdussoutrevel on 15/12/2017.
 */
class FavoriteManager constructor(manager_ref: DataManager) {

    var manager: DataManager
    var events = ArrayList<Event>()
    var eventsType = ArrayList<EventType>()

    init {
        manager = manager_ref
    }


    /*
     * find methods
     */
    fun findAllEvents(): ArrayList<Event> { return events }
    fun findAllEventTypes(): ArrayList<EventType> { return eventsType }


    /*
     * Open file and update its content from stringify favorites
     */
    fun setStoredFavorites(){

    }

    /*
     * Open fil and get the content
     */
    fun getStoredFavorite() {

    }

    /*
     * Transform events & eventsType in json string
     */
    fun stringifyFavorite(): String {
        return ""
    }

    /*
     * Transform a JSON string in a JSON Object
     */
    fun parseFavorite(): JSONObject {
        return JSONObject()
    }
}