package cart;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

import com.order.app.order.R;

import functions.AppConstant;

public class BeersLayoutActivity extends AppCompatActivity {

    private EditText quantity, sxolia;
    private CheckBox big, small, bottle, draught;
    private Spinner beerCompanions;
    private String[] beerItems;
    private Button plus, minus, cart;
    private int quantityNumberFinal;
    private String quantityPreference, price, comment, name, table, image, servitoros_id, magazi_id, sizePreference, wayPreference;
    private Double priceCalculated;
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_beers_layout);
        setupToolBar();
        setupCheckBoxes();
        setupSpinner();
        checkQuantity();
    }

    private void setupSpinner() {
        beerItems = getResources().getStringArray(R.array.beer_companions);
        beerCompanions = (Spinner)findViewById(R.id.beer_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(BeersLayoutActivity.this, R.layout.spinner_flavor_single_line, beerItems);
        beerCompanions.setAdapter(adapter);
    }

    private void setupCheckBoxes() {
        big = (CheckBox)findViewById(R.id.bigCheckBox);
        small = (CheckBox)findViewById(R.id.smallCheckBox);
        bottle = (CheckBox)findViewById(R.id.bottleCheckBox);
        draught = (CheckBox)findViewById(R.id.draughtCheckBox);

        big.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (big.isChecked()){
                    small.setEnabled(false);
                    wayPreference = big.getText().toString();
                }else{
                    small.setEnabled(true);
                    wayPreference = null;
                }
            }
        });
        small.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (small.isChecked()){
                    big.setEnabled(false);
                    wayPreference = small.getText().toString();
                }else{
                    big.setEnabled(true);
                    wayPreference = null;
                }
            }
        });

        bottle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (bottle.isChecked()){
                    draught.setEnabled(false);
                    sizePreference = bottle.getText().toString();
                }else{
                    draught.setEnabled(true);
                    sizePreference = null;
                }
            }
        });
        draught.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (draught.isChecked()){
                    bottle.setEnabled(false);
                    sizePreference = draught.getText().toString();
                }else{
                    bottle.setEnabled(true);
                    sizePreference = null;
                }
            }
        });
    }

    private void checkQuantity() {
        quantity = (EditText) findViewById(R.id.quantityEditTextBeer);
        plus = (Button) findViewById(R.id.buttonBeerPlus);
        minus = (Button) findViewById(R.id.buttonBeerMinus);
        sxolia = (EditText)findViewById(R.id.editTextBeerComments);
        cart = (Button)findViewById(R.id.cartButtonBeer);

        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addTxt = quantity.getText().toString();
                int add = Integer.parseInt(addTxt);
                quantity.setText(String.valueOf(add + 1));
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String minusTxt = quantity.getText().toString();
                int minus = Integer.parseInt(minusTxt);
                if (minus > 0) {
                    quantity.setText(String.valueOf(minus - 1));
                }
            }
        });



        quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String quanText = quantity.getText().toString();
                int numberQuant = Integer.parseInt(quanText);
                if (numberQuant > 0) {
                    cart.setEnabled(true);
                    cart.setBackgroundColor(Color.parseColor(AppConstant.ENABLED_BUTTON_COLOR));
                } else {
                    cart.setEnabled(false);
                    cart.setBackgroundColor(Color.parseColor(AppConstant.DISABLED_BUTTON_COLOR));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantityPreference = quantity.getText().toString();
                quantityNumberFinal = Integer.parseInt(quantityPreference);
                priceCalculated = Double.parseDouble(price) * quantityNumberFinal;
                comment = sxolia.getText().toString();
                if (comment == null) {
                    comment = " ";
                }
            }
        });
    }

    private void setupToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        name = getIntent().getStringExtra(AppConstant.BEER_NAME);
        price = getIntent().getStringExtra(AppConstant.BEER_PRICE);
        table = getIntent().getStringExtra(AppConstant.TABLE_INTENT_ID);
        image = getIntent().getStringExtra(AppConstant.BEER_IMAGE);
        servitoros_id = getIntent().getStringExtra(AppConstant.WAITER_INTENT_ID);
        magazi_id = getIntent().getStringExtra(AppConstant.COMPANY_INTENT_ID);
        toolbar.setTitle(name + " - " + getString(R.string.price) + " " + price);
        toolbar.setSubtitle(getString(R.string.table_id) + table);
        setSupportActionBar(toolbar);
    }

}
