package com.m.githubs.controller;

/**
 * Created by kadan on 2/6/18.
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeintent = new Intent(getApplicationContext(), SearchActivity.class);
                startActivity(homeintent);
                finish();
            }
        },2000);
    }
}
