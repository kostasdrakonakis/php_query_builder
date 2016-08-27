package fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
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
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.order.app.order.R;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
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
import java.util.List;

import adapters.SpiritComponentAdapter;
import adapters.SpiritsListAdapter;
import dialogs.DialogMessageDisplay;
import functions.Constants;
import functions.StringGenerator;
import lists.SpiritComponentProduct;
import lists.SpiritList;


public class Rums extends AppCompatActivity {

    private Spinner spRef, spDrinks;
    private CheckBox shortGlass, longGlass, yes, no;
    private EditText sxolia, quantity;
    private int quantityNumberFinal;
    private Button cart, plus, minus;
    private StringBuilder jsonResult;
    private SpiritsListAdapter adapterRums;
    private String servitoros_id, magazi_id, table, title, quantityPreference, comment, componentPreference, rumPreference, glassPreference, strollPreference, name, image, price;
    ProgressDialog pDialog;
    ArrayList<SpiritList> customSpinner;
    private Toolbar toolbar;
    private List<SpiritComponentProduct> components;
    private SpiritComponentAdapter componentAdapter;
    private JSONObject jsonResponse, jsonChildNode, dataToWrite;
    private OutputStreamWriter outputStreamWriter;
    private JSONArray jsonMainNode;
    private HttpURLConnection urlConnection;
    private URL url;
    private InputStream inputStream;
    private double priceCalculated;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.rums);
        populateToolBar();
        spDrinks = (Spinner)findViewById(R.id.flavor_rum_spinner);
        components = new ArrayList<>();
        checkNetworkInfo();
        findItems();
        setupListeners();
        populateSpiritComponentsList();
        buildRefreshmentsSpinner();
        getSpinnerSelectedPreference();
        checkQuantity();
    }

    private void getSpinnerSelectedPreference() {
        spDrinks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                rumPreference = customSpinner.get(position).getName();
                image = customSpinner.get(position).getImage();
                price = customSpinner.get(position).getPrice();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }

    private void checkNetworkInfo() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean network_connected = activeNetwork != null && activeNetwork.isAvailable() && activeNetwork.isConnectedOrConnecting();
        if (!network_connected) {
            DialogMessageDisplay.displayWifiSettingsDialog(Rums.this, Rums.this, getString(R.string.wifi_off_title), getString(R.string.wifi_off_message));
        } else {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                readDataWebService();
            }
        }
    }

    private void findItems() {
        spRef = (Spinner)findViewById(R.id.refreshment_spinner);
        shortGlass = (CheckBox)findViewById(R.id.short_glass);
        longGlass = (CheckBox)findViewById(R.id.long_glass);
        yes = (CheckBox)findViewById(R.id.yesCheck);
        no = (CheckBox)findViewById(R.id.noCheck);
    }

    private void checkQuantity() {
        quantity = (EditText) findViewById(R.id.quantityEditTextRum);
        plus = (Button) findViewById(R.id.buttonPlusRum);
        minus = (Button) findViewById(R.id.buttonMinusRum);
        sxolia = (EditText)findViewById(R.id.editTextRumsComments);
        cart = (Button)findViewById(R.id.cartButtonRum);

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
                getSpinnerSelectedPreference();
                if (comment == null) {
                    comment = " ";
                }
                if (glassPreference == null) {
                    Toast.makeText(Rums.this, getString(R.string.glass_required), Toast.LENGTH_LONG).show();
                } else if (strollPreference == null) {
                    Toast.makeText(Rums.this, getString(R.string.stroll_required), Toast.LENGTH_LONG).show();
                } else {
                    writeDataWebService();
                }

            }
        });
    }

    private void setupListeners() {
        shortGlass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    longGlass.setEnabled(false);
                    glassPreference = shortGlass.getText().toString();
                }else{
                    longGlass.setEnabled(true);
                    glassPreference = null;
                }
            }
        });
        longGlass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    shortGlass.setEnabled(false);
                    glassPreference = longGlass.getText().toString();
                }else{
                    shortGlass.setEnabled(true);
                    glassPreference = null;
                }
            }
        });
        yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    no.setEnabled(false);
                    strollPreference = yes.getText().toString().toLowerCase();
                }else{
                    no.setEnabled(true);
                    strollPreference = null;
                }
            }
        });
        no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    yes.setEnabled(false);
                    strollPreference = no.getText().toString().toLowerCase();
                }else{
                    yes.setEnabled(true);
                    strollPreference = null;
                }
            }
        });
    }

    private void populateToolBar() {
        title = getIntent().getStringExtra(Constants.SPIRIT_ITEM);
        table = getIntent().getStringExtra(Constants.TABLE_INTENT_ID);
        servitoros_id = getIntent().getStringExtra(Constants.WAITER_INTENT_ID);
        magazi_id = getIntent().getStringExtra(Constants.COMPANY_INTENT_ID);
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setTitle(title);
        toolbar.setSubtitle(getString(R.string.table_id) + table);
        setSupportActionBar(toolbar);
    }

    private void populateSpiritComponentsList() {
        components.add(new SpiritComponentProduct(R.mipmap.ic_glass, getString(R.string.sketo)));
        components.add(new SpiritComponentProduct(R.mipmap.ic_ice, getString(R.string.pago)));
        components.add(new SpiritComponentProduct(R.mipmap.ic_coca_cola, getString(R.string.coca)));
        components.add(new SpiritComponentProduct(R.mipmap.ic_coca_light, getString(R.string.coca_light)));
        components.add(new SpiritComponentProduct(R.mipmap.ic_zero, getString(R.string.coca_zero)));
        components.add(new SpiritComponentProduct(R.mipmap.ic_sprite, getString(R.string.sprite)));
        components.add(new SpiritComponentProduct(R.mipmap.ic_fanta_lemon, getString(R.string.fanta_lemon)));
        components.add(new SpiritComponentProduct(R.mipmap.ic_fanta_orange, getString(R.string.fanta_orange)));
        components.add(new SpiritComponentProduct(R.mipmap.ic_soda, getString(R.string.soda)));
        components.add(new SpiritComponentProduct(R.mipmap.ic_tonic, getString(R.string.tonic)));
        components.add(new SpiritComponentProduct(R.mipmap.ic_water, getString(R.string.water)));
        components.add(new SpiritComponentProduct(R.mipmap.ic_redbull, getString(R.string.redbull)));
    }

    private void buildRefreshmentsSpinner() {
        componentAdapter = new SpiritComponentAdapter(Rums.this, components);
        spRef.setAdapter(componentAdapter);
        spRef.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                componentPreference = components.get(position).getProductName();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });
    }


    public class JsonReadTask extends AsyncTask<String , Void, List<SpiritList>> {
        public JsonReadTask() {
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Rums.this);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setIndeterminate(true);
            pDialog.setMessage(getString(R.string.get_stocks));
            pDialog.setCancelable(false);
            pDialog.setInverseBackgroundForced(true);
            pDialog.show();
        }

        @Override
        protected List<SpiritList> doInBackground(String... params) {
            try {
                url = new URL(params[0]);
                urlConnection =(HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty(Constants.CUSTOM_HEADER, Constants.API_KEY);
                urlConnection.setRequestMethod(Constants.METHOD_GET);
                urlConnection.connect();
                urlConnection.setConnectTimeout(5000);
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                jsonResult = StringGenerator.inputStreamToString(in, Rums.this);
                customSpinner = new ArrayList<>();

                jsonResponse = new JSONObject(jsonResult.toString());
                jsonMainNode = jsonResponse.optJSONArray(Constants.RUMS_JSON_ARRAY);
                for (int i = 0; i < jsonMainNode.length(); i++) {
                    jsonChildNode = jsonMainNode.getJSONObject(i);
                    name = jsonChildNode.optString("name");
                    price = jsonChildNode.optString("price");
                    image = jsonChildNode.optString("image");
                    customSpinner.add(new SpiritList(name, price, image));
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return customSpinner;
        }

        @Override
        protected void onPostExecute(List<SpiritList> customSpinner) {
            if(customSpinner == null){
                Log.d("ERORR", "No result to show.");
                return;
            }
            ListDrawer(customSpinner);
            pDialog.dismiss();
        }
    }

    private class MyInsertDataTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Rums.this);
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

                jsonResult = StringGenerator.inputStreamToString(inputStream, Rums.this);
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
                Toast.makeText(Rums.this, getString(R.string.cart_addition_successfull), Toast.LENGTH_LONG).show();
                Rums.this.finish();
            }else{
                Toast.makeText(Rums.this, "There was a problemm adding this product to the cart. Please try again", Toast.LENGTH_LONG).show();
            }

        }
    }

    private void setupDataToDB() {

        dataToWrite = new JSONObject();
        try {
            dataToWrite.put(""+Constants.PRODUCT_NAME_POST+"", rumPreference);
            dataToWrite.put(""+Constants.PRODUCT_PRICE_POST+"", String.valueOf(priceCalculated));
            dataToWrite.put(""+Constants.PRODUCT_IMAGE_POST+"", image);
            dataToWrite.put(""+Constants.PRODUCT_QUANTITY_POST+"", String.valueOf(quantityNumberFinal));
            dataToWrite.put(""+Constants.PRODUCT_PREFERATION1_POST+"", glassPreference);
            dataToWrite.put(""+Constants.PRODUCT_PREFERATION2_POST+"", strollPreference);
            dataToWrite.put(""+Constants.PRODUCT_PREFERATION3_POST+"", componentPreference);
            dataToWrite.put(""+Constants.PRODUCT_PREFERATION4_POST+"", comment);
            dataToWrite.put(""+Constants.PRODUCT_COMPANY_ID_POST+"", magazi_id);
            dataToWrite.put(""+Constants.PRODUCT_WAITER_ID_POST+"", servitoros_id);
            dataToWrite.put(""+Constants.PRODUCT_TABLE_ID_POST+"", table);
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public void readDataWebService() {
        JsonReadTask task = new JsonReadTask();
        task.execute(Constants.RUMS_URL);
    }

    public void writeDataWebService() {
        MyInsertDataTask task = new MyInsertDataTask();
        task.execute(Constants.CART_URL + servitoros_id + "/" + magazi_id + "/" + table + "/");
        Log.e("Snacks URL",Constants.CART_URL + servitoros_id + "/" + magazi_id + "/" + table + "/");
    }



    public void ListDrawer(List<SpiritList> customSpinner) {
        adapterRums = new SpiritsListAdapter(Rums.this ,customSpinner);
        spDrinks.setAdapter(adapterRums);
        Log.d("Spinner Count", "The Spinner count is " + spDrinks.getCount());
    }
}
