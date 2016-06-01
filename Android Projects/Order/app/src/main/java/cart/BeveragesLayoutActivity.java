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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;

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
    private OutputStream outputStream;
    private BufferedWriter bufferedWriter;
    private InputStream inputStream;
    private ProgressDialog pDialog;
    private ArrayList<NameValuePair> nameValuePairs;
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
        task.execute(Constants.BEVERAGES_ADD_TO_CART_URL);
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
            Toast.makeText(BeveragesLayoutActivity.this, getString(R.string.cart_addition_successfull), Toast.LENGTH_LONG).show();
            BeveragesLayoutActivity.this.finish();
        }
    }

    private void setupDataToDB() {
        nameValuePairs.add(new BasicNameValuePair(Constants.PRODUCT_NAME_VALUE_PAIR, productName));
        nameValuePairs.add(new BasicNameValuePair(Constants.PRODUCT_PRICE_VALUE_PAIR, String.valueOf(priceCalculated)));
        nameValuePairs.add(new BasicNameValuePair(Constants.PRODUCT_IMAGE_VALUE_PAIR, image));
        nameValuePairs.add(new BasicNameValuePair(Constants.PRODUCT_QUANTITY_VALUE_PAIR, String.valueOf(quantityNumberFinal)));
        nameValuePairs.add(new BasicNameValuePair("glass", glassPreference));
        nameValuePairs.add(new BasicNameValuePair("stroll", strollPreference));
        nameValuePairs.add(new BasicNameValuePair("ice", icePreference));
        nameValuePairs.add(new BasicNameValuePair(Constants.PRODUCT_COMMENT_VALUE_PAIR, comment));
        nameValuePairs.add(new BasicNameValuePair(Constants.PRODUCT_COMPANY_ID_VALUE_PAIR, magazi_id));
        nameValuePairs.add(new BasicNameValuePair(Constants.PRODUCT_WAITER_ID_VALUE_PAIR, servitoros_id));
        nameValuePairs.add(new BasicNameValuePair(Constants.PRODUCT_TABLE_ID_VALUE_PAIR, table));
    }

}
