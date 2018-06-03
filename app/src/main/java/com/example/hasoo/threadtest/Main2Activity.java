package com.example.hasoo.threadtest;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

public class Main2Activity extends AppCompatActivity {

    MyTask myTask = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);
        setTitle("AsyncTask Test");

        final int value = 0;
        final TextView textView = findViewById(R.id.textView2);
        final ProgressBar progressBar = findViewById(R.id.progressBar);

        Button button = findViewById(R.id.button);
        Button button2= findViewById(R.id.button2);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myTask = new MyTask(value, textView, progressBar);
                myTask.execute(100);
            }
        });

        button2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                myTask.cancel(true);
            }
        });
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        myTask.cancel(true);
    }

    @SuppressLint("StaticFieldLeak")
    class MyTask extends AsyncTask<Integer, Integer, Integer>{

        int value;
        TextView textView;
        ProgressBar progressBar;

        MyTask(int value, TextView textView, ProgressBar progressBar){
            this.value = value;
            this.textView = textView;
            this.progressBar = progressBar;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            value = 0;
            progressBar.setProgress(value);
        }

        @Override
        protected Integer doInBackground(Integer... integers) {

            while(!isCancelled()){
                value++;
                if(value >= 100) break;
                else publishProgress(value);

                try{
                    Thread.sleep(100);
                }catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return value;
        }

        @Override
        protected void onProgressUpdate(Integer... values) {
            super.onProgressUpdate(values);
            progressBar.setProgress(values[0]);
            String progress = "진행률: " + values[0].toString() + "%";
            textView.setTextColor(Color.RED);
            textView.setText(progress);
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            progressBar.setProgress(100);
            textView.setTextColor(Color.GRAY);
            textView.setText("완료되었습니다.");
        }

        @Override
        protected void onCancelled() {
            super.onCancelled();
            textView.setTextColor(Color.BLUE);
            textView.setText("취소되었습니다.");
        }
    }
}


