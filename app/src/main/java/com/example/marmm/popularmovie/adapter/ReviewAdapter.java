package com.example.marmm.popularmovie.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.marmm.popularmovie.R;
import com.example.marmm.popularmovie.model.Review;

import java.util.List;

/**
 * Created by marmm on 1/26/18.
 */

public class ReviewAdapter extends RecyclerView.Adapter<ReviewAdapter.ViewHolder> {

    private List<Review> mReviews;
    private Context mContext;


    public void setReviewData(List<Review> reviewData) {
        mReviews = reviewData;
        notifyDataSetChanged();
    }


    public ReviewAdapter() {
    }

    @Override
    public ReviewAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        mContext = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(mContext);
        boolean shouldAttachToParentImmediately = false;
        View view = inflater.inflate(R.layout.review_item, parent, shouldAttachToParentImmediately);

        // Return a new holder instance
        return new ReviewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ReviewAdapter.ViewHolder holder, int position) {
        Review review = mReviews.get(position);
        holder.textViewReview.setText(review.getContent());
    }


    @Override
    public int getItemCount() {
        if (mReviews == null) return 0;
        return mReviews.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        public ImageButton play;
        public TextView textViewReview;

        public ViewHolder(View itemView) {
            super(itemView);
            play = itemView.findViewById(R.id.playButton);
            textViewReview = itemView.findViewById(R.id.textViewReview);

        }


    }

}


