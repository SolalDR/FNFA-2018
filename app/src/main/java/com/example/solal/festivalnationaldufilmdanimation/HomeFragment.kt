package com.example.solal.festivalnationaldufilmdanimation

import android.support.v4.app.Fragment
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils

/**
 * Created by sdussoutrevel on 14/02/2018.
 */

class HomeFragment : Fragment(), DialogInterface.OnClickListener  {
    lateinit var recycler: RecyclerView
    var app: MyApplication? = null
    var fragmentView: View? = null
    var emptyMessage: View? = null

    override fun onClick(p0: DialogInterface?, p1: Int) {}

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        app = this.activity.application as MyApplication
        fragmentView = inflater!!.inflate(R.layout.tab_home, container, false)
        recycler = fragmentView!!.findViewById(R.id.recyclerHome)

        return fragmentView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler.layoutManager = LinearLayoutManager(this.context)
        displayEvents()
    }

    private fun displayEvents(){
        val myApp = this.activity.application as MyApplication
        val favoriteEvents = myApp.manager.findNextEvents(3)

        // Setup EventAdapter and on click listener
        recycler.adapter = EventAdapter(favoriteEvents, app!!, { cell, isFav, event -> })

        // Manage empty message
        val adapter = recycler.adapter as EventAdapter
    }
}