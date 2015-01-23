package com.example.kostas.accounts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.kostas.adapters.LoginDataBaseAdapter;
import com.example.kostas.ordertakingsystem.MyActivity;
import com.example.kostas.ordertakingsystem.R;
import com.example.kostas.sessions.SessionManager;

public class Login extends Activity {


    private SessionManager session;
    LoginDataBaseAdapter loginDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getActionBar().setTitle("Login");
        loginDataBaseAdapter = new LoginDataBaseAdapter(this);
        loginDataBaseAdapter = loginDataBaseAdapter.open();
        loginProcess();
    }

    private void loginProcess() {
        session = new SessionManager(getApplicationContext());
        if (session.isLoggedIn()) {
            Intent intent = new Intent(Login.this, MyActivity.class);
            startActivity(intent);
            finish();
        }
        final EditText username = (EditText) findViewById(R.id.login_username);
        final EditText password = (EditText) findViewById(R.id.login_password);
        Button buttonLogin = (Button) findViewById(R.id.btnlogin);
        Button buttonRegister = (Button) findViewById(R.id.btnregister);


        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = username.getText().toString();
                String userPass = password.getText().toString();
                if (userName.isEmpty() || userPass.isEmpty()) {
                    DialogMessageDisplay.displayMessage(Login.this, "Warning", "Both fields are required in order to login");
                } else {

                    String storedPassword = loginDataBaseAdapter.getSinlgeEntry(userName);
                    if (userPass.equals(storedPassword)) {
                        session.createLoginSession(userName, "waiterx@gmail.com");
                        Intent intent = new Intent(Login.this, MyActivity.class);
                        startActivity(intent);
                        finish();
                    }else{
                        DialogMessageDisplay.displayMessage(Login.this, "Error", "Username or Password do not match");
                    }


                }
            }
        });

        buttonRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginDataBaseAdapter.close();
    }
}
