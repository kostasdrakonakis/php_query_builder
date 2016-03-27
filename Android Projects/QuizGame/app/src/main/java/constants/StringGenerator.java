package constants;


import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.util.DisplayMetrics;
import android.widget.Toast;

import com.library.quizgame.R;

import org.apache.http.NameValuePair;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.Locale;

public class StringGenerator {
    /**
     * Χρησιμοποιούμε την μέθοδο αυτήν για να μετατρέψουμε τα bytes που μας επέστρεψε το BufferedInputStream
     * σε String.
     * @param is InputStream to read
     * @param activity Activity that the String is going to be returned
     * @return StringBuilder into a String
     */
    public static StringBuilder inputStreamToString(InputStream is, Activity activity) {
        String rLine = "";
        StringBuilder answer = new StringBuilder();
        BufferedReader rd = new BufferedReader(new InputStreamReader(is));
        try {
            while ((rLine = rd.readLine()) != null) {
                answer.append(rLine);
            }
        } catch (Exception e) {
            activity.finish();
        }
        return answer;
    }

    /**
     * Δεν την χρειαζόμαστε ακόμα. Αυτό που κάνει είναι να μετατρέπει το encoding σε UTF8
     * @param params
     * @return
     * @throws UnsupportedEncodingException
     */
    public static String queryResults(List<NameValuePair> params) throws UnsupportedEncodingException {
        StringBuilder result = new StringBuilder();
        boolean first = true;

        for (NameValuePair pair : params)
        {
            if (first)
                first = false;
            else
                result.append("&");

            result.append(URLEncoder.encode(pair.getName(), Constants.CHARACTER_ENCODING));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), Constants.CHARACTER_ENCODING));
        }
        return result.toString();
    }

    /**
     * Μέθοδος που χρησιμοποιούμε για να μετατρέψουμε την γλώσσα που επιλέγει ο χρήστης
     * σε Language Code: Greek = el, English = en, French = fr, German = de
     * @param language
     * @return String in language code
     */
    public static String checkLanguageCode(String language){
        String langCode = null;
        switch (language){
            case Constants.EN_EN_FULL:{
                langCode = Constants.EN;
                break;
            }case Constants.GR_EN_FULL:{
                langCode = Constants.GR;
                break;
            }case Constants.EN_FULL: {
                langCode = Constants.EN;
                break;
            }case Constants.GR_FULL:{
                langCode = Constants.GR;
                break;
            }
        }

        return langCode;
    }

    /**
     * Μέθοδος που χρησιμοποιούμε για να μετατρέψουμε το Language Code
     * σε γλώσσα που επιλέγει ο χρήστης : el = Greek, en = English, fr = French, de = German
     * @param language
     * @return String in Language
     */
    public static String revertLanguageCode(String language, Context context){
        String lang = null;
        String locale = context.getResources().getConfiguration().locale.toString();
        switch (language){
            case Constants.EN:{
                if (locale.equals(Constants.EN)){
                    lang = Constants.EN_EN_FULL;

                }else {
                    lang = Constants.EN_FULL;
                }
                break;
            }case Constants.GR:{
                if (locale.equals(Constants.GR)){
                    lang = Constants.GR_FULL;
                }else {
                    lang = Constants.GR_EN_FULL;
                }
                break;
            }default:
                lang = context.getString(R.string.ta_to_select);
                break;
        }

        return lang;
    }

    /**
     * Δείχνουμε ένα Toast message στην οθόνη
     * @param context
     * @param message
     */
    public static void showToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Κάνουμε set το language που έχει επιλέξει ο χρήστης σε όλα τα Activities
     * @param lang The language loaded from SharedPreferences
     * @param context The Activity Context
     */
    public static void setLocale(String lang, Context context) {
        Locale myLocale = new Locale(lang);
        Resources resources = context.getResources();
        DisplayMetrics displayMetrics = resources.getDisplayMetrics();
        Configuration configuration = resources.getConfiguration();
        configuration.locale = myLocale;
        resources.updateConfiguration(configuration, displayMetrics);

    }
}
