package adapters;


import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kostas.myapplication.R;

import org.jsoup.Jsoup;

import java.util.ArrayList;

import activityobjects.KouneliObject;

public class TreloKouneliBaseAdapter extends BaseAdapter{
    private LayoutInflater layoutInflater;
    private ArrayList<KouneliObject> postValueArrayList;

    public TreloKouneliBaseAdapter(Context context, ArrayList<KouneliObject> postValueArrayList) {
        this.layoutInflater = LayoutInflater.from(context);
        this.postValueArrayList = postValueArrayList;

    }

    @Override
    public int getCount() {
        return postValueArrayList.size();
    }

    @Override
    public KouneliObject getItem(int position) {
        return postValueArrayList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = layoutInflater.inflate(R.layout.list_item, null);
            viewHolder = new ViewHolder();
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.tvTitle = (TextView) convertView.findViewById(R.id.list_item_title);
        viewHolder.tvPublishDate = (TextView) convertView.findViewById(R.id.list_item_description);

        KouneliObject postValue = getItem(position);
        viewHolder.tvTitle.setText(postValue.getTitle());
        viewHolder.tvPublishDate.setText(html2text(postValue.getDescription()));

        return convertView;
    }

    public String html2text(String html) {
        return Jsoup.parse(html).text();
    }

    private class ViewHolder {
        TextView tvTitle, tvPublishDate;
    }
}
