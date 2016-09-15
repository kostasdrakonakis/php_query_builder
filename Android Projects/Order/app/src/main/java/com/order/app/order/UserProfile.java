package com.order.app.order;

import android.Manifest;
import android.app.AlarmManager;
import android.app.AlertDialog;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.enums.SnackbarType;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import dialogs.DialogMessageDisplay;
import functions.Constants;
import functions.StringGenerator;
import receivers.CheckSubscriptionReceiver;
import sessions.SessionManager;
import settings.SettingsActivity;


public class UserProfile extends AppCompatActivity{

    private SessionManager session;
    private Uri number;
    private ProgressDialog pDialog;
    boolean disabled = false, network_connected, returnedKey, ratingComplited = false;
    private String comTxt, name, timeF, text, subjectTXT, messageTXT, servitorosId, magaziID;
    private SimpleDateFormat timeFormat, dateFormat;
    private Calendar calendar;
    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private TextView date, time, watersName, waiterID, magaziId;
    private FloatingActionButton helpButton, rateButton;
    private RatingBar ratingBar;
    private EditText ratingComment, subject, message;
    private HashMap<String, String> user;
    private float percent = 0;
    private Intent sendIntent, callIntent;
    private Button btnnewworder, payment;
    private AlertDialog.Builder rateDialog, helpDialog;
    private MyInsertDataTask task;
    private View newLayout, helpView;
    private Switch helpSwitch;
    private LinearLayout contactLayout;
    private Toolbar toolbar;
    private HttpURLConnection urlConnection;
    private URL url;
    private OutputStreamWriter outputStreamWriter;
    private InputStream inputStream;
    private StringBuilder jsonResult;
    private JSONObject dataToWrite, jsonResponse;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);
        toolbar = (Toolbar)findViewById(R.id.toolBar);
        toolbar.setTitle(getString(R.string.user_profile));
        setSupportActionBar(toolbar);
        setupReceiver();
        checkSession();
        checkNetwork();
        populateProfileData();
        registerMainButtons();
        populateShareContentButtons();
        retrieveRateFromPrefs();
        setupButtonEvents();

    }

    private void setupReceiver() {
        Long time = new GregorianCalendar().getTimeInMillis() + 24*60*60*1000;//+24*60*60*1000 for a day;
        Intent intentAlarm = new Intent(UserProfile.this, CheckSubscriptionReceiver.class);
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(UserProfile.this, 0, intentAlarm, PendingIntent.FLAG_UPDATE_CURRENT);
        alarmManager.set(AlarmManager.RTC_WAKEUP, time, pendingIntent);
    }

    private void populateShareContentButtons() {
        rateButton = (FloatingActionButton) findViewById(R.id.rate);
        rateButton.setColorNormal(Color.parseColor(Constants.ENABLED_BUTTON_COLOR));
        helpButton = (FloatingActionButton) findViewById(R.id.help);
        helpButton.setColorNormal(Color.parseColor(Constants.ENABLED_BUTTON_COLOR));
    }

    private void checkSession() {
        session = new SessionManager(UserProfile.this);
        session.checkLogin();
        user = session.getUserDetails();
        servitorosId = user.get(Constants.KEY_WAITER_ID);
        magaziID = user.get(Constants.KEY_SHOP_ID);
    }


    private void checkNetwork() {
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        network_connected = networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnectedOrConnecting();
        if (!network_connected) {
            DialogMessageDisplay.displayWifiSettingsDialog(UserProfile.this, UserProfile.this, getString(R.string.wifi_off_title), getString(R.string.wifi_off_message));
        }/*else {
            if (networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                displayInfoMessage(UserProfile.this, getString(R.string.mobile_title), getString(R.string.mobile_message));
            }
        }*/
    }

    private void registerMainButtons() {
        btnnewworder = (Button) findViewById(R.id.btnnewworder);
        btnnewworder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfile.this, Tables.class);
                intent.putExtra(Constants.WAITER_INTENT_ID, servitorosId);
                intent.putExtra(Constants.COMPANY_INTENT_ID, magaziID);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            }
        });
        payment = (Button) findViewById(R.id.btnpayment);
        payment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogMessageDisplay.displayInfoMessage(UserProfile.this, getString(R.string.payment_details), getString(R.string.payment_message));

            }
        });
    }

    private void populateProfileData() {
        name = user.get(Constants.KEY_NAME);
        watersName = (TextView) findViewById(R.id.waitersName);
        date = (TextView) findViewById(R.id.date);
        time = (TextView) findViewById(R.id.time);
        waiterID = (TextView)findViewById(R.id.waiterId);
        magaziId = (TextView)findViewById(R.id.magaziId);
        watersName.setText(Html.fromHtml("<b>" + name + "</b>"));
        calendar = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd" + "/" + "MM" + "/" + "yyyy");
        text = dateFormat.format(calendar.getTime());
        date.setText(text);
        timeFormat = new SimpleDateFormat("HH:mm");
        timeF = timeFormat.format(calendar.getTime());
        time.setText(timeF);
        waiterID.setText(servitorosId);
        magaziId.setText(magaziID);
    }

    private void retrieveRateFromPrefs() {
        sharedPreferences = getSharedPreferences(Constants.RATING_NAME_FILE_PREFS, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        returnedKey = sharedPreferences.getBoolean(Constants.RATING_NAME_FILE_PREFS, disabled);
        if (returnedKey){
            rateButton.setEnabled(false);
        }
    }


    private void insertDataToDatabaseFromRating() {
        ratingBar = (RatingBar) newLayout.findViewById(R.id.ratingBar);
        ratingComment = (EditText) newLayout.findViewById(R.id.ratingComment);
        percent = ratingBar.getRating();
        comTxt = ratingComment.getText().toString();
        if (comTxt.isEmpty()) {
            comTxt = " ";
        }
        if (percent ==0){
            Toast.makeText(UserProfile.this, getString(R.string.zero_rating), Toast.LENGTH_LONG).show();
            ratingComplited= false;
        }else {
            accessWebService();
            ratingComplited = true;
        }


    }

    private void setupButtonEvents() {
        rateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rateDialog = new AlertDialog.Builder(UserProfile.this);
                newLayout = getLayoutInflater().inflate(R.layout.rate_dialog, (ViewGroup) findViewById(R.id.rootLayout));
                rateDialog.setView(newLayout)
                        .setTitle(getString(R.string.rateDialogTitle))
                        .setPositiveButton(R.string.posButtonRate, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                insertDataToDatabaseFromRating();
                                if (ratingComplited) {
                                    rateButton.setEnabled(false);
                                    disabled = true;
                                    editor.putBoolean(Constants.RATING_NAME_FILE_PREFS, disabled);
                                    editor.commit();
                                    Snackbar.with(UserProfile.this).text(getString(R.string.confirmRateText)).color(Color.parseColor(Constants.ENABLED_BUTTON_COLOR)).show(UserProfile.this);
                                }

                            }
                        }).create();
                rateDialog.show();
            }
        });

        helpButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                helpIt();
            }
        });

    }


    private void helpIt() {
        helpDialog = new AlertDialog.Builder(this);
        helpView = getLayoutInflater().inflate(R.layout.help_dialog, (ViewGroup) findViewById(R.id.helpRootLayout));
        subject = (EditText)helpView.findViewById(R.id.subject);
        message = (EditText)helpView.findViewById(R.id.message);
        helpSwitch = (Switch)helpView.findViewById(R.id.helpSwitch);

        contactLayout = (LinearLayout)helpView.findViewById(R.id.contactLayout);
        helpDialog.setTitle(getString(R.string.help))
                .setMessage(getString(R.string.help_dialog_message)+ " \n" + Constants.TEL )
                .setView(helpView)
                .setPositiveButton(getString(R.string.send), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        subjectTXT = subject.getText().toString();
                        messageTXT = message.getText().toString();
                        if (subjectTXT.isEmpty() && messageTXT.isEmpty()){
                            StringGenerator.showSnackMessage(SnackbarType.SINGLE_LINE, getString(R.string.subject_or_message_empty),UserProfile.this, Color.parseColor(Constants.ENABLED_BUTTON_COLOR), UserProfile.this);
                        }else if (subjectTXT.isEmpty()){
                            StringGenerator.showSnackMessage(SnackbarType.SINGLE_LINE, getString(R.string.subject_empty), UserProfile.this, Color.parseColor(Constants.ENABLED_BUTTON_COLOR), UserProfile.this);
                        }else if (messageTXT.isEmpty()){
                            StringGenerator.showSnackMessage(SnackbarType.SINGLE_LINE, getString(R.string.message_empty), UserProfile.this, Color.parseColor(Constants.ENABLED_BUTTON_COLOR), UserProfile.this);

                        }else {
                            sendIntent = new Intent(Intent.ACTION_SEND);
                            sendIntent.setData(Uri.parse("mailto:"));
                            sendIntent.setType("text/plain");
                            sendIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{Constants.ADMIN_EMAIL});
                            sendIntent.putExtra(Intent.EXTRA_SUBJECT, subjectTXT);
                            sendIntent.putExtra(Intent.EXTRA_TEXT, messageTXT);
                            startActivity(sendIntent);
                        }


                    }
                })
                .setNegativeButton(getString(R.string.cancel), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setNeutralButton(getString(R.string.call), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                            if (ContextCompat.checkSelfPermission(UserProfile.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED) {
                                // Should we show an explanation?
                                if (ActivityCompat.shouldShowRequestPermissionRationale(UserProfile.this,
                                        Manifest.permission.CALL_PHONE)) {

                                    StringGenerator.showSnackMessage(
                                            SnackbarType.MULTI_LINE,
                                            "In order to get in touch with the Support Team you have to accept permission",
                                            UserProfile.this,
                                            Color.parseColor(Constants.ENABLED_BUTTON_COLOR),
                                            UserProfile.this);


                                } else {
                                    ActivityCompat.requestPermissions(UserProfile.this,
                                            new String[]{Manifest.permission.CALL_PHONE},
                                            Constants.MY_PERMISSION_CODE);
                                }
                            }else {
                                number = Uri.parse("tel:" + Constants.TEL);
                                callIntent = new Intent(Intent.ACTION_DIAL);
                                callIntent.setData(number);
                                startActivity(callIntent);
                            }
                        }else {
                            number = Uri.parse("tel:" + Constants.TEL);
                            callIntent = new Intent(Intent.ACTION_DIAL);
                            callIntent.setData(number);
                            startActivity(callIntent);
                        }

                    }
                })
                .create();

        final AlertDialog dialog = helpDialog.show();
        dialog.show();
        Button btn = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
        btn.setEnabled(false);
        if (helpSwitch!= null){
            helpSwitch.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if (isChecked) {
                        if (contactLayout.getVisibility() == View.GONE) {
                            contactLayout.setVisibility(View.VISIBLE);
                        } else {
                            contactLayout.setVisibility(View.GONE);
                        }
                        Button btn = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        btn.setEnabled(true);
                    } else {
                        if (contactLayout.getVisibility() == View.GONE) {
                            contactLayout.setVisibility(View.VISIBLE);
                        } else {
                            contactLayout.setVisibility(View.GONE);
                        }
                        Button btn = dialog.getButton(AlertDialog.BUTTON_POSITIVE);
                        btn.setEnabled(false);
                    }
                }
            });
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case Constants.MY_PERMISSION_CODE: {
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    number = Uri.parse("tel:" + Constants.TEL);
                    callIntent = new Intent(Intent.ACTION_DIAL);
                    callIntent.setData(number);
                    startActivity(callIntent);

                } else {
                    StringGenerator.showSnackMessage(SnackbarType.MULTI_LINE,getString(R.string.confirm_phone_permission),UserProfile.this, Color.parseColor(Constants.ENABLED_BUTTON_COLOR), UserProfile.this);
                    ActivityCompat.requestPermissions(UserProfile.this,
                            new String[]{Manifest.permission.CALL_PHONE},
                            Constants.MY_PERMISSION_CODE);
                }
            }

        }
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
            logoutUser();
        }
        if (id == R.id.action_settings){
            openSettings();
        }
        return super.onOptionsItemSelected(item);
    }

    private void openSettings() {
        Intent intent = new Intent(UserProfile.this, SettingsActivity.class);
        startActivity(intent);
    }

    private void logoutUser(){
        session.logoutUser();
        UserProfile.this.finish();
        overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_left);
    }

    @Override
    public void onBackPressed() {
        UserProfile.this.finish();
    }


    private class MyInsertDataTask extends AsyncTask<String, Void, Boolean>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(UserProfile.this);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setIndeterminate(true);
            pDialog.setMessage(getString(R.string.dialog_rate_data_submit));
            pDialog.setCancelable(false);
            pDialog.setInverseBackgroundForced(true);
            pDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {

            try {
                url = new URL(params[0]);
                urlConnection =(HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty(Constants.CUSTOM_HEADER, Constants.API_KEY);
                Log.e("Custom Header", Constants.CUSTOM_HEADER);
                Log.e("Api Key: ", Constants.API_KEY);
                Log.e("Method: ", Constants.METHOD_POST);
                urlConnection.setRequestProperty("Accept-Encoding", "application/json");
                urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                urlConnection.setRequestMethod(Constants.METHOD_POST);

                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.connect();

                setupDataToDB();

                outputStreamWriter = new OutputStreamWriter(urlConnection.getOutputStream());
                outputStreamWriter.write(dataToWrite.toString());
                Log.e("Data To Write", dataToWrite.toString());

                outputStreamWriter.flush();
                outputStreamWriter.close();
                int responseCode = urlConnection.getResponseCode();
                Log.e("Response Code ", String.valueOf(responseCode));
                if (responseCode == 400){
                    return false;
                }else if(responseCode == 500){
                    return false;
                } else if (responseCode == 201){
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());
                    jsonResult = StringGenerator.inputStreamToString(inputStream, UserProfile.this);
                    jsonResponse = new JSONObject(jsonResult.toString());

                    Log.e("Data From JSON", jsonResponse.toString());
                    String status = jsonResponse.getString("status");
                    String status_code = jsonResponse.getString("status_code");
                    String messageFromResponse = jsonResponse.getString("message");
                    Log.e("Response Status", status);
                    Log.e("Response Status Code", status_code);
                    Log.e("Response Message", messageFromResponse);
                    return true;
                }

            } catch (Exception e) {
                urlConnection.getErrorStream();
                e.printStackTrace();
            }

            return false;
        }
        @Override
        protected void onPostExecute(Boolean aVoid) {
            super.onPostExecute(aVoid);
            pDialog.dismiss();
        }
    }

    private void setupDataToDB() {
        dataToWrite = new JSONObject();
        try {
            dataToWrite.put("" + Constants.USER_COMPANY_ID + "", magaziID);
            dataToWrite.put("" + Constants.USER_RATING + "", String.valueOf(percent));
            dataToWrite.put("" + Constants.USER_RATING_COMMENT + "", comTxt);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void accessWebService(){
        task = new MyInsertDataTask();
        task.execute(Constants.RATINGS_URL);
    }
}
