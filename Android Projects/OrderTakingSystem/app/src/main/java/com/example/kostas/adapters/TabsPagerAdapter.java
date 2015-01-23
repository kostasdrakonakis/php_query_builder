package com.example.kostas.adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.example.kostas.fragments.CoffeesFragment;
import com.example.kostas.fragments.DrinksFragment;
import com.example.kostas.fragments.SnacksFragment;
import com.example.kostas.fragments.SweetsFragment;

public class TabsPagerAdapter extends FragmentPagerAdapter {

    private String[] titles = {"Coffees", "Sweets", "Snacks", "Drinks"};

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
                return new DrinksFragment();
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
