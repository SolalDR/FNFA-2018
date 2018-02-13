package com.example.solal.festivalnationaldufilmdanimation.views;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.support.v7.widget.AppCompatTextView;

/**
 * Created by sdussoutrevel on 13/02/2018.
 */

public class BoldTextView extends AppCompatTextView {


    public BoldTextView(Context context) {
        super(context);
        Typeface face= Typeface.createFromAsset(context.getAssets(), "font/Montserrat-Bold.ttf");
        this.setTypeface(face);
    }

    public BoldTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "font/Montserrat-Bold.ttf");
        this.setTypeface(face);
    }

    public BoldTextView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        Typeface face=Typeface.createFromAsset(context.getAssets(), "font/Montserrat-Bold.ttf");
        this.setTypeface(face);
    }

    protected void onDraw (Canvas canvas) {
        super.onDraw(canvas);


    }

}