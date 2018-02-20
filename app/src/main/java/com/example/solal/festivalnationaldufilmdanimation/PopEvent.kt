package com.example.solal.festivalnationaldufilmdanimation

import android.app.ActionBar
import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import com.example.solal.festivalnationaldufilmdanimation.entity.Event
import com.example.solal.festivalnationaldufilmdanimation.repository.DataRepository
import com.example.solal.festivalnationaldufilmdanimation.repository.FavoriteRepository
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.AsyncTask
import android.util.Log
import com.example.solal.festivalnationaldufilmdanimation.helpers.StringHelper
import com.squareup.picasso.Picasso
import java.text.SimpleDateFormat
import java.util.*
import android.view.Gravity
import android.provider.SyncStateContract.Helpers.update
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.widget.*


/**
 * Created by sdussoutrevel on 20/02/2018.
 */
class PopEvent : Activity() {


    lateinit var favoriteManager: FavoriteRepository
    lateinit var manager: DataRepository
    lateinit var event: Event

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.pop_event)


        // Compute metrics
        var dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        var width = dm.widthPixels
        var height = dm.heightPixels

        // Set popup size
        window.setLayout((width * 0.85).toInt(), (height*0.7).toInt())


        // Get current event
        manager = (application as MyApplication).manager
        event = manager.findEventById(intent.extras.getInt("event_id"))!!


        // Get all the views
        val titleView = findViewById<TextView>(R.id.pop_title)
        val subtitleView = findViewById<TextView>(R.id.pop_subtitle)
        val imageView = findViewById<ImageView>(R.id.pop_image)
        val dateView = findViewById<TextView>(R.id.pop_date)
        val placeView = findViewById<TextView>(R.id.pop_place)
        val durationView = findViewById<TextView>(R.id.pop_duration)
        var shareBtn = findViewById<Button>(R.id.pop_action_share)
        var webBtn = findViewById<Button>(R.id.pop_action_web)


        // Set event values on views

        val timeFormat = StringHelper.ucfirst(SimpleDateFormat("EEEE dd/MM - HH'h'mm", Locale.FRENCH).format(event.date_start))
        dateView.setText(timeFormat)

        var diff = event.date_end.time - event.date_start.time
        val durationStr = (diff/60000).toInt().toString() + "min"
        durationView.setText(durationStr)

        titleView.setText(event.name)
        event.type?.apply {  subtitleView.setText(this.name) }
        manager.findSceneById(event.scene_id)?.apply { placeView.setText(this.name) }

        Picasso.with(this.applicationContext).load("http://www.festival-film-animation.fr/media/k2/items/cache/4246b121d2dc949b8f082c5f57840a3b_XL.jpg").into(imageView);


        var parent = findViewById<RelativeLayout>(R.id.pop_parent)

        var mCurrentX = 0
        var mCurrentY = 0

        val otl = object : OnTouchListener {
            private var mDx: Float = 0.toFloat()
            private var mDy: Float = 0.toFloat()

            override fun onTouch(v: View, event: MotionEvent): Boolean {
                val action = event.action

                if (action == MotionEvent.ACTION_DOWN) {
                    mDx = mCurrentX - event.rawX
                    mDy = mCurrentY - event.rawY


                } else if (action == MotionEvent.ACTION_MOVE) {
                    mCurrentX = (event.rawX + mDx).toInt()
                    mCurrentY = (event.rawY + mDy).toInt()
                    if( mCurrentX > 0){
                        parent.alpha = maxOf(0.toFloat(), 1.toFloat() - mCurrentX.toFloat()/(width.toFloat()*0.3.toFloat()))
                    }

                } else if( action == MotionEvent.ACTION_UP){
                    if( mCurrentX > width*0.5 ){
                        this@PopEvent.finish()
                    } else {
                        parent.alpha = 1.toFloat()
                    }
                    mCurrentX = 0
                    mCurrentY = 0
                }
                return true
            }
        }
        parent.setOnTouchListener(otl)






    }
}