package com.example.daniel.assignmentthree;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

/**
 * Created by Daniel on 2017-10-25.
 */

/*
In order to use the contents of the three tabs, i.e. the three fragments, we must extends
Three tabs are created, each containing the layout of the fragment that is assigned to it.
 */
public class PagerAdapter extends FragmentStatePagerAdapter {
    int nbrOfTabs;

    public PagerAdapter(FragmentManager fm, int nbrOfTabs){
        super(fm);
        this.nbrOfTabs = nbrOfTabs;
    }
    /*
    Returns the Fragment associated with a specified position.
     */
    @Override
    public Fragment getItem(int position) {
        //The switch case is used for the variable position
        switch (position){
            //if position is zero then we create an object for Tab1
            case 0:
                ProfilesTab tab1 = new ProfilesTab();
                return tab1;
            case 1:
                GroupsTab tab2 = new GroupsTab();
                return tab2;
            case 2:
                FriendsTab tab3 = new FriendsTab();
                return tab3;
            default:
                return null;

        }
    }

    /*
    Return the number of views available.
     */
    @Override
    public int getCount() {
        return nbrOfTabs;
    }
}
