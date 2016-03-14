package com.library.quizgame;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import constants.Constants;
import constants.StringGenerator;

public class QuestionsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private String name, id;
    private String langText;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFromPrefs();
        StringGenerator.setLocale(langText, QuestionsActivity.this);
        setContentView(R.layout.activity_questions);
        setupToolbar();
    }

    private void setupToolbar() {
        toolbar = (Toolbar)findViewById(R.id.toolBar);
        name = getIntent().getStringExtra(Constants.CATEGORIES_INTENT_NAME);
        id = getIntent().getStringExtra(Constants.CATEGORIES_INTENT_ID);
        toolbar.setTitle(name);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
    }

    private void loadFromPrefs() {
        sharedPreferences = getSharedPreferences(Constants.PREFERENCES_FILE, MODE_PRIVATE);
        langText = sharedPreferences.getString(Constants.LANGUAGE_PREFS_FILE, getString(R.string.ta_to_select));
    }
}
