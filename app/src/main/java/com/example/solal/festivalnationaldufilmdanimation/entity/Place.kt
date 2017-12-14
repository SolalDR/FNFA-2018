package com.example.solal.festivalnationaldufilmdanimation.entity

import org.json.JSONObject
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by sdussoutrevel on 11/12/2017.
 * Entity Place
 */
class Place constructor(
        jsonObject: JSONObject
){

    var lat: Float? = null
    var lon: Float? = null
    var address: String? = null
    var date_start: Date
    var date_end: Date

    init {
        jsonObject.getString("lat")?.apply { lat = this.toFloat() }
        jsonObject.getString("lon")?.apply { lon = this.toFloat() }
        jsonObject.getString("address")?.apply { address = this }
        val sdfmt1 = SimpleDateFormat("yyyy-MM-dd HH:mm:SS", Locale.getDefault())
        date_start = sdfmt1.parse(jsonObject.getString("date_start"))
        date_end = sdfmt1.parse(jsonObject.getString("date_end"))

    }

    fun inspect(indent:String = ""){
        System.out.println(indent + "-----------PLACE-----------")
        System.out.println(indent + "Place")
        System.out.println(indent + "lat : "+ lat)
        System.out.println(indent + "lon : "+ lon)
        System.out.println(indent + "address : "+ address)
        System.out.println(indent + "date start : "+ date_start)
        System.out.println(indent + "date end : "+ date_end)
        System.out.println(indent + "-----------/PLACE-----------")
    }
}