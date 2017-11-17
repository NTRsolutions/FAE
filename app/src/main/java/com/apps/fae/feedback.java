package com.apps.fae;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;


/**
 * Created by Yu on 2017/11/15.
 */



public class feedback extends AppCompatActivity {
    private Context mContext;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(com.apps.fae.R.layout.feedback);

        mContext = feedback.this;


        Toolbar toolbar = (Toolbar) findViewById(com.apps.fae.R.id.toolbar);

        setSupportActionBar(toolbar);

        toolbar.setTitle("Feedback");



        }



    }

