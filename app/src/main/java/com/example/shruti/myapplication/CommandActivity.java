package com.example.shruti.myapplication;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

public class CommandActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_command);

        Intent speechIntent = new Intent(CommandActivity.this, TTSService.class);
        speechIntent.putExtra("content","welcome to commands section !");
        startService(speechIntent);

        //stopService(speechIntent);
    }

}
