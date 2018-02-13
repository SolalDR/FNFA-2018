package com.example.solal.festivalnationaldufilmdanimation

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
import com.example.solal.festivalnationaldufilmdanimation.helpers.StringHelper
import kotlinx.android.synthetic.main.tab_stars.*
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by sdussoutrevel on 13/02/2018.
 */
class StarsFragment : Fragment() , DialogInterface.OnClickListener {

    lateinit var recycler: RecyclerView
    lateinit var currentDate: Date

    var formater = SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE)
    var app: MyApplication? = null
    var fragmentView: View? = null
    var selectedDate: Int = 0

    override fun onClick(p0: DialogInterface?, p1: Int) {}

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)

        app = this.activity.application as MyApplication
        fragmentView = inflater!!.inflate(R.layout.tab_stars, container, false)

        recycler = fragmentView!!.findViewById(R.id.recyclerStars)

        return fragmentView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        recycler.layoutManager = LinearLayoutManager(this.context)
        displayEvents()
    }

    private fun displayEvents(){
        val myApp = this.activity.application as MyApplication
        val eventArrayByDay = myApp.favoriteManager.findAllEvents()

        recycler.adapter = EventAdapter(eventArrayByDay, app!!, { cell, position, isFav ->
            if(!isFav){
                this.recyclerStars.removeView(cell.view)
            }
        })


    }
}
