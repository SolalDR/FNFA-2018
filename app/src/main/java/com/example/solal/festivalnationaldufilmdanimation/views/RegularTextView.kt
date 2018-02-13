package com.example.solal.festivalnationaldufilmdanimation.views

import android.content.Context
import android.graphics.Canvas
import android.graphics.Typeface
import android.util.AttributeSet
import android.support.v7.widget.AppCompatTextView

/**
 * Created by sdussoutrevel on 13/02/2018.
 */

class RegularTextView : AppCompatTextView {


    constructor(context: Context) : super(context) {
        val face = Typeface.createFromAsset(context.assets, "font/Montserrat-Regular.ttf")
        this.typeface = face
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        val face = Typeface.createFromAsset(context.assets, "font/Montserrat-Regular.ttf")
        this.typeface = face
    }

    constructor(context: Context, attrs: AttributeSet, defStyle: Int) : super(context, attrs, defStyle) {
        val face = Typeface.createFromAsset(context.assets, "font/Montserrat-Regular.ttf")
        this.typeface = face
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)


    }

}