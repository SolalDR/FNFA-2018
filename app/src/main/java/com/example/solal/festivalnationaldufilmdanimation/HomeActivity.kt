package com.example.solal.festivalnationaldufilmdanimation

import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Bundle
import android.support.v4.app.NotificationCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.ListView
import com.twitter.sdk.android.core.DefaultLogger
import com.twitter.sdk.android.core.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.TwitterConfig
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter
import com.twitter.sdk.android.tweetui.UserTimeline
import com.example.solal.festivalnationaldufilmdanimation.adapter.EventAdapter
import android.app.PendingIntent
import android.content.Intent
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.test.MoreAsserts.assertEquals
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.*
import java.util.concurrent.atomic.AtomicInteger
import kotlin.collections.ArrayList





class HomeActivity : MainActivity() {

    lateinit var recycler: RecyclerView
    lateinit var recyclerView: ListView
    var notification = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        manageNav()

        recycler = findViewById(R.id.recyclerHome)
        recyclerView = findViewById(R.id.recyclerHomeActu)

        recycler.layoutManager = LinearLayoutManager(this)
        loadTwitterApi()
        displayEvents()
        displayNotificationMoment()
    }



    private fun displayEvents(){
        val myApp = this.application as MyApplication
        val favoriteEvents = myApp.manager.findNextEvents(3)

        // Setup EventAdapter and on click listener
        recycler.adapter = EventAdapter(favoriteEvents, this, { _, _, _ -> })
    }


    private fun loadTwitterApi(){
        object : Thread() {
            override fun run() {
                val key = resources.getString(R.string.twitter_consumer_key)
                val secret = resources.getString(R.string.twitter_consumer_secret)
                val authConfig = TwitterAuthConfig(key, secret)

                val config = TwitterConfig.Builder(this@HomeActivity)
                        .logger(DefaultLogger(Log.DEBUG))
                        .twitterAuthConfig(authConfig)
                        .debug(true)
                        .build()

                Twitter.initialize(config)

                val userTimeline = UserTimeline.Builder().screenName("fnfa_afca")
                        .maxItemsPerRequest(5)
                        .includeRetweets(false)
                        .build()

                val adapterActu = TweetTimelineListAdapter.Builder(this@HomeActivity)
                        .setTimeline(userTimeline)
                        .build()


                this@HomeActivity.runOnUiThread(Runnable {
                    recyclerView.adapter = adapterActu

                })

            }
        }.start()
    }

    class NotificationID {
        private val c = AtomicInteger(0)
        val id: Int
         get() = c.incrementAndGet()

    }


    fun sendNotification( Str : ArrayList<String>, string : String, name : String) {

       val intent = Intent(this, InfoActivity::class.java)
       intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        val pendingIntent = PendingIntent.getActivity(this, 0, intent, 0)

        //Get an instance of NotificationManager//
        val mBuilder = NotificationCompat.Builder(this)
                .setLargeIcon(BitmapFactory.decodeResource(resources, R.mipmap.ic_launcher))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle(name)
                .setContentText(string)
                .setPriority(NotificationCompat.PRIORITY_HIGH)
                .setDefaults(NotificationCompat.DEFAULT_VIBRATE)
                // Set the intent that will fire when the user taps the notification
                .setContentIntent(pendingIntent)
                .setAutoCancel(true)
                .setVisibility(10)
                .setVibrate(longArrayOf(1000, 1000, 1000, 1000, 1000))
                .setLights(Color.BLUE, 3000, 3000)

        // Gets an instance of the NotificationManager service//
        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notification ++

        mNotificationManager.notify(notification, mBuilder.build())

    }

    fun displayNotificationMoment(){


        var favName = ArrayList<String>()

        val favorites = app.favoriteManager.findAllEvents()
        for (item in favorites) {
            print(item.name)
            favName.add(item.name)

            val name = item.name

            //curent Time
            val now = Calendar.getInstance().time
            val timeFormat = SimpleDateFormat("HH'h'mm", Locale.FRENCH).format(now)
            val anHour = 3.6e+6


            val start =SimpleDateFormat("HH'h'mm", Locale.FRENCH).format(item.date_start)
            start.toString()

            timeFormat.toString()
            if(item.date_start > now){
                var difference = item.date_start.time - now.time

                if(difference<anHour){
                    val timeFormat = SimpleDateFormat("DD 'd' HH'h'mm", Locale.FRENCH).format(difference).toString()
                    sendNotification(favName, timeFormat, name)
                }
            }
        }

//
        // 1) get favorites
        // 2) get current time
        // 3) compare, send notification

//        return
    }


}
