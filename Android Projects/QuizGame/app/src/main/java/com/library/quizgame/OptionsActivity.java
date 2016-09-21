package com.library.quizgame;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Switch;
import android.widget.TextView;

import com.nispok.snackbar.enums.SnackbarType;

import constants.Constants;
import constants.StringGenerator;
import dialogs.DialogMessageDisplay;

public class OptionsActivity extends AppCompatActivity {

    private EditText lifes, subject, message;
    private Button plus, minus;
    private LinearLayout languageLayout, aboutLayout, contactLayout;
    private Toolbar toolBar;
    private AlertDialog.Builder languageDialog, contactDialog;
    private String[] languages;
    private TextView displayLanguage;
    private String langText, lifesText, langFromPrefs, lifesFromPrefs, loadLang, subjectTXT, messageTXT;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;
    private View contactView;
    private Switch helpSwitch;
    private Intent sendIntent, callIntent;
    private Uri number;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadLangFromPrefs();
        StringGenerator.setLocale(loadLang, OptionsActivity.this);
        setContentView(R.layout.activity_options);
        displayLanguage = (TextView)findViewById(R.id.displayLanguageText);
        setupToolbar();
        setupLanguage();
        checkLifes();
        setupMenu();
        loadUserPrefs();

    }

    private void loadLangFromPrefs(){
        sharedPreferences = getSharedPreferences(Constants.PREFERENCES_FILE, MODE_PRIVATE);
        loadLang = sharedPreferences.getString(Constants.LANGUAGE_PREFS_FILE, getString(R.string.ta_to_select));
    }

    /**
     * Μέθοδος για να φόρτώσουμε τις ρυθμίσεις του χρήστη απο το αρχείο μας.
     */
    private void loadUserPrefs() {
        sharedPreferences = getSharedPreferences(Constants.PREFERENCES_FILE, MODE_PRIVATE);
        langFromPrefs = sharedPreferences.getString(Constants.LANGUAGE_PREFS_FILE, getString(R.string.ta_to_select));
        lifesFromPrefs = sharedPreferences.getString(Constants.LIFES_PREFS_FILE, String.valueOf(0));
        langFromPrefs = StringGenerator.revertLanguageCode(langFromPrefs, OptionsActivity.this);
        displayLanguage.setText(langFromPrefs);
        lifes.setText(lifesFromPrefs);
    }

    /**
     * Κάνουμε setup το menu στην Toolbar μας.
     */
    private void setupMenu() {
        toolBar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.saveOptions){
                    commitChangesToPrefs();
                    StringGenerator.setLocale(langText, OptionsActivity.this);
                    Intent refresh = new Intent(OptionsActivity.this, StartActivity.class);
                    refresh.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivity(refresh);
                    OptionsActivity.this.finish();
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_options, menu);
        return super.onCreateOptionsMenu(menu);

    }

    /**
     * Μέθοδος για να φτιαξουμε το Toolbar μας.
     */
    private void setupToolbar() {
        toolBar = (Toolbar)findViewById(R.id.toolBar);
        toolBar.setTitle(getString(R.string.options));
        toolBar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolBar);
    }

    /**
     * Μέθοδος για να αποθηκεύσουμε τις ρυθμίσεις που θα επιλέξει ο χρήστης
     * στην εφαρμογή μας. Η αποθήκευση γίνεται με .xml και το αρχείο μπορεί
     * να το βρεί κάποιος με root πρόσβαση στο data/data/com.library.quizgame/shared_prefs/filename.xml
     */
    private void commitChangesToPrefs() {
        lifesText = lifes.getText().toString();
        langText = StringGenerator.checkLanguageCode(langText);
        sharedPreferences = getSharedPreferences(Constants.PREFERENCES_FILE, MODE_PRIVATE);
        editor = sharedPreferences.edit();
        editor.putString(Constants.LANGUAGE_PREFS_FILE, langText);
        editor.putString(Constants.LIFES_PREFS_FILE, lifesText);
        editor.apply();
        StringGenerator.showToast(OptionsActivity.this, getString(R.string.options_saved));
    }

    /**
     * Μέθοδος η οποία μας εμφανίζει το διάλογο για να επιλέξουμε γλώσσα και τον διάλογο About
     */
    private void setupLanguage() {
        languageLayout = (LinearLayout)findViewById(R.id.languageLayout);
        languageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                displayLanguageDialog().show();
            }
        });
        aboutLayout = (LinearLayout)findViewById(R.id.aboutLayout);
        aboutLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogMessageDisplay.displayInfoMessage(OptionsActivity.this, getString(R.string.aboutMe), getString(R.string.messageAboutDialog));
            }
        });
        contactLayout = (LinearLayout)findViewById(R.id.contactLayout);
        contactLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                contactIt();
            }
        });
    }

    /**
     * Δίνουμε στον χρήστη την δυνατότητα είτε να μας στείλει μήνυμα
     * ή να καλέσει απευθείας στην Βιβλιοθήκη.
     */
    private void contactIt() {
        contactDialog = new AlertDialog.Builder(this);
        contactView = getLayoutInflater().inflate(R.layout.contact_dialog, (ViewGroup) findViewById(R.id.helpRootLayout));
        subject = (EditText)contactView.findViewById(R.id.subject);
        message = (EditText)contactView.findViewById(R.id.TextMessage);
        helpSwitch = (Switch)contactView.findViewById(R.id.helpSwitch);

        contactLayout = (LinearLayout)contactView.findViewById(R.id.messageLayout);
        contactDialog.setTitle(getString(R.string.help))
                .setMessage(getString(R.string.help_dialog_message)+"\n" + Constants.TEL)
                .setView(contactView)
                .setPositiveButton(getString(R.string.send), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        subjectTXT = subject.getText().toString();
                        messageTXT = message.getText().toString();
                        if (subjectTXT.isEmpty() && messageTXT.isEmpty()){
                            StringGenerator.showSnackMessage(SnackbarType.SINGLE_LINE, getString(R.string.subject_or_message_empty),OptionsActivity.this, Color.parseColor(Constants.ENABLED_BUTTON_COLOR), OptionsActivity.this);
                        }else if (subjectTXT.isEmpty()){
                            StringGenerator.showSnackMessage(SnackbarType.SINGLE_LINE, getString(R.string.subject_empty), OptionsActivity.this, Color.parseColor(Constants.ENABLED_BUTTON_COLOR), OptionsActivity.this);
                        }else if (messageTXT.isEmpty()){
                            StringGenerator.showSnackMessage(SnackbarType.SINGLE_LINE, getString(R.string.message_empty), OptionsActivity.this, Color.parseColor(Constants.ENABLED_BUTTON_COLOR), OptionsActivity.this);

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
                            if (ContextCompat.checkSelfPermission(OptionsActivity.this, Manifest.permission.CALL_PHONE)!= PackageManager.PERMISSION_GRANTED) {
                                if (ActivityCompat.shouldShowRequestPermissionRationale(OptionsActivity.this,
                                        Manifest.permission.CALL_PHONE)) {
                                    StringGenerator.showSnackMessage(
                                            SnackbarType.MULTI_LINE,
                                            "In order to get in touch with the Support Team you have to accept permission",
                                            OptionsActivity.this,
                                            Color.parseColor(Constants.ENABLED_BUTTON_COLOR),
                                            OptionsActivity.this);
                                } else {
                                    ActivityCompat.requestPermissions(OptionsActivity.this,
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

        final AlertDialog dialog = contactDialog.show();
        dialog.show();
        Button btn = dialog.getButton(AlertDialog.BUTTON_POSITIVE);

        btn.setEnabled(false);
        Button cancel = dialog.getButton(AlertDialog.BUTTON_NEUTRAL);
        Button call = dialog.getButton(AlertDialog.BUTTON_NEGATIVE);
        cancel.setTextColor(getResources().getColor(R.color.colorPrimary));
        call.setTextColor(getResources().getColor(R.color.colorPrimary));
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
                        btn.setTextColor(getResources().getColor(R.color.colorPrimary));
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


    /**
     * Φτιάχνουμε τον διάλογο που δείχνουμε στον χρήστη για να επιλέξει γλώσσα εμφάνισης
     * @return Returns an AlertDialog
     */
    private AlertDialog displayLanguageDialog() {
        languages = new String[]{getString(R.string.grlang), getString(R.string.enlang)};

        languageDialog = new AlertDialog.Builder(OptionsActivity.this);
        languageDialog.setTitle(getString(R.string.languageTitle));
        languageDialog.setSingleChoiceItems(languages, 0, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                langText = languages[which].toString();
                displayLanguage.setText(languages[which]);
                dialog.dismiss();
            }
        });
        return languageDialog.create();
    }

    /**
     * Μέθοδος με την οποία σιγουρευόμαστε οτι ο χρήστης δεν μπορεί να επιλέξει αρνητικό αριθμό
     * για το εύρος ζωών στο παιχνίδι και δεν μπορεί να επιλέξει πάνω απο 3.
     */
    private void checkLifes() {
            lifes = (EditText) findViewById(R.id.lifeEdittext);
            plus = (Button) findViewById(R.id.buttonPlus);
            minus = (Button) findViewById(R.id.buttonMinus);
            plus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String addTxt = lifes.getText().toString();
                    int add = Integer.parseInt(addTxt);
                    if (add <3 ){
                        lifes.setText(String.valueOf(add + 1));
                    }
                }
            });
            minus.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    String minusTxt = lifes.getText().toString();
                    int minus = Integer.parseInt(minusTxt);
                    if (minus > 0) {
                        lifes.setText(String.valueOf(minus - 1));
                    }
                }
            });
    }
}
