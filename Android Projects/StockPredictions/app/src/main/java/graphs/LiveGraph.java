package graphs;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.widget.Toolbar;

import com.kostas.stockpredictions.R;

import org.achartengine.GraphicalView;

import java.util.Random;


public class LiveGraph extends ActionBarActivity {

    private static GraphicalView view;
    private LiveGraphClass live = new LiveGraphClass();
    private static Thread thread;
    static String[] myPrices;
    double point;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_live_graph);
        Toolbar tb = (Toolbar) findViewById(R.id.toolBar);
        setSupportActionBar(tb);
        Intent i = this.getIntent();
        myPrices = i.getStringArrayExtra("stockprices");
        thread = new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < myPrices.length; i++) {
                    try {
                        Thread.sleep(800);
                    } catch (InterruptedException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                    Point p = new Point(i, Double.parseDouble(myPrices[i]));
                    live.addNewPoints(p);
                    view.repaint();
                }
            }
        };
        thread.start();
    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        view = live.getView(this);
        setContentView(view);
    }

    public static Point getDataFromReceiver(double x) {
        return new Point(x, getDouble());
    }

    private static double getDouble() {
        Random random = new Random();
        return random.nextDouble();
    }
}
