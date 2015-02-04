package adapters;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.providers.restaurant.ordertakingsystem.R;

public class ImageAdapter extends BaseAdapter {

    private Context mContext;

    // Keep all Images in array
    int[] mThumbIds;

    public int[] getmThumbIds() {
        return mThumbIds;
    }

    public void setmThumbIds(int[] mThumbIds) {
        this.mThumbIds = mThumbIds;
    }

    // Constructor
    public ImageAdapter(Context c, int[] thumbId){
        mContext = c;
        mThumbIds = thumbId;
    }

    public ImageAdapter(Context mContext) {
        this.mContext = mContext;
    }

    @Override
    public int getCount() {
        return mThumbIds.length;
    }

    @Override
    public Object getItem(int position) {
        return mThumbIds[position];
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ImageView imageView = new ImageView(mContext);
        imageView.setImageResource(mThumbIds[position]);
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
        imageView.setLayoutParams(new GridView.LayoutParams(250, 400));
        return imageView;
    }

}
