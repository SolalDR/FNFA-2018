package com.example.solal.festivalnationaldufilmdanimation

import android.content.Intent
import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.example.admin.myapplication.adapters.EventPagerAdapter
import com.example.admin.myapplication.adapters.ViewPagerAdapter
import com.example.solal.festivalnationaldufilmdanimation.entity.Event
import java.text.SimpleDateFormat
import java.util.*

class ProgramActivity: MainActivity() {

    lateinit var eventsBodyPager: ViewPager
    lateinit var eventsHeadPager: ViewPager
    lateinit var datesList: ArrayList<String>

    private lateinit var eventsLists: ArrayList<ArrayList<Event>>
    private lateinit var currentDate: Date
    private var displayFormater = SimpleDateFormat("E dd", Locale.FRANCE)
    private var selectedDate: Int = 0
    private var currentView: View? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_program)
        manageNav()



        datesList = ArrayList()

        val list = app.manager.findAllDates()
        datesList.add("")
        for( date in list){ datesList.add(displayFormater.format(date)) }
        datesList.add("")
        eventsLists = app.manager.findAllDatesOrder()

        initHeadPager()
        initBodyPager()
        setDefaultDate()
    }


    fun setActiveItem(state: Int){
        if ( state != selectedDate ) {
            currentView?.apply {
                this.animate().scaleX(1.toFloat()).scaleY(1.toFloat()).duration = 200
            }
            currentView = eventsHeadPager.getChildAt(state)

            currentView!!.animate().scaleX(1.2.toFloat()).scaleY(1.2.toFloat()).duration = 200

            selectedDate = state
        }
    }

    private fun initHeadPager(){
        eventsHeadPager = findViewById(R.id.eventsHeadPager)
        eventsHeadPager.adapter = TextPagerAdapter()
        eventsHeadPager.offscreenPageLimit = 0
        eventsHeadPager.pageMargin = 0

        eventsHeadPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            private var mScrollState = ViewPager.SCROLL_STATE_IDLE
            override fun onPageSelected(position: Int) {
                this@ProgramActivity.setActiveItem(position + 1)
            }
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                val correctPos = eventsHeadPager.scrollX * 3
                if (mScrollState == ViewPager.SCROLL_STATE_IDLE) {
                    return
                }
                eventsBodyPager.scrollTo(correctPos, eventsBodyPager.scrollY)
                if (mScrollState == ViewPager.SCROLL_STATE_IDLE) {
                    return
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                mScrollState = state
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    eventsBodyPager.setCurrentItem(eventsHeadPager.currentItem, false)
                }
            }
        })
    }

    private fun initBodyPager(){
        eventsBodyPager = findViewById(R.id.eventsBodyPager)
        eventsBodyPager.adapter = EventPagerAdapter(eventsLists, this.supportFragmentManager)
        eventsBodyPager.clipToPadding = false
        eventsBodyPager.pageMargin = 0

        eventsBodyPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            private var mScrollState = ViewPager.SCROLL_STATE_IDLE
            override fun onPageSelected(position: Int) {}
            override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
                val correctPos = eventsBodyPager.scrollX / 3
                if (mScrollState == ViewPager.SCROLL_STATE_IDLE) {
                    return
                }
                eventsHeadPager.scrollTo(correctPos, eventsHeadPager.scrollY)
                if (mScrollState == ViewPager.SCROLL_STATE_IDLE) {
                    return
                }
            }
            override fun onPageScrollStateChanged(state: Int) {
                mScrollState = state
                if (state == ViewPager.SCROLL_STATE_IDLE) {
                    eventsHeadPager.setCurrentItem(eventsBodyPager.currentItem, false)
                }
            }
        })
    }


    private fun setDefaultDate(){
        currentDate = Calendar.getInstance().time
        selectedDate = 0

        for(i in 0..app.manager.dates.size-1){
            if( app.manager.dates[i] == currentDate ) {
                selectedDate = i
                break
            }
        }
    }


    private inner class TextPagerAdapter : ViewPagerAdapter() {

        override fun getView(position: Int, pager: ViewPager): View {
            val parent = layoutInflater.inflate(R.layout.event_pager_adapter_header_item, null) as ViewGroup
            val dateView = parent.findViewById<TextView>(R.id.date)

            dateView.text = datesList[position]
            dateView.setOnClickListener(View.OnClickListener{
                eventsHeadPager.setCurrentItem(position - 1 )
                if( position > 0 && position < eventsHeadPager.adapter.count){
                    eventsBodyPager.setCurrentItem(position -1 )
                }

            })
            return parent
        }

        override fun getCount(): Int {
            return datesList.size
        }
    }
}
