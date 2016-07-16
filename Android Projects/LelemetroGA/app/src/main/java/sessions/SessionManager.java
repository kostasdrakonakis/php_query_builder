package sessions;


import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;

import com.project.matrix.lelemetroga.LoginActivity;

import java.util.HashMap;

public class SessionManager {

    private SharedPreferences preferences;
    private SharedPreferences.Editor editor;
    private Context context;

    private static final String PREF_NAME = "Lelemetro";
    private static final String IS_LOGGED_IN = "isLoggedIn";
    public static final String KEY_NAME = "name";
    public static final String KEY_EMAIL = "email";
    private static final int PRIVATE_MODE = 0;

    public SessionManager(Context context){
        this.context  = context;
        preferences = context.getSharedPreferences(PREF_NAME, PRIVATE_MODE);
        editor = preferences.edit();
    }

    public void createLoginSession(String name, String email){
        editor.putBoolean(IS_LOGGED_IN, true);
        editor.putString(KEY_NAME, name);
        editor.putString(KEY_EMAIL, email);
        editor.commit();
    }

    public HashMap<String, String> getUserDetails(){
        HashMap<String, String> user = new HashMap<String, String>();
        user.put(KEY_NAME, preferences.getString(KEY_NAME, null));
        user.put(KEY_EMAIL, preferences.getString(KEY_EMAIL, null));
        return user;
    }


    public void checkLogin(){
        if (!this.isLoggedIn()) {
            Intent i = new Intent(context, LoginActivity.class);
            i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(i);
        }
    }

    public void logoutUser(){
        editor.clear();
        editor.commit();
        Intent i = new Intent(context, LoginActivity.class);
        i.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        context.startActivity(i);
    }

    public boolean isLoggedIn(){
        return preferences.getBoolean(IS_LOGGED_IN, false);
    }
}
