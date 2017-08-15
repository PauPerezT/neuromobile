package com.example.tomas.speechprocessingapp;

import android.content.Context;
import android.content.Intent;
import android.os.Vibrator;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.os.Vibrator;
import android.view.View;
import android.widget.Button;

public class FingerTapping extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finger_tapping);
        Intent intent = getIntent();

        Button tapme = (Button) findViewById(R.id.fingertap_FinTap);

        tapme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Vibrator vib = (Vibrator) getSystemService(Context.VIBRATOR_SERVICE);
                // Vibrate for 500 milliseconds
                vib.vibrate(100);
            }
        });


    }
}
