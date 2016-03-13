package com.library.quizgame;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import constants.Constants;

public class QuestionsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_questions);
        setupToolbar();
    }

    private void setupToolbar() {
        toolbar = (Toolbar)findViewById(R.id.toolBar);
        String name = getIntent().getStringExtra(Constants.CATEGORIES_INTENT_NAME);
        toolbar.setTitle(name);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
    }
}
