package fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
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

import adapters.SpiritsListAdapter;
import dialogs.DialogMessageDisplay;
import lists.SpiritList;


public class Gins extends ActionBarActivity {

    private Toolbar tb;
    private Spinner spRef, spDrinks;
    private CheckBox shortGlass, longGlass;
    private EditText comments;
    private Button cart;
    private String jsonResult;
    private ArrayAdapter<String> adapter;
    private SpiritsListAdapter adapterGins;
    private String url = "http://reservations.cretantaxiservices.gr/files/getgins.php";
    ProgressDialog pDialog;
    ArrayList<SpiritList> customSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gins);
        tb = (Toolbar)findViewById(R.id.topBar);
        String name = getIntent().getStringExtra("spirit_name");
        String table = getIntent().getStringExtra("table_name");
        tb.setTitle(name);
        tb.setSubtitle("Table: " + table);
        setSupportActionBar(tb);
        spDrinks = (Spinner)findViewById(R.id.flavor_gin_spinner);
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean network_connected = activeNetwork != null && activeNetwork.isAvailable() && activeNetwork.isConnectedOrConnecting();
        if (!network_connected) {
            DialogMessageDisplay.displayWifiSettingsDialog(Gins.this, Gins.this, "No Internet Connection", "Please connect to the Internet", AlertDialog.THEME_DEVICE_DEFAULT_LIGHT);
        } else {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                accessWebService();

            }
        }
        spRef = (Spinner)findViewById(R.id.refreshment_spinner);
        shortGlass = (CheckBox)findViewById(R.id.short_glass);
        longGlass = (CheckBox)findViewById(R.id.long_glass);
        comments = (EditText)findViewById(R.id.editText_gins);
        cart = (Button)findViewById(R.id.button_gins);

        shortGlass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    longGlass.setEnabled(false);
                }else{
                    longGlass.setEnabled(true);
                }
            }
        });
        longGlass.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    shortGlass.setEnabled(false);
                }else{
                    shortGlass.setEnabled(true);
                }
            }
        });
        buildRefreshmentsSpinner();
    }

    private void buildRefreshmentsSpinner() {
        String[] refreshments = getResources().getStringArray(R.array.refreshments_array);
        adapter = new ArrayAdapter<>(Gins.this, R.layout.spinner_layout, R.id.spinner_ref_id,refreshments);
        spRef.setAdapter(adapter);
    }


    public class JsonReadTask extends AsyncTask<String , Void, List<SpiritList>> {
        public JsonReadTask() {
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Gins.this, ProgressDialog.THEME_DEVICE_DEFAULT_DARK);
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
                    customSpinner.add(new SpiritList(name, price));
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
        task.execute(new String[]{url});
    }

    public void ListDrawer(List<SpiritList> customSpinner) {
        adapterGins = new SpiritsListAdapter(Gins.this ,customSpinner);
        spDrinks.setAdapter(adapterGins);
        Log.d("Spinner Count", "The Spinner count is " + spDrinks.getCount());
    }

}
