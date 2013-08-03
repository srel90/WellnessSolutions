package com.diet.WellnessSolutions;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Window;
import android.view.WindowManager;

public class FirstScreenActivity extends Activity {
    private static final int SWAP_SCREEN = 1;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.firstscreen_activity);
        new Thread() {
            public void run() {
                try{
                    Thread.sleep(1000);
                    hRefresh.sendEmptyMessage(SWAP_SCREEN);
                }catch(Exception e){
                }
            }
        }.start();
    }
    Handler hRefresh = new Handler(){
        public void handleMessage(Message msg) {
            switch(msg.what){
                case SWAP_SCREEN:
                    Intent newActivity = new Intent(FirstScreenActivity.this,MainMenuActivity.class);
                    startActivity(newActivity);
                    finish();
                    break;
                default:
                    break;
            }
        }
    };
}
