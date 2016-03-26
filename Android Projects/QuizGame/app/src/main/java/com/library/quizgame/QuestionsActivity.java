package com.library.quizgame;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

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
import java.util.List;

import constants.Constants;
import constants.StringGenerator;
import lists.QuestionsList;

public class QuestionsActivity extends AppCompatActivity {

    private Toolbar toolbar;
    private String name, id;
    private String langText, ans1, ans2, ans3, questionName, answer1, answer2, answer3, iscorrect1, iscorrect2, iscorrect3, answer, iscorrect;
    private SharedPreferences sharedPreferences;
    private QuestionsReadTask questionsReadTask;
    private ProgressDialog pDialog;
    private List<QuestionsList> questionsLists;
    private TextView question;
    private RadioButton ranswer1, ranswer2, ranswer3;
    private int position, pos;
    private Button next;
    private List<String> question_iscorrect, question_answers, answersArray, array;
    private URL url;
    private HttpURLConnection httpURLConnection;
    private OutputStream outputStream;
    private InputStream inputStream;
    private BufferedWriter bufferedWriter;
    private StringBuilder jsonResult;
    private JSONObject jsonResponse, jsonChildNode, jsonSecondChildNode;
    private JSONArray jsonMainNode, jsonArray;
    private List<NameValuePair> nameValuePairs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFromPrefs();
        StringGenerator.setLocale(langText, QuestionsActivity.this);
        setContentView(R.layout.activity_questions);
        setupToolbar();
        initializeComponents();
        accessWebService();
    }

    private void initializeComponents() {
        question = (TextView)findViewById(R.id.questionText);
        ranswer1 = (RadioButton)findViewById(R.id.radioanswer1);
        ranswer2 = (RadioButton)findViewById(R.id.radioanswer2);
        ranswer3 = (RadioButton)findViewById(R.id.radioanswer3);
        next = (Button)findViewById(R.id.btnNext);
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                position++;
            }
        });
    }

    private void accessWebService() {
        questionsReadTask = new QuestionsReadTask();
        if (langText.equals("en")){
            questionsReadTask.execute(Constants.QUESTIONS_BY_CATEGORY_EN_URL);
        }else if (langText.equals("el")){
            questionsReadTask.execute(Constants.QUESTIONS_BY_CATEGORY_URL);
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
                checkDisplayLanguage(langText);
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
                        answer = jsonSecondChildNode.optString("answer" + (j+1));
                        iscorrect = jsonSecondChildNode.optString("iscorrect" + (j+1));
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
            if (lists.size() == 0){
                //StringGenerator.showToast(QuestionsActivity.this, "This Category does not have answers");
                setContentView(R.layout.noquestions);
                toolbar.setTitle(R.string.unavailable);
            }else{
                position = 0;
                question.setText(lists.get(position).getName());
                answersArray = lists.get(position).getAnswers();
                ans1 = answersArray.get(position);
                ans2 = answersArray.get(position+1);
                ans3 = answersArray.get(position+2);
                ranswer1.setText(ans1);
                ranswer2.setText(ans2);
                ranswer3.setText(ans3);
            }

        }

    }

    private void setupDataToDB() {
        if (id != null){
            nameValuePairs.add(new BasicNameValuePair(Constants.CATEGORY_ID_POST_NAME, id));

        }else {
            nameValuePairs.add(new BasicNameValuePair(Constants.CATEGORY_ID_POST_NAME, "1"));
        }

    }

    private void checkDisplayLanguage(String locale){
        if (locale.equals("en")){
            jsonMainNode = jsonResponse.optJSONArray(Constants.QUESTIONS_BY_CATEGORY_EN_ARRAY);
            Log.e("English Array: ", jsonMainNode.toString());
        }else if (locale.equals("el")){
            jsonMainNode = jsonResponse.optJSONArray(Constants.QUESTIONS_BY_CATEGORY_ARRAY);
            Log.e("Greek Array: ", jsonMainNode.toString());
        }
    }

}
