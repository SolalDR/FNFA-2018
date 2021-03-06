package com.example.solal.festivalnationaldufilmdanimation.repository

import android.content.Context
import com.example.solal.festivalnationaldufilmdanimation.R
import com.example.solal.festivalnationaldufilmdanimation.entity.Event
import com.example.solal.festivalnationaldufilmdanimation.entity.Category
import com.example.solal.festivalnationaldufilmdanimation.entity.Place
import com.example.solal.festivalnationaldufilmdanimation.helpers.JsonParser
import org.json.JSONArray
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList

/**
 * Created by sdussoutrevel on 12/12/2017.
 * This class is used to manage entity
 * It allow to access Events, Places and provide methods.
 */
class DataRepository constructor(contextArg: Context ){

    var context: Context
    private var eventTypes: ArrayList<Category> = ArrayList()
    private var events: ArrayList<Event> = ArrayList()
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
        loadPlaces()
    }

    /*
     * Find all
     */

    fun findAllPlaces(): ArrayList<Place>{ return this.places }
    fun findAllEvents(): ArrayList<Event> { return this.events }
    fun findAllDates(): ArrayList<Date>{ return this.dates }


    fun findAllDatesOrder(): ArrayList<ArrayList<Event>> {
        val dates = this.findAllDates()

        val result = ArrayList<ArrayList<Event>>()
        for( date in dates ){  result.add(ArrayList()) }

        for(event in events) {
            for(i in 0..(dates.size - 1)) {
                if( event.getDateFormat() == dates[i]) {
                    result[i].add(event)
                }
            }
        }

        return result
    }
    /*
     * Find single
     */

    fun findEventTypeById(id: Int): Category? {
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
                return event
            }
        }
        return null
    }

    fun findCategoryById(id: Int): Category? {
        for(event in eventTypes){
            if( event.id == id ){
                return event
            }
        }
        return null
    }


    /*
     * Find parts
     */
    fun findEventsByPlace(id:Int): ArrayList<Event>{
        val result = ArrayList<Event>()
        for(event in events){
            if(event.place_id == id){
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

        formater = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        val eventsList = ArrayList<Event>()
        val date = formater.parse(dateStr)
        var dateEvent: Date

        for(event in events) {
            dateEvent = formater.parse(formater.format(event.date_start))
            if( dateEvent.time == date.time ){
                eventsList.add(event)
            }
        }

        eventsList.sort()
        return eventsList
    }


    /*
     * Get all the events activate for a specific date
     */
    fun findEventsAtTime(date: Date) : ArrayList<Event> {
        val eventsList = ArrayList<Event>()
        formater = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())

        for(event in events) {
            val dateEvent: Date = formater.parse(formater.format(event.date_start))
            if( dateEvent.time == date.time ){
                eventsList.add(event)
            }
        }

        eventsList.sort()
        return eventsList
    }


    /*
     * Get all the events activate or being activate with limit
     */
    fun findNextEvents(limitTmp: Int): ArrayList<Event> {
        var limit = limitTmp
        val eventsList = ArrayList<Event>()
        val currentDate = Calendar.getInstance().time
        var dateEvent: Date
        for(event in events) {
            dateEvent = formater.parse(formater.format(event.date_start))
            if( dateEvent.time >= currentDate.time ){
                eventsList.add(event)
            }
        }
        eventsList.sort()
        if( eventsList.size < limit ) limit = eventsList.size

        return ArrayList(eventsList.subList(0, limit))
    }


    fun findPlaceById(id: Int): Place? {
        for(place in places){
            if( place.id == id ){
                return place
            }
        }
        return null
    }

    /*
     * Add a new date if not register yet
     */
    private fun addDateIfExist(date: Date){
        var found = false
        val dateFormat = formater.parse(formater.format(date))

        for(dateItem in dates){
            if( dateFormat == dateItem){
                found = true
            }
        }
        if( !found ){
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
            eventTypes.add(Category(obj))
        }
    }

    private fun loadEvents() {
        val eventsRaw = JsonParser.getStringFromJson(R.raw.events, context)
        val c = JSONArray(eventsRaw)
        for (i in 0 until c.length()) {
            val obj = c.getJSONObject(i)
            val type = obj.getString("categorie_id")
            var typeRank: Int? = null
            type?.apply {
                typeRank = this.toInt()
            }
            typeRank?.apply {
                val eventType = this@DataRepository.findEventTypeById(this)
                val event = Event(obj, eventType)
                events.add(event)
                this@DataRepository.addDateIfExist(event.date_start)
            }
        }
    }

    private fun loadPlaces() {
        val placesRaw = JsonParser.getStringFromJson(R.raw.places, context)
        val c = JSONArray(placesRaw)
        for (i in 0 until c.length()) {
            val obj = c.getJSONObject(i)
            val place = Place(obj)

            places.add(place)
        }
    }
}