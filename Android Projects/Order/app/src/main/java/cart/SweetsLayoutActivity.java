package cart;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.order.app.order.R;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;

import java.io.InputStream;
import java.util.ArrayList;

public class SweetsLayoutActivity extends AppCompatActivity {

    private static final String URL = "http://my.chatapp.info/order_api/insertData/insert_sweets_to_cart.php";
    private static final String COMPANY_INTENT_ID = "magaziID";
    private static final String WAITER_INTENT_ID = "servitorosID";
    private static final String TABLE_INTENT_ID = "table_name";
    private static final String SWEET_NAME = "sweetsName";
    private static final String SWEET_IMAGE = "sweetsImage";
    private static final String SWEET_PRICE = "sweetsPrice";
    private String name, table, price, image, servitoros_id, magazi_id;
    private CheckBox vanilla, chocolate, strawberry, chocolateSyrup, strawberrySyrup, caramelSyrup, pistachio, banana, mango, cookies, oreo, cheeseCake, caramel, sorbet, pineapple;
    private EditText quantity, sxolia, quantityIceCream;
    private Button cart, plus, minus, iceCreamPlus, iceCreamMinus;
    private String quantityPreference, comment, quantityIceCreamPreference;
    private ProgressDialog pDialog;
    private HttpPost httpPost;
    private ArrayList<NameValuePair> nameValuePairs;
    private HttpClient httpClient;
    private HttpEntity httpEntity;
    private HttpResponse response;
    private int quantityNumberFinal, quantityIceCreamNumberFinal;
    private InputStream is = null;
    private StringBuffer iceCreamPreference, syrupPreference;
    private int syrupBufferLength, iceCreamBufferLength;
    private MyInsertDataTask task;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sweets_layout);
        populateActionBar();
        checkQuantity();
        populateIceCreamCheckBoxes();
        populateSyrupCheckBoxes();
    }

    private void populateSyrupCheckBoxes() {
        chocolateSyrup = (CheckBox) findViewById(R.id.chocolateSyrupCheckBox);
        strawberrySyrup = (CheckBox) findViewById(R.id.strawberrySyrupCheck);
        caramelSyrup = (CheckBox) findViewById(R.id.caramelSyrupcheckBox);
    }

    private void populateIceCreamCheckBoxes() {
        chocolate = (CheckBox) findViewById(R.id.chocolateCheckbox);
        strawberry = (CheckBox) findViewById(R.id.strawberryCheck);
        vanilla = (CheckBox) findViewById(R.id.vanillaCheckBox);
        mango = (CheckBox) findViewById(R.id.mangocheckBox6);
        oreo = (CheckBox) findViewById(R.id.oreocheckBox);
        cookies = (CheckBox) findViewById(R.id.cookiescheckBox);
        pistachio = (CheckBox) findViewById(R.id.peanutscheckBox5);
        cheeseCake = (CheckBox) findViewById(R.id.cheeseCakecheckBox);
        banana = (CheckBox) findViewById(R.id.bananaCheckBox);
        sorbet = (CheckBox) findViewById(R.id.sorbetcheckBox);
        pineapple = (CheckBox) findViewById(R.id.pineapplecheckBox);
        caramel = (CheckBox) findViewById(R.id.caramelcheckBox);
    }

    private void populateActionBar() {
        toolbar = (Toolbar)findViewById(R.id.toolBar);
        name = getIntent().getStringExtra(SWEET_NAME);
        price = getIntent().getStringExtra(SWEET_PRICE);
        table = getIntent().getStringExtra(TABLE_INTENT_ID);
        image = getIntent().getStringExtra(SWEET_IMAGE);
        servitoros_id = getIntent().getStringExtra(WAITER_INTENT_ID);
        magazi_id = getIntent().getStringExtra(COMPANY_INTENT_ID);
        toolbar.setTitle(name + " - " + getString(R.string.price) + " " + price);
        toolbar.setSubtitle(getString(R.string.table_id) + table);
        setSupportActionBar(toolbar);
    }

    private void checkQuantity() {
        quantity = (EditText) findViewById(R.id.quantitySweetEditText);
        plus = (Button) findViewById(R.id.buttonSweetPlus);
        minus = (Button) findViewById(R.id.buttonSweetMinus);
        sxolia = (EditText) findViewById(R.id.editTextSweetsComments);
        cart = (Button) findViewById(R.id.cartSweetsButton);
        iceCreamPlus = (Button) findViewById(R.id.buttonIceCreamPlus);
        iceCreamMinus = (Button) findViewById(R.id.buttonIceCreamMinus);
        quantityIceCream = (EditText) findViewById(R.id.iceCreamQuantityEditText);
        plus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addTxt = quantity.getText().toString();
                int add = Integer.parseInt(addTxt);
                quantity.setText(String.valueOf(add + 1));
            }
        });
        minus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String minusTxt = quantity.getText().toString();
                int minus = Integer.parseInt(minusTxt);
                if (minus > 0) {
                    quantity.setText(String.valueOf(minus - 1));
                }
            }
        });
        iceCreamPlus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String addTxt = quantityIceCream.getText().toString();
                int add = Integer.parseInt(addTxt);
                quantityIceCream.setText(String.valueOf(add + 1));
            }
        });
        iceCreamMinus.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String minusTxt = quantityIceCream.getText().toString();
                int minus = Integer.parseInt(minusTxt);
                if (minus > 0) {
                    quantityIceCream.setText(String.valueOf(minus - 1));
                }
            }
        });


        quantity.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                String quanText = quantity.getText().toString();
                int numberQuant = Integer.parseInt(quanText);
                if (numberQuant > 0) {
                    cart.setEnabled(true);
                    cart.setBackgroundColor(getResources().getColor(R.color.btn_login));
                } else {
                    cart.setEnabled(false);
                    cart.setBackgroundColor(getResources().getColor(R.color.light_gray));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                quantityPreference = quantity.getText().toString();
                quantityNumberFinal = Integer.parseInt(quantityPreference);
                quantityIceCreamPreference = quantityIceCream.getText().toString();
                quantityIceCreamNumberFinal = Integer.parseInt(quantityIceCreamPreference);

                comment = sxolia.getText().toString();
                if (comment == null) {
                    comment = " ";
                }

                checkWhatIceCreamSelected();
                checkWhatSyrupSelected();

                if (iceCreamBufferLength > 0) {
                    iceCreamPreference = iceCreamPreference.deleteCharAt(iceCreamBufferLength - 2);
                }
                if (syrupBufferLength > 0) {
                    syrupPreference = syrupPreference.deleteCharAt(syrupBufferLength - 2);
                }
                accessWebService();
            }
        });
    }

    private void checkWhatSyrupSelected() {
        syrupPreference = new StringBuffer();
        if (chocolateSyrup.isChecked()) {
            syrupPreference.append(chocolateSyrup.getText().toString() + ", ");
        }
        if (strawberrySyrup.isChecked()) {
            syrupPreference.append(strawberrySyrup.getText().toString() + ", ");
        }
        if (caramelSyrup.isChecked()) {
            syrupPreference.append(caramelSyrup.getText().toString() + ", ");
        }
        syrupBufferLength = syrupPreference.length();

    }

    private void checkWhatIceCreamSelected() {
        iceCreamPreference = new StringBuffer();

        if (chocolate.isChecked()){
            iceCreamPreference.append(chocolate.getText().toString() + ", ");
        }
        if (strawberry.isChecked()){
            iceCreamPreference.append(strawberry.getText().toString() + ", ");
        }
        if (vanilla.isChecked()){
            iceCreamPreference.append(vanilla.getText().toString() + ", ");
        }
        if (banana.isChecked()){
            iceCreamPreference.append(banana.getText().toString() + ", ");
        }
        if (cookies.isChecked()){
            iceCreamPreference.append(cookies.getText().toString() + ", ");
        }
        if (pistachio.isChecked()){
            iceCreamPreference.append(pistachio.getText().toString() + ", ");
        }
        if (cheeseCake.isChecked()){
            iceCreamPreference.append(cheeseCake.getText().toString() + ", ");
        }
        if (oreo.isChecked()){
            iceCreamPreference.append(oreo.getText().toString() + ", ");
        }
        if (mango.isChecked()){
            iceCreamPreference.append(mango.getText().toString() + ", ");
        }
        if (caramel.isChecked()){
            iceCreamPreference.append(caramel.getText().toString() + ", ");
        }
        if (pineapple.isChecked()){
            iceCreamPreference.append(pineapple.getText().toString() + ", ");
        }
        if (sorbet.isChecked()){
            iceCreamPreference.append(sorbet.getText().toString() + ", ");
        }
        iceCreamBufferLength = iceCreamPreference.length();

    }

    private void accessWebService() {
        task = new MyInsertDataTask();
        task.execute(new String[]{URL});
    }


    private class MyInsertDataTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SweetsLayoutActivity.this);
            pDialog.getWindow().setType(WindowManager.LayoutParams.TYPE_SYSTEM_ALERT);
            pDialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            pDialog.setIndeterminate(true);
            pDialog.setMessage(getString(R.string.dialog_rate_data_submit));
            pDialog.setCancelable(false);
            pDialog.setInverseBackgroundForced(true);
            pDialog.show();
        }

        @Override
        protected Void doInBackground(String... params) {
            nameValuePairs = new ArrayList<>();

            nameValuePairs.add(new BasicNameValuePair("productName", name));
            nameValuePairs.add(new BasicNameValuePair("productPrice", String.valueOf(price)));
            nameValuePairs.add(new BasicNameValuePair("productImage", image));
            nameValuePairs.add(new BasicNameValuePair("quantity", String.valueOf(quantityNumberFinal)));
            nameValuePairs.add(new BasicNameValuePair("iceCreamScoops", String.valueOf(quantityIceCreamNumberFinal)));
            nameValuePairs.add(new BasicNameValuePair("iceCreamFlavors", iceCreamPreference.toString()));
            nameValuePairs.add(new BasicNameValuePair("syrupFlavors", syrupPreference.toString()));
            nameValuePairs.add(new BasicNameValuePair("comment", comment));
            nameValuePairs.add(new BasicNameValuePair("magazi_id", magazi_id));
            nameValuePairs.add(new BasicNameValuePair("servitoros_id", servitoros_id));
            nameValuePairs.add(new BasicNameValuePair("trapezi", table));
            try
            {
                httpClient = new DefaultHttpClient();
                httpPost = new HttpPost(params[0]);
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs, "UTF-8"));
                response = httpClient.execute(httpPost);
                httpEntity = response.getEntity();
                is = httpEntity.getContent();
            }
            catch(Exception e)
            {
                Log.e("Fail 1", e.toString());
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            pDialog.dismiss();
            Toast.makeText(SweetsLayoutActivity.this, getString(R.string.cart_addition_successfull), Toast.LENGTH_LONG).show();
            SweetsLayoutActivity.this.finish();
        }
    }


}
