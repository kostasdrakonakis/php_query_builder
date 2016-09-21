package fragments;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.enums.SnackbarType;
import com.order.app.order.MainActivity;
import com.order.app.order.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;

import functions.Constants;
import functions.StringGenerator;

public class Register extends Fragment {
    private EditText username, password, retypePassword, emailField;
    private Button signUp;
    private View rootView;
    private ProgressDialog pDialog;
    private MyInsertDataTask task;
    private String uName, pass, email, release, message;
    private int sdkVersion;
    private HttpURLConnection urlConnection;
    private URL url;
    private OutputStreamWriter outputStreamWriter;
    private InputStream inputStream;
    private StringBuilder jsonResult;
    private JSONObject dataToWrite, jsonResponse;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.signup, container, false);
        release = Build.VERSION.RELEASE;
        sdkVersion = Build.VERSION.SDK_INT;
        setupView();
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
            /*if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) {
                displayInfoMessage(getActivity(), getString(R.string.mobile_title), getString(R.string.mobile_message));
            }*/
        }
    }


    private void setupClickEvent() {
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uName = username.getText().toString();
                pass = password.getText().toString();
                email = emailField.getText().toString();
                if (!uName.isEmpty() || !pass.isEmpty() || !email.isEmpty()) {
                    checkConnection();

                }

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

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        if (newConfig.orientation == Configuration.ORIENTATION_PORTRAIT){


            if (onDetectNetworkState().isShowing()
                    && onDetectNetworkState() != null) {
                onDetectNetworkState().show();
            } else {
                onDetectNetworkState().dismiss();
            }
        }
        if (newConfig.orientation == Configuration.ORIENTATION_LANDSCAPE){


            if (onDetectNetworkState().isShowing()
                    && onDetectNetworkState() != null) {
                onDetectNetworkState().show();
            } else {
                onDetectNetworkState().dismiss();
            }
        }
    }

    private void accessWebService() {
        task = new MyInsertDataTask();
        task.execute(new String[]{Constants.REGISTER_URL});
    }

    private void setupView() {
        username = (EditText) rootView.findViewById(R.id.userNameSignUp);
        password = (EditText) rootView.findViewById(R.id.passwordSignUp);
        retypePassword = (EditText) rootView.findViewById(R.id.retypePassword);
        emailField = (EditText) rootView.findViewById(R.id.emailRegisterText);
        signUp = (Button) rootView.findViewById(R.id.buttonSignUp);
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
                    retypePassword.setEnabled(false);
                } else {
                    retypePassword.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        retypePassword.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (retypePassword.getText().toString().isEmpty()) {
                    Snackbar.with(getActivity().getApplicationContext()).text(getString(R.string.retype_pass_required)).color(Color.parseColor(Constants.ENABLED_BUTTON_COLOR)).show(getActivity());
                    emailField.setEnabled(false);
                } else {
                    emailField.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        emailField.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (emailField.getText().toString().isEmpty()) {
                    Snackbar.with(getActivity().getApplicationContext()).text(getString(R.string.email_required)).color(Color.parseColor(Constants.ENABLED_BUTTON_COLOR)).show(getActivity());
                    signUp.setEnabled(false);
                    signUp.setBackgroundColor(Color.parseColor(Constants.DISABLED_BUTTON_COLOR));
                } else {
                    if (isEmailValid(emailField.getText().toString())) {
                        signUp.setEnabled(true);
                        signUp.setBackgroundColor(Color.parseColor(Constants.ENABLED_BUTTON_COLOR));
                    }else {
                        Snackbar.with(getActivity().getApplicationContext()).text(getString(R.string.email_not_valid)).color(Color.parseColor(Constants.ENABLED_BUTTON_COLOR)).show(getActivity());
                    }

                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }
    boolean isEmailValid(CharSequence email) {
        return android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches();
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
                if (responseCode == 400){
                    message = getString(R.string.email_or_username_exists);
                    return false;
                }else if (responseCode == 201){
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
                    return true;
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
            if (aVoid){
                Toast.makeText(getActivity(), getString(R.string.created_successfully), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }else {
                Snackbar.with(getActivity().getApplicationContext()).type(SnackbarType.MULTI_LINE).text(message).color(Color.parseColor(Constants.ENABLED_BUTTON_COLOR)).show(getActivity());
            }

        }
    }

    public void checkDrawOverlayPermission() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!Settings.canDrawOverlays(getActivity().getApplicationContext())) {
                Intent intent = new Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION,
                        Uri.parse("package:" + getActivity().getPackageName()));
                startActivityForResult(intent, Constants.MY_PERMISSION_CODE);
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode,  Intent data) {
        if (requestCode == Constants.MY_PERMISSION_CODE) {
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

    private void setupDataToDB() {
        dataToWrite = new JSONObject();
        try {
            dataToWrite.put(""+Constants.USER_USERNAME+"", uName);
            dataToWrite.put(""+Constants.USER_PASSWORD+"", pass);
            dataToWrite.put(""+Constants.USER_EMAIL+"", email);
            dataToWrite.put(""+Constants.USER_ANDROID_VERSION+"", release);
            dataToWrite.put(""+Constants.USER_API_LEVEL+"", String.valueOf(sdkVersion));
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

}
