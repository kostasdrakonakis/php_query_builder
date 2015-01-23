package com.example.kostas.functions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;

import com.example.kostas.adapters.CartAdapter;
import com.example.kostas.lists.CartList;
import com.example.kostas.ordertakingsystem.R;

import java.util.ArrayList;
import java.util.List;

public class Cart extends Activity {
    private String nameS, sugarS, sugarextraS, doseS, commentsS, quantityS, sugarE;
    private double priceS;
    private boolean g ;
    private List<CartList> cartItems;
    private CartAdapter adapter;
    private ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.cart);
        Intent i = getIntent();
        nameS = i.getStringExtra("coffeeName");
        priceS = i.getDoubleExtra("coffeePrice", 0.00);
        quantityS = i.getStringExtra("quantity");
        sugarS = i.getStringExtra("sugarAmount");
        sugarextraS = i.getStringExtra("sugarFlavor");
        doseS = i.getStringExtra("dose");
        g = i.getBooleanExtra("checkNoSugar", false);
        commentsS = i.getStringExtra("commentsText");
        if(g){
            sugarE = "No Sugar";
        }else{
            if(sugarextraS == null){
                sugarE = "White Normal Sugar";
            }else{
                sugarE = sugarextraS;
            }
        }
        setupCart();
    }

    private void setupCart() {
        cartItems = new ArrayList<CartList>();
        cartItems.add(new CartList(nameS, priceS, sugarS, sugarE, quantityS, doseS, commentsS));
        lv = (ListView)findViewById(R.id.ListViewCatalog);
        adapter = new CartAdapter(getApplicationContext(), R.layout.cart_item, cartItems);
        lv.setAdapter(adapter);
    }
}
