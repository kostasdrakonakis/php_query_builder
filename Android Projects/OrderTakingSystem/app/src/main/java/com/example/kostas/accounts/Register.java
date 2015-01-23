package com.example.kostas.accounts;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.kostas.adapters.LoginDataBaseAdapter;
import com.example.kostas.ordertakingsystem.R;

public class Register extends Activity {

    LoginDataBaseAdapter loginDataBaseAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        loginDataBaseAdapter=new LoginDataBaseAdapter(this);
        loginDataBaseAdapter=loginDataBaseAdapter.open();
        registerProcess();
    }

    private void registerProcess() {
        final EditText userName = (EditText)findViewById(R.id.register_username);
        final EditText userPass = (EditText)findViewById(R.id.register_password);
        final EditText userRetypePass = (EditText)findViewById(R.id.register_retype_password);
        Button register = (Button)findViewById(R.id.register_button);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = userName.getText().toString();
                String pass = userPass.getText().toString();
                String retypePass = userRetypePass.getText().toString();

                if(username.equals("")||pass.equals("")||retypePass.equals("")){
                    DialogMessageDisplay.displayMessage(Register.this, "Error", "All Fields are required");
                    return;
                }

                if(!pass.equals(retypePass)){
                    DialogMessageDisplay.displayMessage(Register.this, "Error", "Passwords do not match");
                    return;
                }else{
                    loginDataBaseAdapter.insertEntry(username, pass);
                    Toast.makeText(getApplicationContext(), "Account Created Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Register.this, Login.class);
                    startActivity(intent);
                    Register.this.finish();
                }
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        loginDataBaseAdapter.close();
    }
}
