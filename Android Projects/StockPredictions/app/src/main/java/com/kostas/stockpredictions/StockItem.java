package com.kostas.stockpredictions;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.app.TaskStackBuilder;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.koushikdutta.ion.Ion;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.DecimalFormat;

import graphs.LiveGraph;


public class StockItem extends ActionBarActivity {

    TextView tv, tv1;
    String name, price;
    String[] myItems, listItems;
    private ListView listaFake;
    private ArrayAdapter<String> adapter;
    private ImageView iv;
    private Toolbar tb;
    private double sum = 0, avg = 0;
    private NotificationCompat.Builder builder;
    private ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stock_item);
        tv = (TextView)findViewById(R.id.stockView1name);
        tv1 = (TextView)findViewById(R.id.stockView1price);
        Intent i = this.getIntent();
        name = i.getStringExtra("name");
        price = i.getStringExtra("price");
        tv.setText(name);
        tv1.setText(price);
        tb = (Toolbar)findViewById(R.id.toolBar);
        tb.setTitle(name);
        setSupportActionBar(tb);
        myItems = i.getStringArrayExtra("stockInfo");
        iv = (ImageView)findViewById(R.id.currentStockImageViewItem);
        Ion.with(iv).placeholder(R.drawable.ic_chat).error(R.drawable.ic_chat).load(i.getStringExtra("stockImage"));
        adapter = new ArrayAdapter<>(this,R.layout.list_stock_item_layout, myItems);
        listaFake = (ListView)findViewById(R.id.listafake1);
        listaFake.setAdapter(adapter);
        populateBottomToolBar();
    }

    private void populateBottomToolBar() {
        Button btngraph = (Button)findViewById(R.id.toolBar_button1);
        Button btnevaluate = (Button)findViewById(R.id.toolBar_button2);

        btngraph.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent intent = new Intent(StockItem.this, LiveGraph.class);
                intent.putExtra("stockprices", myItems);
                startActivity(intent);
            }
        });

        btnevaluate.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if(listaFake.isShown()){
                    listaFake.setVisibility(View.INVISIBLE);
                    LinearLayout ll = (LinearLayout)findViewById(R.id.layout_to_be_shown);
                    TextView evalText = (TextView)findViewById(R.id.textViewEvaluation);
                    for(int k=0;k<myItems.length;k++){
                        sum = sum + Double.parseDouble(myItems[k]);
                    }
                    TextView evalName = (TextView)findViewById(R.id.textViewEValName);
                    ImageView evalImage = (ImageView)findViewById(R.id.imageViewEval);
                    evalName.setText(name);
                    avg = sum/myItems.length;
                    if(avg > 10){
                        evalText.setText(": " + new DecimalFormat("###.##").format(avg));
                        evalText.setTextColor(Color.GREEN);
                        evalImage.setImageResource(R.drawable.green);
                    }else{
                        evalText.setText(": " + new DecimalFormat("###.##").format(avg));
                        evalText.setTextColor(Color.RED);
                        evalImage.setImageResource(R.drawable.red);
                    }

                    ll.setVisibility(View.VISIBLE);

                    Button notify = (Button)findViewById(R.id.buttonNotify);
                    notify.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            createNotification();
                        }
                    });
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_left);

    }


    class GetBitmapUrl extends AsyncTask<String, Void, Bitmap>{

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(StockItem.this);
            pDialog.setTitle(R.string.waiting);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setMessage(getString(R.string.get_stocks));
            pDialog.setIndeterminate(true);
            pDialog.setCancelable(false);
            pDialog.setInverseBackgroundForced(true);
            pDialog.show();
        }

        @Override
        protected Bitmap doInBackground(String... params) {
            try {
                URL url = new URL(params[0]);
                HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                connection.setDoInput(true);
                connection.connect();
                InputStream input = connection.getInputStream();
                Bitmap myBitmap = BitmapFactory.decodeStream(input);
                return myBitmap;
            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }
        }



        @Override
        protected void onPostExecute(Bitmap result) {
            builder.setLargeIcon(result);
            pDialog.dismiss();
        }
    }

    private void createNotification(){
        builder = new NotificationCompat.Builder(StockItem.this);
        builder.setContentTitle(name);
        if(avg > 10){
            builder.setContentText(getString(R.string.notifyText) + " " +  getString(R.string.raisal) + " " + getString(R.string.by) + " " + new DecimalFormat("###.##").format(avg) + " " + getString(R.string.units));
        }else{
            builder.setContentText(getString(R.string.notifyText) + " " +  getString(R.string.drop) + " " + getString(R.string.by) + " " + new DecimalFormat("###.##").format(avg) + " " + getString(R.string.units));
        }

        builder.setWhen(System.currentTimeMillis());
        builder.setSmallIcon(R.drawable.ic_launcher_stock);
        builder.setTicker(getString(R.string.notifyTicker));
        builder.setSound(Uri.parse(ContentResolver.SCHEME_ANDROID_RESOURCE
                + "://" + getPackageName() + "/raw/not"));

        builder.setDefaults(NotificationCompat.DEFAULT_LIGHTS | NotificationCompat.DEFAULT_VIBRATE);
        builder.setAutoCancel(true);
        Intent intent = new Intent(StockItem.this, ListLoaderActivity.class);
        TaskStackBuilder taskStackBuilder = TaskStackBuilder.create(StockItem.this);
        taskStackBuilder.addNextIntent(intent);
        taskStackBuilder.addParentStack(ListLoaderActivity.class);
        PendingIntent pendingIntent = taskStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);
        builder.setContentIntent(pendingIntent);
        Intent mailIntent = new Intent(Intent.ACTION_SEND);
        mailIntent.setType("message/rfc822");
        mailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{"admin@chatapp.info"});
        mailIntent.putExtra(Intent.EXTRA_SUBJECT, getString(R.string.notifyTicker));
        if(avg > 10){
            mailIntent.putExtra(Intent.EXTRA_TEXT,
                    getString(R.string.notifyTextEmail) + " " +
                            name + " " +
                            getString(R.string.raised) + " " +
                            getString(R.string.by) + " " +
                            new DecimalFormat("###.##").format(avg) + " " +
                            getString(R.string.units));
        }else{
            mailIntent.putExtra(Intent.EXTRA_TEXT,
                    getString(R.string.notifyTextEmail) + " " +
                            name + " " +
                            getString(R.string.dropped) + " " +
                            getString(R.string.by) + " " +
                            new DecimalFormat("###.##").format(avg) + " " +
                            getString(R.string.units));
        }

        mailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        builder.setFullScreenIntent(pendingIntent, true);

        PendingIntent pIntent = PendingIntent.getActivity(StockItem.this, 12, mailIntent ,PendingIntent.FLAG_UPDATE_CURRENT);
        builder.addAction(android.R.drawable.sym_action_email, getString(R.string.sendEmail), pIntent);


        Intent shareIntent = new Intent(android.content.Intent.ACTION_SEND);
        shareIntent.setType("text/plain");
        shareIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, getString(R.string.notifyTicker));
        if(avg > 10){
            shareIntent.putExtra(Intent.EXTRA_TEXT,
                    getString(R.string.notifyTextEmail) + " " +
                            name + " " +
                            getString(R.string.raised) + " " +
                            getString(R.string.by) + " " +
                            new DecimalFormat("###.##").format(avg) + " " +
                            getString(R.string.units));
        }else{
            shareIntent.putExtra(Intent.EXTRA_TEXT,
                    getString(R.string.notifyTextEmail) + " " +
                            name + " " +
                            getString(R.string.dropped) + " " +
                            getString(R.string.by) + " " +
                            new DecimalFormat("###.##").format(avg) + " " +
                            getString(R.string.units));
        }


        PendingIntent sharePendingIntent = PendingIntent.getActivity(StockItem.this, 13, shareIntent, PendingIntent.FLAG_UPDATE_CURRENT);

        builder.addAction(android.R.drawable.ic_menu_share, getString(R.string.share), sharePendingIntent);
        builder.setFullScreenIntent(pIntent, true);
        builder.setFullScreenIntent(sharePendingIntent, true);
        NotificationManager manager = (NotificationManager)getSystemService(Context.NOTIFICATION_SERVICE);
        manager.notify(1, builder.build());
    }

    public void getBitmap(){
        GetBitmapUrl task = new GetBitmapUrl();
        task.execute(getIntent().getStringExtra("stockImage"));
    }
}
