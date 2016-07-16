package com.project.matrix.lelemetroga;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.enums.SnackbarType;

import adapters.DatabaseAdapter;
import sessions.SessionManager;


public class LoginActivity extends AppCompatActivity {

    private Toolbar tb;
    private Button login, sendToReg;
    private EditText uname, pass;
    private SessionManager sessionManager;
    private DatabaseAdapter databaseAdapter;
    private String username, userpassword;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        tb = (Toolbar)findViewById(R.id.toolBar);
        tb.setTitle("Λελέμετρο");
        tb.setLogo(R.mipmap.ic_launcher_2);
        setSupportActionBar(tb);
        setupFunctionality();
        login = (Button)findViewById(R.id.loginBtn);
        uname = (EditText)findViewById(R.id.loginUsername);
        pass = (EditText)findViewById(R.id.loginPassword);
        databaseAdapter = new DatabaseAdapter(LoginActivity.this);
        databaseAdapter = databaseAdapter.open();
        sessionManager = new SessionManager(LoginActivity.this);
        if (sessionManager.isLoggedIn()){
            Intent intent = new Intent(LoginActivity.this, ProfilePage.class);
            startActivity(intent);
            LoginActivity.this.finish();
        }
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (validationProcess()){
                    sessionManager.createLoginSession(username, "soldierProfile@lelemetro.com.gr");
                    Intent intent = new Intent(LoginActivity.this, ProfilePage.class);
                    intent.putExtra("username", username);
                    startActivity(intent);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                    LoginActivity.this.finish();
                }
            }
        });

    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        databaseAdapter.close();
    }

    private boolean validationProcess() {
        username = uname.getText().toString();
        userpassword = pass.getText().toString();
        if (username.isEmpty()){
            Snackbar.with(LoginActivity.this).type(SnackbarType.MULTI_LINE).backgroundDrawable(R.color.paralagianoixto).text("Παρακαλώ εισάγετε το username σας").show(this);
            return false;
        }else if (userpassword.isEmpty()){
            Snackbar.with(LoginActivity.this).type(SnackbarType.MULTI_LINE).backgroundDrawable(R.color.paralagianoixto).text("Παρακαλώ εισάγετε το password σας").show(this);
            return false;
        }else if (!databaseAdapter.getSinlgeEntry(username).equals(userpassword)){
            Snackbar.with(LoginActivity.this).type(SnackbarType.MULTI_LINE).backgroundDrawable(R.color.paralagianoixto).text("Ο κωδικός χρήστη είναι λανθασμένος.\nΠαρακαλώ προσπαθήστε ξανά").show(this);
            return false;
        }else {
            return true;
        }
    }

    private void setupFunctionality() {
        sendToReg = (Button)findViewById(R.id.sendToRegister);
        sendToReg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                LoginActivity.this.finish();
            }
        });
    }
}

