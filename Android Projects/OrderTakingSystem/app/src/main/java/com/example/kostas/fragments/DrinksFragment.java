package com.example.kostas.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.example.kostas.adapters.ProductListAdapter;
import com.example.kostas.lists.ProductList;
import com.example.kostas.ordertakingsystem.R;

import java.util.ArrayList;
import java.util.List;

public class DrinksFragment extends Fragment {

    private View rootView;
    private List<ProductList> drinks;
    private ListView lv;
    ProductListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_drinks_fragment, container, false);
        populateDrinksList();
        clickEvent();
        return rootView;
    }

    private void clickEvent() {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity().getApplicationContext(), "You have chosen " + drinks.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateDrinksList() {
        drinks = new ArrayList<ProductList>();
        drinks.add(new ProductList(R.drawable.coca, "Coca Cola", 2.50));
        drinks.add(new ProductList(R.drawable.cocalight, "Coca Cola Light", 2.50));
        drinks.add(new ProductList(R.drawable.cocazero, "Coca Cola Zero", 2.50));
        drinks.add(new ProductList(R.drawable.orange, "Fanta Orange", 2.50));
        drinks.add(new ProductList(R.drawable.lemon, "Fanta Lemon", 2.50));
        drinks.add(new ProductList(R.drawable.ble, "Fanta Blue", 2.50));
        drinks.add(new ProductList(R.drawable.sprite, "Sprite", 2.50));
        drinks.add(new ProductList(R.drawable.soda, "Soda Water", 2.50));
        drinks.add(new ProductList(R.drawable.tonic, "Tonic Water", 2.50));
        drinks.add(new ProductList(R.drawable.ioli, "Sparkling Water Ioli", 2.50));
        drinks.add(new ProductList(R.drawable.perrier, "Sparkling Water Perrier", 2.50));
        drinks.add(new ProductList(R.drawable.nero, "Still Water", 2.00));
        drinks.add(new ProductList(R.drawable.redbull, "Red Bull", 4.00));
        drinks.add(new ProductList(R.drawable.zelita, "Zelita", 2.50));
        lv = (ListView) rootView.findViewById(R.id.drinksListView);
        adapter = new ProductListAdapter(getActivity().getApplicationContext(), R.layout.list_item, drinks);
        lv.setAdapter(adapter);
    }

}
