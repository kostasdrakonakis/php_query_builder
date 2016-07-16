package com.library.quizgame;

import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.TextView;

import constants.Constants;
import constants.StringGenerator;

public class ResultActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView resultText;
    private String langText;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFromPrefs();
        StringGenerator.setLocale(langText, ResultActivity.this);
        setContentView(R.layout.activity_result);
        setupToolBar();
        setResultData();
    }

    private void setResultData() {
        resultText = (TextView)findViewById(R.id.scoreText);
        int playerScore = getIntent().getIntExtra(Constants.PLAYER_SCORE, 0);
        int listSize = getIntent().getIntExtra(Constants.LIST_SIZE, 0);
        if (playerScore > 0 && playerScore < listSize/3){
            resultText.setText(String.valueOf(playerScore) + "/" + String.valueOf(listSize) + " " + getString(R.string.poor));
        }else if (playerScore > listSize/3 && playerScore < listSize/2){
            resultText.setText(String.valueOf(playerScore) + "/" + String.valueOf(listSize) + " " + getString(R.string.medium));
        }else {
            resultText.setText(String.valueOf(playerScore) + "/" + String.valueOf(listSize) + " " + getString(R.string.excellent));
        }
    }

    private void setupToolBar() {
        toolbar = (Toolbar)findViewById(R.id.toolBar);
        toolbar.setTitle(getString(R.string.resultTitle));
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
    }

    private void loadFromPrefs() {
        sharedPreferences = getSharedPreferences(Constants.PREFERENCES_FILE, MODE_PRIVATE);
        langText = sharedPreferences.getString(Constants.LANGUAGE_PREFS_FILE, getString(R.string.ta_to_select));
    }
}
