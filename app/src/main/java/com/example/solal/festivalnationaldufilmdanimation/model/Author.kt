package com.example.solal.festivalnationaldufilmdanimation.model

import org.json.JSONObject

/**
 * Created by sdussoutrevel on 11/12/2017.
 */
class Author constructor(
        jsonObject: JSONObject
){
    init {
        val id: Int
        val name: String
        val background: String
        val description: String
        val events: List<Event>
    }
}
