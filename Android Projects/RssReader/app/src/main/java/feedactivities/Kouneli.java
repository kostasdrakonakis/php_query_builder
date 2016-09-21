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

import activityobjects.KouneliObject;
import adapters.TreloKouneliBaseAdapter;
import parsers.XMLParserTreloKouneli;


public class Kouneli extends ActionBarActivity {
    private ListView lv;
    private ArrayList<KouneliObject> kouneliObjectArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_kouneli);
        Toolbar tb = (Toolbar)findViewById(R.id.toolBar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);

        lv = (ListView)findViewById(R.id.kouneliList);
        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (kouneliObjectArrayList != null && kouneliObjectArrayList.size() > 0) {
                    Intent intentShowPost = new Intent(Intent.ACTION_VIEW, Uri.parse(kouneliObjectArrayList.get(position).getLink()));
                    startActivity(intentShowPost);
                }
            }
        });
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                new PostAsync().execute();
                mSwipeRefreshLayout.setRefreshing(false);
            }
        });
        new PostAsync().execute();

    }


    class PostAsync extends AsyncTask<Void, Void, Void> {
        ProgressDialog pd;
        XMLParserTreloKouneli helper;


        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(Kouneli.this, "Παρακαλώ Περιμένετε", "Φόρτωση δεδομένων απο trelokouneli.gr...", true, false);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            helper = new XMLParserTreloKouneli();
            helper.get();
            kouneliObjectArrayList = helper.getPostsList();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            TreloKouneliBaseAdapter postBaseAdapter = new TreloKouneliBaseAdapter(Kouneli.this, kouneliObjectArrayList);
            lv.setAdapter(postBaseAdapter);
            pd.dismiss();
        }

    }
    }


