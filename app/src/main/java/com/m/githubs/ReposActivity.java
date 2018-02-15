package com.m.githubs;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.m.githubs.adapters.ItemAdapter;
import com.m.githubs.adapters.ReposAdapter;
import com.m.githubs.api.Client;
import com.m.githubs.api.Service;
import com.m.githubs.controller.MainActivity;
import com.m.githubs.model.Item;
import com.m.githubs.model.ItemResponse;
import com.m.githubs.model.Repo;
import com.m.githubs.model.RepoResponse;

import java.util.List;

import retrofit2.Call;

public class ReposActivity extends AppCompatActivity {
    private String keyword, language;
    private ProgressDialog pd;
    private SwipeRefreshLayout swipeContainer;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_repos);

        Intent intent = getIntent();
        keyword = intent.getStringExtra("keyword");
        language = intent.getStringExtra("language");
        getSupportActionBar().setTitle("Search Results");

        initViews();

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        swipeContainer.setColorSchemeResources(android.R.color.holo_orange_dark);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                loadJSON();
                Toast.makeText(ReposActivity.this,"Repos refreshed", Toast.LENGTH_SHORT).show();
            }
        });
    }
    private  void  initViews(){
        pd = new ProgressDialog(this);
        pd.setMessage("Searching Github Repos....");
        pd.setCancelable(false);
        pd.show();
        recyclerView=(RecyclerView) findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getApplicationContext()));
        recyclerView.smoothScrollToPosition(0);
        loadJSON();
    }

    private void loadJSON(){
        try {
            Client client = new Client();
            Service apiService = Client.getClient().create(Service.class);
            Call<RepoResponse> call = apiService.getRepos("/search/repositories?q="+keyword+"+language:"+language+"&sort=stars&order=desc&per_page=100&"+ Constants.TOKEN);
            call.enqueue(new retrofit2.Callback<RepoResponse>() {
                @Override
                public void onResponse(Call<RepoResponse> call, retrofit2.Response<RepoResponse> response) {
                    List<Repo> repos = response.body().getItems();
                    recyclerView.setAdapter(new ReposAdapter(getApplicationContext(),repos));

                    recyclerView.smoothScrollToPosition(0);
                    swipeContainer.setRefreshing(false);
                    pd.hide();
                }

                @Override
                public void onFailure(Call<RepoResponse> call, Throwable t) {
                    Log.d("Error: ",t.getMessage());
                    Toast.makeText(ReposActivity.this, "Error Fecthing Data!", Toast.LENGTH_SHORT).show();

                    pd.hide();
                }
            });

        }catch (Exception e){
            Log.d("Error: " , e.getMessage());
            Toast.makeText(ReposActivity.this, e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }
}
