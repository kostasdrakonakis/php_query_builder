package com.providers.restaurant.ordertakingsystem;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;


import adapters.ImageAdapter;


public class AndroidGridLayoutActivity extends ActionBarActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.grid_layout);
        GridView gridView = (GridView) findViewById(R.id.grid_view);
        final int[] myThumbs = getIntent().getIntArrayExtra("pic_images");
        gridView.setAdapter(new ImageAdapter(this, myThumbs));
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v,
                                    int position, long id) {

                Intent i = new Intent(getApplicationContext(), FullScreenViewActivity.class);
                i.putExtra("id", position);
                i.putExtra("images", myThumbs);
                startActivity(i);
            }
        });
    }
}
