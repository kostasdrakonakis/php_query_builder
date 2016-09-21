package functions;


import android.app.Activity;
import android.content.Context;
import android.support.annotation.Nullable;
import android.widget.Toast;

import com.nispok.snackbar.enums.SnackbarType;

import org.apache.http.NameValuePair;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;

public class StringGenerator {

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

    public static void showToast(Context context, String message){
        Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
    }

    /**
     * Shows a custom error or info message in a snackbar
     * @param type The Snackbar type SINGLE_LINE or MULTI_LINE
     * @param message The message to display
     * @param context The Context of the activity to be shown in
     * @param color The color of the displayed message
     * @param activity The activity to be shown
     */
    public static void showSnackMessage(@Nullable SnackbarType type, String message, Context context, int color, Activity activity){
        com.nispok.snackbar.Snackbar.with(context).text(message).color(color).type(type).show(activity);
    }
}
