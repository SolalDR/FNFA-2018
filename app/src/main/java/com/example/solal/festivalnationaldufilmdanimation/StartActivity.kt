package com.example.solal.festivalnationaldufilmdanimation

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.annotation.IdRes
import android.support.v7.widget.Toolbar
import android.view.Menu
import android.view.View


class StartActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        var myToolbar = findViewById<Toolbar>(R.id.toolbar_top)
        setSupportActionBar(myToolbar)


        var support = getSupportActionBar()
        support?.apply {
            this.setDisplayShowTitleEnabled(false);
            this.setDisplayHomeAsUpEnabled(false);
        }



    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.toolbar, menu)
        return true
    }

}
