package com.m.githubs;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class SearchReposFragment extends Fragment {
    private OnFragmentInteractionListener mListener;
    private EditText keywordET, languageET;
    private Button searchReposBtn;

    public SearchReposFragment() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static SearchReposFragment newInstance() {
        SearchReposFragment fragment = new SearchReposFragment();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_search_repos, container, false);
        keywordET = (EditText)view.findViewById(R.id.keywordET);
        languageET = (EditText)view.findViewById(R.id.languageET);

        searchReposBtn= (Button)view.findViewById(R.id.searchRepos);
        searchReposBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String keyword = keywordET.getText().toString().trim();
                String language = languageET.getText().toString().trim();

                if(keyword.isEmpty()){
                    keywordET.setError("Enter repo keyword to search");
                    keywordET.requestFocus();
                    return;
                }
                if(language.isEmpty()){
                    languageET.setError("Enter repo keyword to search");
                    languageET.requestFocus();
                    return;
                }

                Intent intent = new Intent(getContext(), ReposActivity.class);
                intent.putExtra("keyword", keyword);
                intent.putExtra("language", language);
                startActivity(intent);
            }
        });

        return view;
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
