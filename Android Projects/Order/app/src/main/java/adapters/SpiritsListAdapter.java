package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.order.app.order.R;

import java.util.List;

import lists.SpiritList;

public class SpiritsListAdapter extends BaseAdapter {

    private List<SpiritList> objects;
    private Context context;

    public SpiritsListAdapter(Context context ,List<SpiritList> objects) {
        this.context = context;
        this.objects = objects;
    }

    @Override
    public int getCount() {
        return objects.size();
    }

    @Override
    public Object getItem(int position) {
        return objects.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        SpiritHolderItems holder;

        if(convertView == null){
            LayoutInflater vi;
            vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = vi.inflate(R.layout.spinner_row_item, parent, false);

            holder = new SpiritHolderItems();
            holder.spiritName = (TextView)convertView.findViewById(R.id.spinner_row_name);
            holder.spiritPrice = (TextView)convertView.findViewById(R.id.spinner_row_price);
            holder.spiritImage = (ImageView)convertView.findViewById(R.id.spirit_image);
            convertView.setTag(holder);
        }else{
            holder = (SpiritHolderItems) convertView.getTag();
        }
            SpiritList current = (SpiritList)getItem(position);
            holder.spiritName.setText(current.getName());
            holder.spiritPrice.setText(current.getPrice() + " €");
            Ion.with(holder.spiritImage).error(R.mipmap.placeholder).placeholder(R.mipmap.placeholder).load(current.getImage());
        return convertView;
    }

    static class SpiritHolderItems {
        TextView spiritName, spiritPrice;
        ImageView spiritImage;
    }
}
