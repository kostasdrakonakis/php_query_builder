package com.example.kostas.ordertakingsystem;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.astuetz.PagerSlidingTabStrip;
import com.example.kostas.adapters.TabsPagerAdapter;
import com.example.kostas.functions.Cart;


public class NewOrder extends FragmentActivity {

    private ViewPager viewPager;
    private TabsPagerAdapter mAdapter;
    private int currentColor = 0xFF666666;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_order);

        // Initilization
        viewPager = (ViewPager) findViewById(R.id.pager);
        Intent intent = getIntent();
        String title = intent.getStringExtra("tableID");
        mAdapter = new TabsPagerAdapter(getSupportFragmentManager());

        viewPager.setAdapter(mAdapter);
        PagerSlidingTabStrip tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);

        tabs.setViewPager(viewPager);
        tabs.setIndicatorColor(currentColor);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.new_order, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:{
                Intent intent = new Intent(NewOrder.this, Cart.class);
                startActivity(intent);
            }
            default:{
                return super.onOptionsItemSelected(item);
            }
        }
    }
}
