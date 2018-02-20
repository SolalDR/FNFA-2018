package com.example.solal.festivalnationaldufilmdanimation

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
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.example.solal.festivalnationaldufilmdanimation.helpers.StringHelper
import java.text.SimpleDateFormat
import java.util.*



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

        var dm = DisplayMetrics()
        windowManager.defaultDisplay.getMetrics(dm)

        var width = dm.widthPixels
        var height = dm.heightPixels

        window.setLayout((width * 0.85).toInt(), (width).toInt())

        manager = (application as MyApplication).manager
        event = manager.findEventById(intent.extras.getInt("event_id"))!!




        var titleView = findViewById<TextView>(R.id.pop_title)
        var subtitleView = findViewById<TextView>(R.id.pop_subtitle)
        var imageView = findViewById<ImageView>(R.id.pop_image)
        var dateView = findViewById<TextView>(R.id.pop_date)
        var placeView = findViewById<TextView>(R.id.pop_place)
        var durationView = findViewById<TextView>(R.id.pop_duration)
        var shareBtn = findViewById<Button>(R.id.pop_action_share)
        var webBtn = findViewById<Button>(R.id.pop_action_web)


        titleView.setText(event.name)

        val timeFormat = StringHelper.ucfirst(SimpleDateFormat("EEEE dd/MM - HH'h'mm", Locale.FRENCH).format(event.date_start))

        dateView.setText(timeFormat)


        var diff = event.date_end.time - event.date_start.time

        System.out.println(diff )
        durationView.setText((diff/60000).toInt().toString() + "min")



        event.type?.apply {  subtitleView.setText(this.name) }
        manager.findSceneById(event.scene_id!!)?.apply { placeView.setText(this.name) }


        DownloadImageTask(imageView).execute("http://www.festival-film-animation.fr/media/k2/items/cache/4246b121d2dc949b8f082c5f57840a3b_XL.jpg");

    }

    private inner class DownloadImageTask(internal var bmImage: ImageView) : AsyncTask<String, Void, Bitmap>() {

        override fun doInBackground(vararg urls: String): Bitmap? {
            val urldisplay = urls[0]
            var mIcon11: Bitmap? = null
            try {
                val `in` = java.net.URL(urldisplay).openStream()
                mIcon11 = BitmapFactory.decodeStream(`in`)
            } catch (e: Exception) {
                Log.e("Error", e.message)
                e.printStackTrace()
            }

            return mIcon11
        }

        override fun onPostExecute(result: Bitmap) {
            bmImage.setImageBitmap(result)
        }
    }
}