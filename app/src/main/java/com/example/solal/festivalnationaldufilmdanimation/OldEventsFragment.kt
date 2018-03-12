package com.example.solal.festivalnationaldufilmdanimation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import com.example.solal.festivalnationaldufilmdanimation.helpers.StringHelper
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewPropertyAnimator
import android.widget.TextView
import com.example.solal.festivalnationaldufilmdanimation.entity.Event
import java.text.SimpleDateFormat
import java.util.*
import kotlinx.android.synthetic.main.tab_event.*
import kotlin.collections.ArrayList


class OldEventsFragment : Fragment (), DialogInterface.OnClickListener {

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
    var events: ArrayList<Event>? = null
    var position: Int? = null

    val headerAnimationDuration = 200.toLong()

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

    inline fun ViewPropertyAnimator.onAnimationEnd(crossinline continuation: (Animator) -> Unit) {
        setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                continuation(animation)
            }
        })
    }

    fun displayAll(){
        textCurrentDate.animate().alpha(1f).setDuration(headerAnimationDuration)
        nextDateText.animate().alpha(1f).setDuration(headerAnimationDuration)
        lastDateText.animate().alpha(1f).setDuration(headerAnimationDuration)
    }


    fun previous(){
        if( selectedDate > 0 ) {


            /*selectedDate--
            updateDate()*/


            var updated = false
            lastDateText.animate().alpha(0f).setDuration(headerAnimationDuration)
            nextDateText.animate().alpha(0f).setDuration(headerAnimationDuration)
            textCurrentDate.animate().alpha(0f).setDuration(headerAnimationDuration).onAnimationEnd {
                if( !updated ){
                    selectedDate--
                    updateDate()
                    displayAll()
                    updated = true;
                }
            }
        }
    }


    fun next(){
        if( selectedDate < app!!.manager.dates.size-1 ){

            /*selectedDate++
            updateDate()*/

            var updated = false
            lastDateText.animate().alpha(0f)
            nextDateText.animate().alpha(0f)
            textCurrentDate.animate().alpha(0f).onAnimationEnd {
                if( !updated ){
                    selectedDate++
                    updateDate()
                    displayAll()
                    updated = true;
                }
            }
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
            lastDateText.visibility = View.VISIBLE
        } else {
            lastDateText.visibility = View.INVISIBLE
        }
        if( selectedDate != app!!.manager.dates.size - 1 ){
            nextDateText.text = StringHelper.ucfirst(displayFormater.format(app!!.manager.dates[selectedDate + 1]))
            nextDateText.visibility = View.VISIBLE
        } else {
            nextDateText.visibility = View.INVISIBLE
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
        val eventArrayByDay = myApp.manager.findEventsByDay(getSelectedDateFormat())
        recyclerV.adapter = EventAdapter(
                eventArrayByDay,
                activity,
                { cell, isFav, event -> })
    }

    private fun displayEventsPlace(){
        val myApp = this.activity.application as MyApplication
        val eventArrayByDay = myApp.manager.findEventsByDay(getSelectedDateFormat())
        recyclerV.adapter = EventAdapter(eventArrayByDay, activity, { cell, isFav, event -> })
    }

    companion object {
        // Method for creating new instances of the fragment
        fun newInstance(list: ArrayList<Event>): OldEventsFragment {
            val fragment = OldEventsFragment()
            fragment.events = list
            return fragment
        }
    }
}
