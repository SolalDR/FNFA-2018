package com.example.solal.festivalnationaldufilmdanimation

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.content.DialogInterface
import android.graphics.Typeface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.view.PagerAdapter
import android.support.v4.view.ViewPager
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewPropertyAnimator
import android.widget.TextView
import com.example.admin.myapplication.adapters.EventPagerAdapter
import com.example.admin.myapplication.adapters.ViewPagerAdapter2
import com.example.solal.festivalnationaldufilmdanimation.entity.Event
import org.w3c.dom.Text
import java.text.SimpleDateFormat
import java.util.*
import kotlin.collections.ArrayList


class ProgramFragment : Fragment (), DialogInterface.OnClickListener {

    lateinit var eventsBodyPager: ViewPager
    lateinit var eventsHeadPager: ViewPager

    lateinit var datesList: ArrayList<String>
    lateinit var eventsLists: ArrayList<ArrayList<Event>>

    lateinit var currentDate: Date

    var formater = SimpleDateFormat("yyyy-MM-dd", Locale.FRANCE)
    var displayFormater = SimpleDateFormat("E", Locale.FRANCE)
    var app: MyApplication? = null
    var fragmentView: View? = null
    var selectedDate: Int = 0



    override fun onClick(p0: DialogInterface?, p1: Int) {}

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        app = this.activity.application as MyApplication
        fragmentView = inflater!!.inflate(R.layout.tab_program, container, false)

        datesList = ArrayList()
        fragmentView?.apply {

            app?.apply {
                val list = this.manager.findAllDates()
                datesList.add("")
                for( date in list){ datesList.add(displayFormater.format(date)) }
                datesList.add("")
                eventsLists = this.manager.findAllDatesOrder()
            }

            eventsHeadPager = this.findViewById(R.id.eventsHeadPager)
            eventsBodyPager = this.findViewById(R.id.eventsBodyPager)

            eventsHeadPager.adapter = TextPagerAdapter()
            eventsHeadPager.offscreenPageLimit = 0
            eventsHeadPager.pageMargin = 0

            eventsBodyPager.adapter = EventPagerAdapter(eventsLists, this@ProgramFragment.activity.supportFragmentManager)
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
        }



        setDefaultDate()

        return fragmentView
    }

    inline fun ViewPropertyAnimator.onAnimationEnd(crossinline continuation: (Animator) -> Unit) {
        setListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator) {
                continuation(animation)
            }
        })
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

    fun  getSelectedDateFormat(): String{
        return formater.format(app!!.manager.dates[selectedDate])
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        displayEvents()
    }

    private fun displayEvents(){
        val myApp = this.activity.application as MyApplication
        val eventArrayByDay = myApp.manager.findEventsByDay(getSelectedDateFormat())
    }

    private fun displayEventsPlace(){
        val myApp = this.activity.application as MyApplication
        val eventArrayByDay = myApp.manager.findEventsByDay(getSelectedDateFormat())
    }


    private inner class TextPagerAdapter : ViewPagerAdapter2() {

        override fun getView(position: Int, pager: ViewPager): View {
            val parent = layoutInflater.inflate(R.layout.item_head_pager, null) as ViewGroup

            var dateView = parent.findViewById<TextView>(R.id.date)
            dateView.text = this@ProgramFragment.datesList[position].toString()

            return parent
        }

        override fun getCount(): Int {
            return this@ProgramFragment.datesList.size
        }
    }
}

