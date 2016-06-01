package fragments;

import android.Manifest;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.enums.SnackbarType;
import com.order.app.order.R;
import com.order.app.order.UserProfile;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;

import functions.Constants;
import functions.StringGenerator;
import sessions.SessionManager;

public class Login extends Fragment {
    private static final int MY_PERMISSION_CODE = 1;
    private EditText username, password;
    private Button signIn;
    private View rootView;
    private SessionManager session;
    private TextView tv;
    private ProgressDialog pDialog;
    private ArrayList<NameValuePair> nameValuePairs;
    private HttpResponse response;
    private HttpClient httpClient;
    private HttpPost httpPost;
    private HttpEntity httpEntity;
    private InputStream is = null;
    private MyInsertDataTask task;
    private String userName, userPass, result;
    private boolean isRegistered = false, letLogin = false;
    private JSONObject data;
    private AlertDialog.Builder builder;
    private String isActivated, servitorosId, message;
    private boolean accountExists;
    private String magaziId;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.signin, container, false);
        session = new SessionManager(getActivity().getApplicationContext());
        if (session.isLoggedIn()) {
            Intent intent = new Intent(getActivity().getApplicationContext(), UserProfile.class);
            startActivity(intent);
            getActivity().finish();
        }
        setupView();
        if (ContextCompat.checkSelfPermission(getActivity(),
                Manifest.permission.READ_CONTACTS)
                != PackageManager.PERMISSION_GRANTED) {

            // Should we show an explanation?
            if (ActivityCompat.shouldShowRequestPermissionRationale(getActivity(),
                    Manifest.permission.SYSTEM_ALERT_WINDOW)) {

                // Show an expanation to the user *asynchronously* -- don't block
                // this thread waiting for the user's response! After the user
                // sees the explanation, try again to request the permission.

            } else {

                // No explanation needed, we can request the permission.

                ActivityCompat.requestPermissions(getActivity(),
                        new String[]{Manifest.permission.SYSTEM_ALERT_WINDOW},
                        MY_PERMISSION_CODE);

                // MY_PERMISSIONS_REQUEST_READ_CONTACTS is an
                // app-defined int constant. The callback method gets the
                // result of the request.
            }
        }
        setupClickEvent();
        return rootView;
    }

    private void checkConnection() {
        ConnectivityManager cm = (ConnectivityManager) getActivity().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean network_connected = activeNetwork != null && activeNetwork.isAvailable() && activeNetwork.isConnectedOrConnecting();
        if (!network_connected) {
            onDetectNetworkState().show();
        } else {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI || activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                accessWebService();
            }
            /*
            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                displayInfoMessage(getActivity(), getString(R.string.mobile_title), getString(R.string.mobile_message));
            }*/
        }
    }

    public AlertDialog displayInfoMessage(Context context, String title, String message) {
        builder = new AlertDialog.Builder(context);

        builder.setTitle(title);
        builder.setMessage(message);
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                getActivity().finish();
            }
        }).show();
        return builder.create();

    }


    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT) {

            if (onDetectNetworkState().isShowing()
                    && onDetectNetworkState() != null) {
                onDetectNetworkState().show();
            } else {
                onDetectNetworkState().dismiss();
            }
        }
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE) {

            if (onDetectNetworkState().isShowing()
                    && onDetectNetworkState() != null) {
                onDetectNetworkState().show();
            } else {
                onDetectNetworkState().dismiss();
            }
        }
    }

    private void setupClickEvent() {
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userName = username.getText().toString();
                userPass = password.getText().toString();
                if (!userName.isEmpty() || !userPass.isEmpty()) {
                    checkConnection();
                }
            }
        });
    }

    private void accessWebService() {
        task = new MyInsertDataTask();
        task.execute(Constants.LOGIN_URL);
    }


    private void setupView() {
        username = (EditText) rootView.findViewById(R.id.userName);
        password = (EditText) rootView.findViewById(R.id.password);
        signIn = (Button) rootView.findViewById(R.id.buttonSignIn);
        username.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (username.getText().toString().isEmpty()) {
                    Snackbar.with(getActivity().getApplicationContext()).text(getString(R.string.uname_required)).color(Color.parseColor(Constants.ENABLED_BUTTON_COLOR)).show(getActivity());
                    password.setEnabled(false);
                } else {
                    password.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        password.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (password.getText().toString().isEmpty()) {
                    Snackbar.with(getActivity().getApplicationContext()).text(getString(R.string.password_required)).color(Color.parseColor(Constants.ENABLED_BUTTON_COLOR)).show(getActivity());
                    signIn.setEnabled(false);
                    signIn.setBackgroundColor(Color.parseColor(Constants.DISABLED_BUTTON_COLOR));
                } else {
                    signIn.setEnabled(true);
                    signIn.setBackgroundColor(Color.parseColor(Constants.ENABLED_BUTTON_COLOR));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
    }

    private AlertDialog onDetectNetworkState() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(getActivity());
        builder1.setMessage(R.string.wifi_off_message)
                .setTitle(R.string.wifi_off_title)

                .setNegativeButton(R.string.cancel,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                getActivity().finish();
                            }
                        })
                .setPositiveButton(R.string.action_settings,
                        new DialogInterface.OnClickListener() {

                            @Override
                            public void onClick(DialogInterface dialog,
                                                int which) {
                                startActivityForResult((new Intent(
                                        Settings.ACTION_WIFI_SETTINGS)), 1);
                                getActivity().finish();
                            }
                        });
        return builder1.create();
    }


    private class MyInsertDataTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            checkDrawOverlayPermission();
        }


        @Override
        protected Boolean doInBackground(String... params) {
            nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("user", userName));
            nameValuePairs.add(new BasicNameValuePair("pass", userPass));
            try {
                httpClient = new DefaultHttpClient();
                httpPost = new HttpPost(params[0]);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));
                response = httpClient.execute(httpPost);
                httpEntity = response.getEntity();
                is = httpEntity.getContent();
                BufferedReader br = new BufferedReader(new InputStreamReader(is));
                StringBuilder result = new StringBuilder();
                String line;
                while ((line = br.readLine()) != null) {
                    result.append(line);
                }
                Log.e("Responce", result.toString());
                data = new JSONObject(result.toString());
                accountExists = data.getBoolean("reg");
                isRegistered = data.getBoolean("res");
                isActivated = data.getString("activated");
                servitorosId = data.getString("servitoros_id");
                magaziId = data.getString("magazi_id");

            } catch (Exception e) {
                Log.e("Fail 1", e.toString());
            }
            if (accountExists){
                if(!isRegistered){
                    message = getString(R.string.wrong_password);
                    return false;
                }else {

                    if(isActivated.equals("0")){
                        message = getString(R.string.account_not_active);
                        return false;
                    }else{
                        return true;
                    }
                }
            }else {
                message = getString(R.string.wrong_username);
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aVoid) {
            super.onPostExecute(aVoid);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M){
                if (pDialog != null){
                    pDialog.dismiss();
                }
            }else {
                if (pDialog != null){
                    pDialog.dismiss();
                }
            }

            if (aVoid) {
                session.createLoginSession(userName, servitorosId, magaziId);
                Intent intent = new Intent(getActivity().getApplicationContext(), UserProfile.class);
                startActivity(intent);
                getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                getActivity().finish();
            } else {
                StringGenerator.showSnackMessage(SnackbarType.MULTI_LINE, message, getActivity().getApplicationContext(), Color.parseColor(Constants.ENABLED_BUTTON_COLOR), getActivity());
            }
        }
    }

    public void checkDrawOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(getActivity().getApplicationContext())) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getActivity().getPackageName()));
                startActivityForResult(intent, MY_PERMISSION_CODE);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,  Intent data) {
        if (requestCode == MY_PERMISSION_CODE) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (Settings.canDrawOverlays(getActivity().getApplicationContext())) {
                    pDialog = new ProgressDialog(getActivity().getApplicationContext());
                    pDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
                    pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
                    pDialog.setIndeterminate(true);
                    pDialog.setMessage(getString(R.string.dialog_rate_data_submit));
                    pDialog.setCancelable(false);
                    pDialog.setInverseBackgroundForced(true);
                    pDialog.show();
                }
            }
        }
    }



}
