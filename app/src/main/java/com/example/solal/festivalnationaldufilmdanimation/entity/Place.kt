package com.example.solal.festivalnationaldufilmdanimation.entity

import org.json.JSONObject
import java.util.*

/**
 * Created by sdussoutrevel on 11/12/2017.
 */
class Place constructor(
        jsonObject: JSONObject
){
    init {
        val id: Int
        val lat: Float
        val lon: Float
        val date_start: Date
        val date_end: Date
        val scene: Scene
        val address: String
    }
}