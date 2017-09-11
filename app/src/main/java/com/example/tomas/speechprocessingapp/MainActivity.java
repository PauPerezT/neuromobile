package com.example.tomas.speechprocessingapp;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.hardware.SensorManager;
import android.os.Environment;
import android.os.SystemClock;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.Chronometer;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.tomas.speechprocessingapp.SpeechProcessing.SpeechRec;
import com.example.tomas.speechprocessingapp.TremorProcessing.AccGraph;
import com.example.tomas.speechprocessingapp.TremorProcessing.ActivateAcc;

import java.io.File;

import static android.provider.AlarmClock.EXTRA_MESSAGE;

public class MainActivity extends AppCompatActivity{
    private boolean recflag = false;
    private boolean gaitflag = false;
    String pathData = null;
    private Chronometer rectimer= null;
    private EditText ID_text=null;
    private int facc=1,fspee = 1;
    private SensorManager mSensorManager;
    public ActivateAcc acc;
    public SpeechRec spe;
    TextView tasktext = null;

    //private int time = (int) System.currentTimeMillis();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Create app folders to store data
        pathData = Environment.getExternalStorageDirectory() + File.separator + "AppSpeechData";
        File datafolder = new File(pathData);
        boolean checkF = datafolder.exists();
        //if (checkF == false) {
            datafolder.mkdirs();
            datafolder = new File(pathData + File.separator + "WAV");
            datafolder.mkdirs();
            datafolder = new File(pathData + File.separator + "UBM");
            datafolder.mkdirs();
            datafolder = new File(pathData + File.separator + "ACC");//Folder to save data form acceler
            datafolder.mkdirs();
            datafolder = new File(pathData + File.separator + "TAP");//Folder to save data from fingertapping
            datafolder.mkdirs();
        //}


        // Get an instance of the SensorManager. ACCELEROMETER
        mSensorManager = (SensorManager) getSystemService(Context.SENSOR_SERVICE);
        //acc = new SetAcc(this);
        acc =  new ActivateAcc(this,mSensorManager);
        ID_text  = (EditText)findViewById(R.id.main_subjectID);

        //Chronometer
        rectimer = (Chronometer) findViewById(R.id.chrono);
        //tasktext = (TextView)findViewById(R.id.Timer_text);

        Button bt_listrec = (Button) findViewById(R.id.main_listrec);
        bt_listrec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, RecordingsList.class);
                intent.putExtra(EXTRA_MESSAGE, pathData+File.separator+"WAV");
                startActivity(intent);
            }
        });

        Button bt_speechrec = (Button) findViewById(R.id.main_speechrec);
        bt_speechrec.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Startrec();
            }
        });

        Button bt_TremorL = (Button) findViewById(R.id.main_tremorL);
        bt_TremorL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tremor(ID_text.getText().toString(),"Left");

            }
        });

        Button bt_TremorR = (Button) findViewById(R.id.main_tremorR);
        bt_TremorR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Tremor(ID_text.getText().toString(),"Right");
            }
        });

        Button bt_restTremorL = (Button) findViewById(R.id.main_resttremorL);
        bt_restTremorL.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restTremor(ID_text.getText().toString(),"Left");
            }
        });

        Button bt_restTremorR = (Button) findViewById(R.id.main_resttremorR);
        bt_restTremorR.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                restTremor(ID_text.getText().toString(),"Right");
            }
        });

        Button bt_kintremorL = (Button) findViewById(R.id.main_kintremorL);
        bt_kintremorL.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void  onClick(View view){
                KinTremor(ID_text.getText().toString(),"Left");
            }
        });

        Button bt_kintremorR = (Button) findViewById(R.id.main_kintremorR);
        bt_kintremorR.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void  onClick(View view){
                KinTremor(ID_text.getText().toString(),"Right");
            }
        });

        Button bt_pronL = (Button) findViewById(R.id.main_pronL);
        bt_pronL.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void  onClick(View view){
                Prona(ID_text.getText().toString(),"Left");
            }
        });

        Button bt_pronR = (Button) findViewById(R.id.main_pronR);
        bt_pronR.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void  onClick(View view){
                Prona(ID_text.getText().toString(),"Right");
            }
        });

        Button bt_tapL = (Button) findViewById(R.id.main_tapL);
        bt_tapL.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void  onClick(View view){
                FinTap(ID_text.getText().toString(),"Left");
            }
        });

        Button bt_tapR = (Button) findViewById(R.id.main_tapR);
        bt_tapR.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void  onClick(View view){
                FinTap(ID_text.getText().toString(),"Right");
            }
        });

        Button bt_kin2L = (Button) findViewById(R.id.main_kintremor2L);
        bt_kin2L.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void  onClick(View view){
                KinTremor2(ID_text.getText().toString(),"Left");
            }
        });

        Button bt_kin2R = (Button) findViewById(R.id.main_kintremor2R);
        bt_kin2R.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void  onClick(View view){
                KinTremor2(ID_text.getText().toString(),"Right");
            }
        });

        Button bt_gait = (Button) findViewById(R.id.main_gait);
        bt_gait.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void  onClick(View view){
               Gait(ID_text.getText().toString());
            }
        });
        Button bt_rigL = (Button) findViewById(R.id.main_rigidityL);
        bt_rigL.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void  onClick(View view){
                Rigidity(ID_text.getText().toString(),"Left");
            }
        });
        Button bt_rigR = (Button) findViewById(R.id.main_rigidityR);
        bt_rigR.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void  onClick(View view){
                Rigidity(ID_text.getText().toString(),"Right");
            }
        });
    }


    private void Tremor(String userID, String side)
    {
        Intent intent = new Intent(this,AccGraph.class);
        intent.putExtra("Path", pathData + File.separator + "ACC");
        intent.putExtra("ID",userID);
        intent.putExtra("Task","PosturalTremor"+side);
        startActivity(intent);
        /*
        if( !userID.equals("") ) userID+="_";

        Intent intent = new Intent(this,Timer.class);
        intent.putExtra(EXTRA_MESSAGE, pathData+File.separator+"ACC");
        intent.putExtra("Button", userID+"Tremor"+side);
        startActivity(intent);
        */
    }

    private void restTremor(String userID, String side)
    {
        Intent intent = new Intent(this,AccGraph.class);
        intent.putExtra("Path", pathData + File.separator + "ACC");
        intent.putExtra("ID",userID);
        intent.putExtra("Task","RestTremor"+side);
        startActivity(intent);
        /*
        if( !userID.equals("") ) userID+="_";

        Intent intent = new Intent(this,Timer.class);
        intent.putExtra(EXTRA_MESSAGE, pathData+File.separator+"ACC");
        intent.putExtra("Button", userID+"RestTremor"+side);
        startActivity(intent);
        */
    }

    private void KinTremor(String userID, String side)
    {
        Intent intent = new Intent(this,AccGraph.class);
        intent.putExtra("Path", pathData + File.separator + "ACC");
        intent.putExtra("ID",userID);
        intent.putExtra("Task","KinTremor"+side);
        startActivity(intent);
        /*
        if( !userID.equals("") ) userID+="_";
        //tasktext.setText("Kinetic Left");
        Intent intent = new Intent(this,Timer.class);
        intent.putExtra(EXTRA_MESSAGE, pathData+File.separator+"ACC");
        intent.putExtra("Button", userID+"KinTremor"+side);
        startActivity(intent);
        */
    }

   private void Prona(String userID, String side)
    {
        Intent intent = new Intent(this,AccGraph.class);
        intent.putExtra("Path", pathData + File.separator + "ACC");
        intent.putExtra("ID",userID);
        intent.putExtra("Task","PronSupi"+side);
        startActivity(intent);
        /*
        if( !userID.equals("") ) userID+="_";
        //tasktext.setText("Pronation Left");
        Intent intent = new Intent(this,Timer.class);
        intent.putExtra(EXTRA_MESSAGE, pathData+File.separator+"ACC");
        intent.putExtra("Button", userID+"PronSupi"+side);
        startActivity(intent);
        */
    }

    private void KinTremor2(String userID, String side)
    {
        Intent intent = new Intent(this,AccGraph.class);
        intent.putExtra("Path", pathData + File.separator + "ACC");
        intent.putExtra("ID",userID);
        intent.putExtra("Task","KinTremor2"+side);
        startActivity(intent);
        /*
        if( !userID.equals("") ) userID+="_";
        //tasktext.setText("Rigidity Left");
        Intent intent = new Intent(this,Timer.class);
        intent.putExtra(EXTRA_MESSAGE, pathData+File.separator+"ACC");
        intent.putExtra("Button", userID+"KinTremor2"+side);
        startActivity(intent);*/
    }


    private  void FinTap(String userID, String side)
    {
        if( !userID.equals("") ) userID+="_";
        Intent intent = new Intent(this, FingerTapping.class);
        intent.putExtra(EXTRA_MESSAGE, pathData + File.separator + "TAP");
        intent.putExtra("Button", userID+"FingerTap"+side);
        startActivity(intent);
    }
    private  void Rigidity(String userID, String side)
    {
        Intent intent = new Intent(this,AccGraph.class);
        intent.putExtra("Path", pathData + File.separator + "ACC");
        intent.putExtra("ID",userID);
        intent.putExtra("Task","Rigidity"+side);
        startActivity(intent);
        /*
        if( !userID.equals("") ) userID+="_";
        Intent intent = new Intent(this, Timer.class);
        intent.putExtra(EXTRA_MESSAGE, pathData + File.separator + "ACC");
        intent.putExtra("Button", userID+"Rigidity"+side);
        startActivity(intent);
        */
    }

    private void Gait(String userID){
        Intent intent = new Intent(this,AccGraph.class);
        intent.putExtra("Path", pathData + File.separator + "ACC");
        intent.putExtra("ID",userID);
        intent.putExtra("Task","Gait");
        startActivity(intent);
    }
/*
    public void onCheckboxClicked(View view) {
        // Is the view now checked?
        boolean checked = ((CheckBox) view).isChecked();

        // Check which checkbox was clicked
        switch(view.getId()) {
            case R.id.checkbox_motion:
                if (checked==true) facc = 1;
                else facc=0;
                break;
            case R.id.checkbox_speech:
                if (checked==true) fspee = 1;
                else fspee = 0;
                break;
        }
    }
*/
    //Start recording when the "Record" button is clicked
    private void Startrec() {
        //Request permissions to record and audio files
        int record_perm = RequestPermissions();
        Button pb = (Button) findViewById(R.id.main_speechrec);
        rectimer.setBase(SystemClock.elapsedRealtime());
        if (record_perm == PackageManager.PERMISSION_GRANTED) {

            recflag = !recflag;
            if (recflag) {
                pb.setText("Stop");
                rectimer.start();
                //Microphone
                //if (fspee==1) {
                  //  setMicrophone();
                    //startSpeechRecording();
                    spe = new SpeechRec(this,pathData);
                    spe.start();
                //}
                //Accelerometer
                //if (facc==1) {
                    acc.startAcc(pathData+File.separator+"ACC","ACC_Speech");
                //}

            } else {
                pb.setText("Start");
                rectimer.stop();
                rectimer.setBase(SystemClock.elapsedRealtime());
                //if (fspee==1) {
                    //stopSpeechRecording();//Microphone

                   spe.stopSpeechRecording();
                //}
                //if (facc==1) {
                    acc.stopAcc();
                //}
            }
        } else {
            Toast.makeText(this, "You need to grant permission to record and store audio files", Toast.LENGTH_LONG).show();
        }
    }

    //Request for permission Android >= 6
    private static final int REQUEST_PERMISSIONS = 0;
    private int RequestPermissions() {
        //----------------------------------------------------------------
        //Request permission to RECORD AUDIO and STORE DATA on the phone
        //----------------------------------------------------------------
        //The app had permission to record audio?
        int audio_perm = ContextCompat.checkSelfPermission(this, Manifest.permission.RECORD_AUDIO);
        //int storage_per = ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE);
        if (audio_perm != PackageManager.PERMISSION_GRANTED) {
            //If there is not permission to record and store audio files, then ask to the user for it
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE,
                            Manifest.permission.MODIFY_AUDIO_SETTINGS}, REQUEST_PERMISSIONS);
        }
        return audio_perm;
    }//END REQUEST PERMISSION

}
