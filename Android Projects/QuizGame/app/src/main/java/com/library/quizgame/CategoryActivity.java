package com.library.quizgame;

import android.app.ProgressDialog;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;

import java.util.ArrayList;
import java.util.List;

import adapters.CategoriesAdapter;
import constants.Constants;
import constants.StringGenerator;
import lists.SingleCategories;
import tasks.CategoriesReadTask;

public class CategoryActivity extends AppCompatActivity {
    private Toolbar toolbar;
    private CategoriesAdapter adapter;
    private CategoriesReadTask task;
    private RecyclerView categoriesList;
    private GridLayoutManager gridLayoutManager;
    private List<SingleCategories> categories;
    private ProgressDialog pDialog;
    private String locale;
    private String langText;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        loadFromPrefs();
        StringGenerator.setLocale(langText, CategoryActivity.this);
        setContentView(R.layout.activity_category);
        //Αρχικοποιούμε τα widgets μας, στην περιπτωσή μας το RecyclerView και το LayoutManager που
        //θα χρησιμοποιείσει
        setupComponents();
        //Αρχικοποιούμε την toolbar για το menu
        setupToolbar();
        //Φτιάχνουμε το view μας. Δηλαδή τι θα βλέπει ο χρήστης
        setupCategoryView();
    }

    private void setupComponents() {
        categoriesList = (RecyclerView)findViewById(R.id.categoriesList);
        gridLayoutManager = new GridLayoutManager(CategoryActivity.this, 1);
        categoriesList.setLayoutManager(gridLayoutManager);
        categoriesList.setHasFixedSize(true);
    }

    private void setupCategoryView() {
        pDialog = new ProgressDialog(CategoryActivity.this);
        //Τρέχουμε το Asynctask με τις παραμέτρους και όταν κάνουμε execute του δίνουμε το URL που θέλουμε
        categories = new ArrayList<>();
        task = new CategoriesReadTask(pDialog, CategoryActivity.this, categories, adapter, categoriesList);
        if (langText.equals("en")){
            task.execute(Constants.CATEGORIES_EN_URL);
        }else if (langText.equals("el")){
            task.execute(Constants.CATEGORIES_URL);
        }

    }

    private void loadFromPrefs() {
        sharedPreferences = getSharedPreferences(Constants.PREFERENCES_FILE, MODE_PRIVATE);
        langText = sharedPreferences.getString(Constants.LANGUAGE_PREFS_FILE, getString(R.string.ta_to_select));
    }


    private void setupToolbar() {
        toolbar = (Toolbar)findViewById(R.id.toolBar);
        toolbar.setTitle(getString(R.string.categories));
        toolbar.setTitleTextColor(Color.WHITE);
        toolbar.setLogo(R.drawable.ic_list);
        setSupportActionBar(toolbar);
    }
}
