package com.example.kostas.ordertakingsystem;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.kostas.sessions.SessionManager;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;


public class MyActivity extends Activity {

    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my);
        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();
        String name = user.get(SessionManager.KEY_NAME);
        TextView watersName = (TextView)findViewById(R.id.waitersName);
        TextView date = (TextView)findViewById(R.id.date);
        TextView time = (TextView)findViewById(R.id.time);
        watersName.setText(Html.fromHtml("<b>" + name + "</b>"));
        Calendar c = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EE" + " " + "dd" + " " + "LLL" + " " + "yyyy");
        String text = dateFormat.format(c.getTime());
        date.setText(text);
        SimpleDateFormat timeFormat = new SimpleDateFormat("HH:mm");
        String timeF = timeFormat.format(c.getTime());
        time.setText(timeF);
        Button btnnewworder = (Button)findViewById(R.id.btnnewworder);
        btnnewworder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyActivity.this, Tables.class);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.my, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_settings:{
                session.logoutUser();
                MyActivity.this.finish();
            }
            default:{
                return super.onOptionsItemSelected(item);
            }
        }
    }
}
