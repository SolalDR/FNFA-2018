package com.example.solal.festivalnationaldufilmdanimation

import android.app.Activity
import android.content.Intent
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import android.widget.TextView
import com.example.solal.festivalnationaldufilmdanimation.entity.Event
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Solal on 22/01/2018.
 * An Adapter for events list in recycler view
 * @param events: The events's list
 * @param activity: Current activity for intent popup
 * @param favEventCallback: Listener for "favoris" button
 */

class EventAdapter(

        var events: MutableList<Event>,
        var activity: Activity,
        val favEventCallback: (EventViewHolder, Boolean, Event) -> Unit

) : RecyclerView.Adapter<EventAdapter.EventViewHolder>()
{

    val app: MyApplication = activity.application as MyApplication

    private var lastPosition = -1
    var animation = R.anim.abc_grow_fade_in_from_bottom

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
        val scene = app.manager.findPlaceById(event.place_id!!)!!

        val eventTime = events[position].date_start
        val timeFormat = SimpleDateFormat("HH'h'mm", Locale.FRENCH).format(eventTime)

        val nameView = holder.itemView.findViewById<TextView>(R.id.programTitle)
        val timeView = holder.itemView.findViewById<TextView>(R.id.programTime)
        val placeView = holder.itemView.findViewById<TextView>(R.id.programPlace)

        nameView.text = event.name
        timeView.text = timeFormat.toString()
        placeView.text = scene.name




      /*  if( position >= this.lastShowPosition ){
            this.lastShowPosition = position;
            holder.view.animation = AnimationUtils.loadAnimation(this.activity, R.anim.abc_fade_in)
            holder.view.animation.duration = 1000
            holder.itemView.animation.start();
        }*/


        System.out.println("--------------"+holder.view)

        holder.view.setOnClickListener({
            var intent = Intent(activity, PopEvent::class.java)
            intent.putExtra("event_id", event.id)
            activity.startActivity(intent)
            activity.overridePendingTransition(R.anim.slide_from_left, R.anim.slide_to_left);

        })

        //this.setAnimation(holder.itemView, position)

        manageFav(holder, event)
    }



    /**
     * Here is the key method to apply the animation
     */
    private fun setAnimation(viewToAnimate: View, position: Int)
    {

        if (position > lastPosition)
        {
            // If the bound view wasn't previously displayed on screen, it's animated
            var animation = AnimationUtils.loadAnimation(this.activity, animation);
            animation.duration = 600
            viewToAnimate.startAnimation(animation);
            lastPosition = position;
        }
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
                this.favEventCallback(cell, true, event)

            }else{
                currentColor = R.drawable.nofav
                button.setImageResource(currentColor)
                app.favoriteManager.removeEvent(event)
                favEventCallback(cell, false, event)
            }
        })

    }


    class EventViewHolder(val view: View) : RecyclerView.ViewHolder(view) {

    }
}