package matrix.skonakigr.kostas.skonakiaapp;

import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import functions.AppConstants;

public class TroposItem extends AppCompatActivity {

    private Toolbar toolbar;
    private String name, description, link;
    private TextView desc;
    private int imageId;
    private ImageView imageView;
    private boolean visible;
    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tropos_item);
        setupToolBar();
        setupData();
        setupParentNavigation();
    }

    private void setupParentNavigation() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN){
            if (getSupportActionBar()!=null){
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }

        }
    }

    private void setupData() {
        desc = (TextView)findViewById(R.id.troposItemDescription);
        description = getIntent().getStringExtra(AppConstants.INTENT_EXTRA_DESCRIPTION);
        desc.setText(description);
        imageId = getIntent().getIntExtra(AppConstants.INTENT_EXTRA_IMAGE, R.mipmap.launch_icon);
        imageView = (ImageView)findViewById(R.id.imageDescTropos);
        imageView.setImageResource(imageId);
        visible = getIntent().getBooleanExtra(AppConstants.INTENT_EXTRA_VISIBILITY, false);
        link = getIntent().getStringExtra(AppConstants.INTENT_EXTRA_LINK);
        button = (Button)findViewById(R.id.buttonWebView);
        checkVisibility();
        clickCallback();
    }

    private void clickCallback() {
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent linkIntent = new Intent(Intent.ACTION_VIEW);
                linkIntent.setData(Uri.parse(link));
                startActivity(linkIntent);
            }
        });
    }

    private void checkVisibility(){
        if (visible){
            if (button.getVisibility() == View.GONE){
                button.setVisibility(View.VISIBLE);
            }else {
                button.setVisibility(View.GONE);
            }
        }
    }

    private void setupToolBar() {
        toolbar = (Toolbar)findViewById(R.id.toolBar);
        name = getIntent().getStringExtra(AppConstants.INTENT_EXTRA_NAME);
        toolbar.setTitle(name);
        setSupportActionBar(toolbar);
    }


}
