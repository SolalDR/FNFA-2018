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
import android.widget.ImageView
import android.widget.TextView
import android.support.v4.app.ActivityOptionsCompat
import android.widget.RelativeLayout


abstract class MainActivity : AppCompatActivity() {

    lateinit var app: MyApplication
    lateinit var home: ImageButton
    lateinit var info: ImageButton
    lateinit var program: ImageButton
    lateinit var star: ImageButton
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        app = application as MyApplication
    }


    private fun getOption(): Bundle? {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            return ActivityOptions.makeSceneTransitionAnimation(this).toBundle()
        }
        return null
    }

    private fun manageCurrentNavItem(){
        System.out.println("---------------"+this.javaClass);
        System.out.println("-------------"+HomeActivity::class.java );

        when (this.javaClass.toString()) {
            HomeActivity::class.java.toString() -> home.animate().scaleX(1.3.toFloat()).scaleY(1.3.toFloat()).alpha(1.toFloat()).setDuration(500).start()
            ProgramActivity::class.java.toString() -> program.animate().scaleX(1.3.toFloat()).scaleY(1.3.toFloat()).alpha(1.toFloat()).setDuration(500).start()
            InfoActivity::class.java.toString() -> info.animate().scaleX(1.3.toFloat()).scaleY(1.3.toFloat()).setDuration(500).alpha(1.toFloat()).start()
            FavoriteActivity::class.java.toString() -> star.animate().scaleX(1.3.toFloat()).scaleY(1.3.toFloat()).setDuration(500).alpha(1.toFloat()).start()
        }
    }

    protected fun manageNav(){
        home = findViewById<ImageButton>(R.id.homeBtn)
        info = findViewById<ImageButton>(R.id.infoBtn)
        program = findViewById<ImageButton>(R.id.programBtn)
        star = findViewById<ImageButton>(R.id.starBtn)

        home.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, HomeActivity::class.java))
            overridePendingTransition(R.anim.abc_fade_in, R.anim.no_anim)

        })

        program.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, ProgramActivity::class.java))
            overridePendingTransition(R.anim.abc_fade_in, R.anim.no_anim)

        })

        info.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, InfoActivity::class.java))
            overridePendingTransition(R.anim.abc_fade_in, R.anim.no_anim)

        })

        star.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, FavoriteActivity::class.java))
            overridePendingTransition(R.anim.abc_fade_in, R.anim.no_anim)
        })

        manageCurrentNavItem()
    }




}
