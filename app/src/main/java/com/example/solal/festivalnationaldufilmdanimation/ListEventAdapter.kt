
package com.example.solal.festivalnationaldufilmdanimation

import android.app.Activity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.solal.festivalnationaldufilmdanimation.entity.Event
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


/**
 * Created by Solal on 10/03/2018.
 * List the events list for each day
 */
class ListEventAdapter (

        var eventLists: ArrayList<ArrayList<Event>>,
        var activity: Activity,
        val listEventCallback: (EventListViewHolder, ArrayList<Event>) -> Unit

) : RecyclerView.Adapter<ListEventAdapter.EventListViewHolder>()  {

    val app: MyApplication = activity.application as MyApplication
    //lateinit var recycler: RecyclerView
    lateinit var title: TextView
    var formater = SimpleDateFormat("E", Locale.FRANCE)

    override fun getItemCount(): Int = eventLists.size //size similar to count, size specific from list


    /*
    * Instanciation of a new view
    */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventListViewHolder {
        //To change body of created functions use File | Settings | File Templates.
        val context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val view: View = layoutInflater.inflate(R.layout.item_list_event, parent, false)

        title = view.findViewById(R.id.titleListEvent)
        //recycler = view.findViewById(R.id.recyclerEvent)


        return EventListViewHolder(view)
    }

    /*
    * Recycling of a view
    */
    override fun onBindViewHolder(holder: EventListViewHolder, position: Int) {
        var events = eventLists[position] // Get events

        title.text = formater.format(events[0].getDateFormat())
        var recycler = holder.view.findViewById<RecyclerView>(R.id.recyclerEvent)
        recycler.layoutManager = LinearLayoutManager(holder.view.context)

        // Setup EventAdapter and on click listener
        recycler.adapter = EventAdapter(events, activity, { cell, isFav, event ->
            if(!isFav){
                val adapter = recycler.adapter as EventAdapter
                if( events.size == 1 ){
                    this.listEventCallback(holder, events)
                }
                System.out.println(events)
                System.out.println(cell.adapterPosition)
                System.out.println("---------"+event.name)

                events.remove(event)
                adapter.notifyItemRemoved(cell.adapterPosition)
            }
        })

    }


    class EventListViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}