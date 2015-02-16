package adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import fragments.BeerFragment;
import fragments.BeveragesFragment;
import fragments.CoffeesFragment;
import fragments.SnacksFragment;
import fragments.SpiritsFragment;
import fragments.SweetsFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter{

    private String[] titles = {"Coffees", "Sweets", "Snacks", "Beverages", "Spirits", "Beers"};

    public TabsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int i) {
        switch (i) {
            case 0:
                return new CoffeesFragment();
            case 1:
                return new SweetsFragment();
            case 2:
                return new SnacksFragment();
            case 3:
                return new BeveragesFragment();
            case 4:
                return new SpiritsFragment();
            case 5:
                return new BeerFragment();
        }
        return null;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

    @Override
    public int getCount() {
        return titles.length;
    }
}
