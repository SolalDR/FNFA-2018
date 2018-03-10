package com.example.solal.festivalnationaldufilmdanimation

import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import com.example.solal.festivalnationaldufilmdanimation.entity.Event
import kotlin.collections.ArrayList


/**
 * Created by sdussoutrevel on 13/02/2018.
 * List of favorites
 */
class StarsFragment : Fragment() , DialogInterface.OnClickListener {

    private var emptyMessage: View? = null
    private lateinit var recycler: RecyclerView
    private var fragmentView: View? = null

    var app: MyApplication? = null

    override fun onClick(p0: DialogInterface?, p1: Int) {}

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        app = this.activity.application as MyApplication
        fragmentView = inflater!!.inflate(R.layout.tab_stars, container, false)
        recycler = fragmentView!!.findViewById(R.id.recyclerStars)

        emptyMessage = fragmentView!!.findViewById(R.id.message_empty)
        return fragmentView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler.layoutManager = LinearLayoutManager(this.context)
        displayEvents()
    }

    private fun displayEmpty(){
        emptyMessage?.apply {
            this.alpha = 0.toFloat()
            val anim = AnimationUtils.loadAnimation(this.context, R.anim.abc_fade_in)
            anim.duration = 500
            this.startAnimation(anim)
            this.alpha = 1.toFloat()
        }
    }

    private fun hideEmpty(){
        emptyMessage?.apply {
            this.alpha = 0.toFloat()
        }
    }

    private fun sortPerDay(events: ArrayList<Event>): ArrayList<ArrayList<Event>> {
        val list = ArrayList<ArrayList<Event>>()
        events.sort()

        // Each event
        for(event in events) {
            val date = event.getDateFormat()
            var found = false

            // Each day list
            for(listDayTmp in list){
                // If event match with current day list
                if( listDayTmp[0].getDateFormat() == date ){
                    listDayTmp.add(event)
                    found = true
                }
            }
            // Else create new eventsList
            if(!found) {
                list.add(ArrayList())
                list[list.size - 1].add(event)
            }
        }

        return list
    }

    private fun displayEvents(){
        val myApp = this.activity.application as MyApplication
        val favoriteEvents = myApp.favoriteManager.findAllEvents()
        val listSort = this.sortPerDay(favoriteEvents)

        recycler.adapter = ListEventAdapter(listSort, activity, { cell, list ->
            val adapter = recycler.adapter as ListEventAdapter
            adapter.notifyItemRemoved(cell.adapterPosition)
            listSort.remove(list)
            if(listSort.size == 0){
                this.displayEmpty()
            }
        })

        val adapter = recycler.adapter as ListEventAdapter
        if(adapter.eventLists.size > 0) {
            this.hideEmpty()
        }
    }
}
