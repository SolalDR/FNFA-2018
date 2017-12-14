package com.example.solal.festivalnationaldufilmdanimation.entity

import org.json.JSONObject

/**
 * Created by sdussoutrevel on 11/12/2017.
 */
class Scene constructor(
        jsonObject: JSONObject
){

    var id: Int? = null
    var limit: Int? = null
    lateinit var name: String
    var places: ArrayList<Place> = ArrayList<Place>()
    var events: ArrayList<Event> = ArrayList<Event>()
    //var type: Int? = null
    //var background: String? =  null
    //var description: String? = null

    init {
        jsonObject.getInt("id").apply { id = this }
        jsonObject.getString("name").apply { name = this }
        if(jsonObject.has("limit") == true) {
            limit = jsonObject.getInt("limit")
        }
    }

    fun inspect(indent:String? = ""){
        System.out.println(indent + "-----------SCENE-----------")
        System.out.println(indent + "Scene")
        System.out.println(indent + "id : "+ id)
        System.out.println(indent + "limit : "+ limit)
        System.out.println(indent + "name : "+ name)
        System.out.println(indent + "places : ")
        for(place in places){
            System.out.println(place.inspect("  "))
        }
        for(event in events){
            System.out.println(event.inspect("  "))
        }
        System.out.println("-----------/SCENE-----------")
    }
}
