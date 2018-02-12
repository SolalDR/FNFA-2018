package com.example.solal.festivalnationaldufilmdanimation

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageButton
import android.widget.TextView
import com.example.solal.festivalnationaldufilmdanimation.entity.Event
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Solal on 22/01/2018.
 * An Adapter for events list in recycler view
 */

class EventAdapter(

        private val events: List<Event>,
        private val app: MyApplication,
        val eventCallback: (String) -> Unit

) : RecyclerView.Adapter<EventAdapter.EventViewHolder>()

{

    override fun getItemCount(): Int = events.size //size similar to count, size specific from list


    /*
    * Instanciation of a new view
    */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        //To change body of created functions use File | Settings | File Templates.
        val context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val view: View = layoutInflater.inflate(R.layout.item_event, parent, false)
        return EventViewHolder(view)
    }


    /*
    * Recycling of a view
    */
    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {

        val event = events[position] // Get events
        val scene = app.manager.findSceneById(event.scene_id!!)!!

        val eventTime = events[position].date_start
        val timeFormat = SimpleDateFormat("HH'h'mm", Locale.FRENCH).format(eventTime)

        val nameView = holder.itemView.findViewById<TextView>(R.id.programTitle)
        val timeView = holder.itemView.findViewById<TextView>(R.id.programTime)
        val placeView = holder.itemView.findViewById<TextView>(R.id.programPlace)


        nameView.text = event.name
        timeView.text = timeFormat.toString()
        placeView.text = scene.name


        manageFav(holder, event)
    }


    /*
     * Init the event for update favorites
     */
    fun manageFav(cell: EventViewHolder, event: Event){
        val button = cell.itemView.findViewById<ImageButton>(R.id.imageButton)

        var currentColor = R.drawable.nofav
        if( app.favoriteManager.exist(event) ){
            currentColor = R.drawable.fav
        }

        // set default color favoris
        button.setImageResource(currentColor)

        button.setOnClickListener(View.OnClickListener {
            if(currentColor == R.drawable.nofav){
                currentColor = R.drawable.fav
                button.setImageResource(currentColor)
                app.favoriteManager.addEvent(event)
            }else{
                currentColor = R.drawable.nofav
                button.setImageResource(currentColor)
                app.favoriteManager.removeEvent(event)
            }
        })
    }

    class EventViewHolder(private val view: View) : RecyclerView.ViewHolder(view)
}