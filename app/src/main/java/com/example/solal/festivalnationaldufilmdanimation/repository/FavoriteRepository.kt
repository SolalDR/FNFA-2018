package com.example.solal.festivalnationaldufilmdanimation.repository

import android.app.Notification
import android.app.PendingIntent
import android.content.Intent
import com.example.solal.festivalnationaldufilmdanimation.R
import com.example.solal.festivalnationaldufilmdanimation.entity.Event
import com.example.solal.festivalnationaldufilmdanimation.entity.Category
import org.json.JSONObject
import com.example.solal.festivalnationaldufilmdanimation.helpers.FileHelper
import java.util.*
import android.content.Context.NOTIFICATION_SERVICE
import android.app.NotificationManager
import android.app.AlarmManager
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Color
import com.example.solal.festivalnationaldufilmdanimation.helpers.NotificationPublisher
import android.os.SystemClock
import android.support.v4.app.NotificationCompat
import com.example.solal.festivalnationaldufilmdanimation.helpers.JsonParser
import android.support.v4.app.NotificationManagerCompat
import com.example.solal.festivalnationaldufilmdanimation.InfoActivity
import android.R.attr.description
import android.app.NotificationChannel
import android.content.Context.BIND_IMPORTANT


/**
 * Created by sdussoutrevel on 15/12/2017.
 * Allow to access to favorite which are stored in local file
 */
class FavoriteRepository constructor(private val manager: DataRepository) {

    var events = ArrayList<Event>()
    var eventTypes = ArrayList<Category>()
    var eventsSort = ArrayList<ArrayList<Event>>()

    init {
        getStoredFavorite()
    }


    fun exist(event: Event): Boolean {
        for( test in events) {
            if( test.id == event.id ){
                return true
            }
        }
        return false
    }



    fun addEvent(event: Event){
        if( !exist(event) ){
            this.events.add(event)
        }
        setStoredFavorites()
    }

    fun removeEvent(event: Event){
        this.events.remove(event)
        setStoredFavorites()
    }

    private fun sortPerDay(events: ArrayList<Event>) {
        val list = ArrayList<ArrayList<Event>>()
        events.sort()

        // Each event
        var date: Date?
        var found: Boolean

        for(event in events) {
            date = event.getDateFormat()
            found = false

            // Each day list
            for(listDayTmp in list){
                // If event match with current day list
                if( listDayTmp[0].getDateFormat() == date ){
                    listDayTmp.add(event)
                    found = true
                }
            }
            // Else create new eventsList
            if(!found) {
                list.add(ArrayList())
                list[list.size - 1].add(event)
            }
        }

        eventsSort = list
    }

    /*
     * find methods
     */
    fun findAllEvents(): ArrayList<Event> { return events }
    fun findAllEventTypes(): ArrayList<Category> { return eventTypes }


    /*
     * Get the ID of each Events for storage
     */
    private fun getEventsId(): ArrayList<Int> {
        val list = ArrayList<Int>()
        for(event in events){
            event.id?.apply {
                list.add(this)
            }
        }
        return list
    }

    /*
     * Get the ID of each EventsType for storage
     */
    private fun getEventTypesId(): ArrayList<Int> {
        val list = ArrayList<Int>()
        for(event in eventTypes){
            list.add(event.id)
        }
        return list
    }

    /*
     * Open file and update its content from stringify favorites
     */
    private fun setStoredFavorites(){
        val eventsId: ArrayList<Int> = this.getEventsId()
        val eventTypesId: ArrayList<Int> = this.getEventTypesId()
        val string: String = "{ \"events\": "+eventsId.toString()+", \"eventTypes\": "+eventTypesId.toString()+"}"

        FileHelper.writeFile(FavoriteRepository.FILENAME, string, this.manager.context)

        sortPerDay(events)
    }

    /*
     * Open fil and get the content
     */
    private fun getStoredFavorite() {

        val content = FileHelper.readFile(FavoriteRepository.FILENAME, this.manager.context)

        if( content != "" ){
            val json = JSONObject(content)
            val eventsId = json.getJSONArray("events")
            val eventTypesId = json.getJSONArray("eventTypes")
            for (i in 0 until eventsId.length()) {
                val event: Event? = this.manager.findEventById( eventsId.get(i).toString().toInt() )
                event?.let {
                    this.addEvent(event)
                }
            }
            for (i in 0 until eventTypesId.length()) {
                val eventType: Category? = this.manager.findEventTypeById( eventTypesId.get(i).toString().toInt() )
                eventType?.let {
                    eventTypes.add(eventType)
                }
            }
        }
    }

    companion object {
        const val FILENAME = "fnfa_favorite"
    }
}