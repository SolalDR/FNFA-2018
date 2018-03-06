package com.example.solal.festivalnationaldufilmdanimation

import android.content.DialogInterface
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import com.twitter.sdk.android.core.DefaultLogger
import com.twitter.sdk.android.core.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.TwitterConfig
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter
import com.twitter.sdk.android.tweetui.UserTimeline

/**
 * Created by christine on 06/03/2018.
 */



class NewMainActivity : AppCompatActivity() {
    lateinit var recycler: RecyclerView
    lateinit var recyclerView: ListView
    var app: MyApplication? = null
    var fragmentView: View? = null
    var emptyMessage: View? = null

//    fun onClick(p0: DialogInterface?, p1: Int) {}

    override fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
    }
//
//    fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
//        super.onCreate(savedInstanceState)
//        app = this.application as MyApplication
//        fragmentView = inflater!!.inflate(R.layout.tab_home, container, false)
//        recycler = fragmentView!!.findViewById(R.id.recyclerHome)
//        recyclerView = fragmentView!!.findViewById(R.id.recyclerHomeActu)
//
//        val key = this.resources.getString(R.string.twitter_consumer_key)
//        val secret = this.resources.getString(R.string.twitter_consumer_secret)
//        val authConfig = TwitterAuthConfig(key, secret);
//
//
//
//        val config = TwitterConfig.Builder(this)
//                .logger(DefaultLogger(Log.DEBUG))
//                .twitterAuthConfig(authConfig)
//                .debug(true)
//                .build()
//        Twitter.initialize(config)
//
//
//        val userTimeline = UserTimeline.Builder().screenName("fnfa_afca")
//                .maxItemsPerRequest(5)
//                .includeRetweets(false)
//                .build()
//
//        val adapterActu = TweetTimelineListAdapter.Builder(this)
//                .setTimeline(userTimeline)
//                .build()
//
//        recyclerView.adapter = adapterActu
//
//
////        return fragmentView
//    }
//
//     fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
//        super.onViewCreated(view, savedInstanceState)
//        recycler.layoutManager = LinearLayoutManager(this)
//        displayEvents()
//
//    }

    private fun displayEvents(){
        val myApp = this.application as MyApplication
        val favoriteEvents = myApp.manager.findNextEvents(3)

        // Setup EventAdapter and on click listener
        recycler.adapter = EventAdapter(favoriteEvents, this, { cell, isFav, event -> })

        // Manage empty message
        val adapter = recycler.adapter as EventAdapter

    }


}
