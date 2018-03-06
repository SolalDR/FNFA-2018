package com.example.solal.festivalnationaldufilmdanimation

/**
 * Created by christine on 06/03/2018.
 */
//package com.example.solal.festivalnationaldufilmdanimation

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.solal.festivalnationaldufilmdanimation.entity.Event

import kotlinx.android.synthetic.main.activity_program.*
import java.text.SimpleDateFormat
import java.util.*

class ProgramActivity : AppCompatActivity() {

    lateinit var recyclerV: RecyclerView
    lateinit var currentDate: Date
    lateinit var currentDateText: TextView
    lateinit var lastDateText: TextView
    lateinit var nextDateText: TextView

    var formater = SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE)
    var displayFormater = SimpleDateFormat("E", Locale.FRANCE)

    var app: MyApplication? = null
    var selectedDate: Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_program)


        this.recyclerV = findViewById(R.id.recyclerV)
        this.recyclerV.layoutManager = LinearLayoutManager(this)


        currentDateText = findViewById(R.id.textCurrentDate)
        lastDateText = findViewById(R.id.textLastDate)
        nextDateText = findViewById(R.id.textNextDate)
        displayEvents()
    }

    private fun displayEvents(){
        val myApp = this.application as MyApplication
        val eventArrayByDay = myApp.manager.findEventsByDay("2018-04-04")
        recyclerV.adapter = EventAdapter(eventArrayByDay,{ param ->
            Toast.makeText(this, param, Toast.LENGTH_SHORT)
                    .show()
        })
    }

    class EventAdapter(private val events: List<Event>, val eventCallback: (String) -> Unit) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

        override fun getItemCount(): Int = events.size //size similar to count, size specific from list
        //To change body of created functions use File | Settings | File Templates.

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
            //To change body of created functions use File | Settings | File Templates.
            val context = parent.context
            val layoutInflater = LayoutInflater.from(context)
            val view: View = layoutInflater.inflate(R.layout.item_event, parent, false)
            return EventViewHolder(view)
        }

        override fun onBindViewHolder(holder: EventViewHolder, position: Int) {

            val eventName = events[position].name

            //To change body of created functions use File | Settings | File Templates.
            val nameView = holder.itemView.findViewById<TextView>(R.id.programTitle)

            nameView.text = eventName

            //holder.itemView.setOnClickListener {}
        }

        class EventViewHolder(private val view: View) : RecyclerView.ViewHolder(view)
    }

    fun setDefaultDate(){
        currentDate = Calendar.getInstance().time
        selectedDate = 0

        for(i in 0..app!!.manager.dates.size-1){
            if( app!!.manager.dates[i] == currentDate ) {
                selectedDate = i
                break
            }
        }
    }


}