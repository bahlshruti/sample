package com.example.shruti.myapplication;

import android.app.Service;
import android.content.Context;
import android.content.Intent;
import android.os.IBinder;
import android.os.Binder;
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

    private String str,str1;
    private TextToSpeech tts;
    private static final String TAG="TTSService";
    private int status;
    String reference=null;

    private Object ref;
    // Binder given to clients
    private final IBinder binder = new LocalBinder();
    // Registered callbacks
    private ServiceCallbacks serviceCallbacks;

    // Class used for the client Binder.
    public class LocalBinder extends Binder {
        TTSService getService() {
            // Return this instance of MyService so clients can call public methods
            return TTSService.this;
        }
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
        Log.i(TAG, "onStop service");
    }


    @Override
    protected void onSynthesizeText(SynthesisRequest synthesisRequest, SynthesisCallback synthesisCallback) {

    }

    @Override
    public IBinder onBind(Intent intent) {

        Log.v(TAG, "onbind_service");

        return  binder;
    }

    @Override
    public boolean onUnbind(Intent intent) {
        Log.v(TAG, "onunbinding service");
        return super.onUnbind(intent);
    }

    public void setCallbacks(ServiceCallbacks callbacks) {
        serviceCallbacks = callbacks;
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
                say(str,str1);
            }
        } else {
            Log.v(TAG, "Could not initialize TextToSpeech.");
        }
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {

        Log.v(TAG, "onstart service");
        if (intent !=null && intent.getExtras()!=null){
            str = intent.getExtras().getString("content_to_speak");
            str1=intent.getExtras().getString("options");

        }
        tts = new TextToSpeech(this,this);  // OnInitListener
        tts.setSpeechRate(0.75f);
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {

        Log.v(TAG, "Destroy Service");
        // TODO Auto-generated method stub
        if (tts != null) {

            tts=null;
        }
        super.onDestroy();
    }

    private void say(String str,String str1) {


            HashMap<String, String> map = new HashMap<String, String>();
            map.put(TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID, "UniqueID");
            tts.speak(str, TextToSpeech.QUEUE_FLUSH, map);
            tts.speak(str1, TextToSpeech.QUEUE_ADD, map);
            
            //Utteranceprogresslistener used to identify whenn TTS is completed...
            tts.setOnUtteranceProgressListener(new UtteranceProgressListener() {
                @Override
                public void onStart(String s) {
                    //speakig started
                    Log.i(TAG, "started speaking ");
                }

                @Override
                public void onDone(String s) {
                    //speaking stopped
                    Log.v(TAG, "about to listen "+ s);
                 if (serviceCallbacks != null) {
                    serviceCallbacks.doSomething();
                }

                }

                @Override
                public void onError(String s) {
                    // there was an error
                    Log.e("error","error:"+s);
                }

            });
    }

}


