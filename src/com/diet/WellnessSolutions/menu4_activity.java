package com.diet.WellnessSolutions;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;
import android.widget.Toast;

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
        AlertDialog.Builder adb = new AlertDialog.Builder(this);
        final AlertDialog ad = adb.create();
        kcal = (NumberPicker) findViewById(R.id.kcal);
        kcal.setMaxValue(9999);
        kcal.setMinValue(0);
        int getkcal=0;
        if(getParent().getIntent().getStringExtra("kcal")!=null) {
            getkcal=Integer.valueOf(getParent().getIntent().getStringExtra("kcal"));
        }
        kcal.setValue(getkcal);
        //Toast.makeText(menu4_activity.this,getParent().getIntent().getStringExtra("kcal"), Toast.LENGTH_SHORT).show();
        findViewById(R.id.btnrecord).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                long insertStatus=db.insertTransectionData(kcal.getValue());
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