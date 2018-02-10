package com.example.marmm.popularmovie.activity;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.app.LoaderManager;
import android.support.v4.content.CursorLoader;
import android.support.v4.content.Loader;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.marmm.popularmovie.R;
import com.example.marmm.popularmovie.adapter.MovieAdapter;
import com.example.marmm.popularmovie.database.MoviesContract;
import com.example.marmm.popularmovie.model.Movie;
import com.example.marmm.popularmovie.utilities.AsyncTaskCompleteListener;
import com.example.marmm.popularmovie.utilities.FetchMovieTask;

import java.util.ArrayList;
import java.util.List;

import static com.example.marmm.popularmovie.utilities.NetworkUtils.isConnected;


public class MainActivity extends AppCompatActivity implements MovieAdapter.MovieClickListener, LoaderManager.LoaderCallbacks<Cursor> {

    public static final String POPULAR_MOVIE_API = "popular";
    public static final String TOP_RATED_MOVIE_API = "top_rated";
    private static final String MOVIE_STATE_KEY = "state";
    private static final String FAVORITE_MOVIE_API = "favorite";
    public static final String LISTSTATE = "liststate";
    //, AdapterView.OnItemSelectedListener
    private MovieAdapter mMovieAdapter;
    private static final int NUMBEROFCOLUMNS = 2;
    public static final String INTENT_MOVIE = "movie";
    private ProgressBar mLoadingIndicator;
    private Parcelable listState;
    String mMovieState;
    RecyclerView mRecyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mRecyclerView = findViewById(R.id.recyclerview_main);
        mLoadingIndicator = findViewById(R.id.pb_loading_indicator);
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        //define grid layout
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, NUMBEROFCOLUMNS));
        mRecyclerView.setHasFixedSize(true);
        mMovieAdapter = new MovieAdapter(this);
        //Apply the adapter to the recyclerview
        mRecyclerView.setAdapter(mMovieAdapter);

        // recovering the instance state
        if (savedInstanceState != null) {

            listState =  savedInstanceState.getParcelable (LISTSTATE);

            mMovieState = savedInstanceState.getString(MOVIE_STATE_KEY);
            switch (mMovieState) {
                case POPULAR_MOVIE_API:
                    loadPopular();
                    break;
                case TOP_RATED_MOVIE_API:
                    loadTopRated();
                    break;
                case FAVORITE_MOVIE_API:
                    getSupportLoaderManager().initLoader(0, null, this);
                    break;
            }
        } else {
            loadPopular();
        }
    }

    // invoked when the activity may be temporarily destroyed, save the instance state here
    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(MOVIE_STATE_KEY, mMovieState);
        outState.putParcelable (LISTSTATE, mRecyclerView.getLayoutManager().onSaveInstanceState() );
        // call superclass to save any view hierarchy
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_movie, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // check for internet connection
        {
            switch (item.getItemId()) {
                case R.id.popular:
                    loadPopular();
                    return true;
                case R.id.toprated:
                    loadTopRated();
                    return true;
                case R.id.favorite:
                    getSupportLoaderManager().initLoader(0, null, this);
                    return true;
                default:
                    return super.onOptionsItemSelected(item);
            }
        }

    }

    private void loadPopular() {
        mMovieState = POPULAR_MOVIE_API;
        if (isConnected(this)) {
            mLoadingIndicator.setVisibility(View.VISIBLE);
            new FetchMovieTask(new FetchMyDataTaskCompleteListener()).execute(POPULAR_MOVIE_API);
        }
        else
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show();
    }

    private void loadTopRated() {
        mMovieState = TOP_RATED_MOVIE_API;
        if (isConnected(this)) {
            mLoadingIndicator.setVisibility(View.VISIBLE);
            new FetchMovieTask(new FetchMyDataTaskCompleteListener()).execute(TOP_RATED_MOVIE_API);
        }
        else
            Toast.makeText(this, "No internet connection", Toast.LENGTH_LONG).show();
    }

    @Override
    public Loader<Cursor> onCreateLoader(int id, Bundle args) {

    CursorLoader cursorLoader = new CursorLoader(this, MoviesContract.CONTENT_URI, null, null, null, null);
        return cursorLoader;
    }

    @Override
    public void onLoadFinished(Loader<Cursor> loader, Cursor cursor) {
     //   loadFavorites();
        mMovieState = FAVORITE_MOVIE_API;
        mLoadingIndicator.setVisibility(View.VISIBLE);

        List<Movie> list = new ArrayList<>();
        while (cursor.moveToNext()) {
            String id = cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_NAME_ID));
            String imagename = cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_NAME_IMAGENAME));
            String overview = cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_NAME_OVERVIEW));
            String releasedate = cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_NAME_RELEASEDATE));
            String title = cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_NAME_TITLE));
            String voteaverage = cursor.getString(cursor.getColumnIndex(MoviesContract.MovieEntry.COLUMN_NAME_VOTEAVERAGE));
            Movie data = new Movie(id, imagename, overview, voteaverage, releasedate, title);
            list.add(data);
        }
        mLoadingIndicator.setVisibility(View.INVISIBLE);
        mMovieAdapter.setMovieData(list);

    }

    @Override
    public void onLoaderReset(Loader<Cursor> loader) {
    }


    public class FetchMyDataTaskCompleteListener implements AsyncTaskCompleteListener<List<Movie>> {
        @Override
        public void onTaskComplete(List<Movie> movieData) {
            mLoadingIndicator.setVisibility(View.INVISIBLE);
            mMovieAdapter.setMovieData(movieData);
            mRecyclerView.getLayoutManager().onRestoreInstanceState(listState);
        }
    }


    @Override
    public void MovieOnClick(Movie movie) {
        Context context = this;
        Class destinationClass = DetailMovieActivity.class;
        Intent intentToStartDetailActivity = new Intent(context, destinationClass);
        intentToStartDetailActivity.putExtra(INTENT_MOVIE, movie);
        startActivity(intentToStartDetailActivity);
    }

    @Override
    protected void onResume() {
        if (mMovieState==FAVORITE_MOVIE_API)
            //loadFavorites();
        getSupportLoaderManager().initLoader(0, null, this);
           super.onResume();
    }
}
