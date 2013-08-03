package com.diet.WellnessSolutions;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.*;
import android.widget.*;


/**
 * Created with IntelliJ IDEA.
 * User: William
 * Date: 7/18/13
 * Time: 9:26 AM
 * To change this template use File | Settings | File Templates.
 */
public class MainMenuActivity extends TabActivity implements GestureDetector.OnGestureListener {
    private static final int SWIPE_MIN_DISTANCE = 120;
    private static final int SWIPE_MAX_OFF_PATH = 250;
    private static final int SWIPE_THRESHOLD_VELOCITY = 100;
    private static int currenttab;
    private Config config;
    GestureDetector gd;
    TabHost tabHost;
    TabHost.TabSpec spec;
    NumberPicker age, height, weight;
    RadioGroup rg;
    RadioButton sexMale,sexFemale;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.mainmenu_activity);
        config=new Config();
        config.load();

        gd = new GestureDetector(this, this);

        tabHost = getTabHost();

        spec = tabHost.newTabSpec("My Body")
                .setIndicator("My Body", getResources().getDrawable(R.drawable.users32))
                .setContent(R.id.tab1);
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("Calculate")
                .setIndicator("Calculate", getResources().getDrawable(R.drawable.gear32))
                .setContent(R.id.tab2);
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("Recipe");
        spec.setIndicator("Recipe", getResources().getDrawable(R.drawable.notecheck32));
        Intent m3intent = new Intent(this, menu3_activity.class);
        m3intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        spec.setContent(m3intent);
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("Diary");
        spec.setIndicator("Diary", getResources().getDrawable(R.drawable.notepencil32));
        Intent m4intent = new Intent(this, menu4_activity.class);
        m4intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        spec.setContent(m4intent);
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("Graph");
        spec.setIndicator("Graph", getResources().getDrawable(R.drawable.risegraph32));
        Intent m5intent = new Intent(this, menu5_activity.class);
        m5intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        spec.setContent(m5intent);
        tabHost.addTab(spec);

        spec = tabHost.newTabSpec("About")
                .setIndicator("About", getResources().getDrawable(R.drawable.star32))
                .setContent(R.id.tab6);
        tabHost.addTab(spec);
        rg = (RadioGroup) findViewById(R.id.radioSex);
        age = (NumberPicker) findViewById(R.id.age);
        height = (NumberPicker) findViewById(R.id.height);
        weight = (NumberPicker) findViewById(R.id.weight);
        sexMale=(RadioButton)findViewById(R.id.radioMale);
        sexFemale=(RadioButton)findViewById(R.id.radioFemale);

        age.setMaxValue(99);
        age.setMinValue(1);
        age.setWrapSelectorWheel(true);
        height.setMaxValue(250);
        height.setMinValue(1);
        height.setWrapSelectorWheel(true);
        weight.setMaxValue(250);
        weight.setMinValue(1);
        weight.setWrapSelectorWheel(true);
        if(config.get("sex")!=null && config.get("sex").equals("male")){
            sexMale.setChecked(true);
            sexFemale.setChecked(false);
        } else if(config.get("sex")!=null &&config.get("sex").equals("female")){
            sexMale.setChecked(false);
            sexFemale.setChecked(true);
        }
        if(config.get("age")!=null &&!config.get("age").equals("")){
            age.setValue(Integer.valueOf(config.get("age")));
        }
        if(config.get("height")!=null &&!config.get("height").equals("")){
            height.setValue(Integer.valueOf(config.get("height")));
        }
        if(config.get("weight")!=null &&!config.get("weight").equals("")){
            weight.setValue(Integer.valueOf(config.get("weight")));
        }
        getTabWidget().getChildAt(0).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currenttab = 0;
                tabHost.setCurrentTab(currenttab);
                //Toast.makeText(MainMenuActivity.this, ""+currenttab, Toast.LENGTH_SHORT).show();
            }
        });
        getTabWidget().getChildAt(1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currenttab = 1;
                tabHost.setCurrentTab(currenttab);
                calculateweight();
                //Toast.makeText(MainMenuActivity.this, ""+sex, Toast.LENGTH_SHORT).show();
            }
        });
        getTabWidget().getChildAt(2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currenttab = 2;
                tabHost.setCurrentTab(currenttab);
                //Toast.makeText(MainMenuActivity.this, ""+currenttab, Toast.LENGTH_SHORT).show();
            }
        });
        getTabWidget().getChildAt(3).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currenttab = 3;
                tabHost.setCurrentTab(currenttab);
                //Toast.makeText(MainMenuActivity.this, ""+currenttab, Toast.LENGTH_SHORT).show();
            }
        });
        getTabWidget().getChildAt(4).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currenttab = 4;
                tabHost.setCurrentTab(currenttab);
                //Toast.makeText(MainMenuActivity.this, ""+currenttab, Toast.LENGTH_SHORT).show();
            }
        });
        getTabWidget().getChildAt(5).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                currenttab = 5;
                tabHost.setCurrentTab(currenttab);
                //Toast.makeText(MainMenuActivity.this, ""+currenttab, Toast.LENGTH_SHORT).show();
            }
        });
        ((RadioButton) findViewById(R.id.radioMale)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                config.set("sex", "male");
                config.store();
            }
        });
        ((RadioButton) findViewById(R.id.radioFemale)).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                config.set("sex","female");
                config.store();
            }
        });

    }

    public void calculateweight() {

        TextView bmivalue = (TextView) findViewById(R.id.bmivalue);
        TextView bmiresult = (TextView) findViewById(R.id.bmiresult);
        TextView ws1 = (TextView) findViewById(R.id.ws1);
        TextView ws2 = (TextView) findViewById(R.id.ws2);
        int agevalue = Integer.valueOf(age.getValue());
        int heightvalue = Integer.valueOf(height.getValue());
        int weightvalue = Integer.valueOf(weight.getValue());


        double bmi = (double)weightvalue / Math.pow((double)heightvalue / 100, 2);
        String result = "";
        bmivalue.setText(String.format("คุณมีค่ามวลกาย %.2f", bmi));
        if (bmi < 16.0) {
            result = "ผอมมาก";
        } else if (bmi >= 16.0 && bmi <= 16.9) {
            result = "ผอมปานกลาง";
        } else if (bmi >= 17.0 && bmi <= 18.4) {
            result = "ผอมเล็กน้อย";
        } else if (bmi >= 18.5 && bmi <= 22.9) {
            result = "ปกติ";
        } else if (bmi >= 23.0 && bmi <= 24.9) {
            result = "อ้วนเล็กน้อย";
        } else if (bmi >= 25.0 && bmi <= 29.9) {
            result = "อ้วนปานกลาง";
        } else if (bmi > 30) {
            result = "อ้วนมาก";
        }
        bmiresult.setText(String.format("คุณมีรูปร่าง %s", result));

        String sex = ((RadioButton) findViewById(rg.getCheckedRadioButtonId())).getText().toString();
        double ws1value = 0, ws2value = 0,bmr=0;
        if (sex.equals("ชาย")) {
            ws1value = ((heightvalue - 150) * 0.7) + 50;
            ws2value = ((heightvalue - 100) * 0.9);
            bmr=66+(13.7*(double)weightvalue)+(5*(double)heightvalue)-(6.8*agevalue);
        } else {
            ws1value = ((heightvalue - 150) * 0.7) + 45;
            ws2value = ((heightvalue - 100) * 0.8);
            bmr=66+(9.6*(double)weightvalue)+(1.8*(double)heightvalue)-(4.7*agevalue);
        }
        ws1.setText(String.format("น้ำหนักมาตรฐานควรอยู่ระหว่าง %.2f ถึง %.2f", ws1value, ws2value));
        ws2.setText(String.format("การเผาผลาญพลังงาน Basal Metabolic Rate (BMR) %.2f", bmr));
        config.set("age",String.valueOf(agevalue));
        config.set("height",String.valueOf(heightvalue));
        config.set("weight",String.valueOf(weightvalue));
        config.set("BMR",String.valueOf(bmr));
        config.store();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (gd.onTouchEvent(event))
            return true;
        else
            return false;
    }

    @Override
    public boolean onDown(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent motionEvent) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent motionEvent) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent motionEvent, MotionEvent motionEvent2, float v, float v2) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent motionEvent) {
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        try {
            if (Math.abs(e1.getY() - e2.getY()) > SWIPE_MAX_OFF_PATH)
                return false;
            // right to left swipe
            if (e1.getX() - e2.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                if (currenttab < 5) {
                    currenttab++;
                    if(currenttab==1)calculateweight();
                    tabHost.setCurrentTab(currenttab);
                }
                //Toast.makeText(MainMenuActivity.this, "<---- Left Swipe", Toast.LENGTH_SHORT).show();

            } else if (e2.getX() - e1.getX() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {
                if (currenttab > 0) {
                    currenttab--;
                    if(currenttab==1)calculateweight();
                    tabHost.setCurrentTab(currenttab);
                }

                //Toast.makeText(MainMenuActivity.this, "----> Right Swipe", Toast.LENGTH_SHORT).show();

            } else if (e1.getY() - e2.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

                //Toast.makeText(MainMenuActivity.this, "Swipe up", Toast.LENGTH_SHORT).show();

            } else if (e2.getY() - e1.getY() > SWIPE_MIN_DISTANCE && Math.abs(velocityX) > SWIPE_THRESHOLD_VELOCITY) {

                //Toast.makeText(MainMenuActivity.this, "Swipe down", Toast.LENGTH_SHORT).show();

            }
        } catch (Exception e) {
            // nothing
        }

        return true;
    }
}