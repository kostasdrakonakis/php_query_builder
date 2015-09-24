package com.order.app.order;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.github.clans.fab.FloatingActionButton;
import com.nispok.snackbar.Snackbar;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.File;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;

import dialogs.DialogMessageDisplay;
import sessions.SessionManager;


public class UserProfile extends Activity{

    private static final String URL = "http://my.chatapp.info/order_api/insertData/insert_ratings.php";
    private SessionManager session;
    private static final int REQ_CODE = 1152;
    public static final int MEDIA_TYPE_IMAGE = 1;
    public static final int MEDIA_TYPE_VIDEO = 2;
    private Uri uri;
    private ProgressDialog pDialog;
    private File file;
    private Bitmap bmp;
    boolean taken = false, disabled = false, network_connected, returnedKey;
    private String fileName = "ImageTaken", ratingNameFile = "RatingComplete", orientation = "ImageTakenorientation",
            comTxt, name, timeF, text, committed;
    private SimpleDateFormat timeFormat, dateFormat;
    private Calendar c;
    private ConnectivityManager cm;
    private NetworkInfo activeNetwork;
    private InputStream is = null;
    private ImageView imagePhoto;
    private SharedPreferences sharedPreferences, pref;
    private SharedPreferences.Editor editor , ed;
    private TextView date, time, watersName;
    private FloatingActionButton shareButton, rateButton;
    private RatingBar ratingBar;
    private EditText ratingComment;
    private HashMap<String, String> user;
    private float percent = 0;
    private Intent shareIntent;
    private Button btnnewworder, payment;
    private ArrayList<NameValuePair> nameValuePairs;
    private AlertDialog.Builder builder;
    private MyInsertDataTask task;
    private View newLayout;
    private HttpResponse response;
    private HttpClient httpClient;
    private HttpPost httpPost;
    private HttpEntity httpEntity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.user_profile);
        checkSession();
        checkNetwork();
        populateProfileData();
        registerMainButtons();
        retrievePhotoFromPrefs();
        populateShareContentButtons();
        retrieveRateFromPrefs();
        setupButtonEvents();
    }

    private void populateShareContentButtons() {
        rateButton = (FloatingActionButton) findViewById(R.id.rate);
        shareButton = (FloatingActionButton) findViewById(R.id.share);
    }

    private void checkSession() {
        session = new SessionManager(UserProfile.this);
        session.checkLogin();
        user = session.getUserDetails();
    }


    private void checkNetwork() {
        cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        activeNetwork = cm.getActiveNetworkInfo();
        network_connected = activeNetwork != null && activeNetwork.isAvailable() && activeNetwork.isConnectedOrConnecting();
        if (!network_connected) {
            DialogMessageDisplay.displayWifiSettingsDialog(UserProfile.this, UserProfile.this, getString(R.string.wifi_off_title), getString(R.string.wifi_off_message));
        } else {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                displayInfoMessage(UserProfile.this, getString(R.string.mobile_title), getString(R.string.mobile_message));
            }
        }
    }

    private void registerMainButtons() {
        btnnewworder = (Button) findViewById(R.id.btnnewworder);
        btnnewworder.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserProfile.this, Tables.class);
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
        name = user.get(SessionManager.KEY_NAME);
        watersName = (TextView) findViewById(R.id.waitersName);
        date = (TextView) findViewById(R.id.date);
        time = (TextView) findViewById(R.id.time);
        watersName.setText(Html.fromHtml("<b>" + name + "</b>"));
        c = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd" + "/" + "MM" + "/" + "yyyy");
        text = dateFormat.format(c.getTime());
        date.setText(text);
        timeFormat = new SimpleDateFormat("HH:mm");
        timeF = timeFormat.format(c.getTime());
        time.setText(timeF);
    }

    private void retrieveRateFromPrefs() {
        pref = getSharedPreferences(ratingNameFile, MODE_PRIVATE);
        ed = pref.edit();
        returnedKey = pref.getBoolean(ratingNameFile, disabled);
        if (returnedKey){
            rateButton.setEnabled(false);
        }
    }

    private void retrievePhotoFromPrefs() {
        sharedPreferences = getSharedPreferences(fileName, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        imagePhoto = (ImageView) findViewById(R.id.waiterPhoto);
        imagePhoto.setRotation(270);
        committed = sharedPreferences.getString(fileName, null);


        if (committed != null) {
            imagePhoto.setImageURI(Uri.parse(committed));
        }

        imagePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "waiterProfile.jpg");
                uri = Uri.fromFile(file);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, REQ_CODE);
            }
        });


    }

    private void insertDataToDatabaseFromRating() {
        ratingBar = (RatingBar) newLayout.findViewById(R.id.ratingBar);
        ratingComment = (EditText) newLayout.findViewById(R.id.ratingComment);
        percent = ratingBar.getRating();
        comTxt = ratingComment.getText().toString();
        if (comTxt.isEmpty()) {
            comTxt = " ";
        }
        accessWebService();

    }

    public AlertDialog displayInfoMessage(Context context, String title, String message) {
        builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UserProfile.this.finish();
            }
        }).show();
        return builder.create();

    }

    private void setupButtonEvents() {
        rateButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final AlertDialog.Builder builder = new AlertDialog.Builder(UserProfile.this);
                newLayout = getLayoutInflater().inflate(R.layout.rate_dialog, (ViewGroup) findViewById(R.id.rootLayout));
                builder.setView(newLayout)
                        .setTitle(getString(R.string.rateDialogTitle))
                        .setPositiveButton(R.string.posButtonRate, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                insertDataToDatabaseFromRating();
                                rateButton.setEnabled(false);
                                disabled = true;
                                ed.putBoolean(ratingNameFile, disabled);
                                ed.commit();
                                Snackbar.with(UserProfile.this).text(getString(R.string.confirmRateText)).show(UserProfile.this);
                            }
                        }).create();
                builder.show();
            }
        });

        shareButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                shareIt();
            }
        });
    }

    private void shareIt() {
        shareIntent = new Intent(Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.shareSubject));
        shareIntent.putExtra(android.content.Intent.EXTRA_TEXT, getString(R.string.shareText));
        startActivity(shareIntent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQ_CODE) {
            if (resultCode == RESULT_OK) {
                taken = true;
                getContentResolver().notifyChange(uri, null);
                ContentResolver cr = getContentResolver();
                try {
                    bmp = MediaStore.Images.Media.getBitmap(cr, uri);
                    imagePhoto.setImageBitmap(bmp);
                    imagePhoto.setRotation(270);
                    Toast.makeText(UserProfile.this, getString(R.string.image_saved) + file.getAbsolutePath() + getString(R.string.as) + file.getName(), Toast.LENGTH_LONG).show();
                    editor.putString(fileName, uri.toString());
                    editor.commit();
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage());
                }
            } else if (resultCode == RESULT_CANCELED) {
                taken = false;
                Toast.makeText(UserProfile.this, getString(R.string.canceled), Toast.LENGTH_SHORT).show();
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
        return super.onOptionsItemSelected(item);
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


    private class MyInsertDataTask extends AsyncTask<String, Void, Void>{

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
        protected Void doInBackground(String... params) {
            nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("magazi_id", "" + 3));
            nameValuePairs.add(new BasicNameValuePair("ratingNumber", String.valueOf(percent)));
            nameValuePairs.add(new BasicNameValuePair("comment", comTxt));
            try
            {
                httpClient = new DefaultHttpClient();
                httpPost = new HttpPost(params[0]);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                response = httpClient.execute(httpPost);
                httpEntity = response.getEntity();
                is = httpEntity.getContent();
            }
            catch(Exception e)
            {
                Log.e("Fail 1", e.toString());
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pDialog.dismiss();
        }
    }

    public void accessWebService(){
        task = new MyInsertDataTask();
        task.execute(new String[]{URL});
    }
}
