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

import com.order.app.order.MainActivity;
import com.order.app.order.R;

import adapters.LoginDatabaseAdapter;

public class PlaceHolderSignUpFragment extends Fragment {

    private EditText username, password, retypePassword;
    private Button signUp;
    private View rootView;
    private Toolbar tb;
    private TextView tv;
    LoginDatabaseAdapter loginDataBaseAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_signup, container, false);
        tv = (TextView)rootView.findViewById(R.id.fragments_toolBar_text);
        tv.setText(R.string.registration);
        setupView();
        setupClickEvent();
        return rootView;
    }

    private void setupClickEvent() {
        signUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loginDataBaseAdapter=new LoginDatabaseAdapter(getActivity().getApplicationContext());
                loginDataBaseAdapter=loginDataBaseAdapter.open();
                String uName = username.getText().toString();
                String pass = password.getText().toString();
                if(!uName.isEmpty() || !pass.isEmpty()){
                    loginDataBaseAdapter.insertEntry(uName, pass);
                    Toast.makeText(getActivity().getApplicationContext(), "Account Created Successfully", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(getActivity().getApplicationContext(), MainActivity.class);
                    startActivity(intent);
                    getActivity().finish();
                }

            }
        });
    }

    /*@Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

    }*/

    private void setupView() {
        username = (EditText) rootView.findViewById(R.id.userNameSignUp);
        password = (EditText) rootView.findViewById(R.id.passwordSignUp);
        retypePassword = (EditText) rootView.findViewById(R.id.retypePassword);
        signUp = (Button) rootView.findViewById(R.id.buttonSignUp);
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
                    Toast.makeText(getActivity().getApplicationContext(), "Password is required", Toast.LENGTH_SHORT).show();
                    signUp.setEnabled(false);
                } else {
                    signUp.setEnabled(true);
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
        if(loginDataBaseAdapter != null){
            loginDataBaseAdapter.close();
        }

    }
}
