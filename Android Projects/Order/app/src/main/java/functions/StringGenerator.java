package functions;


import android.app.Activity;

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

            result.append(URLEncoder.encode(pair.getName(), AppConstant.CHARACTER_ENCODING));
            result.append("=");
            result.append(URLEncoder.encode(pair.getValue(), AppConstant.CHARACTER_ENCODING));
        }
        return result.toString();
    }
}
