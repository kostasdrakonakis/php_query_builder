package com.example.kostas.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.kostas.lists.CartList;
import com.example.kostas.ordertakingsystem.R;

import java.util.List;


public class CartAdapter extends ArrayAdapter<CartList> {

    public CartAdapter(Context context, int layoutId, List<CartList> items) {
        super(context, layoutId, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View arrayView = convertView;
        if(arrayView == null){
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            arrayView = vi.inflate(R.layout.cart_item, parent, false);
        }

        CartList currentPosition = getItem(position);
        if(currentPosition != null){

            TextView name = (TextView)arrayView.findViewById(R.id.cartItemName);
            name.setText(currentPosition.getName());

            TextView quantity = (TextView)arrayView.findViewById(R.id.cartItemQuantity);
            quantity.setText(currentPosition.getQuantity());

            TextView sugarTaste = (TextView)arrayView.findViewById(R.id.cartItemSugarTaste);
            sugarTaste.setText(currentPosition.getSugarTaste());

            TextView sugarFlavor = (TextView)arrayView.findViewById(R.id.cartItemSugarFlavor);
            sugarFlavor.setText(currentPosition.getSugarFlavor());

            TextView dose = (TextView)arrayView.findViewById(R.id.cartItemDose);
            dose.setText(currentPosition.getDose());

            TextView comments = (TextView)arrayView.findViewById(R.id.cartItemComments);
            comments.setText(currentPosition.getComments());

            TextView price = (TextView)arrayView.findViewById(R.id.cartItemPrice);
            price.setText(String.format("%.2f", currentPosition.getPrice()) + " €");
        }
        return arrayView;
    }



}
