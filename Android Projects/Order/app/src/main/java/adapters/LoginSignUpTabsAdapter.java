package adapters;

import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.order.app.order.R;

import fragments.Login;
import fragments.Register;

public class LoginSignUpTabsAdapter extends FragmentPagerAdapter {

    Activity activity;
    static Context context;

    static Resources res = null;
    static String[] CONTENT = null;

    public LoginSignUpTabsAdapter(FragmentManager fm, Activity activity, Context context) {
        super(fm);
        this.activity = activity;
        this.context = context;
        res = context.getResources();
        CONTENT = res.getStringArray(R.array.credentials_titles);

    }


    @Override
    public Fragment getItem(int position) {
        switch (position) {
            case 0: {

                return new Login();
            }
            case 1: {
                return new Register();
            }
        }
        return null;
    }

    @Override
    public int getCount() {
        return LoginSignUpTabsAdapter.CONTENT.length;
    }

    @Override
    public CharSequence getPageTitle(int position) {

        return LoginSignUpTabsAdapter.CONTENT[position];
    }

    public String[] getTitles() {
        return LoginSignUpTabsAdapter.CONTENT;
    }
}

