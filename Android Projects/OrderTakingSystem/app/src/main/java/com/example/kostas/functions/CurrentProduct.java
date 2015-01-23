package com.example.kostas.functions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kostas.accounts.DialogMessageDisplay;
import com.example.kostas.ordertakingsystem.R;

public class CurrentProduct extends Activity {
    private String sugar, sugarextra, name, dose;
    private double price;
    private int k = 0;
    boolean checkSugar;
    private EditText commentsText;
    private ImageView image;
    private TextView quantity;
    private CheckBox nosugar, medium, sweet, verysweet, blacksugar, zaxarini, small, mediumDose, large;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.current_item);
        Intent i = getIntent();
        name = i.getStringExtra("name");
        price = i.getDoubleExtra("price", 0.00);
        getActionBar().setTitle(name);

        setupQuantity();
        setupCheckBoxes();
        setupCart();
        setupComments();
    }

    private void setupComments() {
        commentsText = (EditText)findViewById(R.id.coffee_comments);
    }

    private void setupCart() {
        Button cart = (Button)findViewById(R.id.cart);
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String quantityCheck = quantity.getText().toString();
                if(quantityCheck.matches(String.valueOf(k))){
                    quantity.requestFocus();
                    quantity.setError("You have to select quantity!");
                }else if(!nosugar.isChecked() && !medium.isChecked() && !sweet.isChecked() && !verysweet.isChecked() && !blacksugar.isChecked() && !zaxarini.isChecked()){
                    DialogMessageDisplay.displayMessage(CurrentProduct.this, "Error", "You have to select sugar amount");
                }else if(!small.isChecked() && !mediumDose.isChecked() && !large.isChecked()){
                    DialogMessageDisplay.displayMessage(CurrentProduct.this, "Error", "You have to select the coffee dose");
                }else{
                    Intent intent = new Intent(CurrentProduct.this, Cart.class);

                    if(name != null){
                        intent.putExtra("coffeeName", name);
                    }
                    intent.putExtra("coffeePrice", price);
                    if(quantity != null){
                        intent.putExtra("quantity", quantity.getText().toString());
                    }
                    if(sugar != null){
                        intent.putExtra("sugarAmount", sugar);
                    }
                    intent.putExtra("checkNoSugar", checkSugar);
                    if(sugarextra != null){
                        intent.putExtra("sugarFlavor", sugarextra);
                    }
                    intent.putExtra("dose", dose);
                    intent.putExtra("commentsText", commentsText.getText().toString());
                    startActivity(intent);
                    CurrentProduct.this.finish();
                }
            }
        });
    }

    private void setupCheckBoxes() {
        nosugar = (CheckBox)findViewById(R.id.nosugar);
        medium = (CheckBox)findViewById(R.id.medium);
        sweet = (CheckBox)findViewById(R.id.sweet);
        blacksugar = (CheckBox)findViewById(R.id.blacksugar);
        zaxarini = (CheckBox)findViewById(R.id.zaxarini);
        verysweet = (CheckBox)findViewById(R.id.verysweet);
        small = (CheckBox)findViewById(R.id.small);
        mediumDose = (CheckBox)findViewById(R.id.mediumDose);
        large = (CheckBox)findViewById(R.id.large);
        nosugar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(nosugar.isChecked()){
                    sugar = nosugar.getText().toString();
                    checkSugar = true;
                    medium.setEnabled(false);
                    sweet.setEnabled(false);
                    blacksugar.setEnabled(false);
                    zaxarini.setEnabled(false);
                    verysweet.setEnabled(false);
                }else{
                    sugar = null;
                    checkSugar = false;
                    medium.setEnabled(true);
                    sweet.setEnabled(true);
                    blacksugar.setEnabled(true);
                    zaxarini.setEnabled(true);
                    verysweet.setEnabled(true);
                }
            }
        });
        medium.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(medium.isChecked()){
                    sugar = medium.getText().toString();
                    nosugar.setEnabled(false);
                    sweet.setEnabled(false);
                    verysweet.setEnabled(false);
                }else{
                    sugar = null;
                    nosugar.setEnabled(true);
                    sweet.setEnabled(true);
                    verysweet.setEnabled(true);
                }
            }
        });
        sweet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(sweet.isChecked()){
                    sugar = sweet.getText().toString();
                    nosugar.setEnabled(false);
                    medium.setEnabled(false);
                    verysweet.setEnabled(false);
                }else{
                    sugar = null;
                    nosugar.setEnabled(true);
                    medium.setEnabled(true);
                    verysweet.setEnabled(true);
                }
            }
        });
        verysweet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(verysweet.isChecked()){
                    sugar = verysweet.getText().toString();
                    nosugar.setEnabled(false);
                    medium.setEnabled(false);
                    sweet.setEnabled(false);
                }else{
                    sugar = null;
                    nosugar.setEnabled(true);
                    medium.setEnabled(true);
                    sweet.setEnabled(true);
                }
            }
        });
        blacksugar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(blacksugar.isChecked()){
                    sugarextra = blacksugar.getText().toString();
                    nosugar.setEnabled(false);
                    zaxarini.setEnabled(false);
                }else{
                    sugarextra = null;
                    zaxarini.setEnabled(true);
                    if(!medium.isChecked() && !sweet.isChecked() && !verysweet.isChecked()){
                        nosugar.setEnabled(true);
                    }
                }
            }
        });
        zaxarini.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(zaxarini.isChecked()){
                    sugarextra = zaxarini.getText().toString();
                    nosugar.setEnabled(false);
                    blacksugar.setEnabled(false);
                }else{
                    sugarextra = null;
                    blacksugar.setEnabled(true);
                    if(!medium.isChecked() && !sweet.isChecked() && !verysweet.isChecked()){
                        nosugar.setEnabled(true);
                    }
                }
            }
        });
        small.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(small.isChecked()){
                    dose = small.getText().toString();
                    mediumDose.setEnabled(false);
                    large.setEnabled(false);
                }else{
                    mediumDose.setEnabled(true);
                    large.setEnabled(true);
                }
            }
        });
        mediumDose.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(mediumDose.isChecked()){
                    dose = mediumDose.getText().toString();
                    small.setEnabled(false);
                    large.setEnabled(false);
                }else{
                    small.setEnabled(true);
                    large.setEnabled(true);
                }
            }
        });
        large.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(large.isChecked()){
                    dose = large.getText().toString();
                    mediumDose.setEnabled(false);
                    small.setEnabled(false);
                }else{
                    mediumDose.setEnabled(true);
                    small.setEnabled(true);
                }
            }
        });
    }

    private void setupQuantity() {
        quantity = (TextView)findViewById(R.id.quantity_value);
        Button plus = (Button)findViewById(R.id.btnplus);
        Button minus = (Button)findViewById(R.id.btnminus);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(quantity.getText() != null || quantity.equals(String.valueOf(k))){
                    int x = Integer.parseInt(quantity.getText().toString()) + 1;
                    quantity.setText("" + x);
                    quantity.setError(null);
                }

            }
        });

        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int y = Integer.parseInt(quantity.getText().toString());
                if(y > 0){
                    int z = y-1;
                    quantity.setText("" + z);
                }
            }
        });
    }

}
