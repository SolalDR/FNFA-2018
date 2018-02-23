package com.example.solal.festivalnationaldufilmdanimation.entity

import org.json.JSONObject

/**
 * Created by sdussoutrevel on 11/12/2017.
 * EvenType Entity, define the type of an event
 */
class Category constructor(
        jsonObject: JSONObject
){

    var id: Int = 0
    lateinit var name: String

    init {
        id = jsonObject.getString("id").toInt()
        name = jsonObject.getString("name")
    }

}

