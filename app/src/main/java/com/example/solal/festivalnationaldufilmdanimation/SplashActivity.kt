package com.example.solal.festivalnationaldufilmdanimation

import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.content.Intent

/**
 * Created by chuang on 23/02/2018.
 * Simple SplashActivity to improve loading
 */

class SplashActivity:AppCompatActivity(){
    override
    fun onCreate(savedInstanceState: Bundle?, persistentState: PersistableBundle?) {
        super.onCreate(savedInstanceState, persistentState)
        startActivity(Intent(this@SplashActivity, HomeActivity::class.java))
        finish()
    }
}