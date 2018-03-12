package com.example.solal.festivalnationaldufilmdanimation

import android.app.ActivityOptions
import android.content.Intent
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.os.Build
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.support.v4.app.NotificationCompat
import android.view.View
import android.widget.ImageButton


abstract class MainActivity : AppCompatActivity() {

    lateinit var app: MyApplication

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = application as MyApplication
    }


    private fun getOption(): Bundle? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
            overridePendingTransition(0,0)
        }
        return null
    }

    protected fun manageNav(){
        val home = findViewById<ImageButton>(R.id.homeBtn)
        val info = findViewById<ImageButton>(R.id.infoBtn)
        val program = findViewById<ImageButton>(R.id.programBtn)
        val star = findViewById<ImageButton>(R.id.starBtn)

        home.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, HomeActivity::class.java), getOption())
        })

        program.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, ProgramActivity::class.java), getOption())
        })

        info.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, InfoActivity::class.java), getOption())
        })

        star.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, FavoriteActivity::class.java), getOption())
        })
    }




}
