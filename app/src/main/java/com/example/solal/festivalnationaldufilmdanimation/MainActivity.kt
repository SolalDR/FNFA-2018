package com.example.solal.festivalnationaldufilmdanimation

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.view.ViewPager
import com.example.solal.festivalnationaldufilmdanimation.views.SlidingTabLayout


class MainActivity : AppCompatActivity() {

    // Declaring Your View and Variables

    lateinit var view_pager: ViewPager
    lateinit var adapter: ViewPagerAdapter
    lateinit var tabs: SlidingTabLayout

    var Titles = arrayOf<CharSequence>("Accueil", "Program", "Infos", "Favoris")
    var Numboftabs = 4

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        val titles = arrayOf(this.resources.getDrawable(R.drawable.ico_home), this.resources.getDrawable(R.drawable.ico_date), this.resources.getDrawable(R.drawable.ico_info), this.resources.getDrawable(R.drawable.ico_star))

        val app = this.application as MyApplication

        adapter = ViewPagerAdapter(supportFragmentManager, Titles, Numboftabs, titles)

        // Assigning ViewPager View and setting the adapter
        view_pager = findViewById(R.id.view_pager)
        view_pager.adapter = adapter

        // Assiging the Sliding Tab Layout View
        tabs = findViewById(R.id.tabs)


        tabs.setDistributeEvenly(true) // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer { resources.getColor(R.color.white) }

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(view_pager)

    }
}
