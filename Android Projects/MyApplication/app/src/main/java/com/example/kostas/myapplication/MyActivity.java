package com.example.kostas.myapplication;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.os.Bundle;
import android.support.v4.app.ActionBarDrawerToggle;
import android.support.v4.widget.DrawerLayout;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MyActivity extends Activity {

    private DrawerLayout mDrawerLayout;
    private ActionBarDrawerToggle mDrawerToggle;
    private ListView mDrawerList;
    private List<ListaAdapter> drawerList = new ArrayList<ListaAdapter>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        Button btn = (Button) findViewById(R.id.button);
        getActionBar().setDisplayHomeAsUpEnabled(true);
        getActionBar().setHomeButtonEnabled(true);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyActivity.this, NextActivity.class);
                startActivity(intent);
            }
        });
        buildDrawer();
        populateDrawerList();
        buildListDrawer();
    }

    private void populateDrawerList() {
        drawerList.add(new ListaAdapter(R.drawable.ic_rate, "Αξιολογήστε αυτήν την εφαρμογή"));
        drawerList.add(new ListaAdapter(R.drawable.ic_action_content_email, "Στείλτε ιδέες"));
    }

    private void buildDrawer() {
        mDrawerLayout = (DrawerLayout)findViewById(R.id.drawer1);
        mDrawerToggle = new ActionBarDrawerToggle(MyActivity.this, mDrawerLayout, R.drawable.ic_navigation_drawer, R.string.drawerOpen, R.string.drawerClosed){
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
                getActionBar().setTitle(R.string.drawerOpen);
                invalidateOptionsMenu();
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
                getActionBar().setTitle(R.string.drawerClosed);
                invalidateOptionsMenu();
            }
        };
        mDrawerLayout.setDrawerListener(mDrawerToggle);
    }

    private void buildListDrawer() {
        mDrawerList = (ListView)findViewById(R.id.listViewDrawer);
        ArrayAdapter<ListaAdapter> adapter = new MyDrawerArrayAdapter();
        mDrawerList.setAdapter(adapter);
        mDrawerList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MyActivity.this, "Έχετε επιλέξει " + drawerList.get(position).getItemName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(mDrawerToggle.onOptionsItemSelected(item)){
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        mDrawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        mDrawerToggle.syncState();
    }


    private class MyDrawerArrayAdapter extends ArrayAdapter<ListaAdapter> {

        public MyDrawerArrayAdapter() {
            super(MyActivity.this, R.layout.drawer_list_item, drawerList);
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            View drawerView = convertView;

            if(drawerView == null){
                drawerView = getLayoutInflater().inflate(R.layout.drawer_list_item, parent, false);
            }

            ListaAdapter currentPosition = drawerList.get(position);

            ImageView drawerImage = (ImageView)drawerView.findViewById(R.id.drawerImage);
            drawerImage.setImageResource(currentPosition.getImageId());

            TextView drawerText = (TextView)drawerView.findViewById(R.id.drawerText);
            drawerText.setText(currentPosition.getItemName());

            return drawerView;
        }
    }
}
