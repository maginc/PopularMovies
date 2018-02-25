package com.example.user.popularmoviesstage1.view;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.user.popularmoviesstage1.R;
import com.example.user.popularmoviesstage1.model.Constants;
import com.example.user.popularmoviesstage1.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Andris on 024 24.02.18.
 */

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Movie> movies;
    private Context context;

    public RecyclerViewAdapter(List<Movie> movies, Context context) {
        this.movies = movies;
        this.context = context;
    }


    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recyclerview_item, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {

        Movie movie = movies.get(position);
        Picasso.with(context)
                .load(Constants.API_POSTER_HEADER_LARGE + movie.getPosterPath())
                .into(holder.imagePoster);


    }

    @Override
    public int getItemCount() {
        return movies.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ImageView imagePoster;


        public ViewHolder(View itemView) {
            super(itemView);
            imagePoster = itemView.findViewById(R.id.image_poster);
        }

        @Override
        public void onClick(View view) {

        }
    }
}
