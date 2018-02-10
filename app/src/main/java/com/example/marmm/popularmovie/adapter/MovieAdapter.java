package com.example.marmm.popularmovie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.marmm.popularmovie.R;
import com.example.marmm.popularmovie.model.Movie;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by marmm on 07/12/2017.
 */

public class MovieAdapter extends RecyclerView.Adapter<MovieAdapter.ViewHolder> {

    private MovieClickListener mMovieClickListener;
    private List<Movie> mMovies;
    private Context mContext;


    public void setMovieData(List<Movie> movieData) {
        mMovies = movieData;
        notifyDataSetChanged();
    }

    public interface MovieClickListener {
        void MovieOnClick(Movie movie);
    }

    public MovieAdapter(MovieClickListener mMovieClickListener) {
        this.mMovieClickListener = mMovieClickListener;

    }

    @Override
    public MovieAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(R.layout.movie_item, parent, shouldAttachToParentImmediately);

        // Return a new holder instance
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Movie movie = mMovies.get(position);
        Picasso.with(mContext).load("http://image.tmdb.org/t/p/w185/" + movie.getImagename()).into(holder.imageView);
    }


    @Override
    public int getItemCount() {
        if (mMovies == null) return 0;
        return mMovies.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        public ImageView imageView;

        public ViewHolder(View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageView_main);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            int clickedPosition = getAdapterPosition();
            mMovieClickListener.MovieOnClick(mMovies.get(clickedPosition));
        }
    }


}
