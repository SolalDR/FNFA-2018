package com.example.solal.festivalnationaldufilmdanimation

import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.widget.ListView
import com.twitter.sdk.android.core.DefaultLogger
import com.twitter.sdk.android.core.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.TwitterConfig
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter
import com.twitter.sdk.android.tweetui.UserTimeline
import com.example.solal.festivalnationaldufilmdanimation.adapter.EventAdapter


class HomeActivity : MainActivity() {

    lateinit var recycler: RecyclerView
    lateinit var recyclerView: ListView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        manageNav()

        recycler = findViewById(R.id.recyclerHome)
        recyclerView = findViewById(R.id.recyclerHomeActu)

        recycler.layoutManager = LinearLayoutManager(this)
        loadTwitterApi()
        displayEvents()
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
}
