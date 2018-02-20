package com.example.solal.festivalnationaldufilmdanimation

import android.support.v4.app.Fragment
import android.content.DialogInterface
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.twitter.sdk.android.core.Twitter
import com.twitter.sdk.android.core.TwitterConfig
import com.twitter.sdk.android.core.TwitterAuthConfig
import com.twitter.sdk.android.tweetui.TwitterListTimeline
import org.json.JSONArray
import org.json.JSONObject
import android.content.ClipData.newIntent
import com.twitter.sdk.android.core.TwitterAuthException
import com.twitter.sdk.android.core.TwitterException
import com.twitter.sdk.android.core.models.Tweet
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter
import com.twitter.sdk.android.tweetui.UserTimeline


/**
 * Created by sdussoutrevel on 14/02/2018.
 */

class HomeFragment : Fragment(), DialogInterface.OnClickListener  {
    lateinit var recycler: RecyclerView
    var app: MyApplication? = null
    var fragmentView: View? = null
    var emptyMessage: View? = null

    override fun onClick(p0: DialogInterface?, p1: Int) {}

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        super.onCreate(savedInstanceState)
        app = this.activity.application as MyApplication
        fragmentView = inflater!!.inflate(R.layout.tab_home, container, false)
        recycler = fragmentView!!.findViewById(R.id.recyclerHome)

        val authConfig = TwitterAuthConfig(
                getString(R.string.twitter_consumer_key),
                getString(R.string.twitter_consumer_secret))

        val builder = TwitterConfig.Builder(this.context)
        builder.twitterAuthConfig(authConfig)
        Twitter.initialize(builder.build())
        TwitterListTimeline.Builder()

        val userTimeline = UserTimeline.Builder().screenName("nasa").build()


        val timeline = TwitterListTimeline.Builder()
                .slugWithOwnerScreenName("twitter-bots", "christineyini")
                .build()

        val adapterTweetsList = TweetTimelineListAdapter.Builder(activity)
               // .setTimelineFilter(getBasicTimelineFilter())
                .setTimeline(timeline)
                .setViewStyle(R.style.tw__TweetLightWithActionsStyle)
               // .setOnActionCallback(actionCallback)
                .build()


       //try {

            // TWITTER ACCESS TOKEN
            // String twit_access_token = twitPrefs.getString(PREF_KEY_OAUTH_TOKEN, null);

            // TWITTER ACCESS TOKEN SECRET
            //String twit_access_token_secret = twitPrefs.getString(PREF_KEY_OAUTH_SECRET, null);


            // builder.setOAuthAccessToken(twit_access_token);
            // builder.setOAuthAccessTokenSecret(twit_access_token_secret);

            //builder.setJSONStoreEnabled(true);
            //builder.setIncludeEntitiesEnabled(true);
            //builder.setIncludeMyRetweetEnabled(true);
           // builder.setIncludeRTsEnabled(true);

            //AccessToken accessToken = new AccessToken(twit_access_token, twit_access_token_secret);
           // Twitter twitter = new TwitterFactory(builder.build()).getInstance(accessToken);

           // Paging paging = new Paging(200); // MAX 200 IN ONE CALL. SET YOUR OWN NUMBER <= 200
            //statuses = twitter.getHomeTimeline(paging);

           // try {
               // String strInitialDataSet = DataObjectFactory . getRawJSON (statuses);
                /*JSONArray JATweets = new JSONArray(strInitialDataSet);

                for (int i = 0; i < JATweets.length(); i++) {
                    JSONObject JOTweets = JATweets . getJSONObject (i);
                    Log.e("TWEETS", JOTweets.toString());
                }*/
            /*}
            catch (Exception e) {
                // TODO: handle exception
            }
        } catch (Exception e) {
            // TODO: handle exception
        }*/


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

        // Manage empty message
        val adapter = recycler.adapter as EventAdapter
    }
}