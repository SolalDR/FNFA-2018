package com.example.solal.festivalnationaldufilmdanimation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewPropertyAnimator
import com.example.solal.festivalnationaldufilmdanimation.entity.Event
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class EventsFragment : Fragment (), DialogInterface.OnClickListener {

    lateinit var recyclerV: RecyclerView

    var app: MyApplication? = null
    var fragmentView: View? = null
    var events: ArrayList<Event>? = null
    var position: Int? = null


    override fun onClick(p0: DialogInterface?, p1: Int) {}

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        app = this.activity.application as MyApplication
        fragmentView = inflater!!.inflate(R.layout.tab_events, container, false)


        return fragmentView
    }

    inline fun ViewPropertyAnimator.onAnimationEnd(crossinline continuation: (Animator) -> Unit) {
        setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                continuation(animation)
            }
        })
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        events?.apply {
            recyclerV = fragmentView!!.findViewById(R.id.eventsRecycler)
            recyclerV.layoutManager = LinearLayoutManager(this@EventsFragment.context)
            recyclerV.adapter = EventAdapter(this,  activity, { c, i, e -> })
        }
    }

    fun setEventsList(list: ArrayList<Event>){
        events = list
    }

    companion object {
        // Method for creating new instances of the fragment
        fun newInstance(list: ArrayList<Event>): EventsFragment {
            val fragment = EventsFragment()
            fragment.setEventsList(list)
            return fragment
        }
    }
}
