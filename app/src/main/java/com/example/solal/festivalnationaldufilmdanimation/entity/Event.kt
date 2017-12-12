package com.example.solal.festivalnationaldufilmdanimation.entity

import org.json.JSONObject
import java.util.Date

/**
 * Created by sdussoutrevel on 11/12/2017.
 */
class Event constructor(
        jsonObject: JSONObject
){
    init {
        val id: Int
        val name: String
        val description_short: String
        val description: String
        val date_start: Date
        val date_end: Date
        val duration: Int //Seconds
        val background: String
        val type: EventType
        val age_min: Int  // Ex: 6 => 6 years old or more
        val author: Author
        val scene: Scene
    }
}