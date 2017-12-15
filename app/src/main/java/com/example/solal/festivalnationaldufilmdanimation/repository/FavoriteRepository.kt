package com.example.solal.festivalnationaldufilmdanimation.repository

import android.content.Context
import com.example.solal.festivalnationaldufilmdanimation.entity.Event
import com.example.solal.festivalnationaldufilmdanimation.entity.EventType
import org.json.JSONObject
import android.content.Context.MODE_PRIVATE
import com.example.solal.festivalnationaldufilmdanimation.helpers.FileHelper


/**
 * Created by sdussoutrevel on 15/12/2017.
 */
class FavoriteRepository constructor(manager_ref: DataRepository) {

    var manager: DataRepository
    var events = ArrayList<Event>()
    var eventsType = ArrayList<EventType>()

    init {
        manager = manager_ref
        System.out.println("-----------------"+ manager.findEventsByDay("2018-04-04"))
    }


    /*
     * find methods
     */
    fun findAllEvents(): ArrayList<Event> { return events }
    fun findAllEventTypes(): ArrayList<EventType> { return eventsType }


    fun mapEventsId(): ArrayList<Int> {
        var list = ArrayList<Int>()
        for(event in events){
            event.id?.apply {
                list.add(this)
            }
        }
        System.out.println("Hello : " + events)
        return list
    }

    fun mapEventTypesId(): ArrayList<Int> {
        var list = ArrayList<Int>()
        for(event in eventsType){
            event.id?.apply {
                list.add(this)
            }
        }
        return list
    }

    /*
     * Open file and update its content from stringify favorites
     */
    fun setStoredFavorites(){
        val FILENAME = "fnfa_favorite"

        var favoritesJSON: JSONObject = JSONObject()
        var eventsId: ArrayList<Int> = this.mapEventsId()
        var eventTypesId: ArrayList<Int> = this.mapEventTypesId()

        System.out.println("--------------------"+eventsId.toString())
        var string: String = "{ \"events\": [], \"eventTypes\": []}"

        FileHelper.writeFile(FILENAME, string, this.manager.context)
    }

    /*
     * Open fil and get the content
     */
    fun getStoredFavorite() {
        val FILENAME = "fnfa_favorite"
        val content = FileHelper.readFile(FILENAME, this.manager.context)
        System.out.println("File content ------------------------ \n"+content)
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