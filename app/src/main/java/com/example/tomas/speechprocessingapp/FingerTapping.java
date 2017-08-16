package com.example.tomas.speechprocessingapp;

import android.content.Context;
import android.content.Intent;
import android.os.SystemClock;
import android.os.Vibrator;
import android.provider.AlarmClock;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.text.SimpleDateFormat;
import java.util.Date;

public class FingerTapping extends AppCompatActivity {
    private int timef = 0;
    private OutputStreamWriter fout;
    private String TAPpath = "";
    TextView timer=null;
    private String format;
    private File f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_tapping);
        Intent intent = getIntent();


        //Save folder
        TAPpath = intent.getStringExtra(AlarmClock.EXTRA_MESSAGE);
        String fname = intent.getStringExtra("Button");

        Date date = new Date();

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("ddMMyyyyhhmmss");
        //String format = simpleDateFormat.format(date);
        format = simpleDateFormat.format(date);
        f = new File(TAPpath, fname+format + ".txt");
        try {
            fout = new OutputStreamWriter(new FileOutputStream(f));
        } catch (Exception e) {
            e.printStackTrace();
        }

         timer = (TextView)findViewById(R.id.fingertap_timer);

        new CountDownTimer(11000, 1000) {
            public void onTick(long millisUntilFinished) {
                timer.setText(String.valueOf((millisUntilFinished / 1000)-1));
                if (String.valueOf((millisUntilFinished / 1000)-1)=="0")
                {
                    timer.setText("Stop");
                }
            }
            public void onFinish() {
                try {
                    fout.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                SystemClock.sleep(500);
                finish();//close current activity
                //timer.setText("0");;
            }
        }.start();

        timef = (int) SystemClock.currentThreadTimeMillis();
        Button tapme = (Button) findViewById(R.id.fingertap_FinTap);

        tapme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                // Vibrate for 500 milliseconds
                vib.vibrate(100);
                int time2 = (int) SystemClock.currentThreadTimeMillis()-timef;
                String timeStr = String.valueOf(time2);
                generateNoteOnSD( timeStr  + "\n\r");
                        }
        });



    }


public void generateNoteOnSD(String data_sensors){

        try {
            fout.write(data_sensors);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
