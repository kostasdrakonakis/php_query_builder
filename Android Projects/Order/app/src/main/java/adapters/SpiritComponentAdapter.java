package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.order.app.order.R;

import java.util.List;

import lists.SpiritComponentProduct;

public class SpiritComponentAdapter extends BaseAdapter {

    private Context context;
    private List<SpiritComponentProduct> items;

    public SpiritComponentAdapter(Context context, List<SpiritComponentProduct> items) {
        this.context = context;
        this.items = items;
    }

    @Override
    public int getCount() {
        return items.size();
    }

    @Override
    public Object getItem(int position) {
        return items.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }



    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View arrayView = convertView;
        ViewHolderItems holder;
        if(arrayView == null){
            LayoutInflater vi;
            vi = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            arrayView = vi.inflate(R.layout.spirit_component_spinner_item, parent, false);
            holder = new ViewHolderItems();
            holder.viewName = (TextView)arrayView.findViewById(R.id.spirit_component_name);
            holder.viewImage = (ImageView)arrayView.findViewById(R.id.spirit_component_image);
            arrayView.setTag(holder);
        }else{
            holder = (ViewHolderItems) arrayView.getTag();
        }
            SpiritComponentProduct currentPosition = (SpiritComponentProduct)getItem(position);
            holder.viewName.setText(currentPosition.getProductName());
            holder.viewImage.setImageResource(currentPosition.getImage());
        return arrayView;
    }

    static class ViewHolderItems {
        TextView viewName;
        ImageView viewImage;
    }


}
