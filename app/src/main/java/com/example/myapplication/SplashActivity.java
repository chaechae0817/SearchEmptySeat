package com.example.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.SystemClock;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        ActionBar actionBar = getSupportActionBar();
        actionBar.hide();
        ProgressBar pb1 = (ProgressBar) findViewById(R.id.progressBar);
        TextView sp_tv3 = (TextView)findViewById(R.id.sp_tv3);
       new Thread(){
            public void run(){

                for(int i =0; i<=60; i=i+2){
                    pb1.setProgress(pb1.getProgress()+4);
                    SystemClock.sleep(100);
                    runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            sp_tv3.setText(pb1.getProgress()+"%");
                        }
                    });
                }
                Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                startActivity(intent);
                overridePendingTransition(0, 0);
                finish();
            }
        }.start();
    }
}