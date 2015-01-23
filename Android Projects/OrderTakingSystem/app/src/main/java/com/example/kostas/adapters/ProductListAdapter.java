package com.example.kostas.adapters;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.kostas.filters.RoundImage;
import com.example.kostas.lists.ProductList;
import com.example.kostas.ordertakingsystem.R;

import java.util.List;


public class ProductListAdapter extends ArrayAdapter<ProductList> {


    private RoundImage roundedImage;

    public ProductListAdapter(Context context, int layoutId, List<ProductList> items) {
        super(context, layoutId, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View arrayView = convertView;
        if(arrayView == null){
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            arrayView = vi.inflate(R.layout.list_item, parent, false);
        }

        ProductList currentPosition = getItem(position);
        if(currentPosition != null){
            ImageView image = (ImageView)arrayView.findViewById(R.id.product_image_coffee);
            Bitmap bm = BitmapFactory.decodeResource(getContext().getResources(), currentPosition.getImage());
            roundedImage = new RoundImage(bm);
            image.setImageDrawable(roundedImage);

            TextView name = (TextView)arrayView.findViewById(R.id.product_name_coffee);
            name.setText(currentPosition.getName());

            TextView price = (TextView)arrayView.findViewById(R.id.product_price_coffee);
            price.setText(String.format("%.2f", currentPosition.getPrice()));
        }
        return arrayView;
    }



}
