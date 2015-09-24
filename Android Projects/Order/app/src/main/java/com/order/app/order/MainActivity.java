package com.order.app.order;

import android.annotation.TargetApi;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;

import com.astuetz.PagerSlidingTabStrip;

import adapters.LoginSignUpTabsAdapter;


public class MainActivity extends FragmentActivity {

    LoginSignUpTabsAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    private PagerSlidingTabStrip tabs;

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        getActionBar().setElevation(0);
        getActionBar().setTitle(R.string.app_name);
        setupPager();

    }

    private void setupPager() {
        mSectionsPagerAdapter = new LoginSignUpTabsAdapter(getSupportFragmentManager(), MainActivity.this, MainActivity.this);
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(mViewPager);
        tabs.setDividerColor(Color.TRANSPARENT);
        tabs.setIndicatorColor(Color.WHITE);
        tabs.setTextColor(Color.WHITE);
        tabs.setIndicatorHeight(2);
    }


}
