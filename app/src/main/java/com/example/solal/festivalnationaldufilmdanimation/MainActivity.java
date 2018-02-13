package com.example.solal.festivalnationaldufilmdanimation;

import android.graphics.drawable.Drawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v4.view.ViewPager;

import com.example.solal.festivalnationaldufilmdanimation.views.SlidingTabLayout;
import com.google.android.gms.maps.GoogleMap;


public class MainActivity extends AppCompatActivity {

    // Declaring Your View and Variables

    ViewPager view_pager;
    ViewPagerAdapter adapter;
    SlidingTabLayout tabs;
    CharSequence Titles[]={"Accueil", "Program", "Infos", "Favoris"};
    int Numboftabs = 4;
    private GoogleMap mMap;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        // Creating The ViewPagerAdapter and Passing Fragment Manager, Titles fot the Tabs and Number Of Tabs.
        Drawable titles[] = {
                this.getResources().getDrawable(R.drawable.ico_home),
                this.getResources().getDrawable(R.drawable.ico_date),
                this.getResources().getDrawable(R.drawable.ico_info),
                this.getResources().getDrawable(R.drawable.ico_star)
        };

        MyApplication app = (MyApplication) this.getApplication();
        app.getManager();

        adapter = new ViewPagerAdapter(getSupportFragmentManager(), Titles, Numboftabs, titles);

        // Assigning ViewPager View and setting the adapter
        view_pager = findViewById(R.id.view_pager);
        view_pager.setAdapter(adapter);

        // Assiging the Sliding Tab Layout View
        tabs = findViewById(R.id.tabs);


        tabs.setDistributeEvenly(true); // To make the Tabs Fixed set this true, This makes the tabs Space Evenly in Available width

        // Setting Custom Color for the Scroll bar indicator of the Tab View
        tabs.setCustomTabColorizer(new SlidingTabLayout.TabColorizer() {
            @Override
            public int getIndicatorColor(int position) {
                return getResources().getColor(R.color.white);
            }
        });

        // Setting the ViewPager For the SlidingTabsLayout
        tabs.setViewPager(view_pager);

    }
}
