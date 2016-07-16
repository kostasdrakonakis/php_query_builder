package com.example.matrix.accidents;

import android.app.AlertDialog;
import android.content.ActivityNotFoundException;
import android.content.ContentResolver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.location.Location;
import android.location.LocationManager;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.speech.RecognizerIntent;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;
import java.util.Locale;


public class MainActivity extends ActionBarActivity {

    private static final int REQ_CODE = 1152;
    private ImageView pictureImage;
    private Button takePicture, sharePicture, mapButton, sendComment, micButton, callButton;
    private ListView listView;
    private EditText commentBox;
    private TextView lat, lon;
    private Uri uri;
    private double latitude, longitude;
    private Bitmap bmp;
    private File file;
    private Location gpsLocation;
    private LocationManager lm;
    private boolean taken = false;
    private String fileName = "ImageTaken";
    private String listName = "list";
    private final int REQ_CODE_SPEECH_INPUT = 100;
    private ArrayAdapter<String> adapter;
    private ArrayList<String> commentsList;
    private AppLocationService appLocationService;;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar tb = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(tb);
        appLocationService = new AppLocationService(MainActivity.this);
        setupComponents();
        if (!checkGpsSettings(MainActivity.this)) {
            DialogMessageDisplay.displayGpsSettingsDialog(MainActivity.this, MainActivity.this, "Δεν ανιχνέυθηκε σήμα GPS", "Παρακαλώ ελέξτε τις ρυθμίσεις του GPS δέκτη σας", AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        }else{
            setupCameraPhoto();
            setupMaps();
            setupShareIntent();
            setupCommentsList();
            setupMicrophone();
            setupCall();
        }

    }

    private void setupCall() {
        callButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);
                builder.setTitle("Προειδοποίηση");
                builder.setMessage("Με αυτήν την ενέργεια θα πραγματοποιήσετε κλήση στο Υπουργείο Δημοσίας Τάξης. Αυτό μπορεί να επιφέρει χρεώσεις ανάλογα με το πακέτο ομιλίας που χρησιμοποιήτε. Θέλετε να συνεχίσετε?");
                builder.setPositiveButton("Συνέχεια", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(Intent.ACTION_CALL);
                        intent.setData(Uri.parse("tel:2106977000"));
                        startActivity(intent);
                    }
                });
                builder.setNegativeButton("Άκυρο", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                });
                builder.create();
                builder.show();
            }
        });
    }

    private void setupMicrophone() {
        micButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                promptSpeechInput();
            }
        });
    }

    private void promptSpeechInput() {
        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());
        intent.putExtra(RecognizerIntent.EXTRA_PROMPT,
                getString(R.string.speech_prompt));
        try {
            startActivityForResult(intent, REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(getApplicationContext(),
                    getString(R.string.speech_not_supported),
                    Toast.LENGTH_SHORT).show();
        }
    }

    private void setupCommentsList() {
        commentsList = new ArrayList<>();

        sendComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                adapter.notifyDataSetChanged();
                if (commentBox.getText().toString().isEmpty()){
                    DialogMessageDisplay.displayErrorMessage(MainActivity.this, "Λάθος", "Δεν επιτρέπεται κενό μήνυμα");
                }else{
                    commentsList.add("Ο Χρήστης έγραψε: " + commentBox.getText().toString());
                    commentBox.setText(null);
                }

            }
        });
        adapter = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, commentsList);
        adapter.notifyDataSetChanged();
        listView.setAdapter(adapter);

    }

    private void setupShareIntent() {
        sharePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.setType("application/image");
                emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "Η εικόνα τραβήκτηκε στο σημείο (" + latitude + ", " + longitude + ")");
                emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Νέο Μήνυμα");
                emailIntent.putExtra(Intent.EXTRA_STREAM, Uri.parse("file:///mnt/sdcard/Pictures/image.jpg"));
                startActivity(Intent.createChooser(emailIntent, "Στείλτε Μέσω"));
            }
        });
    }

    private void setupMaps() {
        mapButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, MapsActivity.class);
                intent.putExtra("lat", latitude);
                intent.putExtra("lon", longitude);
                startActivity(intent);
            }
        });
    }

    private void setupCameraPhoto() {
        takePicture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                file = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "image.jpg");
                uri = Uri.fromFile(file);
                intent.putExtra(MediaStore.EXTRA_OUTPUT, uri);
                startActivityForResult(intent, REQ_CODE);
            }
        });

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
                    pictureImage.setImageBitmap(bmp);
                    if (taken){
                        lat.setText("" + latitude);
                        lon.setText("" + longitude);
                    }
                    Toast.makeText(MainActivity.this, "Η Εικόνα αποθηκεύτηκε στο " + file.getAbsolutePath() + " με όνομα " + file.getName(), Toast.LENGTH_LONG).show();
                } catch (Exception e) {
                    throw new RuntimeException(e.getMessage());
                }
            } else if (resultCode == RESULT_CANCELED) {
                taken = false;
                Toast.makeText(MainActivity.this, "Ακυρώθηκε", Toast.LENGTH_SHORT).show();
            }
        }else if(requestCode == REQ_CODE_SPEECH_INPUT){
            if (resultCode == RESULT_OK && null != data) {
                ArrayList<String> result = data
                        .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
                adapter.notifyDataSetChanged();

                commentsList.add("Ο χρήστης είπε: " + result.get(0));
            }
        }
    }

    private boolean checkGpsSettings(Context context) {
        lm = (LocationManager) context.getSystemService(LOCATION_SERVICE);
        gpsLocation = appLocationService
                .getLocation(LocationManager.GPS_PROVIDER);
        if(gpsLocation != null) {
            latitude = gpsLocation.getLatitude();
            longitude = gpsLocation.getLongitude();
        }
        return lm.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    private void setupComponents() {
        pictureImage = (ImageView) findViewById(R.id.pictureImage);
        takePicture = (Button) findViewById(R.id.takePictureButton);
        sharePicture = (Button) findViewById(R.id.shareButton);
        mapButton = (Button) findViewById(R.id.mapButton);
        sendComment = (Button) findViewById(R.id.commentButton);
        listView = (ListView) findViewById(R.id.listView);
        commentBox = (EditText) findViewById(R.id.comment);
        lat = (TextView) findViewById(R.id.latitude);
        lon = (TextView) findViewById(R.id.longitude);
        micButton = (Button) findViewById(R.id.micButton);
        callButton  = (Button)findViewById(R.id.callButton);
    }






}
