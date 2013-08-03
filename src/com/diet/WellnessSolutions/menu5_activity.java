package com.diet.WellnessSolutions;

import android.app.Activity;
import android.graphics.Color;
import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.widget.LinearLayout;
import com.jjoe64.graphview.GraphView;
import com.jjoe64.graphview.GraphViewSeries;
import com.jjoe64.graphview.LineGraphView;


import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import static com.jjoe64.graphview.GraphView.*;

/**
 * Created with IntelliJ IDEA.
 * User: William
 * Date: 7/18/13
 * Time: 10:06 AM
 * To change this template use File | Settings | File Templates.
 */
public class menu5_activity extends Activity {
    private Config config;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu5_activity);
        final SQLiteDB db = new SQLiteDB(this);
        String arrData[][] = null;
        arrData=db.selectAllTransectionData();
        int num = arrData.length;
        if(num==0)return;
        final java.text.DateFormat dateTimeFormatter = DateFormat.getTimeFormat(this);
        final SimpleDateFormat df = new SimpleDateFormat("HH:mm:ss");
        GraphViewData[] data = new GraphViewData[num];
        GraphViewData[] standard = new GraphViewData[num];
        config=new Config();
        config.load();
        double standardvalue=0;
        if(config.get("BMR")!=null){
        standardvalue=Double.valueOf(config.get("BMR"));
        }
        for (int i=0; i<num; i++) {
            //Log.i("data", "x format:"+df.format(Long.valueOf(arrData[i][2])));
            //Log.i("data", "value y:"+Double.valueOf(arrData[i][1]));
            data[i] = new GraphViewData(i,Double.valueOf(arrData[i][1]));
            standard[i]= new GraphViewData(i,standardvalue);
        }
        GraphViewSeries data1 = new GraphViewSeries("Standard",new GraphViewSeries.GraphViewSeriesStyle(Color.rgb(0, 0, 255), 3), standard);
        GraphViewSeries data2 = new GraphViewSeries("Calorie",new GraphViewSeries.GraphViewSeriesStyle(Color.rgb(255, 0, 0), 3), data);
        GraphView graphView = new LineGraphView(this,"Calorie and Standard Graph");
        graphView.addSeries(data1); // data
        graphView.addSeries(data2); // data
        graphView.setViewPort(0, num);
        graphView.setScrollable(true);
        graphView.setScalable(true);

        graphView.setShowLegend(true);
        graphView.setLegendAlign(LegendAlign.BOTTOM);
        graphView.setLegendWidth(200);

        LinearLayout tab5 = (LinearLayout) findViewById(R.id.tab5);
        tab5.addView(graphView);
    }
}