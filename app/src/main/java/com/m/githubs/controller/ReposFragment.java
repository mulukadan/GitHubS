package com.m.githubs.controller;

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

import com.m.githubs.Constants;
import com.m.githubs.R;
import com.m.githubs.adapters.ReposAdapter;
import com.m.githubs.api.Client;
import com.m.githubs.api.Service;
import com.m.githubs.model.Repo;

import java.util.List;

import retrofit2.Call;

import static android.content.ContentValues.TAG;

public class ReposFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String USER = "User";
    private static final String GET_WHAT = "GetWhat";
    RecyclerView recyclerView;

    // TODO: Rename and change types of parameters
    private String mUser,mGetWhat;

    private OnFragmentInteractionListener mListener;

    public ReposFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static ReposFragment newInstance(String user, String getWhat) {
        ReposFragment fragment = new ReposFragment();
        Bundle args = new Bundle();
        args.putString(USER, user);
        args.putString(GET_WHAT, getWhat);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mUser = getArguments().getString(USER);
            mGetWhat = getArguments().getString(GET_WHAT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_repos, container, false);
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
            Call<List<Repo>> call = apiService.getUserRepos("/users/"+mUser+"/"+mGetWhat+"?"+ Constants.TOKEN);
            call.enqueue(new retrofit2.Callback<List<Repo>>() {
                @Override
                public void onResponse(Call<List<Repo>> call, retrofit2.Response<List<Repo>> response) {
                    List<Repo> repos = response.body();
                    try {
                        Log.e(TAG, "repos Size: " + repos.size());
                        recyclerView.setAdapter(new ReposAdapter(getContext(), repos));
                        recyclerView.smoothScrollToPosition(0);
                    }catch (Exception e){
                        Toast.makeText(getContext(), "User Not Found", Toast.LENGTH_LONG).show();
                    }
                }

                @Override
                public void onFailure(Call<List<Repo>> call, Throwable t) {
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
