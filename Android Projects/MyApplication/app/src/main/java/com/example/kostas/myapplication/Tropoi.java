package com.example.kostas.myapplication;

import android.app.Activity;
import android.os.Bundle;


public class Tropoi extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tropoi);
        getActionBar().setTitle("Τρόποι συμπεριφοράς");
        getActionBar().setHomeButtonEnabled(true);
        getActionBar().setDisplayHomeAsUpEnabled(true);
    }
}
