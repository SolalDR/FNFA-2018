package com.example.solal.festivalnationaldufilmdanimation;

import android.graphics.drawable.Drawable;
import android.media.Image;
import android.support.design.widget.TabItem;
import android.support.design.widget.TabLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    // Declaring Your View and Variables

    Toolbar toolbar;
    ViewPager view_pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Accueil", "Program", "Infos", "Favoris"};
    int Numboftabs = 4;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        Drawable titles[] = {
                this.getResources().getDrawable(R.mipmap.ic_launcher),
                this.getResources().getDrawable(R.mipmap.ic_launcher),
                this.getResources().getDrawable(R.mipmap.ic_launcher),
                this.getResources().getDrawable(R.mipmap.ic_launcher)
        };
        adapter =  new ViewPagerAdapter(getSupportFragmentManager(), Titles, Numboftabs, titles);

        // Assigning ViewPager View and setting the adapter
        view_pager = (ViewPager) findViewById(R.id.view_pager);
        view_pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = (SlidingTabLayout) findViewById(R.id.tabs);

        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.dark);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(view_pager);


        //tab1.setBackground( this.getResources().getDrawable(R.drawable.ic_launcher_background) );

    }



}
