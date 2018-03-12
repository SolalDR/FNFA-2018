
package com.example.solal.festivalnationaldufilmdanimation.adapter

import android.app.Activity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.solal.festivalnationaldufilmdanimation.MyApplication
import com.example.solal.festivalnationaldufilmdanimation.R
import com.example.solal.festivalnationaldufilmdanimation.entity.Event
import com.example.solal.festivalnationaldufilmdanimation.helpers.StringHelper
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
        private val listEventCallback: (EventListViewHolder, ArrayList<Event>) -> Unit

) : RecyclerView.Adapter<ListEventAdapter.EventListViewHolder>()  {

    private var formater = SimpleDateFormat("EEEE d MMMM", Locale.FRANCE)
    val app: MyApplication = activity.application as MyApplication
    lateinit var title: TextView


    /*
    * Instanciation of a new view
    */
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventListViewHolder {
        //To change body of created functions use File | Settings | File Templates.
        val context = parent.context
        val layoutInflater = LayoutInflater.from(context)
        val view: View = layoutInflater.inflate(R.layout.item_list_event, parent, false)


        //recycler = view.findViewById(R.id.recyclerEvent)


        return EventListViewHolder(view)
    }

    /*
    * Recycling of a view
    */
    override fun onBindViewHolder(holder: EventListViewHolder, position: Int) {
        val events = eventLists[position] // Get events

        title = holder.view.findViewById(R.id.titleListEvent)
        title.text = StringHelper.ucfirst(formater.format(events[0].getDateFormat()))
        val recycler = holder.view.findViewById<RecyclerView>(R.id.recyclerEvent)
        recycler.layoutManager = LinearLayoutManager(holder.view.context)

        // Setup EventAdapter and on click listener
        recycler.adapter = EventAdapter(events, activity, { cell, isFav, event ->
            if (!isFav) {
                val adapter = recycler.adapter as EventAdapter
                if (events.size == 1) {
                    this.listEventCallback(holder, events)
                }

                events.remove(event)
                adapter.notifyItemRemoved(cell.adapterPosition)
            }
        })

    }

    override fun getItemCount(): Int = eventLists.size //size similar to count, size specific from list

    class EventListViewHolder(val view: View) : RecyclerView.ViewHolder(view)
}