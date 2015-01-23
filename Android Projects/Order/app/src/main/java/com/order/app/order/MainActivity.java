package com.order.app.order;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.astuetz.PagerSlidingTabStrip;

import adapters.SectionsPagerAdapter;


public class MainActivity extends ActionBarActivity {

    SectionsPagerAdapter mSectionsPagerAdapter;
    ViewPager mViewPager;
    private PagerSlidingTabStrip tabs;
    private Toolbar tb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        tb = (Toolbar)findViewById(R.id.topBar);
        setSupportActionBar(tb);
        tb.setTitle(R.string.app_name);
        mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(mSectionsPagerAdapter);
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(mViewPager);
        tabs.setDividerColor(Color.BLUE);
        tabs.setIndicatorColor(Color.BLUE);
    }

}
