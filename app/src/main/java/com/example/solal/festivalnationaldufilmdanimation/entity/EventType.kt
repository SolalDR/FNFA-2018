package com.example.solal.festivalnationaldufilmdanimation.entity

import org.json.JSONObject
import java.util.Date

/**
 * Created by sdussoutrevel on 11/12/2017.
 */
class EventType constructor(
        jsonObject: JSONObject
){

    var id: Int? = null
    lateinit var name: String
    lateinit var cd:  String

    init {
        id = jsonObject.getString("id").toInt()
        name = jsonObject.getString("name")
        cd = jsonObject.getString("cd")
    }

}

