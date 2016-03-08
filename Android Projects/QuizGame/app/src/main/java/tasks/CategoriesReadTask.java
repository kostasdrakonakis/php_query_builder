package tasks;


import android.app.ProgressDialog;
import android.content.Context;
import android.os.AsyncTask;

import com.library.quizgame.R;

import java.util.List;

import lists.CategoryList;

public class CategoriesReadTask extends AsyncTask<String, Void, List<CategoryList>>{

    private ProgressDialog progressDialog;
    private Context context;

    public CategoriesReadTask(ProgressDialog progressDialog, Context context) {
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
    protected List<CategoryList> doInBackground(String... params) {
        return null;
    }

    @Override
    protected void onPostExecute(List<CategoryList> categoryLists) {
        super.onPostExecute(categoryLists);
        if (progressDialog.isShowing()){
            progressDialog.dismiss();
        }
    }
}
