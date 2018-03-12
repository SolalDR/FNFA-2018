package com.example.admin.myapplication.adapters

import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import com.example.solal.festivalnationaldufilmdanimation.EventsFragment
import com.example.solal.festivalnationaldufilmdanimation.entity.Event
import kotlin.collections.ArrayList


class EventPagerAdapter(eventsLists: ArrayList<ArrayList<Event>>, fragmentManager: FragmentManager) :
        FragmentStatePagerAdapter(fragmentManager) {

    val eventsLists: ArrayList<ArrayList<Event>> = eventsLists

    override fun getItem(position: Int): Fragment {
        val fragment = EventsFragment.newInstance(eventsLists[position])
        fragment.position = position
        return fragment
    }

    override fun getCount(): Int {
        return eventsLists.size
    }

    override fun getPageTitle(position: Int): CharSequence {
        return eventsLists[position][0].getDateFormat().toString()
    }
}