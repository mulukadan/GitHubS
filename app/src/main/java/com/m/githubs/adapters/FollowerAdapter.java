package com.m.githubs.adapters;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.m.githubs.R;
import com.m.githubs.controller.UserTabActivity;
import com.m.githubs.model.Follower;

import java.util.List;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.bumptech.glide.request.RequestOptions.bitmapTransform;

/**
 * Created by kadan on 2/1/18.
 */

public class FollowerAdapter extends RecyclerView.Adapter<FollowerAdapter.ViewHolder> {
    private List<Follower> followers;
    private Context context;

    public FollowerAdapter(Context applicationContext, List<Follower> itemArray){
        this.context = applicationContext;
        this.followers = itemArray;
    }

    @Override
    public FollowerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_user, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(FollowerAdapter.ViewHolder viewHolder, int position) {
        viewHolder.title.setText(followers.get(position).getLogin());
        viewHolder.githibLink1.setText(followers.get(position).getHtmlUrl());

        Glide.with(context)
                .load(followers.get(position).getAvatarUrl())
                .apply(bitmapTransform(new RoundedCornersTransformation(188, 0, RoundedCornersTransformation.CornerType.ALL)))
                .into(viewHolder.imageView);
    }

    @Override
    public int getItemCount() {
        return followers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView title, githibLink1;
        private ImageView imageView;

        public ViewHolder(View view){
            super(view);
            title = (TextView) view.findViewById(R.id.title);
            imageView = (ImageView) view.findViewById(R.id.cover);
            githibLink1=(TextView) view.findViewById(R.id.githublink1);

            //On item Click
            imageView.setOnClickListener(new View.OnClickListener(){

                @Override
                public void onClick(View v) {
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        Follower clickedDataItem = followers.get(pos);
//                        Intent intent = new Intent(context, DetailActivity.class);
                        Intent intent = new Intent(context, UserTabActivity.class);
                        intent.putExtra("login", followers.get(pos).getLogin());
                        intent.putExtra("html_url", followers.get(pos).getHtmlUrl());
                        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        context.startActivity(intent);
                        Toast.makeText(v.getContext(), "Loading " + clickedDataItem.getLogin()+"...", Toast.LENGTH_SHORT).show();
                    }
                }
            });
        }

    }
}
