package graphs;

import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.kostas.stockpredictions.R;


public class EvaluationActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluation);
        Toolbar tb = (Toolbar)findViewById(R.id.toolBar);
        setSupportActionBar(tb);


    }


}
