package com.order.app.order;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;


public class Tables extends ActionBarActivity {

    private Button table1, table2, table3, table4, table5, table6, table7, table8, table9, table10, table11, table12, tableK1, tableK2, tableK3, tableBar1,tableBar2, tableBar3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tables);
        Toolbar tb = (Toolbar)findViewById(R.id.topBar);
        setSupportActionBar(tb);
        setupTables();
        setupTableListeners();
    }

    private void setupTables() {
        table1 = (Button)findViewById(R.id.table1);
        table2 = (Button)findViewById(R.id.table2);
        table3 = (Button)findViewById(R.id.table3);
        table4 = (Button)findViewById(R.id.table4);
        table5 = (Button)findViewById(R.id.table5);
        table6 = (Button)findViewById(R.id.table6);
        table7 = (Button)findViewById(R.id.table7);
        table8 = (Button)findViewById(R.id.table8);
        table9 = (Button)findViewById(R.id.table9);
        table10 = (Button)findViewById(R.id.table10);
        table11= (Button)findViewById(R.id.table11);
        table12 = (Button)findViewById(R.id.table12);
        tableK1 = (Button)findViewById(R.id.tableK1);
        tableK2 = (Button)findViewById(R.id.tableK2);
        tableK3 = (Button)findViewById(R.id.tableK3);
        tableBar1 = (Button)findViewById(R.id.tableBar1);
        tableBar2 = (Button)findViewById(R.id.tableBar2);
        tableBar3 = (Button)findViewById(R.id.tableBar3);
    }

    private void setupTableListeners() {
        table1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tables.this, NewOrder.class);
                intent.putExtra("tableID", table1.getText().toString());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                finish();
            }
        });
        table2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tables.this, NewOrder.class);
                intent.putExtra("tableID", table2.getText().toString());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                finish();
            }
        });
        table3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tables.this, NewOrder.class);
                intent.putExtra("tableID", table3.getText().toString());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                finish();
            }
        });
        table4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tables.this, NewOrder.class);
                intent.putExtra("tableID", table4.getText().toString());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                finish();
            }
        });
        table5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tables.this, NewOrder.class);
                intent.putExtra("tableID", table5.getText().toString());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                finish();
            }
        });
        table6.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tables.this, NewOrder.class);
                intent.putExtra("tableID", table6.getText().toString());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                finish();
            }
        });
        table7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tables.this, NewOrder.class);
                intent.putExtra("tableID", table7.getText().toString());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                finish();
            }
        });
        table8.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tables.this, NewOrder.class);
                intent.putExtra("tableID", table8.getText().toString());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                finish();
            }
        });
        table9.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tables.this, NewOrder.class);
                intent.putExtra("tableID", table9.getText().toString());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                finish();
            }
        });
        table10.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tables.this, NewOrder.class);
                intent.putExtra("tableID", table10.getText().toString());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                finish();
            }
        });
        table11.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tables.this, NewOrder.class);
                intent.putExtra("tableID", table11.getText().toString());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                finish();
            }
        });
        table12.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tables.this, NewOrder.class);
                intent.putExtra("tableID", table12.getText().toString());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                finish();
            }
        });
        tableK1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tables.this, NewOrder.class);
                intent.putExtra("tableID", tableK1.getText().toString());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                finish();
            }
        });
        tableK2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tables.this, NewOrder.class);
                intent.putExtra("tableID", tableK2.getText().toString());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                finish();
            }
        });
        tableK3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tables.this, NewOrder.class);
                intent.putExtra("tableID", tableK3.getText().toString());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                finish();
            }
        });
        tableBar1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tables.this, NewOrder.class);
                intent.putExtra("tableID", tableBar1.getText().toString());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                finish();
            }
        });
        tableBar2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tables.this, NewOrder.class);
                intent.putExtra("tableID", tableBar2.getText().toString());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                finish();
            }
        });
        tableBar3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Tables.this, NewOrder.class);
                intent.putExtra("tableID", tableBar3.getText().toString());
                startActivity(intent);
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        overridePendingTransition(R.anim.slide_out_left, R.anim.slide_in_left);
    }
}
