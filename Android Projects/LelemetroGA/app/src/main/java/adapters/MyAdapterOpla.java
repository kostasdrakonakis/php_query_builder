package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.project.matrix.lelemetroga.R;
import com.project.matrix.lelemetroga.Swmata;

import java.util.List;

public class MyAdapterOpla extends BaseAdapter {

    private List<Swmata> objects;
    private Context context;

    public MyAdapterOpla(Context context, List objects) {
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
        SwmataHolder holder;

        if (convertView == null){
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            convertView = inflater.inflate(R.layout.spinner_item, parent, false);
            holder = new SwmataHolder();
            holder.imageView = (ImageView)convertView.findViewById(R.id.imageSpinner5);
            holder.textView = (TextView)convertView.findViewById(R.id.textSpinner5);
            convertView.setTag(holder);
        }else {
            holder = (SwmataHolder)convertView.getTag();
        }

        Swmata swma = (Swmata) getItem(position);
        holder.imageView.setImageResource(swma.getImage());
        holder.textView.setText(swma.getName());
        return convertView;
    }

    static class SwmataHolder{
        ImageView imageView;
        TextView textView;
    }
}
