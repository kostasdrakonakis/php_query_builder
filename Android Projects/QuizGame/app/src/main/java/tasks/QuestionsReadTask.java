package tasks;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.library.quizgame.R;

import java.util.List;

import lists.QuestionsList;

public class QuestionsReadTask extends AsyncTask<String, Void, List<QuestionsList>>{

    private ProgressDialog progressDialog;
    private Context context;

    public QuestionsReadTask(ProgressDialog progressDialog, Context context) {
        this.progressDialog = progressDialog;
        this.context = context;
    }

    @Override
    protected void onPreExecute() {
        super.onPreExecute();

        progressDialog.setMessage(String.valueOf(R.string.loading));
        progressDialog.show();
    }

    @Override
    protected List<QuestionsList> doInBackground(String... params) {

        return null;
    }

    @Override
    protected void onPostExecute(List<QuestionsList> questionsLists) {
        super.onPostExecute(questionsLists);
        if (progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }
}
