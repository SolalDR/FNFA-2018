package com.example.solal.festivalnationaldufilmdanimation

import android.content.Intent
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.Button
import com.example.solal.festivalnationaldufilmdanimation.adapter.ListEventAdapter
import com.example.solal.festivalnationaldufilmdanimation.entity.Event
import java.util.*


class FavoriteActivity : MainActivity() {

    lateinit var recycler: RecyclerView
    lateinit var adapter: ListEventAdapter
    lateinit var listSort: ArrayList<ArrayList<Event>>
    lateinit var emptyMessage: View


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        this.manageNav()

        recycler = findViewById(R.id.recyclerStars)
        emptyMessage = findViewById(R.id.message_empty)

        recycler.layoutManager = LinearLayoutManager(this)

        listSort = app.favoriteManager.eventsSort

        adapter = ListEventAdapter(listSort, this, { cell, list ->
            val adapter = recycler.adapter as ListEventAdapter
            adapter.notifyItemRemoved(cell.adapterPosition)
            listSort.remove(list)
            if (listSort.size == 0) {
                this.displayEmpty()
            }
        })

        recycler.adapter = adapter
        if(adapter.eventLists.size > 0) {
            this@FavoriteActivity.hideEmpty()
        }

        val buttonBack = findViewById<Button>(R.id.buttonBack)
        buttonBack.setOnClickListener(View.OnClickListener {
            startActivity(Intent(this, ProgramActivity::class.java))
            overridePendingTransition(R.anim.abc_fade_in, R.anim.no_anim)
        })
    }

    private fun displayEmpty(){

        emptyMessage.alpha = 0.toFloat()
        val anim = AnimationUtils.loadAnimation(this, R.anim.abc_fade_in)
        anim.duration = 500
        emptyMessage.startAnimation(anim)
        emptyMessage.alpha = 1.toFloat()

    }

    private fun hideEmpty(){

        emptyMessage.alpha = 0.toFloat()

    }
}
