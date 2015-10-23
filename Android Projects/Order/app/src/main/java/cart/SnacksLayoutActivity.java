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
import java.util.ArrayList;

public class SnacksLayoutActivity extends AppCompatActivity {

    private static final String URL = "http://my.chatapp.info/order_api/insertData/insert_snacks_to_cart.php";
    private static final String COMPANY_INTENT_ID = "magaziID";
    private static final String WAITER_INTENT_ID = "servitorosID";
    private static final String TABLE_INTENT_ID = "table_name";
    private String name, table, price, image, servitoros_id, magazi_id, quantityPreference, comment, cookingPreference;
    private EditText quantity, sxolia;
    private Button plus, minus, cart;
    private StringBuffer extraPreference, withoutPreference;
    private int quantityNumberFinal;
    private CheckBox fries, friesW, tomato, tomatoW, pepper, pepperW, onnion, onnionW, cheese, cheeseW, ham, hamW, bacon, baconW, lettuce, lettuceW, cabbage, cabbageW, rare, medium, wellDone, vWellDone;
    private ProgressDialog pDialog;
    private HttpPost httpPost;
    private ArrayList<NameValuePair> nameValuePairs;
    private HttpClient httpClient;
    private HttpEntity httpEntity;
    private HttpResponse response;
    private InputStream is = null;
    private MyInsertDataTask task;
    private int extraBufferLength, withoutBufferLenght;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_snacks_layout);
        populateActionBar();
        checkQuantity();
        populateExtraCheckBoxes();
        populateWithoutCheckBoxes();
        populateCookingTime();
    }

    private void populateCookingTime() {
        rare = (CheckBox)findViewById(R.id.rarecheckBox);
        medium = (CheckBox)findViewById(R.id.mediumCookedcheckBox);
        wellDone = (CheckBox)findViewById(R.id.wellDonecheckBox);
        vWellDone = (CheckBox)findViewById(R.id.veryWellDonecheckBox);
        rare.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (rare.isChecked()) {
                    medium.setEnabled(false);
                    wellDone.setEnabled(false);
                    vWellDone.setEnabled(false);
                    cookingPreference = rare.getText().toString();
                } else {
                    medium.setEnabled(true);
                    wellDone.setEnabled(true);
                    vWellDone.setEnabled(true);
                    cookingPreference = "";
                }
            }
        });
        medium.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (medium.isChecked()) {
                    rare.setEnabled(false);
                    wellDone.setEnabled(false);
                    vWellDone.setEnabled(false);
                    cookingPreference = medium.getText().toString();
                } else {
                    rare.setEnabled(true);
                    wellDone.setEnabled(true);
                    vWellDone.setEnabled(true);
                    cookingPreference = "";
                }
            }
        });
        wellDone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (wellDone.isChecked()) {
                    medium.setEnabled(false);
                    rare.setEnabled(false);
                    vWellDone.setEnabled(false);
                    cookingPreference = wellDone.getText().toString();
                } else {
                    medium.setEnabled(true);
                    rare.setEnabled(true);
                    vWellDone.setEnabled(true);
                    cookingPreference = "";
                }
            }
        });
        vWellDone.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (vWellDone.isChecked()) {
                    medium.setEnabled(false);
                    wellDone.setEnabled(false);
                    rare.setEnabled(false);
                    cookingPreference = vWellDone.getText().toString();
                } else {
                    medium.setEnabled(true);
                    wellDone.setEnabled(true);
                    rare.setEnabled(true);
                    cookingPreference = "";
                }
            }
        });

    }

    private void populateExtraCheckBoxes() {
        fries = (CheckBox)findViewById(R.id.fries);
        tomato = (CheckBox)findViewById(R.id.tomatoes);
        pepper = (CheckBox)findViewById(R.id.peppers);
        onnion = (CheckBox)findViewById(R.id.onions);
        cheese = (CheckBox)findViewById(R.id.cheese);
        bacon = (CheckBox)findViewById(R.id.bacon);
        ham = (CheckBox)findViewById(R.id.ham);
        lettuce = (CheckBox)findViewById(R.id.lettuce);
        cabbage = (CheckBox)findViewById(R.id.cabbage);
        fries.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (fries.isChecked()){
                    friesW.setEnabled(false);
                }else {
                    friesW.setEnabled(true);
                }
            }
        });
        tomato.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (tomato.isChecked()){
                    tomatoW.setEnabled(false);
                }else {
                    tomatoW.setEnabled(true);
                }
            }
        });
        pepper.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (pepper.isChecked()){
                    pepperW.setEnabled(false);
                }else {
                    pepperW.setEnabled(true);
                }
            }
        });
        onnion.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (onnion.isChecked()){
                    onnionW.setEnabled(false);
                }else {
                    onnionW.setEnabled(true);
                }
            }
        });
        cheese.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (cheese.isChecked()){
                    cheeseW.setEnabled(false);
                }else {
                    cheeseW.setEnabled(true);
                }
            }
        });
        bacon.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (bacon.isChecked()){
                    baconW.setEnabled(false);
                }else {
                    baconW.setEnabled(true);
                }
            }
        });
        ham.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (ham.isChecked()){
                    hamW.setEnabled(false);
                }else {
                    hamW.setEnabled(true);
                }
            }
        });
        lettuce.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (lettuce.isChecked()){
                    lettuceW.setEnabled(false);
                }else {
                    lettuceW.setEnabled(true);
                }
            }
        });
        cabbage.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (cabbage.isChecked()){
                    cabbageW.setEnabled(false);
                }else {
                    cabbageW.setEnabled(true);
                }
            }
        });

    }

    private void checkWhatExtraSelected() {
        extraPreference = new StringBuffer();
        if (fries.isChecked()){
            extraPreference.append(fries.getText().toString() + ", ");
        }
        if (tomato.isChecked()){
            extraPreference.append(tomato.getText().toString() + ", ");
        }
        if (onnion.isChecked()){
            extraPreference.append(onnion.getText().toString() + ", ");
        }
        if (pepper.isChecked()){
            extraPreference.append(pepper.getText().toString() + ", ");
        }
        if (lettuce.isChecked()){
            extraPreference.append(lettuce.getText().toString() + ", ");
        }
        if (cabbage.isChecked()){
            extraPreference.append(cabbage.getText().toString() + ", ");
        }
        if (cheese.isChecked()){
            extraPreference.append(cheese.getText().toString() + ", ");
        }
        if (ham.isChecked()){
            extraPreference.append(ham.getText().toString() + ", ");
        }
        if (bacon.isChecked()){
            extraPreference.append(bacon.getText().toString() + ", ");
        }

        extraBufferLength = extraPreference.length();
    }

    private void populateWithoutCheckBoxes() {
        friesW = (CheckBox)findViewById(R.id.friesWithout);
        tomatoW = (CheckBox)findViewById(R.id.tomatoesWithout);
        pepperW = (CheckBox)findViewById(R.id.peppersWithout);
        onnionW = (CheckBox)findViewById(R.id.onionsWithout);
        lettuceW = (CheckBox)findViewById(R.id.lettuceWithout);
        cabbageW = (CheckBox)findViewById(R.id.cabbageWithout);
        cheeseW = (CheckBox)findViewById(R.id.cheeseWithout);
        hamW = (CheckBox)findViewById(R.id.hamWithout);
        baconW = (CheckBox)findViewById(R.id.baconWithout);
        friesW.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (friesW.isChecked()){
                    fries.setEnabled(false);
                }else {
                    fries.setEnabled(true);
                }
            }
        });
        tomatoW.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (tomatoW.isChecked()){
                    tomato.setEnabled(false);
                }else {
                    tomato.setEnabled(true);
                }
            }
        });
        pepperW.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (pepperW.isChecked()){
                    pepper.setEnabled(false);
                }else {
                    pepper.setEnabled(true);
                }
            }
        });
        onnionW.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (onnionW.isChecked()){
                    onnion.setEnabled(false);
                }else {
                    onnion.setEnabled(true);
                }
            }
        });
        cheeseW.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (cheeseW.isChecked()){
                    cheese.setEnabled(false);
                }else {
                    cheese.setEnabled(true);
                }
            }
        });
        baconW.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (baconW.isChecked()){
                    bacon.setEnabled(false);
                }else {
                    bacon.setEnabled(true);
                }
            }
        });
        hamW.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (hamW.isChecked()){
                    ham.setEnabled(false);
                }else {
                    ham.setEnabled(true);
                }
            }
        });
        lettuceW.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (lettuceW.isChecked()){
                    lettuce.setEnabled(false);
                }else {
                    lettuce.setEnabled(true);
                }
            }
        });
        cabbageW.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (cabbageW.isChecked()){
                    cabbage.setEnabled(false);
                }else {
                    cabbage.setEnabled(true);
                }
            }
        });
    }


    private void checkQuantity() {
        quantity = (EditText) findViewById(R.id.quantitySnackEditText);
        plus = (Button) findViewById(R.id.buttonSnackPlus);
        minus = (Button) findViewById(R.id.buttonSnackMinus);
        sxolia = (EditText)findViewById(R.id.editTextSnackComments);
        cart = (Button)findViewById(R.id.cartSnackButton);

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

                checkWhatExtraSelected();
                checkWhatWithoutSelected();
                if (extraBufferLength > 0){
                    extraPreference = extraPreference.deleteCharAt(extraBufferLength - 2);
                }
                if (withoutBufferLenght > 0){
                    withoutPreference = withoutPreference.deleteCharAt(withoutBufferLenght - 2);
                }
                if (cookingPreference.isEmpty()){
                    Toast.makeText(SnacksLayoutActivity.this, getString(R.string.cooking_time_required), Toast.LENGTH_LONG).show();
                }else {
                    accessWebService();
                }


            }
        });
    }



    private void checkWhatWithoutSelected() {
        withoutPreference = new StringBuffer();
        if (friesW.isChecked()){
            withoutPreference.append(friesW.getText().toString() + ", ");
        }
        if (tomatoW.isChecked()){
            withoutPreference.append(tomatoW.getText().toString() + ", ");
        }
        if (onnionW.isChecked()){
            withoutPreference.append(onnionW.getText().toString() + ", ");
        }
        if (pepperW.isChecked()){
            withoutPreference.append(pepperW.getText().toString() + ", ");
        }
        if (lettuceW.isChecked()){
            withoutPreference.append(lettuceW.getText().toString() + ", ");
        }
        if (cabbageW.isChecked()){
            withoutPreference.append(cabbageW.getText().toString() + ", ");
        }
        if (cheeseW.isChecked()){
            withoutPreference.append(cheeseW.getText().toString() + ", ");
        }
        if (hamW.isChecked()){
            withoutPreference.append(hamW.getText().toString() + ", ");
        }
        if (baconW.isChecked()){
            withoutPreference.append(baconW.getText().toString() + ", ");
        }

        withoutBufferLenght = withoutPreference.length();
    }

    private void populateActionBar() {
        toolbar = (Toolbar)findViewById(R.id.toolBar);
        name = getIntent().getStringExtra("snackName");
        price = getIntent().getStringExtra("snackPrice");
        table = getIntent().getStringExtra(TABLE_INTENT_ID);
        image = getIntent().getStringExtra("snackImage");
        servitoros_id = getIntent().getStringExtra(WAITER_INTENT_ID);
        magazi_id = getIntent().getStringExtra(COMPANY_INTENT_ID);
        toolbar.setTitle(name + " - " + getString(R.string.price) + " " + price);
        toolbar.setSubtitle(getString(R.string.table_id) + table);
        setSupportActionBar(toolbar);
    }

    private void accessWebService() {
        task = new MyInsertDataTask();
        task.execute(new String[]{URL});
    }


    private class MyInsertDataTask extends AsyncTask<String, Void, Void> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(SnacksLayoutActivity.this);
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
            nameValuePairs.add(new BasicNameValuePair("cooking_time", cookingPreference));
            nameValuePairs.add(new BasicNameValuePair("extraPreference", extraPreference.toString()));
            nameValuePairs.add(new BasicNameValuePair("withoutPreference", withoutPreference.toString()));
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
            Toast.makeText(SnacksLayoutActivity.this, getString(R.string.cart_addition_successfull), Toast.LENGTH_LONG).show();
            SnacksLayoutActivity.this.finish();
        }
    }

}
