package com.m.githubs.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.m.githubs.R;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener{

    EditText username, location,language;
    Button searchM;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        username = (EditText) findViewById(R.id.username);
        location = (EditText) findViewById(R.id.location);
        language = (EditText) findViewById(R.id.language);
        searchM = (Button)findViewById(R.id.searchM);

        String usrName = username.getText().toString();

        searchM.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == searchM){

            Toast.makeText(SearchActivity.this, "Search Clicked", Toast.LENGTH_LONG);
            String loc = location.getText().toString();
            String lang = language.getText().toString();

            Intent intent = new Intent(getApplicationContext(), MainActivity.class);
            startActivity(intent);
        }
    }
}
