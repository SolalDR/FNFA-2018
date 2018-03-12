package com.example.solal.festivalnationaldufilmdanimation;

import android.graphics.drawable.Drawable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.DynamicDrawableSpan;
import android.text.style.ImageSpan;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    Drawable Resources[];
    int NumbOfTabs; // Store the number of tabs, this will also be passed when the ViewPagerAdapter is created


    // Build a Constructor and assign the passed Values to appropriate values in the class
    public ViewPagerAdapter(FragmentManager fm,CharSequence mTitles[], int mNumbOfTabsumb, Drawable[] resources) {
        super(fm);
        this.Resources = resources;
        this.Titles = mTitles;
        this.NumbOfTabs = mNumbOfTabsumb;
    }

    //This method return the fragment for the every position in the View Pager
    @Override
    public Fragment getItem(int position) {

        switch (position) {

            case 0:
                HomeFragment tab1 = new HomeFragment();
                return tab1;
            case 1:
                ProgramFragment tab2 = new ProgramFragment();
                return tab2;
            case 2:
                InfoFragment tab3 = new InfoFragment();
                return tab3;
            default:
                StarsFragment tab4 = new StarsFragment();
                return tab4;
        }
    }

    // This method return the titles for the Tabs in the Tab Strip
    @Override
    public CharSequence getPageTitle(int position) {
        String title = "";
        return Titles[position];
    }

    public Drawable getBackground(Integer position) {
        return this.Resources[position];
    }

    // This method return the Number of tabs for the tabs Strip
    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}
