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

public class SnacksFragment extends Fragment {

    private View rootView;
    private List<ProductList> snacks;
    private ListView lv;
    ProductListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_snacks_fragment, container, false);
        populateSnacksList();
        clickEvent();
        return rootView;
    }

    private void clickEvent() {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity().getApplicationContext(), "You have chosen " + snacks.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateSnacksList() {
        snacks = new ArrayList<ProductList>();
        snacks.add(new ProductList(R.drawable.ic_blt, "BLT Sandwich", 3.50));
        snacks.add(new ProductList(R.drawable.ic_club, "Club Sandwich", 4.50));
        snacks.add(new ProductList(R.drawable.ic_wrap, "Wrap", 4.00));
        snacks.add(new ProductList(R.drawable.ic_springrolls, "Springrolls", 5.50));
        snacks.add(new ProductList(R.drawable.ic_panini, "Panini Sandwich", 4.00));
        snacks.add(new ProductList(R.drawable.ic_salad, "Salad", 5.00));
        snacks.add(new ProductList(R.drawable.ic_omelete, "Omelete", 4.50));
        snacks.add(new ProductList(R.drawable.ic_nuggets, "Chicken Nuggets", 5.50));
        snacks.add(new ProductList(R.drawable.ic_chicken_fillets, "Chicken Fillet", 7.50));
        lv = (ListView)rootView.findViewById(R.id.snacksListView);
        adapter = new ProductListAdapter(getActivity().getApplicationContext(), R.layout.list_item, snacks);
        lv.setAdapter(adapter);
    }
}
