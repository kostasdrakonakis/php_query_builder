package fragments;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.order.app.order.R;
import com.order.app.order.UserData;

import adapters.LoginDatabaseAdapter;
import dialogs.DialogMessageDisplay;
import sessions.SessionManager;

public class PlaceHolderSignInFragment extends Fragment {

    private EditText username, password;
    private Button signIn;
    private View rootView;
    private Toolbar tb;
    private SessionManager session;
    private TextView tv;
    LoginDatabaseAdapter loginDataBaseAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_signin, container, false);
        loginDataBaseAdapter = new LoginDatabaseAdapter(getActivity().getApplicationContext());
        loginDataBaseAdapter = loginDataBaseAdapter.open();
        tv = (TextView)rootView.findViewById(R.id.fragments_toolBar_text);
        tv.setText(R.string.login);
        session = new SessionManager(getActivity().getApplicationContext());
        if (session.isLoggedIn()) {
            Intent intent = new Intent(getActivity().getApplicationContext(), UserData.class);
            startActivity(intent);
            getActivity().finish();
        }
        setupView();
        setupClickEvent();
        return rootView;
    }

    /*@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        ((ActionBarActivity) getActivity()).getSupportActionBar().setTitle(R.string.login);
        super.onActivityCreated(savedInstanceState);

    }*/

    private void setupClickEvent() {
        signIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                String userName = username.getText().toString();
                String userPass = password.getText().toString();

                String storedPassword = loginDataBaseAdapter.getSinlgeEntry(userName);
                if (!userName.isEmpty() || !userPass.isEmpty()) {
                    if (userPass.equals(storedPassword)) {
                        session.createLoginSession(userName, "waiterx@gmail.com");
                        Intent intent = new Intent(getActivity().getApplicationContext(), UserData.class);
                        startActivity(intent);
                        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_right);
                        getActivity().finish();
                    } else {
                        DialogMessageDisplay.displayMessage(getActivity().getApplicationContext(), "Error", "Username or Password do not match");
                    }
                }

            }
        });
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
                    Toast.makeText(getActivity().getApplicationContext(), "Username is required", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(getActivity().getApplicationContext(), "Password is required", Toast.LENGTH_SHORT).show();
                    signIn.setEnabled(false);
                } else {
                    signIn.setEnabled(true);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        loginDataBaseAdapter.close();
    }
}
