package com.example.solal.festivalnationaldufilmdanimation

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.widget.ImageButton
import android.widget.ListView
import com.twitter.sdk.android.core.DefaultLogger
import com.twitter.sdk.android.core.Twitter
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.core.TwitterConfig
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter
import com.twitter.sdk.android.tweetui.UserTimeline
import android.content.Intent
import com.example.solal.festivalnationaldufilmdanimation.adapter.EventAdapter


class HomeActivity : AppCompatActivity() {


    lateinit var recycler: RecyclerView
    lateinit var recyclerView: ListView
    var app: MyApplication? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        manageNav()

        app = this.application as MyApplication

        recycler = findViewById(R.id.recyclerHome)
        recyclerView = findViewById(R.id.recyclerHomeActu)

        recycler.layoutManager = LinearLayoutManager(this)
        displayEvents()

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


    private fun manageNav(){
        var home = findViewById<ImageButton>(R.id.homeBtn)
        var info = findViewById<ImageButton>(R.id.infoBtn)
        var program = findViewById<ImageButton>(R.id.programBtn)
        var star = findViewById<ImageButton>(R.id.starBtn)

        home.setOnClickListener(View.OnClickListener{
            startActivity(Intent(this, HomeActivity::class.java))
        })

        info.setOnClickListener(View.OnClickListener{
            startActivity(Intent(this, InfoActivity::class.java))
        })

        program.setOnClickListener(View.OnClickListener{
            startActivity(Intent(this, ProgramActivity::class.java))
        })

        star.setOnClickListener(View.OnClickListener{
            startActivity(Intent(this, FavoriteActivity::class.java))
        })
    }

    private fun displayEvents(){
        val myApp = this.application as MyApplication
        val favoriteEvents = myApp.manager.findNextEvents(3)

        // Setup EventAdapter and on click listener
        recycler.adapter = EventAdapter(favoriteEvents, this, { cell, isFav, event -> })
    }
}
