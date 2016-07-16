package com.providers.restaurant.ordertakingsystem;

import android.content.Intent;
import android.graphics.PointF;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.util.FloatMath;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ImageView;

import adapters.ImageAdapter;

public class FullScreenViewActivity extends ActionBarActivity {
    PointF start = new PointF();
    PointF mid = new PointF();

    float oldDist = 1f;
    PointF oldDistPoint = new PointF();

    public static String TAG = "ZOOM";

    static final int NONE = 0;
    static final int DRAG = 1;
    static final int ZOOM = 2;
    int mode = NONE;

    private ImageView imageView;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fullscreen_view);

        // get intent data
        Intent i = getIntent();

        // Selected image id
        int position = i.getExtras().getInt("id");
        int[] myImages = i.getIntArrayExtra("images");
        ImageAdapter imageAdapter = new ImageAdapter(this, myImages);

        imageView = (ImageView) findViewById(R.id.full_image_view);
        imageView.setImageResource(imageAdapter.getmThumbIds()[position]);
        imageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Log.d(TAG, "mode=DRAG");
                switch (event.getAction() & MotionEvent.ACTION_MASK) {
                    case MotionEvent.ACTION_DOWN:
                        start.set(event.getX(), event.getY());
                        Log.d(TAG, "mode=DRAG");
                        mode = DRAG;

                        break;
                    case MotionEvent.ACTION_POINTER_DOWN:
                        oldDist = spacing(event);
                        oldDistPoint = spacingPoint(event);
                        Log.d(TAG, "oldDist=" + oldDist);
                        if (oldDist > 10f) {
                            midPoint(mid, event);
                            mode = ZOOM;
                            Log.d(TAG, "mode=ZOOM");
                        }
                        System.out.println("current time :" + System.currentTimeMillis());
                        break;// return !gestureDetector.onTouchEvent(event);
                    case MotionEvent.ACTION_UP:
                    case MotionEvent.ACTION_POINTER_UP:
                        Log.d(TAG, "mode=NONE");
                        mode = NONE;
                        break;
                    case MotionEvent.ACTION_MOVE:
                        if (mode == DRAG) {

                        } else if (mode == ZOOM) {
                            PointF newDist = spacingPoint(event);
                            float newD = spacing(event);
                            Log.e(TAG, "newDist=" + newDist);
                            float[] old = new float[9];
                            float[] newm = new float[9];
                            Log.e(TAG, "x=" + old[0] + ":&:" + old[2]);
                            Log.e(TAG, "y=" + old[4] + ":&:" + old[5]);
                            float scale = newD / oldDist;
                            float scalex = newDist.x / oldDistPoint.x;
                            float scaley = newDist.y / oldDistPoint.y;
                            zoom(scale, scale, start);
                        }
                        break;
                }
                return true;
            }
        });
    }

    public void zoom(Float scaleX, Float scaleY, PointF pivot) {
        imageView.setPivotX(pivot.x);
        imageView.setPivotY(pivot.y);
        imageView.setScaleX(scaleX);
        imageView.setScaleY(scaleY);
    }

    /**
     * space between the first two fingers
     */
    private float spacing(MotionEvent event) {
        // ...
        float x = event.getX(0) - event.getX(1);
        float y = event.getY(0) - event.getY(1);
        return FloatMath.sqrt(x * x + y * y);
    }

    private PointF spacingPoint(MotionEvent event) {
        PointF f = new PointF();
        f.x = event.getX(0) - event.getX(1);
        f.y = event.getY(0) - event.getY(1);
        return f;
    }

    /**
     * the mid point of the first two fingers
     */
    private void midPoint(PointF point, MotionEvent event) {
        // ...
        float x = event.getX(0) + event.getX(1);
        float y = event.getY(0) + event.getY(1);
        point.set(x / 2, y / 2);
    }
}
