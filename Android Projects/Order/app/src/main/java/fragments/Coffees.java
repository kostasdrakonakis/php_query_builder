package fragments;


import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

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

import adapters.ProductListAdapter;
import cart.CoffeesLayoutActivity;
import functions.AppConstant;
import interfaces.CoffeeCommunicator;
import lists.ProductList;

public class Coffees extends Fragment{
    private View rootView;
    private ListView lv;
    private ArrayAdapter<ProductList> adapter;
    private String jsonResult, table;
    private String name, image, price;
    ProgressDialog pDialog;
    List<ProductList> customList;
    private TextView tv1, tv2;
    int pos;
    private CardView cardView;
    private String servitoros_id;
    private String magazi_id;
    private CoffeeCommunicator coffeeCommunicator;

    @Override
    public View onCreateView(LayoutInflater inflater,ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.coffees_fragment, container, false);
        lv = (ListView)rootView.findViewById(R.id.coffeesListView);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            lv.setNestedScrollingEnabled(true);
        }
        cardView = (CardView)rootView.findViewById(R.id.card_view);
        table = getActivity().getIntent().getStringExtra(AppConstant.TABLE_INTENT_ID);
        servitoros_id = getActivity().getIntent().getStringExtra(AppConstant.WAITER_INTENT_ID);
        magazi_id = getActivity().getIntent().getStringExtra(AppConstant.COMPANY_INTENT_ID);
        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.activity_main_swipe_refresh_layout);
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(getActivity().getApplicationContext().CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean network_connected = activeNetwork != null && activeNetwork.isAvailable() && activeNetwork.isConnectedOrConnecting();

        if (!network_connected) {
            onDetectNetworkState().show();
        } else {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                accessWebService();
                pDialog.dismiss();
                setRetainInstance(true);
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
        return rootView;
    }

    private AlertDialog onDetectNetworkState() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity().getApplicationContext());
        builder1.setMessage(R.string.wifi_off_message)
                .setTitle(R.string.wifi_off_title)
                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                getActivity().finish();
                            }
                        })
                .setPositiveButton(R.string.action_settings,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                startActivityForResult((new Intent(
                                        Settings.ACTION_WIFI_SETTINGS)), 1);
                                getActivity().finish();
                            }
                        });
        return builder1.create();
    }



    private void registerCallClickBack() {

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //pos = position;
                Intent intent = new Intent(getActivity(), CoffeesLayoutActivity.class);
                intent.putExtra(AppConstant.COFFEE_NAME, customList.get(position).getName());
                intent.putExtra(AppConstant.COFFEE_PRICE, customList.get(position).getPrice());
                intent.putExtra(AppConstant.COFFEE_IMAGE, customList.get(position).getImage());
                intent.putExtra(AppConstant.TABLE_INTENT_ID, table);
                intent.putExtra(AppConstant.WAITER_INTENT_ID, servitoros_id);
                intent.putExtra(AppConstant.COMPANY_INTENT_ID, magazi_id);
                startActivity(intent);
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
            if (onDetectNetworkState().isShowing()) {
                onDetectNetworkState().show();
            } else {
                onDetectNetworkState().dismiss();
            }
        }
    }


    public class JsonReadTask extends AsyncTask<String , Void, List<ProductList>> {
        public JsonReadTask() {
            super();
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity());
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setIndeterminate(true);
            pDialog.setMessage(getString(R.string.get_stocks));
            pDialog.setCancelable(false);
            pDialog.setInverseBackgroundForced(true);
            pDialog.show();
        }

        @Override
        protected List<ProductList> doInBackground(String... params) {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost(params[0]);
            try {
                HttpResponse response = httpclient.execute(httppost);
                jsonResult = inputStreamToString(
                        response.getEntity().getContent()).toString();
                customList = new ArrayList<>();

                JSONObject jsonResponse = new JSONObject(jsonResult);
                JSONArray jsonMainNode = jsonResponse.optJSONArray("kafedes");
                for (int i = 0; i < jsonMainNode.length(); i++) {
                    JSONObject jsonChildNode = jsonMainNode.getJSONObject(i);
                    name = jsonChildNode.optString("name");
                    price = jsonChildNode.optString("price");
                    image = jsonChildNode.optString("image");

                    customList.add(new ProductList(image, name, price));
                    coffeeCommunicator.sendCoffeeListData(name, image, price);

                }
                return customList;
            } catch (Exception e) {
                e.printStackTrace();
                getActivity().finish();
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
                getActivity().finish();
            }
            return answer;
        }

        @Override
        protected void onPostExecute(List<ProductList> customList) {
            if(customList == null){
                Log.d("ERORR", "No result to show.");
                return;
            }
            ListDrawer(customList);
            pDialog.dismiss();
        }
    }// end async task

    public void accessWebService() {
        JsonReadTask task = new JsonReadTask();
        task.execute(AppConstant.COFFEES_URL);
    }

    public void ListDrawer(List<ProductList> customList) {
        adapter = new ProductListAdapter(getActivity().getApplicationContext(), R.layout.productlist_row_item, customList);
        adapter.notifyDataSetChanged();
        lv.setAdapter(adapter);
    }


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        coffeeCommunicator = (CoffeeCommunicator)activity;
    }
}
