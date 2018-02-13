package com.example.solal.festivalnationaldufilmdanimation

import com.example.solal.festivalnationaldufilmdanimation.helpers.StringHelper
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import java.text.SimpleDateFormat


import java.util.*


class EventsFragment : Fragment (), DialogInterface.OnClickListener {

    lateinit var recyclerV: RecyclerView
    lateinit var currentDate: Date
    lateinit var currentDateText: TextView
    lateinit var lastDateText: TextView
    lateinit var nextDateText: TextView

    var formater = SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE)
    var displayFormater = SimpleDateFormat("E", Locale.FRANCE)
    var app: MyApplication? = null
    var fragmentView: View? = null
    var selectedDate: Int = 0

    override fun onClick(p0: DialogInterface?, p1: Int) {}

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)

        app = this.activity.application as MyApplication

        setDefaultDate()

        fragmentView = inflater!!.inflate(R.layout.tab_event, container, false)

        recyclerV = fragmentView!!.findViewById(R.id.recyclerV)

        currentDateText = fragmentView!!.findViewById<TextView>(R.id.textCurrentDate)
        lastDateText = fragmentView!!.findViewById<TextView>(R.id.textLastDate)
        nextDateText = fragmentView!!.findViewById<TextView>(R.id.textNextDate)

        currentDateText.setOnClickListener(View.OnClickListener { })
        lastDateText.setOnClickListener(View.OnClickListener { previous() })
        nextDateText.setOnClickListener(View.OnClickListener { next() })

        updateDate()

        return fragmentView
    }

    fun previous(){
        if( selectedDate > 0 ) {
            selectedDate--
            updateDate()
        }
    }

    fun next(){
        if( selectedDate < app!!.manager.dates.size-1 ){
            selectedDate++
            updateDate()
        }
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

    fun updateDate(){
        Collections.sort(app!!.manager.dates);

        var selectedDateContent = StringHelper.ucfirst(displayFormater.format(app!!.manager.dates[selectedDate]))

        currentDateText.text = selectedDateContent
        if( selectedDate != 0 ){
            lastDateText.text = StringHelper.ucfirst(displayFormater.format(app!!.manager.dates[selectedDate - 1]))
            lastDateText.alpha = 1.toFloat()
        } else {
            lastDateText.alpha = 0.toFloat()
        }
        if( selectedDate != app!!.manager.dates.size - 1 ){
            nextDateText.text = StringHelper.ucfirst(displayFormater.format(app!!.manager.dates[selectedDate + 1]))
            nextDateText.alpha = 1.toFloat()
        } else {
            nextDateText.alpha = 0.toFloat()
        }

        displayEvents()
    }

    fun  getSelectedDateFormat(): String{
        return formater.format(app!!.manager.dates[selectedDate])
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recyclerV.layoutManager = LinearLayoutManager(this.context)
        displayEvents()
    }

    private fun displayEvents(){
        val myApp = this.activity.application as MyApplication
        val eventArrayByDay = myApp.getManager().findEventsByDay(getSelectedDateFormat())
        recyclerV.adapter = EventAdapter(eventArrayByDay, app!!, { cell, isFav, event ->
            //Toast.makeText(this.context, param, Toast.LENGTH_SHORT).show()
        })
    }

    private fun displayEventsPlace(){
        val myApp = this.activity.application as MyApplication
        val eventArrayByDay = myApp.getManager().findEventsByDay(getSelectedDateFormat())
        recyclerV.adapter = EventAdapter(eventArrayByDay, app!!, { cell, isFav, event ->
            //Toast.makeText(this.context, param, Toast.LENGTH_SHORT).show()
        })
    }
}
