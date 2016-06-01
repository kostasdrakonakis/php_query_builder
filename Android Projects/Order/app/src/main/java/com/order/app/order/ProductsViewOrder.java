package com.order.app.order;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
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
import android.widget.TextView;

import com.astuetz.PagerSlidingTabStrip;
import com.quinny898.library.persistentsearch.SearchBox;
import com.quinny898.library.persistentsearch.SearchResult;

import java.util.ArrayList;
import java.util.List;

import adapters.ProductsTabPagerAdapter;
import cart.CartActivity;
import cart.CoffeesLayoutActivity;
import cart.SnacksLayoutActivity;
import cart.SweetsLayoutActivity;
import dialogs.DialogMessageDisplay;
import functions.Constants;
import interfaces.CoffeeCommunicator;
import interfaces.SnacksCommunicator;
import interfaces.SweetsCommunicator;
import lists.ProductList;


public class ProductsViewOrder extends AppCompatActivity implements CoffeeCommunicator, SnacksCommunicator, SweetsCommunicator {

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
    private Toolbar toolbar;
    private String name, image, price;
    private List<ProductList> coffeesList, snacksList, sweetsList, ginsList;
    private TextView cartCount;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private int counter, sum;

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
        } else {
            setupPages();
            setupSearch();
        }
    }

    private void setupSearch() {
        box = (SearchBox) findViewById(R.id.searchbox);
        box.setLogoText(getString(R.string.search));

    }

    private void setupPages() {
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            if (toolbar != null) {
                toolbar.setElevation(0);
            }
        }
        viewPager = (ViewPager) findViewById(R.id.pager);
        intent = getIntent();
        servitoros_id = intent.getStringExtra(Constants.WAITER_INTENT_ID);
        magazi_id = intent.getStringExtra(Constants.COMPANY_INTENT_ID);
        title = intent.getStringExtra(Constants.TABLE_INTENT_ID);
        if (toolbar != null) {
            toolbar.setTitle(getString(R.string.table_id) + title);
        }

        mAdapter = new ProductsTabPagerAdapter(getSupportFragmentManager(), ProductsViewOrder.this, ProductsViewOrder.this);
        viewPager.setAdapter(mAdapter);
        tabs = (PagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(viewPager);
        tabs.setDividerColor(Color.TRANSPARENT);
        tabs.setIndicatorColor(Color.WHITE);
        tabs.setIndicatorHeight(6);
        setSupportActionBar(toolbar);
        coffeesList = new ArrayList<>();
        snacksList = new ArrayList<>();
        sweetsList = new ArrayList<>();
        if (toolbar != null) {


            toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
                @Override
                public boolean onMenuItemClick(MenuItem item) {
                    int id = item.getItemId();
                    switch (id) {
                        case R.id.search: {
                            showSearchResults();
                            break;
                        }
                        case R.id.cart_menu: {
                            showCart();
                            break;
                        }
                    }
                    return true;
                }
            });
        }

    }

    private void showCart() {
        Intent intent = new Intent(ProductsViewOrder.this, CartActivity.class);
        intent.putExtra(Constants.PRODUCT_COMPANY_ID_VALUE_PAIR, magazi_id);
        intent.putExtra(Constants.TABLE_INTENT_ID, title);
        intent.putExtra(Constants.WAITER_INTENT_ID, servitoros_id);
        startActivity(intent);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_new_order, menu);
        /*MenuItem item = menu.findItem(R.id.cart_menu);
        item = MenuItemCompat.setActionView(item, R.layout.cart_items_count);

        View view = MenuItemCompat.getActionView(item);
        ViewBadgerListener viewBadgerListener = new ViewBadgerListener(view, "Cart") {
            @Override
            public void onClick(View v) {
                showCart();
            }
        };*/
        return true;
    }

    private int updateCount() {
        sharedPreferences = getSharedPreferences(Constants.BADGE_COUNT, MODE_PRIVATE);
        counter = sharedPreferences.getInt(Constants.BADGE_COUNT_VALUE, 0);
        return counter;
    }

    private void showSearchResults() {
        box.revealFromMenuItem(R.id.search, this);
        box.setSearchListener(new SearchBox.SearchListener() {
            @Override
            public void onSearchOpened() {
                for (int i = 0; i < coffeesList.size(); i++) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        option = new SearchResult(coffeesList.get(i).getName(), getDrawable(R.drawable.ic_history));
                    }
                    box.addSearchable(option);
                }
                for (int i = 0; i < snacksList.size(); i++) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        option = new SearchResult(snacksList.get(i).getName(), getDrawable(R.drawable.ic_history));
                    }
                    box.addSearchable(option);
                }
                for (int i = 0; i < sweetsList.size(); i++) {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                        option = new SearchResult(sweetsList.get(i).getName(), getDrawable(R.drawable.ic_history));
                    }
                    box.addSearchable(option);
                }
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
                for (int i = 0; i < coffeesList.size(); i++) {
                    if (searchTerm.equals(coffeesList.get(i).getName())) {
                        name = coffeesList.get(i).getName();
                        image = coffeesList.get(i).getImage();
                        price = coffeesList.get(i).getPrice();
                        Intent intent = new Intent(ProductsViewOrder.this, CoffeesLayoutActivity.class);
                        intent.putExtra(Constants.COFFEE_NAME, name);
                        intent.putExtra(Constants.COFFEE_PRICE, price);
                        intent.putExtra(Constants.COFFEE_IMAGE, image);
                        intent.putExtra(Constants.TABLE_INTENT_ID, title);
                        intent.putExtra(Constants.WAITER_INTENT_ID, servitoros_id);
                        intent.putExtra(Constants.COMPANY_INTENT_ID, magazi_id);
                        startActivity(intent);
                    }
                }
                for (int i = 0; i < snacksList.size(); i++) {
                    if (searchTerm.equals(snacksList.get(i).getName())) {
                        name = snacksList.get(i).getName();
                        image = snacksList.get(i).getImage();
                        price = snacksList.get(i).getPrice();
                        Intent intent = new Intent(ProductsViewOrder.this, SnacksLayoutActivity.class);
                        intent.putExtra(Constants.SNACK_NAME, name);
                        intent.putExtra(Constants.SNACK_PRICE, price);
                        intent.putExtra(Constants.SNACK_IMAGE, image);
                        intent.putExtra(Constants.TABLE_INTENT_ID, title);
                        intent.putExtra(Constants.WAITER_INTENT_ID, servitoros_id);
                        intent.putExtra(Constants.COMPANY_INTENT_ID, magazi_id);
                        startActivity(intent);
                    }
                }
                for (int i = 0; i < sweetsList.size(); i++) {
                    if (searchTerm.equals(sweetsList.get(i).getName())) {
                        name = sweetsList.get(i).getName();
                        image = sweetsList.get(i).getImage();
                        price = sweetsList.get(i).getPrice();
                        Intent intent = new Intent(ProductsViewOrder.this, SweetsLayoutActivity.class);
                        intent.putExtra(Constants.SWEET_NAME, name);
                        intent.putExtra(Constants.SWEET_PRICE, price);
                        intent.putExtra(Constants.SWEET_IMAGE, image);
                        intent.putExtra(Constants.TABLE_INTENT_ID, title);
                        intent.putExtra(Constants.WAITER_INTENT_ID, servitoros_id);
                        intent.putExtra(Constants.COMPANY_INTENT_ID, magazi_id);
                        startActivity(intent);
                    }
                }

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

    @Override
    public void sendCoffeeListData(String name, String image, String price) {
        coffeesList.add(new ProductList(image, name, price));
    }

    @Override
    public void sendSnackListData(String name, String image, String price) {
        snacksList.add(new ProductList(image, name, price));
    }

    @Override
    public void sendSweetListData(String name, String image, String price) {
        sweetsList.add(new ProductList(image, name, price));
    }

}
