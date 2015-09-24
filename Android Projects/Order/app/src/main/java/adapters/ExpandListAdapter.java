package adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.koushikdutta.ion.Ion;
import com.order.app.order.R;

import java.util.ArrayList;
import java.util.List;

import lists.ExpandableListParent;
import lists.ProductList;

public class ExpandListAdapter extends BaseExpandableListAdapter {

    private Context context;
    private ArrayList<ExpandableListParent> groups;


    public ExpandListAdapter(Context context, ArrayList<ExpandableListParent> groups) {
        this.context = context;
        this.groups = groups;
    }

    @Override
    public int getGroupCount() {
        return groups.size();
    }

    @Override
    public int getChildrenCount(int groupPosition) {
        return groups.get(groupPosition).getChilds().size();
    }

    @Override
    public Object getGroup(int groupPosition) {
        return groups.get(groupPosition);
    }

    @Override
    public Object getChild(int groupPosition, int childPosition) {
        return groups.get(groupPosition).getChilds().get(childPosition);
    }

    @Override
    public long getGroupId(int groupPosition) {
        return groupPosition;
    }

    @Override
    public long getChildId(int groupPosition, int childPosition) {
        return childPosition;
    }

    @Override
    public boolean hasStableIds() {
        return true;
    }

    @Override
    public View getGroupView(int groupPosition, boolean isExpanded, View convertView, ViewGroup parent) {
        ExpandableListParent group = (ExpandableListParent) getGroup(groupPosition);
        if (convertView == null) {
            LayoutInflater inf = LayoutInflater.from(context);
            convertView = inf.inflate(R.layout.exp_list_item_parent, null);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.exp_text);
        tv.setText(group.getName());
        return convertView;
    }

    @Override
    public View getChildView(int groupPosition, int childPosition, boolean isLastChild, View convertView, ViewGroup parent) {
        ProductList child = (ProductList) getChild(groupPosition, childPosition);
        if (convertView == null) {
            LayoutInflater infalInflater = LayoutInflater.from(context);
            convertView = infalInflater.inflate(R.layout.list_item, null);
        }
        TextView tv = (TextView) convertView.findViewById(R.id.product_name_coffee);
        tv.setText(child.getItems()[childPosition]);
        TextView tvp = (TextView) convertView.findViewById(R.id.product_price_coffee);
        tvp.setText("5");
        ImageView iv = (ImageView)convertView.findViewById(R.id.product_image_coffee);
        Ion.with(iv).error(R.drawable.ic_launcher).placeholder(R.drawable.ic_launcher).load(child.getImage());
        return convertView;
    }

    @Override
    public boolean isChildSelectable(int groupPosition, int childPosition) {
        return true;
    }

    static class ViewHolderItems {
        TextView viewName, viewPrice;
        ImageView viewImage;
    }


}
