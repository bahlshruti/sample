package com.example.shruti.myapplication;

import android.Manifest;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.speech.RecognitionService;
import android.speech.RecognizerIntent;
import android.speech.SpeechRecognizer;
import android.speech.tts.TextToSpeech;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Locale;
import android.speech.RecognitionListener;

/**
public class SpeechRecognizerService extends RecognitionService{


    private static final int REQUEST_RECORD_PERMISSION = 100;
    private static final String TAG = "Speech";
    private Context context;
    private SpeechRecognizer speech = null;
    private Intent recognizerIntent;


    public SpeechRecognizerService() {
        super();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    protected void onStartListening(Intent intent, Callback callback) {
        Log.i(TAG, "listening");
        speech.startListening(recognizerIntent);
    }

    @Override
    protected void onCancel(Callback callback) {

    }

    @Override
    protected void onStopListening(Callback callback) {

    }

    public void onCreate() {

        Log.i(TAG, "starting recognition listener");
        if (speech == null) {

            speech = SpeechRecognizer.createSpeechRecognizer(this);
            speech.setRecognitionListener(new listener());
            recognizerIntent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL, RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            recognizerIntent.putExtra(RecognizerIntent.EXTRA_MAX_RESULTS, 1);
            recognizerIntent.putExtra(RecognizerIntent.EXTRA_LANGUAGE, Locale.ENGLISH);
            recognizerIntent.putExtra(RecognizerIntent.EXTRA_CALLING_PACKAGE, this.getPackageName());
        }
    }

    protected class listener implements RecognitionListener {

        @Override
        public void onReadyForSpeech(Bundle bundle) {
            Log.i("", "onreadyforspeech ");
        }

        @Override
        public void onBeginningOfSpeech() {
            Log.i("", "onbegofspeech ");
        }

        @Override
        public void onRmsChanged(float v) {

        }

        @Override
        public void onBufferReceived(byte[] bytes) {

        }

        @Override
        public void onEndOfSpeech() {
            Log.i("", "onendofspeech ");
        }

        @Override
        public void onError(int i) {
            String errorMessage = getErrorText(i);
            Toast.makeText(getApplicationContext(), errorMessage, Toast
                    .LENGTH_SHORT).show();
        }

        @Override
        public void onResults(Bundle results) {

            ArrayList<String> Result = results.getStringArrayList(SpeechRecognizer.RESULTS_RECOGNITION);
            Log.i(TAG, "onResults: " + Result);

        }

        @Override
        public void onPartialResults(Bundle bundle) {

        }

        @Override
        public void onEvent(int i, Bundle bundle) {

        }


        public String getErrorText(int errorCode) {
            String message;
            switch (errorCode) {
                case SpeechRecognizer.ERROR_AUDIO:
                    message = "Audio recording error";
                    break;
                case SpeechRecognizer.ERROR_CLIENT:
                    message = "Client side error";
                    break;
                case SpeechRecognizer.ERROR_INSUFFICIENT_PERMISSIONS:
                    message = "Insufficient permissions";
                    break;
                case SpeechRecognizer.ERROR_NETWORK:
                    message = "Network error";
                    break;
                case SpeechRecognizer.ERROR_NETWORK_TIMEOUT:
                    message = "Network timeout";
                    break;
                case SpeechRecognizer.ERROR_NO_MATCH:
                    //No recognition result matched
                    message = "No match";
                    break;
                case SpeechRecognizer.ERROR_RECOGNIZER_BUSY:
                    message = "RecognitionService busy";
                    break;
                case SpeechRecognizer.ERROR_SERVER:
                    message = "error from server";
                    break;
                case SpeechRecognizer.ERROR_SPEECH_TIMEOUT:
                    message = "No speech input";
                    break;
                default:
                    message = "Didn't understand, please try again.";
                    break;
            }
            return message;
        }
    }
}
 **/

public interface speechInterface{
        void startSpeechRecogniser();


}





