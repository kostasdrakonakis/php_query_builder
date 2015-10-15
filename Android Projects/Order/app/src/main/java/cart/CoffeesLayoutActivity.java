package cart;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
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
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.util.ArrayList;

public class CoffeesLayoutActivity extends Activity {

    private static final String URL = "http://my.chatapp.info/order_api/insertData/insert_coffees_to_cart.php";
    private static final String COMPANY_INTENT_ID = "magaziID";
    private static final String WAITER_INTENT_ID = "servitorosID";
    private static final String TABLE_INTENT_ID = "table_name";
    private String productName, table, price, sugarPreference, milkPreference, dosePreference, quantityPreference, comment, zaximau, image;
    private EditText quantity, sxolia;
    private CheckBox nosugar, medium, sweet, vsweet, yesCheck, noCheck, afrogalo, santigi, monos, diplos;
    private ProgressDialog pDialog;
    private ArrayList<NameValuePair> nameValuePairs;
    private Button plus, minus, cart;
    private HttpClient httpClient;
    private HttpPost httpPost;
    private HttpEntity httpEntity;
    private HttpResponse response;
    private InputStream is = null;
    private MyInsertDataTask task;
    private int quantityNumberFinal;
    private String servitoros_id;
    private String magazi_id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coffees_layout);
        populateActionBar();
        checkQuantity();
        setupCheckBoxes();
    }

    private void setupCheckBoxes() {
        nosugar = (CheckBox)findViewById(R.id.sugaFreecheckBox);
        medium = (CheckBox)findViewById(R.id.mediumSugarcheckBox);
        sweet = (CheckBox)findViewById(R.id.sweetSugarcheckBox);
        vsweet = (CheckBox)findViewById(R.id.verySweetSugarcheckBox);
        yesCheck = (CheckBox)findViewById(R.id.milkEbapYes);
        noCheck = (CheckBox)findViewById(R.id.milkEbapNo);
        afrogalo = (CheckBox)findViewById(R.id.whippedCreamBox);
        santigi = (CheckBox)findViewById(R.id.whippedMilkBox);
        monos = (CheckBox)findViewById(R.id.monoscheckBox);
        diplos = (CheckBox)findViewById(R.id.diploscheckBox);

        nosugar.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (nosugar.isChecked()){
                    medium.setEnabled(false);
                    sweet.setEnabled(false);
                    vsweet.setEnabled(false);
                    sugarPreference = nosugar.getText().toString();
                }else{
                    medium.setEnabled(true);
                    sweet.setEnabled(true);
                    vsweet.setEnabled(true);
                    sugarPreference = "";
                }
            }
        });

        medium.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (medium.isChecked()){
                    nosugar.setEnabled(false);
                    sweet.setEnabled(false);
                    vsweet.setEnabled(false);
                    sugarPreference = medium.getText().toString();
                }else{
                    sweet.setEnabled(true);
                    vsweet.setEnabled(true);
                    nosugar.setEnabled(true);
                    sugarPreference = "";
                }
            }
        });
        sweet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (sweet.isChecked()){
                    nosugar.setEnabled(false);
                    medium.setEnabled(false);
                    vsweet.setEnabled(false);
                    sugarPreference = sweet.getText().toString();
                }else{
                    medium.setEnabled(true);
                    vsweet.setEnabled(true);
                    nosugar.setEnabled(true);
                    sugarPreference = "";
                }
            }
        });
        vsweet.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (vsweet.isChecked()){
                    nosugar.setEnabled(false);
                    sweet.setEnabled(false);
                    medium.setEnabled(false);
                    sugarPreference = vsweet.getText().toString();
                }else{
                    sweet.setEnabled(true);
                    medium.setEnabled(true);
                    nosugar.setEnabled(true);
                    sugarPreference = "";
                }
            }
        });
        yesCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (yesCheck.isChecked()) {
                    noCheck.setEnabled(false);
                    santigi.setEnabled(false);
                    afrogalo.setEnabled(false);
                    milkPreference = yesCheck.getText().toString();
                } else {
                    noCheck.setEnabled(true);
                    santigi.setEnabled(true);
                    afrogalo.setEnabled(true);
                    milkPreference = "";
                }
            }
        });
        noCheck.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (noCheck.isChecked()) {
                    yesCheck.setEnabled(false);
                    santigi.setEnabled(false);
                    afrogalo.setEnabled(false);
                    milkPreference = noCheck.getText().toString();
                } else {
                    yesCheck.setEnabled(true);
                    santigi.setEnabled(true);
                    afrogalo.setEnabled(true);
                    milkPreference = "";
                }
            }
        });
        santigi.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (santigi.isChecked()) {
                    noCheck.setEnabled(false);
                    yesCheck.setEnabled(false);
                    afrogalo.setEnabled(false);
                    milkPreference = santigi.getText().toString();
                } else {
                    noCheck.setEnabled(true);
                    yesCheck.setEnabled(true);
                    afrogalo.setEnabled(true);
                    milkPreference = "";
                }
            }
        });
        afrogalo.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (afrogalo.isChecked()) {
                    noCheck.setEnabled(false);
                    santigi.setEnabled(false);
                    yesCheck.setEnabled(false);
                    milkPreference = afrogalo.getText().toString();
                } else {
                    noCheck.setEnabled(true);
                    santigi.setEnabled(true);
                    yesCheck.setEnabled(true);
                    milkPreference = "";
                }
            }
        });
        monos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (monos.isChecked()){
                    diplos.setEnabled(false);
                    dosePreference = monos.getText().toString();
                }else{
                    diplos.setEnabled(true);
                    dosePreference = "";
                }
            }
        });
        diplos.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (diplos.isChecked()){
                    monos.setEnabled(false);
                    dosePreference = diplos.getText().toString();
                }else{
                    monos.setEnabled(true);
                    dosePreference = "";
                }
            }
        });


    }

    private void checkQuantity() {
        quantity = (EditText) findViewById(R.id.quantityEditText);
        plus = (Button) findViewById(R.id.buttonPlus);
        minus = (Button) findViewById(R.id.buttonMinus);
        sxolia = (EditText)findViewById(R.id.editTextComments);
        cart = (Button)findViewById(R.id.cartButton);

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
                    cart.setBackgroundColor(getResources().getColor(R.color.articlecolor));
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
                comment = sxolia.getText().toString();
                if (comment == null) {
                    comment = " ";
                }
                if (sugarPreference == null){
                    Toast.makeText(CoffeesLayoutActivity.this, getString(R.string.sugar_required), Toast.LENGTH_LONG).show();
                }else if(dosePreference == null){
                    Toast.makeText(CoffeesLayoutActivity.this, getString(R.string.dose_required), Toast.LENGTH_LONG).show();
                }else{
                    accessWebService();
                }

            }
        });
    }

    private void accessWebService() {
        task = new MyInsertDataTask();
        task.execute(new String[]{URL});
    }

    private void populateActionBar() {
        productName = getIntent().getStringExtra("coffeeName");
        price = getIntent().getStringExtra("coffeePrice");
        table = getIntent().getStringExtra(TABLE_INTENT_ID);
        image = getIntent().getStringExtra("coffeeImage");
        servitoros_id = getIntent().getStringExtra(WAITER_INTENT_ID);
        magazi_id = getIntent().getStringExtra(COMPANY_INTENT_ID);
        getActionBar().setTitle(productName + " - " + getString(R.string.price) + " " + price);
        getActionBar().setSubtitle(getString(R.string.table_id) + table);
    }

    private class MyInsertDataTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(CoffeesLayoutActivity.this);
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

            ByteBuffer s = Charset.forName("UTF-8").encode(sugarPreference);

            nameValuePairs.add(new BasicNameValuePair("productName", productName));
            nameValuePairs.add(new BasicNameValuePair("productPrice", String.valueOf(price)));
            nameValuePairs.add(new BasicNameValuePair("productImage", image));
            nameValuePairs.add(new BasicNameValuePair("quantity", String.valueOf(quantityNumberFinal)));
            nameValuePairs.add(new BasicNameValuePair("sugar", sugarPreference));
            nameValuePairs.add(new BasicNameValuePair("milk", milkPreference));
            nameValuePairs.add(new BasicNameValuePair("dose", dosePreference));
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
            Toast.makeText(CoffeesLayoutActivity.this, getString(R.string.cart_addition_successfull), Toast.LENGTH_LONG).show();
            CoffeesLayoutActivity.this.finish();
        }
    }
}
