package com.diet.WellnessSolutions;

import android.app.Activity;
import android.app.TabActivity;
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
public class menu3_activity extends Activity {
    final SQLiteDB db = new SQLiteDB(this);
    String arrData[] = null;
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.menu3_activity);

        db.createDataBase();
        arrData=db.selectAllRecipeData();
        final AutoCompleteTextView autoCom = (AutoCompleteTextView)findViewById(R.id.autoCompleteTextView1);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_dropdown_item_1line, arrData);
        autoCom.setAdapter(adapter);
        findViewById(R.id.btnsearch).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                search(autoCom.getText().toString());
            }
        });

    }
    private void search(String search) {
        final ListView listView = (ListView)findViewById(R.id.listView);
        String arrData[][] = null;
        arrData=db.searchRecipeData(search);
        final ArrayList<HashMap<String, String>> MyArrList = new ArrayList<HashMap<String, String>>();
        HashMap<String, String> map;
        for(int i = 0; i < arrData.length; i++){
            map = new HashMap<String, String>();
            map.put("ID",String.valueOf(i + 1));
            map.put("recipe",arrData[i][1].toString());
            map.put("kcal",arrData[i][2].toString());
            MyArrList.add(map);
        }
        SimpleAdapter sAdap;
        sAdap = new SimpleAdapter(menu3_activity.this, MyArrList, R.layout.recipe_list_activity,
                new String[] {"ID","recipe"}, new int[] {R.id.ColOrder,R.id.ColName});
        listView.setAdapter(sAdap);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> myAdapter, View myView,int position, long mylng) {
                String kcal = MyArrList.get(position).get("kcal");
                getParent().getIntent().putExtra("kcal", kcal);
                TabActivity ta = (TabActivity) menu3_activity.this.getParent();
                ta.getTabHost().setCurrentTab(3);

            }
        });
    }
}