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
import cart.SnacksLayoutActivity;
import functions.AppConstant;
import functions.StringGenerator;
import interfaces.SnacksCommunicator;
import listeners.RecyclerItemClickListener;
import lists.ProductList;


public class Snacks extends Fragment {

    private View rootView;
    private RecyclerView recyclerView;
    private ProductsAdapter productsAdapter;
    private StringBuilder jsonResult;
    ProgressDialog pDialog;
    List<ProductList> customList;
    private String servitoros_id, magazi_id, table, name, image, price;
    private SnacksCommunicator snacksCommunicator;
    private GridLayoutManager layoutManager;
    private HttpURLConnection urlConnection;
    private URL url;
    private JSONObject jsonResponse, jsonChildNode;
    private JSONArray jsonMainNode;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.snacks_fragment, container, false);
        recyclerView = (RecyclerView)rootView.findViewById(R.id.snacksRecyclerView);
        checkOrientation();
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setHasFixedSize(true);
        recyclerView.setNestedScrollingEnabled(true);
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
            layoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 3);
        }else{
            layoutManager = new GridLayoutManager(getActivity().getApplicationContext(), 2);

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
                Intent intent = new Intent(getActivity(), SnacksLayoutActivity.class);
                intent.putExtra(AppConstant.SNACK_NAME, customList.get(position).getName());
                intent.putExtra(AppConstant.SNACK_PRICE, customList.get(position).getPrice());
                intent.putExtra(AppConstant.SNACK_IMAGE, customList.get(position).getImage());
                intent.putExtra(AppConstant.TABLE_INTENT_ID, table);
                intent.putExtra(AppConstant.WAITER_INTENT_ID, servitoros_id);
                intent.putExtra(AppConstant.COMPANY_INTENT_ID, magazi_id);
                startActivity(intent);
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
            pDialog.setMessage(getString(R.string.get_stocks));
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
            pDialog.setInverseBackgroundForced(true);
            pDialog.show();
        }

        @Override
        protected List<ProductList> doInBackground(String... params) {
            try {
                url = new URL(params[0]);
                urlConnection =(HttpURLConnection) url.openConnection();
                urlConnection.connect();
                urlConnection.setConnectTimeout(5000);
                InputStream in = new BufferedInputStream(urlConnection.getInputStream());
                jsonResult = StringGenerator.inputStreamToString(in, getActivity());
                customList = new ArrayList<>();

                jsonResponse = new JSONObject(jsonResult.toString());
                jsonMainNode = jsonResponse.optJSONArray(AppConstant.SNACKS_JSON_ARRAY);
                for (int i = 0; i < jsonMainNode.length(); i++) {
                    jsonChildNode = jsonMainNode.getJSONObject(i);
                    name = jsonChildNode.optString("name");
                    price = jsonChildNode.optString("price");
                    image = jsonChildNode.optString("image");
                    customList.add(new ProductList(image, name, price));
                    snacksCommunicator.sendSnackListData(name, image, price);

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
        task.execute(AppConstant.SNACKS_URL);
    }

    public void ListDrawer(List<ProductList> customList) {
        productsAdapter = new ProductsAdapter(customList, getActivity().getApplicationContext());
        productsAdapter.notifyDataSetChanged();
        recyclerView.setAdapter(productsAdapter);
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        snacksCommunicator = (SnacksCommunicator)activity;
    }
}
