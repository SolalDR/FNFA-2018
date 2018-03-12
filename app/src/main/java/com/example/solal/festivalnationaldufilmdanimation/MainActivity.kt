package com.example.solal.festivalnationaldufilmdanimation

import android.content.Intent
import android.app.NotificationManager
import android.content.Context
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
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
//        val notification=notificationBuilder.buildild()
        val mBuilder = NotificationCompat.Builder(this)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher))
                .setSmallIcon(R.mipmap.ic_launcher)
                .setContentTitle("My notification")
                .setContentText("Hello World!")



        // Gets an instance of the NotificationManager service//

        val mNotificationManager =

                getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

        // When you issue multiple notifications about the same type of event,
        // it’s best practice for your app to try to update an existing notification
        // with this new information, rather than immediately creating a new notification.
        // If you want to update this notification at a later date, you need to assign it an ID.
        // You can then use this ID whenever you issue a subsequent notification.
        // If the previous notification is still visible, the system will update this existing notification,
        // rather than create a new one. In this example, the notification’s ID is 001//

//        NotificationManager.notify().mNotificationManager.notify(1, mBuilder.build())

//        NotificationManager.n


        mNotificationManager.notify(1, mBuilder.build());

    }


}
