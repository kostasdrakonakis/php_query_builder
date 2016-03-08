package com.library.quizgame;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

public class StartGameActivity extends AppCompatActivity {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_game);
        setupToolbar();
    }

    private void setupToolbar() {
        toolbar = (Toolbar)findViewById(R.id.toolBar);
        toolbar.setTitle(getString(R.string.start_game));
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setLogo(R.drawable.ic_add_circle_outline);
        setSupportActionBar(toolbar);
    }
}
