package adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.order.app.order.R;

import fragments.Beer;
import fragments.Beverages;
import fragments.Coffees;
import fragments.Snacks;
import fragments.Spirits;
import fragments.Sweets;

public class ProductsTabPagerAdapter extends FragmentPagerAdapter{

    private Activity activity;
    private Context context;

    private static String[] ITEMS = null;
    private static Resources res = null;

    public ProductsTabPagerAdapter(FragmentManager fm, Activity activity, Context context) {
        super(fm);
        this.activity = activity;
        this.context = context;
        res = context.getResources();
        ITEMS = res.getStringArray(R.array.products);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new Coffees();
            case 1:
                return new Sweets();
            case 2:
                return new Snacks();
            case 3:
                return new Beverages();
            case 4:
                return new Spirits();
            case 5:
                return new Beer();
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return ProductsTabPagerAdapter.ITEMS[position];
    }

    @Override
    public int getCount() {
        return ProductsTabPagerAdapter.ITEMS.length;
    }
}
