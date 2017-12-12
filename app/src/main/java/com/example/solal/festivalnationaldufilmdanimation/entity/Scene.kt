package com.example.solal.festivalnationaldufilmdanimation.entity

import org.json.JSONObject

/**
 * Created by sdussoutrevel on 11/12/2017.
 */
class Scene constructor(
        jsonObject: JSONObject
){
    init {
        val id: Int
        val name: String
        val limit: Int
        val type: Int?
        val background: String
        val description: String
        val places: List<Place>
        val events: List<Event>
    }
}
