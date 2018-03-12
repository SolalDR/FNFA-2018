package com.example.solal.festivalnationaldufilmdanimation

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.view.ViewPager
import android.widget.ImageView


class MainActivity : AppCompatActivity() {

    lateinit var view_pager: ViewPager
    lateinit var adapter: ViewPagerAdapter
    lateinit var tabs: TabLayout

    var Titles = arrayOf<CharSequence>("Accueil", "Program", "Infos", "Favoris")
    var Numboftabs = 4

    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)


        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        val titles = arrayOf(
                this.resources.getDrawable(R.drawable.ico_home),
                this.resources.getDrawable(R.drawable.ico_date),
                this.resources.getDrawable(R.drawable.ico_info),
                this.resources.getDrawable(R.drawable.ico_star))


        // Assigning ViewPager View and setting the adapter
        adapter = ViewPagerAdapter(supportFragmentManager, Titles, Numboftabs, titles)
        view_pager = findViewById(R.id.view_pager)
        view_pager.adapter = adapter
        view_pager.setOffscreenPageLimit(4);

        // Assiging the Sliding Tab Layout View
        tabs = findViewById(R.id.tabs)

        // Setting Custom Color for the Scroll bar indicator of the Tab View

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setupWithViewPager(view_pager)
        tabs.setSelectedTabIndicatorColor(this.resources.getColor(R.color.white))


        var t: TabLayout.Tab

        for (i in 0 until 4) {
            val view1 = layoutInflater.inflate(R.layout.item_tab, null)
            view1.findViewById<ImageView>(R.id.icon).setImageDrawable(titles[i])
            t = tabs.getTabAt(i)!!
            t.customView = view1
        }

    }
}
