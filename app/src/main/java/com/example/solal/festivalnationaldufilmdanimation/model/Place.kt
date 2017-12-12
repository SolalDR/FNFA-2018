package com.example.solal.festivalnationaldufilmdanimation.model

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
        val limit: Int
        val name: String
        val date_start: Date
        val date_end: Date
        val background: String
        val type: Int
        val scene: Scene
        val lat: Float
        val lon: Float
        val address: String
    }
}