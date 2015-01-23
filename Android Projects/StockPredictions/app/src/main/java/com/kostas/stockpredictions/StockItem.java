package com.kostas.stockpredictions;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;

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
                /*Intent intent = new Intent(StockItem.this, EvaluationActivity.class);
                startActivity(intent);*/
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
                        evalText.setText(": " + String.valueOf(avg));
                        evalText.setTextColor(Color.GREEN);
                        evalImage.setImageResource(R.drawable.green);
                    }else{
                        evalText.setText(": " + String.valueOf(avg));
                        evalText.setTextColor(Color.RED);
                        evalImage.setImageResource(R.drawable.red);
                    }

                    ll.setVisibility(View.VISIBLE);
                }
            }
        });
    }

    @Override
    public void onBackPressed() {
        finish();
        overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_left);

    }
}
