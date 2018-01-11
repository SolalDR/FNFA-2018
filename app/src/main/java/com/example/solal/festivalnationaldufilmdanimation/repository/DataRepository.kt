package com.example.solal.festivalnationaldufilmdanimation.repository

import android.content.Context
import com.example.solal.festivalnationaldufilmdanimation.R
import com.example.solal.festivalnationaldufilmdanimation.entity.Event
import com.example.solal.festivalnationaldufilmdanimation.entity.EventType
import com.example.solal.festivalnationaldufilmdanimation.entity.Place
import com.example.solal.festivalnationaldufilmdanimation.entity.Scene
import com.example.solal.festivalnationaldufilmdanimation.helpers.JsonParser
import org.json.JSONArray
import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by sdussoutrevel on 12/12/2017.
 * This class is used to manage entity
 * It allow to access Events, Scenes and Place and provide methods.
 */

class DataRepository constructor(contextArg: Context ){

    var context: Context
    private var eventTypes: ArrayList<EventType> = ArrayList()
    private var events: ArrayList<Event> = ArrayList()
    private var scenes: ArrayList<Scene> = ArrayList()
    private var places: ArrayList<Place> = ArrayList()
    var dates: ArrayList<Date> = ArrayList()
    lateinit var formater: SimpleDateFormat;

    /*
     * Initialisation
     */

    init {
        context = contextArg
        formater = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
    }

    fun launchData(){
        loadEventsType()
        loadEvents()
        System.out.println("------NB dates---------"+dates.size)
        loadScenes()
    }

    /*
     * Find all
     */

    fun findAllScenes(): ArrayList<Scene>{ return this.scenes }
    fun findAllEvents(): ArrayList<Event> { return this.events }
    fun findAllPlaces(): ArrayList<Place>{ return this.places }

    /*
     * Find single
     */

    fun findEventTypeById(id: Int): EventType? {
        for (eventType in this.eventTypes) {
            if(eventType.id == id){
                return eventType
            }
        }
        return null
    }

    fun findEventById(id: Int): Event? {
        for(event in events){
            if( event.id == id ){
                return event;
            }
        }
        return null;
    }

    /*
     * Find parts
     */

    fun findEventsByScene(id:Int): ArrayList<Event>{
        val result = ArrayList<Event>()
        for(event in events){
            if(event.scene_id == id){
                result.add(event)
            }
        }
        return result
    }

    /*
     * Get the list of all the events of a day
     * Ex : this.findEventsByDay( "2018-04-04" )
     */
    fun findEventsByDay(dateStr: String): ArrayList<Event> {

        var eventsList = ArrayList<Event>()
        formater = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val date = formater.parse(dateStr)
        for(event in events) {
            var dateEvent: Date = formater.parse(formater.format(event.date_start))
            if( dateEvent.time == date.time ){
                eventsList.add(event)
            }
        }
        return eventsList
    }

    /*
     * Get all the events activate for a specific date
     */
    fun findEventsAtTime(date: Date) : ArrayList<Event> {
        val atTimesList = ArrayList<Event>()
        for(event in events){
            if( event.date_start.compareTo(date) * date.compareTo(event.date_end) > 0 ) {
                atTimesList.add(event)
            }
        }
        return atTimesList
    }

    /*
     *
     */
    private fun addDateIfExist(date: Date){
        var found = false;
        val dateFormat = formater.parse(formater.format(date));

        for(dateItem in dates){
            if( dateFormat == dateItem){
                found = true;
            }
        }
        if( found == false ){
            dates.add(dateFormat)
        }
    }

    /*
     * Loader methods
     */

    private fun loadEventsType() {
        val eventsTypeRaw = JsonParser.getStringFromJson(R.raw.events_type, context)
        val c = JSONArray(eventsTypeRaw)
        for (i in 0 until c.length()) {
            val obj = c.getJSONObject(i)
            eventTypes.add(EventType(obj))
        }
    }

    private fun loadEvents() {
        val eventsRaw = JsonParser.getStringFromJson(R.raw.events, context)
        val c = JSONArray(eventsRaw)
        for (i in 0 until c.length()) {
            val obj = c.getJSONObject(i)
            val type = obj.getString("type")
            var typeRank: Int? = null
            type?.apply {
                typeRank = this.toInt()
            }
            typeRank?.apply {
                val eventType = this@DataRepository.findEventTypeById(this)
                val event = Event(obj, eventType);
                events.add(event)
                this@DataRepository.addDateIfExist(event.date_start)
            }
        }
    }

    private fun loadScenes() {
        val scenesRaw = JsonParser.getStringFromJson(R.raw.scenes, context)
        val c = JSONArray(scenesRaw)
        for (i in 0 until c.length()) {
            val obj = c.getJSONObject(i)
            val places = this.loadPlaces(obj.getJSONArray("places"))
            val scene = Scene(obj)
            scene.id?.apply {
                scene.events = findEventsByScene(this)
            }
            for(place in places){
                scene.places.add(place)
            }

            scenes.add(scene)
        }
    }

    private fun loadPlaces(json: JSONArray):ArrayList<Place> {
        val localPlaces = ArrayList<Place>()
        for(i in 0 until json.length()){
            val obj:JSONObject = json.getJSONObject(i)
            val place = Place(obj)
            localPlaces.add(place)
            places.add(place)
        }
        return localPlaces
    }
}