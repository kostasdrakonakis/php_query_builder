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

import activityobjects.XDAObject;
import adapters.XDABaseAdapter;
import parsers.XMLParserXDA;


public class XDA extends ActionBarActivity {

    private ListView lv;
    private ArrayList<XDAObject> xdaObjectArrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xd);
        Toolbar tb = (Toolbar)findViewById(R.id.toolBar);
        setSupportActionBar(tb);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        lv = (ListView)findViewById(R.id.xdaList);
        final SwipeRefreshLayout mSwipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.activity_main_swipe_refresh_layout);
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if (xdaObjectArrayList != null && xdaObjectArrayList.size() > 0) {
                    Intent intentShowPost = new Intent(Intent.ACTION_VIEW, Uri.parse(xdaObjectArrayList.get(position).getGuid()));
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
        XMLParserXDA helper;


        @Override
        protected void onPreExecute() {
            pd = ProgressDialog.show(XDA.this, "Παρακαλώ Περιμένετε", "Φόρτωση δεδομένων απο xda-developers.com...", true, false);
        }

        @Override
        protected Void doInBackground(Void... arg0) {
            helper = new XMLParserXDA();
            helper.get();
            xdaObjectArrayList = helper.getPostsList();
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            XDABaseAdapter postBaseAdapter = new XDABaseAdapter(XDA.this, xdaObjectArrayList);
            lv.setAdapter(postBaseAdapter);
            pd.dismiss();
        }

    }
}
