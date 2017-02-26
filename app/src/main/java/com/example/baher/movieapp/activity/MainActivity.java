package com.example.baher.movieapp.activity;

import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.baher.movieapp.R;


public class MainActivity extends ActionBarActivity   {

   static int  position;

    public static int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }
    private static Context context;
    public static int type;
    FragmentManager fm;
    FragmentTransaction ft;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        String status = NetworkUtil.getConnectivityStatusString(this);
        if(status.equals("Not connected to Internet")){
            Toast.makeText(this, status, Toast.LENGTH_LONG).show();
        }





    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        Intent i;
        switch (id) {

            case R.id.popular:
                type = 0;
                i = getIntent();
                finish();
                startActivity(i);

                return true;

            case R.id.toprated:
                type = 1;
                i = getIntent();
                finish();
                startActivity(i);

                break;
            case R.id.favourites:
                type = 2;
                i = getIntent();
                finish();
                startActivity(i);
                break;

            default:
                return super.onOptionsItemSelected(item);
        }
        return super.onOptionsItemSelected(item);


    }


}
