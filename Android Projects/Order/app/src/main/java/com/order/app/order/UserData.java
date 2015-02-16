package com.order.app.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;

import dialogs.DialogMessageDisplay;
import sessions.SessionManager;


public class UserData extends ActionBarActivity {

    private SessionManager session;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_data);
        session = new SessionManager(getApplicationContext());
        session.checkLogin();
        HashMap<String, String> user = session.getUserDetails();
        Toolbar tb = (Toolbar)findViewById(R.id.topBar);
        setSupportActionBar(tb);
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
                Intent intent = new Intent(UserData.this, Tables.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            }
        });
        Button payment = (Button)findViewById(R.id.btnpayment);
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogMessageDisplay.displayMessage(UserData.this, "Payment Details", "There was no order made. \n Make an order and then tap the payment button.");
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_user_data, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.exit) {
            session.logoutUser();
            UserData.this.finish();
            overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_left);
        }
        return super.onOptionsItemSelected(item);
    }
}
