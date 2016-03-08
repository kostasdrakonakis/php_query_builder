package com.library.quizgame;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class OptionsActivity extends AppCompatActivity {

    private EditText lifes;
    private Button plus, minus;
    private LinearLayout languageLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);
        setupLanguage();
        checkLifes();
    }

    private void setupLanguage() {
        languageLayout = (LinearLayout)findViewById(R.id.languageLayout);
        languageLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(OptionsActivity.this, "Hi", Toast.LENGTH_SHORT).show();
            }
        });
    }

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
