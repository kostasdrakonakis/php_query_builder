package adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import fragments.PlaceHolderSignInFragment;
import fragments.PlaceHolderSignUpFragment;

public class SectionsPagerAdapter extends FragmentPagerAdapter {

    private String[] titles = {"Login", "Register"};


    public SectionsPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {

                return new PlaceHolderSignInFragment();
            }
            case 1: {
                return new PlaceHolderSignUpFragment();
            }
        }
        return null;
    }

    @Override
    public int getCount() {
        return titles.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return titles[position];
    }

    public String[] getTitles() {
        return titles;
    }
}

