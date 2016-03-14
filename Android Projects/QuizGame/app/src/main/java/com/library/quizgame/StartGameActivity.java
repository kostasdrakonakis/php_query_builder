package com.library.quizgame;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import constants.Constants;
import constants.StringGenerator;

public class StartGameActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private String langText;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFromPrefs();
        StringGenerator.setLocale(langText, StartGameActivity.this);
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

    private void loadFromPrefs() {
        sharedPreferences = getSharedPreferences(Constants.PREFERENCES_FILE, MODE_PRIVATE);
        langText = sharedPreferences.getString(Constants.LANGUAGE_PREFS_FILE, getString(R.string.ta_to_select));
    }
}
