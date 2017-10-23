package com.example.daniel.assignmentthree;

import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class ViewPagerAdapter extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_pager_adapter);
        ViewPager viewPager = (ViewPager) findViewById(R.id.view_pager);
        ToggleBetweenTabs toggleBetweenTabs = new ToggleBetweenTabs(getSupportFragmentManager());
        viewPager.setAdapter(toggleBetweenTabs);
    }
}
