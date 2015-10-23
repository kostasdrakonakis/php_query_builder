package com.order.app.order;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.astuetz.PagerSlidingTabStrip;
import com.quinny898.library.persistentsearch.SearchBox;
import com.quinny898.library.persistentsearch.SearchResult;

import adapters.ProductsTabPagerAdapter;
import dialogs.DialogMessageDisplay;


public class ProductsViewOrder extends AppCompatActivity{

    private ViewPager viewPager;
    private ProductsTabPagerAdapter mAdapter;
    private PagerSlidingTabStrip tabs;
    private SearchBox box;
    private String title;
    private SearchResult option;
    private Intent intent;
    private ConnectivityManager cm;
    private NetworkInfo activeNetwork;
    boolean network_connected;
    private String servitoros_id;
    private String magazi_id;
    private static final String COMPANY_INTENT_ID = "magaziID";
    private static final String WAITER_INTENT_ID = "servitorosID";
    private static final String TABLE_INTENT_ID = "table_name";
    private Toolbar toolbar;
    private float mActionBarHeight;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.new_order);
        checkNetwork();
    }

    private void checkNetwork() {
        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        activeNetwork = cm.getActiveNetworkInfo();
        network_connected = activeNetwork != null && activeNetwork.isAvailable() && activeNetwork.isConnectedOrConnecting();
        if (!network_connected) {
            DialogMessageDisplay.displayWifiSettingsDialog(ProductsViewOrder.this, ProductsViewOrder.this, getString(R.string.wifi_off_title), getString(R.string.wifi_off_message));
        }else {
            setupPages();
            setupSearch();
        }
    }

    private void setupSearch() {
        box = (SearchBox)findViewById(R.id.searchbox);
        box.enableVoiceRecognition(this);
        box.setLogoText(getString(R.string.search));
        box.setDrawerLogo(null);
    }

    private void setupPages() {
        toolbar = (Toolbar)findViewById(R.id.toolBar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP){
            toolbar.setElevation(0);
        }
        viewPager = (ViewPager) findViewById(R.id.pager);
        intent = getIntent();
        servitoros_id = intent.getStringExtra(WAITER_INTENT_ID);
        magazi_id = intent.getStringExtra(COMPANY_INTENT_ID);
        title = intent.getStringExtra(TABLE_INTENT_ID);
        toolbar.setTitle(getString(R.string.table_id) + title);
        mAdapter = new ProductsTabPagerAdapter(getSupportFragmentManager(), ProductsViewOrder.this, ProductsViewOrder.this);
        viewPager.setAdapter(mAdapter);
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(viewPager);
        tabs.setDividerColor(Color.TRANSPARENT);
        tabs.setIndicatorColor(Color.WHITE);
        tabs.setIndicatorHeight(6);
        setSupportActionBar(toolbar);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                showSearchResults();
                return true;
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_order, menu);
        return true;
    }

    private void showSearchResults() {
        box.revealFromMenuItem(R.id.search, this);
        box.setSearchListener(new SearchBox.SearchListener() {
            @Override
            public void onSearchOpened() {
            }

            @Override
            public void onSearchCleared() {
            }

            @Override
            public void onSearchClosed() {
                closeSearch();
            }

            @Override
            public void onSearchTermChanged() {
            }

            @Override
            public void onSearch(String searchTerm) {
                Toast.makeText(ProductsViewOrder.this, searchTerm + " Searched",
                        Toast.LENGTH_LONG).show();
                toolbar.setTitle(searchTerm);
            }
        });
    }

    protected void closeSearch() {
        box.hideCircularly(this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_left);
    }


}
