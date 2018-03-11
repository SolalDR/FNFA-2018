package com.example.solal.festivalnationaldufilmdanimation


import android.util.Log

import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView

import android.content.DialogInterface
import android.os.Bundle

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView

import com.twitter.sdk.android.core.Twitter
import com.twitter.sdk.android.core.TwitterConfig
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter
import com.twitter.sdk.android.tweetui.UserTimeline
import com.twitter.sdk.android.core.DefaultLogger


/**
 * Created by sdussoutrevel on 14/02/2018.
 */

class HomeFragment : Fragment(), DialogInterface.OnClickListener  {
    lateinit var recycler: RecyclerView
    lateinit var recyclerView: ListView
    var app: MyApplication? = null
    var fragmentView: View? = null

    override fun onClick(p0: DialogInterface?, p1: Int) {}

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        app = this.activity.application as MyApplication
        fragmentView = inflater!!.inflate(R.layout.tab_home, container, false)
        recycler = fragmentView!!.findViewById(R.id.recyclerHome)
        recyclerView = fragmentView!!.findViewById(R.id.recyclerHomeActu)

        object : Thread() {
            override fun run() {
                val key = this@HomeFragment.activity.resources.getString(R.string.twitter_consumer_key)
                val secret = this@HomeFragment.activity.resources.getString(R.string.twitter_consumer_secret)
                val authConfig = TwitterAuthConfig(key, secret)

                val config = TwitterConfig.Builder(this@HomeFragment.context)
                        .logger(DefaultLogger(Log.DEBUG))
                        .twitterAuthConfig(authConfig)
                        .debug(true)
                        .build()
                Twitter.initialize(config)


                val userTimeline = UserTimeline.Builder().screenName("fnfa_afca")
                        .maxItemsPerRequest(5)
                        .includeRetweets(false)
                        .build()

                val adapterActu = TweetTimelineListAdapter.Builder(this@HomeFragment.context)
                        .setTimeline(userTimeline)
                        .build()


                this@HomeFragment.activity.runOnUiThread(Runnable {
                    recyclerView.adapter = adapterActu
                })

            }
        }.start()

        return fragmentView
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        recycler.layoutManager = LinearLayoutManager(this.context)
        displayEvents()

    }

    private fun displayEvents(){
        val myApp = this.activity.application as MyApplication
        val favoriteEvents = myApp.manager.findNextEvents(3)

        // Setup EventAdapter and on click listener
        recycler.adapter = EventAdapter(favoriteEvents, activity, { cell, isFav, event -> })
    }


}
