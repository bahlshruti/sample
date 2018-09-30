package com.example.shruti.myapplication;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.speech.tts.SynthesisCallback;
import android.speech.tts.SynthesisRequest;
import android.speech.tts.TextToSpeech;
import android.speech.tts.TextToSpeechService;
import android.speech.tts.UtteranceProgressListener;
import android.util.Log;
import android.widget.Toast;

import java.util.HashMap;
import java.util.Locale;




public class TTSService extends TextToSpeechService  implements TextToSpeech.OnInitListener{

    private String str;
    private TextToSpeech tts;
    private static final String TAG="TTSService";
    private int status;
    String reference=null;
    speechInterface speech;


    public TTSService() {

    }

    @Override
    protected int onIsLanguageAvailable(String s, String s1, String s2) {
        return 0;
    }

    @Override
    protected String[] onGetLanguage() {
        return new String[0];
    }

    @Override
    protected int onLoadLanguage(String s, String s1, String s2) {
        return 0;
    }

    @Override
    protected void onStop() {
        Log.v(TAG, "onStop");
        if (tts != null) {
            tts.stop();
        }
    }

    @Override
    protected void onSynthesizeText(SynthesisRequest synthesisRequest, SynthesisCallback synthesisCallback) {

    }


    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }

    @Override
    public void onCreate() {

        Log.v(TAG, "oncreate_service");
        super.onCreate();

    }

    @Override
    public void onInit(int i) {

        Log.v(TAG, "oninit");
        if (status == TextToSpeech.SUCCESS) {
            int result = tts.setLanguage(Locale.CANADA);
            if (result == TextToSpeech.LANG_MISSING_DATA ||
                    result == TextToSpeech.LANG_NOT_SUPPORTED) {
                Log.v(TAG, "Language is not available.");
            } else {
                Log.v(TAG, "calling say function");
                say(str);
            }
        } else {
            Log.v(TAG, "Could not initialize TextToSpeech.");
        }
    }

    @Override
    public void onDestroy() {

        Log.v(TAG, "Destroy Service");
        // TODO Auto-generated method stub
        if (tts != null) {
            tts.stop();
            tts.shutdown();
        }
        super.onDestroy();
    }

    @Override
    public void onStart(Intent intent, int startId) {

        Log.v(TAG, "onstart_service");

        if (intent !=null && intent.getExtras()!=null){
             str = intent.getExtras().getString("content");
            reference=intent.getExtras().getString("classFrom");
        }
        super.onStart(intent, startId);
        tts = new TextToSpeech(this,this);  // OnInitListener
         tts.setSpeechRate(1.0f);
    }

    private void say(String str) {


            HashMap<String, String> map = new HashMap<String, String>();
            map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID");
            tts.speak(str, TextToSpeech.QUEUE_FLUSH, map);
            //Utteranceprogresslistener used to identify whenn TTS is completed...
            tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                @Override
                public void onStart(String s) {
                    //speakig started
                }

                @Override
                public void onDone(String s) {
                    //speaking stopped
                    Log.v(TAG, "about to listen");
                    //Log.v(TAG, "value of speech: " + speech);
                    if(reference.equals("parent"))
                        MainActivity
                }

                @Override
                public void onError(String s) {
                    // there was an error
                    Log.e("error","error:"+s);
                }

            });
    }
}


