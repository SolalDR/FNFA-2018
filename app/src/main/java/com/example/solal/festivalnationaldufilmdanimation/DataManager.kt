package com.example.solal.festivalnationaldufilmdanimation

import android.content.Context
import com.example.solal.festivalnationaldufilmdanimation.entity.Event
import com.example.solal.festivalnationaldufilmdanimation.entity.EventType
import com.example.solal.festivalnationaldufilmdanimation.entity.Place
import com.example.solal.festivalnationaldufilmdanimation.entity.Scene
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import org.json.JSONArray
import org.json.JSONObject
import java.io.IOException
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by sdussoutrevel on 12/12/2017.
 */

class DataManager constructor(
        contextArg: Context
){
    var context: Context
    var eventTypes: ArrayList<EventType> = ArrayList()
    var events: ArrayList<Event> = ArrayList()
    var scenes: ArrayList<Scene> = ArrayList()
    var places: ArrayList<Place> = ArrayList()

    init {
        context = contextArg;
    }

    fun launchData(){
        loadEventsType()
        loadEvents()
        loadScenes()
    }

    fun findAllScenes(): ArrayList<Scene>{
        return this.scenes
    }

    fun findAllEvents(): ArrayList<Event>{
        return this.events
    }

    fun findAllPlaces(): ArrayList<Place>{
        return this.places
    }


    fun findEventTypeById(id: Int): EventType? {
        for (eventType in this.eventTypes) {
            if(eventType.id == id){
                return eventType;
            }
        }
        return null;
    }

    fun findEventsByScene(id:Int): ArrayList<Event>{
        var result: ArrayList<Event> = ArrayList<Event>()
        for(event in events){
            if(event.scene_id == id){
                result.add(event)
            }
        }
        return result;
    }

    private fun loadEventsType() {
        val eventsTypeRaw = JsonParser.getStringFromJson(R.raw.events_type, context)
        val c: JSONArray = JSONArray(eventsTypeRaw)
        for (i in 0 until c.length()) {
            val obj = c.getJSONObject(i)
            eventTypes.add(EventType(obj))
        }
    }

    private fun loadEvents() {
        val eventsRaw = JsonParser.getStringFromJson(R.raw.events, context)
        val c: JSONArray = JSONArray(eventsRaw)
        for (i in 0 until c.length()) {
            val obj = c.getJSONObject(i)
            val type = obj.getString("type")
            var typeRank: Int? = null;
            type?.apply {
               typeRank = this.toInt()
            }
            typeRank?.apply {
                val eventType = this@DataManager.findEventTypeById(this)
                events.add(Event(obj, eventType))
            }
        }
    }

    private fun loadScenes() {
        val scenesRaw = JsonParser.getStringFromJson(R.raw.scenes, context)
        val c: JSONArray = JSONArray(scenesRaw)
        for (i in 0 until c.length()) {
            val obj = c.getJSONObject(i)
            val places = this.loadPlaces(obj.getJSONArray("places"))
            val scene = Scene(obj)
            scene.id?.apply {
                scene.events = findEventsByScene(this)
            }
            for(place in places){
                scene.places.add(place);
            }

            scenes.add(scene)
        }
    }

    private fun loadPlaces(json: JSONArray):ArrayList<Place> {
        val localPlaces:ArrayList<Place> = ArrayList<Place>()
        for(i in 0 until json.length()){
            val obj:JSONObject = json.getJSONObject(i)
            val place: Place = Place(obj)
            localPlaces.add(place)
            places.add(place)
        }
        return localPlaces;
    }
}