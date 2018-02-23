package com.example.solal.festivalnationaldufilmdanimation.entity

import org.json.JSONObject

/**
 * Created by sdussoutrevel on 11/12/2017.
 * Entity Scene
 */
class Place constructor(
        jsonObject: JSONObject
){

    var id: Int = 0
    var lat: Long = 0
    var lon: Long = 0

    lateinit var name: String
    lateinit var address: String

    init {
        jsonObject.getInt("id").apply { id = this }
        jsonObject.getString("name").apply { name = this }
        jsonObject.getString("address").apply { address = this }
        jsonObject.getLong("lat").apply { lat = this }
        jsonObject.getLong("lon").apply { lon = this }
    }

    fun inspect(indent:String? = ""){
        System.out.println("-----------SCENE-----------"
            + "\nScene"
            + "\nid : "+ id
            + "\nname : "+ name
            + "\nplaces : ")

        System.out.println("-----------/SCENE-----------")
    }
}
