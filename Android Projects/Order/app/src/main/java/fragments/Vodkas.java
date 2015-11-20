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
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import com.order.app.order.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import adapters.SpiritComponentAdapter;
import adapters.SpiritsListAdapter;
import dialogs.DialogMessageDisplay;
import functions.AppConstant;
import functions.StringGenerator;
import lists.SpiritComponentProduct;
import lists.SpiritList;


public class Vodkas extends AppCompatActivity {

    
    private Spinner spRef, spDrinks;
    private CheckBox shortGlass, longGlass, yes, no;
    private EditText sxolia, quantity;
    private int quantityNumberFinal;
    private Button cart, plus, minus;
    private StringBuilder jsonResult;
    private SpiritsListAdapter adapterVodkas;
    private String servitoros_id, magazi_id, table, title, quantityPreference, comment, componentPreference, vodkaPreference, glassPreference, strollPreference, name, image, price;
    ProgressDialog pDialog;
    ArrayList<SpiritList> customSpinner;
    private Toolbar toolbar;
    private List<SpiritComponentProduct> components;
    private SpiritComponentAdapter componentAdapter;
    private JSONObject jsonResponse, jsonChildNode;
    private JSONArray jsonMainNode;
    private HttpURLConnection urlConnection;
    private URL url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.vodkas);
        populateToolBar();
        spDrinks = (Spinner)findViewById(R.id.flavor_vodka_spinner);
        components = new ArrayList<>();
        checkNetworkInfo();
        getSpinnerSelectedPreference();
        findItems();
        setupListeners();
        populateSpiritComponentsList();
        buildRefreshmentsSpinner();
        checkQuantity();
    }

    private void getSpinnerSelectedPreference() {
        spDrinks.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                vodkaPreference = customSpinner.get(position).getName();
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
            DialogMessageDisplay.displayWifiSettingsDialog(Vodkas.this, Vodkas.this, getString(R.string.wifi_off_title), getString(R.string.wifi_off_message));
        } else {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                accessWebService();
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

    private void checkQuantity() {
        quantity = (EditText) findViewById(R.id.quantityEditTextVodkas);
        plus = (Button) findViewById(R.id.buttonPlusVodkas);
        minus = (Button) findViewById(R.id.buttonMinusVodkas);
        sxolia = (EditText)findViewById(R.id.editTextVodkasComments);
        cart = (Button)findViewById(R.id.cartButtonVodkas);

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
                if (glassPreference == null) {
                    Toast.makeText(Vodkas.this, getString(R.string.glass_required), Toast.LENGTH_LONG).show();
                } else if (strollPreference == null) {
                    Toast.makeText(Vodkas.this, getString(R.string.stroll_required), Toast.LENGTH_LONG).show();
                } else {
                    Toast.makeText(Vodkas.this, "Success", Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    private void populateToolBar() {
        title = getIntent().getStringExtra(AppConstant.SPIRIT_ITEM);
        table = getIntent().getStringExtra(AppConstant.TABLE_INTENT_ID);
        servitoros_id = getIntent().getStringExtra(AppConstant.WAITER_INTENT_ID);
        magazi_id = getIntent().getStringExtra(AppConstant.COMPANY_INTENT_ID);
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
        componentAdapter = new SpiritComponentAdapter(Vodkas.this, components);
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
            pDialog = new ProgressDialog(Vodkas.this);
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
                urlConnection.connect();
                urlConnection.setConnectTimeout(5000);
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                jsonResult = StringGenerator.inputStreamToString(in, Vodkas.this);
                customSpinner = new ArrayList<>();

                jsonResponse = new JSONObject(jsonResult.toString());
                jsonMainNode = jsonResponse.optJSONArray(AppConstant.VODKAS_JSON_ARRAY);
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
    }// end async task

    public void accessWebService() {
        JsonReadTask task = new JsonReadTask();
        task.execute(AppConstant.VODKAS_URL);
    }

    public void ListDrawer(List<SpiritList> customSpinner) {
        adapterVodkas = new SpiritsListAdapter(Vodkas.this ,customSpinner);
        spDrinks.setAdapter(adapterVodkas);
        Log.d("Spinner Count", "The Spinner count is " + spDrinks.getCount());
    }


}
