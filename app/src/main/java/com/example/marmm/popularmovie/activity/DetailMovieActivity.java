package com.example.marmm.popularmovie.activity;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.example.marmm.popularmovie.R;
import com.example.marmm.popularmovie.adapter.ReviewAdapter;
import com.example.marmm.popularmovie.adapter.VideoAdapter;
import com.example.marmm.popularmovie.database.DBHelper;
import com.example.marmm.popularmovie.database.MoviesContract;
import com.example.marmm.popularmovie.model.Movie;
import com.example.marmm.popularmovie.model.Review;
import com.example.marmm.popularmovie.model.Video;
import com.example.marmm.popularmovie.utilities.AsyncTaskCompleteListener;
import com.example.marmm.popularmovie.utilities.FetchReviewTask;
import com.example.marmm.popularmovie.utilities.FetchVideoTask;
import com.squareup.picasso.Picasso;

import java.util.List;

public class DetailMovieActivity extends AppCompatActivity implements VideoAdapter.VideoClickListener {

    private static final String PICASSO_PATH = "http://image.tmdb.org/t/p/w185/";
    public static final String VIDEOS = "/videos";
    public static final String REVIEWS = "/reviews";
    private VideoAdapter mVideoAdapter;
    private ReviewAdapter mReviewAdapter;
    private Movie mMovie;
    RecyclerView mRecyclerView;
    RecyclerView mRecyclerViewReview;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_movie);

        ImageView imageView = findViewById(R.id.imageView_detail);
        TextView overview = findViewById(R.id.overview);
        TextView voteAverage = findViewById(R.id.vote_average);
        TextView releaseDate = findViewById(R.id.release_date);
        TextView title = findViewById(R.id.title);
        ToggleButton popularButton = findViewById(R.id.popularButton);


        Intent intentThatStartedThisActivity = getIntent();

        if (intentThatStartedThisActivity != null) {
            if (intentThatStartedThisActivity.hasExtra(MainActivity.INTENT_MOVIE)) {
                mMovie = intentThatStartedThisActivity.getParcelableExtra(MainActivity.INTENT_MOVIE);
                Picasso.with(this).load(PICASSO_PATH + mMovie.getImagename()).into(imageView);
                overview.setText(mMovie.getOverview());
                voteAverage.setText(mMovie.getVoteAverage());
                releaseDate.setText(mMovie.getReleaseDate());
                title.setText(mMovie.getTitle());

                Cursor cursor = getContentResolver().query(MoviesContract.CONTENT_URI, null
                        , "id = ?", new String[]{mMovie.getId()}, null);

                cursor.moveToFirst();
                if (cursor.isBeforeFirst()) //means empty result set
                    //do your stuff when cursor is empty
                    popularButton.setChecked(false);
                else
                    popularButton.setChecked(true);
            }

        }


        popularButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {

                if (isChecked) {
                    // The toggle is enabledT
                    ContentValues values = new ContentValues();
                    values.put(MoviesContract.MovieEntry.COLUMN_NAME_ID, mMovie.getId());
                    values.put(MoviesContract.MovieEntry.COLUMN_NAME_IMAGENAME, mMovie.getImagename());
                    values.put(MoviesContract.MovieEntry.COLUMN_NAME_OVERVIEW, mMovie.getOverview());
                    values.put(MoviesContract.MovieEntry.COLUMN_NAME_VOTEAVERAGE, mMovie.getVoteAverage());
                    values.put(MoviesContract.MovieEntry.COLUMN_NAME_TITLE, mMovie.getTitle());
                    values.put(MoviesContract.MovieEntry.COLUMN_NAME_RELEASEDATE, mMovie.getReleaseDate());
                    getContentResolver().insert(MoviesContract.CONTENT_URI, values);
                } else {
                    // The toggle is disabled
                    getContentResolver().delete(MoviesContract.CONTENT_URI, "id = ?", new String[]{mMovie.getId()});
                }
            }
        });


        // recyclerview trailers
        mRecyclerView = findViewById(R.id.videoRecyclerView);
        mRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerView.setHasFixedSize(true);
        mVideoAdapter = new VideoAdapter(this);

        //Apply the adapter to the recyclerview
        mRecyclerView.setAdapter(mVideoAdapter);
        getData(mMovie.getId(), VIDEOS);

        mRecyclerViewReview = findViewById(R.id.reviewRecyclerView);
        mRecyclerViewReview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        mRecyclerViewReview.setHasFixedSize(true);
        mReviewAdapter = new ReviewAdapter();

        //Apply the adapter to the recyclerview
        mRecyclerViewReview.setAdapter(mReviewAdapter);
        getData(mMovie.getId(), REVIEWS);

    }



    @Override
    public void VideoOnClick(Video video) {
        Intent webIntent = new Intent(Intent.ACTION_VIEW,
                Uri.parse("http://www.youtube.com/watch?v=" + video.getKey()));
        if (webIntent.resolveActivity(getPackageManager()) != null) {
            startActivity(webIntent);
        }
    }


    public void getData(String id, String dateRequest) {
        // check for internet connection
        ConnectivityManager cm =
                (ConnectivityManager) this.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        boolean isConnected = activeNetwork != null &&
                activeNetwork.isConnectedOrConnecting();

        if (isConnected) {
            switch (dateRequest) {
                case VIDEOS:
                    new FetchVideoTask(new FetchMyDataTaskCompleteListener()).execute(id + dateRequest);
                    break;
                case REVIEWS:
                    new FetchReviewTask(new FetchReviewTaskCompleteListener()).execute(id + dateRequest);
                    // Statements
                    break; // optional
            }

        } else {
            Toast.makeText(this, "No internet connection", Toast.LENGTH_SHORT).show();
        }
    }

    public class FetchMyDataTaskCompleteListener implements AsyncTaskCompleteListener<List<Video>> {
        @Override
        public void onTaskComplete(List<Video> videoData) {
            mVideoAdapter.setVideoData(videoData);
                    }
    }

    public class FetchReviewTaskCompleteListener implements AsyncTaskCompleteListener<List<Review>> {
        @Override
        public void onTaskComplete(List<Review> reviewData) {
            mReviewAdapter.setReviewData(reviewData);
            
        }
    }

}


