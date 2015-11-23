package cart;

import android.app.ProgressDialog;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.TextView;

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

import adapters.CartAdapter;
import dialogs.DialogMessageDisplay;
import functions.AppConstant;
import functions.StringGenerator;
import lists.CartList;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private org.solovyev.android.views.llm.LinearLayoutManager layoutManager;
    private Toolbar toolbar;
    private String table, servitoros_id;
    private ProgressDialog progressDialog;
    private URL url;
    private HttpURLConnection httpURLConnection;
    private OutputStream outputStream;
    private InputStream inputStream;
    private BufferedWriter bufferedWriter;
    private String name, image, price, pref1, pref2, pref3, pref4, quantity;
    private TextView totalText;
    private Button modify, checkout;
    private List<NameValuePair> nameValuePairs;
    private List<CartList> cartItems;
    private StringBuilder jsonResult;
    private JSONObject jsonResponse, jsonChildNode;
    private JSONArray jsonMainNode;
    private CartAdapter adapter;
    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;
    private boolean connected;
    float sum = 0;
    private CheckBox checkBox;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        setupToolbar();
        setupRecyclerView();
        totalText = (TextView) findViewById(R.id.totalText);
        modify = (Button) findViewById(R.id.modifyButton);

        checkout = (Button) findViewById(R.id.checkoutButton);
        checkNetwork();

    }

    private void setupButtonCallbacks() {
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkBox = (CheckBox)findViewById(R.id.deleteCheckCartItem);
                for (int position = 0; position<cartItems.size(); position++){
                    if (checkBox.getVisibility() == View.GONE){
                        checkBox.setVisibility(View.VISIBLE);
                    }else {
                        checkBox.setVisibility(View.GONE);
                    }
                }
            }
        });
    }

    private void setupTotalValue() {
        for (int i=0; i<cartItems.size();i++){
            sum = sum + Float.parseFloat(cartItems.get(i).getPrice());
        }
        String text = String.format("%.2f", sum);
        totalText.setText(String.valueOf(text) + " €");
    }

    private void setupRecyclerView() {
        recyclerView = (RecyclerView) findViewById(R.id.cartRecyclerView);
        layoutManager = new org.solovyev.android.views.llm.LinearLayoutManager(CartActivity.this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setNestedScrollingEnabled(true);
    }

    private void checkNetwork() {
        connectivityManager = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        connected = networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnectedOrConnecting();
        if (connected) {
            accessWebService();
            //setupButtonCallbacks();
        }else {
            DialogMessageDisplay.displayWifiSettingsDialog(CartActivity.this, CartActivity.this, getString(R.string.wifi_off_title), getString(R.string.wifi_off_message));
        }
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setTitle(R.string.checkout);
        table = getIntent().getStringExtra(AppConstant.TABLE_INTENT_ID);
        servitoros_id = getIntent().getStringExtra(AppConstant.WAITER_INTENT_ID);
        toolbar.setSubtitle(getString(R.string.table_id) + table);
        setSupportActionBar(toolbar);
    }


    private class FetchData extends AsyncTask<String, Void, List<CartList>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(CartActivity.this);
            progressDialog.setMessage(getString(R.string.get_stocks));
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.show();
        }

        @Override
        protected List<CartList> doInBackground(String... params) {
            nameValuePairs = new ArrayList<>();
            cartItems = new ArrayList<>();
            try {
                url = new URL(params[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(10000);
                httpURLConnection.setConnectTimeout(15000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                setupDataToDB();
                outputStream = httpURLConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                bufferedWriter.write(StringGenerator.queryResults(nameValuePairs));
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                httpURLConnection.connect();
                inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                jsonResult = StringGenerator.inputStreamToString(inputStream, CartActivity.this);
                Log.e("Responce:", jsonResult.toString());
                jsonResponse = new JSONObject(jsonResult.toString());
                jsonMainNode = jsonResponse.optJSONArray(AppConstant.CART_JSON_ARRAY);
                for (int i = 0; i < jsonMainNode.length(); i++) {
                    jsonChildNode = jsonMainNode.getJSONObject(i);
                    name = jsonChildNode.optString("product_name");
                    price = jsonChildNode.optString("product_price");
                    image = jsonChildNode.optString("product_image");
                    pref1 = jsonChildNode.optString("preferation1");
                    pref2 = jsonChildNode.optString("preferation2");
                    pref3 = jsonChildNode.optString("preferation3");
                    pref4 = jsonChildNode.optString("preferation4");
                    quantity = jsonChildNode.optString("quantity");
                    cartItems.add(new CartList(name, price, image, pref1, pref2, pref3, pref4, quantity));
                }

            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }

            return cartItems;
        }

        @Override
        protected void onPostExecute(List<CartList> cartLists) {
            super.onPostExecute(cartLists);
            progressDialog.dismiss();
            adapter = new CartAdapter(cartLists);
            adapter.notifyDataSetChanged();
            recyclerView.setAdapter(adapter);
            setupTotalValue();
        }
    }

    private void accessWebService() {
        FetchData fetchData = new FetchData();
        fetchData.execute(AppConstant.CART_URL);
    }


    private void setupDataToDB() {
        nameValuePairs.add(new BasicNameValuePair(AppConstant.PRODUCT_WAITER_ID_VALUE_PAIR, servitoros_id));
        nameValuePairs.add(new BasicNameValuePair(AppConstant.PRODUCT_TABLE_ID_VALUE_PAIR, table));
    }
}
