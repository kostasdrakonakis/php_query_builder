package com.order.app.order;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;

import adapters.TabsPagerAdapter;


public class NewOrder extends ActionBarActivity {

    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);
        viewPager = (ViewPager) findViewById(R.id.pager);
        Toolbar tb = (Toolbar)findViewById(R.id.topBar);
        Intent intent = getIntent();
        String title = intent.getStringExtra("tableID");
        tb.setTitle("Table: " + title);
        setSupportActionBar(tb);


        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);

        tabs.setViewPager(viewPager);
        tabs.setDividerColor(Color.BLUE);
        tabs.setIndicatorColor(Color.BLUE);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_order, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_left);
    }
}
