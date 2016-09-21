package com.kostas.myapplication;

import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;

import dialogs.MyMessageDialog;
import feedactivities.Enimerwsi;
import feedactivities.Kouneli;
import feedactivities.Tromaktiko;
import feedactivities.XDA;


public class MainActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar tb = (Toolbar)findViewById(R.id.toolBar);
        setSupportActionBar(tb);
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo ni = cm.getActiveNetworkInfo();
        boolean activeNetwork = ni!= null &&  ni.isAvailable() && ni.isConnected() && ni.isConnectedOrConnecting();
        if(activeNetwork){
            if(ni.getType() == ConnectivityManager.TYPE_WIFI || ni.getType() == ConnectivityManager.TYPE_MOBILE){
                setupButtons();
            }
        }else{
            MyMessageDialog md = new MyMessageDialog();
            md.displaySettingsDialog(MainActivity.this ,MainActivity.this, "Χωρίς σύνδεση στο διαδύκτιο", "Παρακαλώ ελέξτε την σύνδεση σας και προσπαθήστε ξανά");
        }

    }

    private void setupButtons() {
        Button kouneli = (Button)findViewById(R.id.kouneli);
        Button tromaktiko = (Button)findViewById(R.id.tromaktiko);
        Button xda = (Button)findViewById(R.id.xdabtn);
        Button enimerwsi = (Button)findViewById(R.id.enimerwsibtn);

        kouneli.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a1 = new Intent(MainActivity.this, Kouneli.class);
                startActivity(a1);
            }
        });
        tromaktiko.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a2 = new Intent(MainActivity.this, Tromaktiko.class);
                startActivity(a2);
            }
        });
        xda.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a3 = new Intent(MainActivity.this, XDA.class);
                startActivity(a3);
            }
        });
        enimerwsi.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent a4 = new Intent(MainActivity.this, Enimerwsi.class);
                startActivity(a4);
            }
        });
    }

}
