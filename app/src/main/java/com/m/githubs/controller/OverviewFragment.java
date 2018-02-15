package com.m.githubs.controller;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.util.Linkify;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.m.githubs.Constants;
import com.m.githubs.R;
import com.m.githubs.api.Client;
import com.m.githubs.api.Service;
import com.m.githubs.model.User;

import org.parceler.Parcels;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;
import retrofit2.Call;
import retrofit2.Callback;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

public class OverviewFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

    android.widget.Toolbar mActionBarToolbar;
    String mUser, mUserUrl, mavatarUrl;

    private OnFragmentInteractionListener mListener;

    public OverviewFragment() {
        // Required empty public constructor
    }

    public static OverviewFragment newInstance(String user, String url) {
        OverviewFragment fragment = new OverviewFragment();
        Bundle args = new Bundle();
        args.putParcelable("user", Parcels.wrap(user));
        args.putParcelable("url", Parcels.wrap(url));
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mUser = Parcels.unwrap(getArguments().getParcelable("user"));
        mUserUrl = Parcels.unwrap(getArguments().getParcelable("user"));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_overview, container, false);

        final TextView Link = (TextView) view.findViewById(R.id.link);
        final TextView Username = (TextView) view.findViewById(R.id.username);
        final TextView Following = (TextView) view.findViewById(R.id.following);
        final TextView Followers = (TextView) view.findViewById(R.id.followers);
        final TextView Created = (TextView) view.findViewById(R.id.created);
        final TextView Repos = (TextView) view.findViewById(R.id.repos);
        final TextView Bio = (TextView) view.findViewById(R.id.bio);
        final TextView Location = (TextView) view.findViewById(R.id.location);
        final TextView Company = (TextView) view.findViewById(R.id.company);
        final ImageView imageView = (ImageView) view.findViewById(R.id.uer_image_header);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(mavatarUrl));
                startActivity(intent);
            }
        });

        try {
            Client client = new Client();
            Service apiService = Client.getClient().create(Service.class);
            Call<User> call = apiService.getUser("/users/"+mUser+"?"+ Constants.TOKEN);
            call.enqueue(new Callback<User>() {
                @Override
                public void onResponse(Call<User> call, retrofit2.Response<User> response) {
                    String login = response.body().getLogin();
                    String avatarUrl = response.body().getAvatarUrl();
                    mavatarUrl = avatarUrl;
                    String name = response.body().getName();
                    String htmlUrl = response.body().getHtmlUrl();
                    String followers = String.valueOf(response.body().getFollowers());
                    String following = String.valueOf(response.body().getFollowing());
                    String repos = String.valueOf(response.body().getPublicRepos());
                    String created = String.valueOf(response.body().getCreatedAt());
                    String bio = response.body().getBio();
                    String loc = response.body().getLocation();
                    String company = response.body().getCompany();
                    mUserUrl = response.body().getHtmlUrl();
                    Link.setText(htmlUrl);
                    Linkify.addLinks(Link, Linkify.WEB_URLS);

                    Username.setText(name +"("+login+")");
                    Followers.setText(followers);
                    Following.setText(following);
                    Repos.setText(repos);
                    Created.setText("Created on: "+created.substring(0,9));
                    Bio.setText(bio);
                    Location.setText(loc);
                    Company.setText(company);

                    Glide.with(imageView.getContext())
                            .load(avatarUrl)
                            .apply(bitmapTransform(new RoundedCornersTransformation(128, 0, RoundedCornersTransformation.CornerType.ALL)))
                            .into(imageView);
                }

                @Override
                public void onFailure(Call<User> call, Throwable t) {
                    Log.d("Error: ",t.getMessage());
                    Toast.makeText(getContext(), "Error Fecthing Data!", Toast.LENGTH_SHORT).show();
                }
            });

        }catch (Exception e){
            Log.d("Error: " , e.getMessage());
            Toast.makeText(getContext(), e.getMessage(),Toast.LENGTH_SHORT).show();
        }

//        loadUser();

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

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
