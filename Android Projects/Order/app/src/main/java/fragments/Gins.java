package fragments;

import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Spinner;

import com.order.app.order.R;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import adapters.SpiritComponentAdapter;
import adapters.SpiritsListAdapter;
import dialogs.DialogMessageDisplay;
import interfaces.GinsCommunicator;
import lists.SpiritComponentProduct;
import lists.SpiritList;


public class Gins extends AppCompatActivity {

    private Toolbar tb;
    private Spinner spRef, spDrinks;
    private CheckBox shortGlass, longGlass, yes, no;
    private EditText comments;
    private Button cart;
    private String jsonResult;
    private ArrayAdapter<String> adapter;
    private SpiritsListAdapter adapterGins;
    private static final String URL = "http://my.chatapp.info/order_api/files/getgins.php";
    private static final String TABLE_INTENT_ID = "table_name";
    private static final String COMPANY_INTENT_ID = "magaziID";
    private static final String WAITER_INTENT_ID = "servitorosID";
    private static final String SPIRIT_ITEM = "spirit_item";
    private String servitoros_id, magazi_id, table, name;
    ProgressDialog pDialog;
    String selectedGin, selectedGlass, selectStroll;
    ArrayList<SpiritList> customSpinner;
    private Toolbar toolbar;
    private List<SpiritComponentProduct> components;
    private SpiritComponentAdapter componentAdapter;
    private GinsCommunicator communicator;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gins);
        populateToolBar();
        spDrinks = (Spinner)findViewById(R.id.flavor_gin_spinner);
        spRef = (Spinner)findViewById(R.id.refreshment_spinner);
        components = new ArrayList<>();
        checkNetworkInfo();
        findItems();

        setupListeners();
        populateSpiritComponentsList();
        buildRefreshmentsSpinner();
        registerCallBackClick();
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

    private void checkNetworkInfo() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean network_connected = activeNetwork != null && activeNetwork.isAvailable() && activeNetwork.isConnectedOrConnecting();
        if (!network_connected) {
            DialogMessageDisplay.displayWifiSettingsDialog(Gins.this, Gins.this, getString(R.string.wifi_off_title), getString(R.string.wifi_off_message));
        } else {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                accessWebService();

            }
        }
    }

    private void setupListeners() {
        shortGlass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    longGlass.setEnabled(false);
                    selectedGlass = shortGlass.getText().toString().toLowerCase();
                }else{
                    longGlass.setEnabled(true);
                    selectedGlass = "";
                }
            }
        });
        longGlass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    shortGlass.setEnabled(false);
                    selectedGlass = longGlass.getText().toString().toLowerCase();
                }else{
                    shortGlass.setEnabled(true);
                    selectedGlass = "";
                }
            }
        });
        yes.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    no.setEnabled(false);
                    selectStroll = yes.getText().toString().toLowerCase();
                } else {
                    no.setEnabled(true);
                    selectStroll = "";
                }
            }
        });
        no.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (isChecked) {
                    yes.setEnabled(false);
                    selectStroll = no.getText().toString().toLowerCase();
                } else {
                    yes.setEnabled(true);
                    selectStroll = "";
                }
            }
        });


    }

    private void findItems() {

        shortGlass = (CheckBox)findViewById(R.id.short_glass);
        longGlass = (CheckBox)findViewById(R.id.long_glass);
        yes = (CheckBox)findViewById(R.id.yesCheck);
        no = (CheckBox)findViewById(R.id.noCheck);
        comments = (EditText)findViewById(R.id.editText_gins);
        cart = (Button)findViewById(R.id.button_gins);
    }

    private void populateToolBar() {
        name = getIntent().getStringExtra(SPIRIT_ITEM);
        table = getIntent().getStringExtra(TABLE_INTENT_ID);
        servitoros_id = getIntent().getStringExtra(WAITER_INTENT_ID);
        magazi_id = getIntent().getStringExtra(COMPANY_INTENT_ID);
        toolbar = (Toolbar)findViewById(R.id.toolBar);
        toolbar.setTitle(name);
        toolbar.setSubtitle(getString(R.string.table_id) + table);
        setSupportActionBar(toolbar);

    }

    private void registerCallBackClick() {
        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!longGlass.isChecked() && !shortGlass.isChecked()){
                    DialogMessageDisplay.displayErrorMessage(Gins.this, getString(R.string.error), getString(R.string.glass_error_msg));
                }
            }
        });
    }

    private void buildRefreshmentsSpinner() {
        componentAdapter = new SpiritComponentAdapter(Gins.this, components);
        spRef.setAdapter(componentAdapter);
    }




    public class JsonReadTask extends AsyncTask<String , Void, List<SpiritList>> {
        public JsonReadTask() {
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Gins.this);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setIndeterminate(true);
            pDialog.setMessage(getString(R.string.get_stocks));
            pDialog.setCancelable(false);
            pDialog.setInverseBackgroundForced(true);
            pDialog.show();
        }

        @Override
        protected List<SpiritList> doInBackground(String... params) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(params[0]);
            try {
                HttpResponse response = httpclient.execute(httppost);
                jsonResult = inputStreamToString(
                        response.getEntity().getContent()).toString();
                customSpinner = new ArrayList<>();

                JSONObject jsonResponse = new JSONObject(jsonResult);
                JSONArray jsonMainNode = jsonResponse.optJSONArray("gins");
                for (int i = 0; i < jsonMainNode.length(); i++) {
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                    String name = jsonChildNode.optString("name");
                    String price = jsonChildNode.optString("price");
                    String image = jsonChildNode.optString("image");
                    customSpinner.add(new SpiritList(name, price, image));
                }
                return customSpinner;
            } catch (Exception e) {
                e.printStackTrace();
            }
            return null;
        }

        private StringBuilder inputStreamToString(InputStream is) {
            String rLine = "";
            StringBuilder answer = new StringBuilder();
            BufferedReader rd = new BufferedReader(new InputStreamReader(is));
            try {
                while ((rLine = rd.readLine()) != null) {
                    answer.append(rLine);
                }
            } catch (Exception e) {
                throw new RuntimeException(e.getMessage());
            }
            return answer;
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
        task.execute(new String[]{URL});
    }

    public void ListDrawer(List<SpiritList> customSpinner) {
        adapterGins = new SpiritsListAdapter(Gins.this ,customSpinner);
        spDrinks.setAdapter(adapterGins);
        Log.d("Spinner Count", "The Spinner count is " + spDrinks.getCount());
    }

}
