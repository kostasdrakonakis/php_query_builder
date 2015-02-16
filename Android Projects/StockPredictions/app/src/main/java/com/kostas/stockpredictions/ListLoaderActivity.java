package com.kostas.stockpredictions;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

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

import adapters.MyStocksAdapter;
import dialogs.MyMessageDialog;
import lists.StockList;

public class ListLoaderActivity extends ActionBarActivity {

    private String jsonResult;
    private String url = "http://www.chatapp.info/myProject/getstocks.php";
    private ListView startList;
    public static final String PREFS_NAME = "MyPrefsFile1";
    ProgressDialog pDialog;
    List<StockList> customList;
    ImageButton fab;
    public CheckBox dontShowAgain;
    private String name, price, price1, price2, price3, price4, price5, price6, price7, price8, price9,
            price10, price11, price12, price13, price14, price15, image;
    private TextView tv1, tv2;
    private String[] loipesTimes, justPrices;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_loader);
        Toolbar tb = (Toolbar) findViewById(R.id.toolBar);

        setSupportActionBar(tb);
        startList = (ListView) findViewById(R.id.startingList);
        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        ConnectivityManager cm = (ConnectivityManager) getSystemService(CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean network_connected = activeNetwork != null && activeNetwork.isAvailable() && activeNetwork.isConnectedOrConnecting();

        if (!network_connected) {
            onDetectNetworkState().show();
        } else {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
                accessWebService();
                registerCallClickBack();
                mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        accessWebService();
                        mSwipeRefreshLayout.setRefreshing(false);
                    }
                });
            }
            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                SharedPreferences settings = getSharedPreferences(PREFS_NAME, 0);
                String skipMessage = settings.getString("skipMessage",
                        "NOT checked");
                if (!skipMessage.equals("checked")) {
                    onAlertMobileData().show();

                }
                if (skipMessage.equals("checked")) {
                    accessWebService();
                    registerCallClickBack();
                    mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                        @Override
                        public void onRefresh() {
                            accessWebService();
                            mSwipeRefreshLayout.setRefreshing(false);
                        }
                    });
                }
            }
        }
    }

    private AlertDialog onAlertMobileData() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        LayoutInflater adbInflater = LayoutInflater.from(this);
        View eulaLayout = adbInflater.inflate(R.layout.checkbox, null);
        dontShowAgain = (CheckBox) eulaLayout.findViewById(R.id.skip);
        builder.setTitle(getString(R.string.alert_title))
                .setView(eulaLayout)
                .setMessage(R.string.alert_message)
                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                String checkBoxResult = "NOT checked";
                                if (dontShowAgain.isChecked())
                                    checkBoxResult = "checked";
                                SharedPreferences settings = getSharedPreferences(
                                        PREFS_NAME, 0);
                                SharedPreferences.Editor editor = settings
                                        .edit();
                                editor.putString("skipMessage", checkBoxResult);
                                // Commit the edits!
                                editor.commit();
                                ListLoaderActivity.this.finish();
                            }
                        })
                .setPositiveButton(R.string.ok,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                String checkBoxResult = "NOT checked";
                                if (dontShowAgain.isChecked())
                                    checkBoxResult = "checked";
                                SharedPreferences settings = getSharedPreferences(
                                        PREFS_NAME, 0);
                                SharedPreferences.Editor editor = settings
                                        .edit();
                                editor.putString("skipMessage", checkBoxResult);
                                editor.commit();
                                accessWebService();
                                registerCallClickBack();
                            }
                        });
        return builder.create();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu_list_loader, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);
        switch (item.getItemId()) {
            case R.id.about: {
                MyMessageDialog.displayMessage(ListLoaderActivity.this, "Stock Predictions - Version 1.0", "Δρακωνάκης Κωνσταντίνος\nΑ.Μ. = 2766\nΤμήμα Μηχανικών Πληροφορικής\nΠτυχιακή Εργασία");
                return true;
            }
            default: {
                return false;
            }
        }
    }

    private AlertDialog onDetectNetworkState() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(this);
        builder1.setMessage(R.string.wifi_off_message)
                .setTitle(R.string.wifi_off_title)
                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                ListLoaderActivity.this.finish();
                            }
                        })
                .setPositiveButton(R.string.action_settings,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                startActivityForResult((new Intent(
                                        Settings.ACTION_WIFI_SETTINGS)), 1);
                                ListLoaderActivity.this.finish();
                            }
                        });
        return builder1.create();
    }

    private void registerCallClickBack() {
        startList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View viewClicked, int position, long id) {
                tv1 = (TextView) viewClicked.findViewById(R.id.stock_name);
                tv2 = (TextView) viewClicked.findViewById(R.id.stock_price);
                Intent intent = new Intent(ListLoaderActivity.this, StockItem.class);
                intent.putExtra("name", tv1.getText().toString());
                intent.putExtra("price", tv2.getText().toString());
                intent.putExtra("stockInfo", customList.get(position).getRestPrices());
                intent.putExtra("stockImage", customList.get(position).getStockImage());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
            }
        });
        fab = (ImageButton) findViewById(R.id.add_button);

        startList.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if(scrollState == SCROLL_STATE_FLING || scrollState == SCROLL_STATE_TOUCH_SCROLL){
                    Ion.getDefault(ListLoaderActivity.this).cancelAll(ListLoaderActivity.this);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                accessWebService();
            }
        });
    }



    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {
            if (pDialog.isShowing()) {
                pDialog.show();
            } else {
                pDialog.dismiss();
            }
            if (onAlertMobileData() != null)
                if (onAlertMobileData().isShowing()) {
                    onAlertMobileData().show();
                } else {
                    onAlertMobileData().dismiss();
                }
            else {
                onAlertMobileData().dismiss();
            }
            if (onDetectNetworkState().isShowing()
                    && onDetectNetworkState() != null) {
                onDetectNetworkState().show();
            } else {
                onDetectNetworkState().dismiss();
            }
        }
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

            if (pDialog.isShowing()) {
                pDialog.show();
            } else {
                pDialog.dismiss();
            }

            if (onAlertMobileData().isShowing()) {
                onAlertMobileData().show();
            } else {
                onAlertMobileData().dismiss();
            }
            if (onDetectNetworkState().isShowing()) {
                onDetectNetworkState().show();
            } else {
                onDetectNetworkState().dismiss();
            }
        }
    }

    public class JsonReadTask extends AsyncTask<String, Void, String> {
        public JsonReadTask() {
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(ListLoaderActivity.this);
            pDialog.setTitle(R.string.waiting);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setMessage(getString(R.string.get_stocks));
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
            pDialog.setInverseBackgroundForced(true);
            pDialog.show();
        }

        @Override
        protected String doInBackground(String... params) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(params[0]);
            try {
                HttpResponse response = httpclient.execute(httppost);
                jsonResult = inputStreamToString(
                        response.getEntity().getContent()).toString();
            } catch (Exception e) {
                Intent intent1 = new Intent(ListLoaderActivity.this,
                        RefreshActivity.class);
                startActivity(intent1);
                ListLoaderActivity.this.finish();
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
                Intent intent1 = new Intent(ListLoaderActivity.this,
                        RefreshActivity.class);
                startActivity(intent1);
                ListLoaderActivity.this.finish();
            }
            return answer;
        }

        @Override
        protected void onPostExecute(String result) {
            ListDrawer();
            pDialog.dismiss();
        }
    }// end async task

    public void accessWebService() {
        JsonReadTask task = new JsonReadTask();
        task.execute(new String[]{url});
    }

    public void ListDrawer() {
        customList = new ArrayList<StockList>();
        try {
            JSONObject jsonResponse = new JSONObject(jsonResult);
            JSONArray jsonMainNode = jsonResponse.optJSONArray("metoxes");
            for (int i = 0; i < jsonMainNode.length(); i++) {
                JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                name = jsonChildNode.optString("name");
                price = jsonChildNode.optString("price");
                price1 = jsonChildNode.optString("price1");
                price2 = jsonChildNode.optString("price2");
                price3 = jsonChildNode.optString("price3");
                price4 = jsonChildNode.optString("price4");
                price5 = jsonChildNode.optString("price5");
                price6 = jsonChildNode.optString("price6");
                price7 = jsonChildNode.optString("price7");
                price8 = jsonChildNode.optString("price8");
                price9 = jsonChildNode.optString("price9");
                price10 = jsonChildNode.optString("price10");
                price11 = jsonChildNode.optString("price11");
                price12 = jsonChildNode.optString("price12");
                price13 = jsonChildNode.optString("price13");
                price14 = jsonChildNode.optString("price14");
                price15 = jsonChildNode.optString("price15");

                image = jsonChildNode.optString("image");

                justPrices = new String[]{price1, price2,
                        price3, price4, price5, price6, price7, price8, price9,
                        price10, price11, price12, price13, price14, price15};
                loipesTimes = new String[]{"1st Day Value " + price1, "2nd Day Value " + price2, "3rd Day Value " + price3, "4th Day Value " + price4, "5th Day Value " + price5,
                        "6th Day Value " + price6, "7th Day Value " + price7, "8th Day Value " + price8, "9th Day Value " + price9,
                        "10th Day Value " + price10, "11th Day Value " + price11, "12th Day Value " + price12, "13th Day Value " + price13, "14th Day Value " + price14, "15th Day Value " + price15};
                customList.add(new StockList(name, price, image, justPrices));

            }
        } catch (Exception e) {
            Intent intent1 = new Intent(ListLoaderActivity.this,
                    RefreshActivity.class);
            startActivity(intent1);
            ListLoaderActivity.this.finish();
        }

        ArrayAdapter adapter = new MyStocksAdapter(ListLoaderActivity.this, R.layout.list_item, customList);
        adapter.notifyDataSetChanged();
        startList.setAdapter(adapter);
    }

}
