package com.kostas.stockpredictions;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;


public class RefreshActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refresh);
        Toolbar tb = (Toolbar)findViewById(R.id.toolBar);
        tb.setTitle(R.string.app_name);
        setSupportActionBar(tb);
        Button btn = (Button)findViewById(R.id.buttonRefresh);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(RefreshActivity.this, ListLoaderActivity.class);
                startActivity(intent);
                RefreshActivity.this.finish();
            }
        });
    }


}
