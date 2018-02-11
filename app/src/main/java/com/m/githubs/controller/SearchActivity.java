package com.m.githubs.controller;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.util.Linkify;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.m.githubs.Constants;
import com.m.githubs.R;
import com.m.githubs.ReposAdapter;
import com.m.githubs.UserTabActivity;
import com.m.githubs.api.Client;
import com.m.githubs.api.Service;
import com.m.githubs.model.Repo;
import com.m.githubs.model.User;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import retrofit2.Call;
import retrofit2.Callback;

import static android.content.ContentValues.TAG;
import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class SearchActivity extends AppCompatActivity implements View.OnClickListener{

    EditText username, location,language;
    Button searchM, searchS;
    ProgressDialog pd;

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

            if(usrName.length()>0){
                pd = new ProgressDialog(this);
                pd.setMessage("Looking for "+ usrName+"...");
                pd.setCancelable(false);
                pd.show();
            userFound(usrName);
            }else{
                Toast.makeText(SearchActivity.this, "Enter Username!!", Toast.LENGTH_LONG).show();
            }
        }
    }

    public void userFound(final String user){

//        try {
            Client client = new Client();
            Service apiService = Client.getClient().create(Service.class);
            Call<User> call = apiService.getUser("/users/"+user+"?"+ Constants.TOKEN);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                   try {
                       String login = response.body().getLogin();

                       Intent intent = new Intent(getApplicationContext(), UserTabActivity.class);
                       intent.putExtra("login", user);
                       String url = "https://github.com/"+user;

                       intent.putExtra("html_url", url );
                       username.setText("");

                       startActivity(intent);
                       pd.hide();
                       Toast.makeText(getApplicationContext(), "Loading " + user+"...", Toast.LENGTH_SHORT).show();

                   }catch (Exception e){
                       pd.hide();
                       Toast.makeText(getApplicationContext(),"User Not Found",Toast.LENGTH_SHORT).show();
                   }



                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.d("Error: ",t.getMessage());
//                    Toast.makeText(thi, "Error Fecthing Data!", Toast.LENGTH_SHORT).show();
                }
            });

//        }catch (Exception e){
//            Log.d("Error: " , e.getMessage());
//            Toast.makeText(getApplicationContext(), e.getMessage(),Toast.LENGTH_SHORT).show();
//        }

    }
}
