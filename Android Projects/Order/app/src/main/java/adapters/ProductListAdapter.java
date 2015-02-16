package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.order.app.order.R;

import java.util.List;

import lists.ProductList;

public class ProductListAdapter extends ArrayAdapter<ProductList> {

    public ProductListAdapter(Context context, int layoutId, List<ProductList> items) {
        super(context, layoutId, items);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View arrayView = convertView;
        ViewHolderItems holder;

        if(arrayView == null){
            LayoutInflater vi;
            vi = LayoutInflater.from(getContext());
            arrayView = vi.inflate(R.layout.list_item, parent, false);

            holder = new ViewHolderItems();
            holder.viewName = (TextView)arrayView.findViewById(R.id.product_name_coffee);
            holder.viewPrice = (TextView)arrayView.findViewById(R.id.product_price_coffee);
            holder.viewImage = (ImageView)arrayView.findViewById(R.id.product_image_coffee);
            arrayView.setTag(holder);
        }else{
            holder = (ViewHolderItems) arrayView.getTag();
        }
            ProductList currentPosition = getItem(position);
            holder.viewName.setText(currentPosition.getName());
            holder.viewPrice.setText(currentPosition.getPrice());
            Ion.with(holder.viewImage).placeholder(R.drawable.ic_launcher).error(R.drawable.ic_launcher).load(currentPosition.getImage());
        return arrayView;
    }

    static class ViewHolderItems {
        TextView viewName, viewPrice;
        ImageView viewImage;
    }


}
