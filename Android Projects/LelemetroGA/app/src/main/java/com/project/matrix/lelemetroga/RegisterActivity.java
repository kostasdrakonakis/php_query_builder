package com.project.matrix.lelemetroga;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Spinner;
import android.widget.Toast;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.enums.SnackbarType;

import java.util.ArrayList;
import java.util.List;

import adapters.DatabaseAdapter;
import adapters.MyAdapterEnopliDunami;
import adapters.MyAdapterOpla;
import dialogs.DatePickerDialog;

public class RegisterActivity extends AppCompatActivity {

    private Toolbar tb;
    private Spinner spinnerSwmata, spinnerEnopliDunami;
    private List<Swmata> listaSwmata, listaEnopliDunami;
    private EditText uname, pass, katataksi, apolusi, repeatPass, seira, esso;
    private BaseAdapter adapter, adapterEnopliDunami;
    private DatabaseAdapter dbAdapter;
    private boolean oploSelected = false;
    private LinearLayout ll;
    private String username, password, repeat, kat, apol, oplo, seiraTxt, essoTxt, enopliDunami;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        spinnerSwmata = (Spinner)findViewById(R.id.spinner3);
        spinnerEnopliDunami = (Spinner)findViewById(R.id.spinnerForce);
        tb = (Toolbar)findViewById(R.id.toolBar);
        listaSwmata = new ArrayList<Swmata>();
        listaEnopliDunami = new ArrayList<Swmata>();
        tb.setLogo(R.mipmap.ic_launcher_2);
        tb.setTitle("Εγγραφή");
        setSupportActionBar(tb);
        populateSpinnerSwmata();
        populateSpinnerEnopliDunami();
        adapter = new MyAdapterOpla(RegisterActivity.this, listaSwmata);
        adapterEnopliDunami = new MyAdapterEnopliDunami(RegisterActivity.this, listaEnopliDunami);
        spinnerSwmata.setAdapter(adapter);
        spinnerEnopliDunami.setAdapter(adapterEnopliDunami);
        Button register = (Button)findViewById(R.id.registerBtn);
        uname = (EditText)findViewById(R.id.registerUsername);
        pass = (EditText)findViewById(R.id.registerPassword);
        repeatPass = (EditText)findViewById(R.id.registerReapeatPassword);
        seira = (EditText)findViewById(R.id.seira);
        esso = (EditText)findViewById(R.id.esso);
        checkEnopliDunami();
        ll = (LinearLayout)findViewById(R.id.layoutSwma);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkValidity()) {
                    registerIntoDB();
                }
            }


        });
        spinnerSwmata.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                oplo = listaSwmata.get(position).getName();
                oploSelected = true;
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void checkEnopliDunami() {
        spinnerEnopliDunami.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                enopliDunami = listaEnopliDunami.get(position).getName();
                if(spinnerEnopliDunami.getSelectedItemPosition() == 0){
                    if (ll.getVisibility() == View.GONE){
                        ll.setVisibility(View.VISIBLE);
                    }
                    oploSelected = true;

                }else {
                    if (ll.getVisibility() == View.VISIBLE){
                        ll.setVisibility(View.GONE);
                    }
                    oploSelected = false;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

    }

    private void registerIntoDB() {
        dbAdapter = new DatabaseAdapter(RegisterActivity.this);
        dbAdapter = dbAdapter.open();
        if (oploSelected){
            dbAdapter.insertEntry(username, password, kat, apol, seiraTxt, essoTxt, enopliDunami, oplo);
        }else {
            oplo = "";
            dbAdapter.insertEntry(username, password, kat, apol, seiraTxt, essoTxt, enopliDunami, oplo);
        }

        Toast.makeText(RegisterActivity.this, "Ο Λογαριασμός Διμιουργήθηκε επιτυχώς", Toast.LENGTH_SHORT).show();
        Log.d("To name sas: ", username);
        Log.d("To password sas: ", password);
        Log.d("I imerominia Katataksis: ", kat);
        Log.d("I imerominia Apolusis: ", apol);
        Log.d("I seira sas: ", seiraTxt);
        Log.d("I ESSO sas: ", essoTxt);
        Log.d("I Enopli Dunami sas: ", enopliDunami);
        Log.d("To oplo sas: ", oplo);
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_left);
        RegisterActivity.this.finish();
    }

    private boolean checkValidity() {
        username = uname.getText().toString();
        password = pass.getText().toString();
        repeat = repeatPass.getText().toString();
        kat = katataksi.getText().toString();
        apol = apolusi.getText().toString();
        seiraTxt = seira.getText().toString();
        essoTxt = esso.getText().toString();
        if (username.isEmpty()){
            //Toast.makeText(RegisterActivity.this, "Παρακαλώ εισάγετε το ονοματεπώνυμο σας", Toast.LENGTH_SHORT).show();
            Snackbar.with(RegisterActivity.this).type(SnackbarType.MULTI_LINE).backgroundDrawable(R.color.paralagianoixto).text("Παρακαλώ εισάγετε το ονοματεπώνυμο σας").show(this);
            return false;
        }else if (password.isEmpty()){
            //Toast.makeText(RegisterActivity.this, "Παρακαλώ εισάγετε εναν κωδικό πρόσβασης", Toast.LENGTH_SHORT).show();
            Snackbar.with(RegisterActivity.this).type(SnackbarType.MULTI_LINE).backgroundDrawable(R.color.paralagianoixto).text("Παρακαλώ εισάγετε εναν κωδικό πρόσβασης").show(this);
            return false;
        }else if (repeat.isEmpty()){
            //Toast.makeText(RegisterActivity.this, "Παρακαλώ επαναλάβετε τον κωδικό πρόσβασης σας", Toast.LENGTH_SHORT).show();
            Snackbar.with(RegisterActivity.this).type(SnackbarType.MULTI_LINE).backgroundDrawable(R.color.paralagianoixto).text("Παρακαλώ επαναλάβετε τον κωδικό πρόσβασης σας").show(this);
            return false;
        }else if (kat.isEmpty()){
            //Toast.makeText(RegisterActivity.this, "Παρακαλώ εισάγετε ημερομηνία κατάταξης", Toast.LENGTH_SHORT).show();
            Snackbar.with(RegisterActivity.this).type(SnackbarType.MULTI_LINE).backgroundDrawable(R.color.paralagianoixto).text("Παρακαλώ εισάγετε ημερομηνία κατάταξης").show(this);
            return false;
        }else if (apol.isEmpty()){
            //Toast.makeText(RegisterActivity.this, "Παρακαλώ εισάγετε ημερομηνία απόλυσης", Toast.LENGTH_SHORT).show();
            Snackbar.with(RegisterActivity.this).type(SnackbarType.MULTI_LINE).backgroundDrawable(R.color.paralagianoixto).text("Παρακαλώ εισάγετε ημερομηνία απόλυσης").show(this);
            return false;
        }else if (seiraTxt.isEmpty()){
            //Toast.makeText(RegisterActivity.this, "Παρακαλώ εισάγετε τη σειρά σας", Toast.LENGTH_SHORT).show();
            Snackbar.with(RegisterActivity.this).type(SnackbarType.MULTI_LINE).backgroundDrawable(R.color.paralagianoixto).text("Παρακαλώ εισάγετε τη σειρά σας").show(this);
            return false;
        }else if (essoTxt.isEmpty()){
            //Toast.makeText(RegisterActivity.this, "Παρακαλώ εισάγετε την ΕΣΣΟ σας", Toast.LENGTH_SHORT).show();
            Snackbar.with(RegisterActivity.this).type(SnackbarType.MULTI_LINE).backgroundDrawable(R.color.paralagianoixto).text("Παρακαλώ εισάγετε την ΕΣΣΟ σας").show(this);
            return false;
        }else {
            if (!password.equals(repeat)){
                //Toast.makeText(RegisterActivity.this, "Οι κωδικοί δεν ταιριάζουν", Toast.LENGTH_SHORT).show();
                Snackbar.with(RegisterActivity.this).type(SnackbarType.MULTI_LINE).backgroundDrawable(R.color.paralagianoixto).text("Οι κωδικοί δεν ταιριάζουν").show(this);
                return false;
            }else {
                return true;
            }

        }
    }

    private void populateSpinnerSwmata() {
        listaSwmata.add(new Swmata(R.drawable.peziko, "Πεζικό(ΠΖ)"));
        listaSwmata.add(new Swmata(R.drawable.eidikesdinameis, "Ειδικές Δυνάμεις(ΕΔ)"));
        listaSwmata.add(new Swmata(R.drawable.tethwrakismena, "Τεθωρακισμένα(ΤΘ)"));
        listaSwmata.add(new Swmata(R.drawable.piroboliko, "Πυροβολικό(ΠΒ)"));
        listaSwmata.add(new Swmata(R.drawable.mixaniko, "Μηχανικό(ΜΧ)"));
        listaSwmata.add(new Swmata(R.drawable.aeroporia, "Αεροπορία Στρατού(ΑΣ)"));
        listaSwmata.add(new Swmata(R.drawable.texniko, "Τεχνικό(ΤΧ)"));
        listaSwmata.add(new Swmata(R.drawable.efodiasmou, "Εφοδιασμού Μεταφορών(ΕΜ)"));
        listaSwmata.add(new Swmata(R.drawable.ylikou, "Υλικού Πολέμου(ΥΠ)"));
        listaSwmata.add(new Swmata(R.drawable.ygeionomiko, "Υγειονομικό(ΥΓ)"));
        listaSwmata.add(new Swmata(R.drawable.diabibaseis, "Διαβιβάσεων(ΔΒ)"));
        listaSwmata.add(new Swmata(R.drawable.stratonomia, "Στρατονομία(ΣΝ)"));
    }


    private void populateSpinnerEnopliDunami(){
        listaEnopliDunami.add(new Swmata(R.drawable.ic_ges, "Στρατός Ξηράς"));
        listaEnopliDunami.add(new Swmata(R.drawable.ic_gen, "Πολεμικό Ναυτικό"));
        listaEnopliDunami.add(new Swmata(R.drawable.ic_gea, "Πολεμική Αεροπορία"));
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_left);
        Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
        startActivity(intent);
        RegisterActivity.this.finish();
    }

    @Override
    protected void onStart() {
        super.onStart();
        katataksi = (EditText)findViewById(R.id.dateKat);
        katataksi.setInputType(InputType.TYPE_NULL);
        katataksi.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {

                if (hasFocus) {

                    DatePickerDialog dialog = new DatePickerDialog(v, RegisterActivity.this);
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    dialog.show(fragmentTransaction, "Date Pick");
                    katataksi.setTextIsSelectable(false);
                }

            }
        });


        apolusi = (EditText)findViewById(R.id.dateApol);
        apolusi.setInputType(InputType.TYPE_NULL);
        apolusi.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    DatePickerDialog dialog = new DatePickerDialog(v, RegisterActivity.this);
                    FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
                    dialog.show(fragmentTransaction, "Pick Date");
                    apolusi.setTextIsSelectable(false);
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (dbAdapter != null){
            dbAdapter.close();
        }
    }
}
