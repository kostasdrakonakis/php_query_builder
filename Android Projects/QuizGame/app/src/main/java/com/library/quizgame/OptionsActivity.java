package com.library.quizgame;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import constants.Constants;
import constants.StringGenerator;
import dialogs.DialogMessageDisplay;

public class OptionsActivity extends AppCompatActivity {

    private EditText lifes;
    private Button plus, minus;
    private LinearLayout languageLayout, aboutLayout;
    private Toolbar toolBar;
    private AlertDialog.Builder languageDialog;
    private String[] languages;
    private TextView displayLanguage;
    private String langText, lifesText, langFromPrefs, lifesFromPrefs, loadLang;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.Editor editor;


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
        toolBar.setLogo(R.drawable.ic_settings);
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
