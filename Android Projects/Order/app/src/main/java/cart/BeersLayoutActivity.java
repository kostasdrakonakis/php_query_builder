package cart;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.nispok.snackbar.enums.SnackbarType;
import com.order.app.order.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

import functions.Constants;
import functions.StringGenerator;

public class BeersLayoutActivity extends AppCompatActivity {

    private EditText quantity, sxolia;
    private CheckBox big, small, bottle, draught;
    private Spinner beerCompanions;
    private String[] beerItems;
    private Button plus, minus, cart;
    private int quantityNumberFinal;
    private String quantityPreference, price, comment, name, table, image, servitoros_id, magazi_id, sizePreference, wayPreference, choicePreference;
    private Double priceCalculated;
    private Toolbar toolbar;
    private HttpURLConnection urlConnection;
    private URL url;
    private OutputStream outputStream;
    private BufferedWriter bufferedWriter;
    private InputStream inputStream;
    private ProgressDialog pDialog;
    private ArrayList<NameValuePair> nameValuePairs;
    private MyInsertDataTask task;

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

        beerCompanions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                choicePreference = beerItems[position].toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void setupCheckBoxes() {
        big = (CheckBox)findViewById(R.id.bigCheckBox);
        small = (CheckBox)findViewById(R.id.smallCheckBox);
        bottle = (CheckBox)findViewById(R.id.bottleCheckBox);
        draught = (CheckBox)findViewById(R.id.draughtCheckBox);

        big.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (big.isChecked()) {
                    small.setEnabled(false);
                    wayPreference = big.getText().toString();
                } else {
                    small.setEnabled(true);
                    wayPreference = null;
                }
            }
        });
        small.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (small.isChecked()) {
                    big.setEnabled(false);
                    wayPreference = small.getText().toString();
                } else {
                    big.setEnabled(true);
                    wayPreference = null;
                }
            }
        });

        bottle.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (bottle.isChecked()) {
                    draught.setEnabled(false);
                    sizePreference = bottle.getText().toString();
                } else {
                    draught.setEnabled(true);
                    sizePreference = null;
                }
            }
        });
        draught.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (draught.isChecked()) {
                    bottle.setEnabled(false);
                    sizePreference = draught.getText().toString();
                } else {
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
                    cart.setBackgroundColor(Color.parseColor(Constants.ENABLED_BUTTON_COLOR));
                } else {
                    cart.setEnabled(false);
                    cart.setBackgroundColor(Color.parseColor(Constants.DISABLED_BUTTON_COLOR));
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

                if (!bottle.isChecked() && !draught.isChecked()){
                    StringGenerator.showSnackMessage(SnackbarType.MULTI_LINE, getString(R.string.way_preference), BeersLayoutActivity.this, Color.parseColor(Constants.ENABLED_BUTTON_COLOR), BeersLayoutActivity.this);
                }else if(!small.isChecked() && !big.isChecked()){
                    StringGenerator.showSnackMessage(SnackbarType.MULTI_LINE, getString(R.string.size_preference), BeersLayoutActivity.this, Color.parseColor(Constants.ENABLED_BUTTON_COLOR), BeersLayoutActivity.this);
                }else {
                    accessWebService();
                }
            }
        });
    }

    private void setupToolBar() {
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        name = getIntent().getStringExtra(Constants.BEER_NAME);
        price = getIntent().getStringExtra(Constants.BEER_PRICE);
        table = getIntent().getStringExtra(Constants.TABLE_INTENT_ID);
        image = getIntent().getStringExtra(Constants.BEER_IMAGE);
        servitoros_id = getIntent().getStringExtra(Constants.WAITER_INTENT_ID);
        magazi_id = getIntent().getStringExtra(Constants.COMPANY_INTENT_ID);
        toolbar.setTitle(name + " - " + getString(R.string.price) + " " + price);
        toolbar.setSubtitle(getString(R.string.table_id) + table);
        setSupportActionBar(toolbar);
    }


    private class MyInsertDataTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(BeersLayoutActivity.this);
            pDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setIndeterminate(true);
            pDialog.setMessage(getString(R.string.dialog_rate_data_submit));
            pDialog.setCancelable(false);
            pDialog.setInverseBackgroundForced(true);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            nameValuePairs = new ArrayList<>();
            try {
                url = new URL(params[0]);
                urlConnection =(HttpURLConnection) url.openConnection();
                urlConnection.setReadTimeout(10000);
                urlConnection.setConnectTimeout(15000);
                urlConnection.setRequestMethod("POST");
                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                setupDataToDB();
                outputStream = urlConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                bufferedWriter.write(StringGenerator.queryResults(nameValuePairs));
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                urlConnection.connect();
                inputStream = new BufferedInputStream(urlConnection.getInputStream());
            } catch (IOException e) {
                e.printStackTrace();

            }

            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pDialog.dismiss();
            Toast.makeText(BeersLayoutActivity.this, getString(R.string.cart_addition_successfull), Toast.LENGTH_LONG).show();
            BeersLayoutActivity.this.finish();
        }
    }

    private void setupDataToDB() {
        nameValuePairs.add(new BasicNameValuePair(Constants.PRODUCT_NAME_VALUE_PAIR, name));
        nameValuePairs.add(new BasicNameValuePair(Constants.PRODUCT_PRICE_VALUE_PAIR, String.valueOf(priceCalculated)));
        nameValuePairs.add(new BasicNameValuePair(Constants.PRODUCT_IMAGE_VALUE_PAIR, image));
        nameValuePairs.add(new BasicNameValuePair(Constants.PRODUCT_QUANTITY_VALUE_PAIR, String.valueOf(quantityNumberFinal)));
        nameValuePairs.add(new BasicNameValuePair("preferation", wayPreference));
        nameValuePairs.add(new BasicNameValuePair("size", sizePreference));
        nameValuePairs.add(new BasicNameValuePair("choice", choicePreference));
        nameValuePairs.add(new BasicNameValuePair(Constants.PRODUCT_COMMENT_VALUE_PAIR, comment));
        nameValuePairs.add(new BasicNameValuePair(Constants.PRODUCT_COMPANY_ID_VALUE_PAIR, magazi_id));
        nameValuePairs.add(new BasicNameValuePair(Constants.PRODUCT_WAITER_ID_VALUE_PAIR, servitoros_id));
        nameValuePairs.add(new BasicNameValuePair(Constants.PRODUCT_TABLE_ID_VALUE_PAIR, table));
    }

    private void accessWebService() {
        task = new MyInsertDataTask();
        task.execute(Constants.BEERS_ADD_TO_CART_URL);
    }

}
