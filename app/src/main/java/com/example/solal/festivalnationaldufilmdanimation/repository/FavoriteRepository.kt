package com.example.solal.festivalnationaldufilmdanimation.repository

import com.example.solal.festivalnationaldufilmdanimation.entity.Event
import com.example.solal.festivalnationaldufilmdanimation.entity.Category
import org.json.JSONObject
import com.example.solal.festivalnationaldufilmdanimation.helpers.FileHelper


/**
 * Created by sdussoutrevel on 15/12/2017.
 * Allow to access to favorite which are stored in local file
 */
class FavoriteRepository constructor(manager_ref: DataRepository) {

    var manager: DataRepository
    var events = ArrayList<Event>()
    var eventTypes = ArrayList<Category>()

    init {
        manager = manager_ref
        getStoredFavorite()
    }


    fun exist(event: Event): Boolean {
        var int = 0;
        for( test in events) {
            if( test.id == event.id ){
                return true
            }
        }
        return false
    }

    fun addEvent(event: Event){
        if( !exist(event) ){
            this.events.add(event);
        }
        setStoredFavorites()
    }

    fun removeEvent(event: Event){
        this.events.remove(event)
        setStoredFavorites()
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
        var list = ArrayList<Int>()
        for(event in eventTypes){
            event.id?.apply {
                list.add(this)
            }
        }
        return list
    }

    /*
     * Open file and update its content from stringify favorites
     */
    private fun setStoredFavorites(){
        val FILENAME = "fnfa_favorite"

        val eventsId: ArrayList<Int> = this.getEventsId()
        val eventTypesId: ArrayList<Int> = this.getEventTypesId()
        val string: String = "{ \"events\": "+eventsId.toString()+", \"eventTypes\": "+eventTypesId.toString()+"}"
        FileHelper.writeFile(FILENAME, string, this.manager.context)

    }

    /*
     * Open fil and get the content
     */
    private fun getStoredFavorite() {
        val FILENAME = "fnfa_favorite"
        val content = FileHelper.readFile(FILENAME, this.manager.context)


        if( content != "" ){
            val json = JSONObject(content)
            val eventsId = json.getJSONArray("events");
            val eventTypesId = json.getJSONArray("eventTypes");
            for (i in 0 until eventsId.length()) {
                val event: Event? = this.manager.findEventById( eventsId.get(i).toString().toInt() )
                event?.let {
                    System.out.println("----------------------Loop IN"+event.id)
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
}