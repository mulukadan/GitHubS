package com.m.githubs.controller;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.util.Linkify;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.Toolbar;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.ViewTarget;
import com.m.githubs.Constants;
import com.m.githubs.R;
import com.m.githubs.api.Client;
import com.m.githubs.api.Service;
import com.m.githubs.model.User;
import com.squareup.picasso.Transformation;

import jp.wasabeef.glide.transformations.BlurTransformation;
import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import retrofit2.Call;
import retrofit2.Callback;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;


/**
 * Created by kadan on 2/1/18.
 */

public class DetailActivity extends AppCompatActivity {
    TextView Link, Username, Following, Followers, Created, Repos, Bio;
    Toolbar mActionBarToolbar;
    ImageView imageView;
    String mUser, mUserUrl;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        imageView = (ImageView) findViewById(R.id.uer_image_header);
        Username = (TextView) findViewById(R.id.username);
        Following = (TextView) findViewById(R.id.following);
        Followers = (TextView) findViewById(R.id.followers);
        Created = (TextView) findViewById(R.id.created);
        Repos = (TextView) findViewById(R.id.repos);
        Bio = (TextView) findViewById(R.id.bio);
        Link = (TextView) findViewById(R.id.link);

        mUser = getIntent().getExtras().getString("login");

        loadUser();
        getSupportActionBar().setTitle("Details Activity");
    }
    private Intent createShareForcastIntent(){
        String username = getIntent().getExtras().getString("login");
        String link = getIntent().getExtras().getString("html_url");
        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setText("Check out this awesome developer @"+ username+", "+link)
                .getIntent();
        return shareIntent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.detail, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        menuItem.setIntent(createShareForcastIntent());
        return true;
    }

    private void loadUser(){
         try {
            Client client = new Client();
            Service apiService = Client.getClient().create(Service.class);
            Call<User> call = apiService.getUser("/users/"+mUser+"?"+ Constants.TOKEN);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                    String login = response.body().getLogin();
                    String avatarUrl = response.body().getAvatarUrl();
                    String name = response.body().getName();
                    String htmlUrl = response.body().getHtmlUrl();
                    String followers = String.valueOf(response.body().getFollowers());
                    String following = String.valueOf(response.body().getFollowing());
                    String repos = String.valueOf(response.body().getPublicRepos());
                    String created = String.valueOf(response.body().getCreatedAt());
                    String bio = response.body().getBio();
                    mUserUrl = response.body().getHtmlUrl();

                    Link.setText(htmlUrl);
                    Linkify.addLinks(Link, Linkify.WEB_URLS);

                    Username.setText("Name: "+name +"("+login+")");
                    Followers.setText("Followers: "+followers);
                    Following.setText("Following: "+following);
                    Repos.setText("Public Repos: "+repos);
                    Created.setText("Created at: "+created);
                    Bio.setText("Bio: "+bio);

                    Glide.with(imageView.getContext())
                            .load(avatarUrl)
                            .apply(bitmapTransform(new RoundedCornersTransformation(128, 0, RoundedCornersTransformation.CornerType.BOTTOM)))
                            .into(imageView);

//                    final ViewTarget<ImageView, Drawable> into;
//                    into = Glide.with(imageView.getContext())
//                            .apply(bitmapTransform(new BlurTransformation(25)))
//                            .load(avatarUrl)
//                            .into(imageView);
               }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.d("Error: ",t.getMessage());
                    Toast.makeText(DetailActivity.this, "Error Fecthing Data!", Toast.LENGTH_SHORT).show();
                }
            });

        }catch (Exception e){
            Log.d("Error: " , e.getMessage());
            Toast.makeText(DetailActivity.this, e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}
