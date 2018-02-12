package com.example.solal.festivalnationaldufilmdanimation.helpers

/**
 * Created by sdussoutrevel on 12/02/2018.
 */

class StringHelper {
    companion object {
        fun ucfirst(chaine: String) : String {
            return chaine.substring(0, 1).toUpperCase() + chaine.substring(1).toLowerCase()
        }
    }
}