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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

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

public class BeveragesLayoutActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private String productName, table, price, servitoros_id, magazi_id, image, quantityPreference, comment, glassPreference, strollPreference, icePreference;
    private Button plus, minus, cart;
    private EditText quantity, sxolia;
    private int quantityNumberFinal;
    private double priceCalculated;
    private CheckBox shortGlass, longGlass, yesStroll, noStroll, yesIce, noIce;
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
        setContentView(R.layout.activity_beverages_layout);
        populateActionBar();
        checkQuantity();
        setupCheckboxes();
    }

    private void setupCheckboxes() {
        shortGlass = (CheckBox) findViewById(R.id.shortBevGlassCheckbox);
        longGlass = (CheckBox) findViewById(R.id.longBevGlassCheckbox);
        yesStroll = (CheckBox) findViewById(R.id.yesStrollCheckbox);
        noStroll = (CheckBox) findViewById(R.id.noStrollCheckbox);
        yesIce = (CheckBox) findViewById(R.id.beveragesYesCheckbox);
        noIce = (CheckBox) findViewById(R.id.beveragesNoCheckbox);
        shortGlass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (shortGlass.isChecked()) {
                    longGlass.setEnabled(false);
                    glassPreference = shortGlass.getText().toString();
                } else {
                    longGlass.setEnabled(true);
                    glassPreference = null;
                }
            }
        });
        longGlass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (longGlass.isChecked()) {
                    shortGlass.setEnabled(false);
                    glassPreference = longGlass.getText().toString();
                } else {
                    shortGlass.setEnabled(true);
                    glassPreference = null;
                }
            }
        });
        yesStroll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (yesStroll.isChecked()) {
                    noStroll.setEnabled(false);
                    strollPreference = yesStroll.getText().toString();
                } else {
                    noStroll.setEnabled(true);
                    strollPreference = null;
                }
            }
        });
        noStroll.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (noStroll.isChecked()) {
                    yesStroll.setEnabled(false);
                    strollPreference = noStroll.getText().toString();
                } else {
                    yesStroll.setEnabled(true);
                    strollPreference = null;
                }
            }
        });
        yesIce.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (yesIce.isChecked()) {
                    noIce.setEnabled(false);
                    icePreference = yesIce.getText().toString();
                } else {
                    noIce.setEnabled(true);
                    icePreference = null;
                }
            }
        });
        noIce.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (noIce.isChecked()) {
                    yesIce.setEnabled(false);
                    icePreference = noIce.getText().toString();
                } else {
                    yesIce.setEnabled(true);
                    icePreference = null;
                }
            }
        });

    }

    private void populateActionBar() {
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        productName = getIntent().getStringExtra(Constants.BEVERAGE_NAME);
        price = getIntent().getStringExtra(Constants.BEVERAGE_PRICE);
        table = getIntent().getStringExtra(Constants.TABLE_INTENT_ID);
        image = getIntent().getStringExtra(Constants.BEVERAGE_IMAGE);
        servitoros_id = getIntent().getStringExtra(Constants.WAITER_INTENT_ID);
        magazi_id = getIntent().getStringExtra(Constants.COMPANY_INTENT_ID);
        toolbar.setTitle(productName + " - " + getString(R.string.price) + " " + price);
        toolbar.setSubtitle(getString(R.string.table_id) + table);
        setSupportActionBar(toolbar);
    }

    private void checkQuantity() {
        quantity = (EditText) findViewById(R.id.quantityBeverageEditText);
        plus = (Button) findViewById(R.id.buttonBeveragesPlus);
        minus = (Button) findViewById(R.id.buttonBeveragesMinus);
        sxolia = (EditText) findViewById(R.id.editTextBeveragesComments);
        cart = (Button) findViewById(R.id.cartBeverageButton);

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

                if (glassPreference == null) {
                    Toast.makeText(BeveragesLayoutActivity.this, R.string.glass_required, Toast.LENGTH_SHORT).show();
                } else if (strollPreference == null) {
                    Toast.makeText(BeveragesLayoutActivity.this, R.string.stroll_required, Toast.LENGTH_SHORT).show();
                } else if (icePreference == null) {
                    Toast.makeText(BeveragesLayoutActivity.this, R.string.ice_required, Toast.LENGTH_SHORT).show();
                } else {
                    accessWebService();
                }
            }
        });
    }

    private void accessWebService() {
        task = new MyInsertDataTask();
        task.execute(Constants.CART_URL + servitoros_id + "/" + magazi_id + "/" + table + "/");
        Log.e("Beverages URL",Constants.CART_URL + servitoros_id + "/" + magazi_id + "/" + table + "/");
    }

    private class MyInsertDataTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(BeveragesLayoutActivity.this);
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

                jsonResult = StringGenerator.inputStreamToString(inputStream, BeveragesLayoutActivity.this);
                jsonResponse = new JSONObject(jsonResult.toString());
                Log.e("Data From JSON", jsonResponse.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pDialog.dismiss();
            Toast.makeText(BeveragesLayoutActivity.this, getString(R.string.cart_addition_successfull), Toast.LENGTH_LONG).show();
            BeveragesLayoutActivity.this.finish();
        }
    }

    private void setupDataToDB() {

        dataToWrite = new JSONObject();
        try {
            dataToWrite.put(""+Constants.PRODUCT_NAME_POST+"", productName);
            dataToWrite.put(""+Constants.PRODUCT_PRICE_POST+"", String.valueOf(priceCalculated));
            dataToWrite.put(""+Constants.PRODUCT_IMAGE_POST+"", image);
            dataToWrite.put(""+Constants.PRODUCT_QUANTITY_POST+"", String.valueOf(quantityNumberFinal));
            dataToWrite.put(""+Constants.PRODUCT_PREFERATION1_POST+"", glassPreference);
            dataToWrite.put(""+Constants.PRODUCT_PREFERATION2_POST+"", strollPreference);
            dataToWrite.put(""+Constants.PRODUCT_PREFERATION3_POST+"", icePreference);
            dataToWrite.put(""+Constants.PRODUCT_PREFERATION4_POST+"", comment);
            dataToWrite.put(""+Constants.PRODUCT_COMPANY_ID_POST+"", magazi_id);
            dataToWrite.put(""+Constants.PRODUCT_WAITER_ID_POST+"", servitoros_id);
            dataToWrite.put(""+Constants.PRODUCT_TABLE_ID_POST+"", table);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
