package sessions;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.order.app.order.MainActivity;

import java.util.HashMap;

import functions.AppConstant;

public class SessionManager {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context context;



    public SessionManager(Context context){
        this.context  = context;
        preferences = context.getSharedPreferences(AppConstant.PREF_NAME, AppConstant.PRIVATE_MODE);
        editor = preferences.edit();
    }

    public void createLoginSession(String name, String servitoros_id, String shop_id){
        editor.putBoolean(AppConstant.IS_LOGGED_IN, true);
        editor.putString(AppConstant.KEY_NAME, name);
        editor.putString(AppConstant.KEY_WAITER_ID, servitoros_id);
        editor.putString(AppConstant.KEY_SHOP_ID, shop_id);
        editor.commit();
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(AppConstant.KEY_NAME, preferences.getString(AppConstant.KEY_NAME, null));
        user.put(AppConstant.KEY_WAITER_ID, preferences.getString(AppConstant.KEY_WAITER_ID, null));
        user.put(AppConstant.KEY_SHOP_ID, preferences.getString(AppConstant.KEY_SHOP_ID, null));
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
        return preferences.getBoolean(AppConstant.IS_LOGGED_IN, false);
    }
}
