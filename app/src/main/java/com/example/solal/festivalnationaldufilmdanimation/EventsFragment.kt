package com.example.solal.festivalnationaldufilmdanimation


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.solal.festivalnationaldufilmdanimation.entity.Event
import android.support.v7.widget.GridLayoutManager
import android.widget.EditText

import kotlinx.android.synthetic.main.tab_event.view.*
import java.text.SimpleDateFormat
import java.util.*



class EventsFragment : Fragment() {

    lateinit var recyclerV: RecyclerView
    lateinit var currentDate: Date
    lateinit var selectedDate: Date
    lateinit var formater: SimpleDateFormat
    lateinit var app: MyApplication;

    // The date TextView
    var currentDateText: TextView? = null
    var lastDateText: TextView? = null
    var nextDateText: TextView? = null


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)

        formater = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        app = this.activity.application as MyApplication

        setDefaultDate()

        return inflater!!.inflate(R.layout.tab_event, container, false)
    }

    fun initClick(){

    }

    fun setDefaultDate(){
        currentDate = Calendar.getInstance().time
        selectedDate = app.manager.dates[0]
        for(dateItem in app.manager.dates){
            if( dateItem == currentDate){
                selectedDate = currentDate;
            }
        }
    }

    fun updateDate(){
        Collections.sort(app.manager.dates);

        var selectedDateContent = formater.format(selectedDate);
        var rank: Int? = null

        for(i in 0..app.manager.dates.size-1){
            if( app.manager.dates[i] == selectedDate){
                rank = i
            }
        }

        when(rank){
            0 -> lastDateText?.alpha = (0).toFloat()
            app.manager.dates.size -1 -> nextDateText?.alpha = (0).toFloat()
        }

        currentDateText?.text = selectedDateContent
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        currentDateText = view?.findViewById<TextView>(R.id.textCurrentDate)
        lastDateText = view?.findViewById<TextView>(R.id.textLastDate)
        nextDateText = view?.findViewById<TextView>(R.id.textNextDate)

        updateDate()

        view?.findViewById<RecyclerView>(R.id.recyclerV)?.apply {
            this@EventsFragment.recyclerV = this
            recyclerV.layoutManager = LinearLayoutManager(this.context)
            displayEvents()
        }
    }

    private fun displayEvents(){
        val myApp = this.activity.application as MyApplication
        val eventArrayByDay = myApp.getManager().findEventsByDay("2018-04-04")
        System.out.println("---------------"+eventArrayByDay.size)
        recyclerV.adapter = EventAdapter(eventArrayByDay,{ param ->
            Toast.makeText(this.context, param, Toast.LENGTH_SHORT).show()
        })
    }

    class EventAdapter(private val events: List<Event>, val eventCallback: (String) -> Unit) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

        override fun getItemCount(): Int = events.size //size similar to count, size specific from list
        //To change body of created functions use File | Settings | File Templates.

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
            //To change body of created functions use File | Settings | File Templates.
            val context = parent.context
            val layoutInflater = LayoutInflater.from(context)
            val view: View = layoutInflater.inflate(R.layout.item_program, parent, false)
            return EventViewHolder(view)
        }

        override fun onBindViewHolder(holder: EventViewHolder, position: Int) {

            val eventName = events[position].name
            val eventTime = events[position].date_start


            //To change body of created functions use File | Settings | File Templates.
            val nameView = holder.itemView.findViewById<TextView>(R.id.programTitle)
            val timeView = holder.itemView.findViewById<TextView>(R.id.programTime)

            nameView.text = eventName
            timeView.text = eventTime.toString()

            //holder.itemView.setOnClickListener {}
        }

        class EventViewHolder(private val view: View) : RecyclerView.ViewHolder(view)
    }
}
