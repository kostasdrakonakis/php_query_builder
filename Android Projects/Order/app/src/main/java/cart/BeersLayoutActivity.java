package cart;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
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
import org.json.JSONException;
import org.json.JSONObject;

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
    private OutputStreamWriter outputStreamWriter;
    private InputStream inputStream;
    private StringBuilder jsonResult;
    private JSONObject jsonResponse, dataToWrite;
    private ProgressDialog pDialog;
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


    private class MyInsertDataTask extends AsyncTask<String, Void, Boolean> {

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
        protected Boolean doInBackground(String... params) {
            try {
                url = new URL(params[0]);
                urlConnection =(HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty(Constants.CUSTOM_HEADER, Constants.API_KEY);
                Log.e("Custom Header", Constants.CUSTOM_HEADER);
                Log.e("Api Key: ", Constants.API_KEY);
                Log.e("Method: ", Constants.METHOD_POST);
                urlConnection.setRequestProperty("Accept-Encoding", "application/json");
                urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                urlConnection.setRequestMethod(Constants.METHOD_POST);

                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.connect();
                setupDataToDB();
                outputStreamWriter = new OutputStreamWriter(urlConnection.getOutputStream());
                outputStreamWriter.write(dataToWrite.toString());
                outputStreamWriter.flush();
                outputStreamWriter.close();

                inputStream = new BufferedInputStream(urlConnection.getInputStream());

                jsonResult = StringGenerator.inputStreamToString(inputStream, BeersLayoutActivity.this);
                jsonResponse = new JSONObject(jsonResult.toString());
                Log.e("Data From JSON", jsonResponse.toString());
                String status = jsonResponse.getString("status");
                String status_code = jsonResponse.getString("status_code");
                if (status.equals("success") && status_code.equals("201")){
                    return true;
                }else{
                    return false;
                }
            } catch (Exception e) {
                e.printStackTrace();

            }

            return false;
        }
        @Override
        protected void onPostExecute(Boolean aVoid) {
            super.onPostExecute(aVoid);
            pDialog.dismiss();
            if (aVoid){
                Toast.makeText(BeersLayoutActivity.this, getString(R.string.cart_addition_successfull), Toast.LENGTH_LONG).show();
                BeersLayoutActivity.this.finish();
            }else{
                Toast.makeText(BeersLayoutActivity.this, "There was a problemm adding this product to the cart. Please try again", Toast.LENGTH_LONG).show();
            }
        }
    }

    private void setupDataToDB() {
        dataToWrite = new JSONObject();
        try {
            dataToWrite.put(""+Constants.PRODUCT_NAME_POST+"", name);
            dataToWrite.put(""+Constants.PRODUCT_PRICE_POST+"", String.valueOf(priceCalculated));
            dataToWrite.put(""+Constants.PRODUCT_IMAGE_POST+"", image);
            dataToWrite.put(""+Constants.PRODUCT_QUANTITY_POST+"", String.valueOf(quantityNumberFinal));
            dataToWrite.put(""+Constants.PRODUCT_PREFERATION1_POST+"", wayPreference);
            dataToWrite.put(""+Constants.PRODUCT_PREFERATION2_POST+"", sizePreference);
            dataToWrite.put(""+Constants.PRODUCT_PREFERATION3_POST+"", choicePreference);
            dataToWrite.put(""+Constants.PRODUCT_PREFERATION4_POST+"", comment);
            dataToWrite.put(""+Constants.PRODUCT_COMPANY_ID_POST+"", magazi_id);
            dataToWrite.put(""+Constants.PRODUCT_WAITER_ID_POST+"", servitoros_id);
            dataToWrite.put(""+Constants.PRODUCT_TABLE_ID_POST+"", table);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    private void accessWebService() {
        task = new MyInsertDataTask();
        task.execute(Constants.CART_URL + servitoros_id + "/" + magazi_id + "/" + table + "/");
        Log.e("Beers URL",Constants.CART_URL + servitoros_id + "/" + magazi_id + "/" + table + "/");
    }

}
