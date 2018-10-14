package com.example.shruti.myapplication;

import android.content.ActivityNotFoundException;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.speech.RecognizerIntent;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;

public class ModeActivity extends AppCompatActivity implements ServiceCallbacks {

    private TTSService TTS;
    private boolean bound = false;
    private static final String TAG = "Mode";
    private final int REQ_CODE_SPEECH_INPUT = 100;
    boolean flag = false;
    String response = "yes";
    Intent speechIntent;
    Intent intent;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mode);
        Log.i(TAG, "onCreate called");
    }

    @Override
    protected void onStart() {
        Log.i(TAG, "onStart");
        super.onStart();
        speechIntent = new Intent(ModeActivity.this, TTSService.class);
        speechIntent.putExtra("content_to_speak", "welcome to Mode section ! Which mode you would prefer");
        bindService(speechIntent, serviceConnection, Context.BIND_AUTO_CREATE);
        startService(speechIntent);
    }

    @Override
    protected void onStop() {
        Log.i(TAG, "Stop activity");
        super.onStop();
        if(bound) {
            unbindService(serviceConnection);
            bound = false;
        }
    }

    @Override
    protected void onDestroy() {

        super.onDestroy();

        //stopIntent=new Intent(MainActivity.this,TTSService.class);
        //stopService(stopIntent);

        Log.i(TAG, "Destroy Activity");
        if(TTS !=null || speechIntent !=null)
            TTS=null;
        speechIntent=null;


    }

    /**
     * Callbacks for service binding, passed to bindService()
     */
    private ServiceConnection serviceConnection = new ServiceConnection() {

        @Override
        public void onServiceConnected(ComponentName className, IBinder service) {
            // cast the IBinder and get MyService instance
            TTSService.LocalBinder binder = (TTSService.LocalBinder) service;
            TTS = binder.getService();
            bound = true;
            TTS.setCallbacks(ModeActivity.this); // register
        }

        @Override
        public void onServiceDisconnected(ComponentName arg0) {
            bound = false;
        }
    };

    /* Defined by ServiceCallbacks interface */
    @Override
    public void doSomething() {
        promptSpeechInput();
    }

    private void promptSpeechInput() {
        Log.i(TAG, "start speech recogniser... ");

        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH); //Simply takes user’s speech input and returns it to same activity
        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);

        intent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);

        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.getDefault());

        try {
            this.startActivityForResult(intent,REQ_CODE_SPEECH_INPUT);
        } catch (ActivityNotFoundException a) {
            Toast.makeText(ModeActivity.this,
                    " exception",
                    Toast.LENGTH_SHORT).show();
        }
    }

    /**
     * Receiving speech input
     */

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        switch (requestCode) {
            case REQ_CODE_SPEECH_INPUT: {
                if (resultCode == RESULT_OK && null != data) {

                    ArrayList<String> Result = data
                            .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);

                    Toast.makeText(ModeActivity.this,
                            "result: " + Result,
                            Toast.LENGTH_SHORT).show();

                    if (Result.get(0).equals("1") || Result.get(0).equals("2") || Result.get(0).equals("3")) {

                        if (!flag) {
                            response = Result.get(0);
                            confirmation(Result);
                        }
                    } else if (Result.get(0).equals("yes") && response.equals("1")) {
                        intent = new Intent(ModeActivity.this, UserMode.class);
                        startActivity(intent);
                    } else if (Result.get(0).equals("yes") && response.equals("2")) {
                        intent = new Intent(ModeActivity.this, AutoPilotMode.class);
                        startActivity(intent);
                    } else if (Result.get(0).equals("yes") && response.equals("3")) {
                        intent = new Intent(ModeActivity.this, AutoMode.class);
                        startActivity(intent);
                    } else {
                        flag = false;
                        speechIntent.putExtra("content_to_speak", " please try again?");
                        startService(speechIntent);
                    }
                }
            }
        }
    }

    public void confirmation(ArrayList<String> Result) {
        flag = true;
        Log.i(TAG, "Result: " + Result);
        speechIntent.putExtra("content_to_speak", "did you say" + Result);
        startService(speechIntent);

    }

}