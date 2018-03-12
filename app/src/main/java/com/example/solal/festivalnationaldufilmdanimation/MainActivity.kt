package com.example.solal.festivalnationaldufilmdanimation

import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Point
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TabLayout
import android.support.v4.app.NotificationCompat
import android.support.v4.view.ViewPager
import android.view.View
import android.view.WindowManager
import android.widget.ImageButton
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

    fun sendNotification(view: View) {

        //Get an instance of NotificationManager//
//        val notification=notificationBuilder.buildild()
        val mBuilder = NotificationCompat.Builder(this)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("My notification")
                .setContentText("Hello World!")



        // Gets an instance of the NotificationManager service//

        val mNotificationManager =

                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // When you issue multiple notifications about the same type of event,
        // it’s best practice for your app to try to update an existing notification
        // with this new information, rather than immediately creating a new notification.
        // If you want to update this notification at a later date, you need to assign it an ID.
        // You can then use this ID whenever you issue a subsequent notification.
        // If the previous notification is still visible, the system will update this existing notification,
        // rather than create a new one. In this example, the notification’s ID is 001//

//        NotificationManager.notify().mNotificationManager.notify(1, mBuilder.build())

//        NotificationManager.n


        mNotificationManager.notify(1, mBuilder.build());

    }


}
