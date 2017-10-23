package com.example.daniel.assignmentthree;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


/**
 * Created by ansambassamabdulhamid on 20/10/17.
 *
 */

public class ToggleBetweenTabs extends FragmentPagerAdapter {
    Controller controller;

    final int TABS = 2;
    private String tabTitles[] = new String[] {"MY INCOMES", "MY EXPENSES"};

    public ToggleBetweenTabs(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        if(position == 0) {
            GalleryFragment galleryFragment = new GalleryFragment();
            return galleryFragment;
        }
        else if(position==1) {
            CapturePicFragment capturePicFragment = new CapturePicFragment();
            return capturePicFragment;
        }
        return null;
    }

    @Override
    public int getCount() {
        return TABS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return tabTitles[position];
    }
}
