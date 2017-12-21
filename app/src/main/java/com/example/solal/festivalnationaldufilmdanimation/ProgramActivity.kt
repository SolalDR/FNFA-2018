package com.example.solal.festivalnationaldufilmdanimation

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import com.example.solal.festivalnationaldufilmdanimation.entity.Event

import kotlinx.android.synthetic.main.activity_program.*

class ProgramActivity : AppCompatActivity() {

    lateinit var recyclerV: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_program)


        this.recyclerV = findViewById(R.id.recyclerV)
        this.recyclerV.layoutManager = LinearLayoutManager(this)
        displayEvents()
    }

    private fun displayEvents(){
        val myApp = this.application as MyApplication
        val eventArrayByDay = myApp.getManager().findEventsByDay("2018-04-04")
        recyclerV.adapter = EventAdapter(eventArrayByDay,{ param ->
            Toast.makeText(this, param, Toast.LENGTH_SHORT)
                    .show()
        })
    }

    class EventAdapter(private val events: List<Event>, val eventCallback: (String) -> Unit) : RecyclerView.Adapter<EventAdapter.EventViewHolder>() {

        override fun getItemCount(): Int = events.size //size similar to count, size specific from list
        //To change body of created functions use File | Settings | File Templates.

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): EventViewHolder {
            //To change body of created functions use File | Settings | File Templates.
            val context = parent.context
            val layoutInflater = LayoutInflater.from(context)
            val view: View = layoutInflater.inflate(R.layout.item_program, parent, false)
            return EventViewHolder(view)
        }

        override fun onBindViewHolder(holder: EventViewHolder, position: Int) {

            val eventName = events[position].name

            //To change body of created functions use File | Settings | File Templates.
            val nameView = holder.itemView.findViewById<TextView>(R.id.programTextView)

            nameView.text = eventName

            //holder.itemView.setOnClickListener {}
        }

        class EventViewHolder(private val view: View) : RecyclerView.ViewHolder(view)
    }
}
