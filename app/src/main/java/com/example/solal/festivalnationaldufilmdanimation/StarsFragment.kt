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
import android.widget.TextView

/**
 * Created by sdussoutrevel on 13/02/2018.
 */


class StarsFragment : Fragment() , DialogInterface.OnClickListener {

    lateinit var recycler: RecyclerView
    var app: MyApplication? = null
    var fragmentView: View? = null
    var emptyMessage: View? = null

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
            val anim = AnimationUtils.loadAnimation(this.context, R.anim.abc_grow_fade_in_from_bottom)
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

    private fun displayEvents(){
        val myApp = this.activity.application as MyApplication
        val favoriteEvents = myApp.favoriteManager.findAllEvents()

        // Setup EventAdapter and on click listener
        recycler.adapter = EventAdapter(favoriteEvents, activity, { cell, isFav, event ->
            if(!isFav){
                val adapter = recycler.adapter as EventAdapter
                adapter.notifyItemRemoved(cell.adapterPosition)
                if(adapter.events.size == 0){
                    this.displayEmpty()
                }
            }
        })

        // Manage empty message
        val adapter = recycler.adapter as EventAdapter
        if(adapter.events.size > 0) {
            this.hideEmpty()
        }
    }
}
