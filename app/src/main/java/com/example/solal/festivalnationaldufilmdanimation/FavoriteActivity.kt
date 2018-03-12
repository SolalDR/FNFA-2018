package com.example.solal.festivalnationaldufilmdanimation

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.ImageButton
import com.example.solal.festivalnationaldufilmdanimation.adapter.ListEventAdapter
import com.example.solal.festivalnationaldufilmdanimation.entity.Event
import java.util.*


class FavoriteActivity : AppCompatActivity() {


    private var emptyMessage: View? = null
    lateinit var recycler: RecyclerView
    lateinit var adapter: ListEventAdapter
    var listSort: ArrayList<ArrayList<Event>>? = null
    var app: MyApplication? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_favorite)
        manageNav()

        app = application as MyApplication

        recycler = findViewById(R.id.recyclerStars)
        emptyMessage = findViewById(R.id.message_empty)

        recycler.layoutManager = LinearLayoutManager(this)

        listSort = app!!.favoriteManager.eventsSort
        displayEvents()


        listSort?.apply {
            adapter = ListEventAdapter(this, this@FavoriteActivity, { cell, list ->
                val adapter = recycler.adapter as ListEventAdapter
                adapter.notifyItemRemoved(cell.adapterPosition)
                this.remove(list)
                if (this.size == 0) {
                    this@FavoriteActivity.displayEmpty()
                }
            })
        }

    }

    private fun displayEmpty(){
        emptyMessage?.apply {
            this.alpha = 0.toFloat()
            val anim = AnimationUtils.loadAnimation(this.context, R.anim.abc_fade_in)
            anim.duration = 500
            this.startAnimation(anim)
            this.alpha = 1.toFloat()
        }
    }

    private fun hideEmpty(){
        emptyMessage?.apply {
            this.alpha = 0.toFloat()
        }
    }

    fun sortPerDay(events: ArrayList<Event>): ArrayList<ArrayList<Event>> {
        val list = ArrayList<ArrayList<Event>>()
        events.sort()

        // Each event
        var date: Date?
        var found: Boolean

        for(event in events) {
            date = event.getDateFormat()
            found = false

            // Each day list
            for(listDayTmp in list){
                // If event match with current day list
                if( listDayTmp[0].getDateFormat() == date ){
                    listDayTmp.add(event)
                    found = true
                }
            }
            // Else create new eventsList
            if(!found) {
                list.add(ArrayList())
                list[list.size - 1].add(event)
            }
        }

        return list
    }


    fun updateList(list: ArrayList<ArrayList<Event>>){
        listSort = list
        displayEvents()
    }

    fun displayEvents(){
        listSort?.apply {
            for(list in this){
                System.out.println("----"+list)
                for(item in list){
                    System.out.println("--"+item)
                }
            }

            System.out.println(recycler)
            System.out.println(recycler.adapter)
            System.out.println(this)

            recycler.swapAdapter(ListEventAdapter(this, this@FavoriteActivity, { cell, list ->
                val adapter = recycler.adapter as ListEventAdapter
                adapter.notifyItemRemoved(cell.adapterPosition)
                this.remove(list)
                if (this.size == 0) {
                    this@FavoriteActivity.displayEmpty()
                }
            }), true)

            val adapter = recycler.adapter as ListEventAdapter
            if(adapter.eventLists.size > 0) {
                this@FavoriteActivity.hideEmpty()
            }
        }
    }
    private fun manageNav(){
        var home = findViewById<ImageButton>(R.id.homeBtn)
        var info = findViewById<ImageButton>(R.id.infoBtn)
        var program = findViewById<ImageButton>(R.id.programBtn)
        var star = findViewById<ImageButton>(R.id.starBtn)

        home.setOnClickListener(View.OnClickListener{
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
}
