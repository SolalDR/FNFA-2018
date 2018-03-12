package com.example.solal.festivalnationaldufilmdanimation

import android.content.Intent
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
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

    protected fun manageNav(){
        val home = findViewById<ImageButton>(R.id.homeBtn)
        val info = findViewById<ImageButton>(R.id.infoBtn)
        val program = findViewById<ImageButton>(R.id.programBtn)
        val star = findViewById<ImageButton>(R.id.starBtn)

        home.setOnClickListener(View.OnClickListener {
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

    fun sendNotification(view: View) {

        //Get an instance of NotificationManager//
        val mBuilder = NotificationCompat.Builder(this)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("My notification")
                .setContentText("Hello World!")

        // Gets an instance of the NotificationManager service//
        val mNotificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        mNotificationManager.notify(1, mBuilder.build());

    }


}
