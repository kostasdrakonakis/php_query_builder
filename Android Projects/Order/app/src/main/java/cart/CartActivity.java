package cart;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.view.ActionMode;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.order.app.order.R;
import com.order.app.order.UserProfile;

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
import functions.Constants;
import functions.StringGenerator;
import lists.CartList;

public class CartActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private org.solovyev.android.views.llm.LinearLayoutManager layoutManager;
    private Toolbar toolbar;
    private String table, servitoros_id, magazi_id;
    private ProgressDialog progressDialog;
    private URL url;
    private HttpURLConnection httpURLConnection;
    private OutputStream outputStream;
    private InputStream inputStream;
    private BufferedWriter bufferedWriter;
    private String name, image, price, pref1, pref2, pref3, pref4, quantity, nameAdapter;
    private TextView totalText;
    private Button modify, checkout;
    private List<NameValuePair> nameValuePairs;
    private List<CartList> cartItems;
    private List<CartList> names = new ArrayList<>();
    private StringBuilder jsonResult;
    private JSONObject jsonResponse, jsonChildNode;
    private JSONArray jsonMainNode;
    private CartAdapter adapter;
    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;
    private boolean connected;
    float sum = 0;
    private int pos;
    private ActionMode actionMode;
    private LinearLayout rootLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        setupToolbar();
        checkNetwork();
        setupRecyclerView();
        totalText = (TextView) findViewById(R.id.totalText);
        modify = (Button) findViewById(R.id.modifyButton);
        checkout = (Button) findViewById(R.id.checkoutButton);
        rootLayout = (LinearLayout)findViewById(R.id.linear_cart_empty);
        setupButtonCallbacks();
        enableSwipeFunctions();

    }

    private void enableSwipeFunctions() {
        ItemTouchHelper.SimpleCallback simpleCallback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT|ItemTouchHelper.RIGHT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                if (cartItems.size() >0){
                    pos = viewHolder.getAdapterPosition();
                    removePrice(cartItems.get(pos).getPrice());
                    deleteDataWebService(cartItems.get(pos).getName());
                    adapter.remove(pos);
                    adapter.notifyItemRemoved(pos);
                    adapter.notifyItemRangeChanged(pos, cartItems.size());
                }

                //accessWebService();
            }
        };

        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(recyclerView);
    }

    private void removePrice(String price){
        if (cartItems.size()>0){
            float aftersum = Float.parseFloat(totalText.getText().toString().replace("€", " "));
            aftersum = aftersum - Float.parseFloat(price);
            String text = String.format("%.2f", aftersum);
            totalText.setText(String.valueOf(aftersum) + " €");
        }

    }


    private void setupButtonCallbacks() {
        modify.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                for (int position = 0; position < cartItems.size(); position++) {
                    cartItems.get(position).setCheckboxIsVisible(true);
                    adapter.notifyDataSetChanged();
                }
                actionMode = startSupportActionMode(actionModeCallback);
                actionMode.setTitle(getString(R.string.selected_modifiable_items));
            }
        });

        checkout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder checkOutDialog = new AlertDialog.Builder(CartActivity.this);
                checkOutDialog.setTitle(getString(R.string.checkout));
                checkOutDialog.setMessage(getString(R.string.checkout_message_dialog));
                checkOutDialog.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int position = 0; position < cartItems.size(); position++) {
                            nameAdapter = cartItems.get(position).getName();
                            deleteDataWebService(nameAdapter);
                        }
                        Intent intent = new Intent(CartActivity.this, UserProfile.class);
                        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        CartActivity.this.finish();
                        overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_left);
                        StringGenerator.showToast(CartActivity.this, getString(R.string.checkout_success_message));
                    }
                });
                checkOutDialog.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
                checkOutDialog.create();
                checkOutDialog.show();
            }
        });
    }

    ActionMode.Callback actionModeCallback = new ActionMode.Callback() {
        @Override
        public boolean onCreateActionMode(ActionMode mode, Menu menu) {
            getMenuInflater().inflate(R.menu.menu_cart_modify, menu);
            return true;
        }

        @Override
        public boolean onPrepareActionMode(ActionMode mode, Menu menu) {
            return false;
        }

        @Override
        public boolean onActionItemClicked(final ActionMode mode, MenuItem item) {
            switch (item.getItemId()){
                case R.id.deleteCartItemModification:{
                    names = adapter.getNames();
                    AlertDialog.Builder builder = new AlertDialog.Builder(CartActivity.this);
                    builder.setTitle(getString(R.string.delete));
                    builder.setMessage(getString(R.string.delete_msg));
                    builder.setPositiveButton(getString(R.string.yes), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            for (int position = 0; position < names.size(); position++) {
                                nameAdapter = names.get(position).getName();
                                deleteDataWebService(nameAdapter);
                                adapter.remove(position);
                            }
                            sum = 0;
                            setupTotalValue();
                            mode.finish();
                            if (names.isEmpty()){
                                if (rootLayout.getVisibility() == View.GONE){
                                    rootLayout.setVisibility(View.VISIBLE);
                                }else {
                                    rootLayout.setVisibility(View.GONE);
                                }
                                checkout.setOnClickListener(new View.OnClickListener() {
                                    @Override
                                    public void onClick(View v) {
                                        StringGenerator.showToast(CartActivity.this, getString(R.string.checkout_failure_message));
                                    }
                                });
                            }else {
                                accessWebService();
                            }
                        }
                    });
                    builder.setNegativeButton(getString(R.string.no), new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });
                    builder.create();
                    builder.show();
                    break;
                }
                case R.id.addCartItemModification:{
                    StringGenerator.showToast(CartActivity.this, getString(R.string.function_unavailable));
                    break;
                }
            }
            return true;
        }

        @Override
        public void onDestroyActionMode(ActionMode mode) {
            sum = 0;
            setupTotalValue();
            for (int position=0; position<cartItems.size();position++){
                cartItems.get(position).setCheckboxIsVisible(false);
                adapter.notifyDataSetChanged();
            }
        }
    };

    private void setupTotalValue() {
        for (int i = 0; i < cartItems.size(); i++) {
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
        } else {
            DialogMessageDisplay.displayWifiSettingsDialog(CartActivity.this, CartActivity.this, getString(R.string.wifi_off_title), getString(R.string.wifi_off_message));
        }
    }

    private void setupToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setTitle(R.string.checkout);
        magazi_id = getIntent().getStringExtra(Constants.PRODUCT_COMPANY_ID_VALUE_PAIR);
        table = getIntent().getStringExtra(Constants.TABLE_INTENT_ID);
        servitoros_id = getIntent().getStringExtra(Constants.WAITER_INTENT_ID);
        toolbar.setSubtitle(getString(R.string.table_id) + table);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            toolbar.setSubtitleTextColor(getColor(R.color.white));
        }else {
            toolbar.setSubtitleTextColor(getResources().getColor(R.color.white));
        }
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
        }

        @Override
        protected List<CartList> doInBackground(String... params) {
            nameValuePairs = new ArrayList<>();
            cartItems = new ArrayList<>();
            try {
                url = new URL(params[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setRequestProperty(Constants.CUSTOM_HEADER, Constants.API_KEY);
                httpURLConnection.setRequestMethod(Constants.METHOD_GET);
                httpURLConnection.connect();
                httpURLConnection.setConnectTimeout(5000);
                inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                jsonResult = StringGenerator.inputStreamToString(inputStream, CartActivity.this);
                jsonResponse = new JSONObject(jsonResult.toString());
                jsonMainNode = jsonResponse.optJSONArray(Constants.CART_JSON_ARRAY);
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
            if (cartLists.isEmpty()){
                if (rootLayout.getVisibility() == View.GONE){
                    rootLayout.setVisibility(View.VISIBLE);
                }else {
                    rootLayout.setVisibility(View.GONE);
                }
                checkout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        StringGenerator.showToast(CartActivity.this, getString(R.string.checkout_failure_message));
                    }
                });
            }else {
                adapter = new CartAdapter(cartLists, CartActivity.this);
                recyclerView.setAdapter(adapter);
                setupTotalValue();
            }
        }
    }

    private class DeleteData extends AsyncTask<String, Void, List<CartList>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            progressDialog = new ProgressDialog(CartActivity.this);
            progressDialog.setMessage(getString(R.string.delete_items_loading));
            progressDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
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
                deleteDataInDB(params[1]);
                outputStream = httpURLConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                bufferedWriter.write(StringGenerator.queryResults(nameValuePairs));
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                httpURLConnection.connect();
                inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                jsonResult = StringGenerator.inputStreamToString(inputStream, CartActivity.this);
                Log.e("Response: ", jsonResult.toString());
            } catch (IOException e) {
                e.printStackTrace();
            }
            return cartItems;
        }
        @Override
        protected void onPostExecute(List<CartList> cartLists) {
            super.onPostExecute(cartLists);
        }
    }

    private void deleteDataWebService(String name){
        DeleteData deleteData = new DeleteData();
        deleteData.execute(Constants.DELETE_URL, name);
    }


    private void accessWebService() {
        FetchData fetchData = new FetchData();
        fetchData.execute(Constants.CART_URL + servitoros_id + "/" + magazi_id + "/" + table + "/");
    }

    private void deleteDataInDB(String name) {
        nameValuePairs.add(new BasicNameValuePair(Constants.PRODUCT_NAME_VALUE_PAIR, name));
        nameValuePairs.add(new BasicNameValuePair(Constants.PRODUCT_COMPANY_ID_VALUE_PAIR, magazi_id));
        nameValuePairs.add(new BasicNameValuePair(Constants.PRODUCT_WAITER_ID_VALUE_PAIR, servitoros_id));
        nameValuePairs.add(new BasicNameValuePair(Constants.PRODUCT_TABLE_ID_VALUE_PAIR, table));
    }
}
