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
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;

import functions.AppConstant;
import functions.StringGenerator;

public class CoffeesLayoutActivity extends AppCompatActivity {

    private String productName, table, price, sugarPreference, milkPreference, dosePreference, quantityPreference, comment, zaximau, image, servitoros_id, magazi_id;
    private EditText quantity, sxolia;
    private CheckBox nosugar, medium, sweet, vsweet, yesCheck, noCheck, afrogalo, santigi, monos, diplos;
    private ProgressDialog pDialog;
    private ArrayList<NameValuePair> nameValuePairs;
    private Button plus, minus, cart;
    private MyInsertDataTask task;
    private int quantityNumberFinal;
    private Toolbar toolbar;
    private HttpURLConnection urlConnection;
    private URL url;
    private OutputStream outputStream;
    private BufferedWriter bufferedWriter;
    private InputStream inputStream;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffees_layout);
        populateActionBar();
        checkQuantity();
        setupCheckBoxes();
    }

    private void setupCheckBoxes() {
        nosugar = (CheckBox)findViewById(R.id.sugaFreecheckBox);
        medium = (CheckBox)findViewById(R.id.mediumSugarcheckBox);
        sweet = (CheckBox)findViewById(R.id.sweetSugarcheckBox);
        vsweet = (CheckBox)findViewById(R.id.verySweetSugarcheckBox);
        yesCheck = (CheckBox)findViewById(R.id.milkEbapYes);
        noCheck = (CheckBox)findViewById(R.id.milkEbapNo);
        afrogalo = (CheckBox)findViewById(R.id.whippedCreamBox);
        santigi = (CheckBox)findViewById(R.id.whippedMilkBox);
        monos = (CheckBox)findViewById(R.id.monoscheckBox);
        diplos = (CheckBox)findViewById(R.id.diploscheckBox);

        nosugar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (nosugar.isChecked()){
                    medium.setEnabled(false);
                    sweet.setEnabled(false);
                    vsweet.setEnabled(false);
                    sugarPreference = nosugar.getText().toString();
                }else{
                    medium.setEnabled(true);
                    sweet.setEnabled(true);
                    vsweet.setEnabled(true);
                    sugarPreference = null;
                }
            }
        });

        medium.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (medium.isChecked()){
                    nosugar.setEnabled(false);
                    sweet.setEnabled(false);
                    vsweet.setEnabled(false);
                    sugarPreference = medium.getText().toString();
                }else{
                    sweet.setEnabled(true);
                    vsweet.setEnabled(true);
                    nosugar.setEnabled(true);
                    sugarPreference = null;
                }
            }
        });
        sweet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (sweet.isChecked()){
                    nosugar.setEnabled(false);
                    medium.setEnabled(false);
                    vsweet.setEnabled(false);
                    sugarPreference = sweet.getText().toString();
                }else{
                    medium.setEnabled(true);
                    vsweet.setEnabled(true);
                    nosugar.setEnabled(true);
                    sugarPreference = null;
                }
            }
        });
        vsweet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (vsweet.isChecked()){
                    nosugar.setEnabled(false);
                    sweet.setEnabled(false);
                    medium.setEnabled(false);
                    sugarPreference = vsweet.getText().toString();
                }else{
                    sweet.setEnabled(true);
                    medium.setEnabled(true);
                    nosugar.setEnabled(true);
                    sugarPreference = null;
                }
            }
        });
        yesCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (yesCheck.isChecked()) {
                    noCheck.setEnabled(false);
                    santigi.setEnabled(false);
                    afrogalo.setEnabled(false);
                    milkPreference = yesCheck.getText().toString();
                } else {
                    noCheck.setEnabled(true);
                    santigi.setEnabled(true);
                    afrogalo.setEnabled(true);
                    milkPreference = null;
                }
            }
        });
        noCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (noCheck.isChecked()) {
                    yesCheck.setEnabled(false);
                    santigi.setEnabled(false);
                    afrogalo.setEnabled(false);
                    milkPreference = noCheck.getText().toString();
                } else {
                    yesCheck.setEnabled(true);
                    santigi.setEnabled(true);
                    afrogalo.setEnabled(true);
                    milkPreference = null;
                }
            }
        });
        santigi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (santigi.isChecked()) {
                    noCheck.setEnabled(false);
                    yesCheck.setEnabled(false);
                    afrogalo.setEnabled(false);
                    milkPreference = santigi.getText().toString();
                } else {
                    noCheck.setEnabled(true);
                    yesCheck.setEnabled(true);
                    afrogalo.setEnabled(true);
                    milkPreference = null;
                }
            }
        });
        afrogalo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (afrogalo.isChecked()) {
                    noCheck.setEnabled(false);
                    santigi.setEnabled(false);
                    yesCheck.setEnabled(false);
                    milkPreference = afrogalo.getText().toString();
                } else {
                    noCheck.setEnabled(true);
                    santigi.setEnabled(true);
                    yesCheck.setEnabled(true);
                    milkPreference = null;
                }
            }
        });
        monos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (monos.isChecked()){
                    diplos.setEnabled(false);
                    dosePreference = monos.getText().toString();
                }else{
                    diplos.setEnabled(true);
                    dosePreference = null;
                }
            }
        });
        diplos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (diplos.isChecked()){
                    monos.setEnabled(false);
                    dosePreference = diplos.getText().toString();
                }else{
                    monos.setEnabled(true);
                    dosePreference = null;
                }
            }
        });


    }

    private void checkQuantity() {
        quantity = (EditText) findViewById(R.id.quantityEditText);
        plus = (Button) findViewById(R.id.buttonPlus);
        minus = (Button) findViewById(R.id.buttonMinus);
        sxolia = (EditText)findViewById(R.id.editTextComments);
        cart = (Button)findViewById(R.id.cartButton);

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
                comment = sxolia.getText().toString();
                if (comment == null) {
                    comment = " ";
                }
                if (sugarPreference == null) {
                    Toast.makeText(CoffeesLayoutActivity.this, getString(R.string.sugar_required), Toast.LENGTH_LONG).show();
                } else if (dosePreference == null) {
                    Toast.makeText(CoffeesLayoutActivity.this, getString(R.string.dose_required), Toast.LENGTH_LONG).show();
                } else {
                    accessWebService();
                }

            }
        });
    }

    private void accessWebService() {
        task = new MyInsertDataTask();
        task.execute(AppConstant.COFFEES_ADD_TO_CART_URL);
    }

    private void populateActionBar() {
        toolbar = (Toolbar)findViewById(R.id.toolBar);

        productName = getIntent().getStringExtra(AppConstant.COFFEE_NAME);
        price = getIntent().getStringExtra(AppConstant.COFFEE_PRICE);
        table = getIntent().getStringExtra(AppConstant.TABLE_INTENT_ID);
        image = getIntent().getStringExtra(AppConstant.COFFEE_IMAGE);
        servitoros_id = getIntent().getStringExtra(AppConstant.WAITER_INTENT_ID);
        magazi_id = getIntent().getStringExtra(AppConstant.COMPANY_INTENT_ID);
        toolbar.setTitle(productName + " - " + getString(R.string.price) + " " + price);
        toolbar.setSubtitle(getString(R.string.table_id) + table);
        setSupportActionBar(toolbar);
    }

    private class MyInsertDataTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CoffeesLayoutActivity.this);
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
            ByteBuffer s = Charset.forName(AppConstant.CHARACTER_ENCODING).encode(sugarPreference);
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
            Toast.makeText(CoffeesLayoutActivity.this, getString(R.string.cart_addition_successfull), Toast.LENGTH_LONG).show();
            CoffeesLayoutActivity.this.finish();
        }
    }

    private void setupDataToDB() {
        nameValuePairs.add(new BasicNameValuePair(AppConstant.PRODUCT_NAME_VALUE_PAIR, productName));
        nameValuePairs.add(new BasicNameValuePair(AppConstant.PRODUCT_PRICE_VALUE_PAIR, String.valueOf(price)));
        nameValuePairs.add(new BasicNameValuePair(AppConstant.PRODUCT_IMAGE_VALUE_PAIR, image));
        nameValuePairs.add(new BasicNameValuePair(AppConstant.PRODUCT_QUANTITY_VALUE_PAIR, String.valueOf(quantityNumberFinal)));
        nameValuePairs.add(new BasicNameValuePair("sugar", sugarPreference));
        nameValuePairs.add(new BasicNameValuePair("milk", milkPreference));
        nameValuePairs.add(new BasicNameValuePair("dose", dosePreference));
        nameValuePairs.add(new BasicNameValuePair(AppConstant.PRODUCT_COMMENT_VALUE_PAIR, comment));
        nameValuePairs.add(new BasicNameValuePair(AppConstant.PRODUCT_COMPANY_ID_VALUE_PAIR, magazi_id));
        nameValuePairs.add(new BasicNameValuePair(AppConstant.PRODUCT_WAITER_ID_VALUE_PAIR, servitoros_id));
        nameValuePairs.add(new BasicNameValuePair(AppConstant.PRODUCT_TABLE_ID_VALUE_PAIR, table));
    }


}
