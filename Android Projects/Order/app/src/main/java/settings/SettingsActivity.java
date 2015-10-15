package settings;

import android.content.Context;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceActivity;

import com.order.app.order.R;

public class SettingsActivity extends PreferenceActivity {

    @Override
    public boolean onIsMultiPane() {
        return isXLargeTablet(this);
    }

    private static boolean isXLargeTablet(Context context) {
        return (context.getResources().getConfiguration().screenLayout
                & Configuration.SCREENLAYOUT_SIZE_MASK) >= Configuration.SCREENLAYOUT_SIZE_XLARGE;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        addPreferencesFromResource(R.xml.preferences_file);
        setTheme(R.style.Preference);
    }
}
