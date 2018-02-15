package com.m.githubs;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.m.githubs.api.Client;
import com.m.githubs.api.Service;
import com.m.githubs.controller.SearchActivity;
import com.m.githubs.controller.UserTabActivity;
import com.m.githubs.model.User;

import retrofit2.Call;
import retrofit2.Callback;

public class SearchUserFragment extends Fragment {

    private OnFragmentInteractionListener mListener;
    private EditText usernameET;
    private Button searchBtn;
    private ProgressDialog pd;

    public SearchUserFragment() {
        // Required empty public constructor
    }

    public static SearchUserFragment newInstance() {
        SearchUserFragment fragment = new SearchUserFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_search_user, container, false);

        usernameET= (EditText)view.findViewById(R.id.username);
        searchBtn = (Button)view.findViewById(R.id.searchS);

        searchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String usrName = usernameET.getText().toString().trim();

                if(!usrName.isEmpty()){
                    pd = new ProgressDialog(getContext());
                    pd.setMessage("Looking for "+ usrName+"...");
                    pd.setCancelable(false);
                    pd.show();
                    userFound(usrName);
                }else{
                    usernameET.setError("Please Provide Username");
                    usernameET.requestFocus();
                }
            }
        });

        return view;
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

                    Intent intent = new Intent(getContext(), UserTabActivity.class);
                    intent.putExtra("login", user);
                    String url = "https://github.com/"+user;

                    intent.putExtra("html_url", url );

                    startActivity(intent);
                    usernameET.setText("");
                    pd.hide();
                    Toast.makeText(getContext(), "Loading " + user+"...", Toast.LENGTH_SHORT).show();

                }catch (Exception e){
                    pd.hide();
                    Toast.makeText(getContext(),"User Not Found",Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Log.d("Error: ",t.getMessage());
                    Toast.makeText(getContext(), "Error Fecthing Data!:" +t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });


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
