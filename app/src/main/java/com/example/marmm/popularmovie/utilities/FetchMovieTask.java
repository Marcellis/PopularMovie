package com.example.marmm.popularmovie.utilities;

import android.os.AsyncTask;

import com.example.marmm.popularmovie.model.Movie;

import java.net.URL;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by marmm on 21/12/2017.
 */

public class FetchMovieTask extends AsyncTask<String, Void, List<Movie>> {

    private AsyncTaskCompleteListener<List<Movie>> listener;

    public FetchMovieTask(AsyncTaskCompleteListener<List<Movie>> listener) {
        this.listener = listener;
    }

    @Override
    protected List<Movie> doInBackground(String... params) {
        // if there is no movie request there is nothing to look up
        if (params.length == 0)
            return null;

        String movieRequest = params[0];

        //build url for request movies
        //URL movieRequestUrl = NetworkUtils.buildUrl(movieRequest);

        try {

            Request request = NetworkUtils.buildUrl(movieRequest);
            OkHttpClient client = new OkHttpClient();
            Response jsonMovieResponse  = client.newCall(request).execute();

            // String jsonVideoResponse = NetworkUtils.getResponseFromHttpUrl(videoRequestUrl);
            return MovieJSON.getJSON(jsonMovieResponse.body().string());



//            String jsonMovieResponse = NetworkUtils.getResponseFromHttpUrl(movieRequestUrl);
 //           return MovieJSON.getJSON(jsonMovieResponse);
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }


    @Override
    protected void onPostExecute(List<Movie> movieData) {
        listener.onTaskComplete(movieData);
    }
}
