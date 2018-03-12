package com.example.solal.festivalnationaldufilmdanimation

import android.os.Bundle
import android.support.v4.view.ViewPager
import android.view.View
import android.view.ViewGroup
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

        eventsHeadPager = findViewById(R.id.eventsHeadPager)
        eventsBodyPager = findViewById(R.id.eventsBodyPager)

        eventsHeadPager.adapter = TextPagerAdapter()
        eventsHeadPager.offscreenPageLimit = 0
        eventsHeadPager.pageMargin = 0

        eventsBodyPager.adapter = EventPagerAdapter(eventsLists, this.supportFragmentManager)
        eventsBodyPager.clipToPadding = false
        eventsBodyPager.pageMargin = 0

        eventsHeadPager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
            private var mScrollState = ViewPager.SCROLL_STATE_IDLE
            override fun onPageSelected(position: Int) {}
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



        setDefaultDate()
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
            val parent = layoutInflater.inflate(R.layout.item_head_pager, null) as ViewGroup
            val dateView = parent.findViewById<TextView>(R.id.date)

            dateView.text = datesList[position]

            return parent
        }

        override fun getCount(): Int {
            return datesList.size
        }
    }
}
