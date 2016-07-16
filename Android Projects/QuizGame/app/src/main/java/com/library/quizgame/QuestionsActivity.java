package com.library.quizgame;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedInputStream;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import constants.Constants;
import constants.StringGenerator;
import lists.QuestionsList;

import static android.view.View.SCROLL_AXIS_HORIZONTAL;
import static android.view.View.SCROLL_AXIS_VERTICAL;
import static android.view.View.TEXT_ALIGNMENT_CENTER;
import static android.view.View.TEXT_ALIGNMENT_GRAVITY;

public class QuestionsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private String name, id;
    private String langText, ans1, ans2, ans3, questionName, answer, iscorrect, curName, is1, is2, is3, correctAnswerText;
    private SharedPreferences sharedPreferences;
    private QuestionsReadTask questionsReadTask;
    private ProgressDialog pDialog;
    private List<QuestionsList> questionsLists, questionsArray;
    private TextView question, wrongText, correctText, rowTextView;
    private RadioButton ranswer1, ranswer2, ranswer3, correct;
    private RadioGroup group;
    private int position = 0, score = 0, p=-1;
    private FloatingActionButton next;
    private List<String> question_iscorrect, question_answers, curArray, curIscorrect;
    private URL url;
    private HttpURLConnection httpURLConnection;
    private OutputStream outputStream;
    private InputStream inputStream;
    private BufferedWriter bufferedWriter;
    private StringBuilder jsonResult;
    private JSONObject jsonResponse, jsonChildNode, jsonSecondChildNode;
    private JSONArray jsonMainNode, jsonArray;
    private List<NameValuePair> nameValuePairs;
    private LinearLayout rootLayout;
    private LinearLayout noQuestionsLayout;
    private LinearLayout textViewLayout;
    private HashMap<String, String> map = new HashMap<>();
    private Iterator iterator;
    private Toast correctToast, wrongToast;
    private View correctToastView, wrongToastView;
    private Thread correctThread, wrongThread;
    private TextView[] myTextViews;
    private LinearLayout.LayoutParams params;
    private HorizontalScrollView scrollView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFromPrefs();
        StringGenerator.setLocale(langText, QuestionsActivity.this);
        setContentView(R.layout.activity_questions);
        setupToolbar();
        initializeComponents();
        accessWebService();

        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (getUserSelection()){
                    final Thread thread = new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                Thread.sleep(500);
                                QuestionsActivity.this.runOnUiThread(new Runnable() {
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
                    StringGenerator.showToast(QuestionsActivity.this, getString(R.string.noanswerselected));
                }
            }
        });
    }

    private void generateTextViews(List<QuestionsList> lista) {
        int number = lista.size();
        myTextViews = new TextView[number]; // create an empty array;
            for (int i = 0; i < lista.size()/3; i++) {
                // create a new textview
                rowTextView = new TextView(QuestionsActivity.this);
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
        if (position < questionsArray.size()) {
            curName = questionsArray.get(position).getName();
            curArray = questionsArray.get(position).getAnswers();
            curIscorrect = questionsArray.get(position).getIscorrect();
            setupQuestionView(curName, curArray, curIscorrect);
        } else {
            int number = questionsArray.size() / 3;
            StringGenerator.showToast(QuestionsActivity.this, getString(R.string.endofgame));
            Intent intent = new Intent(QuestionsActivity.this, ResultActivity.class);
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
        correctToast = new Toast(QuestionsActivity.this);
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
        wrongToast = new Toast(QuestionsActivity.this);
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
        question = (TextView)findViewById(R.id.questionText);
        group = (RadioGroup)findViewById(R.id.radioGroup);
        ranswer1 = (RadioButton)findViewById(R.id.radioanswer1);
        ranswer2 = (RadioButton)findViewById(R.id.radioanswer2);
        ranswer3 = (RadioButton)findViewById(R.id.radioanswer3);
        rootLayout = (LinearLayout)findViewById(R.id.rootLayout);
        noQuestionsLayout = (LinearLayout)findViewById(R.id.noQuestions);
        textViewLayout = (LinearLayout)findViewById(R.id.textViewGenerator);
        scrollView = (HorizontalScrollView)findViewById(R.id.horizontalScroll);
        next = (FloatingActionButton)findViewById(R.id.btnNext);

    }

    private void accessWebService() {
        questionsReadTask = new QuestionsReadTask();
        if (langText.equals(getString(R.string.ta_to_select)) || langText.equals(null)){
            questionsReadTask.execute(Constants.QUESTIONS_BY_CATEGORY_EN_URL);
        }else {
            if (langText.equals("en")) {
                questionsReadTask.execute(Constants.QUESTIONS_BY_CATEGORY_EN_URL);
            } else if (langText.equals("el")) {
                questionsReadTask.execute(Constants.QUESTIONS_BY_CATEGORY_URL);
            }
        }
    }

    private void setupToolbar() {
        toolbar = (Toolbar)findViewById(R.id.toolBar);
        name = getIntent().getStringExtra(Constants.CATEGORIES_INTENT_NAME);
        id = getIntent().getStringExtra(Constants.CATEGORIES_INTENT_ID);
        toolbar.setTitle(name);
        toolbar.setTitleTextColor(Color.WHITE);
        setSupportActionBar(toolbar);
    }

    private void loadFromPrefs() {
        sharedPreferences = getSharedPreferences(Constants.PREFERENCES_FILE, MODE_PRIVATE);
        langText = sharedPreferences.getString(Constants.LANGUAGE_PREFS_FILE, getString(R.string.ta_to_select));
    }

    public class QuestionsReadTask extends AsyncTask<String, Void, List<QuestionsList>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(QuestionsActivity.this);
            pDialog.setMessage(getString(R.string.loading));
            pDialog.show();
        }

        @Override
        protected List<QuestionsList> doInBackground(String... params) {
            nameValuePairs = new ArrayList<>();
            try {
                url = new URL(params[0]);
                httpURLConnection = (HttpURLConnection) url.openConnection();
                httpURLConnection.setReadTimeout(10000);
                httpURLConnection.setConnectTimeout(15000);
                httpURLConnection.setRequestMethod("POST");
                httpURLConnection.setDoInput(true);
                httpURLConnection.setDoOutput(true);
                setupDataToDB();
                outputStream = httpURLConnection.getOutputStream();
                bufferedWriter = new BufferedWriter(new OutputStreamWriter(outputStream));
                bufferedWriter.write(StringGenerator.queryResults(nameValuePairs));
                bufferedWriter.flush();
                bufferedWriter.close();
                outputStream.close();
                httpURLConnection.connect();
                inputStream = new BufferedInputStream(httpURLConnection.getInputStream());
                jsonResult = StringGenerator.inputStreamToString(inputStream, QuestionsActivity.this);
                jsonResponse = new JSONObject(jsonResult.toString());
                if (langText.equals(getString(R.string.ta_to_select)) || langText.equals(null)){
                    jsonMainNode = jsonResponse.optJSONArray(Constants.QUESTIONS_BY_CATEGORY_EN_ARRAY);
                }else {
                    checkDisplayLanguage(langText);
                }
                questionsLists = new ArrayList<>();
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
                        questionsLists.add(new QuestionsList(questionName, question_answers, question_iscorrect));
                    }
                }
            } catch (IOException | JSONException e) {
                e.printStackTrace();
            }
            return questionsLists;
        }

        @Override
        protected void onPostExecute(List<QuestionsList> lists) {
            super.onPostExecute(lists);
            pDialog.dismiss();
            generateTextViews(lists);
            questionsArray = new ArrayList<>();
            questionsArray = lists;
            if (position < questionsArray.size()) {
                curName = questionsArray.get(position).getName();
                curArray = questionsArray.get(position).getAnswers();
                curIscorrect = questionsArray.get(position).getIscorrect();
            }
            if (questionsArray.size() == 0){
                if (noQuestionsLayout.getVisibility() == View.GONE){
                    noQuestionsLayout.setVisibility(View.VISIBLE);
                    rootLayout.setVisibility(View.GONE);
                }else {
                    noQuestionsLayout.setVisibility(View.GONE);
                    rootLayout.setVisibility(View.VISIBLE);
                }
            }else{
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
        ranswer1.setText(ans1);
        ranswer2.setText(ans2);
        ranswer3.setText(ans3);
        map.put(ans1, is1);
        map.put(ans2, is2);
        map.put(ans3, is3);
    }

    private void setupDataToDB() {
        if (id != null){
            nameValuePairs.add(new BasicNameValuePair(Constants.CATEGORY_ID_POST_NAME, id));

        }else {
            nameValuePairs.add(new BasicNameValuePair(Constants.CATEGORY_ID_POST_NAME, "1"));
        }

    }

    private void checkDisplayLanguage(String locale){
        if (locale.equals(Constants.EN)){
            jsonMainNode = jsonResponse.optJSONArray(Constants.QUESTIONS_BY_CATEGORY_EN_ARRAY);
        }else if (locale.equals(Constants.GR)){
            jsonMainNode = jsonResponse.optJSONArray(Constants.QUESTIONS_BY_CATEGORY_ARRAY);
        }
    }

}
