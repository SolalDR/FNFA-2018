package com.example.solal.festivalnationaldufilmdanimation

import android.app.Application
import com.example.solal.festivalnationaldufilmdanimation.repository.DataRepository
import com.example.solal.festivalnationaldufilmdanimation.repository.FavoriteRepository

/**
 * Created by sdussoutrevel on 12/12/2017.
 * Override of Application.
 * Used to instantiate the dataMapper and load the ressource when the app start
 */

class MyApplication : Application() {

    lateinit var manager: DataRepository
    lateinit var favoriteManager: FavoriteRepository

    override fun onCreate() {

        this.manager = DataRepository(this.applicationContext)
        this.favoriteManager = FavoriteRepository(this.manager)

        super.onCreate()
        application = this

        this.manager.launchData()
    }

    companion object {

        private var application: MyApplication? = null
    }
}