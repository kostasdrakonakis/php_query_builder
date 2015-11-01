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
import android.widget.TextView;
import android.widget.Toast;

import com.nispok.snackbar.Snackbar;
import com.nispok.snackbar.enums.SnackbarType;
import com.order.app.order.MainActivity;
import com.order.app.order.R;

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
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Random;

import functions.AppConstant;

public class Register extends Fragment {
    private EditText username, password, retypePassword, emailField;
    private Button signUp;
    private View rootView;
    private TextView tv;
    private ProgressDialog pDialog;
    private ArrayList<NameValuePair> nameValuePairs;
    private HttpResponse response;
    private HttpClient httpClient;
    private HttpPost httpPost;
    private HttpEntity httpEntity;
    private InputStream is = null;
    private MyInsertDataTask task;
    private String uName, pass, email, date, release;
    private Calendar c;
    private DateFormat dateFormat;
    private AlertDialog.Builder builder;
    private Random random;
    private int sdkVersion;
    private JSONObject data;
    private boolean usernameExists=true;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.signup, container, false);
        release = Build.VERSION.RELEASE;
        sdkVersion = Build.VERSION.SDK_INT;
        setupView();

        createDateFormat();
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

    private void createDateFormat() {
        c = Calendar.getInstance();
        dateFormat = new SimpleDateFormat("dd" + "/" + "MM" + "/" + "yyyy");
        date = dateFormat.format(c.getTime());
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
        task.execute(new String[]{AppConstant.REGISTER_URL});
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
                    Snackbar.with(getActivity().getApplicationContext()).text(getString(R.string.uname_required)).color(Color.parseColor(AppConstant.ENABLED_BUTTON_COLOR)).show(getActivity());
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
                    Snackbar.with(getActivity().getApplicationContext()).text(getString(R.string.password_required)).color(Color.parseColor(AppConstant.ENABLED_BUTTON_COLOR)).show(getActivity());
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
                    Snackbar.with(getActivity().getApplicationContext()).text(getString(R.string.retype_pass_required)).color(Color.parseColor(AppConstant.ENABLED_BUTTON_COLOR)).show(getActivity());
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
                    Snackbar.with(getActivity().getApplicationContext()).text(getString(R.string.email_required)).color(Color.parseColor(AppConstant.ENABLED_BUTTON_COLOR)).show(getActivity());
                    signUp.setEnabled(false);
                    signUp.setBackgroundColor(Color.parseColor(AppConstant.DISABLED_BUTTON_COLOR));
                } else {
                    if (isEmailValid(emailField.getText().toString())) {
                        signUp.setEnabled(true);
                        signUp.setBackgroundColor(Color.parseColor(AppConstant.ENABLED_BUTTON_COLOR));
                    }else {
                        Snackbar.with(getActivity().getApplicationContext()).text(getString(R.string.email_not_valid)).color(Color.parseColor(AppConstant.ENABLED_BUTTON_COLOR)).show(getActivity());
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

    private int getRandom(){
        random = new Random();
        return random.nextInt(10000);
    }

    private class MyInsertDataTask extends AsyncTask<String, Void, Boolean> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(getActivity().getApplicationContext());
            pDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setIndeterminate(true);
            pDialog.setMessage(getString(R.string.dialog_rate_data_submit));
            pDialog.setCancelable(false);
            pDialog.setInverseBackgroundForced(true);
            pDialog.show();
        }

        @Override
        protected Boolean doInBackground(String... params) {
            nameValuePairs = new ArrayList<>();
            nameValuePairs.add(new BasicNameValuePair("username", uName));
            nameValuePairs.add(new BasicNameValuePair("password", pass));
            nameValuePairs.add(new BasicNameValuePair("email", email));
            nameValuePairs.add(new BasicNameValuePair("dateCreated", date));
            nameValuePairs.add(new BasicNameValuePair("servitoros_id", String.valueOf(getRandom())));
            nameValuePairs.add(new BasicNameValuePair("magazi_id", String.valueOf(2)));
            nameValuePairs.add(new BasicNameValuePair("android_version", release));
            nameValuePairs.add(new BasicNameValuePair("api_level", String.valueOf(sdkVersion)));

            try
            {
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
                if (result != null) {
                    data = new JSONObject(result.toString());
                    usernameExists = data.getBoolean("result");
                }
            }
            catch(Exception e)
            {
                Log.e("Fail 1", e.toString());
            }
            return  usernameExists;
        }
        @Override
        protected void onPostExecute(Boolean aVoid) {
            super.onPostExecute(aVoid);
            pDialog.dismiss();
            if (aVoid){
                Snackbar.with(getActivity().getApplicationContext()).type(SnackbarType.MULTI_LINE).text(getString(R.string.username_exists_message)).color(Color.parseColor(AppConstant.ENABLED_BUTTON_COLOR)).show(getActivity());
            }else {
                Toast.makeText(getActivity(), getString(R.string.created_successfully), Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                startActivity(intent);
                getActivity().finish();
            }

        }
    }

}
