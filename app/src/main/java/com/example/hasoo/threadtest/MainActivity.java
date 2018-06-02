package com.example.hasoo.threadtest;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    int count = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        @SuppressLint("HandlerLeak")
        final Handler mainHandler = new Handler(){
            @Override
            public void handleMessage(Message msg) {
                super.handleMessage(msg);
                String text = count + "초";
                TextView textView = findViewById(R.id.textView);
                textView.setText(text);
            }
        };

        Thread subThread = new Thread(){
            @Override
            public void run() {
                super.run();
                for(int i = 0; i<10; i++){
                    try {
                        Thread.sleep(1000);
                        count++;
                        mainHandler.sendEmptyMessage(0);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        };

        subThread.start();

        mainHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(getApplicationContext(), Main2Activity.class);
                startActivity(intent);
                finish();
            }
        }, 3000);
    }
}