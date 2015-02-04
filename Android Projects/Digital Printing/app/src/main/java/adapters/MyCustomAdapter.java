package adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import objects.ParseItem;
import com.providers.restaurant.ordertakingsystem.R;

import java.util.List;

public class MyCustomAdapter extends ArrayAdapter<ParseItem>{

    public MyCustomAdapter(Context context, int resource, List<ParseItem> objects) {
        super(context, resource, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolderItems holder;
        View rootView = null;
        ParseItem current = null;

        if(rootView == null){
            LayoutInflater li = LayoutInflater.from(getContext());
            rootView = li.inflate(R.layout.list_item, parent, false);
            current = getItem(position);

            holder = new ViewHolderItems();
            holder.id = (TextView)rootView.findViewById(R.id.textViewForList);
            holder.image = (ImageView)rootView.findViewById(R.id.imageForList);
            rootView.setTag(holder);
        }else{
            holder = (ViewHolderItems)rootView.getTag();
        }

        if(current != null){
            Ion.with(holder.image).placeholder(R.drawable.ic_launcher).error(R.drawable.ic_launcher).load(current.getImage());
            holder.id.setText(current.getName());
        }
        return rootView;
    }

    static class ViewHolderItems {
        TextView id;
        ImageView image;
    }

}
