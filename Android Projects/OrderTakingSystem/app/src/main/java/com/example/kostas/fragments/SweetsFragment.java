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

public class SweetsFragment extends Fragment {

    private View rootView;
    private List<ProductList> sweets;
    private ListView lv;
    ProductListAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.activity_sweets_fragment, container, false);
        populateSweetsList();
        clickEvent();
        return rootView;
    }

    private void clickEvent() {
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getActivity().getApplicationContext(), "You have chosen " + sweets.get(position).getName(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void populateSweetsList() {
        sweets = new ArrayList<ProductList>();
        sweets.add(new ProductList(R.drawable.ic_profiterol, "Profiterol", 5.00));
        sweets.add(new ProductList(R.drawable.ic_banana_split, "Banana Split", 4.50));
        sweets.add(new ProductList(R.drawable.ic_brownies, "Brownies", 5.00));
        sweets.add(new ProductList(R.drawable.ic_sokolatopita, "Chocolate Cake", 5.00));
        sweets.add(new ProductList(R.drawable.ic_soufle, "Chocolate Souffle", 5.00));
        sweets.add(new ProductList(R.drawable.ic_cheesecake, "Cheese Cake", 5.00));
        sweets.add(new ProductList(R.drawable.ic_crumble, "Apple Crumble", 5.00));
        sweets.add(new ProductList(R.drawable.ic_mousse, "Chocolate Mousse", 5.00));
        sweets.add(new ProductList(R.drawable.ic_panakota, "Panakotta", 3.50));
        sweets.add(new ProductList(R.drawable.ic_creme_caramel, "Caramel Creme", 5.00));
        sweets.add(new ProductList(R.drawable.ic_tiramisu, "Tiramisu", 4.00));
        sweets.add(new ProductList(R.drawable.ic_baklava, "Baklava", 3.00));
        sweets.add(new ProductList(R.drawable.ic_kantaifi, "Kantaifi", 3.00));
        lv = (ListView)rootView.findViewById(R.id.sweetsListView);
        adapter = new ProductListAdapter(getActivity().getApplicationContext(), R.layout.list_item, sweets);
        lv.setAdapter(adapter);
    }
}
