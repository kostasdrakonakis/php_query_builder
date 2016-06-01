package fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.Settings;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

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

import adapters.ProductsAdapter;
import dialogs.DialogMessageDisplay;
import functions.Constants;
import functions.StringGenerator;
import listeners.RecyclerItemClickListener;
import lists.ProductList;


public class Spirits extends Fragment {

    private View rootView;
    private RecyclerView recyclerView;
    private ProductsAdapter productsAdapter;
    private String servitoros_id, magazi_id, table, name, image, price;
    private StringBuilder jsonResult;
    ProgressDialog pDialog;
    ArrayList<ProductList> customList;
    private GridLayoutManager layoutManager;
    private HttpURLConnection urlConnection;
    private URL url;
    private JSONObject jsonResponse, jsonChildNode;
    private JSONArray jsonMainNode;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.spirits_fragment, container, false);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.spiritsRecyclerView);
        checkOrientation();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(true);
        table = getActivity().getIntent().getStringExtra(Constants.TABLE_INTENT_ID);
        servitoros_id = getActivity().getIntent().getStringExtra(Constants.WAITER_INTENT_ID);
        magazi_id = getActivity().getIntent().getStringExtra(Constants.COMPANY_INTENT_ID);
        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.activity_main_swipe_refresh_layout);
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(getActivity().getApplicationContext().CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean network_connected = activeNetwork != null && activeNetwork.isAvailable() && activeNetwork.isConnectedOrConnecting();

        if (!network_connected) {
            onDetectNetworkState().show();
        } else {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                accessWebService();
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

    private void checkOrientation() {
        if (getActivity().getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE){
            layoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 2);
        }else{
            layoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 1);

        }
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
        recyclerView.addOnItemTouchListener(new RecyclerItemClickListener(getActivity().getApplicationContext(), new RecyclerItemClickListener.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                switch (position) {
                    case 0: {
                        Intent intent = new Intent(getActivity().getApplicationContext(), Gins.class);
                        intent.putExtra(Constants.SPIRIT_ITEM, customList.get(position).getName());
                        intent.putExtra(Constants.TABLE_INTENT_ID, table);
                        intent.putExtra(Constants.WAITER_INTENT_ID, servitoros_id);
                        intent.putExtra(Constants.COMPANY_INTENT_ID, magazi_id);
                        startActivity(intent);
                        break;
                    }
                    case 1: {
                        Intent intent = new Intent(getActivity().getApplicationContext(), Vodkas.class);
                        intent.putExtra(Constants.SPIRIT_ITEM, customList.get(position).getName());
                        intent.putExtra(Constants.TABLE_INTENT_ID, table);
                        intent.putExtra(Constants.WAITER_INTENT_ID, servitoros_id);
                        intent.putExtra(Constants.COMPANY_INTENT_ID, magazi_id);
                        startActivity(intent);
                        break;
                    }
                    case 2: {
                        Intent intent = new Intent(getActivity().getApplicationContext(), Whiskeys.class);
                        intent.putExtra(Constants.SPIRIT_ITEM, customList.get(position).getName());
                        intent.putExtra(Constants.TABLE_INTENT_ID, table);
                        intent.putExtra(Constants.WAITER_INTENT_ID, servitoros_id);
                        intent.putExtra(Constants.COMPANY_INTENT_ID, magazi_id);
                        startActivity(intent);
                        break;
                    }
                    case 3: {
                        Intent intent = new Intent(getActivity().getApplicationContext(), Liquers.class);
                        intent.putExtra(Constants.SPIRIT_ITEM, customList.get(position).getName());
                        intent.putExtra(Constants.TABLE_INTENT_ID, table);
                        intent.putExtra(Constants.WAITER_INTENT_ID, servitoros_id);
                        intent.putExtra(Constants.COMPANY_INTENT_ID, magazi_id);
                        startActivity(intent);
                        break;
                    }
                    case 4: {
                        Intent intent = new Intent(getActivity().getApplicationContext(), Tequilas.class);
                        intent.putExtra(Constants.SPIRIT_ITEM, customList.get(position).getName());
                        intent.putExtra(Constants.TABLE_INTENT_ID, table);
                        intent.putExtra(Constants.WAITER_INTENT_ID, servitoros_id);
                        intent.putExtra(Constants.COMPANY_INTENT_ID, magazi_id);
                        startActivity(intent);
                        break;
                    }
                    case 5: {
                        Intent intent = new Intent(getActivity().getApplicationContext(), Rums.class);
                        intent.putExtra(Constants.SPIRIT_ITEM, customList.get(position).getName());
                        intent.putExtra(Constants.TABLE_INTENT_ID, table);
                        intent.putExtra(Constants.WAITER_INTENT_ID, servitoros_id);
                        intent.putExtra(Constants.COMPANY_INTENT_ID, magazi_id);
                        startActivity(intent);
                        break;
                    }
                    default: {
                        Log.d("No Case", "You have done something wrong");
                        DialogMessageDisplay.displayInfoMessage(getActivity().getApplicationContext(), "Error", "Please try again");
                        break;
                    }
                }
            }
        }));
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
            try {
                url = new URL(params[0]);
                urlConnection =(HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty(Constants.CUSTOM_HEADER, Constants.API_KEY);
                urlConnection.setRequestMethod(Constants.METHOD_GET);
                urlConnection.setConnectTimeout(5000);
                urlConnection.connect();
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                jsonResult = StringGenerator.inputStreamToString(in, getActivity());
                customList = new ArrayList<>();

                jsonResponse = new JSONObject(jsonResult.toString());
                Log.e("Response spirits: ", jsonResponse.toString());
                jsonMainNode = jsonResponse.optJSONArray(Constants.SPIRITS_JSON_ARRAY);
                for (int i = 0; i < jsonMainNode.length(); i++) {
                    jsonChildNode = jsonMainNode.getJSONObject(i);
                    name = jsonChildNode.optString("type");
                    price = jsonChildNode.optString("price");
                    image = jsonChildNode.optString("image");
                    customList.add(new ProductList(image, name, price));
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return customList;
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
        task.execute(Constants.SPIRITS_URL);
    }

    public void ListDrawer(List<ProductList> customList) {
        productsAdapter = new ProductsAdapter(customList, getActivity().getApplicationContext());
        productsAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(productsAdapter);
    }
}
