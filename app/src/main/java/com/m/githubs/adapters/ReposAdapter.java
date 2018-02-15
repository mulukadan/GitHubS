package com.m.githubs.adapters;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.m.githubs.R;
import com.m.githubs.model.Repo;

import java.util.List;

/**
 * Created by kadan on 2/1/18.
 */

public class ReposAdapter extends RecyclerView.Adapter<ReposAdapter.ViewHolder> {
    private List<Repo> repos;
    private Context context;

    public ReposAdapter(Context applicationContext, List<Repo> repoArray){
        this.context = applicationContext;
        this.repos= repoArray;
    }

    @Override
    public ReposAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.row_repo, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReposAdapter.ViewHolder viewHolder, int position) {
        viewHolder.name.setText(repos.get(position).getName());
        viewHolder.folk.setText(String.valueOf(repos.get(position).getForks()));
        viewHolder.date.setText(repos.get(position).getPushedAt().substring(0,9));
        String sizeString="";
        int sizeInt = repos.get(position).getSize();

                if(sizeInt > 999){
                    double sizeDouble = sizeInt/1000;
                    sizeDouble = Math.round(sizeDouble * 100.0) / 100.0;
                    sizeString = String.valueOf(sizeDouble) + "Mbs";
                }
                else {
                    sizeString = String.valueOf(sizeInt) + "Kbs";
                }
        viewHolder.size.setText(sizeString);
        viewHolder.language.setText(repos.get(position).getLanguage());

        final String Repourl = repos.get(position).getHtmlUrl().toString();

        viewHolder.cardView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(Repourl));
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return repos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView name, folk, date, language,size;
        private CardView cardView;

        public ViewHolder(View view){
            super(view);
            name = (TextView) view.findViewById(R.id.name);
            folk=(TextView) view.findViewById(R.id.forks);
            date=(TextView) view.findViewById(R.id.date);
            size=(TextView) view.findViewById(R.id.size);
            language=(TextView) view.findViewById(R.id.language);
            cardView = (CardView)view.findViewById(R.id.cardview);

            }

    }
}
