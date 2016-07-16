package com.library.quizgame;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import constants.Constants;
import constants.StringGenerator;
import dialogs.DialogMessageDisplay;
import lists.QuestionsList;

import static android.view.View.SCROLL_AXIS_HORIZONTAL;
import static android.view.View.SCROLL_AXIS_VERTICAL;
import static android.view.View.TEXT_ALIGNMENT_CENTER;
import static android.view.View.TEXT_ALIGNMENT_GRAVITY;

public class StartGameActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private String langText, ans1, ans2, ans3, questionName, answer, iscorrect, curName, is1, is2, is3, correctAnswerText;
    private SharedPreferences sharedPreferences;
    private StartGameReadTask task;
    private ProgressDialog pDialog;
    private List<QuestionsList> startGameList, startGameArray;
    private TextView question, wrongText, correctText, rowTextView;
    private RadioButton randomAnswer1, randomAnswer2, randomAnswer3, correct;
    private RadioGroup group;
    private int position = 0, score = 0, p=-1, lifes, number;
    private FloatingActionButton next;
    private List<String> question_iscorrect, question_answers, curArray, curIscorrect;
    private URL url;
    private HttpURLConnection httpURLConnection;
    private InputStream inputStream;
    private StringBuilder jsonResult;
    private JSONObject jsonResponse, jsonChildNode, jsonSecondChildNode;
    private JSONArray jsonMainNode, jsonArray;
    private HashMap<String, String> map = new HashMap<>();
    private Toast correctToast, wrongToast;
    private View correctToastView, wrongToastView;
    private Thread correctThread, wrongThread;
    private TextView[] myTextViews;
    private LinearLayout.LayoutParams params;
    private HorizontalScrollView scrollView;
    private LinearLayout textViewLayout;
    private ConnectivityManager connectivityManager;
    private NetworkInfo networkInfo;
    private boolean network_connected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFromPrefs();
        StringGenerator.setLocale(langText, StartGameActivity.this);
        setContentView(R.layout.activity_start_game);
        setupToolbar();
        initializeComponents();
        checkNetwork();
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getUserSelection()){
                    Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(500);
                                StartGameActivity.this.runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        moveToNextQuestion();
                                        p++;
                                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                                            myTextViews[p].setBackgroundColor(getResources().getColor(R.color.colorPrimary));
                                        }
                                        scrollView.setFocusable(false);
                                        scrollView.setVerticalScrollBarEnabled(false);
                                        scrollView.setHorizontalScrollBarEnabled(false);
                                        scrollView.smoothScrollBy(myTextViews[p].getWidth(), 0);
                                    }
                                });
                            } catch (InterruptedException e) {
                                e.printStackTrace();
                            }
                        }
                    });
                    thread.start();
                }else {
                    StringGenerator.showToast(StartGameActivity.this, getString(R.string.noanswerselected));
                }
            }
        });
    }

    private void checkNetwork() {
        connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        networkInfo = connectivityManager.getActiveNetworkInfo();
        network_connected = networkInfo != null && networkInfo.isAvailable() && networkInfo.isConnectedOrConnecting();
        if (!network_connected) {
            DialogMessageDisplay.displayWifiSettingsDialog(StartGameActivity.this, StartGameActivity.this, getString(R.string.wifi_off_title), getString(R.string.wifi_off_message));
        }else {
            accessWebService();
        }
    }


    private void generateTextViews(List<QuestionsList> lista) {
        if (lista.size() != 0 || lista!=null) {
            number = lista.size();
        }
        myTextViews = new TextView[number]; // create an empty array;
        for (int i = 0; i < lista.size()/3; i++) {
            // create a new textview
            rowTextView = new TextView(StartGameActivity.this);
            params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            params.setMargins(20, 20, 20, 20);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                params.setLayoutDirection(TEXT_ALIGNMENT_CENTER);
            }
            rowTextView.setWidth(180);
            rowTextView.setHeight(180);
            rowTextView.setPadding(70, 40, 40, 40);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
                rowTextView.setBackgroundColor(getResources().getColor(R.color.gray));
            }
            rowTextView.setTextColor(getResources().getColor(R.color.white));
            // set some properties of rowTextView or something
            rowTextView.setText(String.valueOf(i+1));
            // add the textview to the linearlayout
            textViewLayout.addView(rowTextView, params);
            textViewLayout.setScrollContainer(true);
            textViewLayout.setOrientation(LinearLayout.HORIZONTAL);
            textViewLayout.setHorizontalScrollBarEnabled(true);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
                textViewLayout.setGravity(TEXT_ALIGNMENT_GRAVITY);
            }
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                textViewLayout.setScrollIndicators(View.SCROLL_INDICATOR_LEFT|View.SCROLL_INDICATOR_RIGHT);
            }
            textViewLayout.setScrollBarStyle(View.SCROLLBARS_INSIDE_OVERLAY);
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                textViewLayout.setScrollX(SCROLL_AXIS_VERTICAL);
                textViewLayout.setScrollY(SCROLL_AXIS_HORIZONTAL);
            }

            // save a reference to the textview for later
            myTextViews[i] = rowTextView;
        }

    }



    private void moveToNextQuestion() {
        position = position + 3;
        if (position < startGameArray.size()) {
            curName = startGameArray.get(position).getName();
            curArray = startGameArray.get(position).getAnswers();
            curIscorrect = startGameArray.get(position).getIscorrect();
            setupQuestionView(curName, curArray, curIscorrect);
        } else {
            //StringGenerator.showToast(StartGameActivity.this, "Your score : " + score + "/" + (startGameArray.size() / 3));
            int number = startGameArray.size() / 3;
            StringGenerator.showToast(StartGameActivity.this, getString(R.string.endofgame));
            Intent intent = new Intent(StartGameActivity.this, ResultActivity.class);
            intent.putExtra(Constants.PLAYER_SCORE, score);
            intent.putExtra(Constants.LIST_SIZE, number);
            startActivity(intent);
        }
    }

    private boolean getUserSelection() {
        correct = (RadioButton)findViewById(group.getCheckedRadioButtonId());
        if (correct == null){
            return false;
        }else {
            correctAnswerText = correct.getText().toString();
            if (map.get(correctAnswerText).equals(Constants.CORRECTANSWER)) {
                score++;
                setCorrectMessage();
                return true;
            } else {
                setWrongMessage();
                return true;
            }
        }
    }

    private void setCorrectMessage() {
        correctToast = new Toast(StartGameActivity.this);
        correctToastView = getLayoutInflater().inflate(R.layout.correct, (ViewGroup) findViewById(R.id.correctRootLayout));
        correctText = (TextView)correctToastView.findViewById(R.id.correctTextView);
        correctText.setText(getString(R.string.correctAnswer));
        correctToast.setDuration(Toast.LENGTH_SHORT);
        correctToast.setView(correctToastView);
        correctToast.setGravity(Gravity.BOTTOM|Gravity.FILL_HORIZONTAL, 0, 0);
        correctToast.show();
        correctThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                correctToast.cancel();
            }
        });
        correctThread.start();
    }

    private void setWrongMessage() {
        wrongToast = new Toast(StartGameActivity.this);
        wrongToastView = getLayoutInflater().inflate(R.layout.wrong, (ViewGroup) findViewById(R.id.wrongRootLayout));
        wrongText = (TextView)wrongToastView.findViewById(R.id.wrongTextView);
        wrongText.setText(getString(R.string.wrongAnswer));
        wrongToast.setDuration(Toast.LENGTH_SHORT);
        wrongToast.setView(wrongToastView);

        wrongToast.setGravity(Gravity.BOTTOM|Gravity.FILL_HORIZONTAL, 0, 0);
        wrongToast.show();
        wrongThread = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                wrongToast.cancel();
            }
        });
        wrongThread.start();
    }

    private void initializeComponents() {
        question = (TextView)findViewById(R.id.startgameQuestion);
        group = (RadioGroup)findViewById(R.id.radioGroupStartGame);
        randomAnswer1 = (RadioButton)findViewById(R.id.startgameAnswer1);
        randomAnswer2 = (RadioButton)findViewById(R.id.startgameAnswer2);
        randomAnswer3 = (RadioButton)findViewById(R.id.startgameAnswer3);
        textViewLayout = (LinearLayout)findViewById(R.id.textViewGeneratorStartGame);
        scrollView = (HorizontalScrollView)findViewById(R.id.horizontalScrollStartGame);
        next = (FloatingActionButton)findViewById(R.id.startgameButtonNext);
    }

    private void setupToolbar() {
        toolbar = (Toolbar)findViewById(R.id.toolBar);
        toolbar.setTitle(getString(R.string.start_game));
        toolbar.setTitleTextColor(Color.WHITE);
        lifes = getIntent().getIntExtra(Constants.USER_LIFES, 0);
        toolbar.setSubtitle(getString(R.string.lifesuser)+lifes);
        setSupportActionBar(toolbar);
        setupMenu();
    }

    private void setupMenu() {
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                if (item.getItemId() == R.id.startgameOptionsItem){
                    if (lifes == 0){
                        StringGenerator.showToast(StartGameActivity.this, getString(R.string.nomorelifes));
                    }else {
                        checkLifes();
                    }
                }
                return true;
            }
        });
    }

    private void checkLifes() {
        lifes--;
        score--;
        position = position - 3;
        if (position > 0) {
            curName = startGameArray.get(position).getName();
            curArray = startGameArray.get(position).getAnswers();
            curIscorrect = startGameArray.get(position).getIscorrect();
            setupQuestionView(curName, curArray, curIscorrect);
        }
        toolbar.setSubtitle(getString(R.string.lifesuser) + lifes);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_activity_startgame, menu);
        return super.onCreateOptionsMenu(menu);

    }

    private void loadFromPrefs() {
        sharedPreferences = getSharedPreferences(Constants.PREFERENCES_FILE, MODE_PRIVATE);
        langText = sharedPreferences.getString(Constants.LANGUAGE_PREFS_FILE, getString(R.string.ta_to_select));
    }

    private void accessWebService() {
        task = new StartGameReadTask();
        if (langText.equals(getString(R.string.ta_to_select))){
            task.execute(Constants.QUESTIONS_EN_URL);
        }else {
            if (langText.equals(Constants.EN)) {
                task.execute(Constants.QUESTIONS_EN_URL);
                Log.e("URL: ", Constants.QUESTIONS_EN_URL);
            } else if (langText.equals(Constants.GR)) {
                task.execute(Constants.QUESTIONS_URL);
                Log.e("URL: ", Constants.QUESTIONS_URL);
            }
        }
    }

    public class StartGameReadTask extends AsyncTask<String, Void, List<QuestionsList>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(StartGameActivity.this);
            pDialog.setMessage(getString(R.string.loading));
            pDialog.show();
        }

        @Override
        protected List<QuestionsList> doInBackground(String... params) {
            try {
                url = new URL(params[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(10000);
                httpURLConnection.setConnectTimeout(15000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                httpURLConnection.connect();
                inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                jsonResult = StringGenerator.inputStreamToString(inputStream, StartGameActivity.this);
                jsonResponse = new JSONObject(jsonResult.toString());
                Log.e("Response: ", jsonResponse.toString());
                if (langText.equals(getString(R.string.ta_to_select))){
                    jsonMainNode = jsonResponse.optJSONArray(Constants.QUESTIONS_BY_CATEGORY_EN_ARRAY);
                    Log.e("JSON Array: ", jsonMainNode.toString());
                }else {
                    checkDisplayLanguage(langText);
                }
                startGameList = new ArrayList<>();
                for (int i = 0; i < jsonMainNode.length(); i++) {
                    jsonChildNode = jsonMainNode.getJSONObject(i);
                    questionName = jsonChildNode.optString(Constants.QUESTION_NAME_JSON_NAME);
                    jsonArray = jsonChildNode.optJSONArray(Constants.QUESTIONS_ANSWERS_ARRAY);
                    question_answers = new ArrayList<>();
                    question_iscorrect = new ArrayList<>();
                    jsonSecondChildNode = new JSONObject();
                    for (int j = 0; j < jsonArray.length(); j++) {
                        jsonSecondChildNode = jsonArray.getJSONObject(j);
                        answer = jsonSecondChildNode.optString(Constants.ANSWER_NAME_JSON_NAME + (j+1));
                        iscorrect = jsonSecondChildNode.optString(Constants.ISCORRECT_NAME_JSON_NAME + (j+1));
                        question_answers.add(answer);
                        question_iscorrect.add(iscorrect);
                        startGameList.add(new QuestionsList(questionName, question_answers, question_iscorrect));
                    }
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return startGameList;
        }

        @Override
        protected void onPostExecute(List<QuestionsList> lists) {
            super.onPostExecute(lists);
            pDialog.dismiss();
            generateTextViews(lists);
            startGameArray = new ArrayList<>();
            startGameArray = lists;
            if (position < startGameArray.size()) {
                curName = startGameArray.get(position).getName();
                curArray = startGameArray.get(position).getAnswers();
                curIscorrect = startGameArray.get(position).getIscorrect();
            }

            if (startGameArray.size() != 0){
                setupQuestionView(curName, curArray, curIscorrect);
            }
        }
    }

    private void setupQuestionView(String name, List<String> array, List<String> correct){
        question.setText(name);
        ans1 = array.get(0);
        ans2 = array.get(1);
        ans3 = array.get(2);
        is1 = correct.get(0);
        is2 = correct.get(1);
        is3 = correct.get(2);
        randomAnswer1.setText(ans1);
        randomAnswer2.setText(ans2);
        randomAnswer3.setText(ans3);
        map.put(ans1, is1);
        map.put(ans2, is2);
        map.put(ans3, is3);
    }

    private void checkDisplayLanguage(String locale){
        if (locale.equals(Constants.EN)){
            jsonMainNode = jsonResponse.optJSONArray(Constants.QUESTIONS_BY_CATEGORY_EN_ARRAY);
        }else if (locale.equals(Constants.GR)){
            jsonMainNode = jsonResponse.optJSONArray(Constants.QUESTIONS_BY_CATEGORY_ARRAY);
        }
    }
}
