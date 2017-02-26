package com.example.baher.movieapp.activity;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import com.example.baher.movieapp.R;

import java.util.Timer;
import java.util.TimerTask;

public class MovieDetails extends ActionBarActivity {
    static ProgressDialog  dialog;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_movie_details);
        dialog = ProgressDialog.show(this, "",
                "Loading. Please wait...", true);
        long delayInMillis = 1500;
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                dialog.dismiss();
            }
        }, delayInMillis);
    }
    public static void dismiss(){
        dialog.dismiss();
    }

    @Override
    public void onBackPressed()
    {
        dialog.dismiss();
        super.onBackPressed();  // optional depending on your needs
    }


}
