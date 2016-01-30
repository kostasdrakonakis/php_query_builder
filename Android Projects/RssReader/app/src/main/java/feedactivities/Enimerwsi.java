package feedactivities;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.kostas.myapplication.R;

import java.util.ArrayList;

import activityobjects.PostValue;
import adapters.PostBaseAdapter;
import parsers.XMLParserEnimerwsi;


public class Enimerwsi extends ActionBarActivity {

    private ListView lv;
    private ArrayList<PostValue> postValueArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_enimerwsi);
        Toolbar tb = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        lv = (ListView) findViewById(R.id.enimerwsiList);
        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout)findViewById(R.id.activity_main_swipe_refresh_layout);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new PostAsync().execute();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (postValueArrayList != null && postValueArrayList.size() > 0) {
                    Intent intentShowPost = new Intent(Intent.ACTION_VIEW, Uri.parse(postValueArrayList.get(position).getGuid()));
                    startActivity(Intent.createChooser(intentShowPost, "Choose"));


                }
            }
        });
        new PostAsync().execute();
    }

    class PostAsync extends AsyncTask<Void, Void, Void> {
        ProgressDialog pd;
        XMLParserEnimerwsi helper;


        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(Enimerwsi.this, "Παρακαλώ Περιμένετε", "Φόρτωση δεδομένων απο stackoverflow.com/tags?=android...", true, false);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            helper = new XMLParserEnimerwsi();
            helper.get();
            postValueArrayList = helper.getPostsList();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            PostBaseAdapter postBaseAdapter = new PostBaseAdapter(Enimerwsi.this, postValueArrayList);
            lv.setAdapter(postBaseAdapter);
            pd.dismiss();
        }

    }



}
