package adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kostas.stockpredictions.R;
import com.koushikdutta.ion.Ion;

import java.util.List;

import lists.StockList;

public class MyStocksAdapter extends ArrayAdapter<StockList> {

    public MyStocksAdapter(Context context, int resource, List<StockList> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolderItems holder;
        View stockView = null;
        StockList current = null;

        if(stockView == null){
            LayoutInflater li = LayoutInflater.from(getContext());
            stockView = li.inflate(R.layout.list_item, parent, false);
            current = getItem(position);

            holder = new ViewHolderItems();
            holder.stockViewName = (TextView)stockView.findViewById(R.id.stock_name);
            holder.stockViewPrice = (TextView)stockView.findViewById(R.id.stock_price);
            holder.stockViewImage = (ImageView)stockView.findViewById(R.id.imagestartinglist);
            stockView.setTag(holder);
        }else{
            holder = (ViewHolderItems)stockView.getTag();
        }

        if(current != null){
            holder.stockViewName.setText(current.getStockCurrentName());
            holder.stockViewPrice.setText(current.getStockCurrentPrice());
            Ion.with(holder.stockViewImage).placeholder(R.drawable.ic_chat).error(R.drawable.ic_chat).load(current.getStockImage());
        }
        return stockView;
    }

    static class ViewHolderItems {
        TextView stockViewName, stockViewPrice;
        ImageView stockViewImage;
    }
}
