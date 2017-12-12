package com.example.solal.festivalnationaldufilmdanimation.model

import org.json.JSONObject
import java.util.*

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
        val type: Int? = null
        val background: String
        val description: String
        val places: List<Place>
        val events: List<Event>
    }
}
