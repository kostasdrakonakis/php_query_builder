package com.example.kostas.myapplication;

import android.app.Activity;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;


public class ListItemActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_list_item);
        TextView tv = (TextView)findViewById(R.id.description);
        TextView usage = (TextView)findViewById(R.id.usage);
        String fetchedText = getIntent().getStringExtra("name");
        String fetchedDescription = getIntent().getStringExtra("description");
        String fetchedUsage = getIntent().getStringExtra("usage");
        ImageView iv = (ImageView)findViewById(R.id.imageViewListItem);
        Drawable image = getResources().getDrawable(getIntent().getIntExtra("icon", -1));
        iv.setImageDrawable(image);
        tv.setText(fetchedDescription);
        usage.setText(fetchedUsage);
        getActionBar().setTitle(fetchedText);
    }
}
