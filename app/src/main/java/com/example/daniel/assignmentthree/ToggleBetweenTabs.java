package com.example.daniel.assignmentthree;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;


/**
 * Created by ansambassamabdulhamid on 20/10/17.
 *
 */

public class ToggleBetweenTabs extends FragmentPagerAdapter {

    final int TABS = 2;

    public ToggleBetweenTabs(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0:
                GalleryFragment galleryFragment = new GalleryFragment();
                return galleryFragment;

            case 1:
                CapturePicFragment capturePicFragment = new CapturePicFragment();
                return capturePicFragment;
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch( position) {
            case 0:
                return "Gallery";
            case 1:
                return "Capture image";
        }
        return super.getPageTitle(position);
    }

    @Override
    public int getCount() {
        return TABS;
    }

}
