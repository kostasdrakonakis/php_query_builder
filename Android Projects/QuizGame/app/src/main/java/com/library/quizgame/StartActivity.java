package com.library.quizgame;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import constants.Constants;
import constants.StringGenerator;

public class StartActivity extends AppCompatActivity {

    private Button start, categories, options;
    private Toolbar toolbar;
    private String langText;
    private int lifesNumber;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFromPrefs();
        StringGenerator.setLocale(langText, StartActivity.this);
        setContentView(R.layout.activity_start);
        options = (Button)findViewById(R.id.buttonOptions);
        categories = (Button)findViewById(R.id.buttonCategories);
        start = (Button)findViewById(R.id.buttonStartGame);
        setupToolBar();
        setupButtons();
    }

    private void loadFromPrefs() {
        sharedPreferences = getSharedPreferences(Constants.PREFERENCES_FILE, MODE_PRIVATE);
        langText = sharedPreferences.getString(Constants.LANGUAGE_PREFS_FILE, getString(R.string.ta_to_select));
        lifesNumber = Integer.parseInt(sharedPreferences.getString(Constants.LIFES_PREFS_FILE, String.valueOf(0)));
    }

    private void setupToolBar() {
        toolbar = (Toolbar)findViewById(R.id.toolBar);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
    }

    private void setupButtons() {
        options.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, OptionsActivity.class);
                startActivity(intent);
            }
        });

        categories.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, CategoryActivity.class);
                startActivity(intent);
            }
        });

        start.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StartActivity.this, StartGameActivity.class);
                intent.putExtra(Constants.USER_LIFES, lifesNumber);
                startActivity(intent);
            }
        });
    }
}
