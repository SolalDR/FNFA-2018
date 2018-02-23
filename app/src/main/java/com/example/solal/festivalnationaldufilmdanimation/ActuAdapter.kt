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
 * Created by christine on 23/02/2018.
 */



class ActuAdapter(

        var events: MutableList<Event>,
        val app: MyApplication

) : RecyclerView.Adapter<ActuAdapter.EventViewHolder>()

{

    override fun getItemCount(): Int = events.size //size similar to count, size specific from list


    /*
    * Instanciation of a new view
    */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
        //To change body of created functions use File | Settings | File Templates.
        val context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val view: View = layoutInflater.inflate(R.layout.item_actu, parent, false)
        return EventViewHolder(view)
    }


    /*
    * Recycling of a view
    */
    override fun onBindViewHolder(holder: EventViewHolder, position: Int) {

        val event = events[position] // Get events


        val nameView = holder.itemView.findViewById<TextView>(R.id.textViewActu)

        nameView.text = event.name


    }


    class EventViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}