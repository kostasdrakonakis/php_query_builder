package graphs;


import android.content.Context;
import android.graphics.Color;

import org.achartengine.ChartFactory;
import org.achartengine.GraphicalView;
import org.achartengine.chart.PointStyle;
import org.achartengine.model.TimeSeries;
import org.achartengine.model.XYMultipleSeriesDataset;
import org.achartengine.renderer.XYMultipleSeriesRenderer;
import org.achartengine.renderer.XYSeriesRenderer;

public class LiveGraphClass {


    private GraphicalView view;
    private TimeSeries dataset = new TimeSeries("");
    private XYMultipleSeriesDataset mDataset = new XYMultipleSeriesDataset();
    private XYSeriesRenderer renderer = new XYSeriesRenderer();
    private XYMultipleSeriesRenderer mRenderer = new XYMultipleSeriesRenderer();

    public LiveGraphClass(){
        mDataset.addSeries(dataset);
        mRenderer.setAntialiasing(true);
        mRenderer.setFitLegend(true);
        mRenderer.setYAxisMax(150);
        mRenderer.setXAxisMax(15);
        mRenderer.setYAxisMin(0);
        mRenderer.setXAxisMin(0);
        mRenderer.addSeriesRenderer(renderer);
        renderer.setColor(Color.WHITE);
        renderer.setPointStyle(PointStyle.SQUARE);
        renderer.setFillPoints(true);
        renderer.setChartValuesTextSize(13);
        renderer.setShowLegendItem(true);
        mRenderer.setExternalZoomEnabled(true);
        mRenderer.setZoomButtonsVisible(true);
        mRenderer.setBackgroundColor(Color.BLACK);
        mRenderer.setApplyBackgroundColor(true);
        mRenderer.setZoomEnabled(true);
        mRenderer.setYTitle("Prices");
        mRenderer.setXTitle("Day Values");
    }

    public GraphicalView getView(Context context){

        view = ChartFactory.getLineChartView(context, mDataset, mRenderer);
        return view;
    }

    public void addNewPoints(Point p){
        dataset.add(p.getX(), p.getY());
    }
}
