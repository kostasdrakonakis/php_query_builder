package com.example.kostas.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.kostas.adapters.ProductListAdapter;
import com.example.kostas.functions.CurrentProduct;
import com.example.kostas.lists.ProductList;
import com.example.kostas.ordertakingsystem.R;

import java.util.ArrayList;
import java.util.List;

public class CoffeesFragment extends Fragment {
    private List<ProductList> coffees;
    private ListView lv;
    private ProductListAdapter adapter;

    private View rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_coffees_fragment, container, false);
        populateCoffeeList();
        clickEvent();
        return rootView;
    }

    private void clickEvent() {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity().getApplicationContext(), CurrentProduct.class);
                intent.putExtra("name", coffees.get(position).getName());
                intent.putExtra("price", coffees.get(position).getPrice());
                startActivity(intent);
            }
        });
    }

    private void populateCoffeeList() {
        coffees = new ArrayList<ProductList>();
        coffees.add(new ProductList(R.drawable.epresso, "Freddo Espresso", 3.00 ));
        coffees.add(new ProductList(R.drawable.cappucino, "Freddo Cappuccino", 3.00 ));
        coffees.add(new ProductList(R.drawable.frape, "Frape", 2.50 ));
        coffees.add(new ProductList(R.drawable.late, "Latte", 2.50 ));
        coffees.add(new ProductList(R.drawable.nes, "Nescafe", 2.50 ));
        coffees.add(new ProductList(R.drawable.greek, "Greek Coffe", 2.50 ));
        coffees.add(new ProductList(R.drawable.sokolata, "Chocolate", 2.50 ));
        coffees.add(new ProductList(R.drawable.filter, "Filter", 2.50 ));
        lv = (ListView)rootView.findViewById(R.id.coffeesListView);
        adapter = new ProductListAdapter(getActivity().getApplicationContext(), R.layout.list_item, coffees);
        lv.setAdapter(adapter);
    }

}
