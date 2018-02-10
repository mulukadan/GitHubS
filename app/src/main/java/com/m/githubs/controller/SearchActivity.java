package com.m.githubs.controller;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.m.githubs.R;
import com.m.githubs.UserTabActivity;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener{

    EditText username, location,language;
    Button searchM, searchS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        username = (EditText) findViewById(R.id.username);
        location = (EditText) findViewById(R.id.location);
        language = (EditText) findViewById(R.id.language);
        searchM = (Button)findViewById(R.id.searchM);
        searchS = (Button)findViewById(R.id.searchS);

        searchM.setOnClickListener(this);
        searchS.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        if(v == searchM){
            String loc = location.getText().toString();
            String lang = language.getText().toString();

            if(loc.length()>2 && lang.length()>0) {

                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                intent.putExtra("location", loc);
                intent.putExtra("language", lang);
                startActivity(intent);
            }else{
                Toast.makeText(SearchActivity.this, "Enter Location & Language!!", Toast.LENGTH_LONG).show();
            }
        }

        if(v == searchS){
            String usrName = username.getText().toString();

            if(usrName.length()>2) {

                Intent intent = new Intent(getApplicationContext(), UserTabActivity.class);
                intent.putExtra("login", usrName);
                intent.putExtra("html_url", "https://github.com/"+usrName);
                username.setText("");
                startActivity(intent);
                Toast.makeText(v.getContext(), "Loading " + usrName+"...", Toast.LENGTH_SHORT).show();

            }else{
                Toast.makeText(SearchActivity.this, "Enter Username!!", Toast.LENGTH_LONG).show();
            }
        }
    }
}
