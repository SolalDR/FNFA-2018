package com.example.solal.festivalnationaldufilmdanimation

import com.example.solal.festivalnationaldufilmdanimation.entity.Event
import com.example.solal.festivalnationaldufilmdanimation.repository.DataRepository
import com.example.solal.festivalnationaldufilmdanimation.helpers.StringHelper
import com.squareup.picasso.Picasso

import android.app.Activity
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.MotionEvent
import android.view.View
import android.view.View.OnTouchListener
import android.widget.*

import java.text.SimpleDateFormat
import java.util.*


/**
 * Created by sdussoutrevel on 20/02/2018.
 * A simple popin appearing on event's click
 */

class PopEventActivity : Activity() {

    lateinit var manager: DataRepository
    lateinit var event: Event

    fun dpToPx(dp: Int): Int {
        val displayMetrics = this.getResources().getDisplayMetrics()
        return Math.round(dp * (displayMetrics.xdpi / DisplayMetrics.DENSITY_DEFAULT))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        overridePendingTransition(R.anim.appear_from_bottom, R.anim.abc_fade_in)

        setContentView(R.layout.pop_event)


        // Compute metrics
        val dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)
        val width = dm.widthPixels
        val height = dm.heightPixels

        // Set popup size

        window.setLayout((width * 0.85).toInt(), (dpToPx(400)).toInt())

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
        val contentView = findViewById<TextView>(R.id.popin_content)


        contentView
        // Set event values on views

        val timeFormat = StringHelper.ucfirst(SimpleDateFormat("EEEE dd/MM - HH'h'mm", Locale.FRENCH).format(event.date_start))
        dateView.text = timeFormat

        val diff = event.date_end.time - event.date_start.time
        val durationStr = (diff/60000).toInt().toString() + "min"
        durationView.text = durationStr

        titleView.text = event.name
        event.category?.apply {  subtitleView.text = this.name }
        manager.findPlaceById(event.place_id)?.apply { placeView.text = this.name  }

        if( event.description != "" ){
            contentView.text = event.description
            window.setLayout((width * 0.85).toInt(), (dpToPx(450)).toInt())
        }

        Picasso.with(this.applicationContext).load("http://www.festival-film-animation.fr/media/k2/items/cache/4246b121d2dc949b8f082c5f57840a3b_XL.jpg").into(imageView)


        val parent = findViewById<RelativeLayout>(R.id.pop_parent)

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
                        this@PopEventActivity.finish()
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