
package com.example.solal.festivalnationaldufilmdanimation

import android.graphics.drawable.Drawable
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentStatePagerAdapter
import android.text.SpannableStringBuilder
import android.text.Spanned
import android.text.style.DynamicDrawableSpan
import android.text.style.ImageSpan


/**
 * Created by sdussoutrevel on 14/02/2018.
 */
class ViewPagerAdapter      // Build a Constructor and assign the passed Values to appropriate values in the class
(fm: FragmentManager, internal var Titles: Array<CharSequence> // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
 , internal var NumbOfTabs: Int // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created
 , internal var Resources: Array<Drawable>) : FragmentStatePagerAdapter(fm) {

    //This method return the fragment for the every position in the View Pager
    override fun getItem(position: Int): Fragment {

        when (position) {

            0 -> {
                return HomeFragment()
            }
            1 -> {
                return EventsFragment()
            }
            2 -> {
                return InfoFragment()
            }
            else -> {
                return StarsFragment()
            }
        }
    }

    // This method return the titles for the Tabs in the Tab Strip
    override fun getPageTitle(position: Int): CharSequence {
        var title = ""
        val myDrawable = this.Resources[position]
        title = this.Titles[position].toString()
        val sb = SpannableStringBuilder("   \n" + Titles[position]) // space added before text for convenience
        try {
            myDrawable.setBounds(0, 0, 100, 100)
            val span = ImageSpan(myDrawable, DynamicDrawableSpan.ALIGN_BASELINE)
            sb.setSpan(span, 0, 2, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE)
            return sb
        } catch (e: Exception) {
            //
        }

        return Titles[position]
    }

    // This method return the Number of tabs for the tabs Strip
    override fun getCount(): Int {
        return NumbOfTabs
    }
}
