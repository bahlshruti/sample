package com.example.shruti.myapplication;

import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.Locale;

public class MainActivity extends AppCompatActivity implements speechInterface{

    private static final String TAG="Main";
    private Button button;
    public void Switch()

    {
        Intent switchintent =new Intent(MainActivity.this,CommandActivity.class);
        Log.v(TAG, "starting activity");
        startActivity(switchintent);
    }

    public void startSpeechRecogniser() {
        if (speech==null) {

            speech = SpeechRecognizer.createSpeechRecognizer(getApplicationContext());
            speech.setRecognitionListener(new listener());
            recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
            recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
            recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName());
        }
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Intent speechIntent = new Intent(MainActivity.this, TTSService.class);
        speechIntent.putExtra("content","Welcome to aev !");
        speechIntent.putExtra("classFrom", "parent");
        Log.v(TAG, "starting service");
        startService(speechIntent);



        button=findViewById(R.id.button);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Switch();
            }
        });

    }





}
