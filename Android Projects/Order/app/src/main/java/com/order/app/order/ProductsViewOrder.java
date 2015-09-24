package com.order.app.order;

import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.view.ViewPager;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import com.astuetz.PagerSlidingTabStrip;
import com.quinny898.library.persistentsearch.SearchBox;
import com.quinny898.library.persistentsearch.SearchResult;

import adapters.ProductsTabPagerAdapter;


public class ProductsViewOrder extends FragmentActivity {

    private ViewPager viewPager;
    private ProductsTabPagerAdapter mAdapter;
    private PagerSlidingTabStrip tabs;
    private SearchBox box;
    private String title;
    private SearchResult option;
    private Intent intent;


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_order);
        setupPages();
        setupSearch();
    }

    private void setupSearch() {
        box = (SearchBox)findViewById(R.id.searchbox);
        box.enableVoiceRecognition(this);
        box.setLogoText(getString(R.string.search));
        box.setDrawerLogo(null);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setupPages() {
        getActionBar().setElevation(0);
        viewPager = (ViewPager) findViewById(R.id.pager);
        intent = getIntent();
        title = intent.getStringExtra("tableID");
        getActionBar().setTitle(getString(R.string.table_id) + title);
        mAdapter = new ProductsTabPagerAdapter(getSupportFragmentManager(), ProductsViewOrder.this, ProductsViewOrder.this);
        viewPager.setAdapter(mAdapter);
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(viewPager);
        tabs.setDividerColor(Color.TRANSPARENT);
        tabs.setIndicatorColor(Color.WHITE);
        tabs.setIndicatorHeight(6);

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_order, menu);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.search:{
                showSearchResults();
                return true;
            }
            case R.id.cart:{
                return true;
            }
            default:{
                return super.onOptionsItemSelected(item);
            }
        }
    }

    private void showSearchResults() {
        box.revealFromMenuItem(R.id.search, this);
        getActionBar().hide();
        for (int x = 0; x < 10; x++) {
            option = new SearchResult("Result "
                    + Integer.toString(x), getResources().getDrawable(
                    R.drawable.abc_ic_search_api_mtrl_alpha));
            box.addSearchable(option);
        }
        box.setSearchListener(new SearchBox.SearchListener() {
            @Override
            public void onSearchOpened() {
            }

            @Override
            public void onSearchCleared() {
            }

            @Override
            public void onSearchClosed() {
                getActionBar().show();
                if (box.getVisibility() == View.VISIBLE){
                    box.setVisibility(View.GONE);
                }
            }

            @Override
            public void onSearchTermChanged() {
            }

            @Override
            public void onSearch(String s) {

            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_left);
    }
}
