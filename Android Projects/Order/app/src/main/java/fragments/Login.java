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

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.enums.SnackbarType;
import com.order.app.order.R;
import com.order.app.order.UserProfile;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import functions.Constants;
import functions.StringGenerator;
import sessions.SessionManager;

public class Login extends Fragment {
    private static final int MY_PERMISSION_CODE = 1;
    private EditText username, password;
    private Button signIn;
    private View rootView;
    private SessionManager session;
    private ProgressDialog pDialog;
    private MyInsertDataTask task;
    private String userName, userPass;
    private String servitorosId, message;
    private String magaziId;
    private HttpURLConnection urlConnection;
    private URL url;
    private OutputStreamWriter outputStreamWriter;
    private InputStream inputStream;
    private StringBuilder jsonResult;
    private JSONObject dataToWrite, jsonResponse;


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
            try {
                url = new URL(params[0]);
                urlConnection =(HttpURLConnection) url.openConnection();
                urlConnection.setRequestProperty(Constants.CUSTOM_HEADER, Constants.API_KEY);
                Log.e("Custom Header", Constants.CUSTOM_HEADER);
                Log.e("Api Key: ", Constants.API_KEY);
                Log.e("Method: ", Constants.METHOD_POST);
                urlConnection.setRequestProperty("Accept-Encoding", "application/json");
                urlConnection.setRequestProperty("Content-Type", "application/json; charset=UTF-8");
                urlConnection.setRequestMethod(Constants.METHOD_POST);

                urlConnection.setDoInput(true);
                urlConnection.setDoOutput(true);
                urlConnection.connect();

                setupDataToDB();

                outputStreamWriter = new OutputStreamWriter(urlConnection.getOutputStream());
                outputStreamWriter.write(dataToWrite.toString());
                Log.e("Data To Write", dataToWrite.toString());

                outputStreamWriter.flush();
                outputStreamWriter.close();
                int responseCode = urlConnection.getResponseCode();
                Log.e("Response Code ", String.valueOf(responseCode));
                if (responseCode == 404){
                    message = getString(R.string.wrong_credentials);
                    return false;
                }else if (responseCode == 401){
                    message = getString(R.string.account_not_active);
                    return false;
                }else if (responseCode == 200){
                    inputStream = new BufferedInputStream(urlConnection.getInputStream());

                    jsonResult = StringGenerator.inputStreamToString(inputStream, getActivity());
                    jsonResponse = new JSONObject(jsonResult.toString());

                    Log.e("Data From JSON", jsonResponse.toString());
                    String status = jsonResponse.getString("status");
                    String status_code = jsonResponse.getString("status_code");
                    String messageFromResponse = jsonResponse.getString("message");
                    Log.e("Response Status", status);
                    Log.e("Response Status Code", status_code);
                    Log.e("Response Message", messageFromResponse);
                    JSONObject object = jsonResponse.getJSONObject("response");
                    servitorosId = object.getString("servitoros_id");
                    magaziId = object.getString("magazi_id");
                    if (status.equals("success") && status_code.equals("200")){
                        return true;
                    }
                }

            } catch (Exception e) {
                urlConnection.getErrorStream();
                e.printStackTrace();
            }

            return false;
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

    private void setupDataToDB() {
        dataToWrite = new JSONObject();
        try {
            dataToWrite.put(""+Constants.USER_USERNAME+"", userName);
            dataToWrite.put(""+Constants.USER_PASSWORD+"", userPass);
        } catch (JSONException e) {
            e.printStackTrace();
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
