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
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

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
import lists.ProductList;


public class Beer extends Fragment {

    private View rootView;
    private ListView lv;
    private ArrayAdapter<ProductList> adapter;
    private String jsonResult, rLine;
    private StringBuilder answer;
    private BufferedReader rd;
    private String url = "http://my.chatapp.info/order_api/files/getbeers.php";
    ProgressDialog pDialog;
    private JsonReadTask task;
    private HttpClient httpclient;
    private HttpPost httppost;
    private ConnectivityManager cm;
    private NetworkInfo activeNetwork;
    private SwipeRefreshLayout mSwipeRefreshLayout;
    List<ProductList> customList;
    private boolean network_connected;
    private HttpResponse response;
    private JSONArray jsonMainNode;
    private JSONObject jsonResponse, jsonChildNode;


    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.beer_fragment, container, false);
        lv = (ListView)rootView.findViewById(R.id.beerListView);
        mSwipeRefreshLayout = (SwipeRefreshLayout) rootView.findViewById(R.id.activity_main_swipe_refresh_layout);
        cm = (ConnectivityManager) getActivity().getSystemService(getActivity().getApplicationContext().CONNECTIVITY_SERVICE);
        activeNetwork = cm.getActiveNetworkInfo();
        network_connected = activeNetwork != null && activeNetwork.isAvailable() && activeNetwork.isConnectedOrConnecting();

        if (!network_connected) {
            onDetectNetworkState().show();
        } else {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) {
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
                Toast.makeText(getActivity().getApplicationContext(), "You have chosen " + customList.get(position).getName(), Toast.LENGTH_SHORT).show();
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
            httpclient = new DefaultHttpClient();
            httppost = new HttpPost(params[0]);
            try {
                response = httpclient.execute(httppost);
                jsonResult = inputStreamToString(
                        response.getEntity().getContent()).toString();
                customList = new ArrayList<>();

                jsonResponse = new JSONObject(jsonResult);
                jsonMainNode = jsonResponse.optJSONArray("beers");
                for (int i = 0; i < jsonMainNode.length(); i++) {
                    jsonChildNode = jsonMainNode.getJSONObject(i);
                    String name = jsonChildNode.optString("name");
                    String price = jsonChildNode.optString("price");
                    String image = jsonChildNode.optString("image");
                    customList.add(new ProductList(image, name, price));

                }
                return customList;
            } catch (Exception e) {
                e.printStackTrace();
                getActivity().finish();
            }
            return null;
        }

        private StringBuilder inputStreamToString(InputStream is) {
            rLine = "";
            answer = new StringBuilder();
            rd = new BufferedReader(new InputStreamReader(is));
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
        task = new JsonReadTask();
        task.execute(new String[]{url});
    }

    public void ListDrawer(List<ProductList> customList) {
        adapter = new ProductListAdapter(getActivity().getApplicationContext(), R.layout.productlist_row_item, customList);
        adapter.notifyDataSetChanged();
        lv.setAdapter(adapter);
    }

}
