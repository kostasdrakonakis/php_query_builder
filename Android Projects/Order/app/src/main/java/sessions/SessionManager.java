package sessions;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.order.app.order.MainActivity;

import java.util.HashMap;

public class SessionManager {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context context;

    private static final String PREF_NAME = "OrderTakingSystem";
    private static final String IS_LOGGED_IN = "isLoggedIn";
    public static final String KEY_NAME = "name";
    public static final String KEY_WAITER_ID = "servitoros_id";
    public static final String KEY_SHOP_ID = "magazi_id";
    private static final int PRIVATE_MODE = 0;

    public SessionManager(Context context){
        this.context  = context;
        preferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = preferences.edit();
    }

    public void createLoginSession(String name, String servitoros_id, String shop_id){
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_WAITER_ID, servitoros_id);
        editor.putString(KEY_SHOP_ID, shop_id);
        editor.commit();
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_NAME, preferences.getString(KEY_NAME, null));
        user.put(KEY_WAITER_ID, preferences.getString(KEY_WAITER_ID, null));
        user.put(KEY_SHOP_ID, preferences.getString(KEY_SHOP_ID, null));
        return user;
    }


    public void checkLogin(){
        if (!this.isLoggedIn()) {
            Intent i = new Intent(context, MainActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }

    public void logoutUser(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, MainActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public boolean isLoggedIn(){
        return preferences.getBoolean(IS_LOGGED_IN, false);
    }
}
