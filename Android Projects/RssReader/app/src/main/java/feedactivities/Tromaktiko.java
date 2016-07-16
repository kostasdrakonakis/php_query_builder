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

import activityobjects.TromaktikoObject;
import adapters.TromaktikoBaseAdapter;
import parsers.XMLParserTromaktiko;


public class Tromaktiko extends ActionBarActivity {
    private ListView lv;
    private ArrayList<TromaktikoObject> tromaktikoObjectArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tromaktiko);
        Toolbar tb = (Toolbar)findViewById(R.id.toolBar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        lv = (ListView)findViewById(R.id.tromaktikoList);
        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (tromaktikoObjectArrayList != null && tromaktikoObjectArrayList.size() > 0) {
                    Intent intentShowPost = new Intent(Intent.ACTION_VIEW, Uri.parse(tromaktikoObjectArrayList.get(position).getLink()));
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
        XMLParserTromaktiko helper;


        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(Tromaktiko.this, "Παρακαλώ Περιμένετε", "Φόρτωση δεδομένων απο tromaktiko.blogspot.com...", true, false);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            helper = new XMLParserTromaktiko();
            helper.get();
            tromaktikoObjectArrayList = helper.getPostsList();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            TromaktikoBaseAdapter postBaseAdapter = new TromaktikoBaseAdapter(Tromaktiko.this, tromaktikoObjectArrayList);
            lv.setAdapter(postBaseAdapter);
            pd.dismiss();
        }

    }


}
