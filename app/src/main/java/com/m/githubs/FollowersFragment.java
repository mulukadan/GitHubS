package com.m.githubs;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.m.githubs.api.Client;
import com.m.githubs.api.Service;
import com.m.githubs.model.Follower;
import com.m.githubs.model.Repo;

import java.util.List;

import retrofit2.Call;

public class FollowersFragment extends Fragment {
    private String mUser;
    RecyclerView recyclerView;

    private OnFragmentInteractionListener mListener;

    public FollowersFragment() {
        // Required empty public constructor
    }

    public static FollowersFragment newInstance(String user) {
        FollowersFragment fragment = new FollowersFragment();
        Bundle args = new Bundle();
        args.putString("user", user);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUser = getArguments().getString("user");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_followers, container, false);
        recyclerView=(RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.smoothScrollToPosition(0);
        loadJSON();
        return view;
    }

    private void loadJSON(){
        try {
            Client client = new Client();
            Service apiService = Client.getClient().create(Service.class);
            Call<List<Follower>> call = apiService.getUserFollowers("/users/"+mUser+"/followers?"+ Constants.TOKEN);
            call.enqueue(new retrofit2.Callback<List<Follower>>() {
                @Override
                public void onResponse(Call<List<Follower>> call, retrofit2.Response<List<Follower>> response) {
                    List<Follower> followers = response.body();
                    recyclerView.setAdapter(new FollowerAdapter(getContext(),followers));
                    recyclerView.smoothScrollToPosition(0);
                }

                @Override
                public void onFailure(Call<List<Follower>> call, Throwable t) {
                    Log.d("Error: ",t.getMessage());
                    Toast.makeText(getContext(), "Error Fecthing Data!", Toast.LENGTH_SHORT).show();

                }
            });

        }catch (Exception e){
            Log.d("Error: " , e.getMessage());
            Toast.makeText(getContext(), e.getMessage(),Toast.LENGTH_SHORT).show();
        }
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

   public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
