package com.diet.WellnessSolutions;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.*;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Created with IntelliJ IDEA.
 * User: William
 * Date: 7/18/13
 * Time: 10:05 AM
 * To change this template use File | Settings | File Templates.
 */
public class menu4_activity extends Activity {
    NumberPicker kcal;
    Button btnrecord;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu4_activity);
        final SQLiteDB db = new SQLiteDB(this);
        db.createDataBase();
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        final AlertDialog ad = adb.create();
        kcal = (NumberPicker) findViewById(R.id.kcal);
        kcal.setMaxValue(9999);
        kcal.setMinValue(0);
        int getkcal=0;
        String getrecipy="";
        if(getParent().getIntent().getStringExtra("kcal")!=null) {
            getkcal=Integer.valueOf(getParent().getIntent().getStringExtra("kcal"));
        }
        if(getParent().getIntent().getStringExtra("recipe")!=null) {
            getrecipy= getParent().getIntent().getStringExtra("recipe");
        }
        kcal.setValue(getkcal);
        final TextView t1 = (TextView) findViewById(R.id.textView1);
        t1.setText(getrecipy);
        //Toast.makeText(menu4_activity.this,getrecipy, Toast.LENGTH_SHORT).show();

        final ListView listView = (ListView)findViewById(R.id.listView);
        String arrData[][] = null;
        arrData=db.listTransectionData();
        final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map;
        if(arrData!=null){
        for(int i = 0; i < arrData.length; i++){
            map = new HashMap<String, String>();
            map.put("ID",String.valueOf(i + 1));
            map.put("kcal",arrData[i][2].toString());
            map.put("datetime",arrData[i][0].toString());
            map.put("recipe",arrData[i][3].toString());
            MyArrList.add(map);
        }

        SimpleAdapter sAdap;
        sAdap = new SimpleAdapter(menu4_activity.this, MyArrList, R.layout.recipe_list_activity,
                new String[] {"ID","recipe"}, new int[] {R.id.ColOrder,R.id.ColName});
        listView.setAdapter(sAdap);
        }


        findViewById(R.id.btnrecord).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long insertStatus=db.insertTransectionData(kcal.getValue(), (String) t1.getText());
                if(insertStatus <=  0){
                    ad.setMessage("Error!! ");
                    ad.show();
                }
                Toast.makeText(menu4_activity.this, "เพิ่มข้อมูลเรียบร้อยแล้ว. ", Toast.LENGTH_SHORT).show();
                kcal.setValue(0);
            }
        });

    }
}